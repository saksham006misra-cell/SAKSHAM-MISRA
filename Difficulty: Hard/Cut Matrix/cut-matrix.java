class Solution {
    static final int MOD = 1_000_000_007;
    int[][] pre;
    int R, C;

    public int findWays(int[][] matrix, int k) {
        // code here
        R = matrix.length;
        C = matrix[0].length;

        pre = new int[R + 1][C + 1];
        for (int i = 0; i < R; i++)
            for (int j = 0; j < C; j++)
                pre[i + 1][j + 1] = pre[i][j + 1] + pre[i + 1][j] - pre[i][j] + matrix[i][j];

        long[][] dp = new long[R][C];
        dp[0][0] = 1;

        for (int cuts = 1; cuts < k; cuts++) {
            long[][] ndp = new long[R][C];

            // ---- Horizontal cuts: process column by column ----
            for (int c = 0; c < C; c++) {
                long[] diff = new long[R + 1];
                boolean any = false;
                for (int r = 0; r < R; r++) {
                    long val = dp[r][c];
                    if (val == 0 || r > R - 2) continue;
                    if (rectSum(r, R - 2, c, C - 1) == 0) continue;
                    int iMin = binSearchRow(r, c);
                    diff[iMin + 1] = (diff[iMin + 1] + val) % MOD;
                    diff[R] = (diff[R] - val % MOD + MOD) % MOD;
                    any = true;
                }
                if (any) {
                    long running = 0;
                    for (int row = 0; row < R; row++) {
                        running = (running + diff[row]) % MOD;
                        if (running != 0) ndp[row][c] = (ndp[row][c] + running) % MOD;
                    }
                }
            }

            // ---- Vertical cuts: process row by row ----
            for (int r = 0; r < R; r++) {
                long[] diff = new long[C + 1];
                boolean any = false;
                for (int c = 0; c < C; c++) {
                    long val = dp[r][c];
                    if (val == 0 || c > C - 2) continue;
                    if (rectSum(r, R - 1, c, C - 2) == 0) continue;
                    int jMin = binSearchCol(r, c);
                    diff[jMin + 1] = (diff[jMin + 1] + val) % MOD;
                    diff[C] = (diff[C] - val % MOD + MOD) % MOD;
                    any = true;
                }
                if (any) {
                    long running = 0;
                    for (int col = 0; col < C; col++) {
                        running = (running + diff[col]) % MOD;
                        if (running != 0) ndp[r][col] = (ndp[r][col] + running) % MOD;
                    }
                }
            }

            dp = ndp;
        }

        long ans = 0;
        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++)
                if (dp[r][c] != 0 && rectSum(r, R - 1, c, C - 1) > 0)
                    ans = (ans + dp[r][c]) % MOD;

        return (int) ans;
    }

    // smallest i in [r, R-2] such that sum(r..i, c..C-1) > 0
    private int binSearchRow(int r, int c) {
        int lo = r, hi = R - 2, res = hi;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (rectSum(r, mid, c, C - 1) > 0) { res = mid; hi = mid - 1; }
            else lo = mid + 1;
        }
        return res;
    }

    // smallest j in [c, C-2] such that sum(r..R-1, c..j) > 0
    private int binSearchCol(int r, int c) {
        int lo = c, hi = C - 2, res = hi;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (rectSum(r, R - 1, c, mid) > 0) { res = mid; hi = mid - 1; }
            else lo = mid + 1;
        }
        return res;
    }

    private int rectSum(int r1, int r2, int c1, int c2) {
        return pre[r2 + 1][c2 + 1] - pre[r1][c2 + 1] - pre[r2 + 1][c1] + pre[r1][c1];
    
    }
}