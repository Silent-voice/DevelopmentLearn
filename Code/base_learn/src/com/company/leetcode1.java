package com.company;

import java.util.*;

public class leetcode1 {

}




class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int[][] dp = new int[nums.length][nums.length];
        int res = 0;

        for (int i = 0; i < nums.length; i++){
            if(nums[i] % 2 == 0){
                dp[i][i] = 0;
            }else {
                dp[i][i] = 1;
                if (k == 1){
                    res += 1;
                }
            }
        }

        for (int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; j++){
                if (nums[j] % 2 != 0){
                    dp[i][j] = dp[i][j-1] + 1;
                }else {
                    dp[i][j] = dp[i][j-1];
                }
                if (k == dp[i][j]){
                    res += 1;
                }
            }
        }
        return res;
    }
}