package Clases;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import static com.example.trabajofinal.ControladorPantallaJuego.remoto;

public class Refugio{

    private Lock cerrojo = new ReentrantLock();
    private Semaphore comida; // El true hace el semafoto Fair y si no queda comida hacen cola por orden de llegada.
    private Tunel tuneles[] = new Tunel[4];
    private ListaThreads zona;
    private ListaThreads comedor;
    private ListaThreads camas;
    private Label contadorComida;
    private static Logger log = new Logger("apocalipsis.txt");

    public Refugio(int comida, Tunel[] tuneles, TextArea zona, TextArea comedor, Label contadorComida, TextArea camas, Paso p) throws RemoteException{
        this.zona = new ListaThreads(zona,p);
        this.comedor = new ListaThreads(comedor,p);
        this.camas = new ListaThreads(camas,p );

        this.comida = new Semaphore(comida, true); // El que sea fair es para que si no hay comida esperen de forma ordenada

        this.contadorComida = contadorComida;
        Platform.runLater(() ->
                contadorComida.setText(String.valueOf(comida)));

        for (int i = 0; i < 4; i++) {
            this.tuneles = tuneles;
        }
    }

    public void entrarZona(Humano humano, boolean entra) {
        cerrojo.lock();
        try {
            if (entra) {
                zona.meter(humano);
                actualizarRemoto();
                System.out.println(humano.getName() + " ha entrado a la zona común.");
            } else {
                zona.sacar(humano);
                actualizarRemoto();
                System.out.println(humano.getName() + " ha salido de la zona común.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrojo.unlock();
        }
    }

    public synchronized void setComida(int n){

        comida.release(n);
        log.escribir("Se añaden 2 piezas de comida a disposición del refugio");
        Platform.runLater(() ->
                contadorComida.setText(String.valueOf(comida.availablePermits()))
        );
    }

    public void comer(Humano h, int n){

        try {
            comedor.meter(h);
            actualizarRemoto();
            comida.acquire(n);
            log.escribir(h.getName() + " se come una pieza de comida.");
            System.out.println("--------- COMIDA: " + comida.availablePermits());

            Platform.runLater(() ->
                    contadorComida.setText(String.valueOf(comida.availablePermits()))
            );
            Thread.sleep(3000+ (int)(Math.random()*2000));
            comedor.sacar(h);
            actualizarRemoto();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void descansa(Humano h, boolean largo){ // El booleano de largo es para cuando le ataquen que tiene que dormir más tiempo
        camas.meter(h);
        actualizarRemoto();
        try {
            log.escribir(h.getName() + " descansa.");
            Thread.sleep((largo? 3000: 2000) +(int)(Math.random()* 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        camas.sacar(h);
        actualizarRemoto();
    }

    public Semaphore getComida() {
        return comida;
    }

    public Tunel getTuneles(int n) {
        return tuneles[n];
    }

    public int getHumanosEnRefugio(){
        return zona.getLista().size() + comedor.getLista().size() + camas.getLista().size();
    }

    private void actualizarRemoto(){
    // Lo he puesto en todos lados pero realmente con ponerlo al entrar y salir de la zona común valdría por que solo pueden entrar y salir desde ahí, el resto de movimientos son internos al refugio.
        remoto.setHumanosRefugio(getHumanosEnRefugio());
    }
}