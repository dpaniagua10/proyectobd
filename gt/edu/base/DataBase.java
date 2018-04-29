package gt.edu.base;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DataBase {

	final static String declaracion="C:/Users/R&D/Desktop/java/datos1.txt";
	final static String datos="C:/Users/R&D/Desktop/java/datos2.txt";
	static Scanner captura = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		DataBase.menu();//por ser un metodo estatico

	}
	
	public static void menu(){
		byte op;
		
		do {
			
			System.out.println("\t Bienvenido");
			System.out.println("\t Base de datos dinamica \n");
			
			if(validarFile()) {
				System.out.println("Archivo principal ya creado ");
				System.out.println(" ");
			}else {
				System.out.println("Archivo principal no creado");
				System.out.println("Seleccione crear principal ");
			}
			
			System.out.println("\n1. Crear archivo principal ");
			System.out.println("2. Ingresar datos ");
			System.out.println("3. Buscar registro ");
			System.out.println("4. Listar registros ");
			System.out.println("5. Salir ");
			
			op=captura.nextByte();
			
			switch(op) {
				case 1:
					System.out.println(".....");
					if(crearPrincipal()) {
						registrarCampos();
					}else {
						System.out.println("Ocurrio un error");
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
	
	public static boolean validarFile() {
		File f = new File(declaracion);
		return f.exists();
	}

	public static boolean crearPrincipal() {
		File f = new File(declaracion);
		try {
			f.createNewFile();
			System.out.println("\tArchivo creado exitosamente \n");
			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	public static void registrarCampos() {
		System.out.println("Ingrese la cantidad de campos ");
		int n=captura.nextInt();
		for(int i=0;i<n;i++) {
			
			System.out.println("Ingrese el nombre del campo ");
			
			
		}
		
	}
}
