import java.util.Scanner;

public class Principal {
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
		} while(error);
		s.nextLine();
		return Integer.toString(precio);
	}
	
	// Valida el formato ingresado de la matrícula
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
		} while(error);
		return input;
	}
	
	// Busca si la matrícula ya está registrada
	public static int existeMatricula(String[][] multas, String matriculaBuscada) {
		int indiceEncontrada = -1;
		int i = 0;
		do {
			if (multas[i][0] == matriculaBuscada) {
				indiceEncontrada = i;
			}
			i++;
		} while(indiceEncontrada == -1);
		return indiceEncontrada;
	}
	
	// Busca e imprime información de una multa asociada a la matrícula ingresada por el usuario
	public static void buscarMulta(Scanner s, String[][] multas) {
		String matriculaBuscada = validarMatricula(s);
		int indiceMultaBuscada = existeMatricula(multas, matriculaBuscada);
		if (indiceMultaBuscada != -1) {
			System.out.println("---- Datos Multa matrícula " + matriculaBuscada + " ----");
			System.out.println("Marca y modelo: " + multas[indiceMultaBuscada][1]);
			System.out.println("Propietario: " + multas[indiceMultaBuscada][2]);
			System.out.println("Descripción de la multa: " + multas[indiceMultaBuscada][3]);
			System.out.println("Valor de la multa (en pesos): $" + multas[indiceMultaBuscada][4]);
		} else {
			System.out.println("No existe ninguna multa asociada a la matrícula ingresada");
		}
	}
	
	// Crear una multa
	public static String[] ingresarMulta(Scanner s, String[][] multas) {
		String[] multa = new String[6];
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
		multa[5] = "no";
		return multa;
	}
	
	public static void agregarMulta(Scanner s, String[][] multas, int cantMultas) {
		String[] multa = ingresarMulta(s, multas);
		multas[cantMultas] = multa;
	}
	
	public static void generarAccion(Scanner s, String[][] multas, int codigoOperacion, int cantMultas) {
		switch (codigoOperacion) {
		case 1:
			// consultar multas de un veh. por matricula
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
			break;
		case 5:
			break;
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[][] multas = new String[200][6];
		int cantMultas = 0;
		// solicitar multas iniciales
		// menu(cantMultas);
		s.close();
	}	
}
