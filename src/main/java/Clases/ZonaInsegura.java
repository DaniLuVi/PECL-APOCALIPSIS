package Clases;

import javafx.scene.control.TextArea;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaInsegura {
    Lock c = new ReentrantLock();
    Lock c2 = new ReentrantLock();

    ListaThreads zona;
    LinkedList<Humano> humanos = new LinkedList<>();


    public ZonaInsegura(TextArea txt){
        zona = new ListaThreads(txt);
    }


    public void entrar(Humano h, boolean entra){
        c2.lock();
        if (!entra){
            humanos.add(h);
            zona.meter(h);
        } else {
            humanos.remove(h);
            zona.sacar(h);
        }

        c2.unlock();
    }

    public Humano getVictima(){
        c.lock();
        try {

        }catch (Exception e){System.out.println(e);}
        finally {c.unlock();}
        return null;
    }

    public void transformacion(Humano h){
        
    }


    public synchronized Humano obtenerAleatorio() {
        if (humanos.isEmpty()) {
            return null; // Retorna null si la lista está vacía
        }
        int indice = (int)(Math.random()*humanos.size());
        return humanos.get(indice);
    }




















    /*public synchronized void encolar(Humano h, boolean entra){
        if (entra) {fuera.add(h); System.out.println("Encolado fuera"+ h.getName());}
        else {dentro.add(h);System.out.println("Encolado dentro"+ h.getName());} // Para mostrarlo en pantalla
    }
    public synchronized void desencolar(Humano h, boolean entra){
        if (entra) {
            fuera.remove(h);
            System.out.println("Desencolado fuera"+ h.getName());
            //Codigo que lo mueva al refugio puede ser aqui o en el propio humano
        }
        else {dentro.remove(h); System.out.println("Desencolado dentro "+ h.getName());}

    }
*/






}
