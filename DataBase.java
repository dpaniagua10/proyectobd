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
}