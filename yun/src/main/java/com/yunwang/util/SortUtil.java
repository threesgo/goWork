package com.yunwang.util;

import java.util.Arrays;

/**
 * @ClassName: SortUtil
 * @Description:TODO(排序算法类，提供数据各种算法排序)
 * @author: yibinfang
 * @date: 2016-8-11 下午9:54:17
 * 
 */
public class SortUtil {

	public static void main(String[] args) {
		int[] toSort = new int[] { 6, 5, 4};
		System.out.println(Arrays.toString(toSort));
		selectionSort(toSort);
		insertSort(toSort);
		binInsertSort(toSort);
		System.out.println(Arrays.toString(toSort));
	}

	// ++++++++++++++++++选择排序++++++++++++++++++++
	/**
	 * @Title: selectionSort
	 * @Description: TODO(简单排序) 分成有序区和无序区，从第一个位置开始 （比较次数多，移动的次数少）
	 * @param:
	 * @return: void
	 * @throws
	 */
	public static void selectionSort(int[] arg) {
		int length = arg.length;
		for (int i = 0; i < length; i++) {
			int min = i;
			for (int j = i + 1; j < length; j++) {
				// 此时每次都交换了,增加的赋值操作
				/*
				 * if(arg[j]<arg[i]){ int temp=arg[i]; arg[i]=arg[j];
				 * arg[j]=temp; }
				 */
				// 要找到最小的在进行值交换
				if (arg[min] > arg[j]) {
					min = j;
				}
			}
			if (min != i) {
				int temp = arg[min];
				arg[min] = arg[i];
				arg[i] = temp;
			}
		}
	}

	// ++++++++++++++++++插入排序++++++++++++++++++++
	/**
	 * @Title: insertSort
	 * @Description: TODO(插入排序) 有序区和无序区，在有序区进行比较插入，插入后的数据后移 （移动次数和比较次数都比较多）
	 * @param: @param arg
	 * @return: void
	 * @throws
	 */
	public static void insertSort(int[] arg) {
		int length = arg.length;
		int j, temp;
		for (int i = 1; i < length; i++) {
			temp = arg[i];
			j = i - 1;
			// 后移操作
			while (j>-1&&temp < arg[j]) {  //--操作会导致下标值为-1,直接结束操作
				arg[j + 1] = arg[j];
				j--;
			}
			// 给插入位置赋值
			arg[j + 1] = temp;
		}
	}

	/**
	 * @Title: binInsertSort
	 * @Description: TODO(折半插入排序)
	 * @param: @param arg
	 * @return: void
	 * @throws
	 */
	public static void binInsertSort(int[] arg) {
		int length = arg.length;
		int temp, low, high, middle;
		for (int i = 1; i < length; i++) {
			temp = arg[i];
			low = 0;
			high = i - 1;
			while (low <= high) { // high等于-1时已经不比较了
				middle = (low + high) / 2;
				if (temp < arg[middle]) { // 应插入低位区域
					high = middle - 1;
				} else {
					low = middle + 1; // 应插入高位区域
				}
			}
			for (int j = i - 1; j >= high + 1; j--) {
				arg[j + 1] = arg[j];
			}
			arg[high + 1] = temp;
		}
	}

	/**
	 * @Title: shellSort
	 * @Description: TODO(希尔排序)
	 * @param: @param arg
	 * @return: void
	 * @throws
	 */
	public static void shellSort(int[] arg) {

	}

	// ++++++++++++++++++交换排序 ++++++++++++++++++++
	/**
	 * @Title: bubbleSort
	 * @Description: TODO(冒泡排序)
	 * @param: @param arg
	 * @return: void
	 * @throws
	 */
	public static void bubbleSort(int[] arg) {

	}

	/**
	 * @Title: quickSort
	 * @Description: TODO(快速排序)
	 * @param: @param arg
	 * @return: void
	 * @throws
	 */
	public static void quickSort(int[] arg) {

	}

	// ++++++++++++++++++归并排序 ++++++++++++++++++++
	/**
	 * @Title: mergeSort
	 * @Description: TODO(归并排序)
	 * @param: @param arg
	 * @return: void
	 * @throws
	 */
	public static void mergeSort(int[] arg) {

	}
}