import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * @Author LiuRunYuan
 * @CreateDate 2020/5/26
 */
public class everyday {

    //287. 寻找重复数
    //给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
    class Solution {
        public int findDuplicate(int[] nums) {
            Set<Integer> numsSet = new HashSet<Integer>();
            for (int temp : nums) {
                if (numsSet.contains(temp)) {
                    return temp;
                }
                numsSet.add(temp);
            }
            return -1;
        }
    }

    @Test
    public void tryOne() {
        int first[] = {1, 3, 4, 2, 2};
        int second[] = {3, 1, 3, 4, 2};
        Solution solution = new Solution();
        System.out.println(solution.findDuplicate(first));
        System.out.println(solution.findDuplicate(second));
    }


    /**
     * 牌组中的每张卡牌都对应有一个唯一的整数。你可以按你想要的顺序对这套卡片进行排序。
     * 最初，这些卡牌在牌组里是正面朝下的（即，未显示状态）。
     * 现在，重复执行以下步骤，直到显示所有卡牌为止：
     * 从牌组顶部抽一张牌，显示它，然后将其从牌组中移出。
     * 如果牌组中仍有牌，则将下一张处于牌组顶部的牌放在牌组的底部。
     * 如果仍有未显示的牌，那么返回步骤 1。否则，停止行动。
     * 返回能以递增顺序显示卡牌的牌组顺序。
     * 答案中的第一张牌被认为处于牌堆顶部
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int result[] = new int[deck.length];
        LinkedList<Integer> tempIndex = new LinkedList();
        for (int i = 0; i < deck.length; i++) {
            tempIndex.add(i);
        }
        for (int i = 0; i < deck.length; i++) {
            result[tempIndex.pollFirst()] = deck[i];
            if (!tempIndex.isEmpty()) {
                tempIndex.add(tempIndex.pollFirst());
            }
        }
        return result;
    }

    @Test
    public void deckTest() {
        int temp[] = {17, 13, 11, 2, 3, 5, 7};
        int[] ints = deckRevealedIncreasing(temp);
        System.out.println(ints);
    }


    /**
     * 974. 和可被 K 整除的子数组
     * <p>
     * 给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。
     * <p>
     * 输入：A = [4,5,0,-2,-3,1], K = 5
     * 输出：7
     * 解释：
     * 有 7 个子数组满足其元素之和可被 K = 5 整除：
     * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
     */
    public int subarraysDivByK(int[] A, int K) {
        int sum = 0;
        for (int i = 0; i < A.length; i++) {
            int temp = A[i];
            if (temp % K == 0) {
                sum++;
            }
            int flag = temp;
            for (int j = i + 1; j < A.length; j++) {
                flag += A[j];
                if (flag % K == 0) {
                    sum++;
                }

            }
        }
        return sum;
    }

    @Test
    public void try974() {
        int A[] = {4, 5, 0, -2, -3, 1};
        int K = 5;
        int i = subarraysDivByK(A, K);
        System.out.println(i);
    }

