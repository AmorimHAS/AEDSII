#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define TAM_LINHA 4096
#define TAM_CAMPO 512
#define MAX_ELEM 50

// Estrutura para armazenar os dados de um jogo
// Renomeada de 'Game' para 'Jogo'
typedef struct {
    int id;
    char* name;
    char* releaseDate;
    int estimatedOwners;
    float price;
    char** supportedLanguages;
    int supportedLanguagesCount;
    int metacriticScore;
    float userScore;
    int achievements;
    char** publishers;
    int publishersCount;
    char** developers;
    int developersCount;
    char** categories;
    int categoriesCount;
    char** genres;
    int genresCount;
    char** tags;
    int tagsCount;
} Jogo;

// Variáveis globais renomeadas
int g_comparacoes = 0;
int g_movimentacoes = 0;

// Protótipos de funções renomeadas
void carregarJogoDaLinha(Jogo* jogo, char* linha);
void imprimirJogo(Jogo* jogo);
void liberarMemoriaJogo(Jogo* jogo);
char* obterCampo(char* linha, int* pos);
char** quebrarString(const char* str, char delimitador, int* contagem);
char* apararString(char* str);
char* ajustarFormatoData(char* strData);
void imprimirVetorString(char** arr, int contagem);
void ordenacaoSelecao(Jogo* jogos, int n);
void trocarJogos(Jogo* a, Jogo* b);
void gerarArquivoLog(const char* matricula, int comp, int mov, double tempo);

// Lógica Principal
int main() {
    char bufferLinha[TAM_LINHA];
    const char* caminhoArquivo = "/tmp/games.csv";
    
    FILE* arquivo = fopen(caminhoArquivo, "r");
    if (arquivo == NULL) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }
    
    int totalJogos = 0;
    fgets(bufferLinha, TAM_LINHA, arquivo); // Pula o cabeçalho
    while (fgets(bufferLinha, TAM_LINHA, arquivo) != NULL) {
        totalJogos++;
    }
    fclose(arquivo);

    Jogo* vetorTodosJogos = (Jogo*) malloc(sizeof(Jogo) * totalJogos);
    if (vetorTodosJogos == NULL) {
        printf("Erro de alocação de memória\n");
        return 1;
    }
    
    arquivo = fopen(caminhoArquivo, "r");
    if (arquivo == NULL) {
        perror("Erro ao reabrir o arquivo");
        free(vetorTodosJogos);
        return 1;
    }

    fgets(bufferLinha, TAM_LINHA, arquivo); // Pula o cabeçalho novamente
    int i = 0;
    while (fgets(bufferLinha, TAM_LINHA, arquivo) != NULL) {
        carregarJogoDaLinha(&vetorTodosJogos[i], bufferLinha);
        i++;
    }
    fclose(arquivo);

    char idEntrada[TAM_CAMPO];
    Jogo* vetorParaOrdenar = (Jogo*) malloc(sizeof(Jogo) * totalJogos); // Aloca espaço suficiente
    int numParaOrdenar = 0;
    
    while (fgets(idEntrada, TAM_CAMPO, stdin) != NULL) {
        idEntrada[strcspn(idEntrada, "\n")] = 0; 
        if (strcmp(idEntrada, "FIM") == 0) {
            break;
        }

        int idAlvo = atoi(idEntrada);
        for (i = 0; i < totalJogos; i++) {
            if (vetorTodosJogos[i].id == idAlvo) {
                vetorParaOrdenar[numParaOrdenar] = vetorTodosJogos[i];
                numParaOrdenar++;
                break;
            }
        }
    }

    clock_t tempoInicio = clock();
    ordenacaoSelecao(vetorParaOrdenar, numParaOrdenar);
    clock_t tempoFim = clock();
    double tempoTotal = ((double)(tempoFim - tempoInicio)) / CLOCKS_PER_SEC;

    for (i = 0; i < numParaOrdenar; i++) {
        imprimirJogo(&vetorParaOrdenar[i]);
    }

    gerarArquivoLog("885637", g_comparacoes, g_movimentacoes, tempoTotal);

    free(vetorParaOrdenar);
    
    for (i = 0; i < totalJogos; i++) {
        liberarMemoriaJogo(&vetorTodosJogos[i]);
    }
    free(vetorTodosJogos);

    return 0;
}

// Implementação do Selection Sort (função renomeada e variáveis internas)
void ordenacaoSelecao(Jogo* jogos, int n) {
    int i, j, indiceMenor;
    
    for (i = 0; i < n - 1; i++) {
        indiceMenor = i;
        for (j = i + 1; j < n; j++) {
            g_comparacoes++;
            if (strcmp(jogos[j].name, jogos[indiceMenor].name) < 0) {
                indiceMenor = j;
            }
        }
        
        if (indiceMenor != i) {
            trocarJogos(&jogos[indiceMenor], &jogos[i]);
            g_movimentacoes += 3; // Cada troca conta como 3 movimentações
        }
    }
}

