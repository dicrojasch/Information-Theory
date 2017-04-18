import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import giovynet.nativelink.SerialPort;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;


public class Servidor {
	final int PUERTO=5000;
	ServerSocket sc;
	Socket so;
	DataOutputStream salida;
	String mensajeRecibido;
	Scanner in;
	SerialPort free;
	Parameters settings;
	List<String> portList;
	BufferedReader entrada;
	Com com;
	Thread shutdownThread = new Thread(){
		public void run(){
			// Aqui se pone el código que queremos:
			// es util hacer cosas como cerrar ficheros, sockets, etc.
			try{
				System.out.println("Saliendo...");
				so.close();
				sc.close();
				in.close();
				com.close();
			}catch(Exception e ){
				System.out.println("Error: "+e.getMessage());
			}
		}
	};
	
//SERVIDOR
	public void initServer() throws Exception{
		in = new Scanner(System.in);
		String number = "";    
		free = new SerialPort();
	    portList = free.getFreeSerialPort();
		
		int portCom = -1;
		if( portList.size() <= 0 ){
			System.out.println("No hay puertos disponibles");
			return;		
		}
		System.out.println("Lista de pueros disponibles");
		for(int i = 0; i < portList.size(); i++) {			
	        System.out.println(i+". "+portList.get(i));
	    }
		boolean salirPuerto = true;
		while( salirPuerto ){
			System.out.println("¿Que puerto va a ultilizar? [numero]");
			number = in.next();			
			try{
				portCom = Integer.parseInt(number);
				if( portCom < portList.size() &&  portCom >= 0 ) salirPuerto = false;
				else System.out.println("("+number+")"+" no esta en la opciones, ingrese una opcion valida");
			}catch( NumberFormatException nfe ){
				System.out.println("("+number+")"+" no es valido, ingrese una opcion valida");	
			}				
		}
		settings = new Parameters();
		settings.setPort( portList.get(portCom) );
		settings.setBaudRate(Baud._9600);
		com = new Com(settings);
		try{
			sc = new ServerSocket(PUERTO );/* crea socket servidor que escuchara en puerto 5000*/
			so=new Socket();
			
			Runtime.getRuntime().addShutdownHook( shutdownThread );			
			
			while(true){
				System.out.println("Esperando una conexión:");
				so = sc.accept();
				//Inicia el socket, ahora esta esperando una conexión por parte del cliente
				System.out.println("Un cliente se ha conectado.");
				//Canales de entrada y salida de datos
				entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));
				//salida = new DataOutputStream(so.getOutputStream());
				System.out.println("Confirmando conexion al cliente....");
				//salida.writeUTF("Conexión exitosa...n envia un mensaje :D");
				//Recepcion de mensaje
				mensajeRecibido = entrada.readLine();
				try{					
					int opcion = -1;					
					opcion = Integer.parseInt(mensajeRecibido);
					//System.out.println(mensajeRecibido);
					com.sendSingleData( opcion );
					System.out.println("se envio >> " + opcion ); 
					
				}catch( NumberFormatException nfe ){
					System.out.println("la opcion recibida no es valida");
					com.close();
					
				}
				//salida.writeUTF("Se recibio tu mensaje.n Terminando conexion...");
				//salida.writeUTF("Gracias por conectarte, adios!");
				System.out.println("Cerrando conexión...");
				so.close();//Aqui se cierra la conexión con el cliente
				//System.out.println("Desea Salir: (Si/No)");
				//number = in.next();
				//if( number.equals("s") || number.equals("si") || number.equals("S") || number.equals("SI") || number.equals("Si") ) break;
			}
			/*System.out.println("Saliendo...");
			sc.close();
			in.close();
			com.close();*/
		}catch(Exception e ){
			System.out.println("Error: "+e.getMessage());
			com.close();
			sc.close();
			in.close();
		}
	}
	public static void main(String[] args) throws Exception{
		Servidor server = new Servidor();
		server.initServer();		
	}
}