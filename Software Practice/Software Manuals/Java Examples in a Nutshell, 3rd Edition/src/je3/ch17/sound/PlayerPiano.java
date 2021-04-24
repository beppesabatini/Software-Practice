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

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 516-520. The PianoPlayer
 * class can play back or save to disk a (fairly small) subset of MIDI
 * functionality. The test data from the manual sticks to that small subset, and
 * will play here by default. But, the only way to come up with any more of this
 * mini-MIDI test data, is to write it yourself. In other words, this program
 * can't play anything besides that one default string.
 * 
 * <pre>
 * Usage:
  -i &lt;instrument number> default 0, a piano.  Allowed values: 0-127
  -t &lt;beats per minute>  default tempo is 120 quarter notes per minute
  -o &lt;fileName>          save to a midi file instead of playing
 * </pre>
 */
public class PlayerPiano {
	/*
	 * These are some MIDI constants from the specification. They aren't defined for
	 * us in javax.sound.midi.
	 */
	public static final int DAMPER_PEDAL = 64;
	public static final int DAMPER_ON = 127;
	public static final int DAMPER_OFF = 0;
	public static final int END_OF_TRACK = 47;

	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
		int instrument = 0;
		int tempo = 120;
		String fileName = null;
		String defaultPianoRoll = "A B C D E F G +A s/32 D E C D E C /1-->>CEG";

		// Parse the options:
		int a = 0;
		while (a < args.length) {
			if (args[a].equals("-i")) {
				instrument = Integer.parseInt(args[a + 1]);
				a += 2;
			} else if (args[a].equals("-t")) {
				tempo = Integer.parseInt(args[a + 1]);
				a += 2;
			} else if (args[a].equals("-o")) {
				fileName = args[a + 1];
				a += 2;
			} else
				break;
		}

		char[] notes;
		if (args[a] != null && "".equals(args[a])) {
			notes = args[a].toCharArray();
		} else {
			notes = defaultPianoRoll.toCharArray();
		}

		// 16 ticks per quarter note.
		Sequence sequence = new Sequence(Sequence.PPQ, 16);

		// Add the specified notes to the track.
		addTrack(sequence, instrument, tempo, notes);

