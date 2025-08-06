import java.util.Scanner;

public class JavaIterativo {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String string = scanner.nextLine();

		while (!string.equals("FIM")) {
			System.out.println(upperCount(string));

			string = scanner.nextLine();
		}

		scanner.close();
	}

	public static int upperCount(String string) {
		int quantity = 0;

		for (int i = 0; i < string.length(); i++) {
			if (Character.isUpperCase(string.charAt(i))) {
				quantity++;
			}
		}

		return quantity;
	}
}