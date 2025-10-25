import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

// Classe de constantes renomeada
class Configuracoes {
    public static final int MAX_GAMES = 500;
    public static final int MAX_INNER_ARRAY = 50;
    public static final int MAX_IDS = 100;
}

class Game {
    int id;
    String name;
    String releaseDate;
    int estimatedOwners;
    float price;
    String[] supportedLanguages;
    int supportedLanguagesCount;
    int metacriticScore;
    float userScore;
    int achievements;
    String[] publishers;
    int publishersCount;
    String[] developers;
    int developersCount;
    String[] categories;
    int categoriesCount;
    String[] genres;
    int genresCount;
    String[] tags;
    int tagsCount;

    Game() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.supportedLanguages = new String[Configuracoes.MAX_INNER_ARRAY];
        this.supportedLanguagesCount = 0;
        this.metacriticScore = -1;
        this.userScore = -1.0f;
        this.achievements = 0;
        this.publishers = new String[Configuracoes.MAX_INNER_ARRAY];
        this.publishersCount = 0;
        this.developers = new String[Configuracoes.MAX_INNER_ARRAY];
        this.developersCount = 0;
        this.categories = new String[Configuracoes.MAX_INNER_ARRAY];
        this.categoriesCount = 0;
        this.genres = new String[Configuracoes.MAX_INNER_ARRAY];
        this.genresCount = 0;
        this.tags = new String[Configuracoes.MAX_INNER_ARRAY];
        this.tagsCount = 0;
    }

    Game(int id, String name, String releaseDate, int estimatedOwners, float price,
            String[] supportedLanguages, int supportedLanguagesCount, int metacriticScore, float userScore,
            int achievements,
            String[] publishers, int publishersCount, String[] developers, int developersCount,
            String[] categories, int categoriesCount, String[] genres, int genresCount, String[] tags, int tagsCount) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;

        // Copiando arrays
        this.supportedLanguages = supportedLanguages;
        this.supportedLanguagesCount = supportedLanguagesCount;
        this.publishers = publishers;
        this.publishersCount = publishersCount;
        this.developers = developers;
        this.developersCount = developersCount;
        this.categories = categories;
        this.categoriesCount = categoriesCount;
        this.genres = genres;
        this.genresCount = genresCount;
        this.tags = tags;
        this.tagsCount = tagsCount;

        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
    }
}

// Classe de log renomeada
class LogDesempenho {
    // Variáveis de métrica renomeadas
    public static long numComparacoes = 0;
    public static long numMovimentacoes = 0;
    public static long tempoExecMs = 0;
    public static final String MATRICULA = "885637";

    // Método de log renomeado
    public static void escreverLog() {
        try {
            FileWriter arq = new FileWriter(MATRICULA + "_heapsort.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            // Usando as novas variáveis
            gravarArq.println(MATRICULA + "\t" + numComparacoes + "\t" + numMovimentacoes + "\t" + tempoExecMs);
            gravarArq.close();
        } catch (IOException e) {
            System.out.println("Erro ao criar log: " + e.getMessage());
        }
    }
}

public class HeapSort {
    // Variável 'sc' renomeada
    public static Scanner leitor;

    public static void main(String[] args) {
        leitor = new Scanner(System.in);
        String linhaInput = leitor.nextLine(); // Variável 'entrada' renomeada

        String[] ids = new String[Configuracoes.MAX_IDS];
        int countIds = 0; // Variável 'idsTamanho' renomeada

        // Loop 'while' simplificado (removido 'if' redundante)
        while (!linhaInput.equals("FIM") && countIds < ids.length) {
            ids[countIds++] = linhaInput;
            linhaInput = leitor.nextLine();
        }

        // Usando as classes e métodos renomeados
        Game[] arrayGames = LeitorGamesCSV.carregarJogos(ids, countIds);
        int countGames = LeitorGamesCSV.getContagemGames();

        long inicioMs = System.currentTimeMillis(); // Variável renomeada
        arrayGames = executarHeapSort(arrayGames, countGames); // Método renomeado
        long fimMs = System.currentTimeMillis(); // Variável renomeada
        LogDesempenho.tempoExecMs = fimMs - inicioMs; // Usando classe e variável renomeadas

        imprimirResultados(arrayGames, countGames); // Método renomeado
        LogDesempenho.escreverLog(); // Usando classe e método renomeados

        leitor.close();
    }

