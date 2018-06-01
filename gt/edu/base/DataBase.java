package gt.edu.base;

import java.io.*;
import java.util.*;

public class DataBase {
	
	
	final static String entidades = "entidades.txt";//almacenar entidades y sus propiedades
	final static String atributo = "atributos.txt";
	
	RandomAccessFile raf = null;
	static Scanner captura = new Scanner(System.in);
	File fconfig = new File(entidades);
	
	String guardar = null;
	int pruebas = 0;//para index entero
	float pruebas2 = 0;//para index decimal
	boolean alerta = false; //para determinar si es posible el cast
	long pos = 0; //posicion para leer datos
	byte op;//variable de ingresar a menu 
	private List<Entidad> listEntidades = new ArrayList<>();

	public static void main(String[] args) {
		
		DataBase Menu = new DataBase();
		if(Menu.validarEntidad()) {
			Menu.listarEntidades();
			Menu.mPrincipal();
		}else {
			Menu.mPrincipal();
		}
		
		
	}
	
	public void mPrincipal(){
		
		
		do {
			System.out.println("\n\t Bienvenido");
			System.out.println("\t Base de datos dinamica \n");
			
			if(validarEntidad()) {
				System.out.println("1. Registrar Entidad ");
				System.out.println("2. Listar Entidades ");
				System.out.println("3. Ingresar Registro");
				System.out.println("4. Eliminar Entidad y/o Registros");
				System.out.println("5. Salir ");
			}else {
				System.out.println("\tNo ha creado ninguna Entidad ");
				System.out.println("\tSeleccione 1. Registrar Entidad \n\n");
				
				System.out.println("1. Registrar Entidad ");
			}
			
			
			alerta = false;
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
			case 1://registra entidad
				if(validarEntidad()) {
					agregarEntidad();
				}else {
					agregarEntidad();
				}
				break;
			case 2://crea un nuevo registro
				if (listEntidades.size() >=0) {
					for (Entidad entidad : listEntidades) {
						System.out.println("Indice: " + entidad.getIndice());
						System.out.println("Nombre: " + entidad.getNombre());
						System.out.println("Cantidad de atributos: " + entidad.getCantidad());
					}
				} else {
					System.out.println("No hay entidades registradas");
				}
				break;
			case 3:
				int indice = 0;
				while (indice < 1 || indice > listEntidades.size()) {
					for (Entidad entidad : listEntidades) {
						System.out.println(" "+entidad.getIndice() + "  ----------  " + entidad.getNombre());
					}
					alerta = false;
					do{
						try{
							System.out.println("Ingrese opcion");
							indice = Integer.parseInt(captura.nextLine());
							alerta = true;
						}catch(NumberFormatException e){
							alerta = false;
						}
					}while(!alerta);
				}
				menuRegistros(indice);
				break;
			}
				
		}while(op!=5);
		
	}
	
	public boolean validarEntidad() {
		return fconfig.exists();
	}
	
	public void agregarEntidad() {
		
		String name = null;
		long inicio = 0;
		int longitud = 0;
		int cantidad = 0;
		
		Entidad entidad = new Entidad();
		entidad.setIndice(listEntidades.size() + 1);
		Valores atributos = new Valores();

		try {
			raf = new RandomAccessFile(atributo,"rw");
			raf.seek(raf.length());
			inicio = raf.getFilePointer();
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				raf.close();
			} catch (IOException e) {

			}
		}
		entidad.setInicio(inicio);
		System.out.println("Ingrese el nombre de la entidad: ");
		do {
			name = captura.nextLine();
			longitud = name.length();
			if(longitud < 2 | longitud > 25) {
				System.out.println("Lingitud del nombre no es valido [2-25]");
			}else {
				if (name.contains(" ")) {
					System.out.println("El nombre no puede contener espacios, sustituya por guion bajo (underscore)");
					longitud = 0;
				}
			}
		}while(longitud<2 | longitud>25);
		entidad.setNombre(name);
		entidad.setRuta(name+".txt");
		
		System.out.println("Ingrese el numero de atributos de la entidad");
		alerta = false;
		do{
			try{
				cantidad = Integer.parseInt(captura.nextLine());
				alerta = true;
			}catch(NumberFormatException e){
				alerta = false;
			}
		}while(!alerta);				
		entidad.setCantidad(cantidad);			
		for(int i=0;i<cantidad;i++) {		
			if(i==0) {
				System.out.println("\tRecuerde el primer campo sera su campo llave  ");
				System.out.println("\tSe recomienda usar un tipo de dato numerico ");
			}
			System.out.println("\nIngrese el nombre del atributo No. "+(i+1)+": ");
			do {
				name = captura.nextLine();
				longitud = name.length();
				if(longitud < 2 | longitud > 25) {
					System.out.println("Lingitud del nombre no es valido [2-25]");
				}else {
					if (name.contains(" ")) {
					System.out.println("El nombre no puede contener espacios, sustituya por guion bajo (underscore)");
						longitud = 0;
					}
				}			
			}while(longitud<2 | longitud>25);
			atributos.setNombre(name);	
			System.out.print("\nSeleccione tipo de dato: ");
			mostrar();
			do{
				try{
					atributos.setDato(Short.parseShort(captura.nextLine()));
					alerta = true;
				}catch(NumberFormatException e){
					alerta = false;
				}
			}while(!alerta);
			
			try {
				raf = new RandomAccessFile(atributo,"rw");
				raf.seek(raf.length());
				raf.writeUTF(atributos.getNombre());
				raf.writeShort(atributos.getDato());	
			} catch (IOException e) {		
				e.printStackTrace();
			}finally {
				try {
					raf.close();
				} catch (IOException e) {

				}
			}
						
		}
		saltoLinea(atributo);
		
		try {
			raf = new RandomAccessFile(entidades,"rw");
			raf.seek(raf.length());
			raf.writeInt(entidad.getIndice());
			raf.writeUTF(entidad.getNombre());
			raf.writeUTF(entidad.getRuta());
			raf.writeInt(entidad.getCantidad());
			raf.writeLong(entidad.getInicio());
		}catch(IOException f) {
			
		}finally {
			try {
				raf.close();
			} catch (IOException e) {

			}
		}
		saltoLinea(entidades);
		listEntidades.add(entidad);
		System.out.println("Se guardo la siguiente entidad: ");
		mostrarEntidad(entidad);
	}
	
	public void mostrar() {
		
		System.out.println("\n1. Alfanumerico ");//string
		System.out.println("2. Entero ");//int
		System.out.println("3. Decimal ");//float
		System.out.println("4. Verdadero/Falso ");//boolean
		System.out.println("5. Fecha dd/mm/aaaa ");//fecha de nuestra clase fecha
		System.out.println("6. Moneda Q ");// float con signo de Quetzal
		
	}
	
	public void mostrarEntidad(Entidad entidad) {
		
		System.out.println("Indice: " + entidad.getIndice());
		System.out.println("Nombre: " + entidad.getNombre());
		System.out.println("Cantidad de atributos: " + entidad.getCantidad());

	}
	
	public void saltoLinea(String n) {
		
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(n,true));
	        bw.newLine();
	        bw.flush();
	        bw.close();
		}catch(IOException e){
	            
	    }	
	}//
	
	public void listarEntidades() {
		pos = 0;
		int indice = 0;
		Entidad entidad;
		String nombre = null;
		String ruta = null;
		try {
			raf = new RandomAccessFile(entidades,"rw");
			do {
				raf.seek(pos);
				entidad = new Entidad();
				indice = raf.readInt();
				nombre = raf.readUTF();
				ruta = raf.readUTF();
				entidad.setIndice(indice);
				entidad.setNombre(nombre);
				entidad.setRuta(ruta);
				entidad.setCantidad(raf.readInt());
				entidad.setInicio(raf.readLong());
				listEntidades.add(entidad);
				pos = raf.getFilePointer()+2;
			}while(true);
		} catch (IOException e) {

		}finally {
			try {
				raf.close();
			} catch (IOException e) {
				
			}
		}

	}
	
	public void menuRegistros(int n) {
		String name = null;
		int cantidad = 0;
		long position = 0;//se asigna posicion pues cambia
		long rec = 0;//almacena posicion guardad en entidads 
		op = 0;
		int contador = 0;//valida junto con la cantidad los atributos
		File datos = null;
		for (Entidad e : listEntidades) {
			if (n == e.getIndice()) {
				name = e.getNombre();
				guardar = e.getRuta();
				cantidad = e.getCantidad();
				rec = e.getInicio();
				break;
			}
		}
		String index = "index_"+guardar;
		DataInputStream din = null;
		long posi = 0;
		do {
			System.out.println("\n\n\tEntidad: "+name);
			System.out.println("\n1. Nuevo registro ");
			System.out.println("2. Buscar registro ");
			System.out.println("3. Listar registros ");
			System.out.println("4. Eliminar datos ");
			System.out.println("5. Ver configuracion entidad "+name);
			System.out.println("6. Regresar a menu principal ");
			
			alerta = false;
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
			case 1://nuevo registro de entidad
				position = rec;
				contador = 0;
				System.out.println("Recuerde el primer campo sera su campo llave \n");
				try {
					raf = new RandomAccessFile(atributo,"rw");
					try {
						do {
							raf.seek(position);
							String campo = raf.readUTF();//nombre del campo
							short dato = raf.readShort();//identificador del tipo de dato
							position = raf.getFilePointer();
							System.out.print("Ingrese "+campo+": ");
							verConfig(dato);//llamada a funcion con valor dato
							if((contador==0)&((dato==2)|(dato==3))) {
								System.out.println("Se creo index");
								escribirIndex(dato);
							}
							contador++;
						}while(contador<cantidad);
					} catch (IOException f) {
					
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						raf.close();
					} catch (IOException e1) {

					}
				}
				saltoLinea(guardar);
				break;
			case 2://busca por campo llave
				datos = new File("index_"+guardar);
				position = rec;
				posi = 0;
				if(datos.exists()) {
					try {
						raf = new RandomAccessFile(atributo,"rw");
						try {					
							raf.seek(position);
							String campo = raf.readUTF();//nombre del campo
							short dato = raf.readShort();//tipo de dato
							System.out.println("Para ejecutar la busqueda ");
							System.out.print("Ingrese: "+campo+"\n");
							switch(dato) {
							case 2:
								alerta  = false;
								int en = 0;
								do {
									try {
										en = Integer.parseInt(captura.nextLine());
										alerta = true;
									}catch(NumberFormatException e) {
										alerta = false;
									}
								}while(!alerta);
								int entero = 0;
								try {
									din = new DataInputStream(new FileInputStream(index));
									try {
										do {
											entero = din.readInt();
											posi = din.readLong();
											if(en == entero) {
												break;
											}
										}while(true);
										din.close();
									} catch (IOException e) {
		
									}
								} catch (FileNotFoundException e) {
									
								}
								leerRegistro(rec,posi,guardar);//se llama a la funcion para leer el registro completo
								break;
							case 3:
								break;
							}
						} catch (IOException f) {
						
						}
						raf.close();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}else {
					System.out.println("No hay registros");
				}
				break;
			case 3://lista registros aqui comienza
				datos = new File(guardar);
				RandomAccessFile raf2 = null; 
				boolean fin = false;
				pos = 0;
				long leer = 0;
				position = rec;
				contador = 0;
				if(datos.exists()) {
					System.out.println("Sus registros son: ");
					do {
						System.out.print("\n");
						try {
							raf = new RandomAccessFile(atributo,"r");
							try {
								do {
									raf.seek(position);
									String no = raf.readUTF();//nombre del campo
									short da = raf.readShort();//identificador del tipo de dato
									position = raf.getFilePointer();
									try {
										raf2 = new RandomAccessFile(guardar,"r");
										raf2.seek(leer);
										switch(da) {
										case 1:
											System.out.println(no+" :  "+raf2.readUTF());
											leer = raf2.getFilePointer();
											break;
										case 2:
											System.out.println(no+" :  "+raf2.readInt());
											leer = raf2.getFilePointer();
											break;
										case 3:
											System.out.println(no+" :  "+raf2.readFloat());
											leer = raf2.getFilePointer();
											break;
										case 4:
											System.out.println(no+" :  "+raf2.readBoolean());
											leer = raf2.getFilePointer();
											break;
										case 5:
											System.out.println(no+" : "+raf2.readUTF());
											leer = raf2.getFilePointer();
											break;
										case 6:
											System.out.println(no+" : Q."+raf2.readFloat());
											leer = raf2.getFilePointer();
											break;
										}
									}catch(IOException ef){
										fin = true;
									}finally {
										raf2.close();
									}
								}while(true);
							} catch (IOException f) {
								position = 0;
								leer +=2;
							}	
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
								raf.close();
							} catch (IOException e) {
								
							}
						}
					}while(!fin);
				}else {
					System.out.println("No hay registros");
				}
				break;
			case 4://elimina los registros incluyendo el index
				datos = new File(guardar);
				if(datos.exists()) {
					int selec = 0;
					String ind = "index_"+guardar;
					File inde = new File(ind);
					System.out.println("Presione 1, para eliminar registros/datos ");
					System.out.println("Presione 0, para Cancelar ");
					alerta = false;
					do {
						try {
							selec = Integer.parseInt(captura.nextLine());
							alerta = true;
						}catch(NumberFormatException e) {
							alerta = false;
						}
					}while(!alerta);
					if(selec ==1) {
						datos.delete();
						inde.delete();
					}
				}else {
					System.out.println("No ha ingresado registros");
				}
				break;
			case 5:
				//modificar registro
				datos = new File("index_"+guardar);
				position = rec;
				posi = 0;
				if(datos.exists()) {
					try {
						raf = new RandomAccessFile(atributo,"rw");
						try {					
							raf.seek(position);
							String campo = raf.readUTF();//nombre del campo
							short dato = raf.readShort();//tipo de dato
							System.out.println("Para ejecutar la busqueda ");
							System.out.print("Ingrese: "+campo+"\n");
							switch(dato) {
							case 2:
								alerta  = false;
								int en = 0;
								do {
									try {
										en = Integer.parseInt(captura.nextLine());
										alerta = true;
									}catch(NumberFormatException e) {
										alerta = false;
									}
								}while(!alerta);
								int entero = 0;
								try {
									din = new DataInputStream(new FileInputStream(index));
									try {
										do {
											entero = din.readInt();
											posi = din.readLong();
											if(en == entero) {
												break;
											}
										}while(true);
										din.close();
									} catch (IOException e) {
		
									}
								} catch (FileNotFoundException e) {
									
								}
								leerRegistro(rec,posi,guardar);//se llama a la funcion para leer el registro completo
								break;
							case 3:
								break;
							}
						} catch (IOException f) {
						
						}
						raf.close();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}else {
					System.out.println("No hay registros");
				}
				break;
			case 6:
				//regresa al menu principal
				break;
			}
			
		}while(op!=6);
	}

	public void verConfig(short d) {//recibe el identificador d dato y evalua para solcitar el dato 
		
		switch(d) {
		case 1://		
			System.out.println("\tTipo de dato Alfanumerico");
			String texto = captura.nextLine();
			texto(texto);
			break;
		case 2:
			System.out.println("\tTipo de dato Entero");
			alerta = false;
			int entero = 0;
			do{
				try{
					entero = Integer.parseInt(captura.nextLine());
					alerta = true;
				}catch(NumberFormatException e){
					alerta = false;
				}
			}while(!alerta);	
			entero(entero);
			break;
		case 3:
			System.out.println("\tTipo de dato Decimal");
			alerta = false;
			float decimal = 0;
			do{
				try{
					decimal = Float.parseFloat(captura.nextLine());
					alerta = true;
				}catch(NumberFormatException e){
					alerta = false;
				}
			}while(!alerta);
			decimal(decimal);
			break;
		case 4:
			boolean sino = false;
			alerta = false;
			System.out.println("\tIngrese tipo de dato F/V ");
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
			System.out.println("\tTipo de dato Fecha (31/12/200)");
			do{
				try {
					System.out.println("dia: ");
					dia = Integer.parseInt(captura.nextLine());
					if((dia>=1)&(dia<=31)) {
						alerta = true;
					}
				}catch(NumberFormatException e) {
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
					alerta = false;	
				}
			}while(!alerta);
			
			alerta = false;
			do{
				try {
					System.out.println("año: ");
					anio = Integer.parseInt(captura.nextLine());
					if(anio>0) {
						alerta = true;
					}
				}catch(NumberFormatException e) {
					alerta = false;
				}
			}while(!alerta);
			
			Fecha f1 = new Fecha(dia,mes,anio);
			String fecha = f1.toString();
			texto(fecha);
			
			break;
		case 6:
			System.out.println("\tTipo de dato Q Moneda");
			alerta = false;
			float deci = 0; 
			do{
				try {
					deci=Float.parseFloat(captura.nextLine());
					alerta=true;
				}catch(NumberFormatException e) {
					alerta = false;
				}
			}while(!alerta);
			
			decimal(deci);
			break;
		}
	}

	public void fecha(String fecha) {
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(guardar,true));
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
			dout = new DataOutputStream(new FileOutputStream(guardar,true));
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
			raf = new RandomAccessFile(guardar, "rw");
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
		
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(guardar, "rw");
			raf.seek(raf.length());
			pos = raf.getFilePointer();
			pruebas2 = decimal;
			raf.writeFloat(decimal);
		} catch (IOException e) {		
			e.printStackTrace();
		}finally {
			try {
				raf.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public void sino(boolean sino) {
		
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(new FileOutputStream(guardar,true));
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
	
	public void escribirIndex(int d) {
		
		DataOutputStream dout = null;
		String index = "index_"+guardar;
		switch(d) {
		case 2:
			try {
				dout = new DataOutputStream(new FileOutputStream(index,true));
				dout.writeInt(pruebas);
				dout.writeLong(pos);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					dout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case 3:
			try {
				dout = new DataOutputStream(new FileOutputStream(index,true));
				dout.writeFloat(pruebas2);
				dout.writeLong(pos);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					dout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}
	
	private void leerRegistro(long rec, long posi, String guardar) {
		boolean fin = false;
		RandomAccessFile raf2 = null;
		System.out.println("Sus registro es: ");
		do {
			System.out.print("\n");
			try {
				raf = new RandomAccessFile(atributo,"r");
				try {
					do {
						raf.seek(rec);
						String no = raf.readUTF();//nombre del campo
						short da = raf.readShort();//identificador del tipo de dato
						rec = raf.getFilePointer();
						try {
							raf2 = new RandomAccessFile(guardar,"r");
							raf2.seek(posi);
							switch(da) {
							case 1:
								System.out.println(no+" :  "+raf2.readUTF());
								posi = raf2.getFilePointer();
								break;
							case 2:
								System.out.println(no+" :  "+raf2.readInt());
								posi = raf2.getFilePointer();
								break;
							case 3:
								System.out.println(no+" :  "+raf2.readFloat());
								posi = raf2.getFilePointer();
								break;
							case 4:
								System.out.println(no+" :  "+raf2.readBoolean());
								posi = raf2.getFilePointer();
								break;
							case 5:
								System.out.println(no+" : "+raf2.readUTF());
								posi = raf2.getFilePointer();
								break;
							case 6:
								System.out.println(no+" : Q."+raf2.readFloat());
								posi = raf2.getFilePointer();
								break;
							}
						}catch(IOException ef){
							
						}finally {
							raf2.close();
						}
					}while(true);
				} catch (IOException f) {
					fin = true;
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					raf.close();
				} catch (IOException e) {
					
				}
			}
		}while(!fin);
		
	}
	
}	