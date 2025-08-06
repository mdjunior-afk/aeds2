#include <stdio.h>
#include <ctype.h>
#include <string.h>

int upperCount(char *string, int index) {
    if (index == strlen(string)) {
        return 0;
    }

    if (isupper(string[index])) {
        return 1 + upperCount(string, index + 1);
    }

    return upperCount(string, index + 1);
}

int main() {
    char string[200];

    while (1) {
        fgets(string, 200, stdin);
        string[strcspn(string, "\n")] = '\0';

        if (strcmp(string, "FIM") == 0) {
            break;
        }

        printf("%d\n", upperCount(string, 0));
    }

    return 0;
}