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
package je3.ch06.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 147-148. This class
 * demonstrates file locking, and simple file read-and-write operations, using
 * java.nio.channels.FileChannel. It uses file locking to prevent two instances
 * of some certain program from running at the same time.
 * <p/>
 * The goal is not to prevent a process from writing to the locked file. Instead
 * the goal is to prevent two instances of some given program from operating at
 * the same time. In the example here there is only one all-or-nothing lock, but
 * it should be possible to design finer-grained locks to coordinate different
 * VMs on smaller tasks.
 * <p/>
 * The manual notes that it is the VM that holds this lock, not the thread. All
 * of the threads in the VM have access to the locked resource. So, to
 * coordinate thread access, some different mechanism is needed.
 * <p/>
 * To test this, run this same program a few times in two different consoles at
 * about the same time. More testing will be needed if this lock is used in an
 * actual program or production environment.
 */
public class Lock {
	public static void main(String args[]) throws IOException, InterruptedException {
		// The file we'll lock:
		RandomAccessFile randomAccessFile = null;
		// The channel to the file:
		FileChannel fileChannel = null;
		// The lock object we hold:
		FileLock fileLock = null;

		// The "finally" clause below closes the channel and releases the lock.

		// We use a temporary file as the lock file.
		String tempDirectory = System.getProperty("java.io.tmpdir");
		String lockFileName = Lock.class.getName() + ".lock";
		File lockFile = new File(tempDirectory, lockFileName);

		try {
			randomAccessFile = new RandomAccessFile(lockFile, "rw");
			/*
			 * Create a FileChannel that can read and write that lock file. Note that we
			 * rely on the java.io package to open the file, in read/write mode, and then
			 * just get a channel from it. This will create the file if it doesn't exit.
			 * We'll arrange for it to be deleted below, if we succeed in locking it.
			 */
			fileChannel = randomAccessFile.getChannel();

			/*
			 * Try to get an exclusive lock on the file. This method will return a lock or
			 * null, but will not block. See also FileChannel.lock() for a blocking variant.
			 */
			fileLock = fileChannel.tryLock();

			if (fileLock != null) {
				/*
				 * We obtained the lock, so arrange to delete the file when we're done. Then
				 * write the approximate time at which we'll relinquish the lock into the file.
				 */
				System.out.println("Obtained the lock");
				// It's just a temporary file.
				lockFile.deleteOnExit();

				/*
				 * First, we need a buffer to hold the timestamp. Note here a timestamp is a
				 * long, which is 8 bytes.
				 */
				ByteBuffer byteBuffer = ByteBuffer.allocate(8);

				/*
				 * Put the time (plus ten seconds) in the buffer, and flip to prepare for
				 * writing. The flip() resets the buffer for reading. Note that many Buffer
				 * methods can be "chained" like this.
				 */
				byteBuffer.putLong(System.currentTimeMillis() + 10000).flip();

				// Write the buffer contents to the channel.
				fileChannel.write(byteBuffer);
				// Force them out to the disk.
				fileChannel.force(false);
			} else {
				/*
				 * We didn't get the lock, which means another instance is running. First, let
				 * the user know this.
				 */
				System.out.println("Sorry, another instance is already running. Could not get the lock.");

				/*
				 * Next, we attempt to read the file to figure out how much longer the other
				 * instance will be running. Since we don't have a lock, the read may fail or
				 * return inconsistent data.
				 */
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(8);
					/*
					 * Read 8 bytes from the file. This will only work on certain operating systems
					 * which allow read-only access to locked files. It's not clear which systems
					 * allow this.
					 */
					fileChannel.read(byteBuffer);
					// Flip buffer before extracting bytes. This resets it for reading.
					byteBuffer.flip();
					// Read bytes as a long.
					long releaseTime = byteBuffer.getLong();
					/*
					 * Figure out how long that time is from now, and round it to the nearest
					 * second.
					 */
					long seconds = (releaseTime - System.currentTimeMillis() + 500) / 1000;
					// ...and tell the user about it.
					System.out.println("Try again in about " + seconds + " seconds");
				} catch (IOException ioException) {
					/*
					 * This probably means that locking is enforced by the operating system and we
					 * were prevented from reading the file.
					 */
					System.out.println("Sorry, could not read waiting time from lock.");
					// ioException.printStackTrace(System.err);
				}

				// This is an abnormal exit, so set an exit code.
				System.exit(1);
			}

			// Simulate a real application by sleeping for 10 seconds.
			System.out.println("Working while holding the lock...");
			Thread.sleep(10000);
			System.out.println("Done working. Exiting.");
		} finally {
			/*
			 * Always release the lock and close the file. Closing the RandomAccessFile also
			 * closes its FileChannel.
			 */
			if (fileLock != null && fileLock.isValid()) {
				fileLock.release();
				System.out.println("Released the lock");
			}
			if (randomAccessFile != null) {
				randomAccessFile.close();
			}
		}
	}
}
