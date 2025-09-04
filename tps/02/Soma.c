#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int sum(int n) {
	if (n == 0) {
		return 0;
	}

	return n % 10 + sum(n/10);
}

int isEnd(char *str) {
	if (strlen(str) == 3) {
		if (str[0] == 'F' && str[1] == 'I' && str[2] == 'M') {
			return 1;
		}
	}

	return 0;
}

int main() {
	char n[100];

	while (1) {
		
		scanf("%s", n);

		if (isEnd(n)) {
			break;
		}
	
		printf("%d\n", sum(atoi(n)));
	}



	return 0;
}