		if (fileName == null) {
			// No file name to which to output, so play the notes.
			// Set up the Sequencer and Synthesizer objects:
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			Synthesizer synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

			// Specify the sequence to play, and the tempo at which to play it.
			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(tempo);

			// Let us know when it is done playing.
			sequencer.addMetaEventListener(new MetaEventListener() {
				@Override
				public void meta(MetaMessage metaMessage) {
					/*
					 * A message of this type is automatically sent when we reach the end of the
					 * track.
					 */
					if (metaMessage.getType() == END_OF_TRACK) {
						System.exit(0);
					}
				}
			});
			// ...and, start playing now.
			sequencer.start();
		} else {
			// A file name was specified, so save the notes.
			int[] allowedTypes = MidiSystem.getMidiFileTypes(sequence);
			if (allowedTypes.length == 0) {
				System.err.println("No supported MIDI file types.");
			} else {
				MidiSystem.write(sequence, allowedTypes[0], new File(fileName));
				System.exit(0);
			}
		}
	}

	/**
	 * <pre>
	 Add these amounts to the base value:
	  A   B   C   D   E   F   G
	 -4, -2,  0,  1,  3,  5,  7
	 * </pre>
	 */
	static final int[] offsets = { -4, -2, 0, 1, 3, 5, 7 };

	/**
	 * This method parses the specified char[] of notes into a Track. The musical
	 * notation is the following:
	 * 
	 * <pre>
	 *   A-G: A named note; Add ♭  for flat and ♯ for sharp.
	 *     +: Move up one octave. Persists.
	 *     -: Move down one octave. Persists.
	 *    /1: Notes are whole notes. Persists 'till changed.
	 *    /2: Half notes
	 *    /4: Quarter notes
	 *    /n: N can also be, 8, 16, 32, or 64.
	 *     s: Toggle - sustain pedal on or off (initially off)
	 * 
	 *     >: Louder. Persists.
	 *     <: Softer. Persists.
	 *     .: Rest. Length depends on current length setting.
	 * Space: Play the previous note or notes. Notes not separated by spaces
	 *        are played at the same time.
	 * </pre>
	 * 
	 * This is the string the PlayerPiano run configuration plays:
	 * 
	 * <pre>
	 * "A B C D E F G +A s/32 D E C D E C /1-->>CEG"
	 * </pre>
	 */
	public static void addTrack(Sequence sequence, int instrument, int tempo, char[] notes)
			throws InvalidMidiDataException {
		// Begin with a new track:
		Track track = sequence.createTrack();

		// Set the instrument on channel 0:
		ShortMessage programChange = new ShortMessage();
		programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
		track.add(new MidiEvent(programChange, 0));

		// Current character in notes[] array:
		int notesIndex = 0;
		// Time in ticks for the composition:
		int ticks = 0;

		/*
		 * These values persist and apply to all notes until changed:
		 */
		// Default to quarter notes:
		int noteLength = 16;
		// Default to middle volume:
		int velocity = 64;
		// Note that 60 is middle C. Adjusted up and down by octave:
		int baseKey = 60;
		// Is the sustain pedal depressed?
		boolean sustain = false;
		// How many notes in the current chord?
		int numberNotes = 0;

		while (notesIndex < notes.length) {
			char character = notes[notesIndex++];

			if (character == '+') {
				// Increase octave:
				baseKey += 12;
			} else if (character == '-') {
				// Decrease octave:
				baseKey -= 12;
			} else if (character == '>') {
				// Increase volume:
				velocity += 16;
			} else if (character == '<') {
				// Decrease volume:
				velocity -= 16;
			} else if (character == '/') {
				char duration = notes[notesIndex++];
				if (duration == '2') {
					// Half note; 1/2 == 32/64:
					noteLength = 32;
				} else if (duration == '4') {
					// Quarter note; 1/4 == 16/64:
					noteLength = 16;
				} else if (duration == '8') {
					// Eighth note; 1/8 == 8/64:
					noteLength = 8;
				} else if (duration == '3' && notes[notesIndex++] == '2') {
					// A thirty-second note; 1/32 == 2/64:
					noteLength = 2;
				} else if (duration == '6' && notes[notesIndex++] == '4') {
					// A sixty-fourth note; 1/64 == 1/64:
					noteLength = 1;
				} else if (duration == '1') {
					if (notesIndex < notes.length && notes[notesIndex] == '6') {
						// 1/16th note; 1/16 == 4/64:
						noteLength = 4;
					} else {
						// Whole note; 1/1 == 64/64:
						noteLength = 64;
					}
				}
			} else if (character == 's') {
				sustain = !sustain;
				// Change the sustain setting for channel 0:
				ShortMessage damperPedal = new ShortMessage();
				damperPedal.setMessage(ShortMessage.CONTROL_CHANGE, 0, DAMPER_PEDAL, sustain ? DAMPER_ON : DAMPER_OFF);
				track.add(new MidiEvent(damperPedal, ticks));
			} else if (character >= 'A' && character <= 'G') {
				int key = baseKey + offsets[character - 'A'];
				if (notesIndex < notes.length) {
					// Flat:
					if (notes[notesIndex] == 'b' || notes[notesIndex] == '♭') {
						key--;
						notesIndex++;
						// Sharp:
					} else if (notes[notesIndex] == '#' || notes[notesIndex] == '♯') {
						key++;
						notesIndex++;
					}
				}

				addNote(track, ticks, noteLength, key, velocity);
				numberNotes++;
			} else if (character == ' ') {
				/*
				 * Spaces separate groups of notes played at the same time. But we ignore them
				 * unless they follow a note or notes.
				 */
				if (numberNotes > 0) {
					ticks += noteLength;
					numberNotes = 0;
				}
			} else if (character == '.') {
				/*
				 * Rests are like spaces in that they force any previous note to be output
				 * (since they are never part of chords).
				 */
				if (numberNotes > 0) {
					ticks += noteLength;
					numberNotes = 0;
				}
				// Now add additional rest time:
				ticks += noteLength;
			}
		}
	}

	// A convenience method to add a note to the track on channel 0.
	public static void addNote(Track track, int startTick, int tickLength, int key, int velocity)
			throws InvalidMidiDataException {
		ShortMessage on = new ShortMessage();
		on.setMessage(ShortMessage.NOTE_ON, 0, key, velocity);
		ShortMessage off = new ShortMessage();
		off.setMessage(ShortMessage.NOTE_OFF, 0, key, velocity);
		track.add(new MidiEvent(on, startTick));
		track.add(new MidiEvent(off, startTick + tickLength));
	}
}
