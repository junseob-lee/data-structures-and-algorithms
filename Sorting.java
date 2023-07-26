import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Junseob Lee
 * @version 1.0
 * @userid jlee3624
 * @GTID 903493189
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */

    // Selection Sort 는 쉽게 생각하면 가장 작은 값을 찾아서 맨 앞으로 보내는 것을 반복하는 것이다.
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
    // Check for null inputs
    if (arr == null || comparator == null) {
        throw new IllegalArgumentException("Array or comparator cannot be null.");
    }

    // Get the length of the array
    int n = arr.length;

    // Iterate through the array
    for (int i = 0; i < n - 1; i++) {
        // Assume the current index as the minimum
        int minIndex = i;

        // unsorted part 에서 최소값 찾기
        for (int j = i + 1; j < n; j++) {
            // Update the minimum index if a smaller element is found
            if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                minIndex = j;
            }
        }

        // Swap the minimum element with the first element
        if (minIndex != i) {
            T temp = arr[i];
            // i 있던 자리에 minIndex 값 넣기
            arr[i] = arr[minIndex];
            // minIndex 있던 자리에 i 값 넣기
            arr[minIndex] = temp;
        }
    }
}


    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    // Insertion Sort 는 쉽게 생각하면 이미 정렬된 부분과 비교해서 자신의 위치를 찾아가는 것이다.
    // 손안의 카드를 정렬하는 방법과 유사하다. (이미 정렬된 카드와 비교해서 새로운 카드의 위치를 찾아가는 것)
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
    // Check for null inputs
    if (arr == null || comparator == null) {
        throw new IllegalArgumentException("Array or comparator cannot be null.");
    }

    // Get the length of the array
    int n = arr.length;

    // 두번째 원소부터 시작해서 앞의 원소들과만 비교하면서 자신의 위치를 찾아가는 것이다.
    for (int i = 1; i < n; i++) {
        // Store the current element
        T key = arr[i];

        // Compare the current element with the elements before it
        int j = i - 1;
        // comparator.compare(arr[j], key) > 0 이면 arr[j] 가 key 보다 크다는 것이다.
        while (j >= 0 && comparator.compare(arr[j], key) > 0) {
            // arr[j] 가 key 보다 크면 arr[j] 를 오른쪽으로 한 칸씩 옮긴다.
            arr[j + 1] = arr[j];
            // 더 앞에있는수와도 비교해야하므로 j 를 하나 줄여준다.
            j--;
        }

        // 원래 key 값을 알맞은 위치에 넣어준다. while 문에서 j-- 를 해줬으므로 j + 1 이 key 값이 들어갈 위치이다.
        arr[j + 1] = key;
    }
}


    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
    // Check for null inputs
    if (arr == null || comparator == null) {
        throw new IllegalArgumentException("Array or comparator cannot be null.");
    }

    // Get the length of the array
    int n = arr.length;

    // Iterate through the array
    for (int i = 0; i < n - 1; i++) {
        // Assume that the array is already sorted
        boolean swapped = false;

        // Compare adjacent elements and swap them if necessary
        for (int j = 0; j < n - i - 1; j++) {
            if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                swapped = true;
            }
        }

        // If no two elements were swapped, the array is already sorted
        if (!swapped) {
            break;
        }
    }
}


    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */

     public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Input is invalid");
        }
        int n = arr.length;
        int mid = n / 2;
        T[] left = (T[]) new Object[mid];
        T[] right = (T[]) new Object[n - mid];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }
        if (n > 1) {
            mergeSort(left, comparator);
            mergeSort(right, comparator);
        }
        int currIdx = 0;
        int leftIdx = 0;
        int rightIdx = 0;
        // Merge the two arrays
        while (currIdx < n) {
            // If the left array is empty, add the next element from the right array
            if (leftIdx >= left.length) {
                arr[currIdx] = right[rightIdx];
                rightIdx++;
                currIdx++;
                // If the right array is empty, add the next element from the left array
            } else if (rightIdx >= right.length) {
                arr[currIdx] = left[leftIdx];
                leftIdx++;
                currIdx++;
                // If the next element in the left array is smaller than the next element in the right array, add the next element from the left array
            } else if (comparator.compare(left[leftIdx], right[rightIdx]) <= 0) {
                arr[currIdx] = left[leftIdx];
                leftIdx++;
                currIdx++;
                // If the next element in the right array is smaller than the next element in the left array, add the next element from the right array
            } else {
                arr[currIdx] = right[rightIdx];
                rightIdx++;
                currIdx++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
    if (arr == null) {
        throw new IllegalArgumentException("Input array is null");
    }

    // Get the maximum value in the array to determine the number of digits
    int maxVal = Math.abs(arr[0]);
    for (int i = 1; i < arr.length; i++) {
        int absVal = Math.abs(arr[i]);
        if (absVal > maxVal) {
            maxVal = absVal;
        }
    }

    // Determine the number of digits in the maximum value
    int numDigits = 0;
    while (maxVal > 0) {
        maxVal /= 10;
        numDigits++;
    }

    // Create the buckets (lists) for each digit
    List<Integer>[] buckets = new ArrayList[19]; // 19 for -9 to 9 digits
    for (int i = 0; i < buckets.length; i++) {
        buckets[i] = new ArrayList<>();
    }

    // Perform the LSD Radix Sort for each digit
    int divisor = 1;
    for (int i = 0; i < numDigits; i++) {
        // Place elements in the corresponding buckets based on the current digit
        for (int num : arr) {
            int digit = (num / divisor) % 10 + 9; // Offset to handle negative numbers
            buckets[digit].add(num);
        }

        // Merge the elements back into the original array
        int arrIdx = 0;
        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[arrIdx] = num;
                arrIdx++;
            }
            bucket.clear(); // Clear the bucket for the next iteration
        }

        // Move to the next digit position
        divisor *= 10;
    }
}


    
    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid Input - data is null");
        }
        // Create a priority queue (min-heap) using the data list
        PriorityQueue<Integer> minheap = new PriorityQueue<Integer>(data);
        // Create an array to store the sorted elements
        int[] sorted = new int[data.size()];
        // Extract elements from the min-heap to get sorted order
        for (int i = 0; i < data.size(); i++) {
            // Remove the smallest element from the min-heap and add it to the array
            sorted[i] = minheap.remove();
        }
        return sorted;
    }
}