    /**
     * 394. 字符串解码
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * <p>
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * <p>
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * <p>
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     * <p>
     * s = "3[a]2[bc]", 返回 "aaabcbc".
     * s = "3[a2[c]]", 返回 "accaccacc".
     * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
     */
    public String decodeString(String s) {
        //没有 [ 不需要操作
        if (s.indexOf("[") > 0) {
            return s;
        }

        //用于存储返回数据
        String resultStr = s;
        //用于存储 [ 括号位置
        LinkedList<Integer> flagIndex = new LinkedList<Integer>();
        //用于存储出现的数字
        LinkedList<Integer> num = new LinkedList<Integer>();
        //用于存储每个 [ 前的数字
        StringBuilder numStr = new StringBuilder();
        //便于循环  也可以定义个flag来记录指针位置
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '[') {
                //如果是 [ 则记录位置  记录重复数值并清空
                flagIndex.add(i);
                num.add(Integer.valueOf(numStr.toString()));
                numStr.setLength(0);
            } else if (chars[i] == ']') {
                //如果是 ] 则获取最后录入的 [ 位置
                Integer index = flagIndex.pollLast();
                if (flagIndex.size() <= 0) {
                    //获取需要重复的内容
                    String tempStr = s.substring(index + 1, i);
                    StringBuilder sb = new StringBuilder();
                    //获取重复次数
                    Integer integer = num.pollFirst();
                    for (int j = 0; j < integer; j++) {
                        sb.append(tempStr);
                    }
                    //替换内容
                    String target = integer + "[" + tempStr + "]";
                    resultStr = s.replace(target, sb.toString());
                    if (resultStr.indexOf("[") >= 0) {
                        //如果还有 [ 则再来一次
                        resultStr = decodeString(resultStr);
                    }
                    return resultStr;
                }
            } else if (Character.isDigit(chars[i])) {
                numStr.append(chars[i]);
            }
        }
        return s;
    }

    @Test
    public void try394() {
        String s1 = "3[a]2[bc]";
        String s2 = "3[a2[c]]";
        String s3 = "2[abc]3[cd]ef";
        String s4 = "100[leetcode]";

//        System.out.println(decodeString(s1));
        System.out.println(decodeString(s2));
//        System.out.println(decodeString(s3));
//        System.out.println(decodeString(s4));
    }

    /**
     * 1408. 数组中的字符串匹配
     * 给你一个字符串数组 words ，数组中的每个字符串都可以看作是一个单词。请你按 任意 顺序返回 words 中是其他单词的子字符串的所有单词。
     * <p>
     * 如果你可以删除 words[j] 最左侧和/或最右侧的若干字符得到 word[i] ，那么字符串 words[i] 就是 words[j] 的一个子字符串。
     * <p>
     * 输入：words = ["mass","as","hero","superhero"]
     * 输出：["as","hero"]
     * 解释："as" 是 "mass" 的子字符串，"hero" 是 "superhero" 的子字符串。
     * ["hero","as"] 也是有效的答案。
     * <p>
     * 输入：words = ["leetcode","et","code"]
     * 输出：["et","code"]
     * 解释："et" 和 "code" 都是 "leetcode" 的子字符串。
     * <p>
     * 输入：words = ["blue","green","bu"]
     * 输出：[]
     */
    public List<String> stringMatching(String[] words) {
        StringBuilder allStr = new StringBuilder();
        List<String> resultList = new ArrayList<String>();
        for (String temp : words) {
            allStr.append(temp + "\\|");
        }

        for (String temp : words) {
            String s = allStr.toString().replaceFirst(temp, "");
            if (s.indexOf(temp) >= 0) {
                resultList.add(temp);
            }
        }
        return resultList;
    }

    @Test
    public void try1408() {
        String temp1[] = new String[]{"mass", "as", "hero", "superhero"};
        String temp2[] = new String[]{"leetcode", "et", "code"};
        String temp3[] = new String[]{"blue", "green", "bu"};
        String temp4[] = new String[]{"uexk", "aeuexkf", "wgxih", "yuexk", "gxea", "yuexkm", "ypmfx", "jjuexkmb", "wqpri", "aeuexkfpo", "kqtnz", "pkqtnza", "nrbb", "hmypmfx", "krqs", "jjuexkmbyt", "zvru", "ypmfxj"};
//        System.out.println(stringMatching(temp1));
//        System.out.println(stringMatching(temp2));
//        System.out.println(stringMatching(temp3));
        System.out.println(stringMatching(temp4));
    }

    /**
     * 198. 打家劫舍
     * <p>
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * <p>
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * <p>
     * 输入: [1,2,3,1]
     * 输出: 4
     * 解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     * <p>
     * 输入: [2,7,9,3,1]
     * 输出: 12
     * 解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     */
    public int rob(int[] nums) {
        int max = 0;
        int lastMax = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = max;
            max = lastMax + nums[i];
            lastMax = Math.max(lastMax, temp);
            System.out.println();
        }
        return Math.max(max, lastMax);
    }

    @Test
    public void try198() {
        int temp1[] = {1, 2, 3, 1};
        int temp2[] = {2, 7, 9, 3, 1};
        int temp3[] = {2, 1, 1, 2};
//        System.out.println(rob(temp1));
        System.out.println(rob(temp2));
        System.out.println(rob(temp3));
    }

    /**
     * 238. 除自身以外数组的乘积
     * <p>
     * 给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
     * <p>
     * 输入: [1,2,3,4]
     * 输出: [24,12,8,6]
     * <p>
     * 提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。
     * <p>
     * 说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
     * <p>
     * 进阶：
     * 你可以在常数空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）
     */
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        int temp = 1;
        for (int i = 0; i < nums.length; i++) {
            result[i] = temp;
            temp *= nums[i];
        }
        temp = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] *= temp;
            temp *= nums[i];
        }
        return result;
    }

    @Test
    public void try238() {
        int[] temp1 = new int[]{1, 2, 3, 4};
        int[] x = productExceptSelf(temp1);
        System.out.println(x);
    }

    /**
     * 面试题29. 顺时针打印矩阵
     * <p>
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     * 0 <= matrix.length <= 100
     * 0 <= matrix[i].length <= 100
     * <p>
     * [1,2,3]
     * [4,5,6]
     * [7,8,9]
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * <p>
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }

        int rowIndex = 0;
        int columIndex = 0;
        int flag = 1;
        int topFlag = 0;
        int rightFlag = matrix[0].length - 1;
        int floorFlag = matrix.length - 1;
        int leftFlag = 0;

        int result[] = new int[matrix.length * matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[rowIndex][columIndex];
            if (flag == 1) {
                //上方
                if (columIndex == rightFlag) {
                    //到尽头
                    rowIndex++;
                    topFlag++;
                    flag = 2;
                } else {
                    columIndex++;
                }
            } else if (flag == 2) {
                //右侧
                if (rowIndex == floorFlag) {
                    //到尽头
                    columIndex--;
                    rightFlag--;
                    flag = 3;
                } else {
                    rowIndex++;
                }
            } else if (flag == 3) {
                //下方
                if (columIndex == leftFlag) {
                    //到尽头
                    rowIndex--;
                    floorFlag--;
                    flag = 4;
                } else {
                    columIndex--;
                }
            } else if (flag == 4) {
                //左侧
                if (rowIndex == topFlag) {
                    //到尽头
                    columIndex++;
                    leftFlag++;
                    flag = 1;
                } else {
                    rowIndex--;
                }
            }
        }
        return result;
    }

    @Test
    public void tryMS29() {
        int[] ints = spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        int[] ints2 = spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}});
        System.out.println(ints);
        System.out.println(ints2);
    }

    /**
     * 990. 等式方程的可满足性
     * <p>
     * 给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，并采用两种不同的形式之一："a==b" 或 "a!=b"。
     * 在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
     * <p>
     * 只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回 true，否则返回 false
     * <p>
     * 输入：["a==b","b!=a"]
     * 输出：false
     * <p>
     * 输出：["b==a","a==b"]
     * 输入：true
     * <p>
     * 输入：["a==b","b==c","a==c"]
     * 输出：true
     * <p>
     * 输入：["a==b","b!=c","c==a"]
     * 输出：false
     * <p>
     * 输入：["c==c","b==d","x!=z"]
     * 输出：true
     */
    public boolean equationsPossible(String[] equations) {
        Map<Character, Integer> keyMap = new HashMap<Character, Integer>();
        String unequal[] = new String[equations.length];
        int unequireFlag = 0, keyFlag = 0;
        for (String temp : equations) {
            char first = temp.charAt(0);
            char second = temp.charAt(3);
            if ('!' == temp.charAt(1)) {
                unequal[unequireFlag] = temp;
                unequireFlag++;
            } else {
                if (!keyMap.containsKey(first) && !keyMap.containsKey(second)) {
                    keyMap.put(first, keyFlag);
                    keyMap.put(second, keyFlag++);
                } else if (keyMap.containsKey(first)) {
                    if (!keyMap.containsKey(second)) {
                        keyMap.put(second, keyMap.get(first));
                    } else if (keyMap.get(first) != keyMap.get(second)) {
                        changeKey(keyMap, keyMap.get(first), keyMap.get(second));
                    }
                } else if (keyMap.containsKey(second)) {
                    if (!keyMap.containsKey(first)) {
                        keyMap.put(first, keyMap.get(second));
                    } else if (keyMap.get(first) != keyMap.get(second)) {
                        changeKey(keyMap, keyMap.get(second), keyMap.get(first));
                    }
                } else {
                    keyMap.put(first, keyFlag);
                    keyMap.put(second, keyFlag++);
                }
            }
        }

        for (int i = 0; i < unequireFlag; i++) {
            char first = unequal[i].charAt(0);
            char second = unequal[i].charAt(3);
            if (keyMap.get(first) != keyMap.get(second)) {
                return false;
            }
        }
        return true;
    }

    public void changeKey(Map<Character, Integer> keyMap, int oldFlag, int newFlag) {
        for (Map.Entry<Character, Integer> temp : keyMap.entrySet()) {
            if (temp.getValue().equals(oldFlag)) {
                keyMap.put(temp.getKey(), newFlag);
            }
        }
    }

    @Test
    public void try990() {
//        System.out.println(equationsPossible(new String[]{"a==b", "b!=a"}));
//        System.out.println(equationsPossible(new String[]{"b==a", "a==b"}));
//        System.out.println(equationsPossible(new String[]{"a==b", "b==c", "a==c"}));
//        System.out.println(equationsPossible(new String[]{"a==b", "b!=c", "c==a"}));
//        System.out.println(equationsPossible(new String[]{"c==c", "b==d", "x!=z"}));
        //["a==b","e==c","b==c","a!=e"]
        System.out.println(equationsPossible(new String[]{"a==b", "e==c", "b==c", "a!=e"}));
    }

    /**
     * 15. 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new LinkedList<List<Integer>>();
        Set<String> checkSet = new HashSet<String>();
        for (int i = 0; i < nums.length; i++) {
            int first = nums[i];
            for (int j = i + 1; j < nums.length - 1; j++) {
                int second = nums[j];
                for (int k = j + 1; k < nums.length; k++) {
                    int third = nums[k];
                    String allNum = "" + first + second + third;
                    int max = Math.max(first, Math.max(second, third));
                    int min = Math.min(first, Math.min(second, third));
                    int middle = Integer.parseInt(allNum.replaceFirst(min + "", "").replaceFirst(max + "", ""));
                    String checkStr = "" + min + middle + max;
                    if (first + second + third == 0 && !checkSet.contains(checkStr)) {
                        List<Integer> list = new LinkedList<Integer>();
                        list.add(min);
                        list.add(middle);
                        list.add(max);
                        resultList.add(list);
                        checkSet.add(checkStr);
                    }
                }
            }
        }
        return resultList;
    }

    @Test
    public void try15() {
        List<List<Integer>> lists = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        System.out.println();
    }

    /**
     * 1014. 最佳观光组合
     * 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。
     * <p>
     * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离。
     * <p>
     * 返回一对观光景点能取得的最高分。
     * <p>
     * 输入：[8,1,5,2,6]
     * 输出：11
     * 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11
     */
    public int maxScoreSightseeingPair(int[] A) {
        int aMax = 0;
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            result = Math.max(result, aMax + A[i] - i);
            aMax = Math.max(aMax, A[i] + i);
        }
        return result;
    }

    @Test
    public void try1014() {
//        System.out.println(maxScoreSightseeingPair(new int[]{8, 1, 5, 2, 6}));
        System.out.println(maxScoreSightseeingPair(new int[]{1, 2}));
    }

    /**
     * 面试题 16.18. 模式匹配
     * <p>
     * 你有两个字符串，即pattern和value。 pattern字符串由字母"a"和"b"组成，用于描述字符串中的模式。
     * 例如，字符串"catcatgocatgo"匹配模式"aabab"（其中"cat"是"a"，"go"是"b"），
     * 该字符串也匹配像"a"、"ab"和"b"这样的模式。但需注意"a"和"b"不能同时表示相同的字符串。
     * <p>
     * 编写一个方法判断value字符串是否匹配pattern字符串。
     * <p>
     * 输入： pattern = "abba", value = "dogcatcatdog"
     * 输出： true
     * <p>
     * 输入： pattern = "abba", value = "dogcatcatfish"
     * 输出： false
     * <p>
     * 输入： pattern = "aaaa", value = "dogcatcatdog"
     * 输出： false
     * <p>
     * 输入： pattern = "abba", value = "dogdogdogdog"
     * 输出： true
     */
    public boolean patternMatching(String pattern, String value) {
        if (pattern.length() == 0) {
            return value.length() == 0;
        } else if (pattern.length() == 1) {
            return true;
        } else {
            int sum_a = 0, sum_b = 0;
            char firstA = 'a' == pattern.charAt(0) ? 'a' : 'b';
            StringBuilder tempPattern = new StringBuilder();
            for (int i = 0; i < pattern.length(); i++) {
                if (firstA == pattern.charAt(i)) {
                    sum_a++;
                    tempPattern.append('a');
                } else {
                    sum_b++;
                    tempPattern.append('b');
                }
            }
            pattern = tempPattern.toString();

            //假设a所代表的字符串长度
            for (int size_a = 0; sum_a * size_a <= value.length(); size_a++) {
                //除去a后剩余长度
                String temp_a = value.substring(0, size_a);
                int residueNum = value.length() - sum_a * size_a;
                //剩余长度不能平均分配  不满足
                if (sum_b != 0 && residueNum % sum_b != 0) {
                    continue;
                }
                int size_b = sum_b != 0 ? residueNum / sum_b : 0;
                String temp_b = value.substring(0, size_b);
                if (temp_a.equals(temp_b)) {
                    continue;
                }
                boolean flag = false;
                String tempStr = value;
                for (int i = 0; i < pattern.length(); i++) {
                    int tempSize = 'a' == pattern.charAt(i) ? size_a : size_b;
                    String temp = tempStr.substring(0, tempSize);
                    if (!temp.equals('a' == pattern.charAt(i) ? temp_a : temp_b)) {
                        flag = true;
                        break;
                    }
                    tempStr = tempStr.substring(tempSize);
                }
                if (flag || tempStr.length() > 0) continue;
                return true;
            }
        }
        return false;
    }

    @Test
    public void tryMs1618() {
//        System.out.println(patternMatching("abba", "dogcatcatdog"));
//        System.out.println(patternMatching("abba", "dogcatcatfish"));
//        System.out.println(patternMatching("aaaa", "dogcatcatdog"));
//        System.out.println(patternMatching("abba", "dogdogdogdog"));
//        System.out.println(patternMatching("bbba", "xxxxxxy"));
//        System.out.println(patternMatching("", ""));
        System.out.println(patternMatching("baabab", "eimmieimmieeimmiee"));
    }

    /**
     * 120. 三角形最小路径和
     * <p>
     * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
     * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
     * <p>
     * [
     * [2],
     * [3,4],
     * [6,5,7],
     * [4,1,8,3]
     * ]
     * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        for (int i = triangle.size() - 2; i >= 0; i--) {
            List<Integer> temp = triangle.get(i);
            for (int j = 0; j < temp.size(); j++) {
                int flag = temp.get(j);
                int min = Math.min(flag + triangle.get(i + 1).get(j), flag + triangle.get(i + 1).get(j + 1));
                temp.set(j, min);
            }
        }
        return triangle.get(0).get(0);
    }

    public int minimumTotal1(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] f = new int[n][n];
        f[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < n; ++i) {
            f[i][0] = f[i - 1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; ++j) {
                f[i][j] = Math.min(f[i - 1][j - 1], f[i - 1][j]) + triangle.get(i).get(j);
            }
            f[i][i] = f[i - 1][i - 1] + triangle.get(i).get(i);
        }
        int minTotal = f[n - 1][0];
        for (int i = 1; i < n; ++i) {
            minTotal = Math.min(minTotal, f[n - 1][i]);
        }
        return minTotal;
    }

    @Test
    public void try120() {
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        list.add(new LinkedList<Integer>() {{
            add(2);
        }});
        list.add(new LinkedList<Integer>() {{
            add(3);
            add(4);
        }});
        list.add(new LinkedList<Integer>() {{
            add(6);
            add(5);
            add(7);
        }});
        list.add(new LinkedList<Integer>() {{
            add(4);
            add(1);
            add(8);
            add(3);
        }});
        System.out.println(minimumTotal(list));

    }
}
