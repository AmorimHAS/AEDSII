#include <stdio.h>
#include <string.h>
// Protótipo da função recursiva que somará os dígitos
int somaDigitos(char[], int);

int main() {
    char str[1000];
    scanf("%s", str); // Lê a primeira string digitada pelo usuário

    // Inicia um laço que continua enquanto a string digitada for diferente de "FIM"
    while (strcmp(str, "FIM") != 0) {
        int soma = somaDigitos(str, 0); // Chama a função somaDigitos para a string lida, começando do índice 0
        printf("%i\n", soma); // Imprime a soma
        scanf("%s", str); // Le a próxima linha para continuar o laço
    }
    return 0;
}

int somaDigitos(char str[], int pont) { // Recebe a string (str) e a posição atual (pont) como parâmetros
    if (str[pont] == '\0') { // Verifica se chegou ao final da string ('\0' é o terminador)
        return 0; // Se for o fim, retorna 0 para parar a soma
    } else {
        return (str[pont] - '0') + somaDigitos(str, pont + 1); //Converte o caractere em digito, chama a funcao novamente com o indice +1, soma o valor para retornar a soma no final
    }
}