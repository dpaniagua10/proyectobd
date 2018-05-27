package gt.edu.base;

import java.io.*;
import java.util.*;


public class DataBase {
	
	
	final static String declaracion = "definicion.txt";
	final static String datos = "datos2.txt";//variables daniel 2
	final static String index = "indexbase.txt";

	
	static Scanner captura = new Scanner(System.in);
	File fconfig = new File(declaracion);
	
	int pruebas = 0;//para pruebas
	int contador = 0;//para crear index
	boolean alerta = false; //para determinar si es posible la iteraccion
	String n;//se almacena el nombre del campo
	short d;//identificador para ver configuracion
	long pos = 0; //posicion para leer datos
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
			System.out.println("5. Eliminar datos y/o configuracion ");
			System.out.println("6. Ver configuracion ");
			System.out.println("7. Salir ");
			
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
						saltoLinea();
					}else {
						System.out.println("Archivo de definicion no existe ");
					}
					break;
				case 3:
					if(validarFile()) {
						System.out.println("Ingrese codigo");
						alerta = false;
						int codigo = 0;
						do{
							try{
								codigo = Integer.parseInt(captura.nextLine());
								alerta = true;
							}catch(NumberFormatException e){
								System.out.println("Dato incorrecto");
								alerta = false;
							}
						}while(!alerta);	
						buscar(codigo);
					}else {
						System.out.println("Archivo de configuracion no existe ");
					}
					break;
				case 4://listar registros almacenados
					if(validarFile()) {
						if(validarDatos()) {
							leerLista();
						}else {
							System.out.println("No existen datos guardados ");
						}
					}else {
						System.out.println("Archivo de configuracion no existe ");
					}
					break;
				case 5:
					if(validarFile()) {
						eliminar();
					}else {
						System.out.println("Archivo de configuracion no existe ");
					}
					break;
				case 6:
					System.out.println("\tSu configuracion es: \n");
					printConfig();
					break;
				case 7:
					System.out.println("Vulve pronto..... ");
					break;
				default:
					System.out.println("Opcion no valida ");	
			}
			
		}while(op!=7);

	}
	
	public boolean validarFile() {
		File f = new File(declaracion);
		return f.exists();
	}
	
	public boolean validarDatos() {
		File e = new File(datos);
		return e.exists();
	}
	
	public void registrarCampos() {

		System.out.println("Ingrese la cantidad de campos ");
		int n = 0;
		do{
			try{
				System.out.println("");
				n = Integer.parseInt(captura.nextLine());
				if(n>=1) {
					alerta = true;
				}else {
					alerta = false;
				}
			}catch(NumberFormatException e){
				System.out.println( "Dato incorrecto");
				alerta = false;
			}
		}while(!alerta);
		
		Valores x = new Valores();
		
		for(int i=0;i<n;i++) {
			
			lista.clear();
			
			if(i==0) {
				System.out.println("Recuerde el primer campo sera su campo clave  ");
				System.out.println("Se recomienda usar un tipo de dato numerico \n");
			}
			
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
		}//termina ciclo for
		
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
	
	public void buscar(int codigo) {
		
		DataInputStream dain = null;
		try {
			dain = new DataInputStream(new FileInputStream(index));
			try {
				do {
					int c = dain.readInt();//nombre del campo
					long l = dain.readLong();//identificador del tipo de dato
					//System.out.println(c);
					//System.out.println(l);
					
					if(c==codigo) {
						
						System.out.println(c);
						System.out.println(l);
						buscaCodigo(l);
					}
				}while(true);
			} catch (IOException f) {
			
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void printConfig() {
		int conta = 1;
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(declaracion));
			try {
				do {
					n = dain.readUTF();//nombre del campo
					d = dain.readShort();//identificador del tipo de dato
					System.out.print("Su campo "+conta+" es :"+n+" ");
					conta ++;
					switch(d) {
					case 1:
						System.out.println("  Tipo de dato Alfanumero");
						break;
					case 2:
						System.out.println("  Tipo de dato Entero");
						break;
					case 3:
						System.out.println("  Tipo de dato Decimal");
						break;
					case 4:
						System.out.println("  Tipo de dato Verdadero/Falso");
						break;
					case 5:
						System.out.println("  Tipo de dato Fecha dd/mm/aaaa");
						break;
					case 6:
						System.out.println("  Tipo de dato Moneda Q.");
						break;
					}
				}while(true);
			} catch (IOException f) {
			
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void leerConfig() {//lee el archivo de configuracion 

		contador = 0;
		System.out.println("Recuerde el primer campo sera su campo clave ");
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(declaracion));
			try {
				do {
					n = dain.readUTF();//nombre del campo
					d = dain.readShort();//identificador del tipo de dato
					System.out.print("Ingrese "+n+": ");
					verConfig(d);//se llama la funcion para escribir se pasa el identificador d
					if(contador==0) {
						escribirIndex();
					}
					contador ++;
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
					//System.out.println("Dato incorrecto");
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
					//System.out.println("Dato incorrecto");
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
					//System.out.println("Dato incorrecto");
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
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeUTF(fecha);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				dout.close();
			} catch (IOException e) {
				
			}
		}
		
	}
	
	public void texto(String texto) {
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeUTF(texto);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				dout.close();
			} catch (IOException e) {
				
			}
		}
		
	}
	
	public void entero(int entero) {
		
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(datos, "rw");
			raf.seek(raf.length());
			pos = raf.getFilePointer();
			pruebas = entero;
			raf.writeInt(entero);
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				raf.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public void decimal(float decimal) {
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeFloat(decimal);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				dout.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public void sino(boolean sino) {
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeBoolean(sino);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				dout.close();
			} catch (IOException e) {
		
			}
		}
	}
	
	public void buscaCodigo(long l) {
		
		pos = l;
		boolean fin = false;
		RandomAccessFile raf = null;
		DataInputStream dain = null;
		
		do {
			System.out.println("\n");
			try {
				dain = new DataInputStream(new FileInputStream(declaracion));
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
								
							}finally{
								raf.close();
							}
					}while(true);
				} catch (IOException f) {
					//pos += 2;//prueba para leer salto de linea, si funciono
					fin = true;
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					dain.close();
				} catch (IOException e) {
					
				}
			}
		}while(!fin);
	}
	
	public void leerLista() {
		
		pos = 0;
		boolean fin = false;
		RandomAccessFile raf = null;
		DataInputStream dain = null;
		
		do {
			System.out.println("\n");
			try {
				dain = new DataInputStream(new FileInputStream(declaracion));
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
							}finally{
								raf.close();
							}
					}while(true);
				} catch (IOException f) {
					pos += 2;//prueba para leer salto de linea, si funciono
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					dain.close();
				} catch (IOException e) {
					
				}
			}
		}while(!fin);
	}
	
	public void escribirIndex() {
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(index,true));
			dout.writeInt(pruebas);
			dout.writeLong(pos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void eliminar() {
		byte selec = 0;
		alerta = false;
		File f = new File(datos);
		
		do {
			System.out.println("\n\t1. Eliminar configuracion ");
			System.out.println("\t2. Eliminar datos guardados");
			System.out.println("\t3. Eliminar configuracion y datos ");
			System.out.println("\t4. Regresar a menu principal");
			
			do{
				try{
					System.out.println("Ingrese su opcion ");
					selec = Byte.parseByte(captura.nextLine());
					alerta = true;
				}catch(NumberFormatException e){
					alerta = false;
				}
			}while(!alerta);
			
			
			switch(selec) {
			case 1:
				fconfig.delete();
				System.out.println("Configuracion eliminada");
				break;
			case 2:	
				//System.out.println(f.exists());
				if(validarDatos()) {
					f.delete();
					System.out.println("Datos guardados eliminados");
				}else {
					System.out.println("No hay datos guardados");
				}
				break;
			case 3:
				if(validarDatos()) {
					f.delete();
					fconfig.delete();
					System.out.println("Datos y Configuracion eliminados");
				}else {
					fconfig.delete();
					System.out.println("No hay datos guardados");
					System.out.println("Configuracion eliminada");
				}
				break;
			case 4:
				
				break;
			default:
				System.out.println("Opcion incorrecta");
					
			}
		}while(selec!=4);
		
	}
	
	public void saltoLinea() {
		
		//String f = "C:/Users/R&D/Desktop/java/datos2.txt";
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(datos,true));
	        bw.newLine();
	        bw.flush();
	        bw.close();
		}catch(IOException e){
	            //System.out.println("Error E/S: "+e);
	    }
		
	}
}	