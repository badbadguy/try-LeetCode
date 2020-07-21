import org.junit.Test;

/**
 * @Author LiuRunYuan
 * @CreateDate 2020/4/13
 */
public class balanced_binary_tree {
    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * <p>
     * 本题中，一棵高度平衡二叉树定义为：
     * <p>
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
     */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    @Test
    public void try110() {
        TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(9);
        treeNode.right = new TreeNode(20);
        treeNode.right.left = new TreeNode(15);
        treeNode.right.right = new TreeNode(7);
        boolean balanced = isBalanced(treeNode);
        System.out.println(balanced);

        TreeNode treeNode2 = new TreeNode(1);
        treeNode2.left = new TreeNode(2);
        treeNode2.right = new TreeNode(2);
        treeNode2.left.left = new TreeNode(3);
        treeNode2.left.right = new TreeNode(3);
        treeNode2.left.left.left = new TreeNode(4);
        treeNode2.left.left.right = new TreeNode(4);
        boolean balanced2 = isBalanced(treeNode2);
        System.out.println(balanced2);

        TreeNode treeNode3 = new TreeNode(1);
        boolean balanced3 = isBalanced(treeNode3);
        System.out.println(balanced3);

        TreeNode treeNode1 = new TreeNode(1);
        treeNode1.right = new TreeNode(2);
        treeNode1.right.right = new TreeNode(3);
        boolean balanced1 = isBalanced(treeNode3);
        System.out.println(balanced1);
    }

    public boolean isBalanced(TreeNode root) {
        return recur(root) != -1;
    }

    private int recur(TreeNode root) {
        if (root == null) return 0;
        int left = recur(root.left);
        if(left == -1) return -1;
        int right = recur(root.right);
        if(right == -1) return -1;
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }





    @Test
    public void trrrrr(){
        int[] nums = {-4,-3};
        int i = maxProduct(nums);
        System.out.println(i);
    }


    public int maxProduct(int[] nums) {
        if(nums.length < 2){
            return nums[0];
        }
        int max = 0;
        for(int i = 1; i< nums.length;i++){
            int first = nums[i-1];
            int end = nums[i];
            int sum = first*end;
            max = sum>max?sum:max;
        }
        return max;
    }
}
