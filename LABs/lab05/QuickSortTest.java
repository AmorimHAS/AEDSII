import java.util.Random;
import java.util.Arrays;

public class QuickSortTest {
    // Funções para gerar arrays
    public static int[] generateOrderedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = i;
        return arr;
    }
    public static int[] generateAlmostOrderedArray(int size) {
        int[] arr = generateOrderedArray(size);
        Random rand = new Random();
        for (int i = 0; i < size / 20; i++) {
            int a = rand.nextInt(size);
            int b = rand.nextInt(size);
            int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
        }
        return arr;
    }
    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) arr[i] = rand.nextInt(size * 10);
        return arr;
    }

    // Função para medir tempo de execução
    public static long measureSortTime(int[] array, String method) {
        int[] copy = array.clone();
        long start = System.nanoTime();
        switch (method) {
            case "first": QuickSortStrategies.QuickSortFirstPivot(copy, 0, copy.length - 1); break;
            case "last": QuickSortStrategies.QuickSortLastPivot(copy, 0, copy.length - 1); break;
            case "random": QuickSortStrategies.QuickSortRandomPivot(copy, 0, copy.length - 1); break;
            case "median": QuickSortStrategies.QuickSortMedianOfThree(copy, 0, copy.length - 1); break;
        }
        long end = System.nanoTime();
        return (end - start); // retorna nanossegundos
    }

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000};
        String[] types = {"Ordenado", "Quase Ordenado", "Aleatório"};
        String[] methods = {"first", "last", "random", "median"};
        String[] methodNames = {
            "Primeiro elemento", "Último elemento", "Aleatório", "Mediana de três"
        };

        for (int size : sizes) {
            System.out.println("\nTamanho do array: " + size);
            for (int t = 0; t < types.length; t++) {
                int[] array;
                switch (t) {
                    case 0: array = generateOrderedArray(size); break;
                    case 1: array = generateAlmostOrderedArray(size); break;
                    default: array = generateRandomArray(size); break;
                }
                System.out.println("Tipo: " + types[t]);
                for (int m = 0; m < methods.length; m++) {
                    long timeNano = measureSortTime(array, methods[m]);
                    double timeMs = timeNano / 1_000_000.0;
                    System.out.printf("  %s: %d ns (%.6f ms)\n", methodNames[m], timeNano, timeMs);
                }
            }
        }
    }
}
