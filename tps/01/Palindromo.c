#include <stdio.h>
#include <stdbool.h>
#include <locale.h>
#include <wchar.h>

// funcao que verifica se é um palindromo, recebendo uma letra inicial e uma final
bool isPalindrum(const wchar_t *str, int inicio, int fim) {
	// Se chegar no meio, ele retorna que é um palindromo
	if (inicio >= fim) {
		return true;
	}

	// Compara se a letra inicial e final é diferente, se sim então não é um palindromo
	if (str[inicio] != str[fim]) {
		return false;
    	}

	// Chama a função novamente, passando para a proxima letra
	return isPalindrum(str, inicio + 1, fim - 1);
}

int main() {
	setlocale(LC_ALL, ""); // codificação UTF-8
	wchar_t text[100];
    
	while (true) {
		// Pega o que o usuário digitou
		fgetws(text, 100, stdin); 
		
		// Retira a quebra de linha do final da palavra
		text[wcscspn(text, L"\n")] = L'\0';

		// Compara se o texto digitado é igual a FIM, se sim então quebra o loop
		if (wcscmp(text,L"FIM") == 0) {
			break;
		}
	 
		// Pega o tamanho da string, pra saber qual a letra final
   		int size = wcslen(text);

   		// Roda a função de palindromo, passando a posição da letra inicial e da final
    		if (size > 0 && isPalindrum(text, 0,  size - 1)) {
        		wprintf(L"SIM\n");
    		} else {
        		wprintf(L"NAO\n");
    		}
    	}

    return 0;
}

