#include <stdio.h>    // Entrada e saída de dados (printf, fgets)
#include <stdbool.h>  // Tipo de dado booleano (true/false)
#include <string.h>   // Funções de strings (strcmp, strcspn)
#include <ctype.h>    // Funções de caracteres (tolower, isdigit)

// Protótipos das funções recursivas
bool IsVogal(char string[], int i);
bool IsConsoante(char string[], int i);
bool IsInteiro(char string[], int i);
bool IsReal(char string[], int i, int contador);

int main() {
    char str[1000];
    
    // Lê a entrada e remove a quebra de linha
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0';

    // Loop que processa as entradas até "FIM"
    while (strcmp(str, "FIM") != 0) {
        // Chama as funções e imprime SIM/NAO para cada verificação
        printf(IsVogal(str, 0) ? "SIM " : "NAO ");
        printf(IsConsoante(str, 0) ? "SIM " : "NAO ");
        printf(IsInteiro(str, 0) ? "SIM " : "NAO ");
        printf(IsReal(str, 0, 0) ? "SIM\n" : "NAO\n");

        // Lê a próxima entrada
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0';
    }
    return 0;
}

// --- Funções Recursivas ---

// Verifica se a string é composta apenas por vogais
bool IsVogal(char string[], int i) {
    if (string[i] == '\0') {
        return true; // Caso base: chegou ao fim da string
    }
    // Retorna falso se o caractere não for uma vogal
    if (tolower(string[i]) != 'a' && tolower(string[i]) != 'e' && tolower(string[i]) != 'i' && tolower(string[i]) != 'o' && tolower(string[i]) != 'u') {
        return false;
    }
    return IsVogal(string, i + 1); // Chamada recursiva para o próximo caractere
}

// Verifica se a string é composta apenas por consoantes
bool IsConsoante(char string[], int i) {
    if (string[i] == '\0') {
        return true; // Caso base: chegou ao fim da string
    }
    // Retorna falso se não for uma letra ou se for uma vogal
    if (!('a' <= tolower(string[i]) && tolower(string[i]) <= 'z') || (tolower(string[i]) == 'a' || tolower(string[i]) == 'e' || tolower(string[i]) == 'i' || tolower(string[i]) == 'o' || tolower(string[i]) == 'u')) {
        return false;
    }
    return IsConsoante(string, i + 1); // Chamada recursiva
}

// Verifica se a string é um número inteiro
bool IsInteiro(char string[], int i) {
    if (string[i] == '\0') {
        return true; // Caso base: chegou ao fim da string
    }
    if (!isdigit(string[i])) {
        return false; // Retorna falso se não for um dígito
    }
    return IsInteiro(string, i + 1); // Chamada recursiva
}

// Verifica se a string é um número real
bool IsReal(char string[], int i, int contador) {
    if (string[i] == '\0') {
        return true; // Caso base: chegou ao fim da string
    }
    if (string[i] == '.' || string[i] == ',') {
        if (contador > 0) {
            return false; // Retorna falso se já houver um ponto/vírgula
        }
        return IsReal(string, i + 1, contador + 1); // Chamada recursiva, incrementando o contador
    }
    if (!isdigit(string[i])) {
        return false; // Retorna falso se não for um dígito
    }
    return IsReal(string, i + 1, contador); // Chamada recursiva
}