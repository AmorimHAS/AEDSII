import java.util.Random;

public class QuickSortStrategies {
    // QuickSort usando o primeiro elemento como pivô
    public static void QuickSortFirstPivot(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partitionFirst(array, left, right);
            QuickSortFirstPivot(array, left, pivotIndex - 1);
            QuickSortFirstPivot(array, pivotIndex + 1, right);
        }
    }
    private static int partitionFirst(int[] array, int left, int right) {
        int pivot = array[left];
        int i = left + 1;
        for (int j = left + 1; j <= right; j++) {
            if (array[j] < pivot) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, left, i - 1);
        return i - 1;
    }

    // QuickSort usando o último elemento como pivô
    public static void QuickSortLastPivot(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partitionLast(array, left, right);
            QuickSortLastPivot(array, left, pivotIndex - 1);
            QuickSortLastPivot(array, pivotIndex + 1, right);
        }
    }
    private static int partitionLast(int[] array, int left, int right) {
        swap(array, right, left); // Move o último para o início
        return partitionFirst(array, left, right);
    }

    // QuickSort usando pivô aleatório
    public static void QuickSortRandomPivot(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partitionRandom(array, left, right);
            QuickSortRandomPivot(array, left, pivotIndex - 1);
            QuickSortRandomPivot(array, pivotIndex + 1, right);
        }
    }
    private static int partitionRandom(int[] array, int left, int right) {
        int randIndex = left + new Random().nextInt(right - left + 1);
        swap(array, left, randIndex);
        return partitionFirst(array, left, right);
    }

    // QuickSort usando mediana de três
    public static void QuickSortMedianOfThree(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partitionMedianOfThree(array, left, right);
            QuickSortMedianOfThree(array, left, pivotIndex - 1);
            QuickSortMedianOfThree(array, pivotIndex + 1, right);
        }
    }
    private static int partitionMedianOfThree(int[] array, int left, int right) {
        int mid = left + (right - left) / 2;
        int a = array[left], b = array[mid], c = array[right];
        int medianIndex;
        if ((a < b && b < c) || (c < b && b < a)) medianIndex = mid;
        else if ((b < a && a < c) || (c < a && a < b)) medianIndex = left;
        else medianIndex = right;
        swap(array, left, medianIndex);
        return partitionFirst(array, left, right);
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
