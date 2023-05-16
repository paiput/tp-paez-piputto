import java.util.Scanner;

public class Principal {
	// funcion generica
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

	public static int validarCantidadMultasIniciales(Scanner s) {
		int cantMultas = 0;
		boolean error = false;
		do {
			try {
				error = false;
				System.out.print("\nIngrese cantidad de multas iniciales: ");
				cantMultas = s.nextInt();
				if (cantMultas < 0) {
					System.out.println("Error: No puede ingresar una cantidad negativa de multas");
					error = true;
				} else if (cantMultas > 200) {
					System.out.println("Error: No se pueden ingresar más de 200 multas");
					error = true;
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Error: La cantidad de multas no puede contener caracteres");
				s.nextLine();
			}
		} while (error);
		s.nextLine();
		return cantMultas;
	}

	// Valida el codigo de operacion ingresado
	public static int validarCodigoOperacion(Scanner s) {
		int codigoOperacion = 0;
		boolean error = false;
		do {
			try {
				error = false;
				System.out.print("\nIngrese el código de operación: ");
				codigoOperacion = s.nextInt();
				if (codigoOperacion < 1 || codigoOperacion > 5) {
					System.out.println("Error: El código de operación ingresado no válido");
					error = true;
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Error: El código de operación es numérico");
				s.nextLine();
			}
		} while (error);
		s.nextLine();
		return codigoOperacion;
	}

	// Valida el precio ingresado para una multa
	public static String validarPrecioMatricula(Scanner s) {
		int precio = 0;
		boolean error = false;
		do {
			try {
				error = false;
				System.out.print("\nIngrese precio de la multa: $");
				precio = s.nextInt();
				if (precio < 1) {
					System.out.println("Error: La multa no puede valer menos de $1");
					error = true;
				}
			} catch (Exception e) {
				error = true;
				System.out.println("Error: El precio no puede contener caracteres");
				s.nextLine();
			}
		} while (error);
		s.nextLine();
		return Integer.toString(precio);
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
	public static int existeMatricula(String[][] multas, String matriculaBuscada) {
		int indiceEncontrada = -1;
		int i = 0;
		while (indiceEncontrada == -1 && multas[i][0] != null) { // REVISAR
			
			System.out.println(" -> matricula: " + multas[i][0]);
			
			if (multas[i][0].equals(matriculaBuscada)) {
				indiceEncontrada = i;
				System.out.println("LA ENCONTRO");
			}
			i++;
		}
		/*do {
			System.out.println(" -> matricula: " + multas[i][0]);
			if (multas[i][0] != null && multas[i][0].equals(matriculaBuscada)) {
				indiceEncontrada = i;
				System.out.println("LA ENCONTRO");
			}
			i++;
		} while (indiceEncontrada == -1);*/
		return indiceEncontrada;
	}

	// 3. Busca e imprime información de una multa asociada a la matrícula ingresada
	// por el usuario
	public static void buscarMulta(Scanner s, String[][] multas) {
		String matriculaBuscada = validarMatricula(s);
		int indiceMultaBuscada = existeMatricula(multas, matriculaBuscada);
		if (indiceMultaBuscada != -1) {
			System.out.println("---- Datos Multa matrícula nro " + matriculaBuscada + " ----");
			System.out.println("Marca y modelo: " + multas[indiceMultaBuscada][1]);
			System.out.println("Propietario: " + multas[indiceMultaBuscada][2]);
			System.out.println("Descripción de la multa: " + multas[indiceMultaBuscada][3]);
			System.out.println("Valor de la multa (en pesos): $" + multas[indiceMultaBuscada][4]);
		} else {
			System.out.println("No existe ninguna multa asociada a la matrícula ingresada");
		}
	}

	// 1. Retorna datos solicitados para el ingreso de una multa, para su posterior
	// agregado
	public static String[] ingresarMulta(Scanner s, String[][] multas) {
		String[] multa = new String[5];
		String matricula = validarMatricula(s);

		while (existeMatricula(multas, matricula) != -1) {
			System.out.println("Ya hay una multa registrada a la matrícula " + matricula + ", ingrese otra matrícula");
			matricula = validarMatricula(s);
		}

		// quizas habria que validar [1] [2] y [3]
		multa[0] = matricula;
		System.out.print("Ingrese marca y modelo del vehículo: ");
		multa[1] = s.nextLine();
		System.out.print("Ingrese nombre y apellido del propietario: ");
		multa[2] = s.nextLine();
		System.out.print("Ingrese descripción de la multa: ");
		multa[3] = s.nextLine();
		multa[4] = validarPrecioMatricula(s);
		return multa;
	}

	// 4. Agrega la multa con los datos previamente ingresados
	public static void agregarMulta(Scanner s, String[][] multas, int cantMultas) {
		String[] multa = ingresarMulta(s, multas);
		multas[cantMultas] = multa;
	}

	// 5. Paga la multa de un vehículo asociado a una matícula solicitada
	public static void pagarMulta(Scanner s, String[][] multas, int cantMultas) {
		String matricula = validarMatricula(s);
		int indiceMatricula = existeMatricula(multas, matricula);
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
	}

	// 6. Calcula el total de multas pendientes de pago
	public static void calcularTotalMultasPendientes(String[][] multas, int cantMultas) {
		System.out.println("---- Multas pendientes -----");
		int valorTotal = 0;
		for (int i = 0; i < cantMultas; i++) {
			valorTotal += Integer.parseInt(multas[i][4]);
		}
		System.out.println("Hay " + cantMultas + " pendientes de pago, el valor total es de $" + valorTotal);
	}

	// 7
	public static int mostrarMenu(Scanner s) {
		int codigoOperacion = 0;
		System.out.print("Ingrese código de operación:\n1.- Consultar Multas\n2.- Agregar Multa\n3.- Pagar Multa\n");
		System.out.print("4.- Mostar total de multas pendientes de pago y su valor\n5.- Salir\n.- ");
		codigoOperacion = validarCodigoOperacion(s);
		return codigoOperacion;
	}

	// 8
	public static void generarAccion(Scanner s, String[][] multas, int codigoOperacion, int cantMultas) {
		switch (codigoOperacion) {
		case 1:
			// consultar multas de un veh. por matricula
			buscarMulta(s, multas);
			break;
		case 2:
			agregarMulta(s, multas, cantMultas);
			cantMultas++;
			break;
		case 3:
			// pagar multa
			break;
		case 4:
			// mostrar multas pendientes
			calcularTotalMultasPendientes(multas, cantMultas); // VER NOMBRE DE LA FUNCION
			break;
		case 5:
			break;
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[][] multas = new String[200][5];
		int cantMultas = 0;

		// solicitar multas iniciales
		System.out.println("Ingrese cuantas multas desea cargar inicialmente: ");
		cantMultas = validarCantidadMultasIniciales(s);
		for (int i = 0; i < cantMultas; i++) {
			agregarMulta(s, multas, cantMultas);
		}

		int codigoOperacion = 0;
		do {
			codigoOperacion = mostrarMenu(s);
			generarAccion(s, multas, codigoOperacion, cantMultas);
		} while (codigoOperacion != 5);

		s.close();
	}
}
