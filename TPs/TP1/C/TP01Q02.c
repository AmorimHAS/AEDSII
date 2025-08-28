#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// função recursiva que verifica se uma string é palíndromo
bool descobrirPalindromo(char str[], int TAM, int i) {
    if (i >= TAM) { // caso base
        return true;
    }
    if (str[i] != str[TAM]) { // se caracteres forem diferentes
        return false;
    } else {
        return descobrirPalindromo(str, TAM - 1, i + 1); // chama a função recursivamente, movendo índices
    }
}

int main() {
    char str[10000]; // vetor de caracteres para receber a string

    // lê linhas do input ate ser igual a FIM
    while (fgets(str, sizeof(str), stdin) != NULL) {
        int TAM = strlen(str); // obtém o tamanho da string
        int i = 0; // índice inicial para verificação de palíndromo

        // remove o '\n' no final da string, se existir
        if (TAM > 0 && str[TAM - 1] == '\n') {
            str[--TAM] = '\0';
        }

        // chama a função recursiva para verificar se é palíndromo
        bool palindromo = descobrirPalindromo(str, TAM - 1, i);

        // imprime o resultado
        if (palindromo == 1) {
            printf("SIM\n"); 
        } else {
            printf("NAO\n");
        }
    }

    return 0; // finaliza o programa
}
