package gt.edu.base;

import java.io.*;
import java.util.*;


public class DataBase {

	final static String declaracion="C:/Users/Rosy Perez/Desktop/java/definicion.txt";//variables paniagua
	final static String datos="C:/Users/Rosy Perez/Desktop/java/datos2.txt";
	/*final static String declaracion="definicion.txt";//variable otros
	final static String datos="datos2.txt";*/
	long pos = 0;
	static Scanner captura = new Scanner(System.in);
	File fconfig = new File(declaracion);
	
	boolean alerta = false; //valida si es numero
	String n;
	short d;
	byte op;//variable de ingresar a menu principal
	List<Valores> lista = new ArrayList<Valores>();
	
	public static void main(String[] args) {
		
		DataBase principal = new DataBase();
		principal.menu();
		
	}
	
	public void menu(){
		
		do {
			System.out.println("\n\t Bienvenido");
			System.out.println("\t Base de datos dinamica \n");
			
			if(validarFile()) {
				System.out.println("Archivo principal ya creado ");
				System.out.println(" ");
			}else {
				System.out.println("Archivo de configuracion, no creado ");
				System.out.println("Seleccione 1. crear archivo de configuracion ");
			}
			
			System.out.println("\n1. Crear archivo de configuracion ");
			System.out.println("2. Ingresar registro ");
			System.out.println("3. Buscar registro ");
			System.out.println("4. Listar registros ");
			System.out.println("5. Salir ");
			
			//op=captura.nextByte();
			do{
				try{
					System.out.println("Ingrese opcion");
					op = Byte.parseByte(captura.nextLine());
					alerta = true;
				}catch(NumberFormatException e){
					System.out.println( "Opcion incorrecta");
					alerta = false;
				}
			}while(!alerta);
			
			switch(op) {
				case 1://crea archivo de configuracion
					System.out.println(".....");
					if(validarFile()) {
						System.out.println("Ocurrio un error");
					}else {
						registrarCampos();
					}
					break;
				case 2://crea un nuevo registro
					if(validarFile()) {
						leerConfig();
					}else {
						System.out.println("Archivo de definicion no existe ");
					}
					break;
				case 3:
					if(validarFile()) {
						
					}else {
						System.out.println("Archivo de definicion no existe ");
					}
					break;
				case 4://listar registros,
					if(validarFile()) {
						leerLista();
					}else {
						System.out.println("Archivo de definicion no existe ");
					}
					break;
				case 5:
					System.out.println("Vulve pronto..... ");
					break;
				default:
					System.out.println("Opcion no valida ");	
			}
			
		}while(op!=5);

	}
	
	public boolean validarFile() {
		File f = new File(declaracion);
		return f.exists();
	}
	
	public void registrarCampos() {

		System.out.println("Ingrese la cantidad de campos ");
		int n = 0;//captura.nextInt();
		do{
			try{
				System.out.println("");
				n = Integer.parseInt(captura.nextLine());
				alerta = true;
			}catch(NumberFormatException e){
				System.out.println( "Dato incorrecto");
				alerta = false;
			}
		}while(!alerta);
		
		Valores x = new Valores();
		for(int i=0;i<n;i++) {
			
			lista.clear();
			
			System.out.println("\n Ingrese el nombre del campo: ");
			x.setNombre(captura.nextLine());
			System.out.println("\n Seleccione tipo de dato: ");
			mostrar();

			do{
				try{
					System.out.println("");
					x.setDato(Short.parseShort(captura.nextLine()));
					alerta = true;
				}catch(NumberFormatException e){
					System.out.println( "Dato incorrecto");
					alerta = false;
				}
			}while(!alerta);
			
			lista.add(x);
			escribirConfig();
		}
		
	}
	
	public void mostrar() {
		
		System.out.println("\n1. Alfanumerico ");//string
		System.out.println("2. Entero ");//int
		System.out.println("3. Decimal ");//float
		System.out.println("4. Verdadero/Falso ");//boolean
		System.out.println("5. Fecha dd/mm/aaaa ");//fecha de nuestra clase fecha
		System.out.println("6. Moneda Q ");// float con signo de Quetzal
		
	}
	