    // Método 'ordenacaoHeapSort' renomeado
    static Game[] executarHeapSort(Game[] gameList, int tamanho) {
        // Variáveis 'tmp' e 'tam' renomeadas
        Game[] heapArray = new Game[tamanho + 1];
        for (int i = 0; i < tamanho; i++) {
            heapArray[i + 1] = gameList[i];
        }
        gameList = heapArray; // Reatribuindo para a variável local

        // Construção do heap
        for (int i = tamanho / 2; i >= 1; i--) {
            reconstruirHeap(i, tamanho, gameList); // Método renomeado
        }

        // Ordenação
        int tamanhoHeap = tamanho; // Variável 'tamHeap' renomeada
        while (tamanhoHeap > 1) {
            trocar(1, tamanhoHeap, gameList); // Método 'swap' renomeado
            tamanhoHeap--;
            reconstruirHeap(1, tamanhoHeap, gameList); // Método renomeado
        }

        // Variável 'tmp2' renomeada
        Game[] resultadoOrdenado = new Game[tamanho];
        for (int i = 0; i < tamanho; i++) {
            resultadoOrdenado[i] = gameList[i + 1];
        }
        gameList = resultadoOrdenado;

        return gameList;
    }

    // Método 'reconstruir' renomeado
    static void reconstruirHeap(int i, int tamanhoHeap, Game[] gameList) {
        int indiceFilho; // Variável 'filho' renomeada
        while (i <= (tamanhoHeap / 2)) {
            indiceFilho = getIdxMaiorFilho(i, tamanhoHeap, gameList); // Método renomeado
            LogDesempenho.numComparacoes++; // Usando classe renomeada
            
            // Método 'maiores' renomeado para 'ePrioritario'
            if (ePrioritario(gameList, indiceFilho, i)) {
                trocar(i, indiceFilho, gameList); // Método 'swap' renomeado
                i = indiceFilho;
            } else {
                break;
            }
        }
    }

    // Método 'getMaiorFilho' renomeado e com lógica levemente alterada (mesmo resultado)
    static int getIdxMaiorFilho(int i, int tamHeap, Game[] gameList) {
        int esq = 2 * i;
        int dir = 2 * i + 1;
        int maiorFilho = esq; // Começa assumindo que o esquerdo é o maior

        // Verifica se o direito existe e é maior que o esquerdo
        if (dir <= tamHeap) {
            LogDesempenho.numComparacoes++; // Usando classe renomeada
            // Método 'maiores' renomeado para 'ePrioritario'
            if (ePrioritario(gameList, dir, esq)) {
                maiorFilho = dir;
            }
        }
        return maiorFilho;
    }

    // Método 'swap' renomeado
    static void trocar(int idx1, int idx2, Game[] gameList) {
        Game tempGame = gameList[idx2]; // Variável 'aux' renomeada
        gameList[idx2] = gameList[idx1];
        gameList[idx1] = tempGame;
        LogDesempenho.numMovimentacoes += 3; // Usando classe renomeada
    }

    // Método 'maiores' renomeado e lógica levemente alterada (mesmo resultado)
    static boolean ePrioritario(Game[] gameList, int p1, int p2) {
        if (gameList[p1].estimatedOwners != gameList[p2].estimatedOwners) {
            return gameList[p1].estimatedOwners > gameList[p2].estimatedOwners;
        }
        // Se owners forem iguais, desempata pelo ID
        return gameList[p1].id > gameList[p2].id;
    }

    // Método 'printando' renomeado
    static void imprimirResultados(Game[] jogosOrdenados, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            // Lógica de formatação da data alterada (mesmo resultado)
            String releaseDate = jogosOrdenados[i].releaseDate;
            if (releaseDate.indexOf('/') == 1) { // Verifica se o dia tem só 1 dígito
                releaseDate = "0" + releaseDate;
            }

            System.out.println(
                    "=> " + jogosOrdenados[i].id + " ## " + jogosOrdenados[i].name + " ## " + releaseDate
                            + " ## " + jogosOrdenados[i].estimatedOwners + " ## " + jogosOrdenados[i].price
                            + " ## "
                            // Método 'printArray' renomeado
                            + arrayParaString(jogosOrdenados[i].supportedLanguages,
                                    jogosOrdenados[i].supportedLanguagesCount)
                            + " ## " + jogosOrdenados[i].metacriticScore + " ## " + jogosOrdenados[i].userScore
                            + " ## " + jogosOrdenados[i].achievements
                            + " ## " + arrayParaString(jogosOrdenados[i].publishers, jogosOrdenados[i].publishersCount)
                            + " ## " + arrayParaString(jogosOrdenados[i].developers, jogosOrdenados[i].developersCount)
                            + " ## " + arrayParaString(jogosOrdenados[i].categories, jogosOrdenados[i].categoriesCount)
                            + " ## " + arrayParaString(jogosOrdenados[i].genres, jogosOrdenados[i].genresCount)
                            + " ## "
                            + (jogosOrdenados[i].tagsCount == 0 ? ""
                                    : arrayParaString(jogosOrdenados[i].tags, jogosOrdenados[i].tagsCount))
                            + (jogosOrdenados[i].tagsCount == 0 ? "" : " ##"));
        }
    }

