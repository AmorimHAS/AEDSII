import java.util.*;

public class largada {

    public static void swap(int i, int j, int[] posicoes){
        int tmp = posicoes[i];
        posicoes[i] = posicoes[j];
        posicoes[j] = tmp;
    }

    public static int sort(int[] posicoes, int n) {
        int contador = 0;
        for (int i = (n - 1); i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (posicoes[j] > posicoes[j + 1]) {
                    swap(j, j+1, posicoes);
                    contador++;
                }
            }
        }
        return contador;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            sc.nextLine(); // consome o '\n' após o número

            String largada = sc.nextLine().trim();
            String chegada = sc.nextLine().trim();

            int[] largadaArray = new int[n];
            int[] chegadaArray = new int[n];
            int[] posicoes = new int[n];

            String[] largadaSplit = largada.split("\\s+");
            String[] chegadaSplit = chegada.split("\\s+");

            for (int i = 0; i < n; i++) {
                largadaArray[i] = Integer.parseInt(largadaSplit[i]);
                chegadaArray[i] = Integer.parseInt(chegadaSplit[i]);
            }

            // mapa valor -> índice na largada
            Map<Integer, Integer> indexMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                indexMap.put(largadaArray[i], i);
            }

            // posicoes: para cada competidor na chegada, coloca seu índice de largada
            for (int i = 0; i < n; i++) {
                posicoes[i] = indexMap.get(chegadaArray[i]);
            }

            int ultrapassagens = sort(posicoes, n);
            System.out.println(ultrapassagens);
        }
        sc.close();
    }
}
