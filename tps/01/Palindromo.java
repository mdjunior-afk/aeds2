import java.util.Scanner;

public class Palindromo {
	public static void main(String[] args) {
		// Cria um scanner para recuperar os digitos do usuário
		Scanner scan = new Scanner(System.in);

		while (true) {
			String s = scan.nextLine(); // Le o proximo texto digitado

			if (isFim(s)) {
				break;
			}
			
			if (isPalindrum(s)) { // Roda a função e verifica se é palindromo ou não
				MyIO.println("SIM");
			} else {
				MyIO.println("NAO");
			}
		}
	}

	// Uma função que verifica se a string é igual a FIM, que vai ser usada para parar o loop principal
	public static boolean isFim(String s) {
		if (s.length() == 3) {
			if (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
				return true;
			}
		}

		return false;
	}

	public static boolean isPalindrum(String s) {
		// Passa por cada letra do inicio ao final comparando se são iguais
		// Quando o inicio se torna igual o final, então siginifica que chegou no meio da palavra e quebra o loop
		for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
			if (s.charAt(i) != s.charAt(j)) { // Verifica se a letra no inicio e no final são iguais, senão quebra o loop e retorna não é um palindromo
				return false;
			}
		}
		
		// Caso todas as letras sejam iguais então é palindromo
		return true;
	}
}
