package Clases;

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

    private List<Humano> humanos;

    public Refugio(int comida, Tunel[] tuneles) {

        for (int i = 0; i < 4; i++) {
            accesoTunel[i] = new CyclicBarrier(3);
            this.tuneles = tuneles;
        }
        this.comida = new Semaphore(comida, true);
    }

    public void entrarTunel(int n, Humano h) {

        try {
            accesoTunel[n].await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        // Se han alcanado los 3 supervivientes esperando, se desplazan para entrar al tunel
        //h.move()
        tuneles[n].paso(h,true);

    }
    public void entrarTunel(int n, Humano h, boolean entra) {

        try {
            accesoTunel[n].await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        // Se han alcanado los 3 supervivientes esperando, se desplazan para entrar al tunel
        //h.move()
        System.out.println("Pasa al tunel" + n + " "+ h.getName());
        tuneles[n].paso(h,entra);

    }

    public void entrarZona(Humano humano, boolean entra) {
        cerrojo.lock();
        try {
            if (entra) {
                humanos.add(humano);
                System.out.println(humano.getName() + " ha entrado a la zona común.");
            } else {
                humanos.remove(humano);
                System.out.println(humano.getName() + " ha salido de la zona común.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            cerrojo.unlock();
        }
    }

    public void setComida(int n){
        comida.release(n);
    }

    public void comer(int n){
        try {
            comida.acquire(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args){
        Tunel tuneles[] = new Tunel[4];
        Humano humanos[] = new Humano[30];

        for (int i = 0; i < 4; i++) {
            tuneles[i] = new Tunel();
            tuneles[i].nTunel = i+1;
        }

        Refugio r = new Refugio(20,tuneles);
        for (int i = 0; i < 30 ; i++) {
            humanos[i] = new Humano(i+1,r);
            humanos[i].start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }



}