class Solution {
    int minOperations(int[] b) {
        // code here
        final long MOD = 1_000_000_007L;
        int n = b.length;
        boolean[] visited = new boolean[n + 1];
        java.util.List<Integer> cycleLengths = new java.util.ArrayList<>();

        // Step 1: find all cycle lengths in permutation b (1-based logic)
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                int len = 0;
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    j = b[j - 1]; // move to next element in cycle (1-based value)
                    len++;
                }
                cycleLengths.add(len);
            }
        }

        // Step 2: smallest prime factor sieve up to n
        int[] spf = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            if (spf[i] == 0) {
                for (int k = i; k <= n; k += i) {
                    if (spf[k] == 0) spf[k] = i;
                }
            }
        }

        // Step 3: for each cycle length, factorize and track max exponent per prime
        java.util.Map<Integer, Integer> maxExp = new java.util.HashMap<>();
        for (int len : cycleLengths) {
            java.util.Map<Integer, Integer> localExp = new java.util.HashMap<>();
            int val = len;
            while (val > 1) {
                int p = spf[val];
                int cnt = 0;
                while (val % p == 0) {
                    val /= p;
                    cnt++;
                }
                localExp.put(p, cnt);
            }
            for (java.util.Map.Entry<Integer, Integer> entry : localExp.entrySet()) {
                maxExp.merge(entry.getKey(), entry.getValue(), Math::max);
            }
        }

        // Step 4: compute LCM mod = product of p^maxExp mod MOD
        long result = 1;
        for (java.util.Map.Entry<Integer, Integer> entry : maxExp.entrySet()) {
            long p = entry.getKey();
            int e = entry.getValue();
            result = (result * power(p, e, MOD)) % MOD;
        }

        return (int) result;
    }

    private long power(long base, long exp, long mod) {
        base %= mod;
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return res;
    }
}
