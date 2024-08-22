package com.example.shipmentservicelight.letcode;

class NumArray {
    int[] prefix;

    public NumArray(int[] nums) {
        prefix = new int[nums.length+1];
        prefix[0] = 0;
        for(int i =0;i<nums.length;i++){
            prefix[i+1] = prefix[i]+nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        return prefix[right+1] - prefix[left];
    }

    public static void main(String[] args) {
        NumArray n =new NumArray(new int[]{-2,0,3,-5,2,-1});
        System.out.println(n);
        System.out.println(n.sumRange(0,5));
    }
}