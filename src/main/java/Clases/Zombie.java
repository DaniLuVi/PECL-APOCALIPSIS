package Clases;

public class Zombie extends Thread{

    private String nombre;
    private ZonaInsegura zona;
    private int muertes = 0;

    public Zombie(String nombre, ZonaInsegura zona) {
        this.nombre = nombre;
        this.zona = zona;
    }

    public void run() {
        try {
            while(true) {
                if (zona.getNumHumanos() > 0) {
                    int elegido = (int) Math.random() * zona.getNumHumanos();
                    //zona.atacar();
                    Thread.sleep((int) (Math.random() * 2000) + 1000);
                }
                int numero_zona = ((int) Math.random() * 5) - 1;
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
