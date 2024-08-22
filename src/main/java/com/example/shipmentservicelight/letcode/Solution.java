package com.example.shipmentservicelight.letcode;

class Solution {
    public boolean lemonadeChange(int[] bills) {
        int cass = 0;
        for(int i =0;i<bills.length;i++){
            if(bills[i] == 5){
                cass+=5;
            }else {
                if(cass < bills[i] - 5)
                    return false;

                cass-= (bills[i] -5);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().lemonadeChange(new int[]{5,5,5,10,20}));
    }
}
