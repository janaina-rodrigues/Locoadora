import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LTPUtils {
	private static Scanner leia = new Scanner(System.in);
	private static DecimalFormat real = new DecimalFormat("R$ #,##0.00");
	private static DecimalFormat dolar = new DecimalFormat("US$ #,##0.00");
	private static DecimalFormat perc = new DecimalFormat("0.00'%'");

	public static int getInt(String texto) {
		int valor = 0;
		boolean validar;
		do {
			try {
				validar = true;
				System.out.println(texto);
				valor = leia.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Você inseriu uma letra");
				validar = false;
				limparBuffer();
			}
		} while (!validar);

		return valor;
	}

	public static int getIntPositivo(String texto) {

		int valor;

		do {
			try {
				valor = getInt(texto);
				if (valor < 0) {
					System.err.println("O valor informado deve ser positivo!");
				}
			} catch (InputMismatchException e) {
				System.err.println("Você inseriu uma letra");
				valor = -1; // Para for�ar voltar no loop
				limparBuffer();
			}
		} while (valor < 0);

		return valor;
	}

	public static float getFloatPositivo(String texto) {

		float valor;

		do {
			try {
				valor = getFloat(texto);
				if (valor < 0) {
					System.err.println("O valor informado deve ser positivo!");
				}
			} catch (InputMismatchException e) {
				System.err.println("Você inseriu uma letra");
				valor = -1; // Para for�ar voltar no loop
				limparBuffer();
			}
		} while (valor < 0);

		return valor;
	}

	public static float getFloat(String texto) {
		float valor = 0;
		boolean validar;
		do {
			try {
				validar = true;
				System.out.println(texto);
				valor = leia.nextFloat();
			} catch (InputMismatchException e) {
				System.err.println("Você inseriu uma letra");
				validar = false;
				limparBuffer();
			}
		} while (!validar);

		return valor;
	}

	public static String formatacaoReal(float valor) {
		return real.format(valor);
	}

	public static String formatacaoDolar(float valor) {
		return dolar.format(valor);
	}

	public static String formatacaoPercentual(float valor) {
		return perc.format(valor);
	}

	public static void limparBuffer() {
		leia.nextLine();
	}

	public static char getChar(String texto) {
		System.out.print(texto);
		return leia.next().charAt(0);
	}

	public static char getCharUpperCase(String texto) {
		System.out.print(texto);
		return leia.next().toUpperCase().charAt(0);
	}

	public static String getString(String texto) {
		String valor = "";

		System.out.print(texto);
		valor = leia.nextLine();

		if (!valor.equals("")) {
			return valor;
		} else {
			// Para caso o buffer tiver cheio (Limpar o buffer)
			return leia.nextLine();
		}
	}
	
	public static String getStringUpperCase(String texto) {
		String valor = "";

		System.out.print(texto);
		valor = leia.nextLine().toUpperCase();

		if (!valor.equals("")) {
			return valor;
		} else {
			// Para caso o buffer tiver cheio (Limpar o buffer)
			return leia.nextLine().toUpperCase();
		}
	}

	public static char recebeSouN(String texto) {
		char opcao;
		do {
			opcao = getCharUpperCase(texto);
			if (opcao != 'S' && opcao != 'N') {
				System.out.println("Você deve informar S ou N");
			}
		} while (opcao != 'S' && opcao != 'N');

		return opcao;
	}

	public static boolean confirmarSaida() {

		if (recebeSouN("Deseja sair (S/N): ") == 'S') {
			return true;
		} else {
			return false;
		}

	}

	public static boolean validarNumeroPositivoInt(int valor) {
		return (valor >= 0) ? true : false; // Tern�rio
	}

	public static boolean validarNumeroPositivoFloat(float valor) {
		return (valor >= 0) ? true : false; // Tern�rio
	}

	public static float getFloatEntreIntervalos(String texto, float menor, float maior) {
		float valor;

		do {
			valor = getFloat(texto);
			if (valor < menor || valor > maior) {
				System.err.println("O valor deve estar entre " + menor + " e " + maior);
			}
		} while (valor < menor || valor > maior);

		return valor;
	}

	public static int getIntEntreIntervalos(String texto, int menor, int maior) {
		int valor;

		do {
			valor = getInt(texto);
			if (valor < menor || valor > maior) {
				System.err.println("O valor deve estar entre " + menor + " e " + maior);
			}
		} while (valor < menor || valor > maior);

		return valor;
	}

	public static String retirarEspacoInicio(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) != ' ') {
				return texto.substring(i);
			}
		}
		return texto;
	}

	public static String retirarEspacoFim(String texto) {
		for (int i = texto.length() - 1; i >= 0; i--) {
			if (texto.charAt(i) != ' ') {
				return texto.substring(0, i + 1);
			}
		}
		return texto;
	}

	public static String retirarEspacosDuplicados(String texto) {
		do {
			texto = texto.replace("  ", " ");
		} while (texto.contains("  "));

		return texto;
	}

	public static String capitalize(String texto) {
		// Trocar todo o texto para minusculo
		texto = texto.toLowerCase();

		// Vari�vel Auxiliar
		String novoTexto = "";

		// Pega a primeira letra, converte na tabela ASCII
		// e depois faz o typecast para mostrar a letra novamente
		novoTexto += (char) (texto.charAt(0) - 32);

		for (int i = 1; i < texto.length(); i++) {
			if (texto.charAt(i - 1) == ' ') {
				// Se o caracter a ser convertido n�o for letra, n�o realiza a convers�o para
				// mai�sculo
				novoTexto += (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') ? (char) (texto.charAt(i) - 32)
						: texto.charAt(i);
			} else {
				novoTexto += texto.charAt(i);
			}
		}

		return novoTexto;
	}

	public static String captalizeReservedWord(String texto) {
		String newText = capitalize(texto);

		newText = newText.replace(" De ", " de ");
		newText = newText.replace(" Da ", " da ");
		newText = newText.replace(" Pelo ", " pelo ");
		newText = newText.replace(" Em ", " em ");
		newText = newText.replace(" E ", " e ");
		newText = newText.replace(" Ou ", " ou ");
		newText = newText.replace(" As ", " as ");
		newText = newText.replace(" Os ", " os ");
		newText = newText.replace(" Dos ", " dos ");

		return newText;
	}

	public static boolean converterSouNParaBoolean(char c) {
		if (c == 'S' || c == 's') {
			return true;
		} else {
			return false;
		}
	}

	public static char converterBooleanParaSouN(boolean b) {
		if (b == true) {
			return 'S';
		} else {
			return 'N';
		}
	}

	public static boolean consistirHoraMinutos(String hora) {

		int hh, mm;

		if (hora.length() != 5) {
			System.out.println("A hora tem que ter o formato HH:MM");
			return false;
		} else if (hora.charAt(2) != ':') {
			System.out.println("A hora tem que ter o formato HH:MM");
			return false;
		} else {
			try {
				hh = Integer.parseInt(hora.substring(0, 2));
				mm = Integer.parseInt(hora.substring(3));

				if (hh < 0 || hh > 23) {
					System.out.println("A hora deve ser entre 0 e 23!");
					return false;
				}

				if (mm < 0 || mm > 59) {
					System.out.println("Os minutos deve ser entre 0 e 59!");
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println("HH e MM deve ser números!");
				return false;
			}
		}
		return true;
	}

	public static String getHorasMinutos(String texto) {
		String hora;
		do {
			hora = LTPUtils.getString(texto);
		} while (!consistirHoraMinutos(hora));
		return hora;
	}

	public static boolean ehValidaData(String data) {
		
		if (data.length() != 10) {
			System.out
					.println("Data inválida, digite 10 caracteres DD/MM/AAAA");
			return false;
		}
		if (data.charAt(2) != '/' || data.charAt(5) != '/') {
			System.out
					.println("Data inválida, digite / no 3o. e 6o. caracteres DD/MM/AAAA");
			return false;
		}
		try {
			int ano = Integer.parseInt(data.substring(6));
			if (ano < 2013) {
				System.out
						.println("Data inválida, digite ano maior ou igual a 2013");
				return false;
			}
			int mes = Integer.parseInt(data.substring(3, 5));
			if (mes < 1 || mes > 12) {
				System.out.println("Data inválida, digite mes entre 1 e 12");
				return false;
			}
			int dia = Integer.parseInt(data.substring(0, 2));
			if (dia < 1 || dia > 31) {
				System.out.println("Data inválida, digite dia entre 1 e 31");
				return false;
			}
			if (mes == 2 && dia > 28) {
				System.out
						.println("Data inválida, para fevereiro, digite dia entre 1 e 28");
				return false;
			}
			if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
				System.out
						.println("Data inválida, para meses 4,6,9 e 11, digite dia entre 1 e 30");
				return false;
			}
		} catch (NumberFormatException e) {
			System.out
					.println("Data inválida, digite dia, mes e ano numéricos");
			return false;
		}
		return true;
		
	}
	
	public static int getDiaData(String data) {
		if(ehValidaData(data)) {
			return Integer.parseInt(data.substring(0,2));
		}else {
			System.out.println("Data inválida!");
			return -1;
		}
	}
	
	public static void showArrayString(String list[]) {
		System.out.println("\n====================================");
		for (int i = 0; i < list.length; i++) {
			System.out.println((i + 1) + " - " + list[i]);
		}
		System.out.println("\n====================================");
	}
	
	public static String getItensInArray(String[] list, String nameItem) {
		int id = 0;
		
		do {

			showArrayString(list);
			id = LTPUtils.getIntPositivo("Informe o ID ("+nameItem+"): ");
			if (id <= 0 || id > list.length) {
				System.err.println("Informe um ID Válido!");
			} else {
				System.out.println("======================================");
				System.out.println("|> Foi selecionado: " + list[id - 1]);
				System.out.println("======================================");
			}
		} while (id <= 0 || id > list.length);
		
		return list[id - 1];
	}
	
	public static int menuOptions(String menuName, String itensMenu[]) {
		
		String texto = "========= "+menuName+" ============================================";
		
		for (int i = 0; i < itensMenu.length; i++) {
			texto+="\n "+(i+1)+" - "+itensMenu[i];
		}
		texto+="\n 0 - SAIR";
		texto+="\n Selecione uma op��o: ";
		
		return LTPUtils.getIntEntreIntervalos(texto,0, itensMenu.length);
	}
}
