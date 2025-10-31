#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#define MAX_LINE_LENGTH 1024

long long int numCompare, numMov;

typedef struct {
    int id;
    char *name;
    char *releasedDate;
    
    int estimatedOwners;
    int metacriticScore;
    int achivements;
    
    float price;
    float userScore;
    
    char **supportedLanguages;
    int supportedLanguages_len;
    char **publishers;
    int publishers_len;
    char **developers;
    int developers_len;
    char **categories;
    int categories_len;
    char **genres;
    int genres_len;
    char **tags;
    int tags_len;
} Game;

void stringToArray(char *string, char ***arr, int *len) {
    int arrLength = 1;

    string[strcspn(string, "\r\n")] = '\0';

    for (int i = 0; i < strlen(string); i++) {
        if (string[i] == ',') {
            arrLength++;
        }
    }

    *len = arrLength;

    char **newArr = (char**) malloc(arrLength * sizeof(char *));
    for (int i = 0; i < arrLength; i++) {
        newArr[i] = (char *) malloc(100 * sizeof(char));
    }
    int newArr_idx = 0;

    int value_idx = 0;
    
    for (int i = 0; i < strlen(string); i++) {
        char c = string[i];
        
        if (c == '[' || c == ']') {
            continue;
        }

        if (c == ',') {
            newArr[newArr_idx][value_idx] = '\0';

            newArr_idx++;
            value_idx = 0;
            continue;
        }

        newArr[newArr_idx][value_idx] = c;
        value_idx++;
    }

    newArr[newArr_idx][value_idx] = '\0';

    *arr = newArr;
}

void updateDate(char *date) {
    char month[5] = "";
    char *new_month;
    char day[4] = "";
    char year[6] = "";
    char new_date[15] = "";
    
    int date_idx = 0;
    int i;
    
    i = 0;
    while (date[date_idx] != ' ' && i < sizeof(month) - 1) {
        month[i++] = date[date_idx++];
    }
    month[i] = '\0';

    if (month[0] == 'J' && month[1] == 'a' && month[2] == 'n') {
        new_month = "01";
    } else if (month[0] == 'F' && month[1] == 'e' && month[2] == 'b') {
        new_month = "02";
    } else if (month[0] == 'M' && month[1] == 'a' && month[2] == 'r') {
        new_month = "03";
    } else if (month[0] == 'A' && month[1] == 'p' && month[2] == 'r') {
        new_month = "04";
    } else if (month[0] == 'M' && month[1] == 'a' && month[2] == 'y') {
        new_month = "05";
    } else if (month[0] == 'J' && month[1] == 'u' && month[2] == 'n') {
        new_month = "06";
    } else if (month[0] == 'J' && month[1] == 'u' && month[2] == 'l') {
        new_month = "07";
    } else if (month[0] == 'A' && month[1] == 'u' && month[2] == 'g') {
        new_month = "08";   
    } else if (month[0] == 'S' && month[1] == 'e' && month[2] == 'p') {
        new_month = "09";
    } else if (month[0] == 'O' && month[1] == 'c' && month[2] == 't') {
        new_month = "10";
    } else if (month[0] == 'N' && month[1] == 'o' && month[2] == 'v') {
        new_month = "11";
    } else if (month[0] == 'D' && month[1] == 'e' && month[2] == 'c') {
        new_month = "12";
    } else {
        new_month = "01";
    }

    date_idx++;

    i = 0;
    while (date[date_idx] != ',' && i < sizeof(day) - 1) {
        if (date[date_idx] != ' ') { 
            day[i++] = date[date_idx];
        }
        date_idx++;
    }
    day[i] = '\0';

    while (date[date_idx] == ' ' || date[date_idx] == ',') {
        date_idx++;
    }

    i = 0;
    while (date[date_idx] != '\0' && i < sizeof(year) - 1) {
        year[i++] = date[date_idx++];
    }
    year[i] = '\0';

    strcat(new_date, day);
    strcat(new_date, "/");
    strcat(new_date, new_month);
    strcat(new_date, "/");
    strcat(new_date, year);

    strcpy(date, new_date);
}

