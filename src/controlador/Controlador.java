package controlador;

import editor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase controlador encargada de manejar los eventos de la ventana principal
 * @author David Dorado Carvajal
 * @version 1.0
 * @since 1.0
 */
public class Controlador {
    private FileChooser archivo = new FileChooser();
    private File file;
    @FXML
    private TextArea texto = new TextArea();
    @FXML
    private Label estado = new Label();
    @FXML
    private String[] recientes = new String[3];
    @FXML
    private MenuItem reciente1 = new MenuItem();
    @FXML
    private MenuItem reciente11 = new MenuItem();
    @FXML
    private MenuItem reciente2 = new MenuItem();
    @FXML
    private MenuItem reciente22 = new MenuItem();
    @FXML
    private MenuItem reciente3 = new MenuItem();
    @FXML
    private MenuItem reciente33 = new MenuItem();




    /**
     * Método encargado de preparar los botones de los menús de archivos recientes para
     * abrir sus archivos correspondientes con setOnAction para cuando se pulse sobre ellos
     */
    public void botonRecientes(){
        // Para cada uno de los menuitem se ejecuta abrirReciente cuando llegue un evento
        this.reciente1.setOnAction(event -> abrirReciente(this.reciente1.getText()));
        this.reciente11.setOnAction(event -> abrirReciente(this.reciente11.getText()));
        this.reciente2.setOnAction(event -> abrirReciente(this.reciente2.getText()));
        this.reciente22.setOnAction(event -> abrirReciente(this.reciente22.getText()));
        this.reciente3.setOnAction(event -> abrirReciente(this.reciente3.getText()));
        this.reciente33.setOnAction(event -> abrirReciente(this.reciente33.getText()));
    }

