package com.billenius;

public class Utils {
    public static boolean arrayContains(int[] array, int toCheck){
        for (int value : array)
            if (value == toCheck)
                return true;
        return false;
    }
}