Game findGame(char *path, char *key) {
    FILE *file;
    char line[MAX_LINE_LENGTH];

    file = fopen(path, "r");

    int firstLine = 0;

    while (fgets(line, MAX_LINE_LENGTH, file) != NULL) {
        if (firstLine == 0) {
            firstLine = 1;
            continue;
        }

        int commaCount = 0;
        int isList = 0;

        char id[50];
        int id_idx = 0;

        char name[100];
        int name_idx = 0;

        char releaseDate[15];
        int releaseDate_idx = 0;

        char estimatedOwners[20];
        int estimatedOwners_idx = 0;

        char price[20];
        int price_idx = 0;

        char supportedLanguage[200];
        int supportedLanguage_idx = 0;

        char metacriticScore[10];
        int metacriticScore_idx = 0;

        char userScore[10];
        int userScore_idx = 0;

        char achievements[10];
        int achievements_idx = 0;

        char publishers[200];
        int publishers_idx = 0;

        char developers[200];
        int developers_idx = 0;

        char categories[200];
        int categories_idx = 0;

        char genres[200];
        int genres_idx = 0;

        char tags[200];
        int tags_idx = 0;

        for (int i = 0; i < strlen(line); i++) {
            if (line[i] == ',' && isList == 0) {
                commaCount++;
                continue;
            }

            if (line[i] == '\'') {
                continue;
            }

            char c = line[i];

            switch (commaCount) {
                case 0:
                    id[id_idx] = c;
                    id_idx++;

                    break;
                case 1:
                    name[name_idx] = c;
                    name_idx++;

                    break;
                case 2:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    releaseDate[releaseDate_idx] = c;
                    releaseDate_idx++;

                    break;
                case 3:
                    estimatedOwners[estimatedOwners_idx] = c;
                    estimatedOwners_idx++;

                    break;
                case 4:
                    price[price_idx] = c;
                    price_idx++;

                    break;
                case 5:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    supportedLanguage[supportedLanguage_idx] = c;
                    supportedLanguage_idx++;

                    break;
                case 6:
                    metacriticScore[metacriticScore_idx] = c;
                    metacriticScore_idx++;

                    break;
                case 7:
                    userScore[userScore_idx] = c;
                    userScore_idx++;

                    break;
                case 8:
                    achievements[achievements_idx] = c;
                    achievements_idx++;

                    break;
                case 9:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    publishers[publishers_idx] = c;
                    publishers_idx++;
                    if (c == ',') {
                        publishers[publishers_idx] = ' ';
                        publishers_idx++;
                    }

                    break;
                case 10:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    developers[developers_idx] = c;
                    developers_idx++;
                    if (c == ',') {
                        developers[developers_idx] = ' ';
                        developers_idx++;
                    }

                    break;
                case 11:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    categories[categories_idx] = c;
                    categories_idx++;
                    if (c == ',') {
                        categories[categories_idx] = ' ';
                        categories_idx++;
                    }

                    break;
                case 12:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    genres[genres_idx] = c;
                    genres_idx++;
                    if (c == ',') {
                        genres[genres_idx] = ' ';
                        genres_idx++;
                    }

                    break;
                case 13:
                    if (c == '"' && isList == 0) {
                        isList = 1;
                        continue;
                    } else if (c == '"' && isList == 1) {
                        isList = 0;
                        continue;
                    }

                    tags[tags_idx] = c;
                    tags_idx++;
                    if (c == ',') {
                        tags[tags_idx] = ' ';
                        tags_idx++;
                    }

                    break;
            }

        }

        id[id_idx] = '\0';
        name[name_idx] = '\0';
        releaseDate[releaseDate_idx] = '\0';
        estimatedOwners[estimatedOwners_idx] = '\0';
        price[price_idx] = '\0';
        supportedLanguage[supportedLanguage_idx] = '\0';
        metacriticScore[metacriticScore_idx] = '\0';
        userScore[userScore_idx] = '\0';
        achievements[achievements_idx] = '\0';
        publishers[publishers_idx] = '\0';
        developers[developers_idx] = '\0';
        categories[categories_idx] = '\0';
        genres[genres_idx] = '\0';
        tags[tags_idx] = '\0';

        updateDate(releaseDate);

        if (strcmp(key, id) == 0) {
            Game newGame;
            newGame.id = atoi(id);
            newGame.name = malloc(strlen(name) + 1);
            strcpy(newGame.name, name);

            newGame.releasedDate = malloc(strlen(releaseDate) + 1);
            strcpy(newGame.releasedDate, releaseDate);

            newGame.estimatedOwners = atoi(estimatedOwners);
            newGame.price = atof(price);
            newGame.metacriticScore = atoi(metacriticScore);
            newGame.userScore = atof(userScore);
            newGame.achivements = atoi(achievements);

            stringToArray(supportedLanguage, &newGame.supportedLanguages, &newGame.supportedLanguages_len);
            stringToArray(publishers, &newGame.publishers, &newGame.publishers_len);
            stringToArray(developers, &newGame.developers, &newGame.developers_len);
            stringToArray(categories, &newGame.categories, &newGame.categories_len);
            stringToArray(genres, &newGame.genres, &newGame.genres_len);
            stringToArray(tags, &newGame.tags, &newGame.tags_len);

            return newGame;
        }
    }
}

