/*
 * Se dividen los datos segun bytes, que para este caso será por cada linea de texto que el 
arcivo tenga, por lo tanto, por cada linea del archivo se va repartiendo entre los 3 discos
para posteriormente calcular cada line a nivel de byte, para despues sumar todos los bytes
cuya suma es utilizada para generar un bit de paridad
que es almacenada en el disco de paridad, si ocurre un fallo, se podría calcular los bytes restantes 
y por consiguiente se puede volver a restaurar el texto perdido (ESTO ULTIMO NO ESTA 
CONSIDERADO EN ESTA IMPLEMENTACIÓN)
 */
package simuladorraid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Unknown
 */
public class RAID3 {
     private File archivoOrigen;

    private File disco1;
    private File disco2;
    private File disco3;
    private File discoParidad;
    
    public RAID3(){
    } 
    public RAID3(File archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
        String nombreArch = archivoOrigen.getName().split(".txt")[0];
        String _pathPrograma = new File ("").getAbsolutePath ();
        new File(_pathPrograma+"/RAID3/"+nombreArch+"/").mkdirs(); //Crea carpeta
        this.disco1 = new File(_pathPrograma+"/RAID3/"+nombreArch+"/disco1.txt");
        this.disco2 = new File(_pathPrograma+"/RAID3/"+nombreArch+"/disco2.txt");
        this.disco3 = new File(_pathPrograma+"/RAID3/"+nombreArch+"/disco3.txt");
        this.discoParidad = new File(_pathPrograma+"/RAID3/"+nombreArch+"/discoParidad.txt");
        System.out.println(disco1.getAbsolutePath());
    }
    public void ejecutarAlgoritmo(){
        try {
            FileWriter escritorDisco1 = new FileWriter(this.disco1);
            FileWriter escritorDisco2 = new FileWriter(this.disco2);
            FileWriter escritorDisco3 = new FileWriter(this.disco3);
            FileWriter escritorDiscoP = new FileWriter(this.discoParidad);
            FileReader f = new FileReader(this.archivoOrigen);
            BufferedReader b = new BufferedReader(f);
            String linea ="";
            int i=0;
            String texto1=null,texto2 =null,texto3 =null;
            while((linea = b.readLine())!=null) {
                if(i%3==0){
                    escritorDisco1.write(linea+"\n");
                    texto1= linea;
                }
                else if(i%3==1){
                    escritorDisco2.write(linea+"\n");
                    texto2 =linea;
                }
                else{
                    escritorDisco3.write(linea+"\n");
                    texto3=linea;
                    //Obtengo los bytes de cada linea ingresada
                    byte[] arr1 = texto1.getBytes(StandardCharsets.UTF_8);
                    byte[] arr2 = texto2.getBytes(StandardCharsets.UTF_8);
                    byte[] arr3 = texto3.getBytes(StandardCharsets.UTF_8);
                    //se suman los bytes de las 3 lineas
                    byte resultado = this.sumarBytes(arr1, arr2, arr3);
                    //se obtiene el bit de paridad para las tres lineas
                    short calculo = this.calularParidad((long)resultado);
                    
                    escritorDiscoP.write(calculo + "\n");
                    texto1=null;
                    texto2 =null;
                    texto3 =null;
                }
                i++;
            }
            byte[] arr1={},arr2={},arr3={};
            if(texto1!=null){arr1 = texto1.getBytes(StandardCharsets.UTF_8);}
            if(texto2!=null){arr2 = texto2.getBytes(StandardCharsets.UTF_8);}
            if(texto3!=null){arr3 = texto3.getBytes(StandardCharsets.UTF_8);}
            //se suman los bytes de las 3 lineas
            byte resultado = this.sumarBytes(arr1, arr2, arr3);
            //se obtiene el bit de paridad para las tres lineas
            short calculo = this.calularParidad((long)resultado);
            escritorDiscoP.write(calculo + "\n");
            
            escritorDisco1.close();
            escritorDisco2.close();
            escritorDisco3.close();
            escritorDiscoP.close();
        }
        catch (IOException ex) {
            System.out.println("HA OCURRIDO UN ERROR AL ESCRIBIR EN EL ARCHIVO");
        }
    }
    
