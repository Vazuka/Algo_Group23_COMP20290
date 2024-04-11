import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSort {

    // Bucket sort function
    public static void bucketSort(float[] array) {
        int n = array.length;

        // Create buckets
        List<Float>[] buckets = new List[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Add elements to buckets
        for (int i = 0; i < n; i++) {
            int bucketIndex = (int) (n * array[i]);
            buckets[bucketIndex].add(array[i]);
        }

        // Sort each bucket
        for (int i = 0; i < n; i++) {
            Collections.sort(buckets[i]);
        }

        // Concatenate buckets back into array
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (float num : buckets[i]) {
                array[index++] = num;
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        float[] array = {0.42f, 0.32f, 0.33f, 0.52f, 0.37f, 0.47f, 0.51f};

        System.out.println("Original Array: " + arrayToString(array));

        bucketSort(array);

        System.out.println("Bucket Sorted Array: " + arrayToString(array));
    }

    // Utility method to convert array to string for printing
    private static String arrayToString(float[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}