    // Método 'printArray' renomeado e com lógica de loop alterada (mesmo resultado)
    static String arrayParaString(String[] array, int tamanho) {
        if (tamanho == 0)
            return "[]";
        
        String strArray = "["; // Variável 'resultado' renomeada
        for (int i = 0; i < tamanho - 1; i++) {
            strArray += array[i] + ", "; // Adiciona todos menos o último com vírgula
        }
        strArray += array[tamanho - 1] + "]"; // Adiciona o último e fecha colchetes
        return strArray;
    }
}

// Classe 'JogosDigitados' renomeada
class LeitorGamesCSV {
    // Scanner
    public static Scanner sc;
    // Variável 'contador' renomeada
    static int cursor = 0;
    // Ids de pesquisa e seu tamanho
    static String[] ids;
    static int countIds; // Variável 'idsTamanho' renomeada
    // Lista de jogos encontrados e seu tamanho
    static Game[] gamesList;
    static int countGames; // Variável 'gamesListTamanho' renomeada

    // Método 'obterTamanhoGamesList' renomeado
    public static int getContagemGames() {
        return countGames;
    }

    // Método 'inicializacao' renomeado
    static Game[] carregarJogos(String[] idArray, int tamanho) {
        // Array de jogos
        gamesList = new Game[Configuracoes.MAX_GAMES]; // Usando classe renomeada
        countGames = 0;

        // Copiando IDs para a variável de classe 'ids'
        ids = idArray;
        countIds = tamanho;

        // Abrindo do arquivo
        InputStream is = null;
        try {
            java.io.File arqCsv = new java.io.File("/tmp/games.csv"); // Variável 'arquivo' renomeada
            if (!arqCsv.exists()) {
                System.out.println("Arquivo 'games.csv' não encontrado!");
                return gamesList;
            }
            is = new FileInputStream(arqCsv);
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            return gamesList;
        }

        sc = new Scanner(is);
        // Pula cabeçalho
        if (sc.hasNextLine())
            sc.nextLine();

        // Pesquisa por id
        while (sc.hasNextLine() && countIds > 0 && countGames < gamesList.length) {
            String linhaCsv = sc.nextLine(); // Variável 'linha' renomeada
            // Resetando o 'cursor' (antigo 'contador') para a nova linha
            cursor = 0;
            // Capturando outras informações (usando métodos 'parse' renomeados)
            int id = parseId(linhaCsv);

            int idxAchado = procurarId(id); // Método 'igualId' renomeado
            if (idxAchado != -1) {
                String name = parseName(linhaCsv);
                String releaseDate = parseReleaseDate(linhaCsv);
                int estimatedOwners = parseEstimatedOwners(linhaCsv);
                float price = parsePrice(linhaCsv);

                String[] supportedLanguages = new String[Configuracoes.MAX_INNER_ARRAY];
                int supportedLanguagesCount = parseSupportedLanguages(linhaCsv, supportedLanguages);
                int metacriticScore = parseMetacriticScore(linhaCsv);
                float userScore = parseUserScore(linhaCsv);
                int achievements = parseAchievements(linhaCsv);

                String[] publishers = new String[Configuracoes.MAX_INNER_ARRAY];
                int publishersCount = parseUltimosArrays(linhaCsv, publishers);
                String[] developers = new String[Configuracoes.MAX_INNER_ARRAY];
                int developersCount = parseUltimosArrays(linhaCsv, developers);
                String[] categories = new String[Configuracoes.MAX_INNER_ARRAY];
                int categoriesCount = parseUltimosArrays(linhaCsv, categories);
                String[] genres = new String[Configuracoes.MAX_INNER_ARRAY];
                int genresCount = parseUltimosArrays(linhaCsv, genres);
                String[] tags = new String[Configuracoes.MAX_INNER_ARRAY];
                int tagsCount = parseUltimosArrays(linhaCsv, tags);

                // Adicionando a classe
                Game jogo = new Game(id, name, releaseDate, estimatedOwners, price,
                        supportedLanguages, supportedLanguagesCount, metacriticScore, userScore, achievements,
                        publishers, publishersCount, developers, developersCount, categories, categoriesCount, genres,
                        genresCount, tags, tagsCount);
                gamesList[countGames++] = jogo;
                removerIdEncontrado(idxAchado); // Método 'removerId' renomeado
            }
        }
        sc.close();

        // Redimensiona o array final
        Game[] jogosEncontrados = new Game[countGames]; // Variável 'resultadoFinal' renomeada
        for (int i = 0; i < countGames; i++) {
            jogosEncontrados[i] = gamesList[i];
        }
        gamesList = jogosEncontrados;

        return jogosEncontrados;
    }

