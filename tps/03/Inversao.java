public class Inversao {
    public static void main(String[] args) {
        while (true) {
            String str = MyIO.readLine();

            if (isFim(str)) {
                break;
            }

            MyIO.println(inverter(str, str.length() - 1));
        }
    }

    public static String inverter(String string, int index) {
        if (index == 0) {
            return string.charAt(index) + "";
        }

        return string.charAt(index) + inverter(string, index - 1);
    }

    public static boolean isFim(String str) {
        if (str.length() == 3) {
            if (str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M') {
                return true;
            }
        }

        return false;
    }
}