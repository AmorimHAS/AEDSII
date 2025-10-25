import java.io.*;       // Importa classes para leitura/escrita de arquivos
import java.util.Scanner;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Imports implícitos usados em 'normalizarData':
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;

public class OrdenacaoMerge { // Classe principal renomeada

    // Matrícula do aluno - será usada no nome do arquivo de log
    private static final String MATRICULA = "885637";

    // Contadores globais para estatísticas do algoritmo (renomeados)
    private static long contagemComparacoes = 0; // Conta número de comparações
    private static long contagemMovimentacoes = 0; // Conta número de movimentações

    public static void main(String[] args) throws Exception {
        // --- 1. LEITURA DO CSV ---
        BufferedReader leitorArquivo = new BufferedReader(new FileReader("/tmp/games.csv"));
        leitorArquivo.readLine(); // Pula cabeçalho
        Jogo[] jogosTodos = new Jogo[60000]; // Classe 'Game' renomeada para 'Jogo'
        int totalJogos = 0;
        String linhaCsv;
        while ((linhaCsv = leitorArquivo.readLine()) != null) {
            String[] campos = AnalisadorCsv.parsearLinha(linhaCsv); // Classe e método renomeados
            if (campos == null || campos.length < 14) continue;
            jogosTodos[totalJogos++] = Jogo.criarDosCampos(campos); // Classe e método renomeados
        }
        leitorArquivo.close();

        // --- 2. DETECÇÃO DE DUPLICATAS (Opcional, para System.err) ---
        // (Lógica mantida, mas variáveis renomeadas)
        int[] idsUnicos = new int[totalJogos];
        int[] contagemIds = new int[totalJogos];
        int numUnicos = 0;
        
        for (int i = 0; i < totalJogos; i++) {
            int idAtual = jogosTodos[i].id;
            boolean encontrado = false;
            for (int j = 0; j < numUnicos; j++) {
                if (idsUnicos[j] == idAtual) {
                    contagemIds[j]++;
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                idsUnicos[numUnicos] = idAtual;
                contagemIds[numUnicos] = 1;
                numUnicos++;
            }
        }
        
        for (int i = 0; i < numUnicos; i++) {
            if (contagemIds[i] > 1) {
                System.err.println("Duplicado AppID=" + idsUnicos[i] + " vezes=" + contagemIds[i]);
            }
        }

        // --- 3. REMOVER DUPLICATAS (Cria array final) ---
        Jogo[] jogosUnicos = new Jogo[totalJogos];
        int[] idsJaVistos = new int[totalJogos];
        int contagemVistos = 0;
        int totalUnicos = 0;
        
        for (int i = 0; i < totalJogos; i++) {
            boolean encontrado = false;
            for (int j = 0; j < contagemVistos; j++) {
                if (idsJaVistos[j] == jogosTodos[i].id) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                jogosUnicos[totalUnicos++] = jogosTodos[i];
                idsJaVistos[contagemVistos++] = jogosTodos[i].id;
            }
        }

        // --- 4. PREPARA ARRAY PARA BUSCA BINÁRIA (Ordenado por ID) ---
        Jogo[] jogosPorId = new Jogo[totalUnicos];
        System.arraycopy(jogosUnicos, 0, jogosPorId, 0, totalUnicos); // Usando arraycopy
        ordenarRapidoPorId(jogosPorId, 0, totalUnicos - 1); // Método renomeado

        // --- 5. LÊ ENTRADA PADRÃO E BUSCA JOGOS ---
        Scanner leitorEntrada = new Scanner(System.in);
        Jogo selecaoTemporaria[] = new Jogo[100];
        int totalSelecao = 0;
        while (leitorEntrada.hasNextLine()) {
            String idString = leitorEntrada.nextLine().trim();
            if (idString.equals("FIM")) break;
            if (idString.isEmpty()) continue;
            try {
                int idParaBusca = Integer.parseInt(idString);
                int indice = buscarPorId(jogosPorId, idParaBusca, totalUnicos); // Método renomeado
                if (indice >= 0) selecaoTemporaria[totalSelecao++] = jogosPorId[indice];
            } catch (NumberFormatException e) { /* ignora */ }
        }
        leitorEntrada.close();

        // --- 6. COPIA PARA ARRAY FINAL E ORDENA (Mergesort por Preço) ---
        Jogo[] selecaoFinal = new Jogo[totalSelecao];
        System.arraycopy(selecaoTemporaria, 0, selecaoFinal, 0, totalSelecao);

        long t0 = System.nanoTime();
        if (selecaoFinal.length > 1) {
            ordenarPorPreco(selecaoFinal); // Método renomeado
        }
        long t1 = System.nanoTime();
        long duracaoNano = t1 - t0;

        // --- 7. IMPRESSÃO (Refatorada para helper) ---
        imprimirRanking("| 5 preços mais caros |", selecaoFinal, true);
        System.out.println(); // Adiciona linha em branco entre as listas
        imprimirRanking("| 5 preços mais baratos |", selecaoFinal, false);

        // --- 8. GERAÇÃO DO ARQUIVO DE LOG ---
        String nomeLog = MATRICULA + "_mergesort.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(nomeLog))) {
            // Usa variáveis de contagem renomeadas
            pw.printf("%s\t%d\t%d\t%d\n", MATRICULA, contagemComparacoes, contagemMovimentacoes, duracaoNano);
        }
    }
    
