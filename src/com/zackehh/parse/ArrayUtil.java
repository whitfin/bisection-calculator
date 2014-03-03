package com.zackehh.parse;

public abstract class ArrayUtil {
    public static double[] reverse(double[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            // Swap the values at the left and right indices
            double temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            // Move the left and right index pointers in toward the center
            left++;
            right--;
        }
        return data;
    }
}