    private byte sumarBytes(byte[] arr1, byte[] arr2 ,byte[] arr3){
        byte b1=0,b2=0,b3=0;
        for (int i = 0; i < arr1.length; i++) {
             b1= (byte) (b1 + arr1[i]);
        }
        for (int i = 0; i < arr2.length; i++) {
             b2= (byte) (b2 + arr2[i]);
        }
         for (int i = 0; i < arr3.length; i++) {
             b2= (byte) (b2 + arr3[i]);
        }
         return (byte)(b1+b2+b3);
    }
    
    //SE ENVIA EL NOMBRE : archivo.txt
    public void generarArchivo(String nombreArchivo, VentanaPrincipalController vp){
        VentanaPrincipalController ventana = vp;
        String _pathPrograma = new File ("").getAbsolutePath ();
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID3/"+nombreArchivo+"/"+"disco1.txt");
            BufferedReader buffer1 = new BufferedReader(disco1);
            FileReader disco2 = new FileReader(_pathPrograma+"/RAID3/"+nombreArchivo+"/"+"disco2.txt");
            BufferedReader buffer2 = new BufferedReader(disco2);
            FileReader disco3 = new FileReader(_pathPrograma+"/RAID3/"+nombreArchivo+"/"+"disco3.txt");
            BufferedReader buffer3 = new BufferedReader(disco3);
            FileReader discoP = new FileReader(_pathPrograma+"/RAID3/"+nombreArchivo+"/"+"discoParidad.txt");
            BufferedReader buffer4 = new BufferedReader(discoP);
            try {
                
                String linea1 ="";
                String linea2 ="";
                String linea3 ="";
                boolean flag = true;
                int cont=0;
                while( flag ){
                    if(cont%3==0){
                        linea1 = buffer1.readLine();
                    }
                    else if(cont%3==1){
                        linea2 = buffer2.readLine();
                        
                    }
                    else{
                       linea3 = buffer3.readLine();
                        System.out.println(linea1+"-"+linea2+"-"+linea3);
                       //SE PROCEDE A CALCULAR LA PARIDAD DE LAS LINEAS PARA COMPARARLAS CON 
                       //LAS AMACENADAS EN EL DISCO DE PARIDAD
                       byte[] arr1 ={},arr2={},arr3={};
                        if(linea1!=null) { arr1= linea1.getBytes(StandardCharsets.UTF_8);}
                        if(linea2!=null){arr2 = linea2.getBytes(StandardCharsets.UTF_8);}
                        if(linea3!=null){ arr3 = linea3.getBytes(StandardCharsets.UTF_8);}
                        //se suman los bytes de las 3 lineas
                        byte resultado = this.sumarBytes(arr1, arr2, arr3);
                        //se obtiene el bit de paridad para las tres lineas
                        short calculo = this.calularParidad((long)resultado);
                        String lineaP = buffer4.readLine(); //Se obtiene el bit paridad guardado
                        if(lineaP!=null){
                            //SI LA INFO PERMANECE CORRECTA SE INSERTA EN EL ARCHIVO GENERADO
                            if(lineaP.equals(calculo+"")){
                                if(linea1!=null)ventana.modificarTextArea(linea1+"\n");
                                if(linea2!=null)ventana.modificarTextArea(linea2+"\n");
                                if(linea3!=null)ventana.modificarTextArea(linea3+"\n");
                            }
                            else{
                                System.out.println("error");
                            }
                            
                        }
                    }
                    cont++;
                    if(linea1 ==null && linea2==null &&linea3==null){
                        flag=false;
                    }
                }

            }
            catch (IOException ex) {

            }
        }
        catch (FileNotFoundException ex) {
        }
    }
    
    //NOSE LO QUE HACEE
    public static short calularParidad(long x) {

        short result = 0;
        while (x != 0) {
          result ^= (x & 1);

          x >>>= 1;

        }
        return result;

      }
}
