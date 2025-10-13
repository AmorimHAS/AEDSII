#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h> // Para usar o tipo 'bool'

#define TAM_LINHA 4096
#define TAM_CAMPO 512

// A struct foi traduzida para "Jogo" e seus campos para o português.
typedef struct {
    int id;
    char* nome;
    char* dataLancamento;
    int proprietariosEstimados;
    float preco;
    char** idiomasSuportados;
    int idiomasSuportadosQtd;
    int pontuacaoMetacritic;
    float pontuacaoUsuario;
    int conquistas;
    char** publicadoras;
    int publicadorasQtd;
    char** desenvolvedoras;
    int desenvolvedorasQtd;
    char** categorias;
    int categoriasQtd;
    char** generos;
    int generosQtd;
    char** tags;
    int tagsQtd;
} Jogo;

// --- Declarações das Funções ---

// Funções de manipulação de strings e dados
char* lerCampo(char* linha, int* pos);
char** dividirString(char* texto, char delimitador, int* qtd);
char* removerEspacosDasBordas(char* texto);
char* formatarData(char* dataOriginal);
bool ehEspacoBranco(char c);

// Funções de lógica do programa
void interpretarLinha(Jogo* jogo, char* linha);
void liberarMemoriaJogo(Jogo* jogo);

// Funções de exibição
void exibirJogo(Jogo* jogo);
void exibirArrayDeStrings(char** vetor, int qtd);


// --- Função Principal ---
int main() {
    const char* caminhoArquivo = "/tmp/games.csv";
    FILE* arquivo = fopen(caminhoArquivo, "r");
    if (!arquivo) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    // 1ª Leitura: Contar quantos jogos existem no arquivo para alocar memória
    char linhaBuffer[TAM_LINHA];
    int totalJogos = 0;
    fgets(linhaBuffer, TAM_LINHA, arquivo); // Pula a linha do cabeçalho
    while (fgets(linhaBuffer, TAM_LINHA, arquivo)) {
        totalJogos++;
    }
    fclose(arquivo);

    // Alocação de memória para todos os jogos de uma vez
    Jogo* todosOsJogos = (Jogo*) malloc(sizeof(Jogo) * totalJogos);
    if (!todosOsJogos) {
        printf("Falha na alocação de memória.\n");
        return 1;
    }

    // 2ª Leitura: Ler o arquivo novamente para popular a struct de cada jogo
    arquivo = fopen(caminhoArquivo, "r");
    fgets(linhaBuffer, TAM_LINHA, arquivo); // Pula o cabeçalho novamente
    int indice = 0;
    while (fgets(linhaBuffer, TAM_LINHA, arquivo)) {
        interpretarLinha(&todosOsJogos[indice], linhaBuffer);
        indice++;
    }
    fclose(arquivo);

    // Leitura da entrada do usuário (stdin)
    char entrada[TAM_CAMPO];
    while (fgets(entrada, TAM_CAMPO, stdin)) {
        entrada[strcspn(entrada, "\n")] = '\0'; // Remove o '\n' do final
        if (strcmp(entrada, "FIM") == 0) {
            break;
        }

        int idBuscado = atoi(entrada);
        for (int k = 0; k < totalJogos; k++) {
            if (todosOsJogos[k].id == idBuscado) {
                exibirJogo(&todosOsJogos[k]);
                break; // Encerra o loop interno após encontrar o jogo
            }
        }
    }

    // Liberação da memória alocada para cada jogo e para o array principal
    for (int k = 0; k < totalJogos; k++) {
        liberarMemoriaJogo(&todosOsJogos[k]);
    }
    free(todosOsJogos);

    return 0;
}

// --- Implementação das Funções ---

/**
 * Lê um campo da linha CSV, tratando campos entre aspas.
 * Avança a posição de leitura na linha.
 */
char* lerCampo(char* linha, int* pos) {
    char* campo = (char*) malloc(TAM_CAMPO);
    int i = 0;
    bool entreAspas = false;

    if (linha[*pos] == '"') {
        entreAspas = true;
        (*pos)++; // Pula a aspa inicial
    }

    while (linha[*pos] != '\0' && (entreAspas || linha[*pos] != ',')) {
        if (entreAspas && linha[*pos] == '"') {
            (*pos)++; // Pula a aspa final
            break;
        }
        campo[i++] = linha[(*pos)++];
    }

    if (linha[*pos] == ',') {
        (*pos)++; // Pula a vírgula após o campo
    }
    campo[i] = '\0';
    return campo;
}

/**
 * Divide uma string em várias substrings com base em um delimitador.
 * Retorna um array de strings.
 */
