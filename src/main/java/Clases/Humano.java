package Clases;

import java.util.concurrent.Exchanger;

public class Humano extends Thread{

    private int id;
    private Refugio refugio;
    private Exchanger<Boolean> exchanger = new Exchanger<>();

    public Humano(int nombre, Refugio refugio) {
        super("H" +nombre);
        this.id = nombre;
        this.refugio = refugio;
    }


//Voy a ir factorizando
    private int buscar(int n){

        if (n == -1){n = (int)( Math.random()*4); }
        System.out.println(this.getName()+"Elige tunel " +n);
        refugio.entrarTunel(n, this, false);
        try{Thread.sleep(3000 + (int) (2000* Math.random()));
           refugio.tuneles[n].paso(this,true);
           if( this.isInterrupted()){
               exchanger.exchange(false);
           }
           refugio.setComida(2);
           return 1; // Tod0 ha ido bien
        } catch (InterruptedException e){
            try {


                boolean resultado = exchanger.exchange(true);
                //boolean ataque = false;
                if (resultado == true){
                    System.out.println("He muerto.");
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

    public Zombie crearZombie(ZonaInsegura[] zonas, int n){
        System.out.println("Nuevo Zombie cond ID: " + this.id+ " "+id );
        return new Zombie(this.id, zonas, n);


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
        int tmp;
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
            n = buscar(-1);
            if(n==0){break;}// Si n es 2 es que ha sido atacado y ha sobrevivido.
            // Y si es 1 es que no ha habido ataque
            /*refugio.entrarTunel(n, this, false);

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
            refugio.descansa(this, false);
            refugio.comer(this, 1);
            if (n == 2){refugio.descansa(this, true);}
            try {
                Thread.sleep(3000 + (int) Math.random() * 2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }   // se come una pieza de comida


        }
    }

    public int getid() {
        System.out.println("Mi id es: " + this.id);
        return this.id;
    }
}
