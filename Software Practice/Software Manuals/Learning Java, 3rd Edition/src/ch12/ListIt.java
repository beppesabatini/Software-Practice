package ch12;

import java.io.*;

/**
 * From Learning Java, 3rd Edition, p. 410. Opens a file and prints it to
 * the console.
 */
class ListIt {
	public static void main(String args[]) throws Exception {
		if (args.length <= 0) {
			System.out.println("Usage: ListIt <filename>");
			return;
		}
		File file = new File(args[0]);

		if (!file.exists() || !file.canRead()) {
			System.out.println("Can't read " + file);
			return;
		}

		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; i++)
				System.out.println(files[i]);
		} else
			try {
				FileReader fr = new FileReader(file);
				BufferedReader in = new BufferedReader(fr);
				String line;
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("File Disappeared");
			}
	}
}
