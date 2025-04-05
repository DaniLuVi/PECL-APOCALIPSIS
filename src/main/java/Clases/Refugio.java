package Clases;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Refugio {

    private Lock cerrojo = new ReentrantLock();
    CyclicBarrier accesoTunel[] = new CyclicBarrier[4];
    Semaphore comida; // El true hace el semafoto Fair y si no queda comida hacen cola por orden de llegada.
    Tunel tuneles[] = new Tunel[4];
    private int num_humanos = 0;

    public Refugio(int comida, Tunel[] tuneles) {

        for (int i = 0; i < 4; i++) {
            accesoTunel[i] = new CyclicBarrier(3 );
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
    }

    public void entrarZona(String nombre, boolean entra) {
        cerrojo.lock();
        try {
            if (entra) {
                num_humanos++;
                System.out.println(nombre + " ha entrado a la zona común.");
            } else {
                num_humanos--;
                System.out.println(nombre + " ha salido de la zona común.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            cerrojo.unlock();
        }
    }
}
