package Clases;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ZonaInsegura {
    Lock c = new ReentrantLock();



    public ZonaInsegura(){

    }




    public Humano getVictima(){
        c.lock();
        try {

        }catch (Exception e){System.out.println(e);}
        finally {c.unlock();}

    }

    public void transformacion(Humano h){
        
    }


}
