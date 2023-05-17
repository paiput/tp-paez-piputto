import java.util.Scanner;

public class Principal {
	// Función genérica para validar el ingreso de número enteros dentro de un cierto rango
	public static int ingresarEntero(Scanner s, int min, int max, String msg, String errorMsg) {
		int n = 0;
		boolean error = false;
		do {
			try {
				error = false;
				System.out.print(msg);
				n = s.nextInt();
				if (n < min || n > max) {
					System.out.println(errorMsg);
					error = true;
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Error: El dato solicitado no debe contener caracteres");
				s.nextLine();
			}
		} while (error);
		s.nextLine();
		return n;
	}

	// Función genérica para validar que no se ingresen strings vacíos
	public static String ingresarString(Scanner s, String msg) {
		String str = "";
		boolean error = false;
		do {
			error = false;
			System.out.print(msg);
			str = s.nextLine();			
			if (str.length() == 0) {
				System.out.println("Error: No puede ingresar una cadena de texto vacía");
				error = true;
			}
		} while(error);
		return str;
	}

	// 2. Valida el formato ingresado de la matrícula
	public static String validarMatricula(Scanner s) {
		String input = "";
		boolean error = false;
		do {
			try {
				error = false;
				System.out.print("\nIngrese número de matrícula: ");
				input = s.nextLine();
				Integer.parseInt(input);
				if (input.length() != 6) {
					System.out.println("Error: La matrícula debe ser de 6 dígitos");
					error = true;
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Error: La matrícula no puede contener caracteres");
			}
		} while (error);
		return input;
	}

	// Busca si la matrícula ya está registrada
	public static int indiceMatricula(String[][] multas, String matriculaBuscada) {
		int indiceEncontrada = -1;
		int i = 0;

		while (indiceEncontrada == -1 && multas[i][0] != null) {
			if (multas[i][0].equals(matriculaBuscada)) {
				indiceEncontrada = i;
			} else {
				i++;
			}
		}
		return indiceEncontrada;
	}

	// 3. Busca e imprime información de una multa asociada a la matrícula ingresada
	// por el usuario
	public static void buscarMulta(Scanner s, String[][] multas, int cantMultas) {
		if (cantMultas > 0) {
			String matriculaBuscada = validarMatricula(s);
			int indiceMultaBuscada = indiceMatricula(multas, matriculaBuscada);
			if (indiceMultaBuscada != -1) {
				System.out.println("---- Datos Multa matrícula nro " + matriculaBuscada + " ----");
				System.out.println("Marca y modelo: " + multas[indiceMultaBuscada][1]);
				System.out.println("Propietario: " + multas[indiceMultaBuscada][2]);
				System.out.println("Descripción de la multa: " + multas[indiceMultaBuscada][3]);
				System.out.println("Valor de la multa (en pesos): $" + multas[indiceMultaBuscada][4]);
			} else {
				System.out.println("No existe ninguna multa asociada a la matrícula ingresada");
			}			
		} else {
			System.out.println("No se pueden buscar multas ya que no hay multas registradas en el sistema");
		}
	}

	// 1. Retorna datos solicitados para el ingreso de una multa, para su posterior agregado
	public static String[] ingresarMulta(Scanner s, String[][] multas) {
		String[] multa = new String[5];
		String matricula = validarMatricula(s);

		while (indiceMatricula(multas, matricula) != -1) {
			System.out.println("Ya hay una multa registrada a la matrícula " + matricula + ", ingrese otra matrícula");
			matricula = validarMatricula(s);
		}

		multa[0] = matricula;
		multa[1] = ingresarString(s, "Ingrese marca y modelo del vehículo: ");
		multa[2] = ingresarString(s, "Ingrese nombre y apellido del propietario: ");
		multa[3] = ingresarString(s, "Ingrese descripción de la multa: ");
		multa[4] = Integer.toString(ingresarEntero(s, 1, 1000000, "Ingrese el valor de la multa (en pesos): $", "Error: El valor de la multa debe ser de entre $1 y $1000000"));
		return multa;
	}

	// 4. Agrega la multa con los datos previamente ingresados
	public static void agregarMulta(Scanner s, String[][] multas, int cantMultas) {
		String[] multa = ingresarMulta(s, multas);
		multas[cantMultas] = multa;
	}

	// 5. Paga la multa de un vehículo asociado a una matícula solicitada
	public static void pagarMulta(Scanner s, String[][] multas, int cantMultas) {
		System.out.println("---- Pagar multa ----");
		if (cantMultas > 0) {
			String matricula = validarMatricula(s);
			int indiceMatricula = indiceMatricula(multas, matricula);
			final int COLUMNAS = multas[0].length; // Para que después no tenga que calcularlo en cada vuelta de i
			if (indiceMatricula != -1) {
				for (int i = indiceMatricula; i < cantMultas; i++) {
					for (int j = 0; j < COLUMNAS; j++) {
						multas[i][j] = multas[i + 1][j];
					}
				}
				for (int i = 0; i < COLUMNAS; i++) {
					multas[cantMultas - 1][i] = "";
				}
				System.out.println("Multa pagada correctamente");
			} else {
				System.out.println("La matrícula ingresada no tiene ninguna multa registrada");
			}
		} else {
			System.out.println("No se puede pagar una multa ya que no hay multas registradas en el sistema");
		}
	}

	// 6. Calcula el total de multas pendientes de pago
	public static void calcularTotalMultasPendientes(String[][] multas, int cantMultas) {
		System.out.println("\n---- Multas pendientes -----");
		if (cantMultas > 0) {
			int valorTotal = 0;
			for (int i = 0; i < cantMultas; i++) {
				valorTotal += Integer.parseInt(multas[i][4]);
			}
			System.out.println("Hay " + cantMultas + " pendientes de pago, el valor total es de $" + valorTotal);			
		} else {
			System.out.println("No se puede calcular el total de multas pendientes ya que no hay multas registradas en el sistema");
		}
	}

	// 7. Muestra el menú del sistema
	public static int mostrarMenu(Scanner s) {
		int codigoOperacion = 0;
		System.out.println("\nIngrese código de operación:\n 1 - Consultar Multas\n 2 - Agregar Multa\n 3 - Pagar Multa\n 4 - Mostar total de multas pendientes de pago y su valor\n 5 - Salir");
		codigoOperacion = ingresarEntero(s, 1, 6, "-> ", "Error: El código de operación ingresado es inexistente");
		return codigoOperacion;
	}

	// 8. Genera una acción en base al código de operación seleccionado en el menú
	public static int generarAccion(Scanner s, String[][] multas, int codigoOperacion, int cantMultas) {
		switch (codigoOperacion) {
		case 1:
			buscarMulta(s, multas, cantMultas);
			break;
		case 2:
			agregarMulta(s, multas, cantMultas);
			cantMultas++;
			break;
		case 3:
			pagarMulta(s, multas, cantMultas);
			cantMultas--;
			break;
		case 4:
			calcularTotalMultasPendientes(multas, cantMultas); 
			break;
		case 5:
			System.out.println("Sistema de multas cerrado");
			break;
		}
		return cantMultas;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[][] multas = new String[200][5];
		int cantMultas = 0;

		cantMultas = ingresarEntero(s, 0, 200, "Ingrese cuantas multas desea cargar inicialmente: ", "Error: La cantidad iniciales no puede ser menor que 0 o mayor que 200");
		for (int i = 0; i < cantMultas; i++) {
			System.out.print("\n -- Multa " + (i + 1) + " -- ");
			agregarMulta(s, multas, i);
		}

		int codigoOperacion = 0;
		do {
			codigoOperacion = mostrarMenu(s);
			cantMultas = generarAccion(s, multas, codigoOperacion, cantMultas);
		} while (codigoOperacion != 5);

		s.close();
	}
}
