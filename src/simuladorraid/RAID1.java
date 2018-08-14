/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorraid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unknown
 */
public class RAID1 {
    private File archivoOrigen;
    private File disco1;
    private File disco2;

    
    public RAID1(){}
    
    public RAID1(File archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
        String nombreArch = archivoOrigen.getName().split(".txt")[0];
        String _pathPrograma = new File ("").getAbsolutePath ();
        new File(_pathPrograma+"/RAID1/"+nombreArch+"/").mkdirs();
        this.disco1 = new File(_pathPrograma+"/RAID1/"+nombreArch+"/disco1.txt");
        this.disco2 = new File(_pathPrograma+"/RAID1/"+nombreArch+"/disco2.txt");
        //System.out.println(disco1.getAbsolutePath());
    }
    
    public void ejecutarAlgoritmo(){
        try {
            FileWriter escritorDisco1 = new FileWriter(this.disco1);
            FileWriter escritorDisco2 = new FileWriter(this.disco2);
            FileReader f = new FileReader(this.archivoOrigen);
            BufferedReader b = new BufferedReader(f);
            String linea ="";
            while((linea = b.readLine())!=null) {
                escritorDisco1.write(linea+"\n");
                escritorDisco2.write(linea+"\n");
            }
            escritorDisco1.close();
            escritorDisco2.close();
        }
        catch (IOException ex) {
            System.out.println("HA OCURRIDO UN ERROR AL ESCRIBIR EN EL ARCHIVO");
        }
    }
    
    public void generarArchivo(String nombreArchivo,VentanaPrincipalController vp){
        VentanaPrincipalController ventana=vp;
        String _pathPrograma = new File ("").getAbsolutePath ();

        boolean exito = false;
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID1/"+nombreArchivo+"/"+"disco1.txt");
            BufferedReader buffer1 = new BufferedReader(disco1);     
            try {
                String linea1 ="";
                while((linea1 = buffer1.readLine())!=null){
                    if(linea1!=null){
                        ventana.modificarTextArea(linea1+"\n");
                    }
                }
                exito = true;
            }
            catch (IOException ex) {
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(RAID1.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!exito){
            try {
                FileReader disco2 = new FileReader(_pathPrograma+"/RAID1/"+nombreArchivo+"/"+"disco2.txt");
                BufferedReader buffer2 = new BufferedReader(disco2);    
                try {
                    String linea2 ="";
                    while((linea2 = buffer2.readLine())!=null){
                        if(linea2!=null){
                            ventana.modificarTextArea(linea2+"\n");
                        }
                    }
                }
                catch (IOException ex) {
                }
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(RAID1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
