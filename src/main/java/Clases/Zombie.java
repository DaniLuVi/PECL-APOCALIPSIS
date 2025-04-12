package Clases;

import javafx.scene.control.TableColumn;

public class Zombie extends Thread{

    private String nombre;
    private ZonaInsegura zona;
    private int muertes = 0;

    public Zombie(ZonaInsegura zona) {
        this.nombre = "Z" + this.getId();
        this.zona = zona;
    }

    public void run() {
        try {
            Humano elegido;
            while(true) {
                elegido = zona.getVictima();
                if (elegido != null) {
                    elegido=zona.getVictima();
                    morder(elegido);
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







}