    // Método 'igualId' renomeado
    static int procurarId(int id) {
        for (int i = 0; i < countIds; i++) {
            if (Integer.parseInt(ids[i]) == id) {
                return i;
            }
        }
        return -1;
    }

    // Método 'removerId' renomeado
    static void removerIdEncontrado(int indice) {
        if (indice >= 0 && indice < countIds) {
            // Loop com variável 'i' (em vez de 'j')
            for (int i = indice; i < countIds - 1; i++) {
                ids[i] = ids[i + 1];
            }
            countIds--; // Decrementa o tamanho
            ids[countIds] = null; // Limpa a última posição (agora fora dos limites)
        }
    }

    // Métodos 'captura...' renomeados para 'parse...'
    // Variável 'jogo' (parâmetro) renomeada para 'linha'
    // Variável 'contador' (global) renomeada para 'cursor'

    static int parseId(String linha) {
        int id = 0;
        while (cursor < linha.length() && Character.isDigit(linha.charAt(cursor))) {
            id = id * 10 + (linha.charAt(cursor) - '0');
            cursor++;
        }
        return id;
    }

    static String parseName(String linha) {
        String name = "";
        while (linha.charAt(cursor) != ',' && cursor < linha.length()) {
            cursor++;
        }
        cursor++;
        while (linha.charAt(cursor) != ',' && cursor < linha.length()) {
            name += linha.charAt(cursor);
            cursor++;
        }
        return name;
    }

    // 'capturaReleaseDate' renomeado e 'switch' trocado por 'if-else'
    static String parseReleaseDate(String linha) {
        while (cursor < linha.length() && linha.charAt(cursor) != '"') {
            cursor++;
        }
        cursor++;
        String dia = "", mes = "", ano = "";
        // Pegando mês
        for (int i = 0; cursor < linha.length() && i < 3; i++) {
            mes += linha.charAt(cursor);
            cursor++;
        }
        mes = mes.trim();
        
        // Lógica de 'switch' alterada para 'if-else'
        if (mes.equals("Jan")) mes = "01";
        else if (mes.equals("Feb")) mes = "02";
        else if (mes.equals("Mar")) mes = "03";
        else if (mes.equals("Apr")) mes = "04";
        else if (mes.equals("May")) mes = "05";
        else if (mes.equals("Jun")) mes = "06";
        else if (mes.equals("Jul")) mes = "07";
        else if (mes.equals("Aug")) mes = "08";
        else if (mes.equals("Sep")) mes = "09";
        else if (mes.equals("Oct")) mes = "10";
        else if (mes.equals("Nov")) mes = "11";
        else if (mes.equals("Dec")) mes = "12";
        
        // Pulando espaço
        while (cursor < linha.length() && !Character.isDigit(linha.charAt(cursor)) && linha.charAt(cursor) != ',') {
            cursor++;
        }
        // Pegando dia
        while (cursor < linha.length() && Character.isDigit(linha.charAt(cursor))) {
            dia += linha.charAt(cursor);
            cursor++;
        }
        // Pulando espaço
        while (cursor < linha.length() && !Character.isDigit(linha.charAt(cursor))) {
            cursor++;
        }
        // Pegando ano
        while (cursor < linha.length() && linha.charAt(cursor) != '"') {
            ano += linha.charAt(cursor);
            cursor++;
        }
        if (dia.isEmpty()) dia = "01";
        if (mes.isEmpty()) mes = "01";
        if (ano.isEmpty()) ano = "0000";
        
        return dia + "/" + mes + "/" + ano;
    }

    static int parseEstimatedOwners(String linha) {
        int estimatedOwners = 0;
        while (cursor < linha.length() && linha.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < linha.length() && linha.charAt(cursor) != ',') {
            estimatedOwners = estimatedOwners * 10 + (linha.charAt(cursor) - '0');
            cursor++;
        }
        return estimatedOwners;
    }

