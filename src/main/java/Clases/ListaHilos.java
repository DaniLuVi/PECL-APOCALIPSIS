package Clases;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class ListaHilos {

    private ArrayList<Thread> lista;

    private TextArea texto;

    public ListaHilos(TextArea texto) {
        lista = new ArrayList<>();
        this.texto = texto;
    }

    public void añadir(Thread t) {
        lista.add(t);
        mostrar();
    }

    public void eliminar(Thread t) {
        lista.remove(t);
        mostrar();
    }

    public void mostrar() {
        String hilo = "";
        for (int i = 0; i < lista.size(); i++) {
            hilo = hilo + lista.get(i).getName() + " ";
        }

        final String textoMostrar = hilo.toString();

        Platform.runLater(() -> texto.setText(textoMostrar));
    }
}

// esta clase es con la que vamos a mostrar en la interfaz los humanos/zombies que hay en cada zona