package Pruebas;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunel {
    private Lock c = new ReentrantLock();

    private Condition esperaFuera = c.newCondition();
    private Condition esperaDentro = c.newCondition();
    public LinkedList<Humano> dentro = new LinkedList<Humano>(); //Si se puede ver cuantos hay en espera en cada condition podria valer con ellos
    public LinkedList<Humano> fuera = new LinkedList<Humano>();


    public void paso(Humano h, boolean entra ){
        try{
            encolar(h, entra);
            c.lock();
            if(!entra){while (!fuera.isEmpty()) {esperaDentro.await();}} // Si no entra espera hasta que todos los de fuera hayan pasado.
            else { } //Si entra
            //System.out.println("Comienza a entrar"+ entra + h.getName());
            desencolar(h,entra);
            Thread.sleep(2000); // Simula el estar pasando el tunel


            //System.out.println("Termina de entrar"+ entra + h.getName());
            if (fuera.isEmpty()) {
                dentro.notify();}

        }
        catch(Exception e){//System.out.println();
        }
        finally  { c.unlock();}



    }
    public synchronized void encolar(Humano h, boolean entra){
        if (entra) {fuera.add(h);// System.out.println("Encolado fuera"+ h.getName());
             }
        else {dentro.add(h);//System.out.println("Encolado dentro"+ h.getName());
                 } // Para mostrarlo en pantalla
    }
    public synchronized void desencolar(Humano h, boolean entra){
        if (entra) {
            fuera.remove(h);
            //System.out.println("Desencolado fuera"+ h.getName());
            //Codigo que lo mueva al refugio puede ser aqui o en el propio humano
        }
        else {dentro.remove(h); //System.out.println("Desencolado dentro "+ h.getName());
        }

    }






    public static void main(String[] args){

        Tunel t1 = new Tunel();
        Humano humanos[] = new Humano[20];
        for (int i = 0; i < 20; i++) {
            humanos[i] = new Humano("Humano" + i, t1);
            humanos[i].start();
        }


        while(true) {
            System.out.println("Dentro " + t1.dentro);
            System.out.println("Fuera "  + t1.fuera + "\n \n\n");

            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }






}






