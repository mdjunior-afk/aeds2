public class Inversao {
	public static void main(String[] args) {
		while (true) {
			String word = MyIO.readLine();

			if (isEnd(word)) {
				break;
			}

			MyIO.println(invertString(word));
		}
	}

	public static String invertString(String word) {
		StringBuilder sb = new StringBuilder(word);

		for (int i = 0, j = word.length() - 1; i < j; i++, j--) {
			char ltemp = word.charAt(i);

			sb.setCharAt(i, word.charAt(j));
			sb.setCharAt(j, ltemp);
		}

		return sb.toString();
	}

	public static boolean isEnd(String word) {
		if (word.length() == 3) {
			if (word.charAt(0) == 'F' && word.charAt(1) == 'I' && word.charAt(2) == 'M') {
				return true;
			}
		
		}

		return false;
	}
}
