/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch17.sound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.border.TitledBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;

// import javax.sound.sampled.Control;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 505-511. This class is a
 * Swing component that can load and play a sound clip, displaying progress and
 * controls. The main() method is a test program. This component can play
 * sampled audio or MIDI files, but handles them differently. For sampled audio,
 * time is reported in microseconds, tracked in milliseconds and displayed in
 * seconds and tenths of seconds. For MIDI files time is reported, tracked, and
 * displayed in MIDI "ticks". This program does no transcoding, so it can only
 * play sound files that use the PCM encoding.
 * 
 * <pre>
 * MIDI: musical instrument digital interface
 * PCM:  pulse-code modulation
 * </pre>
 * 
 * To test this program, there is a Run Configuration "SoundPlayer", with
 * pathways to a WAV file and a MIDI file which are checked into the code base.
 * The SoundPlayer program will play whichever music file appears first in the
 * Run Configuration. The the Run Configuration needs a few variables concerning
 * your local file system, which you will need to define.
 */
public class SoundPlayer extends JComponent {

	private static final long serialVersionUID = -6792635608575322562L;

	// Are we playing a midi file or a sampled one?
	private boolean midi;
	// The contents of a MIDI file:
	private Sequence sequence;
	// We play MIDI Sequences with a Sequencer:
	private Sequencer sequencer;
	// Contents of a sampled audio file:
	private Clip clip;
	// Whether the sound is current playing:
	boolean playing = false;

	/*
	 * Length and position of the sound are measured in milliseconds for sampled
	 * sounds and MIDI "ticks" for MIDI sounds.
	 */
	// Length of the sound:
	private int audioLength;
	// Current position within the sound:
	private int audioPosition = 0;

	/* The following fields are for the GUI: */
	// The Play/Stop button:
	private JButton play;
	// Shows and sets current position in sound:
	private JSlider progress;
	// Displays audioPosition as a number:
	private JLabel time;
	// Updates slider every 100 milliseconds:
	private Timer timer;

	// The main method just creates an SoundPlayer in a Frame and displays it.
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
			MidiUnavailableException, InvalidMidiDataException {
		SoundPlayer player;

		// This is the file we'll be playing:
		File file = new File(args[0]);
		// Determine whether it is midi or sampled audio.
		boolean isMidi;
		try {
			/*
			 * We discard the return value of this method; we just need to know whether it
			 * returns successfully or throws an exception.
			 */
			MidiSystem.getMidiFileFormat(file);
			isMidi = true;
		} catch (InvalidMidiDataException e) {
			isMidi = false;
		}

		// Create a SoundPlayer object to play the sound.
		player = new SoundPlayer(file, isMidi);

		// Put it in a window and play it.
		JFrame jFrame = new JFrame("SoundPlayer");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().add(player, "Center");
		jFrame.pack();
		jFrame.setVisible(true);
	}

