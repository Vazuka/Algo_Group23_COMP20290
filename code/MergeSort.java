import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {

    public static <T extends Comparable<? super T>> void mergesort(final T[] array, final Comparator<T> comparator) {
        int length = array.length;
        if (length < 2) {
            return;
        }

        final T[] left = Arrays.copyOfRange(array, 0, array.length / 2);
        final T[] right = Arrays.copyOfRange(array, array.length / 2, array.length);

        mergesort(left, comparator);
        mergesort(right, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int index = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            T leftItem = left[leftIndex];
            T rightItem = right[rightIndex];

            // If left < right
            if (comparator.compare(leftItem, rightItem) < 0) {
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
        // Example usage
        Integer[] array = {5, 2, 9, 1, 3, 7, 4, 8, 6};

        System.out.println("Original Array: " + Arrays.toString(array));
        long startTime = System.nanoTime(); // Start the timer
        mergesort(array, Comparator.naturalOrder());

        System.out.println("Merge Sorted Array: " + Arrays.toString(array));
        long endTime = System.nanoTime(); // End the timer

        long duration = (endTime - startTime) / 1_000; // Calculate duration in microseconds
        long dur = (endTime - startTime) / 1_000_000; // Calculate duration in milliseconds

        System.out.println("Time taken to sort: " + duration + " microseconds");
        System.out.println("Time taken to sort: " + dur + " milliseconds");
    }
}
