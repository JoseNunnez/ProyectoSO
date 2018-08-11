/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorraid;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Unknown
 */
public class VentanaPrincipalController implements Initializable {

    @FXML
    private Button botonArchivo;
    @FXML
    private Label labelRutaArchivo;
    @FXML
    private ComboBox<?> comboRAID;
    @FXML
    private Button botonEjecutar;
    @FXML
    private TableView<?> tablaArchivo;
    @FXML
    private TableColumn<?, ?> columnaArchivo;
    @FXML
    private TableColumn<?, ?> columnaRAID;
    @FXML
    private Button botonMostrar;
    @FXML
    private Label labelRetroalimentacion;
    @FXML
    private TextArea textAreaMostrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void accionBotonArchivo(ActionEvent event) {
    }

    @FXML
    private void accionComboRAID(ActionEvent event) {
    }

    @FXML
    private void accionBotonEjecutar(ActionEvent event) {
    }

    @FXML
    private void accionBotonMostrar(ActionEvent event) {
    }
    
}
