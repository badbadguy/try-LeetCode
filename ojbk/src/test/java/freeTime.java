import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author LiuRunYuan
 * @CreateDate 2020/6/4
 */
public class freeTime {

    /**
     * 1431. 拥有最多糖果的孩子
     * <p>
     * 给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。
     * <p>
     * 对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。
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
        List<Boolean> result = new LinkedList<Boolean>();
        for (int i = 0; i < candies.length; i++) {
            result.add(candies[i] + extraCandies >= max);
        }
        return result;
    }

    @Test
    public void try1431() {
        int temp1[] = new int[]{2, 3, 5, 1, 3};
        int temp2[] = new int[]{4, 2, 1, 1, 2};
        int temp3[] = new int[]{12, 1, 12};

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
        StringBuilder sb = new StringBuilder(s.substring(n));
        sb.append(s.substring(0, n));
        return sb.toString();
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
     *  给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。 S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
     * <p>
     * J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
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
        String temp1 = "1234567890123456";
        String temp2 = "123456789012345";
        String temp3 = "12345678901234";
        System.out.println(temp1.length() != 15);
        System.out.println(temp2.length() != 15);
        System.out.println(temp3.length() != 15);
    }
}