char** dividirString(char* texto, char delimitador, int* qtd) {
    int contDelimitadores = 0;
    for (int i = 0; texto[i] != '\0'; i++) {
        if (texto[i] == delimitador) {
            contDelimitadores++;
        }
    }
    *qtd = contDelimitadores + 1;

    char** partes = (char**) malloc(sizeof(char*) * (*qtd));
    int inicio = 0, indiceAtual = 0;

    for (int i = 0; ; i++) {
        if (texto[i] == delimitador || texto[i] == '\0') {
            int tamanho = i - inicio;
            partes[indiceAtual] = (char*) malloc(tamanho + 1);
            strncpy(partes[indiceAtual], texto + inicio, tamanho);
            partes[indiceAtual][tamanho] = '\0';

            // Remove espaços em branco do início e do fim da substring
            char* semEspacos = removerEspacosDasBordas(partes[indiceAtual]);
            free(partes[indiceAtual]); // Libera a string original com espaços
            partes[indiceAtual] = semEspacos; // Atribui a nova string tratada

            indiceAtual++;
            inicio = i + 1;
        }
        if (texto[i] == '\0') {
            break;
        }
    }
    return partes;
}

/**
 * Verifica se um caractere é um espaço em branco (substitui isspace).
 */
bool ehEspacoBranco(char c) {
    return c == ' ' || c == '\f' || c == '\n' || c == '\r' || c == '\t' || c == '\v';
}

/**
 * Remove espaços em branco do início e do fim de uma string.
 * Retorna uma nova string alocada dinamicamente.
 */
char* removerEspacosDasBordas(char* texto) {
    // Avança o ponteiro para ignorar espaços no início
    while (ehEspacoBranco(*texto)) {
        texto++;
    }

    // Aloca memória para a nova string e copia o conteúdo
    char* copia = (char*) malloc(strlen(texto) + 1);
    strcpy(copia, texto);

    // Remove espaços do final da nova string
    char* fim = copia + strlen(copia) - 1;
    while (fim > copia && ehEspacoBranco(*fim)) {
        *fim-- = '\0';
    }

    return copia;
}

/**
 * Converte a data do formato "Mmm dd, yyyy" para "dd/MM/yyyy".
 */
char* formatarData(char* dataOriginal) {
    char* novaData = (char*) malloc(12);
    char dia[3] = "01", mesAbrev[4] = {0}, ano[5] = "0000";

    sscanf(dataOriginal, "%3s %[^,], %s", mesAbrev, dia, ano);

    char* mesNum = "01";
    if (strcmp(mesAbrev, "Feb") == 0) mesNum = "02";
    else if (strcmp(mesAbrev, "Mar") == 0) mesNum = "03";
    else if (strcmp(mesAbrev, "Apr") == 0) mesNum = "04";
    else if (strcmp(mesAbrev, "May") == 0) mesNum = "05";
    else if (strcmp(mesAbrev, "Jun") == 0) mesNum = "06";
    else if (strcmp(mesAbrev, "Jul") == 0) mesNum = "07";
    else if (strcmp(mesAbrev, "Aug") == 0) mesNum = "08";
    else if (strcmp(mesAbrev, "Sep") == 0) mesNum = "09";
    else if (strcmp(mesAbrev, "Oct") == 0) mesNum = "10";
    else if (strcmp(mesAbrev, "Nov") == 0) mesNum = "11";
    else if (strcmp(mesAbrev, "Dec") == 0) mesNum = "12";

    sprintf(novaData, "%s/%s/%s", dia, mesNum, ano);
    free(dataOriginal); // Libera a string de data original que não será mais usada
    return novaData;
}

/**
 * Lê uma linha inteira do CSV e preenche a struct Jogo.
 */
