import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class MergeSort {

    public static void mergesort(final BigInteger[] array) {
        int length = array.length;
        if (length < 2) {
            return;
        }

        final BigInteger[] left = Arrays.copyOfRange(array, 0, array.length / 2);
        final BigInteger[] right = Arrays.copyOfRange(array, array.length / 2, array.length);

        mergesort(left);
        mergesort(right);

        int leftIndex = 0;
        int rightIndex = 0;
        int index = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            BigInteger leftItem = left[leftIndex];
            BigInteger rightItem = right[rightIndex];

            // If left < right
            if (leftItem.compareTo(rightItem) < 0) {
                array[index] = left[leftIndex];
                leftIndex += 1;
            } else {
                array[index] = right[rightIndex];
                rightIndex += 1;
            }
            index += 1;
        }

        // merge the remaining elements(if there are any)
        while (leftIndex < left.length) {
            array[index] = left[leftIndex];
            leftIndex += 1;
            index += 1;
        }

        while (rightIndex < right.length) {
            array[index] = right[rightIndex];
            rightIndex += 1;
            index += 1;
        }
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
        mergesort(array);

        System.out.println("Merge Sorted Array: " + Arrays.toString(array));
        long endTime = System.nanoTime(); // End the timer

        long duration = (endTime - startTime) / 1_000; // Calculate duration in microseconds
        long dur = (endTime - startTime) / 1_000_000; // Calculate duration in milliseconds

        System.out.println("Time taken to sort: " + duration + " microseconds");
        System.out.println("Time taken to sort: " + dur + " milliseconds");
    }
}