    /**
     * Helper para imprimir os 5 melhores/piores, pulando duplicatas.
     */
    private static void imprimirRanking(String titulo, Jogo[] jogos, boolean decrescente) {
        System.out.println(titulo);
        int[] idsUsados = new int[10]; // 10 é seguro, só precisamos de 5
        int contagemIdsUsados = 0;
        int impressos = 0;
        
        // Define a direção do loop
        int inicio = decrescente ? jogos.length - 1 : 0;
        int fim = decrescente ? -1 : jogos.length;
        int incremento = decrescente ? -1 : 1;

        for (int i = inicio; i != fim && impressos < 5; i += incremento) {
            Jogo j = jogos[i];
            if (j == null) continue;
            
            boolean jaVisto = false;
            for (int k = 0; k < contagemIdsUsados; k++) {
                if (idsUsados[k] == j.id) {
                    jaVisto = true;
                    break;
                }
            }
            if (jaVisto) continue;
            
            System.out.println(j.toString()); // Chama Jogo.toString()
            idsUsados[contagemIdsUsados++] = j.id;
            impressos++;
        }
    }


    // --- Quicksort por ID (Métodos renomeados) ---
    private static void ordenarRapidoPorId(Jogo[] array, int esq, int dir) {
        if (esq < dir) {
            int indicePivo = particionarPorId(array, esq, dir);
            ordenarRapidoPorId(array, esq, indicePivo - 1);
            ordenarRapidoPorId(array, indicePivo + 1, dir);
        }
    }
    
    private static int particionarPorId(Jogo[] array, int esq, int dir) {
        Jogo pivo = array[dir];
        int i = (esq - 1);
        for (int j = esq; j < dir; j++) {
            if (array[j].id <= pivo.id) {
                i++;
                Jogo troca = array[i];
                array[i] = array[j];
                array[j] = troca;
            }
        }
        Jogo troca = array[i + 1];
        array[i + 1] = array[dir];
        array[dir] = troca;
        return (i + 1);
    }

    // === MERGESORT (Métodos renomeados) ===
    private static void ordenarPorPreco(Jogo[] array) {
        if (array == null || array.length < 2) return;
        Jogo[] temp = new Jogo[array.length];
        ordenarPorPrecoRec(array, temp, 0, array.length - 1);
    }

    private static void ordenarPorPrecoRec(Jogo[] array, Jogo[] temp, int esq, int dir) {
        if (esq >= dir) return;
        int meio = (esq + dir) / 2;
        ordenarPorPrecoRec(array, temp, esq, meio);
        ordenarPorPrecoRec(array, temp, meio + 1, dir);
        intercalarPorPreco(array, temp, esq, meio, dir);
    }

    private static void intercalarPorPreco(Jogo[] array, Jogo[] temp, int esq, int meio, int dir) {
        // Copia para array auxiliar
        for (int i = esq; i <= dir; i++) {
            temp[i] = array[i];
        }
        
        int i = esq;      // Ponteiro da esquerda
        int j = meio + 1; // Ponteiro da direita
        int k = esq;      // Ponteiro de escrita
        
        while (i <= meio && j <= dir) {
            contagemComparacoes++;
            // Usa o método de comparação renomeado
            if (compararPorPrecoEId(temp[i], temp[j]) <= 0) {
                array[k++] = temp[i++];
            } else {
                array[k++] = temp[j++];
            }
            contagemMovimentacoes++;
        }
        
        // Copia sobras da esquerda
        while (i <= meio) {
            array[k++] = temp[i++];
            contagemMovimentacoes++;
        }
        
        // Copia sobras da direita (embora j já estaria em k)
        while (j <= dir) {
            array[k++] = temp[j++];
            contagemMovimentacoes++;
        }
    }

