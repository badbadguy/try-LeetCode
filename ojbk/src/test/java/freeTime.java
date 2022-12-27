import org.junit.Test;

import java.util.*;

/**
 * @author LiuRunYuan
 * @date 2020/6/4
 */
public class freeTime {

    /**
     * 1431. 拥有最多糖果的孩子
     * <p>
     * 给你一个数组candies和一个整数extraCandies，其中candies[i]代表第 i 个孩子拥有的糖果数目。
     * <p>
     * 对每一个孩子，检查是否存在一种方案，将额外的extraCandies个糖果分配给孩子们之后，此孩子有 最多的糖果。注意，允许有多个孩子同时拥有 最多的糖果数目。
     * <p>
     * 输入：candies = [2,3,5,1,3], extraCandies = 3
     * 输出：[true,true,true,false,true]
     * <p>
     * 输入：candies = [4,2,1,1,2], extraCandies = 1
     * 输出：[true,false,false,false,false]
     * <p>
     * 输入：candies = [12,1,12], extraCandies = 10
     * 输出：[true,false,true]
     */
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        for (int temp : candies) {
            max = Math.max(temp, max);
        }
        List<Boolean> result = new LinkedList<>();
        for (int temp : candies) {
            result.add(temp + extraCandies >= max);
        }
        return result;
    }

    @Test
    public void try1431() {
        int[] temp1 = new int[]{2, 3, 5, 1, 3};
        int[] temp2 = new int[]{4, 2, 1, 1, 2};
        int[] temp3 = new int[]{12, 1, 12};

        System.out.println(kidsWithCandies(temp1, 3).toString());
        System.out.println(kidsWithCandies(temp2, 1).toString());
        System.out.println(kidsWithCandies(temp3, 10).toString());
    }

    /**
     * 面试题58 - II. 左旋转字符串
     * <p>
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
     * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     * <p>
     * 输入: s = "abcdefg", k = 2
     * 输出: "cdefgab"
     * <p>
     * 输入: s = "lrloseumgh", k = 6
     * 输出: "umghlrlose"
     */
    public String reverseLeftWords(String s, int n) {
        return s.substring(n).concat(s.substring(0, n));
    }

    @Test
    public void try58() {
        System.out.println(reverseLeftWords("abcdefg", 2));
        System.out.println(reverseLeftWords("lrloseumgh", 6));
    }

    /**
     * 面试题 02.03. 删除中间节点
     * <p>
     * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
     * <p>
     * 输入：单向链表a->b->c->d->e->f中的节点c
     * 结果：不返回任何数据，但该链表变为a->b->d->e->f
     */
    public class ListNode {
        String val;
        ListNode next;

        ListNode(String x) {
            val = x;
        }
    }

    public void deleteNode(ListNode node) {
        while (true) {
            if (node.next != null) {
                node.val = node.next.val;
                if (node.next.next == null) {
                    node.next = null;
                    return;
                }
                node = node.next;
            }
        }
        //看到别人是直接删除下一个节点
        //即  将下一个节点的值赋给该节点，然后将该节点的next指向下下个节点
        //a->b->c->d->e->f 初始
        //a->b->d->d->e->f 将下一个节点的值赋给该节点
        //a->b->d->e->f 该节点的next指向下下个节点
        //看上去是删除了该节点  但实际是删除了下一个节点
        //wc 称之为绝活  我真蠢

        //node.val = node.next.val;
        //node.next = node.next.next;
    }

    @Test
    public void tryMS0203() {
        //a->b->c->d->e->f
        ListNode list = new ListNode("a");
        list.next = new ListNode("b");
        ListNode listNode = list.next.next = new ListNode("c");
        list.next.next.next = new ListNode("d");
        list.next.next.next.next = new ListNode("e");
        list.next.next.next.next.next = new ListNode("f");
        deleteNode(listNode);
        System.out.println();
    }

    /**
     * LCP 01. 猜数字
     * <p>
     * 小A 和 小B 在玩猜数字。小B 每次从 1, 2, 3 中随机选择一个，小A 每次也从 1, 2, 3 中选择一个猜。他们一共进行三次这个游戏，请返回 小A 猜对了几次？
     * <p>
     * 输入的guess数组为 小A 每次的猜测，answer数组为 小B 每次的选择。guess和answer的长度都等于3。
     * <p>
     * 输入：guess = [1,2,3], answer = [1,2,3]
     * 输出：3
     * 解释：小A 每次都猜对了。
     * <p>
     * 输入：guess = [2,2,3], answer = [3,2,1]
     * 输出：1
     * 解释：小A 只猜对了第二次。
     */
    public int game(int[] guess, int[] answer) {
        int answerNum = 0;
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == answer[i]) {
                answerNum++;
            }
        }
        return answerNum;
    }

    @Test
    public void tryLCP01() {
        System.out.println(game(new int[]{1, 2, 3}, new int[]{1, 2, 3}));
        System.out.println(game(new int[]{2, 2, 3}, new int[]{3, 2, 1}));
    }

    /**
     * 771. 宝石与石头
     * 给定字符串J代表石头中宝石的类型，和字符串S代表你拥有的石头。S中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
     * <p>
     * J中的字母不重复，J和S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
     * <p>
     * 输入: J = "aA", S = "aAAbbbb"
     * 输出: 3
     * <p>
     * 输入: J = "z", S = "ZZ"
     * 输出: 0
     * <p>
     * S 和 J 最多含有50个字母。
     * J 中的字符不重复。
     */
    public int numJewelsInStones(String J, String S) {
        Set<Character> checkSet = new HashSet<Character>();
        for (char temp : J.toCharArray()) {
            checkSet.add(temp);
        }
        int num = 0;
        for (char temp : S.toCharArray()) {
            if (checkSet.contains(temp)) {
                num++;
            }
        }
        return num;
    }

    @Test
    public void try771() {
        System.out.println(numJewelsInStones("aA", "aAAbbbb"));
        System.out.println(numJewelsInStones("z", "ZZ"));
    }

    /**
     * 1342. 将数字变成 0 的操作次数
     * <p>
     * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
     * <p>
     * 输入：num = 14
     * 输出：6
     * <p>
     * 输入：num = 8
     * 输出：4
     * <p>
     * 输入：num = 123
     * 输出：12
     */
    public int numberOfSteps(int num) {
        int result = 0;
        while (true) {
            if (num % 2 == 0) {
                num /= 2;
            } else {
                num--;
            }
            result++;
            if (num == 0) {
                return result;
            }
        }
    }

    @Test
    public void try1342() {
        System.out.println(numberOfSteps(14));
        System.out.println(numberOfSteps(8));
        System.out.println(numberOfSteps(123));
    }

    /**
     * 461. 汉明距离
     * <p>
     * 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
     * 给出两个整数 x 和 y，计算它们之间的汉明距离。
     * 注意：
     * 0 ≤ x, y < 231.
     * <p>
     * 输入: x = 1, y = 4
     * 输出: 2
     */
    public int hammingDistance(int x, int y) {
        int result = 0;
        String xStr = Integer.toBinaryString(x);
        String yStr = Integer.toBinaryString(y);

        int size = Math.max(xStr.length(), yStr.length());
        int xIndex = size - xStr.length();
        int yIndex = size - yStr.length();
        for (int i = 0; i < size; i++) {
            int i1 = i - xIndex;
            int i2 = i - yIndex;
            if (i1 >= 0 && i2 >= 0) {
                if (xStr.charAt(i1) != yStr.charAt(i2)) {
                    result++;
                }
            } else if (i1 >= 0 && i2 <= 0 && xStr.charAt(i1) == '1') {
                result++;
            } else if (i1 <= 0 && i2 >= 0 && yStr.charAt(i2) == '1') {
                result++;
            }
        }
        return result;
    }

    /**
     * 方法二是逐位移动，逐位比较边缘位置是否为 1。寻找一种更快的方法找出等于 1 的位数。
     * <p>
     * 是否可以像人类直观的计数比特为 1 的位数，跳过两个 1 之间的 0。例如：10001000。
     * <p>
     * 上面例子中，遇到最右边的 1 后，如果可以跳过中间的 0，直接跳到下一个 1，效率会高很多。
     * <p>
     * 这是布赖恩·克尼根位计数算法的基本思想。该算法使用特定比特位和算术运算移除等于 1 的最右比特位。
     */
    public int hammingDistance1(int x, int y) {
        int xor = x ^ y;
        int distance = 0;
        while (xor != 0) {
            distance += 1;
            // remove the rightmost bit of '1'
            xor = xor & (xor - 1);
        }
        return distance;
    }

    @Test
    public void try461() {
        System.out.println(hammingDistance(1, 4));
    }


    /**
     * 快排
     */
    @Test
    public void fast() {
        int[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
        quickSort(arr, 0, arr.length - 1);
        System.out.println();
    }

    private void quickSort(int[] data, int low, int hight) {
        if (low > hight) {
            return;
        }

        int left = low;
        int right = hight;
        int key = data[low];
        while (left < right) {
            while (left < right && data[right] >= key) {
                right--;
            }
            while (left < right && data[left] <= key) {
                left++;
            }
            if (left < right) {
                int temp = data[left];
                data[left] = data[right];
                data[right] = temp;
            }
        }

        data[low] = data[left];
        data[left] = key;

        quickSort(data, low, left - 1);
        quickSort(data, left + 1, hight);
    }

    @Test
    public void getLength() {
        String temp1 = "xxxx分公司";
//        String temp2 = "xxxx分公";

        System.out.println(temp1.substring(0, temp1.indexOf("分公司")));
//        System.out.println(temp2.substring(0, temp2.indexOf("分公司")));
    }

    /**
     * 2. 两数相加
     * <p>
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * <p>
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
    static class try2 {

        public static class ListNode {
            int val;
            ListNode next;

            ListNode(int x) {
                val = x;
            }
        }

        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            boolean carry = false;
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
            ListNode resultListNode = new ListNode(0);
            ListNode tempListNode = resultListNode;

            while (true) {
                int temp = carry ? 1 : 0;
                int s = l1.val + l2.val + temp;
                carry = s >= 10;
                if (carry) s -= 10;
                tempListNode.val = s;

                if (l1.next == null && l2.next == null && !carry) {
                    return resultListNode;
                }

                tempListNode.next = new ListNode(0);
                tempListNode = tempListNode.next;

                if (l1.next == null) {
                    l1.next = new ListNode(0);
                }
                l1 = l1.next;
                if (l2.next == null) {
                    l2.next = new ListNode(0);
                }
                l2 = l2.next;
            }
        }
    }

    @Test
    public void test() {
        try2.ListNode temp1 = new try2.ListNode(2);
        temp1.next = new try2.ListNode(4);
        temp1.next.next = new try2.ListNode(3);
        try2.ListNode temp2 = new try2.ListNode(5);
        temp2.next = new try2.ListNode(6);
        temp2.next.next = new try2.ListNode(4);

        try2.ListNode listNode = try2.addTwoNumbers(temp1, temp2);
        System.out.println();
    }


    /**
     * 416. 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     */
    private boolean canPartitionFlag = false;

    public boolean canPartition(int[] nums) {
        //过滤一遍数据
        int sum = 0;
        Set<Integer> numberSet = new HashSet<>();
        for (int temp : nums) {
            if (numberSet.contains(temp)) {
                //已经出现过
                sum -= temp;
                numberSet.remove(temp);
            } else {
                //未出现过
                sum += temp;
                numberSet.add(temp);
            }
        }
        if ((sum & 1) == 1) {
            //该数组合计为单数  无法正好分成两个相等子集
            return false;
        }
        this.canPartitionTryToAdd(numberSet.toArray(new Integer[0]), 0, sum / 2, 0);
        return canPartitionFlag;
    }

    private void canPartitionTryToAdd(Integer[] nums, int index, int halfSum, int nowSum) {
        System.out.println("当前标记：" + index + " 当前总和：" + nowSum + " 目标总和：" + halfSum);
        if (canPartitionFlag) {
            //如果已有符合条件的子集、直接返回
            return;
        } else if (nowSum > halfSum) {
            //该情况已比目标值大  无需继续
            return;
        } else if (index == nums.length) {
            //已到尽头
            return;
        }

        if (nowSum == halfSum) {
            //当前发现可分开的子集
            canPartitionFlag = true;
            return;
        } else {
            //增加
            this.canPartitionTryToAdd(nums, index + 1, halfSum, nowSum + nums[index]);
            //不增加
            this.canPartitionTryToAdd(nums, index + 1, halfSum, nowSum);
        }
    }


    /**
     * 示例 1：
     * <p>
     * 输入：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
     * 示例 2：
     * <p>
     * 输入：nums = [1,2,3,5]
     * 输出：false
     * 解释：数组不能分割成两个元素和相等的子集。
     */
    @Test
    public void try416() {
        System.out.println("true->" + canPartition(new int[]{1, 5, 11, 5}));
        System.out.println("false->" + canPartition(new int[]{1, 2, 3, 5}));
        System.out.println("false->" + canPartition(new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 99, 97}));
    }

    /**
     * 在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用0来表示.
     * 一次移动定义为选择0与一个相邻的数字（上下左右）进行交换.
     * 最终当板board的结果是[[1,2,3],[4,5,0]]谜板被解开。
     * 给出一个谜板的初始状态，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
     */
    public int slidingPuzzle(int[][] board) {
        //每个位置能移动的格子
        Map<String, Set<String>> ableMoveMap = this.getAbleMoveMap();
        //位移的消耗-确认某一种情况是否已经遇到过 以及 记录该情况的操作值
        Map<String, Integer> routeHistoryMap = new HashMap<>();

        //填充可以移动的方块
        Queue<String> ableMoveQueue = new LinkedList<>();
        //找到0在哪里  作为初始化
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j] == 0) {
                    ableMoveQueue.addAll(ableMoveMap.get(i + "-" + j));
                    break;
                }
            }
            //已经找到了就退出
            if (ableMoveQueue.size() > 0) {
                break;
            }
        }

        //根本没有0
        if (ableMoveQueue.size() == 0) {
            return -1;
        }

        while (ableMoveQueue.size() != 0) {
            String peek = ableMoveQueue.peek();
            ableMoveQueue.poll();
        }


        return 0;
    }

    private Map<String, Set<String>> getAbleMoveMap() {
        Map<String, Set<String>> ableMoveMap = new HashMap<>(6);
        ableMoveMap.put("0-0", new HashSet<>(Arrays.asList("0-1", "1-0")));
        ableMoveMap.put("0-1", new HashSet<>(Arrays.asList("0-0", "0-2", "1-1")));
        ableMoveMap.put("0-2", new HashSet<>(Arrays.asList("0-1", "1-2")));
        ableMoveMap.put("1-0", new HashSet<>(Arrays.asList("0-0", "1-1")));
        ableMoveMap.put("1-1", new HashSet<>(Arrays.asList("1-0", "0-1", "1-2")));
        ableMoveMap.put("1-2", new HashSet<>(Arrays.asList("1-1", "0-2")));


        return ableMoveMap;
    }

    /**
     * 输入：board = [[1,2,3],[4,0,5]]
     * 输出：1
     * 解释：交换 0 和 5 ，1 步完成
     * <p>
     * 输入：board = [[1,2,3],[5,4,0]]
     * 输出：-1
     * 解释：没有办法完成谜板
     * <p>
     * 输入：board = [[4,1,2],[5,0,3]]
     * 输出：5
     * 解释：
     * 最少完成谜板的最少移动次数是 5 ，
     * 一种移动路径:
     * 尚未移动: [[4,1,2],[5,0,3]]
     * 移动 1 次: [[4,1,2],[0,5,3]]
     * 移动 2 次: [[0,1,2],[4,5,3]]
     * 移动 3 次: [[1,0,2],[4,5,3]]
     * 移动 4 次: [[1,2,0],[4,5,3]]
     * 移动 5 次: [[1,2,3],[4,5,0]]
     * <p>
     * 输入：board = [[3,2,4],[1,5,0]]
     * 输出：14
     */
    @Test
    public void try773() {
        System.out.println("1->" + slidingPuzzle(new int[][]{{1, 2, 3}, {4, 0, 5}}));
        System.out.println("-1->" + slidingPuzzle(new int[][]{{1, 2, 3}, {5, 0, 4}}));
        System.out.println("5->" + slidingPuzzle(new int[][]{{4, 1, 2}, {5, 0, 3}}));
        System.out.println("14->" + slidingPuzzle(new int[][]{{3, 2, 4}, {1, 5, 0}}));
    }

    /**
     * 62. 不同路径
     * 一个机器人位于一个 m x n网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     */
    public int uniquePaths(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;

            if (i != 0) {
                for (int j = 1; j < n; j++) {
                    dp[0][j] = 1;
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 示例 1：
     * 输入：m = 3, n = 7
     * 输出：28
     * <p>
     * 示例 2：
     * 输入：m = 3, n = 2
     * 输出：3
     * 解释：
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向下
     * <p>
     * 示例 3：
     * 输入：m = 7, n = 3
     * 输出：28
     * <p>
     * 示例 4：
     * 输入：m = 3, n = 3
     * 输出：6
     */
    @Test
    public void try62() {
//        System.out.println("28->" + this.uniquePaths(3, 7));
//        System.out.println("3->" + this.uniquePaths(3, 2));
//        System.out.println("28->" + this.uniquePaths(7, 3));
//        System.out.println("6->" + this.uniquePaths(3, 3));
        System.out.println("1->" + this.uniquePaths(1, 2));
    }

    /**
     * 64. 最小路径和
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 说明：每次只能向下或者向右移动一步。
     */
    public int minPathSum(int[][] grid) {
        //每次只能向下或者向右移动一步
        //dp存每一格子最少的步数  右下角即[m-1][n-1]
        int sum = 0;
        //极限情况 只有一行/列
        if (grid.length == 1) {
            //只能一直向右
            for (int temp : grid[0]) {
                sum += temp;
            }
            return sum;
        }
        if (grid[0].length == 1) {
            //只能一直向下
            for (int[] temp : grid) {
                sum += temp[0];
            }
            return sum;
        }

        for (int i = 1; i < grid.length; i++) {
            //初始化最左一列
            grid[i][0] += grid[i - 1][0];

            for (int j = 1; j < grid[0].length; j++) {
                if (i == 1) {
                    //初始化最上一列
                    grid[0][j] += grid[0][j - 1];
                }

                //看哪个比较小
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     * 示例 1：
     * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
     * 输出：7
     * 解释：因为路径 1→3→1→1→1 的总和最小。
     * <p>
     * 示例 2：
     * 输入：grid = [[1,2,3],[4,5,6]]
     * 输出：12
     */
    @Test
    public void try64() {
        System.out.println("7->" + this.minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}));
        System.out.println("12->" + this.minPathSum(new int[][]{{1, 2, 3}, {4, 5, 6}}));
    }

    /**
     * 72. 编辑距离
     * <p>
     * 给你两个单词word1 和word2，请你计算出将word1转换成word2 所使用的最少操作数。
     * 你可以对一个单词进行如下三种操作：
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     */
    public int minDistance(String word1, String word2) {
        int bLength = word2.length();
        int aLength = word1.length();
        //一个字符串为空 即另一个字符串的长度
        if (word1 == null || aLength == 0) {
            return word2 == null ? 0 : bLength;
        }
        if (word2 == null || bLength == 0) {
            return aLength;
        }
        //当两个字符串都为空时不需要操作
        int[][] dp = new int[aLength + 1][bLength + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= aLength; i++) {
            //初始化其中一个字符串为空的情况
            dp[i][0] = i;

            for (int j = 1; j <= bLength; j++) {
                if (i == 1) {
                    //初始化其中一个字符串为空的情况
                    dp[0][j] = j;
                }

                //如果相等  不需要操作
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }
                //不相等 可以有三种操作
                //1-插入一个字符  即word1上一个字符步数+1  dp[i-1][j] + 1
                //2-删除一个字符  即word2上一个字符步数+1  dp[i][j-1] + 1
                //3-替换一个字符  即word1与word2上一个字符步数+1 dp[i-1][j-1] +1
                dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]) + 1;
            }
        }

        return dp[aLength][bLength];
    }

    /**
     * 示例1：
     * 输入：word1 = "horse", word2 = "ros"
     * 输出：3
     * 解释：
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     * <p>
     * 示例2：
     * 输入：word1 = "intention", word2 = "execution"
     * 输出：5
     * 解释：
     * intention -> inention (删除 't')
     * inention -> enention (将 'i' 替换为 'e')
     * enention -> exention (将 'n' 替换为 'x')
     * exention -> exection (将 'n' 替换为 'c')
     * exection -> execution (插入 'u')
     */
    @Test
    public void try72() {
        System.out.println("3->" + this.minDistance("horse", "ros"));
        System.out.println("5->" + this.minDistance("intention", "execution"));
    }
}
