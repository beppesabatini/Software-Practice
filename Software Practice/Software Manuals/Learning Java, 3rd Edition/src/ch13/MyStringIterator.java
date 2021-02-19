package ch13;

import java.rmi.*;

// From p. 471.

public class MyStringIterator
  extends java.rmi.server.UnicastRemoteObject
  implements StringIterator {

	private static final long serialVersionUID = 2883120231119494734L;

	String [] list;
    int index = 0;

    public MyStringIterator( String [] list )
      throws RemoteException {
        this.list = list;
    }
    public boolean hasNext(  ) throws RemoteException {
        return index < list.length;
    }
    public String next(  ) throws RemoteException {
        return list[index++];
    }
}
