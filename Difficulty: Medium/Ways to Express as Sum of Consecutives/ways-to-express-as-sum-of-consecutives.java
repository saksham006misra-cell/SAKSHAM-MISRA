class Solution {
    public int getCount(int n) {
        // code here
       // Remove all even factors by dividing by 2
        while (n % 2 == 0) {
            n /= 2;
        }

        int oddFactorsCount = 1;

        // Count prime factors of the remaining odd number
        for (int i = 3; i * i <= n; i += 2) {
            int count = 0;
            while (n % i == 0) {
                count++;
                n /= i;
            }
            // Use the divisor function rule: multiply by (exponent + 1)
            oddFactorsCount *= (count + 1);
        }

        // If n is still greater than 2, it is a prime factor itself
        if (n > 2) {
            oddFactorsCount *= 2;
        }

        // Subtract 1 to exclude the single-number representation
        return oddFactorsCount - 1;
      
    }
};