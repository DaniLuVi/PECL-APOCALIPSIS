package Clases;

import javafx.scene.control.TableColumn;

public class Zombie extends Thread{

    private ZonaInsegura[] zonas;
    private int muertes = 0;

    public Zombie(int id, ZonaInsegura[] zonas) {
        this.setName( "Z" + String.format("%04d", id));
        this.zonas = zonas;
    }
    public Zombie(int id, ZonaInsegura[] zonas, int n ) {
        this.setName( "Z" + String.format("%04d", id));
        this.zonas = zonas;
        zonas[n].entrar(this, false);
        try {
            Thread.sleep(2000 + (int)(2000*Math.random()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        zonas[n].entrar(this, true);
    }


    public void run() {
        try {
            Humano elegido;
            int numero_zona = 0;
            boolean res_Ataque;
            while(true) {
                numero_zona = (int) (Math.random() * 3);        // igual lo he cambiado en los tuneles porque 0 a 3 hay 0,1,2,3 (4 zonas)
                zonas[numero_zona].entrar(this,false);
                elegido = zonas[numero_zona].getVictima();
                if (elegido != null) {
                    System.out.println("---------------- ELEGIDO: " + elegido.getName() +"----------------");
                    if(morder(elegido)){
                        muertes++;
                        zonas[numero_zona].entrar(elegido, true);
                        new Zombie(elegido.getid(), zonas, numero_zona).start();

                        elegido.join();
                    }
                }
                Thread.sleep(2000+ (int)(Math.random()*1000));
                zonas[numero_zona].entrar(this, true);
                }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean morder(Humano h){
        try{
            h.interrupt();
            Thread.sleep((int)(500 + Math.random() * 1000));

            boolean b = Math.random() * 3 > 2;
            System.out.println("valor de b:" + b);
            if (h.morder(b)== false) {return false;} // Un tercio de las veces devuelve true;
            return b ;
        } catch (InterruptedException e) {throw new RuntimeException(e);}
    }



}