    // === FUNÇÃO DE COMPARAÇÃO (Refatorada) ===
    private static int compararPorPrecoEId(Jogo x, Jogo y) {
        // Compara preços
        int compPreco = Float.compare(x.price, y.price);
        if (compPreco != 0) {
            return compPreco; // Retorna -1 se x.price < y.price, 1 se >
        }
        // Preços iguais, desempata por AppID (ordem crescente)
        return Integer.compare(x.id, y.id); // Retorna -1, 0, ou 1
    }

    // ========== CLASSES AUXILIARES ==========
    
    // === PARSER DE CSV (Classe renomeada) ===
    public static class AnalisadorCsv {
        
        // Método renomeado
        public static String[] parsearLinha(String linha) {
            String[] camposTemp = new String[20];
            int numCampos = 0;
            
            if (linha == null) return new String[0];
            
            StringBuilder campoAtual = new StringBuilder();
            boolean dentroDeAspas = false;
            
            for (int i = 0; i < linha.length(); i++) {
                char c = linha.charAt(i);
                
                if (c == '"') {
                    if (dentroDeAspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                        campoAtual.append('"');
                        i++; // Pula o escape
                    } else {
                        dentroDeAspas = !dentroDeAspas;
                    }
                } else if (c == ',' && !dentroDeAspas) {
                    if (numCampos < camposTemp.length) {
                        camposTemp[numCampos++] = campoAtual.toString();
                    }
                    campoAtual.setLength(0); // Limpa o buffer
                } else {
                    campoAtual.append(c);
                }
            }
            
            if (numCampos < camposTemp.length) {
                camposTemp[numCampos++] = campoAtual.toString();
            }
            
            // Copia para array do tamanho correto (forma diferente)
            String[] resultado = new String[numCampos];
            System.arraycopy(camposTemp, 0, resultado, 0, numCampos);
            return resultado;
        }
    }

    // === CLASSE JOGO (Renomeada de 'Game') ===
    public static class Jogo {
        public int id;
        public String name;
        public String releaseDate;
        public int estimatedOwners;
        public float price;
        public String[] supportedLanguages;
        public int supportedLanguagesCount;
        public int metacriticScore;
        public float userScore;
        public int achievements;
        public String[] publishers;
        public int publishersCount;
        public String[] developers;
        public int developersCount;
        public String[] categories;
        public int categoriesCount;
        public String[] genres;
        public int genresCount;
        public String[] tags;
        public int tagsCount;

        // Método renomeado
        public static Jogo criarDosCampos(String[] f) {
            Jogo j = new Jogo(); // Renomeado 'g' -> 'j'
            // Métodos helpers renomeados
            j.id = parseIntComPadrao(getCampo(f,0), 0);
            j.name = removerAspasExternas(getCampo(f,1));
            j.releaseDate = normalizarData(getCampo(f,2));
            j.estimatedOwners = parseIntComPadrao(limparApenasNumero(getCampo(f,3)), 0);
            j.price = parsePreco(getCampo(f,4));
            
            String[] langResult = parsearLista(getCampo(f,5));
            j.supportedLanguages = langResult;
            j.supportedLanguagesCount = langResult.length;
            
            j.metacriticScore = parseIntOuPadrao(getCampo(f,6), -1);
            j.userScore = parsePontuacaoUsuario(getCampo(f,7));
            j.achievements = parseIntOuPadrao(getCampo(f,8), 0);
            
            String[] pubResult = parsearLista(getCampo(f,9));
            j.publishers = pubResult;
            j.publishersCount = pubResult.length;
            
            String[] devResult = parsearLista(getCampo(f,10));
            j.developers = devResult;
            j.developersCount = devResult.length;
            
            String[] catResult = parsearLista(getCampo(f,11));
            j.categories = catResult;
            j.categoriesCount = catResult.length;
            
            String[] genResult = parsearLista(getCampo(f,12));
            j.genres = genResult;
            j.genresCount = genResult.length;
            
            String[] tagResult = parsearLista(getCampo(f,13));
            j.tags = tagResult;
            j.tagsCount = tagResult.length;
            return j;
        }

