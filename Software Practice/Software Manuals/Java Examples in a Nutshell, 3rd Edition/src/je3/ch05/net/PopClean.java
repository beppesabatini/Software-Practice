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
package je3.ch05.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.StringTokenizer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 116-121. A simple utility
 * program for deleting messages from a POP3 mailbox based on message size and
 * Subject line. Not clear how to test this.
 * <p>
 * Don't run this program unless you understand what it is doing. It deletes
 * e-mail without downloading it: YOU MAY PERMANENTLY LOSE DATA!
 * <p>
 * Typical usage: 1) Look at the subject lines for the big messages you've got
 * 
 * <pre>
 * java PopClean -host host -user user -pass pass -size 100000
 * </pre>
 * 
 * 2) Create a regular expression to match viral subject lines, and use it to
 * delete large matching messages.
 * 
 * <pre>
 * java PopClean -host h -user u -pass p -delete -size 100000 \
 *            -subject 'Thank you!|Re: Your application'
 * </pre>
 * 
 * This will ask for confirmation before proceeding.
 * <p>
 * 3) If you're confident that all big messages are virus-infected, then you can
 * skip the -subject argument and delete on size alone
 * 
 * <pre>
 * java PopClean -host h -user u -pass p -delete -size 100000
 * </pre>
 * 
 * This will ask for confirmation before proceeding.
 */
public class PopClean {
	// The connection to the server:
	static Socket s = null;
	// To read lines from the server:
	static BufferedReader in = null;
	// To write to the server:
	static PrintWriter out = null;
	// Are we in debug mode?
	static String DEBUG = "false";

	public static void main(String args[]) {
		try {
			String hostname = null, username = null, password = null;
			int port = 110;
			int sizelimit = -1;
			String subjectPattern = null;
			Pattern pattern = null;
			Matcher matcher = null;
			boolean delete = false;
			boolean confirm = true;

			// Handle command-line arguments
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-user")) {
					username = args[++i];
				} else if (args[i].equals("-pass") || args[i].equals("-password")) {
					password = args[++i];
				} else if (args[i].equals("-host")) {
					hostname = args[++i];
				} else if (args[i].equals("-port")) {
					port = Integer.parseInt(args[++i]);
				} else if (args[i].equals("-size")) {
					sizelimit = Integer.parseInt(args[++i]);
				} else if (args[i].equals("-subject")) {
					subjectPattern = args[++i];
				} else if (args[i].equals("-debug")) {
					DEBUG = "true";
				} else if (args[i].equals("-delete")) {
					delete = true;
				} else if (args[i].equals("-force")) { // Don't confirm.
					confirm = false;
				}
			}

			// Verify them
			if (hostname == null || username == null || password == null || sizelimit == -1) {
				usage();
			}

			// Make sure the pattern is a valid regexp.
			if (subjectPattern != null) {
				pattern = Pattern.compile(subjectPattern);
				matcher = pattern.matcher("");
			}

			// Say what we are going to do.
			System.out.println("Connecting to " + hostname + " on port " + port + " with username " + username + ".");
			if (delete) {
				System.out.println("Will delete all messages longer than " + sizelimit + " bytes");
				if (subjectPattern != null) {
					System.out.println("that have a subject matching: [" + subjectPattern + "]");
				}
			} else {
				System.out.println("Will list subject lines for messages " + "longer than " + sizelimit + " bytes");
				if (subjectPattern != null) {
					System.out.println("that have a subject matching: [" + subjectPattern + "]");
				}
			}

			// If asked to delete, ask for confirmation unless -force is given.
			if (delete && confirm) {
				System.out.println();
				System.out.print("Do you want to proceed (y/n) [n]: ");
				System.out.flush();
				BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
				String response = console.readLine();
				if (!response.equals("y")) {
					System.out.println("No messages deleted.");
					System.exit(0);
				}
			}

