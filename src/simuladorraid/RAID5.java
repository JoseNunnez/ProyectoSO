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
public class RAID5 {
    private File archivoOrigen;

    private File disco1;
    private File disco2;
    private File disco3;
    
    public RAID5(){}
    
    public RAID5(File archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
        String nombreArch = archivoOrigen.getName().split(".txt")[0];
        String _pathPrograma = new File ("").getAbsolutePath ();
        new File(_pathPrograma+"/RAID5/"+nombreArch+"/").mkdirs();
        this.disco1 = new File(_pathPrograma+"/RAID5/"+nombreArch+"/disco1.txt");
        this.disco2 = new File(_pathPrograma+"/RAID5/"+nombreArch+"/disco2.txt");
        this.disco3 = new File(_pathPrograma+"/RAID5/"+nombreArch+"/disco3.txt");
        //System.out.println(disco1.getAbsolutePath());
    }
    
    public void ejecutarAlgoritmo(){
        int random=(int)(Math.random() * 3) + 1;
        int max=0;
        switch(random){
            case 1:
                max = escribirArchivoCompleto(this.disco1);
                escribirParteArchivo(this.disco2, this.disco3, max);
                break;
            case 2:
                max = escribirArchivoCompleto(this.disco2);
                escribirParteArchivo(this.disco1, this.disco3, max);
                break;
            case 3:
                max = escribirArchivoCompleto(this.disco3);
                escribirParteArchivo(this.disco1, this.disco2, max);
                break;
        }
    }
    
    public int escribirArchivoCompleto(File archivo){
        try {
            FileWriter escritorDisco1 = new FileWriter(archivo);
            FileReader f = new FileReader(this.archivoOrigen);
            BufferedReader b = new BufferedReader(f);
            String linea ="";
            int cantidadLineas=0;
            while((linea = b.readLine())!=null) {
                escritorDisco1.write(linea+"\r\n");
                cantidadLineas++;
            }
            escritorDisco1.close();
            return cantidadLineas;
        }
        catch (IOException ex) {
            System.out.println("HA OCURRIDO UN ERROR AL ESCRIBIR EN EL ARCHIVO COMPLETO");
        }
        return 0;
    }
    
    public void escribirParteArchivo(File archivo1,File archivo2, int max){
        try {
            FileWriter escritorDisco1 = new FileWriter(archivo1);
            FileWriter escritorDisco2 = new FileWriter(archivo2);
            FileReader f = new FileReader(this.archivoOrigen);
            BufferedReader b = new BufferedReader(f);
            String linea ="";
            int cantidadLineas=0;
            while((linea = b.readLine())!=null) {
                if(cantidadLineas<max/2){
                    escritorDisco1.write(linea+"\r\n");
                }
                else{
                    escritorDisco2.write(linea+"\r\n");
                }
                cantidadLineas++;
            }
            escritorDisco1.close();
            escritorDisco2.close();
        }
        catch (IOException ex) {
            System.out.println("HA OCURRIDO UN ERROR AL ESCRIBIR EN EL ARCHIVO PARTE");
        }
    }
    
    public void generarArchivo(String nombreArchivo, VentanaPrincipalController vp){
        VentanaPrincipalController ventana = vp;
        String _pathPrograma = new File ("").getAbsolutePath ();
        switch(retornarMayor(nombreArchivo)){
            case 1:
                System.out.println("Leyendo disco 2 y 3");
                generarArchivoCompleto(nombreArchivo, "disco2.txt",
                                       "disco3.txt", ventana);
            case 2:
                System.out.println("Leyendo disco 1 y 3");
                generarArchivoCompleto(nombreArchivo, "disco1.txt",
                                       "disco3.txt", ventana);
            case 3:
                System.out.println("Leyendo disco 1 y 2");
                generarArchivoCompleto(nombreArchivo, "disco1.txt",
                                       "disco2.txt", ventana);
        }

    }
    
    public int retornarMayor(String nombreArchivo){
        String _pathPrograma = new File ("").getAbsolutePath ();
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID5/"+nombreArchivo+"/"+"disco1.txt");
            BufferedReader buffer1 = new BufferedReader(disco1);
            FileReader disco2 = new FileReader(_pathPrograma+"/RAID5/"+nombreArchivo+"/"+"disco2.txt");
            BufferedReader buffer2 = new BufferedReader(disco2);
            FileReader disco3 = new FileReader(_pathPrograma+"/RAID5/"+nombreArchivo+"/"+"disco3.txt");
            BufferedReader buffer3 = new BufferedReader(disco3);
            int cant1 = (int)buffer1.lines().count();
            int cant2 = (int)buffer2.lines().count();
            int cant3 = (int)buffer3.lines().count();
            if ( cant1 > cant2 && cant1 > cant3  ) {
                return 1;
            }
            if ( cant2 > cant1 && cant2 > cant3  ) {
                return 2;
            }
            if ( cant3 > cant1 && cant3 > cant2  ) {
                return 3;
            }         
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(RAID5.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void generarArchivoCompleto(String nombreArchivo,String archivo1, String archivo2, 
                                       VentanaPrincipalController vp){
        VentanaPrincipalController ventana = vp;
        String _pathPrograma = new File ("").getAbsolutePath ();
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID5/"+nombreArchivo+"/"+archivo1);
            BufferedReader buffer1 = new BufferedReader(disco1);
            FileReader disco2 = new FileReader(_pathPrograma+"/RAID5/"+nombreArchivo+"/"+archivo2);
            BufferedReader buffer2 = new BufferedReader(disco2);
            try {
                String linea ="";
                while((linea = buffer1.readLine())!=null) {
                    ventana.modificarTextArea(linea+"\r\n");
                }
                while((linea = buffer2.readLine())!=null) {
                    ventana.modificarTextArea(linea+"\r\n");
                }
            }
            catch (IOException ex) {

            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(RAID5.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
