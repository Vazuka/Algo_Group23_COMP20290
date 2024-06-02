package pqsa;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class pqsa {

    public static void main(String[] args) {

        BigInteger k = BigInteger.valueOf(50);
        int[] randArr = new int[5000000];
        psqsa(randArr,5 , k);
//        for (int i = 0; i < randArr.length; i++) {
//            System.out.print(randArr[i] + " ");
//        }
    }

    public static void psqsa(int[] arr, int num_thread, BigInteger num_integer) {
        BigInteger N = num_integer;
        int n = num_thread;

        Random rand = new Random();

        // Generate 'N' Random Numbers
        for (BigInteger i = BigInteger.ZERO; i.compareTo(N) < 0; i = i.add(BigInteger.ONE)) {
            arr[i.intValue()] = rand.nextInt(1000);
        }

        long startTime, endTime;
        startTime = System.nanoTime();

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(n);

        // Submit tasks to the thread pool
        for (BigInteger i = BigInteger.ZERO; i.compareTo(N) < 0; i = i.add(N.divide(BigInteger.valueOf(n)))) {
            subArraySort s1 = new subArraySort(arr, i, N, n);
            executor.execute(s1);
        }

        // Shutdown the executor after tasks are completed
        executor.shutdown();

        try {
            // Wait for all tasks to finish
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        Random rand = new Random();
        int pivotIndex =  rand.nextInt(high - low + 1) + low;

        // System.out.println(pivotIndex);
        int t= arr[pivotIndex];
        arr[pivotIndex]= arr[high];
        arr[high]= t;

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