// Função para trocar dois jogos (renomeada)
void trocarJogos(Jogo* jogoA, Jogo* jogoB) {
    Jogo tempJogo = *jogoA;
    *jogoA = *jogoB;
    *jogoB = tempJogo;
}

// Função para criar arquivo de log (renomeada)
void gerarArquivoLog(const char* matricula, int numComp, int numMov, double tempoExec) {
    FILE* arquivoLog = fopen("suamatricula_selecao.txt", "w");
    if (arquivoLog != NULL) {
        fprintf(arquivoLog, "%s\t%d\t%d\t%f\n", matricula, numComp, numMov, tempoExec);
        fclose(arquivoLog);
    }
}

// Função que preenche uma struct Jogo a partir de uma linha do CSV (renomeada)
void carregarJogoDaLinha(Jogo* jogo, char* linha) {
    int posicao = 0;

    jogo->id = atoi(obterCampo(linha, &posicao));
    jogo->name = obterCampo(linha, &posicao);
    jogo->releaseDate = ajustarFormatoData(obterCampo(linha, &posicao));
    jogo->estimatedOwners = atoi(obterCampo(linha, &posicao));
    
    char* precoStr = obterCampo(linha, &posicao);
    jogo->price = (strcmp(precoStr, "Free to Play") == 0 || strlen(precoStr) == 0) ? 0.0f : atof(precoStr);
    free(precoStr);

    char* idiomasStr = obterCampo(linha, &posicao);
    idiomasStr[strcspn(idiomasStr, "]")] = 0; 
    memmove(idiomasStr, idiomasStr + 1, strlen(idiomasStr)); 
    for(int i = 0; idiomasStr[i]; i++) if(idiomasStr[i] == '\'') idiomasStr[i] = ' ';
    jogo->supportedLanguages = quebrarString(idiomasStr, ',', &jogo->supportedLanguagesCount);
    free(idiomasStr);
    
    jogo->metacriticScore = atoi(obterCampo(linha, &posicao));
    jogo->userScore = atof(obterCampo(linha, &posicao));
    jogo->achievements = atoi(obterCampo(linha, &posicao));

    jogo->publishers = quebrarString(obterCampo(linha, &posicao), ',', &jogo->publishersCount);
    jogo->developers = quebrarString(obterCampo(linha, &posicao), ',', &jogo->developersCount);
    jogo->categories = quebrarString(obterCampo(linha, &posicao), ',', &jogo->categoriesCount);
    jogo->genres = quebrarString(obterCampo(linha, &posicao), ',', &jogo->genresCount);
    jogo->tags = quebrarString(obterCampo(linha, &posicao), ',', &jogo->tagsCount);
}

// Imprime uma struct Jogo no formato exigido (renomeada)
void imprimirJogo(Jogo* jogo) {
    char dataStrFormatada[12];
    strcpy(dataStrFormatada, jogo->releaseDate);
    if(dataStrFormatada[1] == '/') { // Adiciona zero à esquerda para dias 1-9
        memmove(dataStrFormatada + 1, dataStrFormatada, strlen(dataStrFormatada) + 1);
        dataStrFormatada[0] = '0';
    }

    printf("=> %d ## %s ## %s ## %d ## %.2f ## ", 
        jogo->id, jogo->name, dataStrFormatada, jogo->estimatedOwners, jogo->price);
    imprimirVetorString(jogo->supportedLanguages, jogo->supportedLanguagesCount);
    printf(" ## %d ## %.1f ## %d ## ", 
        jogo->metacriticScore == 0 ? -1 : jogo->metacriticScore, 
        jogo->userScore == 0 ? -1.0f : jogo->userScore, 
        jogo->achievements);
    imprimirVetorString(jogo->publishers, jogo->publishersCount);
    printf(" ## ");
    imprimirVetorString(jogo->developers, jogo->developersCount);
    printf(" ## ");
    imprimirVetorString(jogo->categories, jogo->categoriesCount);
    printf(" ## ");
    imprimirVetorString(jogo->genres, jogo->genresCount);
    printf(" ## ");
    imprimirVetorString(jogo->tags, jogo->tagsCount);
    printf(" ##\n");
}