			// Connect to the server, and set up streams.
			s = new Socket(hostname, port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

			// Read the welcome message from the server, confirming it is OK.
			System.out.println("Connected: " + checkResponse());

			// Now log in.
			send("USER " + username); // Send username, wait for response.
			send("PASS " + password); // Send password, wait for response.
			System.out.println("Logged in");

			// Check how many messages are waiting, and report it.
			String stat = send("STAT");
			StringTokenizer t = new StringTokenizer(stat);
			System.out.println(t.nextToken() + " messages in mailbox.");
			System.out.println("Total size: " + t.nextToken());

			// Get a list of message numbers and sizes.
			send("LIST"); // Send LIST command, wait for OK response.
			// Now read lines from the server until we get a period ('.') by itself.
			List<String> msgs = new ArrayList<String>();
			String line;
			for (;;) {
				line = in.readLine();
				if (line == null) {
					throw new IOException("Unexpected EOF");
				}
				if (line.equals(".")) {
					break;
				}
				msgs.add(line);
			}

			// Now loop through the lines we read one at a time.
			// Each line should specify the message number and its size.
			int nummsgs = msgs.size();
			for (int i = 0; i < nummsgs; i++) {
				String m = (String) msgs.get(i);
				StringTokenizer st = new StringTokenizer(m);
				int msgnum = Integer.parseInt(st.nextToken());
				int msgsize = Integer.parseInt(st.nextToken());

				// If the message is too small, ignore it.
				if (msgsize <= sizelimit) {
					continue;
				}

				// If we're listing messages, or matching subject lines
				// find the subject line for this message.
				String subject = null;
				if (!delete || pattern != null) {
					// Get the subject line.
					subject = getSubject(msgnum);

					// If we couldn't find a subject, skip the message.
					if (subject == null) {
						continue;
					}

					// If this subject does not match the pattern, then
					// skip the message.
					if (pattern != null) {
						matcher.reset(subject);
						if (!matcher.matches()) {
							continue;
						}
					}

					// If we are listing, list this message.
					if (!delete) {
						System.out.println("Subject " + msgnum + ": " + subject);
						continue; // so we never delete it
					}
				}

				// If we were asked to delete, then delete the message.
				if (delete) {
					send("DELE " + msgnum);
					if (pattern == null) {
						System.out.println("Deleted message " + msgnum);
					} else {
						System.out.println("Deleted message " + msgnum + ": " + subject);
					}
				}
			}

			// When we're done, log out and shutdown the connection.
			shutdown();
		} catch (Exception e) {
			// If anything goes, wrong print the exception and show usage.
			System.err.println(e);
			usage();
			// Always try to shutdown nicely, so the server doesn't hang on us.
			shutdown();
		}
	}

	// Explain how to use the program.
	public static void usage() {

		String usageMessage = "";
		usageMessage += "Options are:\n";
		usageMessage += "-host <hostname>  # Required\n";
		usageMessage += "-port <port>      # Optional; default is 110\n";
		usageMessage += "-user <username>  # Required\n";
		usageMessage += "-pass <password>  # Required and sent as cleartext; APOP not supported\n";
		usageMessage += "-size <limit>     # Required. Message size in bytes. Shorter messages are ignored.\n";
		usageMessage += "-subject <regexp> # Optional java.util.regex.Pattern regular expression\n";
		usageMessage += "                  # only messages with a matching Subject line are deleted\n";
		usageMessage += "-delete           # Delete messages; the default is just to list them\n";
		usageMessage += "-force            # Don't ask for confirmation before deleting\n";
		usageMessage += "-debug            # Display POP3 protocol requests and responses\n";

		System.err.println("java PopClean <options>");
		System.err.println(usageMessage);

		System.exit(1);
	}

	// Send a POP3 command to the server and return its response.
	public static String send(String cmd) throws IOException {
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println(">>>" + cmd);
		}
		// Send command:
		out.print(cmd);
		// ...and, line terminator.
		out.print("\r\n");
		// Send it now!
		out.flush();
		// Get the response.
		String response = checkResponse();
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("<<<+OK " + response);
		}
		return response;
	}

	// Wait for a response and make sure it is an "OK" response.
	public static String checkResponse() throws IOException {
		String response;
		for (;;) {
			response = in.readLine();
			if (response == null) {
				throw new IOException("Server unexpectedly closed connection");
			} else if (response.startsWith("-ERR")) {
				throw new IOException("Error from server: " + response);
			} else if (response.startsWith("+OK")) {
				return response.substring(3);
			}
		}
	}

	/*
	 * Ask the server to send the headers of the numbered message. Look through them
	 * for the Subject header and return its content.
	 */
	public static String getSubject(int msgnum) throws IOException {
		send("TOP " + msgnum + " 0");
		String subject = null, line;
		for (;;) {
			line = in.readLine();
			if (line == null) {
				throw new IOException("Unexpected EOF");
			}
			if (line.startsWith("Subject: ")) {
				subject = line.substring(9);
			}
			if (line.equals(".")) {
				break;
			}
		}
		return subject;
	}

	/*
	 * Disconnect nicely from the POP server. This method is called for normal
	 * termination and exceptions.
	 */
	public static void shutdown() {
		try {
			if (out != null) {
				send("QUIT");
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (s != null) {
				s.close();
			}
		} catch (IOException e) {
			// ignore
		}
	}
}
