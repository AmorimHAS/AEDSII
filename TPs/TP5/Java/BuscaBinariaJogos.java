import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// Classe simples para armazenar o nome de um jogo
class JogoBuscado {
    String nome; // Campo para guardar o nome do jogo

    // Construtor padrão
    JogoBuscado() {
        this.nome = "";
    }

    // Construtor que recebe o nome
    JogoBuscado(String nome) {
        this.nome = nome;
    }
}

// Classe principal responsável pela leitura, ordenação e busca
public class BuscaBinariaJogos {
    public static Scanner leitor = new Scanner(System.in);

    // Variáveis para o log
    private static final String MATRICULA = "885637";
    private static long tempoDeExecucao = 0;
    private static int totalComparacoes = 0;

    public static void main(String[] args) {
        long tempoInicial = System.currentTimeMillis();

        // --- Leitura dos IDs ---
        int numIds = 0;
        String[] listaIds = new String[0];
        String entrada = leitor.nextLine();

        while (!entrada.equals("FIM")) {
            numIds++;

            // Redimensiona o vetor manualmente
            String[] arrayTemporario = new String[numIds];
            for (int i = 0; i < numIds - 1; i++) {
                arrayTemporario[i] = listaIds[i];
            }
            arrayTemporario[numIds - 1] = entrada;
            listaIds = arrayTemporario;
            arrayTemporario = null;

            entrada = leitor.nextLine();
        }

        // Carrega jogos do CSV pelos IDs lidos
        JogoBuscado vetorGames[] = LeitorCsvGames.carregarGamesDoCsv(numIds, listaIds);

        // Ordena os jogos pelo nome
        if (vetorGames != null) {
            ordenarPorNome(vetorGames, 0, vetorGames.length - 1);
        }

        // --- Fase de pesquisa ---
        entrada = leitor.nextLine();

        while (!entrada.equals("FIM")) {
            boolean encontrado = false;
            int direita = vetorGames.length - 1;
            int esquerda = 0;

            // Busca binária
            while (esquerda <= direita) {
                int meio = (esquerda + direita) / 2;
                totalComparacoes++;

                int comparacao = entrada.compareTo(vetorGames[meio].nome);

                if (comparacao == 0) {
                    encontrado = true;
                    esquerda = direita + 1;
                } else {
                    totalComparacoes++;
                    if (comparacao > 0) {
                        esquerda = meio + 1;
                    } else {
                        direita = meio - 1;
                    }
                }
            }

            // Exibe resultado
            if (encontrado)
                System.out.println(" SIM");
            else
                System.out.println(" NAO");

            entrada = leitor.nextLine();
        }

        leitor.close();

        // --- Log de execução ---
        long tempoFinal = System.currentTimeMillis();
        tempoDeExecucao = tempoFinal - tempoInicial;
        salvarLog();
    }

    // Escreve o arquivo de log com matrícula, tempo e número de comparações
    private static void salvarLog() {
        try (FileWriter escritor = new FileWriter(MATRICULA + "_binaria.txt")) {
            String linhaDeLog = MATRICULA + "\t" + tempoDeExecucao + "\t" + totalComparacoes + "\n";
            escritor.write(linhaDeLog);
        } catch (IOException e) {
            System.err.println("Falha ao escrever o arquivo de log: " + e.getMessage());
        }
    }

    // Ordena o array de JogoBuscado usando QuickSort pelo nome
    static void ordenarPorNome(JogoBuscado lista[], int inicio, int fim) {
        int i = inicio, j = fim;
        int meio = (fim + inicio) / 2;
        String pivoNome = lista[meio].nome;

        while (i <= j) {
            while (lista[i].nome.compareTo(pivoNome) < 0) {
                i++;
            }
            while (lista[j].nome.compareTo(pivoNome) > 0) {
                j--;
            }

            if (i <= j) {
                trocar(lista, i, j);
                i++;
                j--;
            }
        }

        if (inicio < j)
            ordenarPorNome(lista, inicio, j);
        if (i < fim)
            ordenarPorNome(lista, i, fim);
    }

