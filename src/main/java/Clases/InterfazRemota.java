package Clases;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;

public interface InterfazRemota extends Remote {



    public void checkPodio(Zombie z, int muertes) throws RemoteException;

    public void setZombiesPorZona(int zona, int zombies) throws RemoteException;

    public void setHumanosPorZona(int zona, int humanos) throws RemoteException;

    public void setHumanosRefugio(int humanosRefugio) throws RemoteException;

    public void setHumanosEnTuneles(int tunel, int humanosEnTuneles) throws RemoteException;

    public String[] getPodio() throws RemoteException;

    public String[] getZombiesPorZona() throws RemoteException;

    public String[] getHumanosPorZona() throws RemoteException;

    public int getHumanosRefugio() throws RemoteException;

    public String[] getHumanosEnTuneles() throws RemoteException;

    public String getInfo()  throws RemoteException;

    public boolean pausa() throws RemoteException;


}