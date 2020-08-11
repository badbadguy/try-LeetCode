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

    /**
     * 95. 不同的二叉搜索树 II
     * <p>
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。（0 <= n <= 8）
     * <p>
     * 输入：3
     * 输出：
     * [
     *   [1,null,3,2],
     *   [3,2,null,1],
     *   [3,1,null,null,2],
     *   [2,1,3],
     *   [1,null,2,null,3]
     * ]
     * 解释：
     * 以上的输出对应以下 5 种不同结构的二叉搜索树：
     * <p>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     */
    public List<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> resultList = new LinkedList<TreeNode>();
        if (start > end) {
            resultList.add(null);
            return resultList;
        }

        //遍历所有可能的根节点
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftList = generateTrees(start, i - 1);
            List<TreeNode> rightList = generateTrees(i + 1, end);

            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode oneTree = new TreeNode(i);
                    oneTree.left = left;
                    oneTree.right = right;
                    resultList.add(oneTree);
                }
            }
        }

        return resultList;
    }

    public class TreeNode {
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

    @Test
    public void try95() {
        List<TreeNode> treeNodes = generateTrees(3);
        System.out.println();
    }

    /**
     * 1025. 除数博弈
     * <p>
     * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
     * <p>
     * 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作：
     * <p>
     * 选出任一 x，满足 0 < x < N 且 N % x == 0 。
     * 用 N - x 替换黑板上的数字 N 。
     * 如果玩家无法执行这些操作，就会输掉游戏。
     * <p>
     * 只有在爱丽丝在游戏中取得胜利时才返回 True，否则返回 false。假设两个玩家都以最佳状态参与游戏。
     * <p>
     * 输入：2
     * 输出：true
     * 解释：爱丽丝选择 1，鲍勃无法进行操作。
     * 输入：3
     * 输出：false
     * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
     */
    public boolean divisorGame(int N) {
        return N % 2 == 0;
        /*int index = 0;
        while (true) {
            index++;
            int x = selectOne(N);
            if (x == -1) {
                break;
            }
            N -= x;
        }
        return index % 2 == 0;*/
    }

    private int selectOne(int N) {
        for (int x = 1; x < N; x++) {
            if (N % x == 0) {
                return x;
            }
        }
        return -1;
    }

    @Test
    public void try1025() {
        System.out.println(divisorGame(2));
        System.out.println(divisorGame(3));
    }

    /**
     * 104. 二叉树的最大深度
     * <p>
     * 给定一个二叉树，找出其最大深度。
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 给定二叉树 [3,9,20,null,null,15,7]
     * 返回它的最大深度 3
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     */
    public int maxDepth(TreeNode root) {
        return root == null ? 0 : Math.max(this.maxDepth(root.left), this.maxDepth(root.right)) + 1;
//        if (root == null) return 0;
//        return maxDepth(root, 0, 1);
    }

    private int maxDepth(TreeNode root, int index, int max) {
        index++;
        max = Math.max(index, max);
        if (root.right != null) {
            max = maxDepth(root.right, index, max);
        }
        if (root.left != null) {
            max = maxDepth(root.left, index, max);
        }
        return max;
    }

    @Test
    public void try104() {
        TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(9);
        treeNode.right = new TreeNode(20);
        TreeNode temp = treeNode.left;
        temp.left = new TreeNode(15);
        temp.right = new TreeNode(7);

        System.out.println(maxDepth(treeNode));
    }

    /**
     * 343. 整数拆分
     * 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。
     * <p>
     * 示例 1:
     * 输入: 2
     * 输出: 1
     * 解释: 2 = 1 + 1, 1 × 1 = 1。
     * <p>
     * 示例 2:
     * 输入: 10
     * 输出: 36
     * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
     */
    public int integerBreak(int n) {
        /*if (n - 3 < 2) {
            return (n / 2) * (n-n/2);
        }

        StringBuilder tempStr = new StringBuilder();
        while (n - 3 >= 2) {
            tempStr.append(3);
            tempStr.append(",");
            n -= 3;
        }
        tempStr.append(n);
        String[] split = tempStr.toString().split(",");
        int result = Integer.parseInt(split[0]);
        for (int i = 1; i < split.length; i++) {
            result = result * Integer.parseInt(split[i]);
        }
        return result;*/
        //网友牛逼plus
        long[] longs = new long[]{0, 0, 1, 2, 4, 6, 9, 12, 18, 27, 36, 54, 81, 108, 162, 243, 324, 486, 729, 972, 1458, 2187, 2916, 4374, 6561, 8748, 13122, 19683, 26244, 39366, 59049, 78732, 118098, 177147, 236196, 354294, 531441, 708588, 1062882, 1594323, 2125764, 3188646, 4782969, 6377292, 9565938, 14348907, 19131876, 28697814, 43046721, 57395628, 86093442, 129140163, 172186884, 258280326, 387420489, 516560652, 774840978, 1162261467, 1549681956, 2324522934L};
        return (int) longs[n];
    }

    @Test
    public void try343() {
        System.out.println(integerBreak(10));
    }

    /**
     * 面试题 08.03. 魔术索引
     * 魔术索引。 在数组A[0...n-1]中，有所谓的魔术索引，满足条件A[i] = i。给定一个有序整数数组，编写一种方法找出魔术索引，
     * 若有的话，在数组A中找出一个魔术索引，
     * 如果没有，则返回-1。若有多个魔术索引，返回索引值最小的一个。
     * <p>
     * 示例1:
     * 输入：nums = [0, 2, 3, 4, 5]
     * 输出：0
     * 说明: 0下标的元素为0
     * <p>
     * 示例2:
     * 输入：nums = [1, 1, 1]
     * 输出：1
     */
    public int findMagicIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i) {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void ms0803() {
        System.out.println(findMagicIndex(new int[]{0, 2, 3, 4, 5}));
        System.out.println(findMagicIndex(new int[]{1, 1, 1}));
    }

    /**
     * 415. 字符串相加
     * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
     * <p>
     * num1 和num2 的长度都小于 5100.
     * num1 和num2 都只包含数字 0-9.
     * num1 和num2 都不包含任何前导零。
     * 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
     */
    public String addStrings(String num1, String num2) {
        int num1Size = num1.length();
        int num2Size = num2.length();
        int maxSize = Math.max(num1Size, num2Size);

        StringBuilder resultStr = new StringBuilder();

        boolean carry = false;
        for (int i = 0; i < maxSize; i++) {
            char char1 = num1Size - i - 1 >= 0 ? num1.charAt(num1Size - i - 1) : '0';
            char char2 = num2Size - i - 1 >= 0 ? num2.charAt(num2Size - i - 1) : '0';
            int addNum = char1 + char2 - 48 * 2;
            if (carry) {
                addNum++;
                carry = false;
            }
            if (addNum >= 10) {
                addNum -= 10;
                carry = true;
            }
            resultStr.insert(0, addNum);
        }
        if (carry) {
            resultStr.insert(0, 1);
        }
        return resultStr.toString();
    }

    @Test
    public void try415() {
        System.out.println(addStrings("1", "29"));
    }

    /**
     * 336. 回文对
     * 给定一组唯一的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
     * <p>
     * 示例 1:
     * <p>
     * 输入: ["abcd","dcba","lls","s","sssll"]
     * 输出: [[0,1],[1,0],[3,2],[2,4]]
     * 解释: 可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
     * 示例 2:
     * <p>
     * 输入: ["bat","tab","cat"]
     * 输出: [[0,1],[1,0]]
     * 解释: 可拼接成的回文串为 ["battab","tabbat"]
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> resultList = new LinkedList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                //如果是当前下标，直接跳过
                if (i == j) {
                    continue;
                }
                //校验是否回文
                String checkStr = words[i] + words[j];

                boolean skip = false;
                for (int k = 0, l = checkStr.length() - 1; k < l; k++, l--) {
                    if (checkStr.charAt(k) != checkStr.charAt(l)) {
                        skip = true;
                        break;
                    }
                }
                if (skip) {
                    continue;
                }

                LinkedList<Integer> temp = new LinkedList<>();
                temp.add(i);
                temp.add(j);
                resultList.add(temp);
            }
        }
        return resultList;
    }

    @Test
    public void try336() {
        List<List<Integer>> lists1 = palindromePairs(new String[]{"abcd", "dcba", "lls", "s", "sssll"});
        List<List<Integer>> lists = palindromePairs(new String[]{"bat", "tab", "cat"});
        List<List<Integer>> lists2 = palindromePairs(new String[]{"aba", ""});
        System.out.println();
    }


    public class TreeNode100 {
        int val;
        TreeNode100 left;
        TreeNode100 right;

        TreeNode100() {
        }

        TreeNode100(int val) {
            this.val = val;
        }

        TreeNode100(int val, TreeNode100 left, TreeNode100 right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isSameTree(TreeNode100 p, TreeNode100 q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.val != q.val) {
            return false;
        } else {
            return isSameTree(p.right, q.right) && isSameTree(p.left, q.left);
        }
    }

    @Test
    public void try100() {
        TreeNode100 temp1 = new TreeNode100(1, null, new TreeNode100(3));
        TreeNode100 temp2 = new TreeNode100(1, new TreeNode100(3), null);
        System.out.println(isSameTree(temp1, temp2));
    }

    /**
     * 696. 计数二进制子串
     * <p>
     * 给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
     * <p>
     * 重复出现的子串要计算它们出现的次数。
     * <p>
     * 示例 1 :
     * <p>
     * 输入: "00110011"
     * 输出: 6
     * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
     * <p>
     * 请注意，一些重复出现的子串要计算它们出现的次数。
     * <p>
     * 另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。
     * 示例 2 :
     * <p>
     * 输入: "10101"
     * 输出: 4
     * 解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。
     * 注意：
     * <p>
     * s.length 在1到50,000之间。
     * s 只包含“0”或“1”字符。
     */
    public int countBinarySubstrings(String s) {
        char flag = s.charAt(0);
        int[] temp = new int[s.length()];
        int tempIndex = 0;
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            char that = s.charAt(i);
            if (that == flag) {
                temp[tempIndex]++;
            } else {
                flag = that;
                tempIndex++;
                temp[tempIndex] = 1;
            }

            if (tempIndex > 0 && temp[tempIndex - 1] >= temp[tempIndex]) {
                result++;
            }
        }
        return result;
    }

    @Test
    public void try696() {
        System.out.println(countBinarySubstrings("00110011"));
        System.out.println(countBinarySubstrings("10101"));
    }

    /**
     * 93. 复原IP地址
     * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
     * <p>
     * 有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
     * <p>
     * 示例:
     * 输入: "25525511135"
     * 输出: ["255.255.11.135", "255.255.111.35"]
     */


    public List<String> restoreIpAddresses(String s) {
        List<String> resultList = new LinkedList<>();
        int[] segments = new int[4];

        dfs93(s, 0, 0, resultList, segments);

        return resultList;
    }

    public void dfs93(String str, int index, int start, List<String> list, int[] segments) {

        if (index == 4) {
            if(start == str.length()) {
                StringBuffer ipAddr = new StringBuffer();
                for (int i = 0; i <= 3; i++) {
                    ipAddr.append(segments[i]);
                    ipAddr.append(".");
                }
                ipAddr.deleteCharAt(ipAddr.length()-1);
                list.add(ipAddr.toString());
            }
            return;
        }

        if (index != 0) {
            int lessNum = str.length() - start;
            float eachNum = (float) lessNum / (4 - index);
            if (eachNum > 3F) {
                return;
            }
        }

        if (start == str.length()) {
            return;
        }

        if (str.charAt(start) == '0') {
            segments[index] = 0;
            dfs93(str, index + 1, start + 1, list, segments);
        }

        int temp = 0;
        for (int i = start; i < str.length(); ++i) {
            temp = temp * 10 + (str.charAt(i) - '0');
            if (temp > 0 && temp <= 255) {
                segments[index] = temp;
                dfs93(str, index + 1, i + 1, list, segments);
            } else {
                break;
            }
        }
    }

    @Test
    public void try93() {
        System.out.println(restoreIpAddresses("25525511135"));
    }

    /**
     * 130. 被围绕的区域
     * <p>
     * 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
     * <p>
     * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
     * <p>
     * 示例:
     * X X X X
     * X O O X
     * X X O X
     * X O X X
     * <p>
     * 运行你的函数后，矩阵变为：
     * X X X X
     * X X X X
     * X X X X
     * X O X X
     * <p>
     * 解释:
     * 被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
     * 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
     * 如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
     */
    public void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }

        for (int i = 0; i < board[0].length; i++) {
            dfs130(board, i, 0);
            dfs130(board, i, board.length - 1);
        }
        for (int i = 1; i < board.length - 1; i++) {
            dfs130(board, 0, i);
            dfs130(board, board[0].length - 1, i);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'x') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs130(char[][] board, int x, int y) {
        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length || board[y][x] != 'O') {
            return;
        }
        board[y][x] = 'x';
        dfs130(board, x - 1, y);
        dfs130(board, x + 1, y);
        dfs130(board, x, y - 1);
        dfs130(board, x, y + 1);
    }

    @Test
    public void try130() {
        char[][] temp = {
                {'X', 'O', 'X', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'X'}
        };
        solve(temp);

        //[["X","O","X","O","X","O"],
        // ["O","X","O","X","O","X"],
        // ["X","O","X","O","X","O"],
        // ["O","X","O","X","O","X"]]
    }
}