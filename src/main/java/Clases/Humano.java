package Clases;

public class Humano extends Thread{

    private String nombre;
    private Refugio refugio;

    public Humano(String nombre, Refugio refugio) {
        super(nombre);
        this.refugio = refugio;
    }
    public void run() {
        while(true) {

        }
    }

}
