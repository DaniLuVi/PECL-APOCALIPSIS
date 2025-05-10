package Clases;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotaZonaInsegura extends Remote {

    int getHumanosEnZonaInsegura() throws RemoteException;

    int getZombiesEnZonaInsegura() throws RemoteException;
}