// Libera a memória de uma única struct Jogo (renomeada)
void liberarMemoriaJogo(Jogo* jogo) {
    free(jogo->name);
    free(jogo->releaseDate);
    for (int i = 0; i < jogo->supportedLanguagesCount; i++) free(jogo->supportedLanguages[i]);
    free(jogo->supportedLanguages);
    for (int i = 0; i < jogo->publishersCount; i++) free(jogo->publishers[i]);
    free(jogo->publishers);
    for (int i = 0; i < jogo->developersCount; i++) free(jogo->developers[i]);
    free(jogo->developers);
    for (int i = 0; i < jogo->categoriesCount; i++) free(jogo->categories[i]);
    free(jogo->categories);
    for (int i = 0; i < jogo->genresCount; i++) free(jogo->genres[i]);
    free(jogo->genres);
    for (int i = 0; i < jogo->tagsCount; i++) free(jogo->tags[i]);
    free(jogo->tags);
}

// Pega o próximo campo do CSV, tratando aspas (renomeada)
char* obterCampo(char* linha, int* pos) {
    char* campo = (char*) malloc(sizeof(char) * TAM_CAMPO);
    int i = 0;
    bool entreAspas = false;
    
    if (linha[*pos] == '"') {
        entreAspas = true;
        (*pos)++; // Pula a aspa inicial
    }
    
    while (linha[*pos] != '\0') {
        if (entreAspas) {
            if (linha[*pos] == '"') {
                (*pos)++; // Pula a aspa final
                break;
            }
        } else {
            if (linha[*pos] == ',') {
                break; // Fim do campo
            }
        }
        campo[i++] = linha[(*pos)++];
    }
    
    if (linha[*pos] == ',') {
        (*pos)++; // Pula a vírgula
    }
    
    campo[i] = '\0';
    return campo;
}

// Divide uma string em um array de strings (renomeada)
char** quebrarString(const char* str, char delimitador, int* contagem) {
    int contagemInicial = 0;
    for(int i = 0; str[i]; i++) if(str[i] == delimitador) contagemInicial++;
    *contagem = contagemInicial + 1;

    char** resultado = (char**) malloc(sizeof(char*) * (*contagem));
    char buffer[TAM_CAMPO];
    int str_idx = 0;
    int resultado_idx = 0;

    for (int i = 0; i <= strlen(str); i++) {
        if (str[i] == delimitador || str[i] == '\0') {
            buffer[str_idx] = '\0';
            resultado[resultado_idx] = (char*) malloc(sizeof(char) * (strlen(buffer) + 1));
            strcpy(resultado[resultado_idx], apararString(buffer));
            resultado_idx++;
            str_idx = 0;
        } else {
            buffer[str_idx++] = str[i];
        }
    }
    return resultado;
}

// Remove espaços das bordas (renomeada)
char* apararString(char* str) {
    char *fim;
    while(isspace((unsigned char)*str)) str++;
    if(*str == 0) return str;
    fim = str + strlen(str) - 1;
    while(fim > str && isspace((unsigned char)*fim)) fim--;
    fim[1] = '\0';
    return str;
}

// Formata a data para dd/MM/yyyy (renomeada e lógica alterada)
char* ajustarFormatoData(char* strData) {
    char* dataFormatada = (char*) malloc(sizeof(char) * 12);
    char strMes[4] = {0};
    char dia[3] = "01";
    char ano[5] = "0000";

    sscanf(strData, "%s", strMes);

    const char* abrevMeses[12] = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    const char* numMeses[12] = {
        "01", "02", "03", "04", "05", "06",
        "07", "08", "09", "10", "11", "12"
    };

    char* numMes = "01"; // Padrão
    for (int k = 0; k < 12; k++) {
        if (strcmp(strMes, abrevMeses[k]) == 0) {
            numMes = (char*)numMeses[k];
            break;
        }
    }

    char* ptr = strData;
    while(*ptr && !isdigit(*ptr)) ptr++; // Avança até o primeiro dígito (dia ou ano)
    if(isdigit(*ptr)) {
        // Tenta ler "dd, yyyy" ou "yyyy" (caso o dia não exista)
        if (sscanf(ptr, "%[^,], %s", dia, ano) != 2) {
             // Se falhar, tenta ler só o ano (formato "MMM yyyy")
             sscanf(ptr, "%s", ano);
             strcpy(dia, "01"); // Assume dia 01 se não for especificado
        }
    }
    
    sprintf(dataFormatada, "%s/%s/%s", dia, numMes, ano);
    free(strData); // Libera a string de data original
    return dataFormatada;
}


// Imprime um array de strings (renomeada)
void imprimirVetorString(char** arr, int contagem) {
    printf("[");
    for (int i = 0; i < contagem; i++) {
        printf("%s", arr[i]);
        if (i < contagem - 1) {
            printf(", ");
        }
    }
    printf("]");
}