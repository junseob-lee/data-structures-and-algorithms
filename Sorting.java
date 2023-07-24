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

        // Find the index of the minimum element in the remaining unsorted part
        for (int j = i + 1; j < n; j++) {
            if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                minIndex = j;
            }
        }

        // Swap the minimum element with the current element
        if (minIndex != i) {
            T temp = arr[i];
            arr[i] = arr[minIndex];
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
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
    // Check for null inputs
    if (arr == null || comparator == null) {
        throw new IllegalArgumentException("Array or comparator cannot be null.");
    }

    // Get the length of the array
    int n = arr.length;

    // Iterate through the array starting from the second element
    for (int i = 1; i < n; i++) {
        // Store the current element to be inserted into the sorted part
        T key = arr[i];

        // Move elements that are greater than the key to the right
        int j = i - 1;
        while (j >= 0 && comparator.compare(arr[j], key) > 0) {
            arr[j + 1] = arr[j];
            j--;
        }

        // Insert the key into the correct position
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
    // Check for null inputs
    if (arr == null || comparator == null) {
        throw new IllegalArgumentException("Input is invalid");
    }

    // Get the length of the array
    int len = arr.length;

    // Base case: If the array has 0 or 1 elements, it is already sorted
    if (len <= 1) {
        return;
    }

    // Find the middle index to split the array into two halves
    int mid = len / 2;

    // Create temporary arrays to store the left and right halves
    T[] left = (T[]) new Object[mid];
    T[] right = (T[]) new Object[len - mid];

    // Copy elements into the temporary left and right arrays
    for (int i = 0; i < mid; i++) {
        left[i] = arr[i];
    }
    for (int i = mid; i < len; i++) {
        right[i - mid] = arr[i];
    }

    // Recursively sort the left and right halves
    mergeSort(left, comparator);
    mergeSort(right, comparator);

    // Merge the sorted left and right halves back into the original array
    merge(arr, left, right, comparator);
}

private static <T> void merge(T[] arr, T[] left, T[] right, Comparator<T> comparator) {
    int leftIdx = 0;
    int rightIdx = 0;
    int currIdx = 0;

    // Merge the left and right arrays by comparing elements
    while (leftIdx < left.length && rightIdx < right.length) {
        if (comparator.compare(left[leftIdx], right[rightIdx]) <= 0) {
            arr[currIdx++] = left[leftIdx++];
        } else {
            arr[currIdx++] = right[rightIdx++];
        }
    }

    // Copy any remaining elements from the left and right arrays
    while (leftIdx < left.length) {
        arr[currIdx++] = left[leftIdx++];
    }
    while (rightIdx < right.length) {
        arr[currIdx++] = right[rightIdx++];
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
    // Check for null input
    if (arr == null) {
        throw new IllegalArgumentException("Input is invalid");
    }

    // Base case: If the array is empty or has only one element, it is already sorted
    if (arr.length <= 1) {
        return;
    }

    // Find the value of k, which is the number of digits in the longest number in the array
    int kVal = khelper(arr);

    // Perform radix sort for each digit place value from LSD to MSD
    int div = 1;
    for (int i = 0; i < kVal; i++) {
        // Create an array of LinkedLists to store the elements for each digit place value
        LinkedList<Integer>[] dummy = (LinkedList<Integer>[]) new LinkedList[19];

        // Distribute elements to the appropriate bucket based on the current digit place value
        for (int j = 0; j < arr.length; j++) {
            int index = (arr[j] / div) % 10 + 9;
            if (dummy[index] == null) {
                dummy[index] = new LinkedList<>();
            }
            dummy[index].addLast(arr[j]);
        }

        // Update the div value to consider the next digit place value
        div *= 10;

        // Reconstruct the array by merging the elements from the buckets back into the original array
        int newIdx = 0;
        for (LinkedList<Integer> list : dummy) {
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    arr[newIdx] = list.get(j);
                    newIdx++;
                }
            }
        }
    }
}

/**
 * Helper method for finding the number of digits in the longest number in the array.
 *
 * @param arr target array
 * @return length of the longest number in the array
 */
private static int khelper(int[] arr) {
    int k = 0;
    int target = Math.abs(arr[0]);

    // Find the absolute value of the maximum element in the array
    for (int i = 1; i < arr.length; i++) {
        int curr = Math.abs(arr[i]);

        // Handle special case for Integer.MIN_VALUE
        if (curr == Integer.MIN_VALUE) {
            target = Integer.MIN_VALUE;
            break;
        } else if (curr > target) {
            target = curr;
        }
    }

    // If all elements are zero, set k to 1
    if (target == 0) {
        k = 1;
    }

    // If target is Integer.MIN_VALUE, return 10 as the number of digits for the special case
    if (target == Integer.MIN_VALUE) {
        return 10;
    }

    // Find the number of digits for the target value
    while (target > 0) {
        target /= 10;
        k++;
    }

    return k;
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
        int[] sorted = new int[data.size()];
        // Extract elements from the min-heap to get sorted order
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = minheap.remove();
        }
        return sorted;
    }
}
