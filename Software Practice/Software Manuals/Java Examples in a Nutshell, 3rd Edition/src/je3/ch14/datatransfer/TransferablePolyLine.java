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
package je3.ch14.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import je3.ch12.graphics.PolyLine;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 447. This class implements
 * the Transferable interface for PolyLine objects. It also defines a DataFlavor
 * used to describe this data type.
 */
public class TransferablePolyLine implements Transferable {
	public static DataFlavor FLAVOR = new DataFlavor(PolyLine.class, "PolyLine");
	static DataFlavor[] FLAVORS = new DataFlavor[] { FLAVOR };

	// This is the PolyLine we wrap:
	PolyLine polyLine;

	public TransferablePolyLine(PolyLine polyLine) {
		this.polyLine = polyLine;
	}

	/** Return the supported flavor. */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return FLAVORS;
	}

	/** Check for the one flavor we support. */
	@Override
	public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
		return dataFlavor.equals(FLAVOR);
	}

	/** Return the wrapped PolyLine, if the flavor is right. */
	@Override
	public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException {
		if (!dataFlavor.equals(FLAVOR)) {
			throw new UnsupportedFlavorException(dataFlavor);
		}
		return polyLine;
	}
}
