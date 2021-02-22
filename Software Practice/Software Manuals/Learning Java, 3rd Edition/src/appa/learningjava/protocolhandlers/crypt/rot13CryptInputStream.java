package appa.learningjava.protocolhandlers.crypt;

import java.io.*;

abstract class CryptInputStream extends InputStream {
    InputStream in;
    OutputStream out;
    abstract public void set( InputStream in, OutputStream out );
}

class rot13CryptInputStream extends CryptInputStream {

    public void set( InputStream in, OutputStream out ) {
        this.in = in;
        this.out = out;
    }
    public int read(  ) throws IOException {
        return in.read(  );
    }
}
