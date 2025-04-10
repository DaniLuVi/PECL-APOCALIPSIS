package Clases;

import javafx.scene.control.TableColumn;

public class Zombie extends Thread{

    private int nombre;
    private ZonaInsegura zona;
    private int muertes = 0;

    public Zombie(int nombre, ZonaInsegura zona) {
        super("Z" + nombre);
        this.zona = zona;
        this.start();
    }

    public void run() {
        try {
            while(true) {
                Humano elegido = zona.getVictima();
                if (elegido != null) {
                    int elegido = (int) (Math.random() * zona.getNumHumanos());
                    morder(zona.humanos[elegido]);
                    Thread.sleep((int) (Math.random() * 2000) + 1000);
                }
                int numero_zona = (int) (Math.random() * 4);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void morder(Humano h){
        try{
            h.interrupt();
            Thread.sleep((int)(500 + Math.random()*1000));
            h.morder(Math.random()*3>2 ); // Un tercio de las veces devuelve true;

        } catch (InterruptedException e) {throw new RuntimeException(e);}
    }


    public static void main(String[] args ){
        Tunel t = new Tunel();
        Tunel[] ts = new Tunel[1];
        ts[0] = t;
        Refugio r = new Refugio(20,ts ) ;
        Humano h = new Humano(1, r);
        Zombie z = new Zombie(0, null);

        h.start();
        for (int i = 0; i < 15; i++) {
            try {
            z.morder(h);

                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }




}
