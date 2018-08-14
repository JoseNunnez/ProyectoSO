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
import simuladorraid.VentanaPrincipalController;
/**
 *
 * @author Unknown
 */
public class RAID6 {
    private File archivoOrigen;

    private File disco1;
    private File disco2;
    private File disco3;
    private File disco4;
    
    public RAID6(){
    } 
    public RAID6(File archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
        String nombreArch = archivoOrigen.getName().split(".txt")[0];
        String _pathPrograma = new File ("").getAbsolutePath ();
        new File(_pathPrograma+"/RAID6/"+nombreArch+"/").mkdirs(); //Crea carpeta
        this.disco1 = new File(_pathPrograma+"/RAID6/"+nombreArch+"/disco1.txt");
        this.disco2 = new File(_pathPrograma+"/RAID6/"+nombreArch+"/disco2.txt");
        this.disco3 = new File(_pathPrograma+"/RAID6/"+nombreArch+"/disco3.txt");
        this.disco4 = new File(_pathPrograma+"/RAID6/"+nombreArch+"/disco4.txt");
        System.out.println(disco1.getAbsolutePath());
    }
    public void ejecutarAlgoritmo(){
        try {
            FileWriter escritorDisco1 = new FileWriter(this.disco1);
            FileWriter escritorDisco2 = new FileWriter(this.disco2);
            FileWriter escritorDisco3 = new FileWriter(this.disco3);
            FileWriter escritorDisco4 = new FileWriter(this.disco4);
            FileReader f = new FileReader(this.archivoOrigen);
            BufferedReader b = new BufferedReader(f);
            int  caracter;
            String texto="";
            int i=0,contCaract=0,posParidad=0;
            String texto1=null,texto2 =null;
            while((caracter = b.read())!=-1) {
                if(contCaract==10){
                    //texto+=(char)linea;
                    if(i%2==0){
                        texto1= texto;
                    }
                    else{
                        texto2=texto;
                        //Obtengo los bytes de cada linea ingresada
                        byte[] arr1 = texto1.getBytes(StandardCharsets.UTF_8);
                        byte[] arr2 = texto2.getBytes(StandardCharsets.UTF_8);
                        //se suman los bytes de las 3 lineas
                        byte resultado = this.sumarBytes(arr1, arr2);
                        //se obtiene el bit de paridad para las tres lineas
                        short paridadXOR = this.calcularParidadXOR((long)resultado);
                        int paridad2=0;//HACER FUNCION PARA PARIDAD 2 !!!!!!!!!!!!!!!!!!!!!!
                        
                        this.escribirLineas(posParidad, escritorDisco1,
                                            escritorDisco2, escritorDisco3,
                                            escritorDisco4, paridadXOR+"", paridad2+"",
                                            texto1, texto2);
                        posParidad++;
                        texto1=null;
                        texto2 =null;
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
            if(texto!="" && i%2==0){
                texto1 = texto;
            }
            if(texto!="" && i%2==1){
                texto2 = texto;
            }
            
            byte[] arr1={},arr2={},arr3={};
            if(texto1!=null){
                arr1 = texto1.getBytes(StandardCharsets.UTF_8);}
            if(texto2!=null){
                arr2 = texto2.getBytes(StandardCharsets.UTF_8);}
          //se suman los bytes de las 3 lineas
            byte resultado = this.sumarBytes(arr1, arr2);
            //se obtiene el bit de paridad para las tres lineas
            short paridad1 = this.calcularParidadXOR((long)resultado);
            int paridad2 = 0; //FUNCION PARA CALCULAR PARIDAD 2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
            this.escribirLineas(posParidad, escritorDisco1, escritorDisco2,
                                escritorDisco3, escritorDisco4, paridad1+"", paridad2+"",
                                texto1, texto2);
            
            escritorDisco1.close();
            escritorDisco2.close();
            escritorDisco3.close();
            escritorDisco4.close();
        }
        catch (IOException ex) {
            System.out.println("HA OCURRIDO UN ERROR AL ESCRIBIR EN EL ARCHIVO");
        }
    }
    
    private byte sumarBytes(byte[] arr1, byte[] arr2){
        byte b1=0,b2=0;
        for (int i = 0; i < arr1.length; i++) {
             b1= (byte) (b1 + arr1[i]);
        }
        for (int i = 0; i < arr2.length; i++) {
             b2= (byte) (b2 + arr2[i]);
        }
         return (byte)(b1+b2);
    }
    
    private void escribirLineas(int posParidad,FileWriter escritorDisco1,FileWriter escritorDisco2,
        FileWriter escritorDisco3,FileWriter escritorDisco4,String paridadXOR,String paridad2,
        String texto1,String texto2){
        System.out.println("pos:"+(posParidad%4)+" t1:"+texto1+"t2:"+texto2+"p1:"+paridadXOR+"p2:"+paridad2);
        try{
            switch(posParidad%4){
                case 0:
                    //[p2][data1][data2][p1]
                    escritorDisco1.write(paridad2+"");
                    if(!texto1.equals("null"))escritorDisco2.write(texto1+"");
                    if(!texto2.equals("null"))escritorDisco3.write(texto2+"");
                    escritorDisco4.write(paridadXOR+"");
                    break;
                case 1:
                    //[data1][data2][p1][p2]
                    if(!texto1.equals("null"))escritorDisco1.write(texto1+"");
                    if(!texto2.equals("null"))escritorDisco2.write(texto2+"");
                    escritorDisco3.write(paridadXOR+"");
                    escritorDisco4.write(paridad2+"");
                    break;
                case 2:
                    //[data1][p1][p2][data2]
                    if(!texto1.equals("null"))escritorDisco1.write(texto1+"");
                    escritorDisco2.write(paridadXOR+"");
                    escritorDisco3.write(paridad2+"");
                    if(!texto2.equals("null"))escritorDisco4.write(texto2+"");
                    break;
                default:
                    //[p1][p2][data1][data2]
                    escritorDisco1.write(paridadXOR+"");
                    escritorDisco2.write(paridad2+"");
                    if(!texto1.equals("null"))escritorDisco3.write(texto1+"");
                    if(!texto2.equals("null"))escritorDisco4.write(texto2+"");
                    break;
            }
        }catch(Exception e){
        
        }
    }
    
    private class Bloque{
        String texto1;
        String texto2;
        String paridad1;
        String paridad2;
        boolean disco1;
        boolean disco2;
        boolean disco3;
        boolean disco4;

        public Bloque(String texto1, String texto2, String paridad1, String paridad2) {
            this.disco1 =true;
            this.disco2 =true;
            this.disco3 =true;
            this.disco4 =true;
            this.texto1 = texto1;
            this.texto2 = texto2;
            this.paridad1 = paridad1;
            this.paridad2 = paridad2;
        }

        public boolean isDisco1() {
            return disco1;
        }

        public void setDisco1(boolean disco1) {
            this.disco1 = disco1;
        }

        public boolean isDisco2() {
            return disco2;
        }

        public void setDisco2(boolean disco2) {
            this.disco2 = disco2;
        }

        public boolean isDisco3() {
            return disco3;
        }

        public void setDisco3(boolean disco3) {
            this.disco3 = disco3;
        }

        public boolean isDisco4() {
            return disco4;
        }

        public void setDisco4(boolean disco4) {
            this.disco4 = disco4;
        }

        public String getTexto1() {
            return texto1;
        }

        public void setTexto1(String texto1) {
            this.texto1 = texto1;
        }

        public String getTexto2() {
            return texto2;
        }

        public void setTexto2(String texto2) {
            this.texto2 = texto2;
        }

        public String getParidad1() {
            return paridad1;
        }

        public void setParidad1(String paridad1) {
            this.paridad1 = paridad1;
        }

        public String getParidad2() {
            return paridad2;
        }

        public void setParidad2(String paridad2) {
            this.paridad2 = paridad2;
        }
        
    }
    
    //SE ENVIA EL NOMBRE : archivo
    public void generarArchivo(String nombreArchivo, VentanaPrincipalController vp){
        VentanaPrincipalController ventana = vp;
        String _pathPrograma = new File ("").getAbsolutePath ();
        int posicionParidad=0;
        boolean flag =true,fdisco1=true,fdisco2=true,fdisco3=true,fdisco4=true;
        
        try {
            FileReader disco1 = new FileReader(_pathPrograma+"/RAID6/"+nombreArchivo+"/"+"disco1.txt");
            BufferedReader buffer1 = new BufferedReader(disco1);
            FileReader disco2 = new FileReader(_pathPrograma+"/RAID6/"+nombreArchivo+"/"+"disco2.txt");
            BufferedReader buffer2 = new BufferedReader(disco2);
            FileReader disco3 = new FileReader(_pathPrograma+"/RAID6/"+nombreArchivo+"/"+"disco3.txt");
            BufferedReader buffer3 = new BufferedReader(disco3);
            FileReader disco4 = new FileReader(_pathPrograma+"/RAID6/"+nombreArchivo+"/"+"disco4.txt");
            BufferedReader buffer4 = new BufferedReader(disco4);
            while(flag){
                Bloque bloque = obtenerLectura(posicionParidad, buffer1, buffer2,
                                               buffer3, buffer4);
                String linea1 =bloque.getTexto1();
                String linea2 =bloque.getTexto2();
                String paridad1 =bloque.getParidad1();
                String paridad2 =bloque.getParidad2();
                
                //COMPARAR LAS PARIDADES CON LAS CALCULADAS
                 byte[] arr1 ={},arr2={};
                if(linea1!="") { arr1= linea1.getBytes(StandardCharsets.UTF_8);}
                if(linea2!=""){arr2 = linea2.getBytes(StandardCharsets.UTF_8);}
                
                byte resultado = this.sumarBytes(arr1, arr2);
                //se obtiene el bit de paridad para las tres lineas
                short calculoP1 = this.calcularParidadXOR((long)resultado);
                int calculoP2 = 0; //FUNCION PARA CALCULAR PRIORIDAD !!!!!!!!!!!!!!!!!!!!!!!!!!
                
                if(paridad1.equals(calculoP1+"") && paridad2.equals(
                                calculoP2+"")){
                 ventana.modificarTextArea(linea1+linea2);    
                }
                posicionParidad++;
                if(!bloque.isDisco1())fdisco1=false;
                if(!bloque.isDisco2())fdisco2=false;
                if(!bloque.isDisco3())fdisco3=false;
                if(!bloque.isDisco4())fdisco4=false;
                if(!fdisco1 && !fdisco2 && !fdisco3 && !fdisco4) flag =false;
            }
        }
        catch (FileNotFoundException ex) {
        }
    }
    
    private Bloque obtenerLectura(int posicion,BufferedReader buffer1,BufferedReader buffer2,
                             BufferedReader buffer3,BufferedReader buffer4){
        String texto1 ="",texto2="",paridad1="",paridad2="";
        boolean disco1=true,disco2=true,disco3=true,disco4=true;
        int c1,c2;
        try {
            switch(posicion%4){
                case 0:
                    //[p2][][][p1]
                    for(int i=0;i<10;i++){
                        c1 = buffer2.read();
                        if(c1!=-1) texto1+=(char) c1;
                        c2 = buffer3.read();
                        if(c2!=-1) texto2+=(char)c2;
                        if(c1==-1) disco2=false;
                        if(c2==-1) disco3=false;
                    }
                    paridad1+=(char)buffer4.read();
                    paridad2+=(char)buffer1.read();
                    break;
                case 1:
                    //[][][p1][p2]
                    for(int i=0;i<10;i++){
                        c1 = buffer1.read();
                        if(c1!=-1) texto1+=(char) c1;
                        c2 = buffer2.read();
                        if(c2!=-1) texto2+=(char)c2;
                        if(c1==-1) disco1=false;
                        if(c2==-1) disco2=false;
                    }
                    paridad1+=(char)buffer3.read();
                    paridad2+=(char)buffer4.read();
                    break;
                case 2:
                    //[][p1][p2][]
                    for(int i=0;i<10;i++){
                        c1 = buffer1.read();
                        if(c1!=-1) texto1+=(char) c1;
                        c2 = buffer4.read();
                        if(c2!=-1) texto2+=(char)c2;
                        if(c1==-1) disco1=false;
                        if(c2==-1) disco4=false;
                    }
                    paridad1+=(char)buffer2.read();
                    paridad2+=(char)buffer3.read();
                    break;
                default:
                    //[p1][p2][][]
                    for(int i=0;i<10;i++){
                        c1 = buffer3.read();
                        if(c1!=-1) texto1+=(char) c1;
                        c2 = buffer4.read();
                        if(c2!=-1) texto2+=(char)c2;
                        if(c1==-1) disco3=false;
                        if(c2==-1) disco4=false;
                    }
                    paridad1+=(char)buffer1.read();
                    paridad2+=(char)buffer2.read();
                    break;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(RAID6.class.getName()).log(Level.SEVERE, null, ex);
        }
        Bloque b = new Bloque(texto1, texto2, paridad1, paridad2);
        b.setDisco1(disco1);
        b.setDisco2(disco2);
        b.setDisco3(disco3);
        b.setDisco4(disco4);
        return b;
    }
    
    //NOSE LO QUE HACEE
    public static short calcularParidadXOR(long x) {

        short result = 0;
        while (x != 0) {
          result ^= (x & 1);

          x >>>= 1;

        }
        return result;

      }
}