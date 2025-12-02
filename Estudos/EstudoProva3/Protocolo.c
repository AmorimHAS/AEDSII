#include <stdio.h>
#include <string.h>

// 1. Definição da "Classe" Pacote (em C usamos struct)
typedef struct {
    int id;
} Pacote;

int main() {
    char buffer[50]; // Buffer para ler as palavras (substituto do sc.next())

    // Loop principal: roda enquanto o arquivo não acabar (EOF)
    // O scanf retorna EOF quando não consegue ler mais nada
    while (scanf("%s", buffer) != EOF) {

        // Verifica se leu o início de um caso de teste ("1")
        if (strcmp(buffer, "1") == 0) {
            
            // Criamos o vetor estático com tamanho seguro
            Pacote vetor[1000];
            int n = 0; // Contador de quantos pacotes temos

            // Loop interno para ler os pacotes deste caso
            while (1) {
                char leitura[50];
                scanf("%s", leitura); // Lê "Package" ou "0"

                // Se for "0", termina a leitura deste caso
                if (strcmp(leitura, "0") == 0) {
                    break;
                }

                // Se for "Package", lê o número int logo em seguida
                if (strcmp(leitura, "Package") == 0) {
                    int id;
                    scanf("%d", &id);
                    
                    // Guarda no vetor e incrementa o contador
                    vetor[n].id = id;
                    n++;
                }
            }

            // --- ORDENAÇÃO MANUAL (Bubble Sort) ---
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    // Se o ID atual for maior que o próximo, troca
                    if (vetor[j].id > vetor[j + 1].id) {
                        Pacote temp = vetor[j];
                        vetor[j] = vetor[j + 1];
                        vetor[j + 1] = temp;
                    }
                }
            }

            // --- SAÍDA ---
            for (int i = 0; i < n; i++) {
                // %03d coloca os zeros à esquerda (ex: 1 vira 001)
                printf("Package %03d\n", vetor[i].id);
            }
            printf("\n"); // Linha em branco ao final do caso
        }
    }

    return 0;
}