	public void escribirConfig() {
		
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(declaracion,true));
			for (Valores x : lista) {
				dout.writeUTF(x.getNombre());
				dout.writeShort(x.getDato());
				
			}
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	public void leerConfig() {//lee el archivo de configuracion 

		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(declaracion));
			try {
				do {
					n = dain.readUTF();//nombre del campo
					d = dain.readShort();//identificador del tipo de dato
					if(op==2) {// segun la opcion seleccionada en el menu principal evalua si es escribir o leer
						System.out.print("Ingrese "+n+": ");
						verConfig(d);//se llama la funcion para escribir se pasa el identificador d
					}
				}while(true);
			} catch (IOException f) {
			
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void verConfig(short d) {//recibe el identificador d y evalua para solcitar el dato 
		
		switch(d) {
		case 1://		
			System.out.println("Tipo de dato Alfanumerico");
			String texto = captura.nextLine();
			texto(texto);
			break;
		case 2:
			System.out.println("Tipo de dato Entero");
			alerta = false;
			int entero = 0;
			do{
				try{
					System.out.println("");
					entero = Integer.parseInt(captura.nextLine());
					alerta = true;
				}catch(NumberFormatException e){
					System.out.println("Dato incorrecto");
					alerta = false;
				}
			}while(!alerta);	
			entero(entero);
			break;
		case 3:
			System.out.println("Tipo de dato Decimal");
			alerta = false;
			float decimal = 0;
			do{
				try{
					System.out.println("");
					decimal = Float.parseFloat(captura.nextLine());
					alerta = true;
				}catch(NumberFormatException e){
					System.out.println( "Dato incorrecto");
					alerta = false;
				}
			}while(!alerta);
			decimal(decimal);
			break;
		case 4:
			boolean sino = false;
			alerta = false;
			System.out.println("Ingrese tipo de F/V");
			do {
				String bool = captura.nextLine();
				
				if(bool.equals("V")||bool.equals("v")) {
					sino = true;
					alerta = true;
				}else if(bool.equals("F")||bool.equals("f")){
					sino = true;
					alerta = true;
				}else {
					alerta = false;
				}
				
			}while(!alerta);
			sino(sino);
			
			break;
		case 5:
			
			alerta = false;
			int dia = 0;
			int mes = 0;
			int anio = 0;
			System.out.println("Tipo de dato Fecha (31/12/200)");
			
			do{
				try {
					System.out.println("dia: ");
					dia = Integer.parseInt(captura.nextLine());
					if((dia>=1)&(dia<=31)) {
						alerta = true;
					}
				}catch(NumberFormatException e) {
					System.out.println("Dato incorrecto");
					alerta = false;
					
				}
			}while(!alerta);
			
			alerta = false;
			
			do{
				try {
					System.out.println("mes: ");
					mes = Integer.parseInt(captura.nextLine());

					if((mes>=1)&(mes<=12)){
						alerta = true;
					}
					
				}catch(NumberFormatException e) {
					System.out.println("Dato incorrecto");
					alerta = false;	
				}
			}while(!alerta);
			
			alerta = false;
			
			do{
				try {
					System.out.println("anio: ");
					anio = Integer.parseInt(captura.nextLine());
					if(anio>0) {
						alerta = true;
					}
				}catch(NumberFormatException e) {
					System.out.println("Dato incorrecto");
					alerta = false;
					
				}
			}while(!alerta);
			
			
			Fecha f1 = new Fecha(dia,mes,anio);
			String fecha = f1.toString();
			
			texto(fecha);
			
			break;
		case 6:
			System.out.println("Tipo de dato Q Moneda");
			alerta = false;
			float deci = 0; 
			do{
				try {
					System.out.println("");
					deci=Float.parseFloat(captura.nextLine());
					alerta=true;
				}catch(NumberFormatException e) {
					System.out.println("Dato incorrecto");
					alerta = false;
					
				}
			}while(!alerta);
			
			decimal(deci);
		}
		
	}

	public void fecha(String fecha) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeUTF(fecha);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
	}
	
	public void texto(String texto) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeUTF(texto);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	public void entero(int entero) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeInt(entero);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	public void decimal(float decimal) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeFloat(decimal);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}	
	}
	
	public void sino(boolean sino) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeBoolean(sino);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}		
	}
	
	public void leerLista() {
		pos = 0;
		boolean fin = false;
		RandomAccessFile raf = null;
		
		do {
			System.out.println("\n");
			try {
				DataInputStream dain = new DataInputStream(new FileInputStream(declaracion));
				try {
					do {
						n = dain.readUTF();//nombre del campo
						d = dain.readShort();//identificador del tipo de dato
							File f = new File(datos);
							raf = new RandomAccessFile(f,"r");
							raf.seek(pos);
							try {
								switch(d) {
								case 1:
									System.out.println(n+" : "+raf.readUTF());
									pos = raf.getFilePointer();
									break;
								case 2:
									System.out.println(n+" : "+raf.readInt());
									pos = raf.getFilePointer();
									break;
								case 3:
									System.out.println(n+" : "+raf.readFloat());
									pos = raf.getFilePointer();
									break;
								case 4:
									System.out.println(n+" : "+raf.readBoolean());
									pos = raf.getFilePointer();
									break;
								case 5:
									System.out.println(n+" : "+raf.readUTF());
									pos = raf.getFilePointer();
									break;
								case 6:
									System.out.println(n+" : "+"Q."+raf.readFloat());
									pos = raf.getFilePointer();
									break;
								}
							}catch(IOException ef) {
								fin = true;
							}
					}while(true);
				} catch (IOException f) {
				}
				dain.close();
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(!fin);
	}
}	