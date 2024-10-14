package podpivasniki.shortfy.site.branchedpipeline;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
class Solution {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(3);
        left.right = new TreeNode(4);

        TreeNode right = new TreeNode(3);
        right.left = new TreeNode(4);
        right.right = new TreeNode(3);

        root.left = left;
        root.right = right;
        System.out.println(new Solution().isSymmetric(root));
    }
    public boolean isSymmetric(TreeNode root) {
        List<Integer> l = new ArrayList<>();
        recurs(root.left,l,true);
        List<Integer> r = new ArrayList<>();
        recurs(root.right,r,false);
        System.out.println(l);
        System.out.println(r);
        return false;
    }
    private void recurs(TreeNode treeNode, List<Integer> list, boolean isLeft){
        if(treeNode != null){
            list.add(treeNode.val);
            if(isLeft){
                recurs(treeNode.left, list, isLeft);
                recurs(treeNode.right, list, isLeft);
            }else {
                recurs(treeNode.right, list, isLeft);
                recurs(treeNode.left, list, isLeft);
            }
        }
    }
}
