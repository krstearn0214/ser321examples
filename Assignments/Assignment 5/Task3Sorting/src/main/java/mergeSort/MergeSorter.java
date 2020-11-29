//**************************************************************************************************
// CLASS: MergeSorter (MergeSorter.java)
//
// DESCRIPTION
// This class contains methods that implement the recursive merge sort algorithm.
//
// AUTHOR
// Kevin R. Burger (burgerk@asu.edu)
// Computer Science & Engineering
// School of Computing, Informatics, and Decision Systems Engineering
// Fulton Schools of Engineering
// Arizona State University, Tempe, AZ 85287-8809
// Web: http://www.devlang.com
//**************************************************************************************************
/*
Additions by
@Author: Kyle Stearns
11/28/2020
for SER321 @ ASU
*/
package mergeSort;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import com.google.common.primitives.Ints;
/**
 * Implements the recursive merge sort algorithm.
 */
public class MergeSorter {

    public static JSONObject mergeSortJSON(JSONObject obj)
    {
        ArrayList<Integer> data = new ArrayList<Integer>();
        for (var val : obj.getJSONArray("data")) {
            data.add((Integer) val);
        }
        mergeSort(data);
        int[] a = Ints.toArray(data);
        System.out.println("prim array sort " + a);
        //int[] a = new int[data.size()];
        //a = data.toArray(a);
        JSONObject sortObj = MergeSort.init(a);
        return sortObj;
    }

    public static JSONObject sortedJSON(JSONObject l, JSONObject r)
    {
        ArrayList<Integer> aD = new ArrayList<Integer>();
        for (var val : l.getJSONArray("data")) {
            aD.add((Integer) val);
        }
        ArrayList<Integer> bD = new ArrayList<Integer>();
        for (var val : r.getJSONArray("data")) {
            bD.add((Integer) val);
        }
        int[] a = Ints.toArray(aD);
        int[] b = Ints.toArray(bD);
        int[] sorted = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length)
        {
            if (a[i] < b[j])
            {
                sorted[k] = a[i];
                i++;
            }
            else
            {
            sorted[k] = b[j];
                j++;
            }
            k++;
        }
        while (i < a.length)
        {
            sorted[k] = a[i];
            i++;
            k++;
        }
        while (j < b.length)
        {
            sorted[k] = b[j];
            j++;
            k++;
        }
        JSONObject sortObj = MergeSort.init(sorted);
        return sortObj;
    }
    /**
     * Creates and returns a new ArrayList consisting of the elements from pList at indices
     * pFromIndex (inclusive) to pToIndex (exclusive).
     */
    private static ArrayList<Integer> arrayListCopy(ArrayList<Integer> pList, int pFromIndex,
    int pToIndex) {
        ArrayList<Integer> pCopy = new ArrayList<>();
        for (int i = pFromIndex; i < pToIndex; ++i) {
            pCopy.add(pList.get(i));
        }
        return pCopy;
    }

    /**
     * Copies elements from pSrcList[pSrcIndex..pSrcList.size - 1] to pDstList starting at
     * index pDstIndex.
     */
    private static void copyRest(ArrayList<Integer> pSrcList, int pSrcIndex,
    ArrayList<Integer> pDstList, int pDstIndex) {
        while (pSrcIndex < pSrcList.size()) {
            pDstList.set(pDstIndex, pSrcList.get(pSrcIndex));
            ++pSrcIndex;
            ++pDstIndex;
        }
    }

    /**
     * The primary recursive routine that merge sorts pList.
     */
    public static void mergeSort(ArrayList<Integer> pList) {
        // The base case is reached when the size of pList is 1. The list is trivially
        // sorted so we have nothing to do.
        if (pList.size() < 2) return;

        // Otherwise, split the list into two halves: a left half and a right half. Recursively
        // merge sort each half.
        ArrayList<Integer> listL = arrayListCopy(pList, 0, pList.size() / 2);
        ArrayList<Integer> listR = arrayListCopy(pList, pList.size() / 2, pList.size());
        mergeSort(listL);
        mergeSort(listR);

        // On return from the two method calls above, both listL and listR will be sorted.
        // Merge them to form a sorted list.
        merge(listL, listR, pList);
    }

    /**
     * Merges pListL and pListR into pList.
     */
    private static void merge(ArrayList<Integer> pListL, ArrayList<Integer> pListR,
    ArrayList<Integer> pList) {
        int leftIndex = 0, rightIndex = 0, listIndex = 0;
        while (leftIndex < pListL.size() && rightIndex < pListR.size()) {
            if (pListL.get(leftIndex).compareTo(pListR.get(rightIndex)) <= 0) {
                pList.set(listIndex, pListL.get(leftIndex));
                ++leftIndex;
            } else {
                pList.set(listIndex, pListR.get(rightIndex));
                ++rightIndex;
            }
            ++listIndex;
        }
        if (leftIndex < pListL.size()) {
            copyRest(pListL, leftIndex, pList, listIndex);
        } else {
            copyRest(pListR, rightIndex, pList, listIndex);
        }
    }
}
