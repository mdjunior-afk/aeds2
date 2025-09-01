public class Espelho {
	public static void main(String[] args) {
		int n1, n2;

		while (true) {
			n1 = MyIO.readInt();
			n2 = MyIO.readInt();

			inverter(n1, n2);
		}

	}

	public static void inverter(int n1, int n2) {
		String first = "", last = "";

		for (int i = n1; i <= n2; i++) {
			first += String.valueOf(i);	
		}

		for (int i = n2; i >= n1; i--) {
			String new_str = "";
			int num = i;

			while (num > 0) {
				int digit = num % 10;
				new_str += String.valueOf(digit);
				num = num / 10;
			}

			last += new_str;
		}

		MyIO.println(first + last);
	}
}
