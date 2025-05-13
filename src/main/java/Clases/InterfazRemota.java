package Clases;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;

public interface InterfazRemota extends Remote {

    public int mostrarPodio() throws RemoteException;


    public void checkPodio(Zombie z, int muertes) throws RemoteException;

    public void setZombiesPorZona(int zona, int zombies) throws RemoteException;

    public void setHumanosPorZona(int zona, int humanos) throws RemoteException;

    public void setHumanosRefugio(int humanosRefugio) throws RemoteException;

    public Zombie[] getPodio() throws RemoteException;

    public int[] getZombiesPorZona() throws RemoteException;

    public int[] getHumanosPorZona() throws RemoteException;

    public int getHumanosRefugio() throws RemoteException;

    public String getInfo()  throws RemoteException;



}