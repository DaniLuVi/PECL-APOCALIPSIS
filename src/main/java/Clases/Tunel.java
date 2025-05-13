package Clases;

import javafx.scene.control.TextArea;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.trabajofinal.ControladorPantallaJuego.remoto;

public class Tunel{
    private Lock c = new ReentrantLock();
    private int nTunel;
    private Condition esperaDentro = c.newCondition();
    private ListaThreads dentro; //Si se puede ver cuantos hay en espera en cada condition podria valer con ellos
    private ListaThreads fuera;
    private TextArea pasando;
    private ZonaInsegura zona;
    private Paso p;
    private CyclicBarrier accesoTunel = new CyclicBarrier(3);
    private ListaThreads esperandoTunel;

    private static Logger log = new Logger("apocalipsis.txt");

    public Tunel(TextArea izq, TextArea drc, TextArea in, TextArea esperandoTunel, ZonaInsegura zona, Paso p) throws RemoteException{
        fuera = new ListaThreads(drc, p);
        dentro = new ListaThreads(izq,p );
        pasando = in;
        this.zona = zona;
        this.esperandoTunel = new ListaThreads(esperandoTunel,p);
        this.p = p;
    }

    public void entrarTunel(int n, Humano h, boolean entra) {

        try {
            esperandoTunel.meter(h);
            actualizar();
            accesoTunel.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        // Se han alcanado los 3 supervivientes esperando, se desplazan para entrar al tunel
        log.escribir("Se han alcanzado 3 humanos esperando para entrar al túnel.");
        System.out.println("Pasa al tunel" + n + " "+ h.getName());
        this.paso(h,entra);
    }

    public void paso(Humano h, boolean entra){
        try{
            if(entra)zona.entrar(h, true);
            encolar(h, entra);
            c.lock();
            if(!entra){while (!fuera.isEmpty()) {esperaDentro.await();}} // Si no entra espera hasta que todos los de fuera hayan pasado.

            esperandoTunel.sacar(h);
            actualizar();
            System.out.println("Comienza a entrar en "+ nTunel+ " " + entra +" "+ h.getName());
            desencolar(h,entra);
            p.mirar();
            pasando.setText(h.getName());
            actualizar();
            Thread.sleep(1000); // Simula el estar pasando el tunel

            System.out.println("Sale del tunel "+ nTunel+ " " + entra +" "+ h.getName());
            p.mirar();
            pasando.setText(null);
            actualizar();
            if (!entra){zona.entrar(h,false);}
            if (fuera.isEmpty()) {esperaDentro.signalAll();}

        }
        catch(Exception e){System.out.println(e);}
        finally  { c.unlock();}

    }
    public synchronized void encolar(Humano h, boolean entra) throws RemoteException{
        if (entra) {fuera.meter(h); actualizar(); System.out.println("Encolado fuera "+ h.getName());}
        else {dentro.meter(h); actualizar(); System.out.println("Encolado dentro "+ h.getName());
        } // Para mostrarlo en pantalla
    }
    public synchronized void desencolar(Humano h, boolean entra){
        if (entra) {
            fuera.sacar(h);
            actualizar();
            System.out.println("Desencolado fuera"+ h.getName());
            //Codigo que lo mueva al refugio puede ser aqui o en el propio humano
        }
        else {dentro.sacar(h); actualizar(); System.out.println("Desencolado dentro "+ h.getName());}

    }

    public int getnTunel() {
        return nTunel;
    }

    public void setnTunel(int nTunel) {
        this.nTunel = nTunel;
    }

    public int getHumanosEnTuneles(){
        return esperandoTunel.getLista().size() + dentro.getLista().size() + fuera.getLista().size() + pasando.getLength();
    }

    private void actualizar() {
        remoto.setHumanosEnTuneles(nTunel, getHumanosEnTuneles());
    }
}