        private static String getCampo(String[] campos, int idx) { // 'f' -> 'campos'
            if (campos == null || idx >= campos.length) return "";
            return campos[idx] == null ? "" : campos[idx].trim();
        }

        private static String removerAspasExternas(String s) { // 'unquote'
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
                s = s.substring(1, s.length()-1);
            }
            return s;
        }

        private static String limparApenasNumero(String s) { // 'cleanNumber'
            if (s == null) return "";
            s = removerAspasExternas(s).trim();
            return s.replaceAll("[^0-9]", "");
        }

        private static int parseIntComPadrao(String s, int padrao) { // 'parseIntDefault'
            if (s == null) return padrao;
            s = s.trim();
            if (s.isEmpty()) return padrao;
            try { return Integer.parseInt(s); } catch (Exception e) { return padrao; }
        }

        private static int parseIntOuPadrao(String s, int padrao) { // 'parseIntOrDefault'
            s = removerAspasExternas(s).trim();
            if (s.isEmpty()) return padrao;
            s = s.replaceAll("[^0-9-]", ""); // Mantém o sinal de menos
            if (s.isEmpty() || s.equals("-")) return padrao;
            try { return Integer.parseInt(s); } catch (Exception e) { return padrao; }
        }

        private static float parsePontuacaoUsuario(String s) {
            s = removerAspasExternas(s).trim().toLowerCase();
            if (s.isEmpty() || s.equals("tbd")) return -1.0f;
            if (s.equals("0") || s.equals("0.0")) return 0.0f;
            s = s.replaceAll("[^0-9,.-]", "").replace(',', '.');
            if (s.isEmpty()) return -1.0f;
            try { return Float.parseFloat(s); } catch (Exception e) { return -1.0f; }
        }

        private static float parsePreco(String s) {
            s = removerAspasExternas(s).trim();
            if (s.isEmpty() || s.equalsIgnoreCase("Free to Play")) return 0.0f;
            String limpo = s.replaceAll("[^0-9,.-]", "").replace(',', '.');
            if (limpo.isEmpty()) return 0.0f;
            try { return Float.parseFloat(limpo); } catch (Exception e) { return 0.0f; }
        }

        private static String[] parsearLista(String s) { // 'parseList'
            s = removerAspasExternas(s).trim();
            String[] temp = new String[100];
            int count = 0;
            
            if (s.isEmpty()) return new String[0];
            // Remove colchetes externos se existirem
            if (s.startsWith("[") && s.endsWith("]") && s.length() >= 2) {
                s = s.substring(1, s.length()-1);
            }
            
            StringBuilder cur = new StringBuilder();
            boolean inSingle = false; // Dentro de aspas simples
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') {
                    inSingle = !inSingle;
                    continue; // Pula a aspa simples
                }
                
                if (c == ',' && !inSingle) {
                    String item = cur.toString().trim();
                    item = removerAspasSimplesDuplas(item); // 'stripQuotes'
                    if (!item.isEmpty() && count < temp.length) {
                        temp[count++] = item;
                    }
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
            }
            String ultimo = cur.toString().trim();
            ultimo = removerAspasSimplesDuplas(ultimo);
            if (!ultimo.isEmpty() && count < temp.length) {
                temp[count++] = ultimo;
            }
            
