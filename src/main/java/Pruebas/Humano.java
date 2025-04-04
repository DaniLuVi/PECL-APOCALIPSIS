package Pruebas;


public class Humano extends Thread{

    private String nombre;
    private Pruebas.Tunel tunel;
    public Humano(String nombre, Pruebas.Tunel tunel) {
        super(nombre);
        this.tunel = tunel;
        }
        public void run() {
            while(true) {
                tunel.paso(this,  false);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                tunel.paso(this,true);
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                }
            }

        }

