import java.util.Random;

public class Alteracao {
	public static void main(String[] args) {
		// Cria uma nova instacia de random
		Random rand = new Random();
		// Usa a seed 4 para efeito de correção no verde
		rand.setSeed(4);

		while (true) {
			// Pega o texto digitado pelo usuário
			String text = MyIO.readLine();

			if (isFim(text)) {
				break;
			}
						
			// O MyIO mostra na tela a String retornada pelo metodo alter
			MyIO.println(alter(text, rand));
		}
	}

	// Uma função que verifica se a string é igual a FIM, é usada para quebrar o loop no main
	public static boolean isFim(String s) {
		if (s.length() == 3) {
			if (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
				return true;
			}
		}

		return false;
	}

	// O metodo recebe o text e o Random para escolher letras aleatorias para substituir
	public static String alter(String text, Random rand) {
		// Gera a primeira letra para verificar na string, e a segunda para substituir a primeira, caso exista a primeira
		char firstl = (char) ('a' + (Math.abs(rand.nextInt()) % 26));
		char secondl = (char) ('a' + (Math.abs(rand.nextInt()) % 26));

		// Cria um StringBuilder para substituir letras no texto digitado
		StringBuilder sb = new StringBuilder(text);

		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == firstl) { // Verifica se a letra atual é a mesma que foi gerada de forma aleatoria
				sb.setCharAt(i, secondl); // Se sim, ele troca a letra atual pela segunda letra gerada de forma aleatoria
			}
		}
		
		// Retorna a nova string
		return sb.toString();
	}
}
