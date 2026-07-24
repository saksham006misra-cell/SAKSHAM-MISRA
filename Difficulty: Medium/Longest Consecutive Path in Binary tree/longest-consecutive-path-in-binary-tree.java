/* Structure of Binary Tree Node
class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}
}*/
class Solution {
    public int longestConsecutive(Node root) {
        // code here
              if (root == null) return -1;
        int[] maxLen = {0};
        dfs(root, maxLen);
        // if the best we found is a single isolated node (no actual +1 chaining),
        // there is no valid "path" per problem definition
        return maxLen[0] <= 1 ? -1 : maxLen[0];
    }

    private int dfs(Node node, int[] maxLen) {
        if (node == null) return 0;

        int leftLen = dfs(node.left, maxLen);
        int rightLen = dfs(node.right, maxLen);

        int curLen = 1;

        if (node.left != null && node.left.data == node.data + 1) {
            curLen = Math.max(curLen, leftLen + 1);
        }
        if (node.right != null && node.right.data == node.data + 1) {
            curLen = Math.max(curLen, rightLen + 1);
        }

        maxLen[0] = Math.max(maxLen[0], curLen);
        return curLen; 
    }
}