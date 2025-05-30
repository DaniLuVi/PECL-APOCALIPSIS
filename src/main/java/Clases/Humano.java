package Clases;

import java.util.concurrent.Exchanger;

public class Humano extends Thread{

    private int id;
    private Refugio refugio;
    private Exchanger<Boolean> exchanger = new Exchanger<>();

    private static Logger log;

    public Humano(int nombre, Refugio refugio) {
        super("H" + String.format("%04d", nombre));
        this.id = nombre;
        this.refugio = refugio;
        //this.start();
        log = new Logger("apocalipsis.txt");
    }


//Voy a ir factorizando
    private int buscar(int n){

        if (n == -1){n = (int)( Math.random() * 4); }       // lo he cambiado de 4 a 3 pq es entre 0 y 3: 0,1,2,3 (4 tuneles)
        //Efectivamente por eso es un 4.
        System.out.println(this.getName()+"Elige tunel " +n);
        log.escribir(this.getName() + " Elige tunel " + n);
        refugio.tuneles[n].entrarTunel(n, this, false);
        try{Thread.sleep(3000 + (int) (2000* Math.random()));
            refugio.tuneles[n].paso(this,true);
           if( this.isInterrupted()){
               exchanger.exchange(false);
               System.out.println("Me han llamado fuera de tiempo " + this.getName());
               log.escribir("Me han llamado fuera de tiempo " + this.getName());
           }
           refugio.setComida(2);
           return 1; // Tod0 ha ido bien
        } catch (InterruptedException e){
            try {
                boolean resultado = exchanger.exchange(true);
                //boolean ataque = false;
                if (resultado == true){
                    System.out.println("He muerto.");
                    log.escribir(this.getName() + ": He muerto.");
                    return 0;
                }else{
                    System.out.println("Sobrevivo al ataque.");
                    log.escribir(this.getName() + ": Sobrevivo al ataque.");
                    return 2;
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Zombie crearZombie(ZonaInsegura[] zonas, int n){
        System.out.println("Nuevo Zombie cond ID: " + this.id + " " + id );
        log.escribir("Nuevo Zombie cond ID: " + this.id + " " + id );
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
            log.escribir("El humano " + this.getName() + " entra a la zona común.");
            try {
                Thread.sleep((int)(Math.random() * 1000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }       // esta es la entrada a la zona común antes de elegir el túnel

            refugio.entrarZona(this, false);
            log.escribir("El humano " + this.getName() + " sale de la zona común.");
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

            //refugio.tuneles[n].entrarTunel(n, this, true);
            refugio.tuneles[n].paso(this,false);    // entra del tunel al refugio
            // se va a la zona de descanso
            refugio.descansa(this, false);
            refugio.comer(this, 1);
            if (n == 2){refugio.descansa(this, true);}
            try {
                Thread.sleep(3000 + (int) Math.random() * 2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }   // se come una pieza de comida


        }
    }   // hay que ver que el comportamiento de los humanos lo hace adecuadamente
    public int getid() {
        System.out.println("Mi id es: " + this.id);
        return this.id;
    }
}
