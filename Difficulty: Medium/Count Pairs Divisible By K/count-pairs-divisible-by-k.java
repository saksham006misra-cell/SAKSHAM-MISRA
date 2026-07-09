class Solution {
    public int countKdivPairs(int[] arr, int k) {
        // code here
      
        // Frequency array to store counts of remainders from 0 to k-1
        long[] remCounters = new long[k];
        long totalPairs = 0;

        for (int num : arr) {
            // Safely calculate remainder, handling potential negative numbers
            int rem = ((num % k) + k) % k;

            // Find the required complement remainder
            int complement = (k - rem) % k;

            // Add the count of matching complements found so far
            totalPairs += remCounters[complement];

            // Record the current remainder in the frequency array
            remCounters[rem]++;
        }

        return (int) totalPairs;
    }
}
 
 