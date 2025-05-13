package Clases;

import javafx.scene.control.TextArea;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.trabajofinal.ControladorPantallaJuego.remoto;

public class ZonaInsegura{
    private Lock c = new ReentrantLock();
    private Lock c2 = new ReentrantLock();

    private ListaThreads zona;
    private LinkedList<Humano> humanos = new LinkedList<>();
    private int nZona;

    private static Logger log = new Logger("apocalipsis.txt");

    public ZonaInsegura(TextArea txt, Paso p,int i) throws RemoteException{
        zona = new ListaThreads(txt, p);
        this.nZona = i;
    }

    public void entrar(Humano h, boolean sale){
        c2.lock();
        try {
            if (!sale){
                humanos.add(h);
                zona.meter(h);
                remoto.setHumanosPorZona(nZona, humanos.size());
                log.escribir("El humano " + h.getName() + " entra en la zona insegura");
            } else {
                humanos.remove(h);
                zona.sacar(h);
                remoto.setHumanosPorZona(nZona, humanos.size());
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
                remoto.setZombiesPorZona(nZona, getZombiesEnZonaInsegura());
                log.escribir("El zombie " + h.getName() + " sale de la zona insegura");

            } else {
                zona.meter(h);
                remoto.setZombiesPorZona(nZona, getZombiesEnZonaInsegura());
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

    public int getHumanosEnZonaInsegura(){
        return humanos.size();
    }

    public int getZombiesEnZonaInsegura(){
        return (zona.getLista().size() - humanos.size());
    }


}
