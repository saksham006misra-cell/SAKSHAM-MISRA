class Solution {
    public int maxIndexDifference(String s) {
        // code here
       
        int n = s.length();
        
        // bestReach[i] keeps track of the maximum index reachable 
        // by any character tracking (i + 'a') found to its right
        int[] bestReach = new int[26];
        Arrays.fill(bestReach, -1);
        
        // reach[i] will store the absolute farthest index 
        // that can be reached starting from index i
        int[] reach = new int[n];
        int maxDiff = -1;
        
        // Traverse backwards from right to left
        for (int i = n - 1; i >= 0; i--) {
            int c = s.charAt(i) - 'a';
            
            // By default, a character can at least reach itself
            reach[i] = i;
            
            // If the immediate next alphabet character ('c' + 1) exists to its right,
            // we can jump to it and inherit its maximum downstream reach.
            if (c < 25 && bestReach[c + 1] != -1) {
                reach[i] = Math.max(reach[i], bestReach[c + 1]);
            }
            
            // Record or update the best reach for character 'c' at this point
            bestReach[c] = Math.max(bestReach[c], reach[i]);
            
            // According to problem constraints, a valid chain must start with 'a' (c == 0)
            if (c == 0) {
                maxDiff = Math.max(maxDiff, reach[i] - i);
            }
        }
        
        return maxDiff;
    }
}
 