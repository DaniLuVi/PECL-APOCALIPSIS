package Clases;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotaTunel extends Remote {

    int getHumanosEnTuneles() throws RemoteException;
}
