package Clases;

import java.io.BufferedWriter;
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
        this.ruta = ruta;
        log = new File(ruta);
        try {
            log.createNewFile();
            escribir = new FileWriter(log,true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void escribir(String mensaje) {
        c.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
            writer.write(new Date() + ":" + mensaje + "\n");
        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
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
