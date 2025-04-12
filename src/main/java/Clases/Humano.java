package Clases;

import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Humano extends Thread{

    private int id;
    private Refugio refugio;
    private Exchanger<Boolean> exchanger = new Exchanger<>();

    public Humano(int nombre, Refugio refugio) {
        super("H" +nombre);
        this.refugio = refugio;
    }


//Voy a ir factorizando
    private int buscar(int n){

        if (n == -1){n = (int)( Math.random()*4); }
        System.out.println(this.getName()+"Elige tunel " +n);
        refugio.entrarTunel(n, this, false);
        try{Thread.sleep(3000 + (int) (2000* Math.random()));
           refugio.tuneles[n].paso(this,true);
           refugio.setComida(2);
           return 1; // Tod0 ha ido bien
        } catch (InterruptedException e){
            try {


                boolean resultado = exchanger.exchange(true);
                //boolean ataque = false;
                if (resultado == true){
                    System.out.println("He muerto.");
                    crearZombie();
                    return 0;
                }else{
                    System.out.println("Sobrevivo al ataque.");
                    return 2;
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void crearZombie(){



    }
    public boolean morder(boolean b){
        try {
            return exchanger.exchange(b);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public void run() {
        int n;
        boolean ataque;
//        while (true){
//            this.buscar(-1);
//            if (1 +2 == 39) break;
//        }
        while(true) {
        /*
            n = (int)(Math.random()*4);
            System.out.println(this.getName()+"Elige tunel " +n);
            refugio.entrarTunel(n, this, false);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            refugio.entrarTunel(n, this,true);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
           */

            refugio.entrarZona(this, true);
            try {
                Thread.sleep((int)Math.random() * 1000 + 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }       // esta es la entrada a la zona común antes de elegir el túnel
            refugio.entrarZona(this, false);
            n = (int)(Math.random()*4);
            System.out.println(this.getName()+"Elige tunel " +n);
            buscar(1);
            /*refugio.entrarZona(this, false);    // sale de la zona común
            refugio.entrarTunel(n, this, false);

            try{Thread.sleep(3000 + (int) (2000* Math.random()));
                refugio.tuneles[n].paso(this,false);
                refugio.setComida(2);
                boolean atacado = false;
            }
            catch (InterruptedException e){
                boolean atacado= true;
                try {
                    Thread.sleep(1000000000);
                  //  ataque = exchanger.exchange(true);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } // Ha sido atacado.*/

            refugio.comer(1);
            try {
                Thread.sleep(3000 + (int) Math.random() * 2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }   // se come una pieza de comida


        }
    }
























}
