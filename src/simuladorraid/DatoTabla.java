/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorraid;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Unknown
 */
public class DatoTabla {
    private StringProperty nombreArchivo;
    private StringProperty tipoRAID;

    public DatoTabla(String nombreArchivo, String tipoRAID) {
        this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
        this.tipoRAID = new SimpleStringProperty(tipoRAID);
    }

    public String getNombreArchivo() {
        return nombreArchivo.getValue();
    }

    public void setNombreArchivo(StringProperty nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoRAID() {
        return tipoRAID.getValue();
    }

    public void setTipoRAID(StringProperty tipoRAID) {
        this.tipoRAID = tipoRAID;
    }
    
    
    
    
}
