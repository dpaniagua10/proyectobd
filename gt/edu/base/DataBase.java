package gt.edu.base;

import java.io.*;
import java.util.*;

public class DataBase {

	final static String declaracion="C:/Users/Rosy Perez/Desktop/java/datos1.txt";
	final static String datos="C:/Users/Rosy Perez/Desktop/java/datos2.txt";
	static Scanner captura = new Scanner(System.in);
	File fconfig = new File(declaracion);
	List<Valores> lista = new ArrayList<Valores>();
	
	public static void main(String[] args) {
		
		//DataBase.menu();//por ser un metodo estatico
		DataBase principal = new DataBase();
		principal.menu();
		
	}
	
	public void menu(){
		byte op;
		
		do {
			
			System.out.println("\t Bienvenido");
			System.out.println("\t Base de datos dinamica \n");
			
			if(validarFile()) {
				System.out.println("Archivo principal ya creado ");
				System.out.println(" ");
			}else {
				System.out.println("Archivo de configuracion, no creado ");
				System.out.println("Seleccione 1. crear archivo de configuracion ");
			}
			
			System.out.println("\n1. Crear archivo de configuracion ");
			System.out.println("2. Ingresar datos ");
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
						escribirConfig();
					}
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
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
		
		Valores x = new Valores();
		System.out.println("Ingrese la cantidad de campos ");
		int n=captura.nextInt();
		for(int i=0;i<n;i++) {
			captura.nextLine();
			System.out.println("Ingrese el nombre del campo: ");
			x.setNombre(captura.nextLine());
			System.out.println("Seleccione tipo de dato: ");
			mostrar();
			x.setDato(captura.nextShort());
			
			lista.add(x);
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
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(declaracion,true));
			for (Valores x : lista) {
				dout.writeUTF(x.getNombre());
				dout.writeShort(x.getDato());
			}
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				dout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
