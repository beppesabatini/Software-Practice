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

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 512-515. This class plays
 * sounds streaming from a URL: it does not have to preload the entire sound
 * into memory before playing it. It is a command-line application with no gui.
 * It includes code to convert ULAW and ALAW audio formats to PCM so they can be
 * played. Use the -m command-line option before MIDI files.
 * <p>
 * Note midi and wav files are supported, but mp3, flac, and aiff files are not.
 * The point of this example is to stream large files from disk, so there is no
 * GUI (it's a command-line program), and no way to hop around to different
 * sections of a sound file.
 * 
 * <pre>
 * Usage: 
 * java PlaySoundStream -m file:/C:/music/Beatles/myMidiFile.mid
 * java PlaySoundStream    file:/C:/music/Beethoven/myWavFile.wav
 * </pre>
 * 
 * A one-click Demo to launch this program is not possible. There is a Run
 * Configuration "PlaySoundStream", with paths to several sound files which are
 * checked in to the code base. Launching the configuration will play whichever
 * file comes first. Furthermore, the Run Configuration requires a few variables
 * concerning your local file system, which you will need to define.
 */
public class PlaySoundStream {

	static final String DEBUG = "true";
	private static Logger logger = Logger.getLogger(PlaySoundStream.class.getName());

	/*
	 * Create a URL from the command-line argument and pass it to the right static
	 * method depending on the presence of the -m (MIDI) option.
	 */
	public static void main(String[] args) throws Exception {

		if (args[0].equals("-m")) {
			streamMidiSequence(new URL(args[1]));
		} else {
			streamSampledAudio(new URL(args[0]));
		}

		/*
		 * Exit explicitly. This is needed because the audio system starts background
		 * threads.
		 */
		System.exit(0);
	}

	/** Read sampled audio data from the specified URL and play it */
	public static void streamSampledAudio(URL url)
			throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		// We read audio data from here:
		AudioInputStream audioInputStream = null;
		// ...and, write it here:
		SourceDataLine sourceDataLine = null;

		try {
			// Get an audio input stream from the URL:
			audioInputStream = AudioSystem.getAudioInputStream(url);
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}

		try {
			// Get information about the format of the stream:
			AudioFormat format = audioInputStream.getFormat();
			if (Boolean.valueOf(DEBUG) == true) {
				logger.log(Level.INFO, "Original music format: " + format.getEncoding().toString());
			}
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			/*
			 * If the format is not supported directly (i.e. if it is not PCM encoded), then
			 * try to transcode it to PCM (pulse-code modulation).
			 */
			if (!AudioSystem.isLineSupported(info)) {
				/*
				 * This is the PCM format we want to transcode to. The parameters here are audio
				 * format details that you shouldn't need to understand for casual use.
				 */
				float sampleRate = format.getSampleRate();
				AudioFormat pulseCodeModulation = new AudioFormat(sampleRate, 16, format.getChannels(), true, false);

				/*
				 * Get a wrapper stream around the input stream that does the transcoding for
				 * us.
				 */
				audioInputStream = AudioSystem.getAudioInputStream(pulseCodeModulation, audioInputStream);

				// Update the format and info variables for the transcoded data
				format = audioInputStream.getFormat();
				info = new DataLine.Info(SourceDataLine.class, format);
				if (Boolean.valueOf(DEBUG) == true) {
					logger.log(Level.INFO, "Transcoded to: " + format.getEncoding().toString());
				}
			}

			// Open the line through which we'll play the streaming audio.
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceDataLine.open(format);

			/*
			 * Allocate a buffer for reading from the input stream and writing to the line.
			 * Make it large enough to hold 4k audio frames. Note that the SourceDataLine
			 * also has its own internal buffer.
			 */
			int frameSize = format.getFrameSize();
			byte[] buffer = new byte[4 * 1024 * frameSize];
			int numberBytes = 0;

			// We haven't started the line yet.
			boolean started = false;
			// We'll exit the loop when we reach the end of stream.
			for (;;) {
				// First, read some bytes from the input stream.
				int bytesRead = audioInputStream.read(buffer, numberBytes, buffer.length - numberBytes);
				// If there were no more bytes to read, we're done.
				if (bytesRead == -1) {
					break;
				}
				numberBytes += bytesRead;

				/*
				 * Now that we've got some audio data, to write to the line, start the line, so
				 * it will play that data as we write it.
				 */
				if (!started) {
					sourceDataLine.start();
					started = true;
				}

				/*
				 * We must write bytes to the line in an integer multiple of the frame size. So,
				 * figure out how many bytes we'll write.
				 */
				int bytesToWrite = (numberBytes / frameSize) * frameSize;

				/*
				 * Now write the bytes. The line will buffer them and play them. This call will
				 * block until all bytes are written.
				 */
				sourceDataLine.write(buffer, 0, bytesToWrite);

				/*
				 * If we didn't have an integer multiple of the frame size, then copy the
				 * remaining bytes to the start of the buffer.
				 */
				int remaining = numberBytes - bytesToWrite;
				if (remaining > 0) {
					System.arraycopy(buffer, bytesToWrite, buffer, 0, remaining);
				}
				numberBytes = remaining;
			}

			// Now block until all buffered sound finishes playing.
			sourceDataLine.drain();
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		} finally {
			// Always relinquish the resources we use:
			if (sourceDataLine != null) {
				sourceDataLine.close();
			}
			if (audioInputStream != null) {
				audioInputStream.close();
			}
		}
	}

	// A MIDI protocol constant that isn't defined by javax.sound.midi:
	public static final int END_OF_TRACK = 47;

	/* Stream MIDI or RMF data from the specified URL and play it. */
	public static void streamMidiSequence(URL url)
			throws IOException, InvalidMidiDataException, MidiUnavailableException {
		// Converts a Sequence to MIDI events:
		Sequencer sequencer = null;
		// Plays notes in response to MIDI events:
		Synthesizer synthesizer = null;

		try {
			/*
			 * Create, open, and connect a Sequencer and Synthesizer They are closed in the
			 * finally block at the end of this method.
			 */
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

			// Specify the InputStream from which to stream the sequence.
			sequencer.setSequence(url.openStream());

			/*
			 * This is an arbitrary object used with wait and notify to prevent the method
			 * from returning before the music finishes.
			 */
			final Object lock = new Object();

			/*
			 * Register a listener to make the method exit when the stream is done. See
			 * Object.wait() and Object.notify()
			 */
			sequencer.addMetaEventListener(new MetaEventListener() {
				@Override
				public void meta(MetaMessage metaMessage) {
					if (metaMessage.getType() == END_OF_TRACK) {
						synchronized (lock) {
							lock.notify();
						}
					}
				}
			});

			// Start playing the music.
			sequencer.start();

			// Now block until the listener above notifies us that we're done.
			synchronized (lock) {
				while (sequencer.isRunning()) {
					try {
						lock.wait();
					} catch (InterruptedException interruptedException) {
						interruptedException.printStackTrace(System.err);
					}
				}
			}
		} finally {
			// Always relinquish the sequencer, so others can use it.
			if (sequencer != null) {
				sequencer.close();
			}
			if (synthesizer != null) {
				synthesizer.close();
			}
		}
	}
}
