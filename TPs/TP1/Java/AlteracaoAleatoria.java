import java.util.Scanner;
import java.util.Random;

public class AlteracaoAleatoria {

    // Função que percorre o texto e substitui todas as ocorrências do caractere "alvo" pelo "novoChar"
    public static String modificarTexto(String texto, char alvo, char novoChar) {
        // Cria um vetor de caracteres do mesmo tamanho da string
        char[] resultado = new char[texto.length()];
        // Copia os caracteres, trocando somente onde for necessário
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == alvo) {
                resultado[i] = novoChar;
            } else {
                resultado[i] = texto.charAt(i);
            }
        }
        // Constrói a nova string a partir do vetor
        return new String(resultado);
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        Random sorteio = new Random();
        sorteio.setSeed(4);

        String entrada = leitor.nextLine();
        while (!entrada.equals("FIM")) {
            // Gera dois caracteres entre 'a' e 'z'
            char antigo = (char) ('a' + Math.abs(sorteio.nextInt() % 26));
            char substituto = (char) ('a' + Math.abs(sorteio.nextInt() % 26));
            System.out.println(modificarTexto(entrada, antigo, substituto));
            entrada = leitor.nextLine();
        }
        leitor.close();
    }
}
