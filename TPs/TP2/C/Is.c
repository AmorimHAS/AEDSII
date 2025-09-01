#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>

//Protótipos das Funções
bool compostaVogais(char[]);      // Verifica se a string é composta apenas por vogais
bool compostaConsoantes(char[]);  // Verifica se a string é composta apenas por consoantes
bool compostaInteiros(char[]);    // Verifica se a string é um número inteiro
bool compostaReais(char[]);       // Verifica se a string é um número real

int main() {
    char str[1000];
    fgets(str, sizeof(str), stdin); // Le a primeira string

    // Substitui '\n' por '\0' para finalizar a string corretamente.
    for (int i = 0; str[i] != '\0'; i++) {
        if (str[i] == '\n') {
            str[i] = '\0';
            break;
        }
    }

    // Para o loop quando a string for FIM
    while (!(str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0')) {
        
        // Verifica se a string é composta apenas por vogais e imprimi SIM ou NAO
        if (compostaVogais(str) == true) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }
        
        // Verifica se a string é composta apenas por consoantes e imprimi SIM ou NAO
        if (compostaConsoantes(str) == true) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        // Verifica se a string é um número inteiro e imprimi SIM ou NAO
        if (compostaInteiros(str) == true) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        // Verifica se a string é um número real e imprimi SIM ou NAO
        if (compostaReais(str) == true) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        // Le a proxima string pra continuar o loop
        fgets(str, sizeof(str), stdin);
        
        // Substitui '\n' por '\0' para finalizar a string corretamente.
    for (int i = 0; str[i] != '\0'; i++) {
        if (str[i] == '\n') {
            str[i] = '\0';
            break;
        }
    }
    }
    
    return 0;
}

// Verifica se a string é composta apenas por vogais
bool compostaVogais(char string[]) {
    // Percorre cada caractere da string até encontrar o terminador nulo '\0'.
    for (int i = 0; string[i] != '\0'; i++) {
        // Converte o caractere para minúsculo e verifica se ele NÃO é uma das vogais.
        if (tolower(string[i]) != 'a' && tolower(string[i]) != 'e' && tolower(string[i]) != 'i' && tolower(string[i]) != 'o' && tolower(string[i]) != 'u') {
            return false; // Se encontrar qualquer caractere que não seja vogal, retorna false
        }
    }
    return true; // Se o laço terminar, significa que todos os caracteres eram vogais.
}

// Verifica se a string é composta apenas por consoantes
bool compostaConsoantes(char string[]) {
    for (int i = 0; string[i] != '\0'; i++) {
        // Verifica se o caractere (em minúsculo) NÃO é uma letra do alfabeto ('a' a 'z').
        if (!('a' <= tolower(string[i]) && tolower(string[i]) <= 'z')) {
            return false; // Se não for uma letra, já não pode ser composta só de consoantes.
        } 
        // Se for uma letra, verifica se é uma vogal.
        else if (tolower(string[i]) == 'a' || tolower(string[i]) == 'e' || tolower(string[i]) == 'i' || tolower(string[i]) == 'o' || tolower(string[i]) == 'u') {
            return false; // Se for uma vogal, retorna 'false'.
        }
    }
    return true; // Se o laço terminar, todos os caracteres são letras e nenhuma é vogal, portanto, são consoantes.
}

// Verifica se a string é um número inteiro
bool compostaInteiros(char string[]) {
    for (int i = 0; string[i] != '\0'; i++) {
        // A função isdigit() retorna verdadeiro se o caractere for um dígito.
        if (!isdigit(string[i])) {
            return false; // Se encontrar qualquer caractere que não seja um dígito, retorna 'false'.
        }
    }
    return true; // Se o laço terminar, todos os caracteres eram dígitos.
}

// Verifica se a string é um número real
bool compostaReais(char string[]) {
    int ponto = 0;
    for (int i = 0; string[i] != '\0'; i++) {
        // Verifica se o caractere atual é um ponto ou uma vírgula.
        if (string[i] == '.' || string[i] == ',') {
            ponto++; // Incrementa o contador
            // Se já tivermos encontrado mais de um separador, o número é inválido.
            if (ponto > 1) {
                return false;
            }
        } 
        // Se não for um separador, verifica se NÃO é um dígito.
        else if (!isdigit(string[i])) {
            return false; // Se não for nem dígito nem separador, a string é inválida.
        }
    }
    return true; // Se o laço terminar, a string representa um número real válido.
}