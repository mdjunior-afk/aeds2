import java.util.Scanner;

public class JavaRecursivo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();

        while (!string.equals("FIM")) {
            System.out.println(upperCount(string, 0));

            string = scanner.nextLine();
        }

        scanner.close();
    }

    public static int upperCount(String string, int index) {
        if (index == string.length()) {
            return 0;
        }

        if (Character.isUpperCase(string.charAt(index))) {
            return 1 + upperCount(string, index + 1);
        }

        return upperCount(string, index + 1);
    }
}
