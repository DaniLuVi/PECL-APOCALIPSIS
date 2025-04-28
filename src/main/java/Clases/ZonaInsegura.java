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

    private static Logger log = new Logger("apocalipsis.txt");

    public ZonaInsegura(TextArea txt){
        zona = new ListaThreads(txt);
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
        //c2.lock();
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

      //  c2.unlock();
    }

    public synchronized Humano getVictima(){
        //c.lock();
        try {
            if (humanos.isEmpty()){return null;}
            Humano h = humanos.remove((int) (Math.random() * humanos.size()));
            h.interrupt();
            return h;
        }catch (Exception e){System.out.println(e);}
        //finally {c.unlock();}
        return null;
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
