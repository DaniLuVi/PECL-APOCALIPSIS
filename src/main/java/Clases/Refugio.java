package Clases;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Refugio {

    private Lock cerrojo = new ReentrantLock();

    private CyclicBarrier accesoTunel[] = new CyclicBarrier[4];
    private Semaphore comida; // El true hace el semafoto Fair y si no queda comida hacen cola por orden de llegada.
    Tunel tuneles[] = new Tunel[4];
    ListaThreads zona;
    ListaThreads comedor;
    ListaThreads camas;
    Label contadorComida;

    private static Logger log = new Logger("apocalipsis.txt");

    private ListaThreads humanos;

    public Refugio(int comida, Tunel[] tuneles, TextArea zona, TextArea comedor, Label contadorComida, TextArea camas) {
        this.zona = new ListaThreads(zona);
        this.comedor = new ListaThreads(comedor);
        this.camas = new ListaThreads(camas);
        this.comida = new Semaphore(comida, true); // El que sea fair es para que si no hay comida esperen de forma ordenada

        this.contadorComida = contadorComida;
        Platform.runLater(() ->
                contadorComida.setText(String.valueOf(comida)));

        for (int i = 0; i < 4; i++) {
            accesoTunel[i] = new CyclicBarrier(3);
            this.tuneles = tuneles;
        }
    }

    public void entrarTunel(int n, Humano h, boolean entra) {

        try {
            accesoTunel[n].await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        // Se han alcanado los 3 supervivientes esperando, se desplazan para entrar al tunel
        log.escribir("Se han alcanzado 3 humanos esperando para entrar al túnel.");
        System.out.println("Pasa al tunel" + n + " "+ h.getName());
        tuneles[n].paso(h,entra);
    }

    public void entrarZona(Humano humano, boolean entra) {
        cerrojo.lock();
        try {
            if (entra) {
                zona.meter(humano);
                System.out.println(humano.getName() + " ha entrado a la zona común.");
            } else {
                zona.sacar(humano);
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
            comida.acquire(n);
            log.escribir(h.getName() + " se come una pieza de comida.");
            System.out.println("--------- COMIDA: " + comida.availablePermits());

            Platform.runLater(() ->
                    contadorComida.setText(String.valueOf(comida.availablePermits()))
            );
            Thread.sleep(3000+ (int)(Math.random()*2000));
            comedor.sacar(h);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            //System.out.println(comida.availablePermits());

    }


    public void descansa(Humano h, boolean largo){ // El booleano de largo es para cuando le ataquen que tiene que dormir más tiempo
        camas.meter(h);
        try {
            log.escribir(h.getName() + "descansa.");
            Thread.sleep((largo? 3000: 2000) +(int)(Math.random()* 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        camas.sacar(h);
    }






}