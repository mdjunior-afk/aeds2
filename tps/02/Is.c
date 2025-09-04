#include <stdio.h>
#include <string.h>
#include <ctype.h>

int isEnd(char *str) {
	if (strlen(str) == 3) {
		if (str[0] == 'F' && str[1] == 'I' && str[2] == 'M') {
			return 1;
		}
	}

	return 0;
}

int isAllConsonant(char *str) {
	for (int i = 0; i < strlen(str); i++) {
		if (!isalpha(str[i])) return 0;
		
		if (tolower(str[i]) == 'a' || tolower(str[i]) == 'e' || tolower(str[i]) == 'i' || tolower(str[i]) == 'o' || tolower(str[i]) == 'u') {
			return 0;		
		}
	}

	return 1;
}

int isAllVowel(char *str) {
	for (int i = 0; i < strlen(str); i++) {
        	if (!isalpha(str[i])) return 0;       
	       
		if (!(tolower(str[i]) == 'a' || tolower(str[i]) == 'e' || tolower(str[i]) == 'i' || tolower(str[i]) == 'o' || tolower(str[i]) == 'u')) {
                        return 0;
                }
        }

        return 1;
}

int isInt(char *str) {
	for (int i = 0; i < strlen(str); i++) {
		if (!isdigit(str[i])) {
			return 0;		
		}
	}

	return 1;
}

int isReal(char *str) {
	int has_decimal = 0;

	for (int i = 0; i < strlen(str); i++) {
		if (str[i] == '.' || str[i] == ',') {
			if (has_decimal) return 0;
			has_decimal = 1;
		} else if (!isdigit(str[i])) {
			return 0;		
		}
	}

	return 1;
}

int main() {
	char str[500];

	while (1) {
		scanf(" %[^\n]", str);

		if (isEnd(str)) {
			break;
		}
		
		printf("%s %s %s %s\n", isAllVowel(str) ? "SIM" : "NAO", isAllConsonant(str) ? "SIM" : "NAO", isInt(str) ? "SIM" : "NAO", isReal(str) ? "SIM" : "NAO");

	}




	return 0;
}
