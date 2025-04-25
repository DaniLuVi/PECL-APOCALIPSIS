package Clases;

import javafx.scene.control.TextArea;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunel {
    private Lock c = new ReentrantLock();
    public  int nTunel;
    private Condition esperaDentro = c.newCondition();
    private ListaThreads dentro; //Si se puede ver cuantos hay en espera en cada condition podria valer con ellos
    private ListaThreads fuera;
    private TextArea pasando;
    private ZonaInsegura zona;

    public Tunel(TextArea izq, TextArea drc, TextArea in, ZonaInsegura zona){
        fuera = new ListaThreads(drc);
        dentro = new ListaThreads(izq);
        pasando = in;
        this.zona = zona;
    }

    public void paso(Humano h, boolean entra){
        try{
            if(entra)zona.entrar(h, true);
            encolar(h, entra);
            c.lock();
            if(!entra){while (!fuera.isEmpty()) {esperaDentro.await();}} // Si no entra espera hasta que todos los de fuera hayan pasado.
            //Si entra
            System.out.println("Comienza a entrar en "+ nTunel+ " " + entra +" "+ h.getName());
            desencolar(h,entra);
            pasando.setText(h.getName());
            Thread.sleep(1000); // Simula el estar pasando el tunel

            System.out.println("Sale del tunel "+ nTunel+ " " + entra +" "+ h.getName());
            pasando.setText(null);
            if (!entra){zona.entrar(h,false);}  // revisar pq creo que no están saliendo correctamete de la zona insegura (puede ser que los booleanos sean al revés, pero tengo que verlo un poco mas)
            if (fuera.isEmpty()) {esperaDentro.signalAll();}

        }
        catch(Exception e){System.out.println(e);}
        finally  { c.unlock();}

    }
    public synchronized void encolar(Humano h, boolean entra){
        if (entra) {fuera.meter(h); System.out.println("Encolado fuera "+ h.getName());}
        else {dentro.meter(h);System.out.println("Encolado dentro "+ h.getName());} // Para mostrarlo en pantalla
    }
    public synchronized void desencolar(Humano h, boolean entra){
        if (entra) {
            fuera.sacar(h);
            System.out.println("Desencolado fuera"+ h.getName());
            //Codigo que lo mueva al refugio puede ser aqui o en el propio humano
        }
        else {dentro.sacar(h); System.out.println("Desencolado dentro "+ h.getName());}

    }
}






