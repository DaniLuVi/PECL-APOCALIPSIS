package Clases;



import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ListaThreads {
    ArrayList<Thread> lista;
    TextArea txtarea;

    public ListaThreads(TextArea tf)
    {
        lista=new ArrayList<Thread>();
        this.txtarea =tf;
    }
    public synchronized boolean isEmpty(){
        System.out.println(lista+"" +lista.isEmpty());

        return lista.isEmpty();
    }

    public synchronized void meter(Thread t)
    {
        lista.add(t);
        //imprimir();
    }

    public synchronized void sacar(Thread t)
    {
        lista.remove(t);
        imprimir();
    }

    public void imprimir()
    {
        String contenido="";
        for(int i=0; i<lista.size(); i++)
        {
            contenido=contenido+lista.get(i).getName()+" ";
        }
        final String textoImprimir = contenido.toString();

        Platform.runLater(() -> {
            txtarea.setText(textoImprimir);
        });
    }
}
