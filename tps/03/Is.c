#include <stdio.h>
#include <ctype.h>
#include <string.h>

int isVogal(char *c) {
    if (*c == '\0') {
        return 1;
    }

    if (isalpha(*c)) {
        if (tolower(*c) == 'a' || tolower(*c) == 'e' || tolower(*c) == 'i' || tolower(*c) == 'o' || tolower(*c) == 'u') {
            return isVogal(c + 1);
        }
    }
    
    return 0;
}

int isConsoante(char *c) {
    if (*c == '\0') {
        return 1;
    }

    if (!isalpha(*c)) {
        return 0;
    }

    if (tolower(*c) == 'a' || tolower(*c) == 'e' || tolower(*c) == 'i' || tolower(*c) == 'o' || tolower(*c) == 'u') {
            return 0;
    }

    return isConsoante(c + 1);
}

int isInteiro(char *c) {
    if (*c == '\0') {
        return 1;
    }

    if (isdigit(*c)) {
        return isInteiro(c + 1);
    }

    return 0;
}

int isReal(char *c, int ponto) {
    if (*c == '\0') {
        return 1;
    }

    if (isdigit(*c)) {
        return isReal(c + 1, ponto);
    }

    if ((*c == '.' || *c == ',') && ponto == 0) {
        return isReal(c + 1, 1);
    }

    return 0;
}

int main() {
    char c[500];

    while (1) {
      fgets(c, 500, stdin);
      c[strcspn(c, "\n")] = 0;

      if (strcmp(c, "FIM") == 0) {
        break;
      }

      if (isVogal(c) == 1) {
        printf("SIM ");
      } else {
        printf("NAO ");
      }

      if (isConsoante(c) == 1) {
        printf("SIM ");
      } else {
        printf("NAO ");
      }

      if (isInteiro(c) == 1) {
        printf("SIM ");
      } else {
        printf("NAO ");
      }

      if (isReal(c, 0) == 1) {
        printf("SIM\n");
      } else {
        printf("NAO\n");
      }
    }
}
