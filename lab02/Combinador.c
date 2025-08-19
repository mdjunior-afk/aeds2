#include <stdio.h>
#include <string.h>

int maxLength(char* arg1, char*arg2) {
	return (strlen(arg1) > strlen(arg2)) ? strlen(arg1) : strlen(arg2);
}

int maxString(char* arg1, char* arg2) {
	if (strlen(arg1) > strlen(arg2)) {
		return 1;
	} else {
		return 2;
	}
}

int main() {
	char fstring[100], lstring[100];

	scanf("%s %s", fstring, lstring);

	int str_sum = strlen(fstring) + strlen(lstring), str_index = 0, counter = 0, maxl = maxLength(fstring, lstring);
	char new_str[100];

	while (counter < str_sum) {
		if (counter <= maxl) {
			new_str[counter] = fstring[str_index];
			
			counter++;
			
			new_str[counter] = lstring[str_index];
			
			counter++;
			str_index++;
		} else {
			if (maxString(fstring, lstring) == 1) {
				new_str[counter] = fstring[str_index];
			} else {
				new_str[counter] = lstring[str_index];
			}
			
			counter++;
			str_index++;
		}
	}

	printf("%s", new_str);
}
