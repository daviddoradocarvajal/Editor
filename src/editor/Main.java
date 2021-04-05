package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Clase lanzadora de la aplicacion
 * @author David Dorado Carvajal
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {
    private static Stage principal;

    /**
     * Metodo especial que se invoca desde el metodo main para generar la primera ventana de la aplicacion
     * @param primaryStage Stage principal que es la ventana de la aplicacion
     * @throws Exception Prepara el método para cualquier excepción que pueda lanzar
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Se asigna a la propiedad principal el valor de primaryStage para cuando se necesite cambiar de Stage
        principal = primaryStage;
        //Se carga la ventana principal desde principal.fxml
        Parent root = FXMLLoader.load(getClass().getResource("../vista/principal.fxml"));
        /*
         * Se asignan las propiedades a la Stage como el titulo y la escena y se muestra
         */
        primaryStage.setTitle("Editor de texto");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }


    public static Stage getStage () {
        return principal;
    }


    public static void main (String[]args){
        launch(args);
    }
}

