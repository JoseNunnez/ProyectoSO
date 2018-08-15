/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorraid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

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
    private TableView<DatoTabla> tablaArchivo;
    @FXML
    private TableColumn<DatoTabla, String> columnaArchivo;
    @FXML
    private TableColumn<DatoTabla, String> columnaRAID;
    @FXML
    private Button botonMostrar;
    @FXML
    private Label labelRetroalimentacion;
    @FXML
    private TextArea textAreaMostrar;

    private ObservableList<DatoTabla> datos = FXCollections.observableArrayList();
    private ArrayList<String> opcionesCombo; 
    private File archivoAbierto;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));
        columnaRAID.setCellValueFactory(new PropertyValueFactory<>("tipoRAID"));
        
       this.inicializarCombo();
       this.inicializarTabla();
        
    }    

    private void inicializarTabla(){
        this.labelRetroalimentacion.setText("Iniciando tabla....");
        String _pathPrograma = new File ("").getAbsolutePath ();
        for (String tipoRaid : opcionesCombo) {
            File carpeta = new File(_pathPrograma+"/"+tipoRaid);
            if(carpeta!=null){
                this.obtenerArchivosCargados(carpeta, tipoRaid);
            }
        }
        this.tablaArchivo.setItems(datos);
        this.labelRetroalimentacion.setText("");
    }
    
    public void obtenerArchivosCargados(final File carpeta, String tipo) {
        if(carpeta.list()!=null){
            for (final String ficheroEntrada : carpeta.list()) {
                DatoTabla nuevo = new DatoTabla(ficheroEntrada, tipo);
                this.datos.add(nuevo);
            }
        }
}
    private void inicializarCombo(){
        this.opcionesCombo = new ArrayList<>();
        this.opcionesCombo.add("RAID0");
        this.opcionesCombo.add("RAID1");
        this.opcionesCombo.add("RAID3");
        this.opcionesCombo.add("RAID4");
        this.opcionesCombo.add("RAID5");
        this.opcionesCombo.add("RAID6");
        this.comboRAID.getItems().addAll(this.opcionesCombo);
        this.comboRAID.getSelectionModel().select(0);
    }
    @FXML
    private void accionBotonArchivo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        this.archivoAbierto = fc.showOpenDialog(null);
        if(archivoAbierto!=null){
            labelRutaArchivo.setText("Archivo Seleccionado: "+ archivoAbierto.getAbsolutePath());
        }
        else{
            labelRutaArchivo.setText("");
        }
    }

    @FXML
    private void accionComboRAID(ActionEvent event) {
    }

    @FXML
    private void accionBotonEjecutar(ActionEvent event) {
        if(this.archivoAbierto ==null){
         //MENSAJE DE ERROR
            this.mostrarMensajeAlerta("No ha ingresado un archivo", "Debe ingresar un archivo antes de ejecutar.");
        }
        else{
            String tipo = this.comboRAID.getSelectionModel().getSelectedItem();
            this.labelRetroalimentacion.setText("Ejecutando "+tipo+" en"+archivoAbierto.getName());
            System.out.println(tipo);
            switch(tipo){
                case "RAID0":
                    RAID0 raid0 = new RAID0(archivoAbierto, this);
                    raid0.ejecutarAlgoritmo();
                    break;
                case "RAID1":
                    RAID1 raid1 = new RAID1(archivoAbierto);
                    raid1.ejecutarAlgoritmo();
                    break;
                case "RAID3":
                    RAID3 raid3 = new RAID3(archivoAbierto);
                    raid3.ejecutarAlgoritmo();
                    break;
                case "RAID4":
                    RAID4 raid4 = new RAID4(archivoAbierto);
                    raid4.ejecutarAlgoritmo();
                    break;
                case "RAID5":
                    RAID5 raid5 = new RAID5(archivoAbierto);
                    raid5.ejecutarAlgoritmo();
                    break;
                case "RAID6":
                    RAID6 raid6 = new RAID6(archivoAbierto);
                    raid6.ejecutarAlgoritmo();
                    break;
            }
            this.updateTabla();
            this.labelRetroalimentacion.setText("");
        }
    }

 
    @FXML
    private void accionBotonMostrar(ActionEvent event) throws IOException {
        this.textAreaMostrar.setText("");
        DatoTabla dato = this.tablaArchivo.getSelectionModel().getSelectedItem();
        if(dato==null){
            this.mostrarMensajeAlerta("", "Seleccione una fila de la tabla");
        }
        else{
            this.labelRetroalimentacion.setText("Mostrando "+ dato.getNombreArchivo() +
                                                " generado en "+ dato.getTipoRAID());
            switch(dato.getTipoRAID()){
                case "RAID0":
                    RAID0 raid0 = new RAID0();
                    raid0.generarArchivo(dato.getNombreArchivo(),this);                   
                    break;
                case "RAID1":
                    RAID1 raid1 = new RAID1();
                    raid1.generarArchivo(dato.getNombreArchivo(), this);
                    break;
                case "RAID3":
                    RAID3 raid3 = new RAID3();
                    raid3.generarArchivo(dato.getNombreArchivo(),this);
                    break;
                case "RAID4":
                    RAID4 raid4 = new RAID4();
                    raid4.generarArchivo(dato.getNombreArchivo(), this);
                    break;
                case "RAID5":
                    RAID5 raid5 = new RAID5();
                    raid5.generarArchivo(dato.getNombreArchivo(), this);
                    break;
                case "RAID6":
                    RAID6 raid6 = new RAID6();
                    raid6.generarArchivo(dato.getNombreArchivo(), this);
                    break;
            }
        }
        
    }
    private  void updateTabla(){
        this.datos.clear();
        this.inicializarTabla();
    }
    private void mostrarArchivoVentana(File arch){
        this.textAreaMostrar.setText("");
        try{
            FileReader f = new FileReader(arch);
            BufferedReader b = new BufferedReader(f);
            String linea ="";
          
            while((linea = b.readLine())!=null) {
                this.textAreaMostrar.setText( this.textAreaMostrar.getText()+linea+"\n");
            }
        }
        catch(Exception e){
        }
    }
        /**
     * Se encarga de mostrar un mensaje de alerta en pantalla
     * @param text1 Es el titulo del mensaje
     * @param texto2 Es el cuerpo del mensaje
     */
    private void mostrarMensajeAlerta(String text1, String texto2) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(null);
        alert.setTitle(text1);
        alert.setHeaderText(null);
        alert.setContentText(texto2);
        alert.showAndWait();
    }
    
    public void modificarTextArea(String texto){
        this.textAreaMostrar.setText(this.textAreaMostrar.getText()+texto);
    }
}