void interpretarLinha(Jogo* jogo, char* linha) {
    int pos = 0;
    
    char* campoId = lerCampo(linha, &pos);
    jogo->id = atoi(campoId);
    free(campoId);
    
    jogo->nome = lerCampo(linha, &pos);
    jogo->dataLancamento = formatarData(lerCampo(linha, &pos));
    
    char* campoProp = lerCampo(linha, &pos);
    jogo->proprietariosEstimados = atoi(campoProp);
    free(campoProp);
    
    char* campoPreco = lerCampo(linha, &pos);
    jogo->preco = (campoPreco[0] == '\0' || strcmp(campoPreco, "Free to Play") == 0) ? 0.0f : atof(campoPreco);
    free(campoPreco);

    char* campoLinguas = lerCampo(linha, &pos);
    if (campoLinguas[0] == '[') memmove(campoLinguas, campoLinguas + 1, strlen(campoLinguas)); // Remove '['
    campoLinguas[strcspn(campoLinguas, "]")] = '\0'; // Remove ']'
    for (int i = 0; campoLinguas[i]; i++) if (campoLinguas[i] == '\'') campoLinguas[i] = ' '; // Troca ' por espaço
    jogo->idiomasSuportados = dividirString(campoLinguas, ',', &jogo->idiomasSuportadosQtd);
    free(campoLinguas);

    char* campoScore = lerCampo(linha, &pos);
    jogo->pontuacaoMetacritic = atoi(campoScore);
    free(campoScore);
    
    char* campoUserScore = lerCampo(linha, &pos);
    jogo->pontuacaoUsuario = atof(campoUserScore);
    free(campoUserScore);
    
    char* campoConquistas = lerCampo(linha, &pos);
    jogo->conquistas = atoi(campoConquistas);
    free(campoConquistas);

    char* campoPubs = lerCampo(linha, &pos);
    jogo->publicadoras = dividirString(campoPubs, ',', &jogo->publicadorasQtd);
    free(campoPubs);
    
    char* campoDevs = lerCampo(linha, &pos);
    jogo->desenvolvedoras = dividirString(campoDevs, ',', &jogo->desenvolvedorasQtd);
    free(campoDevs);

    char* campoCats = lerCampo(linha, &pos);
    jogo->categorias = dividirString(campoCats, ',', &jogo->categoriasQtd);
    free(campoCats);
    
    char* campoGens = lerCampo(linha, &pos);
    jogo->generos = dividirString(campoGens, ',', &jogo->generosQtd);
    free(campoGens);

    char* campoTags = lerCampo(linha, &pos);
    jogo->tags = dividirString(campoTags, ',', &jogo->tagsQtd);
    free(campoTags);
}

/**
 * Exibe um array de strings no formato [str1, str2, ...].
 */
void exibirArrayDeStrings(char** vetor, int qtd) {
    printf("[");
    for (int i = 0; i < qtd; i++) {
        printf("%s", vetor[i]);
        if (i < qtd - 1) printf(", ");
    }
    printf("]");
}

/**
 * Exibe todos os campos de um Jogo no formato de saída especificado.
 */
void exibirJogo(Jogo* jogo) {
    char dataFormatada[12];
    strcpy(dataFormatada, jogo->dataLancamento);
    if (dataFormatada[1] == '/') { // Adiciona zero à esquerda para dias < 10
        memmove(dataFormatada + 1, dataFormatada, strlen(dataFormatada) + 1);
        dataFormatada[0] = '0';
    }

    printf("=> %d ## %s ## %s ## %d ## %.2f ## ",
           jogo->id, jogo->nome, dataFormatada, jogo->proprietariosEstimados, jogo->preco);
    
    exibirArrayDeStrings(jogo->idiomasSuportados, jogo->idiomasSuportadosQtd);
    
    printf(" ## %d ## %.1f ## %d ## ",
           jogo->pontuacaoMetacritic ? jogo->pontuacaoMetacritic : -1,
           jogo->pontuacaoUsuario > 0.0f ? jogo->pontuacaoUsuario : -1.0f,
           jogo->conquistas);
           
    exibirArrayDeStrings(jogo->publicadoras, jogo->publicadorasQtd); printf(" ## ");
    exibirArrayDeStrings(jogo->desenvolvedoras, jogo->desenvolvedorasQtd); printf(" ## ");
    exibirArrayDeStrings(jogo->categorias, jogo->categoriasQtd); printf(" ## ");
    exibirArrayDeStrings(jogo->generos, jogo->generosQtd); printf(" ## ");
    exibirArrayDeStrings(jogo->tags, jogo->tagsQtd); printf(" ##\n");
}

/**
 * Libera toda a memória alocada dinamicamente dentro de uma struct Jogo.
 */
void liberarMemoriaJogo(Jogo* jogo) {
    free(jogo->nome);
    free(jogo->dataLancamento);

    for (int i = 0; i < jogo->idiomasSuportadosQtd; i++) free(jogo->idiomasSuportados[i]);
    free(jogo->idiomasSuportados);

    for (int i = 0; i < jogo->publicadorasQtd; i++) free(jogo->publicadoras[i]);
    free(jogo->publicadoras);

    for (int i = 0; i < jogo->desenvolvedorasQtd; i++) free(jogo->desenvolvedoras[i]);
    free(jogo->desenvolvedoras);

    for (int i = 0; i < jogo->categoriasQtd; i++) free(jogo->categorias[i]);
    free(jogo->categorias);

    for (int i = 0; i < jogo->generosQtd; i++) free(jogo->generos[i]);
    free(jogo->generos);

    for (int i = 0; i < jogo->tagsQtd; i++) free(jogo->tags[i]);
    free(jogo->tags);
}