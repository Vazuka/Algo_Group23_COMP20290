import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class parallelMerge {

    public static void main(String[] args) throws InterruptedException {
        int N = 50;
        int n= 5;
        int[] randArr = new int[N];
        msqsa(randArr,n, N);
        for (int i = 0; i < randArr.length; i++) {
            System.out.print(randArr[i] + " ");
        }
    }

    public static void msqsa(int[] arr, int num_thread, int num_integer) throws InterruptedException {

        int N = num_integer;
        int n = num_thread;

        Random rand = new Random();

        // Generate 'N' Random Numbers
        for(int i =0; i<N; i++){
            arr[i] = rand.nextInt(500);
        }

        long startTime, endTime;
        startTime = System.nanoTime();

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(n);

        // Submit tasks to the thread pool
        for (int i = 0; i < n; i++) {
            subSort s1 = new subSort(arr, N, n, i);
            executor.execute(s1);
        }

        // Shutdown the executor after tasks are completed
        executor.shutdown();

        // Wait for all tasks to finish
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


//---------------------------Merging--------------------------------

//Only the main thread runs the algorithm to sort all self-ordered subarrays


    int interval= N/n;

    while(interval<N){
        int x=0;
        while(x<N){
            int L1= x;
            int R1= x+ interval-1;
            int L2= x+ interval;
            int R2= x+ 2*interval -1;

            if(L2>=N){ break;}
            if (R2>= N){ R2 = N-1;}

            int[] temp= merge(arr, L1, R1, L2, R2);

            for(int j=0; j< R2-L1 +1; j++){
                arr[x+j]= temp[j];
            }
            x= x+ 2*interval;
        }
        interval= 2*interval;
    }

        endTime = System.nanoTime();

        long finalTime = endTime - startTime;

        System.out.println("Running time: "+finalTime);
    }

    private static int[] merge(int[] arr, int L1, int R1, int L2, int R2) {
        int []temp= new int[arr.length];
        int index= 0;
        while(L1<= R1 && L2<= R2){
            if(arr[L1]<= arr[L2]){
                temp[index]= arr[L1];
                index++;
                L1+=1;
            }else{
                temp[index]= arr[L2];
                index++;
                L2+=1;
            }
        }
        while(L1<= R1){
            temp[index]= arr[L1];
            index= index+1;
            L1+=1;
        }
        while(L2<= R2){
            temp[index]= arr[L2];
            index= index+1;
            L2++;
        }
        return temp;
    }


}


class subSort implements Runnable {
    int[] a;
    int integers;
    int threads;
    int i;
    public subSort(int[] arr, int num_integers, int num_threads, int i) {
        this.a = arr;
        this.integers = num_integers;
        this.threads = num_threads;
        this.i=i;
    }

    public int[] getArray(){
        return a;
    }

    @Override
    public void run() {

        //iterativeQuickSort(a,startIdx, startIdx+(integers/threads)-1);// sorting without creating separate sub arrays.
        quickSort(a, i*(integers/threads), (i+1)*(integers/threads)-1);

    }

    private static void quickSort(int[] arr, int l, int h) {

        Stack<Integer> stack = new Stack<>();
        stack.push(l);
        stack.push(h);

        while (!stack.isEmpty()) {
            h = stack.pop();
            l = stack.pop();


            int pivotIndex = partition(arr, l, h);


            if (pivotIndex - 1 > l) {
                stack.push(l);
                stack.push(pivotIndex - 1);
            }


            if (pivotIndex + 1 < h) {
                stack.push(pivotIndex + 1);
                stack.push(h);

            }
        }
    }
    private static int partition(int[] arr, int low, int high) {
        Random rand = new Random();
        int pivotIndex =  rand.nextInt(high - low + 1) + low;

        // System.out.println(pivotIndex);
        int t= arr[pivotIndex];
        arr[pivotIndex]= arr[high];
        arr[high]= t;

        int pivot= arr[high];

        int i = low;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
            }
        }

        int temp = arr[i];
        arr[i] = arr[high];
        arr[high] = temp;

        return i;
    }

}