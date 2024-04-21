import java.math.BigInteger;
import java.util.Random;

public class ParallelSort {

    public static void main(String[] args) {
        int[] randArr = new int[50];
        BigInteger k = BigInteger.valueOf(50);
        psqsa(randArr, 5, k);
        for (int i = 0; i < randArr.length; i++) {
            System.out.println(randArr[i] + " ");
        }
    }

    public static int[] psqsa(int[] arr, int num_thread, BigInteger num_integer) {
        BigInteger N = num_integer;
        int n = num_thread;

        Random rand = new Random();

        // Generate 'N' Random Numbers
        for (BigInteger i = BigInteger.ZERO; i.compareTo(N) < 0; i = i.add(BigInteger.ONE)) {
            arr[i.intValue()] = rand.nextInt(1000);
        }

        long startTime, endTime;
        startTime = System.nanoTime();

        // Parallel Sorting using Quick Sort Algorithm
        for (BigInteger i = BigInteger.ZERO; i.compareTo(N) < 0; i = i.add(N.divide(BigInteger.valueOf(n)))) {
            subArraySort s1 = new subArraySort(arr, i, N, n);
            Thread newThread = new Thread(s1);
            newThread.start();
            try {
                newThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Each thread sorts its corresponding subarray using the algorithm of non-recursive quick sort
        for (int i = 0; i < n - 1; i++) {
            quickSort(arr, i * (N.divide(BigInteger.valueOf(n))).intValue(), (i + 1) * (N.divide(BigInteger.valueOf(n))).intValue() - 1);
        }

        // Merge sorted subarrays
        int interval = N.divide(BigInteger.valueOf(n)).intValue();
        int j = 0;

        while (N.compareTo(BigInteger.valueOf(interval)) > 0) {
            while (N.compareTo(BigInteger.valueOf(interval + j)) > 0) {
                merge(arr, j, j + interval - 1, Math.min(j + 2 * interval - 1, arr.length - 1));
                j = j + 2 * interval;
            }
            j = 0;
            interval = 2 * interval;
        }

        endTime = System.nanoTime();
        long finalTime = endTime - startTime;
        System.out.println("Sorting Time: " + finalTime + " ns");

        return arr;
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int left = low, right = mid + 1, k = 0;

        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp[k++] = arr[left++];
            } else {
                temp[k++] = arr[right++];
            }
        }

        while (left <= mid) {
            temp[k++] = arr[left++];
        }

        while (right <= high) {
            temp[k++] = arr[right++];
        }

        for (int i = low; i <= high; i++) {
            arr[i] = temp[i - low];
        }
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

class subArraySort implements Runnable {
    private final int[] arr;
    private final BigInteger start;
    private final BigInteger N;
    private final int n;

    public subArraySort(int[] arr, BigInteger start, BigInteger N, int n) {
        this.arr = arr;
        this.start = start;
        this.N = N;
        this.n = n;
    }

    @Override
    public void run() {
        int interval = N.divide(BigInteger.valueOf(n)).intValue();
        int low = start.intValue();
        int high = Math.min(start.add(N.divide(BigInteger.valueOf(n))).intValue() - 1, arr.length - 1);
        quickSort(arr, low, high);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
