<<<<<<< HEAD
package gt.edu.base;

import java.io.*;
import java.util.*;


public class DataBase {

	final static String declaracion="C:/Users/Rosy Perez/Desktop/java/datos1.txt";
	final static String entidad = "C:/Users/Rosy Perez/Desktop/java/";
	final static String datos="C:/Users/Rosy Perez/Desktop/java/datos2.txt";//variable entidad
	static Scanner captura = new Scanner(System.in);
	File fconfig = new File(declaracion);
	
	String n;
	short d;
	byte op;
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
			
			op=captura.nextByte();
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
					leerConfig();
					break;
				case 3:
					break;
				case 4://listar registros, no se me ocurre como
					leerConfig();
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
		
		/*System.out.println("Ingrese el nombre de la entidad: ");
		String entidad = captura.nextLine();
		*/
		
		System.out.println("Ingrese la cantidad de campos ");
		int n=captura.nextInt();
		Valores x = new Valores();
		for(int i=0;i<n;i++) {
			
			lista.clear();
			captura.nextLine();
			System.out.println("\n Ingrese el nombre del campo: ");
			x.setNombre(captura.nextLine());
			System.out.println("\n Seleccione tipo de dato: ");
			mostrar();
			x.setDato(captura.nextShort());
			lista.add(x);
	
			escribirConfig();
		}
		
	}
	
	public void mostrar() {
		
		System.out.println("1. Alfanumerico ");//string
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
					d = dain.readShort();//identificador de la variable
					
					if(op==2) {// segun la opcion seleccionada en el menu principal evalua si es escribir o leer
						System.out.print("Ingrese "+n+": ");
						//System.out.println("Ingrese ");
						verConfig(d);//se llama la funcion para escribir se pasa el identificador d
					}else if(op==4){
						//verConfig(d);
						try {
							dain = new DataInputStream(new FileInputStream(datos));
							try {
								do {
									switch(d) {
									case 1:
										String nn = dain.readUTF();
										System.out.println(nn);
										break;
									case 2:
										int nm = dain.readInt();
										System.out.println(nm);
										break;
									case 3:
										float nw = dain.readFloat();
										System.out.println(nw);
										break;
									case 4:
										boolean nq = dain.readBoolean();
										System.out.println(nq);
										break;
									case 5:
										String ns = dain.readUTF();
										System.out.println(ns);
										break;
									case 6:
										float nd = dain.readFloat();
										System.out.println("Q "+nd);
										break;
									default:
										break;
									}
								}while(true);
								
							} catch (IOException f) {
								//f.printStackTrace();
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								dain.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}
					
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void verConfig(short d) {//recibe el identificador d y evalua para solcitar el dato 
		
		switch(d) {
		case 1://
			if(op==2) {
				System.out.println("Tipo de dato Alfanumerico");
				captura.nextLine();
				captura.nextLine();
				String texto = captura.nextLine();
				texto(texto);
			}else if(op==4){
				//leeTexto();
			}
			break;
		case 2:
			if(op==2) {
				System.out.println("Tipo de dato Entero");
				int entero = captura.nextInt();
				entero(entero);
			}else if(op==4) {
				//leeEntero();
			}
			break;
		case 3:
			if(op==2) {
				System.out.println("Tipo de dato Decimal");
				float decimal = captura.nextFloat();
				decimal(decimal);
			}else if(op==4){
				//leeDecimal();
			}
			break;
		case 4:
			if(op==2) {
				System.out.println("Tipo de dato F/V");
				captura.nextLine();
				String log = captura.nextLine();
				boolean sino = false;
				
				if(log.equals("v")) {
					sino = true;
					sino(sino);
				}else if(log.equals("f")) {
					sino(sino);
				}
			}else if(op==4) {
				//leeSino();
			}
			
			break;
		case 5:
			if(op==2) {
				System.out.println("Tipo de dato Fecha (31/12/2000): ");
				int dia = captura.nextInt();
				int mes = captura.nextInt();
				int anio = captura.nextInt();
				Fecha f1 = new Fecha(dia,mes,anio);
				String fecha = f1.toString();
				fecha(fecha);
			}else if(op==4) {
				//leeTexto();
			}
			break;
		case 6:
			if(op==2) {
				System.out.println("Ingrese tipo de dato Moneda Q");
				float moneda = captura.nextFloat();
				decimal(moneda);
				break;
			}else if(op==4) {
				//leeDecimal();
			}
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
	
	private void sino(boolean sino) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeBoolean(sino);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}		
	}
	
	/*public void leeTexto() {
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(datos));
			try {
				do {
					String no = dain.readUTF();
					System.out.println(no);
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			//dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void leeEntero() {
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(datos));
			try {
				do {
					int da = dain.readInt();
					System.out.println(da);
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			//dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void leeDecimal() {
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(datos));
			try {
				do {
					float da = dain.readFloat();
					System.out.println(da);
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			//dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void leeSino() {
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(datos));
			try {
				do {
					boolean da = dain.readBoolean();
					System.out.println(da);
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			//dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}*/
}
=======
package gt.edu.base;

import java.io.*;
import java.util.*;

public class DataBase {

	final static String declaracion="datos1.txt";
	final static String datos="datos2.txt";
	static Scanner captura = new Scanner(System.in);
	File fconfig = new File(declaracion);
	
	String n;
	short d;
	byte op;
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
			
			op=captura.nextByte();
			switch(op) {
				case 1:
					System.out.println(".....");
					if(validarFile()) {
						System.out.println("Ocurrio un error");
					}else {
						registrarCampos();
					}
					break;
				case 2:
					leerConfig();
					break;
				case 3:
					System.out.println("Ingrese un campo con tipo de dato Entero");
					buscar(captura.nextInt());
					break;
				case 4:
					leerConfig();
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
		int n=captura.nextInt();
		Valores x = new Valores();
		for(int i=0;i<n;i++) {
			
			lista.clear();
			captura.nextLine();
			System.out.println("Ingrese el nombre del campo: ");
			x.setNombre(captura.nextLine());
			System.out.println("Seleccione tipo de dato: ");
			mostrar();
			x.setDato(captura.nextShort());
			lista.add(x);
	
			escribirConfig();
		}
		
	}
	
	public void mostrar() {
		
		System.out.println("1. Alfanumerico ");
		System.out.println("2. Entero ");
		System.out.println("3. Decimal ");
		System.out.println("4. Verdadero/Falso ");
		System.out.println("5. Fecha dd/mm/aaaa ");
		System.out.println("6. Moneda Q ");
		
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
					n = dain.readUTF();
					d = dain.readShort();
					if(op==2) {// segun la opcion seleccionada en el menu principal evalua si es escribir o leer
						System.out.print("Ingrese "+n+": ");
						//System.out.println("Ingrese ");
						verConfig(d);//se llama la funcion para escribir se pasa el identificador d
					}else if(op==4){
						verConfig(d);
					}
					
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void verConfig(short d) {//recibe el identificador d y evalua para solcitar el dato 
		
		switch(d) {
		case 1://
			if(op==2) {
				captura.nextLine();
				String texto = captura.nextLine();
				texto(texto);
			}else if(op==4){
				System.out.println("\tRegistros Almacenados");
				leeTexto();
			}
			break;
		case 2:
			if(op==2) {
				System.out.println("Tipo de dato Entero");
				int entero = captura.nextInt();
				entero(entero);
			}else if(op==4) {
				
			}
			
			break;
		case 3:
			float decimal = captura.nextFloat();
			decimal(decimal);
			break;
		case 4:
			boolean sino = captura.nextBoolean();
			sino(sino);
			break;
		case 5:
			break;
		case 6:
			break;
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
	
	private void sino(boolean sino) {
		try {
			DataOutputStream dout = new DataOutputStream(new FileOutputStream(datos,true));
			dout.writeBoolean(sino);
			dout.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}		
	}
	
	public void leeTexto() {
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(datos));
			try {
				do {
					System.out.println("|------------------------------------|\n");
					System.out.println("\t"+n+": "+dain.readUTF());
					System.out.println("\t"+dain.readInt());
					System.out.println("\t"+dain.readFloat());
				
				}while(true);
			} catch (IOException f) {
				System.out.println("\tFinalizacion de Registros");
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void leeEntero() {
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(datos));
			try {
				do {
					int da = dain.readInt();
					System.out.println(da);
				}while(true);
			} catch (IOException f) {
				//f.printStackTrace();
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void buscar(int entero) {
		int da;
		String nombre;
		float decimal;
		try {
			DataInputStream dain = new DataInputStream(new FileInputStream(declaracion));
			try {
				do {
				     da = dain.readInt();
				     nombre = dain.readUTF();
				     decimal = dain.readFloat();
					if(entero == da) {
						System.out.println("\t"+nombre);
						System.out.println("\t"+da);
						System.out.println("\t"+decimal);
					}
				}while(da != entero);
			} catch (IOException f) {
				System.out.println("Registro no existe");
			}
			dain.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
}
>>>>>>> b49459a460ba319d89bf2e9f819bd7da8bd0a63d
