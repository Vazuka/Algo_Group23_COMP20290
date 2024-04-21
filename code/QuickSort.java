import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class QuickSort {

    public static void quicksort(final BigInteger[] array) {
        int low = 0;
        int high = array.length - 1;
        _quicksort(array, low, high);
    }

    private static void _quicksort(final BigInteger[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            _quicksort(array, low, pivotIndex - 1);
            _quicksort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(final BigInteger[] array, int low, int high) {
        BigInteger pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) < 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(final BigInteger[] array, int i, int j) {
        BigInteger temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        // Generate random BigInteger array
        int size = 50;
        BigInteger[] array = new BigInteger[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = new BigInteger(10, rand);
        }

        System.out.println("Original Array: " + Arrays.toString(array));
        long startTime = System.nanoTime(); // Start the timer
        quicksort(array);
        long endTime = System.nanoTime(); // End the timer

        System.out.println("Quick Sorted Array: " + Arrays.toString(array));

        long duration = (endTime - startTime) / 1_000; // Calculate duration in microseconds
        long dur = (endTime - startTime) / 1_000_000; // Calculate duration in milliseconds

        System.out.println("Time taken to sort: " + duration + " microseconds");
        System.out.println("Time taken to sort: " + dur + " milliseconds");
    }
}