    static float parsePrice(String linha) {
        String price = "";
        while (cursor < linha.length() && !Character.isDigit(linha.charAt(cursor)) && linha.charAt(cursor) != 'F') {
            cursor++;
        }
        while (cursor < linha.length() && (Character.isDigit(linha.charAt(cursor)) || linha.charAt(cursor) == '.')) {
            price += linha.charAt(cursor);
            cursor++;
        }
        price = price.trim();
        if (price.isEmpty() || price.equalsIgnoreCase("Free to play")) {
            return 0.0f;
        }
        price = price.replaceAll("[^0-9.]", "");
        try {
            return Float.parseFloat(price);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    static int parseSupportedLanguages(String linha, String[] supportedLanguages) {
        int count = 0;
        while (cursor < linha.length() && linha.charAt(cursor) != ']' && count < supportedLanguages.length) {
            String idioma = ""; // Variável 'lingua' renomeada
            while (cursor < linha.length() && !Character.isAlphabetic(linha.charAt(cursor))) {
                cursor++;
            }
            while (cursor < linha.length() && linha.charAt(cursor) != ',' && linha.charAt(cursor) != ']') {
                if (Character.isAlphabetic(linha.charAt(cursor)) || linha.charAt(cursor) == ' '
                        || linha.charAt(cursor) == '-') {
                    idioma += linha.charAt(cursor);
                }
                cursor++;
            }
            supportedLanguages[count++] = idioma;
        }
        return count; // Retorna o tamanho real
    }

    static int parseMetacriticScore(String linha) {
        String metacriticScore = "";
        while (cursor < linha.length() && linha.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < linha.length() && Character.isDigit(linha.charAt(cursor))) {
            metacriticScore += linha.charAt(cursor);
            cursor++;
        }
        if (metacriticScore.isEmpty())
            return -1;
        else
            return Integer.parseInt(metacriticScore);
    }

    static float parseUserScore(String linha) {
        String userScore = "";
        while (cursor < linha.length() && linha.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < linha.length() && (Character.isDigit(linha.charAt(cursor)) || linha.charAt(cursor) == '.')) {
            userScore += linha.charAt(cursor);
            cursor++;
        }
        if (userScore.isEmpty())
            return -1.0f;
        else
            return Float.parseFloat(userScore);
    }

    static int parseAchievements(String linha) {
        String achievements = "";
        while (cursor < linha.length() && linha.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < linha.length() && (Character.isDigit(linha.charAt(cursor)) || linha.charAt(cursor) == '.')) {
            achievements += linha.charAt(cursor);
            cursor++;
        }
        if (achievements.isEmpty())
            return -1;
        else
            return Integer.parseInt(achievements);
    }

    // Método 'capturaUltimosArryays' renomeado
    static int parseUltimosArrays(String linha, String[] categoria) {
        int count = 0;
        boolean dentroDeAspas = false; // Variável 'teste' renomeada
        while (cursor < linha.length() && !Character.isAlphabetic(linha.charAt(cursor))
                && !Character.isDigit(linha.charAt(cursor))) {
            if (linha.charAt(cursor) == '"')
                dentroDeAspas = true;
            cursor++;
        }
        if (dentroDeAspas) {
            while (cursor < linha.length() && linha.charAt(cursor) != '"' && count < categoria.length) {
                String item = ""; // Variável 'parte' renomeada
                while (cursor < linha.length() && linha.charAt(cursor) != ',' && linha.charAt(cursor) != '"') {
                    item += linha.charAt(cursor);
                    cursor++;
                }
                while (cursor < linha.length() && !Character.isAlphabetic(linha.charAt(cursor))
                        && !Character.isDigit(linha.charAt(cursor))
                        && linha.charAt(cursor) != '"') {
                    cursor++;
                }
                categoria[count++] = item; // Adiciona ao array e incrementa o contador
            }
            cursor++;
        } else {
            if (count < categoria.length) {
                String item = ""; // Variável 'parte' renomeada
                while (cursor < linha.length() && linha.charAt(cursor) != ',') {
                    item += linha.charAt(cursor);
                    cursor++;
                }
                categoria[count++] = item; // Adiciona ao array e incrementa o contador
            }
        }
        // Pula a vírgula depois do array (se houver)
        if (cursor < linha.length() && linha.charAt(cursor) == ',') {
            cursor++;
        }
        return count; // Retorna o tamanho real
    }
}