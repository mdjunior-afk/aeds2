#include <stdio.h>
#include <ctype.h>
#include <string.h>

int upperCount(char *string) {
    int quantity = 0;

    for (int i = 0; i < strlen(string); i++) {
        if (isupper(string[i])) {
            quantity++;
        }
    }

    return quantity;
}

int main() {
    char string[200];

    while (1) {
        fgets(string, 200, stdin);
        string[strcspn(string, "\n")] = '\0';

        if (strcmp(string, "FIM") == 0) {
            break;
        }

        printf("%d\n", upperCount(string));
    }   

    return 0;
}


