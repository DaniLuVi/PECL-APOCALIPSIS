package Clases;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface RemotaZonaInsegura extends Remote {
    Lock c = new ReentrantLock();


    int getHumanosEnZonaInsegura() throws RemoteException;

    int getZombiesEnZonaInsegura() throws RemoteException;







}
