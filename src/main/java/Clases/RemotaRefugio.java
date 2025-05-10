package Clases;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotaRefugio extends Remote {

    int getHumanosEnRefugio() throws RemoteException;
}
