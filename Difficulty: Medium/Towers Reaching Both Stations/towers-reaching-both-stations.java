class Solution {
    public int countCoordinates(int[][] mat) {
      
        if (mat == null || mat.length == 0 || mat[0].length == 0) return 0;

        int n = mat.length;
        int m = mat[0].length;

        // Matrices to keep track of which cells can reach Station P and Station Q
        boolean[][] canReachP = new boolean[n][m];
        boolean[][] canReachQ = new boolean[n][m];

        // Queues for multi-source BFS
        Queue<int[]> qP = new LinkedList<>();
        Queue<int[]> qQ = new LinkedList<>();

        // Add the Left and Right boundaries
        for (int i = 0; i < n; i++) {
            // Station P: Left Boundary
            qP.add(new int[]{i, 0});
            canReachP[i][0] = true;
            
            // Station Q: Right Boundary
            qQ.add(new int[]{i, m - 1});
            canReachQ[i][m - 1] = true;
        }

        // Add the Top and Bottom boundaries
        for (int j = 0; j < m; j++) {
            // Station P: Top Boundary
            if (!canReachP[0][j]) { // avoid adding the corner twice
                qP.add(new int[]{0, j});
                canReachP[0][j] = true;
            }
            
            // Station Q: Bottom Boundary
            if (!canReachQ[n - 1][j]) { // avoid adding the corner twice
                qQ.add(new int[]{n - 1, j});
                canReachQ[n - 1][j] = true;
            }
        }

        // Run BFS for both stations to expand paths inward
        bfs(mat, canReachP, qP, n, m);
        bfs(mat, canReachQ, qQ, n, m);

        // Count cells that can reach both Station P and Station Q
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (canReachP[i][j] && canReachQ[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void bfs(int[][] mat, boolean[][] canReach, Queue<int[]> queue, int n, int m) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            for (int[] d : directions) {
                int nr = r + d[0];
                int nc = c + d[1];

                // Check bounds and ensure we haven't visited this neighbor already
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !canReach[nr][nc]) {
                    // Moving backwards: signal flows from neighbor to current cell.
                    // This is valid only if neighbor's value >= current cell's value.
                    if (mat[nr][nc] >= mat[r][c]) {
                        canReach[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                    }
                }
            }
        }
    }
}

    