    /**
     * Metodo que crea un archivo nuevo mostrando una ventana de dialogo para decidir donde se va a crear
     */
    public void nuevoArchivo() {
        try {
            // Primero se vacia el textarea
            this.texto.setText("");
            //Se asigna a file el valor que devuelve la ventana de showSaveDialog
            this.file = new File(this.archivo.showSaveDialog(Main.getStage()).getPath());
            //Se cambia la propiedad WrapText a true para que salte de linea cuando llegue al borde de la ventana
            this.texto.setWrapText(true);
            //Se llama a los metodos asignar recientes y boton recientes
            this.asignarRecientes();
            this.botonRecientes();
            //Una vez asignado se pone en el titulo de la Stage principal la ruta del archivo abierto
            Main.getStage().setTitle("Editor de Texto " + this.file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo encargado de abrir un archivo existente mostrando una ventana de dialogo para buscar el archivo
     */
    public void abrirArchivo() {
        try {
            // Primero se vacia el textarea
            this.texto.setText("");
            //Se asigna a file el valor que devuelve la ventana de showOpenDialog
            this.file = new File(this.archivo.showOpenDialog(Main.getStage()).getAbsolutePath());
            //Se llama al método leerArchivo
            this.leerArchivo();
            //Se llama a los metodos asignar recientes y boton recientes
            this.asignarRecientes();
            this.botonRecientes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que muestra una ventana de dialogo para guardar un archivo abierto en otra ruta
     */
    public void guardarComo() {
        try {
            /*
             * Se crea un printwriter y se le asigna el valor de un showsavedialog de un objeto filewriter
             * creado como parámetro
             */
            PrintWriter pw = new PrintWriter(new FileWriter(this.archivo.showSaveDialog(Main.getStage())));
            //Se guarda en el archivo el texto del textarea texto con print
            pw.print(this.texto.getText());
            //Se realiza un flush para guardarlo y despues se cierra el flujo.
            pw.flush();
            pw.close();
            // Se asigna la nueva ruta al titulo de la Scene
            Main.getStage().setTitle("Editor de Texto " + this.file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo encargado de mostrar la ventana Acerca De
     */
    public void acercaDe() {
        try {
            // Se asigna el fxml que va a tener la ventana
            Parent root = FXMLLoader.load(getClass().getResource("../vista/AcercaDe.fxml"));
            // Se crea una escena
            Scene escena = new Scene(root);
            // Se crea un Stage
            Stage stage = new Stage();
            /*
             * Se usa el método initModality para indicar que es una ventana modal que bloquea
             * la otra ventana
             */
            stage.initModality(Modality.APPLICATION_MODAL);
            //Se asigna la scena al Stage, el titulo Acerca de y se muestra
            stage.setScene(escena);
            stage.setTitle("Acerca de");
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para cerrar el programa
     */
    public void cerrar() {
        System.exit(0);
    }

    /**
     * Metodo encargado de guardar un archivo abierto
     */
    public void guardar() {
        try {
            /*
             * Se obtiene la ruta del archivo abierto se usa print para guardar el texto del textarea
             * y se realiza un flush para guardarlo y despues se cierra el flujo.
             */
            PrintWriter pw = new PrintWriter(this.file.getAbsolutePath());
            pw.print(this.texto.getText());
            pw.flush();
            pw.close();
            Main.getStage().setTitle("Editor de Texto " + this.file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método encargado de contar las palabras del textarea y de mostrar
     * la columna en la que está el cursor
     */
    public void contadorPalabras() {
        /*
         * Se crea un escaner, se va leyendo el escaner mientras que hasNext() devuelva true
         * y se suma 1 a palabras cada vuelta de bucle
         */
        Scanner sc = new Scanner(this.texto.getText());
        int palabras = 0;
        while (sc.hasNext()) {
            try {
                sc.next();
                palabras++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
         * Se muestra en el label estado las palabras que hay con la variable palabras y la columna
         * con la propiedad CaretPosition
         */
        this.estado.setText("Palabras: " + palabras + " Columna: " + texto.getCaretPosition());
    }

    /**
     * Metodo encargado de abrir un archivo de los archivos recientes
     * @param archivo Cadena con la ruta del archivo a abrir
     */
    public void abrirReciente(String archivo) {
        try {
            // Primero se vacia el textarea
            this.texto.setText("");
            // Se asigna a file la cadena introducida como parámetro
            this.file = new File(archivo);
            //Se llama al método leerArchivo
            this.leerArchivo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método para asignar a los botones de los archivos recientes las rutas de los archivos abiertos
     */
    public void asignarRecientes(){
        /*
         * Se asigna a archivos recientes el valor de la ruta del archivo nuevo rellenando un array
         * con la ruta y vaciandolo en caso de que esté lleno, finalmente se asigna a los menús
         */
        for (int i = 0; i < this.recientes.length; i++) {
            if (this.recientes[i] == null) {
                this.recientes[i] = this.file.getAbsolutePath();
                break;
            }else if (this.recientes[2]!=null){
                Arrays.fill(this.recientes, null);
                this.recientes[0] = this.file.getAbsolutePath();
                break;
            }
        }
        this.reciente1.setText(recientes[0]);
        this.reciente11.setText(recientes[0]);
        this.reciente2.setText(recientes[1]);
        this.reciente22.setText(recientes[1]);
        this.reciente3.setText(recientes[2]);
        this.reciente33.setText(recientes[2]);
    }

    /**
     * Método encargado de leer archivos y rellenar el textarea con el contenido del archivo leido
     */
    public void leerArchivo(){
        try {
            // Se prepara un FileReader para leer el archivo y BufferedReader para leer las lineas
            FileReader fr = new FileReader(this.file);
            BufferedReader bf = new BufferedReader(fr);
            // Se pone en el titulo de la Stage principal la ruta del archivo abierto
            Main.getStage().setTitle("Editor de Texto " + this.file.getAbsolutePath());
            // Se rellena el text area con el contenido del archivo
            String line;
            while ((line = bf.readLine()) != null)
                this.texto.appendText(line + "\n");
            this.texto.setWrapText(true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

