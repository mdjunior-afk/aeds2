public class A {
	public static void main(String[] args) {
		while (true) {
			String expression = MyIO.readLine();
		
			if (expression.equals("0")) {
				break;
			}

			// Pega os argumentos iniciais | 2 0 0
			char[] arguments = getArgs(expression, 0);
			
			StringBuilder sb = new StringBuilder(expression);

			// Removendo os argumentos da string
			if (arguments.length == 2) {
				sb.replace(0, 6, "");
			} else if (arguments.length == 3) {
				sb.replace(0, 8, "");
			}

			for (int i = 0; i < sb.length() - 1; i++) {
				if (sb.charAt(i) == 32 && sb.charAt(i + 1) == ',') {
					sb.replace(i, i + 1, "");
				}
			}

			// Atualiza o StringBuilder para ter os valores de A, B e C sendo igual aos argumentos digitados
			sb = swapValues(sb, arguments);

			while (true) {
			
				String[] infos = getExpression(sb.toString(), sb.length() - 1);
		
				/*for (int i = 0; i < infos.length; i++) {
					MyIO.println(i + ": " + infos[i]);
				}*/


				int result = runExpression(infos);

				int start = 0;
				int end = 0;
				
				for (int i = 0; i < infos[0].length(); i++) {
					start = start * 10 + (infos[0].charAt(i) - '0');
				}

				for (int i = 0; i < infos[1].length(); i++) {
					end = end * 10 + (infos[1].charAt(i) - '0');
				}

				end += start;
			
				// Substituindo a expressão que ja foi rodada
				sb.replace(start, end, result + "");
				
				if (start == 0) {
					break;
				}
			}

			MyIO.println(sb.toString());
		}		
	}

	public static int runExpression(String[] values) {
		String method = values[2];

		switch (method) {
			case "and":
				for (int i = 3; i < values.length; i++) {
					if (values[i].equals("0")) {
						return 0;
					}	
				}

				return 1;
			case "or":
				for (int i = 3; i < values.length; i++) {
					if (values[i].equals("1")) {
						return 1;
					}
				}

				return 0;


			case "not":
				if (values[3].equals("0")) {
					return 1;
				}

				return 0;

			default:
				return 0;
		}
	}

	public static String[] getExpression(String expression, int counter) {
		int end = 0;
		
		while (expression.charAt(counter) != '(') {
			if (expression.charAt(counter) == ')') {
				end = counter;
			}
			counter--;
		}

		while (counter > 0) {
			if (expression.charAt(counter - 1) == '(') {
				counter--;
				break;
			}

			if (expression.charAt(counter) == 32 || expression.charAt(counter) == ',')
				break;

			counter--;
		}

		int n_arguments = 0;
		boolean get = true;

		String method = "", str = "";

		if (counter == 0) {
			counter--;
		}

		int position = counter + 1;

		for (int i = position; i <= end; i++) {
			if (expression.charAt(i) != '(' && get) {
				method += expression.charAt(i);
			} else {
				get = false;
			}
			       
			if (expression.charAt(i) == '0' || expression.charAt(i) == '1') {
				n_arguments++;
			}

			str += expression.charAt(i);
		}

		String[] result = new String[n_arguments + 3];
		result[0] = position + "";
		result[1] = str.length() + "";
		result[2] = method;

		int index = 3;

		for (int i = 0; i < str.length() - 1; i++) {
			if (str.charAt(i) == '0' || str.charAt(i) == '1') {
				result[index++] = str.charAt(i) + "";
			}

			if (index == n_arguments + 3) {
				break;
			}
		}

		return result;
	}

	public static StringBuilder swapValues(StringBuilder sb, char[] args) {
		for (int i = sb.length() - 1; i > 0; i--) {
			switch (sb.charAt(i)) {
				case 'A':
					sb.setCharAt(i, args[0]);
					break;
				case 'B':
					sb.setCharAt(i, args[1]);
					break;
				case 'C':
					sb.setCharAt(i, args[2]);
					break;
			}
		}

		return sb;	
	}

	public static char[] getArgs(String expression, int start) {
		int count = expression.charAt(start) - '0', index = 0;
		
		char[] args = new char[count];

		for (int i = 0; index < count; i++) {
			if (expression.charAt(i) == '0' || expression.charAt(i) == '1') {
				args[index++] = expression.charAt(i);		
			}
		}

		return args;
	
	}
}
		
