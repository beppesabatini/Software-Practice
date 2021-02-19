package appa.learningjava.contenthandlers.application;

import java.net.*;
import java.io.*;
import java.util.Date;

public class x_tar extends ContentHandler {
	
	// No main function in this file.
	
	static int RECORDLEN = 512, NAMEOFF = 0, NAMELEN = 100, SIZEOFF = 124, SIZELEN = 12, MTIMEOFF = 136, MTIMELEN = 12;

	public Object getContent(URLConnection uc) throws IOException {
		InputStream is = uc.getInputStream();
		StringBuffer output = new StringBuffer("Tape Archive Listing:\n\n");
		byte[] header = new byte[RECORDLEN];
		int count = 0;

		while ((is.read(header) == RECORDLEN) && (header[NAMEOFF] != 0)) {
			String name = new String(header, NAMEOFF, NAMELEN, "8859_1").trim();
			String s = new String(header, SIZEOFF, SIZELEN, "8859_1").trim();
			int size = Integer.parseInt(s, 8);
			s = new String(header, MTIMEOFF, MTIMELEN, "8859_1").trim();
			long l = Integer.parseInt(s, 8);
			Date mtime = new Date(l * 1000);

			output.append(size + " " + mtime + " " + name + "\n");

			count += is.skip(size) + RECORDLEN;
			if (count % RECORDLEN != 0)
				count += is.skip(RECORDLEN - count % RECORDLEN);
		}

		if (count == 0)
			output.append("Not a valid TAR file\n");

		return (output.toString());
	}
}