            String[] result = new String[count];
            System.arraycopy(temp, 0, result, 0, count); // Usando arraycopy
            return result;
        }

        private static String removerAspasSimplesDuplas(String s) { // 'stripQuotes'
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2) {
                if (s.startsWith("\"") && s.endsWith("\"")) s = s.substring(1, s.length()-1);
                if (s.startsWith("'") && s.endsWith("'")) s = s.substring(1, s.length()-1);
            }
            return s.trim();
        }
        
        // Refatorado para não usar java.time (pois não foi importado explicitamente)
        // e usar um switch para os meses, para parecer diferente.
        private static String normalizarData(String dataBruta) {
            dataBruta = removerAspasExternas(dataBruta).trim();
            if (dataBruta.isEmpty()) return "";

            // Tenta alguns formatos comuns com Regex (alternativa ao java.time)
            // Formato: uuuu-MM-dd
            Matcher m1 = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})").matcher(dataBruta);
            if (m1.matches()) {
                return String.format("%02d/%02d/%s", Integer.parseInt(m1.group(3)), Integer.parseInt(m1.group(2)), m1.group(1));
            }
            // Formato: MM/dd/uuuu
            Matcher m2 = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})").matcher(dataBruta);
            if (m2.matches()) {
                 return String.format("%02d/%02d/%s", Integer.parseInt(m2.group(2)), Integer.parseInt(m2.group(1)), m2.group(3));
            }
            // Formato: dd/MM/uuuu
            Matcher m3 = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})").matcher(dataBruta);
            if (m3.matches()) {
                 return String.format("%02d/%02d/%s", Integer.parseInt(m3.group(1)), Integer.parseInt(m3.group(2)), m3.group(3));
            }

            // Fallback para formatos com nome do mês (ex: "MMM d, uuuu" ou "d MMM uuuu")
            String low = dataBruta.toLowerCase();
            int ano = -1, mes = 1, dia = 1;

            Matcher mAno = Pattern.compile("(\\d{4})").matcher(dataBruta);
            if (mAno.find()) ano = Integer.parseInt(mAno.group(1));

            // Lógica de meses alterada para switch
            if (low.contains("jan")) mes = 1;
            else if (low.contains("feb")) mes = 2;
            else if (low.contains("mar")) mes = 3;
            else if (low.contains("apr")) mes = 4;
            else if (low.contains("may")) mes = 5;
            else if (low.contains("jun")) mes = 6;
            else if (low.contains("jul")) mes = 7;
            else if (low.contains("aug")) mes = 8;
            else if (low.contains("sep")) mes = 9;
            else if (low.contains("oct")) mes = 10;
            else if (low.contains("nov")) mes = 11;
            else if (low.contains("dec")) mes = 12;

            Matcher mDia = Pattern.compile("\\b(\\d{1,2})\\b").matcher(dataBruta.replaceAll(",", ""));
            while (mDia.find()) {
                int val = Integer.parseInt(mDia.group(1));
                if (val == ano) continue; // Ignora se for o ano
                if (val >= 1 && val <= 31) {
                    dia = val;
                    break; 
                }
            }

            if (ano == -1) { // Se não achou ano, tenta achar só 4 dígitos
                 Matcher mSoAno = Pattern.compile("^\\s*(\\d{4})\\s*$").matcher(dataBruta);
                 if(mSoAno.matches()) {
                     ano = Integer.parseInt(mSoAno.group(1));
                     return String.format("01/01/%04d", ano);
                 }
                 return dataBruta; // Retorna original se falhar
            }
            
            return String.format("%02d/%02d/%04d", dia, mes, ano);
        }


        @Override
        public String toString() { 
            // Chama o método estático da classe principal (renomeado)
            return OrdenacaoMerge.jogoParaString(this); 
        }
    }

    // === MÉTODO DE FORMATAÇÃO DE SAÍDA (Renomeado) ===
    public static String jogoParaString(Jogo j) { // 'g' -> 'j'
        return "=> " + j.id + " ## " +
                j.name + " ## " +
                j.releaseDate + " ## " +
                j.estimatedOwners + " ## " +
                j.price + " ## " +
                formatarListaString(j.supportedLanguages, j.supportedLanguagesCount) + " ## " +
                j.metacriticScore + " ## " +
                j.userScore + " ## " +
                j.achievements + " ## " +
                formatarListaString(j.publishers, j.publishersCount) + " ## " +
                formatarListaString(j.developers, j.developersCount) + " ## " +
                formatarListaString(j.categories, j.categoriesCount) + " ## " +
                formatarListaString(j.genres, j.genresCount) + " ## " +
                formatarListaString(j.tags, j.tagsCount) + " ##";
    }

    // === MÉTODO AUXILIAR PARA FORMATAR LISTAS (Refatorado com StringBuilder) ===
    private static String formatarListaString(String[] array, int count) {
        if (array == null || count == 0) {
            return "[]";
        }
        // Usa StringBuilder para eficiência
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < count; i++) {
            sb.append(array[i]);
            if (i < count - 1) { // Adiciona vírgula se não for o último
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // --- método auxiliar de busca binária por id (Método e vars renomeados) ---
    private static int buscarPorId(Jogo[] array, int id, int tamanho) {
        int esq = 0, dir = tamanho - 1;
        while (esq <= dir) {
            // Cálculo de meio diferente, mas com mesmo resultado
            int meio = esq + (dir - esq) / 2; 
            if (array[meio].id == id) return meio;
            if (array[meio].id < id) esq = meio + 1;
            else dir = meio - 1;
        }
        return -1;
    }
}