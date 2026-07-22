class Solution {
    public int minDeletions(int[] arr) {
        // code here
     
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        int n = arr.length;
        // tails[i] stores the smallest tail of all increasing subsequences of length i + 1
        int[] tails = new int[n];
        int len = 0; // Stores the length of the Longest Increasing Subsequence (LIS)
        
        for (int num : arr) {
            // Binary search to find the insertion point of 'num' in the active tails array
            int idx = Arrays.binarySearch(tails, 0, len, num);
            
            // If num is not found, binarySearch returns (-(insertion point) - 1)
            if (idx < 0) {
                idx = -(idx + 1);
            }
            
            // Update the tail element at the insertion point
            tails[idx] = num;
            
            // If num is placed at the end of the current active elements, extend the LIS length
            if (idx == len) {
                len++;
            }
        }
        
        // Minimum deletions required = Total elements - Elements kept (LIS length)
        return n - len;
    }
}
 
    