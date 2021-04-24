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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 521-522. This program
 * plays the MIDI percussion channel with a Swing window. It monitors keystrokes
 * and mouse motion in the window and uses them to create music. Key codes
 * between 35 and 81, inclusive, generate different percussive sounds. See the
 * VK_ constants in java.awt.event.KeyEvent, or just experiment. Mouse position
 * controls volume: move the mouse to the right of the window to increase the
 * volume.
 */
public class Drums extends JFrame {

	private static final long serialVersionUID = 4626081380990883349L;

	// The channel we play on. Channel 10 is for percussion.
	MidiChannel channel;
	// Default volume is 50%:
	int velocity = 64;

	public static void main(String[] args) throws MidiUnavailableException {
		/*
		 * We don't need a Sequencer in this example, since we send MIDI events directly
		 * to the Synthesizer instead.
		 */
		Synthesizer synthesizer = MidiSystem.getSynthesizer();
		synthesizer.open();
		JFrame jFrame = new Drums(synthesizer);

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// We use window width as volume control:
		jFrame.setSize(200, 128);
		jFrame.setTitle("Volume");

		JLabel jLabel00 = new JLabel("Click Here For Volume Control");
		jFrame.add(jLabel00);
		jFrame.setVisible(true);
	}

	public Drums(Synthesizer synthesizer) {
		super("Drums");

		/*
		 * Channel 10 is the GeneralMidi percussion channel. In Java code, we number
		 * channels from 0 and use channel 9 instead.
		 */
		channel = synthesizer.getChannels()[9];

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				int key = keyEvent.getKeyCode();
				if (key >= 35 && key <= 81) {
					channel.noteOn(key, velocity);
				}
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				int key = keyEvent.getKeyCode();
				if (key >= 35 && key <= 81) {
					channel.noteOff(key);
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mouseEvent) {
				velocity = mouseEvent.getX();
			}
		});
	}
}
