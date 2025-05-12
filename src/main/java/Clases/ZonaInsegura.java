package Clases;

import javafx.scene.control.TextArea;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaInsegura extends UnicastRemoteObject implements RemotaZonaInsegura {
    private Lock c = new ReentrantLock();
    private Lock c2 = new ReentrantLock();

    private ListaThreads zona;
    private LinkedList<Humano> humanos = new LinkedList<>();

    private static Logger log = new Logger("apocalipsis.txt");

    public ZonaInsegura(TextArea txt, Paso p) throws RemoteException{
        zona = new ListaThreads(txt, p);
    }


    public void entrar(Humano h, boolean sale){
        c2.lock();
        try {
            if (!sale){
                humanos.add(h);
                zona.meter(h);
                log.escribir("El humano " + h.getName() + " entra en la zona insegura");
            } else {
                humanos.remove(h);
                zona.sacar(h);
                log.escribir("El humano " + h.getName() + " sale de la zona insegura");
            }
        } finally {
            c2.unlock();
        }
    }
    public synchronized void entrar(Zombie h, boolean sale){
        try {
            if (sale){
                zona.sacar(h);
                log.escribir("El zombie " + h.getName() + " sale de la zona insegura");

            } else {
                zona.meter(h);
                log.escribir("El zombie " + h.getName() + " entra en la zona insegura");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Humano getVictima(){
        try {
            if (humanos.isEmpty()){return null;}
            Humano h = humanos.remove((int) (Math.random() * humanos.size()));
            h.interrupt();
            return h;
        }catch (Exception e){System.out.println(e);}
        return null;
    }

    public synchronized Humano obtenerAleatorio() {
        if (humanos.isEmpty()) {
            return null; // Retorna null si la lista está vacía
        }
        int indice = (int)(Math.random()*humanos.size());
        return humanos.get(indice);
    }   // ¿ este metodo lo vamos a utilizar en algo?

    public int sumarMuerte(String nombre, int muerte) {
        c.lock();
        try {
            muerte++;
            log.escribir("Se ha añadido una muerte al zombie " + nombre);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            c.unlock();
        }
        return muerte;
    }

    public int getHumanosEnZonaInsegura() throws RemoteException{
        return humanos.size();
    }

    public int getZombiesEnZonaInsegura() throws RemoteException {
        return (zona.getLista().size() - getHumanosEnZonaInsegura());
    }
}
