import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by ziqi on 2019/12/14.
 */
public class QS {

    /**
     *  Generate Random Elements Arrays
     * */
    public static  int[] genRandomArray(int len,int max){
        int[] arr = new int[len];
        for(int i=0;i<len;i++){
            arr[i] = (int)(Math.random()*max);
        }
        return arr;
    }

    /**
     *  Generate Sorted Elements Array
     * */
    public static  int[] genSortedArray(int len){
        int[] arr = new int[len];
        for(int i=1;i<len;i++){
            arr[i]=i;
        }
        return arr;
    }

    /**
     *  Insert Sort,used when size of array  is less than 10
     * */
    public static void insertsort(int[] arr,int left,int right){
        if(left>right)
            return;
        int min = Integer.MAX_VALUE;
        int index=-1;
        int tmp=0;
        for(int i=left;i<=right;i++){
            for(int j=i;j<=right;j++){
                if(arr[j]<min){
                    min=arr[j];
                    index = j;
                }
            }
            tmp = arr[i];
            arr[i]=min;
            arr[index]=tmp;
            min = Integer.MAX_VALUE;
        }
    }
    /**
     *  Optimized non-recursive quick sort algorithm
     *  Optimization Strategy：
     *   1. Use insert sort to handle small array sort
     *   2. Use partition_optimaze as partition method
     * */
    public static void qs_optimize(int[] arr,int low,int high){
        if(low>high){
            return;
        }
        //use insert sort when array size is less than 10
        if(high-low<10){
            insertsort(arr,low,high);
            return;
        }
        int pivot;
        if (low >= high)
            return;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(low);
        stack.push(high);
        while (!stack.empty()) {
            // pop high first,then low
            high = stack.pop();
            low = stack.pop();
            pivot = partition_optimaze(arr,low,high);
            // push low first,then high
            if (low < pivot - 1) {
                stack.push(low);
                stack.push(pivot - 1);
            }
            if (pivot + 1 < high) {
                stack.push(pivot + 1);
                stack.push(high);
            }
        }
    }

    /**
     *  Traditional non-recursive quick sort algorithm
     * */
    public static void qs_original(int[] arr, int low ,int high){
        int pivot;
        if (low >= high)
            return;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(low);
        stack.push(high);
        while (!stack.empty()) {
            // pop high first,then low
            high = stack.pop();
            low = stack.pop();
            pivot = partition(arr,low,high);
            // push low first,then high
            if (low < pivot - 1) {
                stack.push(low);
                stack.push(pivot - 1);
            }
            if (pivot + 1 < high) {
                stack.push(pivot + 1);
                stack.push(high);
            }
        }
    }

    /**
     *  Traditional quick sorting algorithm for array partitioning
     * */
    public static int partition(int[] a, int low, int high) {
        int pivotKey = a[low]; // Use the first element as the Pivot element
        while (low < high) { // Scan from both sides to the middle
            while (low < high && a[high] >= pivotKey)
                high--;
            a[low] = a[high];
            while (low < high && a[low] <= pivotKey)
                low++;
            a[high] = a[low];
        }
        a[low] = pivotKey; // Put the reference value back in the middle
        return low; // Returns the position of the pivot element
    }

    /**
     *  Optimized quick sort algorithm for array partitioning
     * */
    public static int partition_optimaze(int[] a, int low, int high) {
        // Take three numbers and place the middle element in the first position
        if (a[low] > a[high])
            swap(a, low, high);
        if (a[(low + high) / 2] > a[high])
            swap(a, (low + high) / 2, high);
        if (a[low] < a[(low + high) / 2])
            swap(a, (low + high) / 2, low);

        int pivotKey = a[low]; // Use the first element as the Pivot element
        while (low < high) { //  Scan from both sides to the middle
            while (low < high && a[high] >= pivotKey)
                high--;
            a[low] = a[high];
            while (low < high && a[low] <= pivotKey)
                low++;
            a[high] = a[low];
        }
        a[low] = pivotKey; //  Put the reference value back in the middle
        return low; // Returns the position of the pivot element
    }

    /**
     *  swap elements in a
     * */
    public static void swap(int[] a, int i, int j) {
        int temp;
        temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void printArray(int[] arr){
        for(int i=0;i<arr.length;i++)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
    public static int[] arrayCopy(int[] arr){
        int[] arr2= new int[arr.length];
        for(int i=0;i<arr.length;i++){
            arr2[i]=arr[i];
        }
        return arr2;
    }

    /**
     *  run sort algorithm and record run time
     * */
    public static long timecount(int[] arr,int algorithm,int showresult){
        switch (algorithm){
            case 0:{
                long start=System.currentTimeMillis();
                qs_original(arr,0,arr.length-1);
                if(showresult==1){
                    System.out.println("qs_original sort result");
                    printArray(arr);
                }

                return System.currentTimeMillis()-start;
            }
            case 1:{
                long start=System.currentTimeMillis();
                qs_optimize(arr,0,arr.length-1);
                if(showresult==1) {
                    System.out.println("qs_optimize sort result");
                    printArray(arr);
                }
                return System.currentTimeMillis()-start;
            }
            default:{
                return -1;
            }
        }

    }

    public static void main(String[] args) {
        HashMap<Integer,Long> testcount_qs_original = new HashMap<>();
        HashMap<Integer,Long> testcount_qs_optimize = new HashMap<>();
        int upper=1000000;
        //Random Array
        for(int i=1000;i<=upper;i=i*10){
            int[] arrs = genRandomArray(i,1000);
            testcount_qs_original.put(i,timecount(arrayCopy(arrs),0,0));
            testcount_qs_optimize.put(i,timecount(arrayCopy(arrs),1,0));
        }

        System.out.println("qs_original run time");
        for(int i=1000;i<=upper;i=i*10){
            System.out.println("Array Size:\t"+i+"\tRun Time:\t"+testcount_qs_original.get(i));
        }
        System.out.println("qs_optimize run time");
        for(int i=1000;i<=upper;i=i*10){
            System.out.println("Array Size:\t"+i+"\tRun Time:\t"+testcount_qs_optimize.get(i));
        }

        //Sorted Array
        upper=1000000;
        for(int i=1000;i<=upper;i=i*10){
            int[] arrs = genSortedArray(i);
            testcount_qs_original.put(i,timecount(arrayCopy(arrs),0,0));
            testcount_qs_optimize.put(i,timecount(arrayCopy(arrs),1,0));
        }
        System.out.println("qs_original run time");
        for(int i=1000;i<=upper;i=i*10){
            System.out.println("Array Size:\t"+i+"\tRun Time:\t"+testcount_qs_original.get(i)+"ms");
        }
        System.out.println("qs_optimize run time");
        for(int i=1000;i<=upper;i=i*10){
            System.out.println("Array Size:\t"+i+"\tRun Time:\t"+testcount_qs_optimize.get(i)+"ms");
        }

    }
}
