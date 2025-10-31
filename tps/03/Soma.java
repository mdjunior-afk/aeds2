import java.util.Scanner;

public class Soma {
    public static void main(String[] args) {
        int i = 1;

        Scanner scan = new Scanner(System.in);

        while (true) {
            String str = scan.nextLine();

            if (isFim(str)) {
                break;
            }

            MyIO.println(somaDigitos(str, 0));
            i++;
        }
    }

    public static boolean isFim(String str) {
        if (str.length() >= 3) {
            if (str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
                return true;
            }
        }

        return false;
    }

    public static int somaDigitos(String str, int index) {
        if (index == str.length() - 1) {
            return str.charAt(index) - '0';
        }

        return (str.charAt(index) - '0') + somaDigitos(str, index + 1);
    }
}