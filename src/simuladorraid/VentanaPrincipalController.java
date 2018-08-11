/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorraid;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
    private ComboBox<String> comboRAID;
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

    private ArrayList<String> opcionesCombo; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.inicializarCombo();
        
    }    

    private void inicializarCombo(){
        this.opcionesCombo = new ArrayList<>();
        this.opcionesCombo.add("RAID0");
        this.opcionesCombo.add("RAID1");
        this.opcionesCombo.add("RAID2");
        this.opcionesCombo.add("RAID3");
        this.opcionesCombo.add("RAID4");
        this.opcionesCombo.add("RAID5");
        this.opcionesCombo.add("RAID6");
        this.comboRAID= new ComboBox(FXCollections.observableArrayList(this.opcionesCombo));
        this.comboRAID.getSelectionModel().select(0);
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