	// Create an SoundPlayer component for the specified file.
	public SoundPlayer(File file, boolean isMidi) throws IOException, UnsupportedAudioFileException,
			LineUnavailableException, MidiUnavailableException, InvalidMidiDataException {
		if (isMidi) { // The file is a MIDI file.
			midi = true;
			/*
			 * First, get a Sequencer to play sequences of MIDI events. That is, to send
			 * events to a Synthesizer at the right time.
			 */
			// Used to play sequences:
			sequencer = MidiSystem.getSequencer();
			// Turn it on:
			sequencer.open();

			// Get a Synthesizer for the Sequencer to send notes to:
			Synthesizer synthesizer = MidiSystem.getSynthesizer();
			// Acquire whatever resources it needs:
			synthesizer.open();

			/*
			 * The Sequencer obtained above may be connected to a Synthesizer by default, or
			 * it may not. Therefore, we explicitly connect it.
			 */
			Transmitter transmitter = sequencer.getTransmitter();
			Receiver receiver = synthesizer.getReceiver();
			transmitter.setReceiver(receiver);

			// Read the sequence from the file and tell the sequencer about it.
			sequence = MidiSystem.getSequence(file);
			sequencer.setSequence(sequence);
			// Get sequence length:
			audioLength = (int) sequence.getTickLength();
		} else {
			// The file is sampled audio:
			midi = false;
			/*
			 * Getting a Clip object for a file of sampled audio data is kind of cumbersome.
			 * The following lines do what we need.
			 */
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			try {
				DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(audioInputStream);
			} finally {
				// We're done with the input stream.
				audioInputStream.close();
			}
			// Get the clip length in microseconds, and convert it to milliseconds.
			audioLength = (int) (clip.getMicrosecondLength() / 1000);
		}

		/* Now create the basic GUI. */
		// Play/stop button:
		play = new JButton("Play");
		// Shows position in sound:
		progress = new JSlider(0, audioLength, 0);
		// Shows position as a number:
		time = new JLabel("0");

		// When clicked, start or stop playing the sound.
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (playing) {
					stop();
				} else {
					play();
				}
			}
		});

		/*
		 * Whenever the slider value changes, first update the time label. Next, if
		 * we're not already at the new position, skip to it.
		 */
		progress.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				int value = progress.getValue();
				// Update the time label
				if (midi == true) {
					time.setText(value + "");
				} else {
					time.setText(value / 1000 + "." + (value % 1000) / 100);
				}
				// If we're not already there, skip there.
				if (value != audioPosition) {
					skip(value);
				}
			}
		});

		/*
		 * This timer calls the tick() method 10 times a second to keep our slider in
		 * sync with the music.
		 */
		timer = new javax.swing.Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				tick();
			}
		});

		// Put those controls in a row::
		Box row = Box.createHorizontalBox();
		row.add(play);
		row.add(progress);
		row.add(time);

		// ..and, add them to this component.
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(row);

		// Now add additional controls based on the type of the sound.
		if (midi == true) {
			addMidiControls();
		} else
			addSampledControls();
	}

	/** Start playing the sound at the current position. */
	public void play() {
		if (midi == true) {
			sequencer.start();
		} else {
			clip.start();
		}
		timer.start();
		play.setText("Stop");
		playing = true;
	}

	/** Stop playing the sound, but retain the current position. */
	public void stop() {
		timer.stop();
		if (midi == true) {
			sequencer.stop();
		} else {
			clip.stop();
		}
		play.setText("Play");
		playing = false;
	}

	/** Stop playing the sound, and reset the position to zero. */
	public void reset() {
		stop();
		if (midi == true) {
			sequencer.setTickPosition(0);
		} else {
			clip.setMicrosecondPosition(0);
		}
		audioPosition = 0;
		progress.setValue(0);
	}

	/** Skip to the specified position. */
	// Called when user drags the slider:
	public void skip(int position) {
		if (position < 0 || position > audioLength) {
			return;
		}
		audioPosition = position;
		if (midi == true) {
			sequencer.setTickPosition(position);
		} else {
			clip.setMicrosecondPosition(position * 1000);
		}
		// In case skip() is called from outside:
		progress.setValue(position);
	}

	/** Return the length of the sound in microseconds or ticks. */
	public int getLength() {
		return audioLength;
	}

	/*
	 * An internal method that updates the progress bar. The Timer object calls it
	 * 10 times a second. If the sound has finished, it resets to the beginning.
	 */
	void tick() {
		if (midi == true && sequencer.isRunning()) {
			audioPosition = (int) sequencer.getTickPosition();
			progress.setValue(audioPosition);
		} else if (midi == false && clip.isActive()) {
			audioPosition = (int) (clip.getMicrosecondPosition() / 1000);
			progress.setValue(audioPosition);
		} else {
			reset();
		}
	}

	// For sampled sounds, add sliders to control volume and balance.
	void addSampledControls() {
		try {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			if (gainControl != null) {
				this.add(createSlider(gainControl));
			}
		} catch (IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace(System.err);
		}

		try {
			/*
			 * FloatControl.Type.BALANCE is probably the correct control to use here, but it
			 * doesn't work for me, so I use PAN instead.
			 */

			FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
			if (panControl != null) {
				this.add(createSlider(panControl));
			}
		} catch (IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace(System.err);
		}
	}

	@SuppressWarnings("unchecked")
	/*
	 * Return a JSlider component to manipulate the supplied FloatControl for
	 * sampled audio.
	 */
	JSlider createSlider(final FloatControl floatControl) {
		if (floatControl == null) {
			return null;
		}
		final JSlider jSlider = new JSlider(0, 1000);
		final float minimum = floatControl.getMinimum();
		final float maximum = floatControl.getMaximum();
		final float width = maximum - minimum;
		float floatControlValue = floatControl.getValue();
		jSlider.setValue((int) ((floatControlValue - minimum) / width * 1000));

		Map<Integer, JLabel> labels = new Hashtable<Integer, JLabel>(3);
		labels.put(0, new JLabel(floatControl.getMinLabel()));
		labels.put(500, new JLabel(floatControl.getMidLabel()));
		labels.put(1000, new JLabel(floatControl.getMaxLabel()));

		jSlider.setLabelTable((Dictionary<Integer, JLabel>) labels);
		jSlider.setPaintLabels(true);

		jSlider.setBorder(new TitledBorder(floatControl.getType().toString() + " " + floatControl.getUnits()));

		jSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				int jSliderValue = jSlider.getValue();
				float currentSliderPosition = minimum + (jSliderValue * width / 1000.0f);
				floatControl.setValue(currentSliderPosition);
			}
		});
		return jSlider;
	}

	/*
	 * For Midi files, create a JSlider to control the tempo, and create JCheckBoxes
	 * to mute or solo each MIDI track.
	 */
	void addMidiControls() {
		// Add a slider to control the tempo:
		final JSlider tempo = new JSlider(50, 200);
		tempo.setValue((int) (sequencer.getTempoFactor() * 100));
		tempo.setBorder(new TitledBorder("Tempo Adjustment (%)"));
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		labels.put(50, new JLabel("50%"));
		labels.put(100, new JLabel("100%"));
		labels.put(200, new JLabel("200%"));
		tempo.setLabelTable(labels);
		tempo.setPaintLabels(true);
		// The event listener actually changes the tempo:
		tempo.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				sequencer.setTempoFactor(tempo.getValue() / 100.0f);
			}
		});

		this.add(tempo);

		// Create rows of solo and checkboxes for each track.
		Track[] tracks = sequence.getTracks();
		for (int i = 0; i < tracks.length; i++) {
			final int trackNumber = i;
			// Two checkboxes per track
			final JCheckBox solo = new JCheckBox("solo");
			final JCheckBox mute = new JCheckBox("mute");
			// The listeners solo or mute the track:
			solo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					sequencer.setTrackSolo(trackNumber, solo.isSelected());
				}
			});
			mute.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					sequencer.setTrackMute(trackNumber, mute.isSelected());
				}
			});

			// Build up a row:
			Box box = Box.createHorizontalBox();
			box.add(new JLabel("Track " + trackNumber));
			box.add(Box.createHorizontalStrut(10));
			box.add(solo);
			box.add(Box.createHorizontalStrut(10));
			box.add(mute);
			box.add(Box.createHorizontalGlue());
			// ...and, add it to this component.
			this.add(box);
		}
	}
}
