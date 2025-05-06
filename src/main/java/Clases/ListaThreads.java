package Clases;



import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ListaThreads {
    ArrayList<Thread> lista;
    TextArea txtarea;
    Paso p;

    public ListaThreads(TextArea tf, Paso p)
    {
        lista=new ArrayList<Thread>();
        this.txtarea = tf;
        this.p = p;
    }
    public synchronized boolean isEmpty(){
        System.out.println(lista+"" +lista.isEmpty());

        return lista.isEmpty();
    }

    public synchronized void meter(Thread t)
    {
        p.mirar();
        lista.add(t);
        imprimir();
    }

    public synchronized void sacar(Thread t)
    {
        p.mirar();
        lista.remove(t);
        imprimir();
    }

    public void imprimir()
    {
        p.mirar();
        String contenido = "";
        for(int i=0; i<lista.size(); i++)
        {
            contenido = contenido + lista.get(i).getName() + " ";
        }
        final String textoImprimir = contenido.toString();

        Platform.runLater(() -> {
            txtarea.setText(textoImprimir);
        });
    }
}
