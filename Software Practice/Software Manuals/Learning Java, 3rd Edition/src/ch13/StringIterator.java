package ch13;

import java.rmi.*;

public interface StringIterator extends Remote {
    public boolean hasNext(  ) throws RemoteException;
    public String next(  ) throws RemoteException;
}
