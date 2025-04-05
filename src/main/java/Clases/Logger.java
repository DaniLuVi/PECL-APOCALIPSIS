package Clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class Logger {
    private static String ruta = "apocalipsis.txt";
    private static Lock c = new ReentrantLock();
    private static File log;
    private static FileWriter escribir;

    public Logger(String ruta){
        log = new File(ruta);
        try {
            log.createNewFile();
            escribir = new FileWriter(log,true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void escribir(String texto){
        c.lock();
        try {

            System.out.println(new Date() + ": " + texto);
            //escribir.append(new Date() + ": " + texto + "\n");

            //escribir.write(new Date() + ": " + texto + "\n");
            //escribir.close();
            //escribir.flush();
        } catch (Exception e){
            System.out.println(e);
        }finally {
            c.unlock();
        }

    }

    public static void main(String[] args){
        Logger log = new Logger("apocalipsis.txt");
        log.escribir("Esto es una prueba");
        log.escribir("Esto es una prueba2");
        log.escribir("Esto es una prueba3");




    }

}
