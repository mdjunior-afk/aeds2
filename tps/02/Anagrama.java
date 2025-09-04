public class Anagrama {
	public static void main(String[] args) {
		while (true) {
			String str = MyIO.readLine();

			if (isEnd(str)) {
				break;
			}

			if (isAnagram(str)) {
				MyIO.println("SIM");
			} else {
				MyIO.println("NÃO");
			}
		}
	}

	public static boolean isAnagram(String str) {
		int symbol_position = -1;

		// Uma função que encontra o sinal - para separar as Strings
		
		for (int i = 0; i < str.length() - 1; i++) {
			if (str.charAt(i) == '-') {
				symbol_position = i;
				break;
			}
		}

		for (int i = 0; i < symbol_position - 1; i++) {
			boolean hasl = false;

			for (int j = symbol_position + 2; j < str.length(); j++) {
				if (str.charAt(i) == str.charAt(j) || str.charAt(i) + 32 == str.charAt(j) || str.charAt(i) - 32 == str.charAt(j)) {
					hasl = true;
					break;
				}
			}

			if (!hasl) {
				return false;
			}
		}

		return true;
	}

	public static boolean isEnd(String str) {
		if (str.length() == 3) {
			if (str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
				return true;
			}
		}

		return false;
	}
}
