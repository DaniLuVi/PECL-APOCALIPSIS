package Clases;

public class Humano extends Thread{

    private String nombre;
    private Refugio refugio;
    private Tunel tunel;
    private Logger log;
    private int comida;

    public Humano(String nombre, Refugio refugio) {
        super(nombre);
        this.refugio = refugio;
        log = new Logger("apocalipsis.txt");

    }


    public void run() {
      /*   try {
            while(true) {
               refugio.entrarZona(nombre);
                Thread.sleep((int)Math.random() * 1000 + 1000);
                int tunel_escogido = ((int)Math.random() * 5) - 1;
                tunel.paso(tunel_escogido, this, true);
                comida = comida + 2;
                Thread.sleep((int)Math.random() * 2000 + 3000);

            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } */
    }
}
