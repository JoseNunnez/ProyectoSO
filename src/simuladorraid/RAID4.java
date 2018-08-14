/*
 *Es igual al 3 pero los datos se dividen en bloques de 10 caracteres
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
/**
 *
 * @author Unknown
 */
public class RAID4 {
    private File archivoOrigen;

    private File disco1;
    private File disco2;
    private File disco3;
    private File discoParidad;
    
    public RAID4(){
    } 
    public RAID4(File archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
        String nombreArch = archivoOrigen.getName().split(".txt")[0];
        String _pathPrograma = new File ("").getAbsolutePath ();
        new File(_pathPrograma+"/RAID4/"+nombreArch+"/").mkdirs(); //Crea carpeta
        this.disco1 = new File(_pathPrograma+"/RAID4/"+nombreArch+"/disco1.txt");
        this.disco2 = new File(_pathPrograma+"/RAID4/"+nombreArch+"/disco2.txt");
        this.disco3 = new File(_pathPrograma+"/RAID4/"+nombreArch+"/disco3.txt");
        this.discoParidad = new File(_pathPrograma+"/RAID4/"+nombreArch+"/discoParidad.txt");
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
            int  caracter;
            String texto="";
            int i=0,contCaract=0;
            String texto1=null,texto2 =null,texto3 =null;
            while((caracter = b.read())!=-1) {
                if(contCaract==10){
                    //texto+=(char)linea;
                    
                    if(i%3==0){
                    escritorDisco1.write(texto);
                    texto1= texto;
                    }
                    else if(i%3==1){
                        escritorDisco2.write(texto);
                        texto2 =texto;
                    }
                    else{
                        escritorDisco3.write(texto);
                        texto3=texto;
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
                    contCaract=1;
                    texto="";
                    i++;
                }
                else{  
                    contCaract++;
                }
                texto+=(char)caracter;
            }
            if(texto!="" && i%3==0){
                escritorDisco1.write(texto);
                texto1 = texto;
            }
            if(texto!="" && i%3==1){
                escritorDisco2.write(texto);
                texto2 = texto;
            }
            if(texto!="" && i%3==2){
                escritorDisco3.write(texto);
                texto3 = texto;
            }
            
            byte[] arr1={},arr2={},arr3={};
            if(texto1!=null){
                arr1 = texto1.getBytes(StandardCharsets.UTF_8);}
            if(texto2!=null){
                arr2 = texto2.getBytes(StandardCharsets.UTF_8);}
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
    
    //SE ENVIA EL NOMBRE : archivo
    public void generarArchivo(String nombreArchivo, VentanaPrincipalController vp){
        VentanaPrincipalController ventana = vp;
        String _pathPrograma = new File ("").getAbsolutePath ();
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID4/"+nombreArchivo+"/"+"disco1.txt");
            BufferedReader buffer1 = new BufferedReader(disco1);
            FileReader disco2 = new FileReader(_pathPrograma+"/RAID4/"+nombreArchivo+"/"+"disco2.txt");
            BufferedReader buffer2 = new BufferedReader(disco2);
            FileReader disco3 = new FileReader(_pathPrograma+"/RAID4/"+nombreArchivo+"/"+"disco3.txt");
            BufferedReader buffer3 = new BufferedReader(disco3);
            FileReader discoP = new FileReader(_pathPrograma+"/RAID4/"+nombreArchivo+"/"+"discoParidad.txt");
            BufferedReader buffer4 = new BufferedReader(discoP);
            try {
                
                String linea1 ="";
                String linea2 ="";
                String linea3 ="";
                boolean flag = true,x1=true,x2=true,x3=true;
                
                int cont=0, i=0;
                while( flag ){
                    if(x1 && cont%3==0){
                        if(i!=10){
                            int li = buffer1.read();
                            if(li!=-1){
                                linea1 += (char)li;
                                i++;
                            }
                            else 
                            {
                                x1=false;
                                i=10;
                            }
                        }
                        else{
                            cont++;
                            i=0;
                        }
                    }
                    else if(x2 && cont%3==1){
                        if(i!=10){
                        int li = buffer2.read();
                            System.out.println("li_"+li);    
                        if(li!=-1){
                                linea2 += (char)li;
                                i++;
                            }
                       else{ 
                            x2=false;
                            System.out.println("x2 falso");
                            i=10;}
                        }
                        else{
                            cont++;
                            i=0;
                        }
                    }
                    else{
                       if(x3 && i!=10){
                        int li = buffer3.read();
                            if(li!=-1){
                                linea3 += (char)li;
                                i++;
                            }
                            else {
                                i=10;
                                x3=false;}
                        }
                        else{

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
                                    if(linea1!="")ventana.modificarTextArea(linea1);
                                    if(linea2!="")ventana.modificarTextArea(linea2);
                                    if(linea3!="")ventana.modificarTextArea(linea3);
                                    linea1="";
                                    linea2="";
                                    linea3="";
                                }
                                else{
                                    System.out.println("error");
                                }
                            }
                            cont++;
                            i=0;
                        }   
                    }
                    if(!x1 && !x2 && !x2){
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
