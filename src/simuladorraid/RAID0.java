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
public class RAID0 {
    private File archivoOrigen;

    private File disco1;
    private File disco2;
    
    public RAID0(){
    } 
    public RAID0(File archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
        String nombreArch = archivoOrigen.getName().split(".txt")[0];
        String _pathPrograma = new File ("").getAbsolutePath ();
        new File(_pathPrograma+"/RAID0/"+nombreArch+"/").mkdirs();
        this.disco1 = new File(_pathPrograma+"/RAID0/"+nombreArch+"/disco1.txt");
        this.disco2 = new File(_pathPrograma+"/RAID0/"+nombreArch+"/disco2.txt");
        System.out.println(disco1.getAbsolutePath());
    }
    public void ejecutarAlgoritmo(){
        try {
            FileWriter escritorDisco1 = new FileWriter(this.disco1);
            FileWriter escritorDisco2 = new FileWriter(this.disco2);
            FileReader f = new FileReader(this.archivoOrigen);
            BufferedReader b = new BufferedReader(f);
            String linea ="";
            int i=0;
            while((linea = b.readLine())!=null) {
                if(i%2==0){
                    escritorDisco1.write(linea+"\n");
                }
                else{
                    escritorDisco2.write(linea+"\n");
                }
                i++;
            }
            escritorDisco1.close();
            escritorDisco2.close();
        }
        catch (IOException ex) {
            System.out.println("HA OCURRIDO UN ERROR AL ESCRIBIR EN EL ARCHIVO");
        }
    }
    
    //SE ENVIA EL NOMBRE : archivo.txt
    public File generarArchivo(String nombreArchivo){
        
        String _pathPrograma = new File ("").getAbsolutePath ();
        File generado = new File(_pathPrograma+"/RAID0/"+nombreArchivo+"/generado.txt");
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID0/"+nombreArchivo+"/"+"disco1.txt");
            BufferedReader buffer1 = new BufferedReader(disco1);
            FileReader disco2 = new FileReader(_pathPrograma+"/RAID0/"+nombreArchivo+"/"+"disco2.txt");
            BufferedReader buffer2 = new BufferedReader(disco2);
            
            try {
                
                FileWriter escritorGenerado = new FileWriter(generado);
                String linea1 ="";
                String linea2 ="";
                boolean flag = true;
                int cont=0;
                while( flag ){
                    if(cont%2==0){
                        if((linea1 = buffer1.readLine())!=null){
                        escritorGenerado.write(linea1 +"\n");
                        }
                    }
                    else{
                        if((linea2 = buffer2.readLine())!=null){
                        escritorGenerado.write(linea2 +"\n");
                        }
                    }
                    cont++;
                    if(linea1 ==null && linea2==null){
                        flag=false;
                    }
                }
                escritorGenerado.close();  
            }
            catch (IOException ex) {

            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(RAID0.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generado;
    }
    
}