void selectionSort(Game *arr, int length) {
    for (int i = 0; i < length; i++) {
        int min = i;

        for (int j = i + 1; j < length; j++) {
            numCompare++;
            if (strcmp(arr[min].name, arr[j].name) > 0) {
                min = j;
            }
        }

        if (min != i) {
            Game temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
            numMov += 3;
        }
    }
}

void printGame(Game g) {
    printf("=> %d ## ", g.id);
    printf("%s ## ", g.name);
    printf("%s ## ", g.releasedDate);
    printf("%d ## ", g.estimatedOwners);
    printf("%.2f ## ", g.price);

    printf("[");
    for (int i = 0; i < g.supportedLanguages_len; i++) {
        if (i < g.supportedLanguages_len - 1) {
            printf("%s,", g.supportedLanguages[i]);
        } else {
            printf("%s] ## ", g.supportedLanguages[i]);
        }
    }

    printf("%d ## ", g.metacriticScore);
    printf("%.1f ## ", g.userScore);
    printf("%d ## ", g.achivements);

    printf("[");
    for (int i = 0; i < g.publishers_len; i++) {
        if (i < g.publishers_len - 1) {
            printf("%s,", g.publishers[i]);
        } else {
            printf("%s] ## ", g.publishers[i]);
        }
    }

    printf("[");
    for (int i = 0; i < g.developers_len; i++) {
        if (i < g.developers_len - 1) {
            printf("%s,", g.developers[i]);
        } else {
            printf("%s] ## ", g.developers[i]);
        }
    }

    printf("[");
    for (int i = 0; i < g.categories_len; i++) {
        if (i < g.categories_len - 1) {
            printf("%s,", g.categories[i]);
        } else {
            printf("%s] ## ", g.categories[i]);
        }
    }

    printf("[");
    for (int i = 0; i < g.genres_len; i++) {
        if (i < g.genres_len - 1) {
            printf("%s,", g.genres[i]);
        } else {
            printf("%s] ## ", g.genres[i]);
        }
    }

    printf("[");
    for (int i = 0; i < g.tags_len; i++) {
        if (i < g.tags_len - 1) {
            printf("%s,", g.tags[i]);
        } else {
            printf("%s] ## ", g.tags[i]);
        }
    }
}

int main() {
    char key[100];

    Game *games = NULL;
    int games_count = 0;

    clock_t start, end;

    while (1) {
        scanf("%s", key);

        if (strcmp(key, "FIM") == 0) break;

        Game game = findGame("/tmp/games.csv", key);

        Game *temp = (Game *)realloc(games, (games_count + 1) * sizeof(Game));
        games = temp;

        games[games_count] = game;
        games_count++;
    }
    selectionSort(games, games_count);


    
    for (int i = 0; i < games_count; i++) {
        printGame(games[i]);
        printf("\n");
    }

    end = clock();

    double time = ((double)(end - start)) / CLOCKS_PER_SEC;

    FILE *file;
    
    file = fopen("896612_selection.txt", "w"); 

    if (file == NULL) {
        fprintf(stderr, "Erro ao abrir o arquivo para escrita.\n");
        return 1;
    }

    fprintf(file, "%lld\t", numCompare);
    fprintf(file, "%lld\t", numMov);
    fprintf(file, "%.6f\t", time);

    fclose(file);

    return 0;
}