public class Cifra {
	public static void main(String[] args) {
		
		while (true) {
			// Pega a proxima palavra digitada pelo usuário
			String text = MyIO.readLine();

			if (isFim(text)) {
				break;
			}

			// Gera a palavra cifrada e usa o MyIO para mostrar na tela
			MyIO.println(getCifra(text, 3));
		}
	}

	// Uma função que verifica se a string é igual a FIM, que vai ser usada para quebrar o loop no main
	public static boolean isFim(String s) {
		if (s.length() == 3) {
			if (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
				return true;
			}
		}

		return false;
	}

	// Um método que retorna a cifra, recebe a string digitada pelo usuário e o move (que é quantidade de caracteres que vai pular)
	public static String getCifra(String text, int move) {
		// Cria um StringBuilder para gerar uma nova string cifrada
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i); // Pega a letra atual no loop
			
			char newChar;
			// Verifica se o char está entre todos os caracteres da tabela ASCII (32 até 126)
			if (c >= ' ' && c <= '~') {
				/* 
				Se estiver entre 32 e 126, ele pega o caracter atual e substitui pelo caracter 3 posições a frente;
				% 126 -> Faz um loop por toda a tabela ASCII, caso passe de 126 ele volta para a posição inicial (1) e soma com o simbolo inicial (32);
				Exemplo: O caracter atual é A (65), subtraimos pelo primeiro caracter ESPAÇO (32) = 65 - 32 = 33 (símbolo !)
				Somamos com o deslocamento 33 + 3 = 36 ($) | 36 % 126 = 36 + 32 (' ') = 68 (D)
			       	*/
				
				newChar = (char) ((c - ' ' + move) % 126 + ' ');
				sb.append(newChar); // Adiciona o novo char no StringBuilder
			} else {
				sb.append(c); // Caso o char não seja identificado, ele so vai adicionar sem nenhuma modificação
			}
		}

		// Retorna uma nova String 
		return sb.toString();
	}

}