    // Troca dois elementos de posição no vetor
    static void trocar(JogoBuscado lista[], int i, int j) {
        JogoBuscado temp = lista[i];
        lista[i] = lista[j];
        lista[j] = temp;
    }
}

// Classe auxiliar para leitura e filtragem do CSV
class LeitorCsvGames {
    static int posicaoLeitura = 0;

    // Lê o CSV, filtra pelos IDs e retorna os jogos encontrados
    static JogoBuscado[] carregarGamesDoCsv(int totalIds, String idsParaBuscar[]) {
        JogoBuscado jogosEncontrados[] = new JogoBuscado[totalIds];
        InputStream inputStream = null;

        try {
            java.io.File arquivoCsv = new java.io.File("/tmp/games.csv");
            if (!arquivoCsv.exists()) {
                System.out.println("Arquivo 'games.csv' não localizado em /tmp!");
                return new JogoBuscado[0];
            }
            inputStream = new FileInputStream(arquivoCsv);
        } catch (Exception e) {
            System.out.println("Erro na abertura do arquivo: " + e.getMessage());
            return new JogoBuscado[0];
        }

        Scanner leitorArquivo = new Scanner(inputStream);

        // Pula cabeçalho
        if (leitorArquivo.hasNextLine())
            leitorArquivo.nextLine();

        int contadorJogos = 0;
        int indiceDoId;
        String linhaCsv;

        while (leitorArquivo.hasNextLine() && contadorJogos < totalIds) {
            linhaCsv = leitorArquivo.nextLine();

            int idAtual = extrairId(linhaCsv);
            String nomeAtual = extrairNome(linhaCsv);

            // Se o ID está na lista, adiciona o jogo
            if ((indiceDoId = buscarIdNaLista(idsParaBuscar, idAtual)) != -1) {
                JogoBuscado novoJogo = new JogoBuscado(nomeAtual);
                jogosEncontrados[contadorJogos] = novoJogo;
                contadorJogos++;
                idsParaBuscar[indiceDoId] = null;
            }
            posicaoLeitura = 0;
        }

        leitorArquivo.close();
        return jogosEncontrados;
    }

    // Procura o ID na lista de busca
    static int buscarIdNaLista(String listaDeIds[], int idDoCsv) {
        for (int i = 0; i < listaDeIds.length; i++) {
            if (listaDeIds[i] != null) {
                try {
                    if (Integer.parseInt(listaDeIds[i]) == idDoCsv) {
                        return i;
                    }
                } catch (NumberFormatException e) {
                    // ignora caso não seja número
                }
            }
        }
        return -1;
    }

    // Extrai o ID do início da linha do CSV
    static int extrairId(String linhaCsv) {
        int idExtraido = 0;
        while (posicaoLeitura < linhaCsv.length() && Character.isDigit(linhaCsv.charAt(posicaoLeitura))) {
            idExtraido = idExtraido * 10 + (linhaCsv.charAt(posicaoLeitura) - '0');
            posicaoLeitura++;
        }
        return idExtraido;
    }

    // Extrai o nome do jogo
    static String extrairNome(String linhaCsv) {
        StringBuilder nomeBuilder = new StringBuilder();

        while (posicaoLeitura < linhaCsv.length() && linhaCsv.charAt(posicaoLeitura) != ',') {
            posicaoLeitura++;
        }
        posicaoLeitura++;

        if (posicaoLeitura < linhaCsv.length() && linhaCsv.charAt(posicaoLeitura) == '"') {
            posicaoLeitura++;
            while (posicaoLeitura < linhaCsv.length() && linhaCsv.charAt(posicaoLeitura) != '"') {
                nomeBuilder.append(linhaCsv.charAt(posicaoLeitura));
                posicaoLeitura++;
            }
            posicaoLeitura++;
        } else {
            while (posicaoLeitura < linhaCsv.length() && linhaCsv.charAt(posicaoLeitura) != ',') {
                nomeBuilder.append(linhaCsv.charAt(posicaoLeitura));
                posicaoLeitura++;
            }
        }
        return nomeBuilder.toString();
    }
}
