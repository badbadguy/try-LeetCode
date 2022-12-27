import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;

/**
 * @Author LiuRunYuan
 * @CreateDate 2020/5/26
 */
public class everyday {

    //287. 寻找重复数
    //给定一个包含n + 1 个整数的数组nums，其数字都在 1 到 n之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
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
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像3a或2[4]的输入。
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
     * 如果你可以删除 words[j]最左侧和/或最右侧的若干字符得到 word[i] ，那么字符串 words[i] 就是 words[j] 的一个子字符串。
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
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * <p>
     * 输入: [2,7,9,3,1]
     * 输出: 12
     * 解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
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
     * 给你一个长度为n的整数数组nums，其中n > 1，返回输出数组output，其中 output[i]等于nums中除nums[i]之外其余各元素的乘积。
     * <p>
     * 输入: [1,2,3,4]
     * 输出: [24,12,8,6]
     * <p>
     * 提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。
     * <p>
     * 说明: 请不要使用除法，且在O(n) 时间复杂度内完成此题。
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
     * 给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，并采用两种不同的形式之一："a==b" 或"a!=b"。
     * 在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
     * <p>
     * 只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回true，否则返回 false
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
     * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
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
     * 给定正整数数组A，A[i]表示第 i 个观光景点的评分，并且两个景点i 和j之间的距离为j - i。
     * <p>
     * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i- j）：景点的评分之和减去它们两者之间的距离。
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
     * [1,null,3,2],
     * [3,2,null,1],
     * [3,1,null,null,2],
     * [2,1,3],
     * [1,null,2,null,3]
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
     * 最初，黑板上有一个数字N。在每个玩家的回合，玩家需要执行以下操作：
     * <p>
     * 选出任一x，满足0 < x < N 且N % x == 0。
     * 用 N - x替换黑板上的数字 N 。
     * 如果玩家无法执行这些操作，就会输掉游戏。
     * <p>
     * 只有在爱丽丝在游戏中取得胜利时才返回True，否则返回 false。假设两个玩家都以最佳状态参与游戏。
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
     * 示例2:
     * 输入: 10
     * 输出: 36
     * 解释: 10 = 3 + 3 + 4, 3 ×3 ×4 = 36。
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
     * 给定一组唯一的单词， 找出所有不同的索引对(i, j)，使得列表中的两个单词，words[i] + words[j]，可拼接成回文串。
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
     * 给定一个字符串s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
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
     * s.length在1到50,000之间。
     * s只包含“0”或“1”字符。
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
            if (start == str.length()) {
                StringBuffer ipAddr = new StringBuffer();
                for (int i = 0; i <= 3; i++) {
                    ipAddr.append(segments[i]);
                    ipAddr.append(".");
                }
                ipAddr.deleteCharAt(ipAddr.length() - 1);
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
     * 给定一个二维的矩阵，包含'X'和'O'（字母 O）。
     * <p>
     * 找到所有被 'X' 围绕的区域，并将这些区域里所有的'O' 用 'X' 填充。
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
     * 被围绕的区间不会存在于边界上，换句话说，任何边界上的'O'都不会被填充为'X'。
     * 任何不在边界上，或不与边界上的'O'相连的'O'最终都会被填充为'X'。
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
        char[][] temp = {{'X', 'O', 'X', 'O', 'X', 'O'}, {'O', 'X', 'O', 'X', 'O', 'X'}, {'X', 'O', 'X', 'O', 'X', 'O'}, {'O', 'X', 'O', 'X', 'O', 'X'}};
        solve(temp);

        //[["X","O","X","O","X","O"],
        // ["O","X","O","X","O","X"],
        // ["X","O","X","O","X","O"],
        // ["O","X","O","X","O","X"]]
    }

    /**
     * 494. 目标和
     * <p>
     * 给你一个整数数组 nums 和一个整数 target 。
     * 向数组中的每个整数前添加'+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
     * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     */
    private int findTargetSumWaysCount = 0;

    public int findTargetSumWays(int[] nums, int target) {
        //启动
        tryToAddOrDeleteNext(nums, target, 0, 0);
        return findTargetSumWaysCount;
    }

    private void tryToAddOrDeleteNext(int[] nums, int target, int index, int sum) {
        //不用nums.length-1因为需要把最后一位也加/减上
        if (index == nums.length) {
            //已到最后一位
            if (target == sum) {
                findTargetSumWaysCount++;
            }
            return;
        } else {
            //增加的情况
            tryToAddOrDeleteNext(nums, target, index + 1, sum + nums[index]);
            //删除的情况
            tryToAddOrDeleteNext(nums, target, index + 1, sum - nums[index]);
        }
    }

    /**
     * 示例 1：
     * 输入：nums = [1,1,1,1,1], target = 3
     * 输出：5
     * 解释：一共有 5 种方法让最终目标和为 3 。
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     * <p>
     * 示例 2：
     * 输入：nums = [1], target = 1
     * 输出：1
     */
    @Test
    public void try494() {
        System.out.println("5->" + findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println("1->" + findTargetSumWays(new int[]{1}, 1));
    }


    /**
     * 1449. 数位成本和为目标值的最大数字
     * <p>
     * 给你一个整数数组cost和一个整数target。请你返回满足如下规则可以得到的最大整数：
     * 给当前结果添加一个数位（i + 1）的成本为cost[i]（cost数组下标从 0 开始）。
     * 总成本必须恰好等于target。
     * 添加的数位中没有数字 0 。
     * 由于答案可能会很大，请你以字符串形式返回。
     * 如果按照上述要求无法得到任何整数，请你返回 "0" 。
     */
    public String largestNumber(int[] cost, int target) {

        return "0";
    }


    /**
     * 示例 1：
     * 输入：cost = [4,3,2,5,6,7,2,5,5], target = 9
     * 输出："7772"
     * 解释：添加数位 '7' 的成本为 2 ，添加数位 '2' 的成本为 3 。所以 "7772" 的代价为 2*3+ 3*1 = 9 。 "977" 也是满足要求的数字，但 "7772" 是较大的数字。
     * 数字     成本
     * 1  ->   4
     * 2  ->   3
     * 3  ->   2
     * 4  ->   5
     * 5  ->   6
     * 6  ->   7
     * 7  ->   2
     * 8  ->   5
     * 9  ->   5
     * <p>
     * 示例 2：
     * 输入：cost = [7,6,5,5,5,6,8,7,8], target = 12
     * 输出："85"
     * 解释：添加数位 '8' 的成本是 7 ，添加数位 '5' 的成本是 5 。"85" 的成本为 7 + 5 = 12 。
     * <p>
     * 示例 3：
     * 输入：cost = [2,4,6,2,4,6,4,4,4], target = 5
     * 输出："0"
     * 解释：总成本是 target 的条件下，无法生成任何整数。
     * <p>
     * 示例 4：
     * 输入：cost = [6,10,15,40,40,40,40,40,40], target = 47
     * 输出："32211"
     */
    @Test
    public void try1449() {
        System.out.println("7772->" + largestNumber(new int[]{4, 3, 2, 5, 6, 7, 2, 5, 5}, 9));
        System.out.println("85->" + largestNumber(new int[]{7, 6, 5, 5, 5, 6, 8, 7, 8}, 12));
        System.out.println("0->" + largestNumber(new int[]{2, 4, 6, 2, 4, 6, 4, 4, 4}, 5));
        System.out.println("32211->" + largestNumber(new int[]{6, 10, 15, 40, 40, 40, 40, 40, 40}, 47));
    }

    /**
     * 371. 两整数之和
     * <p>
     * 给你两个整数 a 和 b ，不使用 运算符 + 和 - ，计算并返回两整数之和。
     */
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }

    /**
     * 示例 1：
     * 输入：a = 1, b = 2
     * 输出：3
     * <p>
     * 示例 2：
     * 输入：a = 2, b = 3
     * 输出：5
     */
    @Test
    public void try371() {
        System.out.println("3->" + getSum(1, 2));
        System.out.println("5->" + getSum(2, 3));
    }

    /**
     * 437. 路径总和 III
     * 给定一个二叉树的根节点 root，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
     * <p>
     * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
     */
    public int pathSum(TreeNode root, int sum) {
        // key是前缀和, value是大小为key的前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        // 前缀和为0的一条路径
        prefixSumCount.put(0, 1);
        // 前缀和的递归回溯思路
        return recursionPathSum(root, prefixSumCount, sum, 0);
    }

    /**
     * 前缀和的递归回溯思路
     * 从当前节点反推到根节点(反推比较好理解，正向其实也只有一条)，有且仅有一条路径，因为这是一棵树
     * 如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
     * 所以前缀和对于当前路径来说是唯一的，当前记录的前缀和，在回溯结束，回到本层时去除，保证其不影响其他分支的结果
     *
     * @param node           树节点
     * @param prefixSumCount 前缀和Map
     * @param target         目标值
     * @param currSum        当前路径和
     * @return 满足题意的解
     */
    private int recursionPathSum(TreeNode node, Map<Integer, Integer> prefixSumCount, int target, int currSum) {
        // 1.递归终止条件
        if (node == null) {
            return 0;
        }
        // 2.本层要做的事情
        int res = 0;
        // 当前路径上的和
        currSum += node.val;

        //---核心代码
        // 看看root到当前节点这条路上是否存在节点前缀和加target为currSum的路径
        // 当前节点->root节点反推，有且仅有一条路径，如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
        // currSum-target相当于找路径的起点，起点的sum+target=currSum，当前点到起点的距离就是target
        res += prefixSumCount.getOrDefault(currSum - target, 0);
        // 更新路径上当前节点前缀和的个数
        prefixSumCount.put(currSum, prefixSumCount.getOrDefault(currSum, 0) + 1);
        //---核心代码

        // 3.进入下一层
        res += recursionPathSum(node.left, prefixSumCount, target, currSum);
        res += recursionPathSum(node.right, prefixSumCount, target, currSum);

        // 4.回到本层，恢复状态，去除当前节点的前缀和数量
        prefixSumCount.put(currSum, prefixSumCount.get(currSum) - 1);
        return res;
    }


    /**
     * 示例 1：
     * 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
     * 输出：3
     * 解释：和等于 8 的路径有 3 条，如图所示。
     * <p>
     * 示例 2：
     * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * 输出：3
     */
    @Test
    public void try437() {
        /*TreeNode treeNode1 = new TreeNode(10);
        treeNode1.left = new TreeNode(5);
        treeNode1.left.left = new TreeNode(3);
        treeNode1.left.left.left = new TreeNode(3);
        treeNode1.left.left.right = new TreeNode(-2);
        treeNode1.left.right = new TreeNode(2);
        treeNode1.left.right.right = new TreeNode(1);

        treeNode1.right = new TreeNode(-3);
        treeNode1.right.right = new TreeNode(11);

        System.out.println("3->" + this.pathSum(treeNode1, 8));
        this.sum = 0;


        TreeNode treeNode2 = new TreeNode(5);
        treeNode2.left = new TreeNode(4);
        treeNode2.left.left = new TreeNode(11);
        treeNode2.left.left.left = new TreeNode(7);
        treeNode2.left.left.right = new TreeNode(2);

        treeNode2.right = new TreeNode(8);
        treeNode2.right.left = new TreeNode(13);
        treeNode2.right.right = new TreeNode(4);
        treeNode2.right.right.left = new TreeNode(5);
        treeNode2.right.right.right = new TreeNode(1);

        System.out.println("3->" + this.pathSum(treeNode2, 22));
        this.sum = 0;

        //[5,4,8,11,null,13,4,7,2,null,null,5,1]
        //22
        TreeNode treeNode3 = new TreeNode(5);
        treeNode3.left = new TreeNode(4);
        treeNode3.left.left = new TreeNode(11);
        treeNode3.left.left.left = new TreeNode(7);
        treeNode3.left.left.right = new TreeNode(2);
        treeNode3.right = new TreeNode(8);
        treeNode3.right.left = new TreeNode(13);
        treeNode3.right.right = new TreeNode(4);
        treeNode3.right.right.left = new TreeNode(5);
        treeNode3.right.right.right = new TreeNode(1);
        System.out.println("3->" + this.pathSum(treeNode3, 22));
        this.sum = 0;

        //[-2,null,-3]
        //-5
        TreeNode treeNode4 = new TreeNode(-2);
        treeNode4.right = new TreeNode(-3);
        System.out.println("1->" + this.pathSum(treeNode4, -5));*/

        TreeNode treeNode5 = new TreeNode(1);
        treeNode5.left = new TreeNode(-2);
        treeNode5.left.left = new TreeNode(1);
        treeNode5.left.left.left = new TreeNode(-1);
        treeNode5.left.right = new TreeNode(3);
        treeNode5.right = new TreeNode(-3);
        treeNode5.right.left = new TreeNode(-2);
        System.out.println("1->" + this.pathSum(treeNode5, -2));
    }

    /**
     * 223. 矩形面积
     * 给你 二维 平面上两个 由直线构成的 矩形，请你计算并返回两个矩形覆盖的总面积。
     * <p>
     * 每个矩形由其 左下 顶点和 右上 顶点坐标表示：
     * <p>
     * 第一个矩形由其左下顶点 (ax1, ay1) 和右上顶点 (ax2, ay2) 定义。
     * 第二个矩形由其左下顶点 (bx1, by1) 和右上顶点 (bx2, by2) 定义。
     */
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        // 首先分别计算出两个矩形的面积
        // 长
        int aLength = ax2 - ax1;
        // 宽
        int aWidth = ay2 - ay1;
        // 面积
        int aArea = aLength * aWidth;

        int bLength = bx2 - bx1;
        int bWidth = by2 - by1;
        int bArea = bLength * bWidth;

        // 重复计算的面积
        int xArea = 0;
        if (ax2 < bx2 && ax1 > bx1 && ay2 < by2 && ay1 > by2) {
            //a包含在b内
            return bArea;
        } else if (bx2 < ax2 && bx1 > ax1 && by2 < ay2 && by1 > ay2) {
            //b包含在a内
            return aArea;
        } else if (ax2 > bx1 && by2 > ay1) {
            //左a右b 且覆盖
            xArea = (ax2 - bx1) * (by2 - ay1);
        } else if (bx2 > ax1 && ay2 > by1) {
            //左b右a  且覆盖
            xArea = (bx2 - ax1) * (ay2 - by1);
        }
        return aArea + bArea - xArea;
    }

    /**
     * 示例 1：
     * 输入：ax1 = -3, ay1 = 0, ax2 = 3, ay2 = 4, bx1 = 0, by1 = -1, bx2 = 9, by2 = 2
     * 输出：45
     * <p>
     * 示例 2：
     * 输入：ax1 = -2, ay1 = -2, ax2 = 2, ay2 = 2, bx1 = -2, by1 = -2, bx2 = 2, by2 = 2
     * 输出：16
     */
    @Test
    public void try223() {
        System.out.println("16->" + this.computeArea(0, 0, 0, 0, -1, -1, 1, 1));
    }

    /**
     * 488. 祖玛游戏
     * <p>
     * 你正在参与祖玛游戏的一个变种。
     * 在这个祖玛游戏变体中，桌面上有 一排 彩球，每个球的颜色可能是：红色 'R'、黄色 'Y'、蓝色 'B'、绿色 'G' 或白色 'W' 。你的手中也有一些彩球。
     * 你的目标是 清空 桌面上所有的球。每一回合：
     * <p>
     * 从你手上的彩球中选出 任意一颗 ，然后将其插入桌面上那一排球中：两球之间或这一排球的任一端。
     * 接着，如果有出现 三个或者三个以上 且 颜色相同 的球相连的话，就把它们移除掉。
     * 如果这种移除操作同样导致出现三个或者三个以上且颜色相同的球相连，则可以继续移除这些球，直到不再满足移除条件。
     * 如果桌面上所有球都被移除，则认为你赢得本场游戏。
     * 重复这个过程，直到你赢了游戏或者手中没有更多的球。
     * 给你一个字符串 board ，表示桌面上最开始的那排球。另给你一个字符串 hand ，表示手里的彩球。请你按上述操作步骤移除掉桌上所有球，计算并返回所需的 最少 球数。如果不能移除桌上所有的球，返回 -1 。
     */
    public int findMinStep(String board, String hand) {


        return 0;
    }

    /**
     * 示例 1：
     * 输入：board = "WRRBBW", hand = "RB"
     * 输出：-1
     * 解释：无法移除桌面上的所有球。可以得到的最好局面是：
     * - 插入一个 'R' ，使桌面变为 WRRRBBW 。WRRRBBW -> WBBW
     * - 插入一个 'B' ，使桌面变为 WBBBW 。WBBBW -> WW
     * 桌面上还剩着球，没有其他球可以插入。
     * <p>
     * 示例 2：
     * 输入：board = "WWRRBBWW", hand = "WRBRW"
     * 输出：2
     * 解释：要想清空桌面上的球，可以按下述步骤：
     * - 插入一个 'R' ，使桌面变为 WWRRRBBWW 。WWRRRBBWW -> WWBBWW
     * - 插入一个 'B' ，使桌面变为 WWBBBWW 。WWBBBWW -> WWWW -> empty
     * 只需从手中出 2 个球就可以清空桌面。
     * <p>
     * 示例 3：
     * 输入：board = "G", hand = "GGGGG"
     * 输出：2
     * 解释：要想清空桌面上的球，可以按下述步骤：
     * - 插入一个 'G' ，使桌面变为 GG 。
     * - 插入一个 'G' ，使桌面变为 GGG 。GGG -> empty
     * 只需从手中出 2 个球就可以清空桌面。
     * <p>
     * 示例 4：
     * 输入：board = "RBYYBBRRB", hand = "YRBGB"
     * 输出：3
     * 解释：要想清空桌面上的球，可以按下述步骤：
     * - 插入一个 'Y' ，使桌面变为 RBYYYBBRRB 。RBYYYBBRRB -> RBBBRRB -> RRRB -> B
     * - 插入一个 'B' ，使桌面变为 BB 。
     * - 插入一个 'B' ，使桌面变为 BBB 。BBB -> empty
     * 只需从手中出 3 个球就可以清空桌面。
     */
    @Test
    public void try488() {
        System.out.println("-1->" + findMinStep("WRRBBW", "RB"));
        System.out.println("2->" + findMinStep("WWRRBBWW", "WRBRW"));
        System.out.println("2->" + findMinStep("G", "GGGGG"));
        System.out.println("3->" + findMinStep("RBYYBBRRB", "YRBGB"));
    }

    /**
     * 319. 灯泡开关
     * 初始时有n 个灯泡处于关闭状态。第一轮，你将会打开所有灯泡。接下来的第二轮，你将会每两个灯泡关闭一个。
     * 第三轮，你每三个灯泡就切换一个灯泡的开关（即，打开变关闭，关闭变打开）。第 i 轮，你每 i 个灯泡就切换一个灯泡的开关。直到第 n 轮，你只需要切换最后一个灯泡的开关。
     * 找出并返回 n轮后有多少个亮着的灯泡。
     */
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n + 0.5);
    }

    /**
     * 输入：n = 3
     * 输出：1
     * 解释：
     * 初始时, 灯泡状态 [关闭, 关闭, 关闭].
     * 第一轮后, 灯泡状态 [开启, 开启, 开启].
     * 第二轮后, 灯泡状态 [开启, 关闭, 开启].
     * 第三轮后, 灯泡状态 [开启, 关闭, 关闭].
     * 你应该返回 1，因为只有一个灯泡还亮着。
     * <p>
     * 示例 2：
     * 输入：n = 0
     * 输出：0
     * <p>
     * 示例 3：
     * 输入：n = 1
     * 输出：1
     */
    @Test
    public void try319() {
        System.out.println("1->" + bulbSwitch(3));
        System.out.println("0->" + bulbSwitch(0));
        System.out.println("1->" + bulbSwitch(1));
    }

    /**
     * 563. 二叉树的坡度
     * <p>
     * 给定一个二叉树，计算 整个树 的坡度 。
     * 一个树的 节点的坡度 定义即为，该节点左子树的节点之和和右子树节点之和的 差的绝对值 。如果没有左子树的话，左子树的节点之和为 0 ；没有右子树的话也是一样。空结点的坡度是 0 。
     * 整个树 的坡度就是其所有节点的坡度之和。
     */
    int ans = 0;

    public int findTilt(TreeNode root) {
        this.ans = 0;
        this.findTiltDfs(root);
        return this.ans;
    }

    public int findTiltDfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int sumLeft = this.findTiltDfs(node.left);
        int sumRight = this.findTiltDfs(node.right);
        this.ans += Math.abs(sumLeft - sumRight);
        return sumLeft + sumRight + node.val;
    }

    /**
     * 示例 1：
     * 输入：root = [1,2,3]
     * 输出：1
     * 解释：
     * 节点 2 的坡度：|0-0| = 0（没有子节点）
     * 节点 3 的坡度：|0-0| = 0（没有子节点）
     * 节点 1 的坡度：|2-3| = 1（左子树就是左子节点，所以和是 2 ；右子树就是右子节点，所以和是 3 ）
     * 坡度总和：0 + 0 + 1 = 1
     * <p>
     * 示例 2：
     * 输入：root = [4,2,9,3,5,null,7]
     * 输出：15
     * 解释：
     * 节点 3 的坡度：|0-0| = 0（没有子节点）
     * 节点 5 的坡度：|0-0| = 0（没有子节点）
     * 节点 7 的坡度：|0-0| = 0（没有子节点）
     * 节点 2 的坡度：|3-5| = 2（左子树就是左子节点，所以和是 3 ；右子树就是右子节点，所以和是 5 ）
     * 节点 9 的坡度：|0-7| = 7（没有左子树，所以和是 0 ；右子树正好是右子节点，所以和是 7 ）
     * 节点 4 的坡度：|(3+5+2)-(9+7)| = |10-16| = 6（左子树值为 3、5 和 2 ，和是 10 ；右子树值为 9 和 7 ，和是 16 ）
     * 坡度总和：0 + 0 + 0 + 2 + 7 + 6 = 15
     * <p>
     * 示例 3：
     * 输入：root = [21,7,14,1,1,2,2,3,3]
     * 输出：9
     */
    @Test
    public void try563() {
        TreeNode node1 = new TreeNode(1);
        node1.left = new TreeNode(2);
        node1.right = new TreeNode(3);
        System.out.println("1->" + findTilt(node1));

        TreeNode node2 = new TreeNode(4);
        node2.left = new TreeNode(2);
        node2.right = new TreeNode(9);
        node2.left.left = new TreeNode(3);
        node2.left.right = new TreeNode(5);
        node2.right.right = new TreeNode(7);
        System.out.println("15->" + findTilt(node2));

        TreeNode node3 = new TreeNode(21);
        node3.left = new TreeNode(7);
        node3.right = new TreeNode(14);
        node3.left.left = new TreeNode(1);
        node3.left.right = new TreeNode(1);
        node3.left.left.left = new TreeNode(3);
        node3.left.left.right = new TreeNode(3);
        node3.right.left = new TreeNode(2);
        node3.right.right = new TreeNode(2);
        System.out.println("9->" + findTilt(node3));
    }

    /**
     * 423. 从英文中重建数字
     * 给你一个字符串 s ，其中包含字母顺序打乱的用英文单词表示的若干数字（0-9）。按 升序 返回原始的数字。
     * s[i] 为 ["e","g","f","i","h","o","n","s","r","u","t","w","v","x","z"] 这些字符之一
     */
    public String originalDigits(String s) {
        // 0  -  zero       |   z
        // 1  -  one        |   o-zero-two-four
        // 2  -  two        |   w
        // 3  -  three      |   r-zero-four
        // 4  -  four       |   u
        // 5  -  five       |   f-four
        // 6  -  six        |   x
        // 7  -  seven      |   s-six
        // 8  -  eight      |   g
        // 9  -  nine       |   i-eight-six-five

        if (StringUtils.isBlank(s)) {
            return null;
        }

        s = "a" + s + "a";
        // 开始查找
        int zero = s.split("z").length - 1;
        int two = s.split("w").length - 1;
        int four = s.split("u").length - 1;
        int six = s.split("x").length - 1;
        int eight = s.split("g").length - 1;
        int five = s.split("f").length - 1 - four;
        int seven = s.split("s").length - 1 - six;
        int one = s.split("o").length - 1 - zero - two - four;
        int three = s.split("r").length - 1 - zero - four;
        int nine = s.split("i").length - 1 - eight - six - five;

        List<Integer> integers = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);
        StringBuilder sb = new StringBuilder();
        int index = -1;
        for (Integer temp : integers) {
            index++;
            if (temp == 0) {
                continue;
            }
            while ((temp-- > 0)) {
                sb.append(index);
            }
        }
        return sb.toString();
    }

    /**
     * 示例 1：
     * 输入：s = "owoztneoer"
     * 输出："012"
     * <p>
     * 示例 2：
     * 输入：s = "fviefuro"
     * 输出："45"
     */
    @Test
    public void try423() {
        System.out.println("012->" + this.originalDigits("owoztneoer"));
        System.out.println("45->" + this.originalDigits("fviefuro"));
    }

    /**
     * 458. 可怜的小猪
     * 有 buckets 桶液体，其中 正好 有一桶含有毒药，其余装的都是水。它们从外观看起来都一样。为了弄清楚哪只水桶含有毒药，你可以喂一些猪喝，通过观察猪是否会死进行判断。
     * 不幸的是，你只有minutesToTest 分钟时间来确定哪桶液体是有毒的。
     * <p>
     * 喂猪的规则如下：
     * <p>
     * 选择若干活猪进行喂养
     * 可以允许小猪同时饮用任意数量的桶中的水，并且该过程不需要时间。
     * 小猪喝完水后，必须有 minutesToDie 分钟的冷却时间。在这段时间里，你只能观察，而不允许继续喂猪。
     * 过了 minutesToDie 分钟后，所有喝到毒药的猪都会死去，其他所有猪都会活下来。
     * 重复这一过程，直到时间用完。
     * 给你桶的数目 buckets ，minutesToDie 和 minutesToTest ，返回在规定时间内判断哪个桶有毒所需的 最小 猪数。
     */
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        // 一头猪代表行  一头猪代表列
        // 先确定轮数 - 可以通过排除前n列得到n+1列结果
        int maxRound = minutesToTest / minutesToDie + 1;
        int pigs = 1;
        // maxRound 的 pigs 次幂
        while (Math.pow(maxRound, pigs) < buckets) {
            pigs++;
        }
        return pigs;
    }

    /**
     * 示例 1：
     * 输入：buckets = 1000, minutesToDie = 15, minutesToTest = 60
     * 输出：5
     * <p>
     * 示例 2：
     * 输入：buckets = 4, minutesToDie = 15, minutesToTest = 15
     * 输出：2
     * <p>
     * 示例 3：
     * 输入：buckets = 4, minutesToDie = 15, minutesToTest = 30
     * 输出：2
     */
    @Test
    public void try458() {
        System.out.println("5->" + this.poorPigs(1000, 15, 60));
        System.out.println("2->" + this.poorPigs(4, 15, 15));
        System.out.println("2->" + this.poorPigs(4, 15, 30));
    }

    /**
     * 1816. 截断句子
     * <p>
     * 句子 是一个单词列表，列表中的单词之间用单个空格隔开，且不存在前导或尾随空格。每个单词仅由大小写英文字母组成（不含标点符号）。
     * <p>
     * 例如，"Hello World"、"HELLO" 和 "hello world hello world" 都是句子。
     * 给你一个句子 s 和一个整数 k ，请你将 s 截断 ，使截断后的句子仅含 前 k 个单词。返回 截断 s 后得到的句子。
     */
    public String truncateSentence(String s, int k) {
        if (StringUtils.isBlank(s)) {
            return s;
        }

        String[] ss = s.split(" ");
        if (k >= ss.length) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (String emmmm : ss) {
            sb.append(emmmm);
            if (--k == 0) {
                break;
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 示例 1：
     * 输入：s = "Hello how are you Contestant", k = 4
     * 输出："Hello how are you"
     * 解释：
     * s 中的单词为 ["Hello", "how" "are", "you", "Contestant"]
     * 前 4 个单词为 ["Hello", "how", "are", "you"]
     * 因此，应当返回 "Hello how are you"
     * <p>
     * 示例 2：
     * 输入：s = "What is the solution to this problem", k = 4
     * 输出："What is the solution"
     * 解释：
     * s 中的单词为 ["What", "is" "the", "solution", "to", "this", "problem"]
     * 前 4 个单词为 ["What", "is", "the", "solution"]
     * 因此，应当返回 "What is the solution"
     * <p>
     * 示例 3：
     * 输入：s = "chopper is not a tanuki", k = 5
     * 输出："chopper is not a tanuki"
     */
    @Test
    public void test1816() {
        System.out.println("Hello how are you-->" + this.truncateSentence("Hello how are you Contestant", 4));
        System.out.println("What is the solution-->" + this.truncateSentence("What is the solution to this problem", 4));
        System.out.println("chopper is not a tanuki-->" + this.truncateSentence("chopper is not a tanuki", 5));
    }

    /**
     * 71. 简化路径
     * 给你一个字符串 path ，表示指向某一文件或目录的Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
     * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..）表示将目录切换到上一级（指向父目录）；
     * 两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
     * 请注意，返回的 规范路径 必须遵循下述格式：
     * <p>
     * 始终以斜杠 '/' 开头。
     * 两个目录名之间必须只有一个斜杠 '/' 。
     * 最后一个目录名（如果存在）不能 以 '/' 结尾。
     * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
     * 返回简化后得到的 规范路径 。
     */
    public String simplifyPath(String path) {
        String[] split = path.split("/");
        String[] resultStrs = new String[split.length];
        int index = 0;

        for (String temp : split) {
            if (temp.equals("")) {
                // 多个连续斜杠
                continue;
            }
            if (temp.equals(".")) {
                // 当前目录本身
                continue;
            }
            if (temp.equals("..")) {
                if (index == 0) {
                    continue;
                }
                // 上级目录
                index--;
                resultStrs[index] = null;
                continue;
            }
            resultStrs[index++] = temp;
        }

        StringBuilder sb = new StringBuilder("/");
        for (String temp : resultStrs) {
            if (temp != null) {
                sb.append(temp);
                sb.append("/");
            }
        }
        if (sb.length() == 1) {
            return sb.toString();
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 示例 1：
     * 输入：path = "/home/"
     * 输出："/home"
     * 解释：注意，最后一个目录名后面没有斜杠。
     * <p>
     * 示例 2：
     * 输入：path = "/../"
     * 输出："/"
     * 解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
     * <p>
     * 示例 3：
     * 输入：path = "/home//foo/"
     * 输出："/home/foo"
     * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
     * <p>
     * 示例 4：
     * 输入：path = "/a/./b/../../c/"
     * 输出："/c"
     */
    @Test
    public void try71() {
        System.out.println("/home -> " + simplifyPath("/home/"));
        System.out.println("/ -> " + simplifyPath("/../"));
        System.out.println("/home//foo/ -> " + simplifyPath("/home/foo"));
        System.out.println("/a/./b/../../c/ -> " + simplifyPath("/c"));
    }

    /**
     * 2013. 检测正方形
     * 给你一个在 X-Y 平面上的点构成的数据流。设计一个满足下述要求的算法：
     * 添加 一个在数据流中的新点到某个数据结构中。可以添加 重复 的点，并会视作不同的点进行处理。
     * 给你一个查询点，请你从数据结构中选出三个点，使这三个点和查询点一同构成一个 面积为正 的 轴对齐正方形 ，统计 满足该要求的方案数目。
     * 轴对齐正方形 是一个正方形，除四条边长度相同外，还满足每条边都与 x-轴 或 y-轴 平行或垂直。
     * <p>
     * 实现 DetectSquares 类：
     * DetectSquares() 使用空数据结构初始化对象
     * void add(int[] point) 向数据结构添加一个新的点 point = [x, y]
     * int count(int[] point) 统计按上述方式与点 point = [x, y] 共同构造 轴对齐正方形 的方案数。
     */
    class DetectSquares {
        // Map< X , Map< Y , NUM > >
        Map<Integer, Map<Integer, Integer>> pointMapX;
        // Map< Y , X >  数量已由x储存，无需重复
        Map<Integer, Set<Integer>> pointMapY;

        public DetectSquares() {
            // 初始化
            this.pointMapX = new HashMap<>();
            this.pointMapY = new HashMap<>();
        }

        public void add(int[] point) {
            int x = point[0];
            int y = point[1];

            // 观察x轴
            if (pointMapX.containsKey(x)) {
                if (pointMapX.get(x).containsKey(y)) {
                    // 插入已存在的点
                    int num = pointMapX.get(x).get(y) + 1;
                    pointMapX.get(x).put(y, num);
                    pointMapY.get(y).add(x);
                    return;
                }
                pointMapX.get(x).put(y, 1);
            } else {
                pointMapX.put(x, new HashMap<Integer, Integer>() {{
                    this.put(y, 1);
                }});
            }
            // 观察y轴
            if (pointMapY.containsKey(y)) {
                // 检查x轴时已返回了完全相同的坐标
                pointMapY.get(y).add(x);
            } else {
                pointMapY.put(y, new HashSet<Integer>() {{
                    this.add(x);
                }});
            }
        }

        public int count(int[] point) {
            //  (x3,y2)   (x1,y2)   (x2,y2)
            //
            //  (x3,y1)   (x1,y1)   (x2,y1)
            //
            //  (x3,y2)   (x1,y2)   (x2,y2)

            // 以传入点为x1,y1思考
            int x1 = point[0];
            int y1 = point[1];
            if (this.pointMapY.isEmpty()) {
                // 为空时直接返回空
                return 0;
            }
            if (!this.pointMapX.containsKey(x1) || !this.pointMapY.containsKey(y1)) {
                // 正方形其余三点必定有与其相同y的一个  相同x的一个
                // 即(x1,y2)  (x2,y1)
                return 0;
            }

            int num = 0;
            // 先看相同x轴 即上方点或下方点  (x1,y2)
            Map<Integer, Integer> y2Map = this.pointMapX.get(x1);
            for (Integer y2 : y2Map.keySet()) {
                // 存在(x1,y2)点  检查是否有 (x2,y2) 以及 (x2,y1)
                Integer x1y2Num = y2Map.get(y2);
                // 由于是正方形  即|y2-y1| = |x2-x1|
                int length = Math.abs(y2 - y1);
                int x2 = x1 + length;
                int x3 = x1 - length;
                // 至此已知 x1 x2 x3  y1 y2  即所有点
                if (this.pointMapY.get(y2).contains(x2) && this.pointMapX.containsKey(x2) && this.pointMapX.get(x2).containsKey(y1)) {
                    num += this.pointMapX.get(x1).get(y2) * this.pointMapX.get(x2).get(y1) * this.pointMapX.get(x2).get(y2);

                    if (this.pointMapX.get(x1).containsKey(y1)) {
                        num += this.pointMapX.get(x1).get(y1);
                    }
                }
                if (this.pointMapY.get(y2).contains(x3) && this.pointMapX.containsKey(x3) && this.pointMapX.get(x3).containsKey(y1)) {
                    num += this.pointMapX.get(x1).get(y2) * this.pointMapX.get(x3).get(y1) * this.pointMapX.get(x3).get(y2);

                    if (this.pointMapX.get(x1).containsKey(y1)) {
                        num += this.pointMapX.get(x1).get(y1);
                    }
                }
            }
            return num;
        }
    }

    /**
     * 输入：
     * ["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
     * [[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
     * 输出：
     * [null, null, null, null, 1, 0, null, 2]
     * <p>
     * 解释：
     * DetectSquares detectSquares = new DetectSquares();
     * detectSquares.add([3, 10]);
     * detectSquares.add([11, 2]);
     * detectSquares.add([3, 2]);
     * detectSquares.count([11, 10]); // 返回 1 。你可以选择：
     * //   - 第一个，第二个，和第三个点
     * detectSquares.count([14, 8]);  // 返回 0 。查询点无法与数据结构中的这些点构成正方形。
     * detectSquares.add([11, 2]);    // 允许添加重复的点。
     * detectSquares.count([11, 10]); // 返回 2 。你可以选择：
     * //   - 第一个，第二个，和第三个点
     * //   - 第一个，第三个，和第四个点
     */
    @Test
    public void try2013() {
        DetectSquares detectSquares = new DetectSquares();
        detectSquares.add(new int[]{3, 10});
        detectSquares.add(new int[]{11, 2});
        detectSquares.add(new int[]{3, 2});
        System.out.println("1->" + detectSquares.count(new int[]{11, 10}));
        System.out.println("0->" + detectSquares.count(new int[]{14, 8}));
        detectSquares.add(new int[]{11, 2});
        System.out.println("2->" + detectSquares.count(new int[]{11, 10}));
    }

    /**
     * 1765. 地图中的最高点
     * <p>
     * 给你一个大小为m x n的整数矩阵isWater，它代表了一个由 陆地和 水域单元格组成的地图。
     * <p>
     * 如果isWater[i][j] == 0，格子(i, j)是一个 陆地格子。
     * 如果isWater[i][j] == 1，格子(i, j)是一个 水域格子。
     * 你需要按照如下规则给每个单元格安排高度：
     * <p>
     * 每个格子的高度都必须是非负的。
     * 如果一个格子是是 水域，那么它的高度必须为 0。
     * 任意相邻的格子高度差 至多为 1。当两个格子在正东、南、西、北方向上相互紧挨着，就称它们为相邻的格子。（也就是说它们有一条公共边）
     * 找到一种安排高度的方案，使得矩阵中的最高高度值最大。
     * <p>
     * 请你返回一个大小为m x n的整数矩阵 height，其中 height[i][j]是格子 (i, j)的高度。如果有多种解法，请返回任意一个。
     */
    public int[][] highestPeak(int[][] isWater) {
        int yNum = isWater.length;
        int xNum = isWater[0].length;

        int[][] resultData = new int[yNum][xNum];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                if (isWater[i][j] == 1) {
                    // 水域
                    resultData[i][j] = 0;
                    queue.offer(new int[]{i, j});
                } else {
                    resultData[i][j] = -1;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            for (int[] pointCut : pointCut1765) {
                int y = point[0] + pointCut[0];
                int x = point[1] + pointCut[1];
                if (0 <= y && y < yNum && 0 <= x && x < xNum && resultData[y][x] == -1) {
                    resultData[y][x] = resultData[point[0]][point[1]] + 1;
                    queue.offer(new int[]{y, x});
                }
            }
        }
        return resultData;
    }

    static final int[][] pointCut1765 = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * 示例 1：
     * 输入：isWater = [[0,1],[0,0]]
     * 输出：[[1,0],[2,1]]
     * 解释：上图展示了给各个格子安排的高度。
     * 蓝色格子是水域格，绿色格子是陆地格。
     * <p>
     * 示例 2：
     * 输入：isWater = [[0,0,1],[1,0,0],[0,0,0]]
     * 输出：[[1,1,0],[0,1,1],[1,2,2]]
     * 解释：所有安排方案中，最高可行高度为 2 。
     * 任意安排方案中，只要最高高度为 2 且符合上述规则的，都为可行方案。
     */
    @Test
    public void try1765() {
//        int[][] ints = this.highestPeak(new int[][]{{0, 1}, {0, 0}});
//        int[][] ints1 = this.highestPeak(new int[][]{{0, 0, 1}, {1, 0, 0}, {0, 0, 0}});
        int[][] ints1 = this.highestPeak(new int[][]{{0, 1, 1, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0}});
        System.out.println();
    }


    /**
     * 面试题 01.05. 一次编辑
     * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
     */
    public boolean oneEditAway(String first, String second) {
        // 思路
        // 取最长的比较，此时只有删除字符/替换一个字符，不需考虑插入字符

        // 特殊情况
        if (first.equals(second)) {
            return true;
        }

        if (second.length() > first.length()) {
            // 使得first为最长
            String temp = second;
            second = first;
            first = temp;
        }

        int index = 0;
        boolean changeFlag = false;
        for (char firstChar : first.toCharArray()) {
            if (index >= second.length()) {
                // 已经到了尽头
                if (!changeFlag) {
                    // 还没变更-用一次变更机会
                    changeFlag = true;
                } else {
                    return false;
                }
            }

            char secondChar = second.charAt(index);

            if (firstChar == secondChar) {
                // 相等
                index++;
            } else if (!changeFlag) {
                // 还没变更过-进行一次变更
                changeFlag = true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 示例1:
     * 输入:
     * first = "pale"
     * second = "ple"
     * 输出: True
     * <p>
     * 示例2:
     * 输入:
     * first = "pales"
     * second = "pal"
     * 输出: False
     */
    @Test
    public void try0105() {
        System.out.println("true->" + this.oneEditAway("pale", "ple"));
        System.out.println("false->" + this.oneEditAway("pales", "pal"));
    }


    /**
     * 946. 验证栈序列
     * 给定pushed和popped两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false。
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // 双指针
        int pushedIndex = 0, poppedIndex = 0;
        // 记录是否已被推出
        boolean[] pushedFlag = new boolean[pushed.length];
        // 移动pushed指针
        int move = 1;

        while ((pushedIndex >= 0 && pushedIndex < pushed.length) && poppedIndex < popped.length) {
            // 检查是否相同
            if (pushed[pushedIndex] == popped[poppedIndex]) {
                // 当前位置相等
                // 弹出指针移动到下一位
                poppedIndex++;
                // 标记已弹出
                pushedFlag[pushedIndex] = true;

                // 移动压入指针
                // 相同时应继续比较已压入的（特殊情况：第一次进入时无法后退
                move = pushedIndex == 0 ? move : -1;
                int tempIndex = pushedIndex;
                boolean complete = false;
                // 找到下一个未弹出的压入
                do {
                    pushedIndex += move;
                    if (pushedIndex == -1 && poppedIndex == popped.length) {
                        return true;
                    }
                    if (pushedIndex == -1) {
                        // 已经到了开头-改为前进
                        move = 1;
                        pushedIndex = tempIndex + move;
                        if (complete) {
                            break;
                        }
                        complete = true;
                    }
                    if (pushedIndex == pushed.length) {
                        // 已经到了尽头-改为后退
                        move = -1;
                        pushedIndex = tempIndex + move;
                        if (complete) {
                            break;
                        }
                        complete = true;
                    }
                } while ((pushedIndex >= 0 && pushedIndex < pushed.length) && poppedIndex < popped.length && pushedFlag[pushedIndex]);
            } else {
                // 不相同-继续压入 无弹出
                pushedIndex++;
            }
        }

        for (boolean temp : pushedFlag) {
            if (!temp) {
                return false;
            }
        }

        return true;
    }

    /**
     * 示例 1：
     * <p>
     * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
     * 输出：true
     * 解释：我们可以按以下顺序执行：
     * push(1), push(2), push(3), push(4), pop() -> 4,
     * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
     * 示例 2：
     * <p>
     * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
     * 输出：false
     * 解释：1 不能在 2 之前弹出。
     */
    @Test
    public void try946() {
        System.out.println("true->" + this.validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 2, 1}));
        System.out.println("false->" + this.validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 3, 5, 1, 2}));
        System.out.println("true->" + this.validateStackSequences(new int[]{0}, new int[]{0}));
        System.out.println("true->" + this.validateStackSequences(new int[]{1, 0}, new int[]{1, 0}));
    }


    /**
     * 1700. 无法吃午餐的学生数量
     * 学校的自助午餐提供圆形和方形的三明治，分别用数字 0 和 1 表示。所有学生站在一个队列里，每个学生要么喜欢圆形的要么喜欢方形的。
     * 餐厅里三明治的数量与学生的数量相同。所有三明治都放在一个 栈 里，每一轮：
     * 如果队列最前面的学生 喜欢 栈顶的三明治，那么会 拿走它 并离开队列。
     * 否则，这名学生会 放弃这个三明治 并回到队列的尾部。
     * 这个过程会一直持续到队列里所有学生都不喜欢栈顶的三明治为止。
     * 给你两个整数数组 students 和 sandwiches ，
     * 其中 sandwiches[i] 是栈里面第 i 个三明治的类型（i = 0 是栈的顶部），
     * students[j] 是初始队列里第 j 名学生对三明治的喜好（j = 0 是队列的最开始位置）。
     * 请你返回无法吃午餐的学生数量。
     */
    public int countStudents(int[] students, int[] sandwiches) {
        if (students.length == 0) {
            return 0;
        }

        // 学生指针
        int stuIndex = 0;
        // 用于记录新面包时当前学生的位置
        int stuStartIndex = 0;
        boolean initFlag = false;
        // 记录学生是否已离开
        boolean[] studentsFlag = new boolean[students.length];
        // 三文治指针
        int sandIndex = 0;
        // 吃的数量
        int num = 0;

        while (num < students.length) {
            if (students[stuIndex] == sandwiches[sandIndex]) {
                // 排队第一位的学生喜欢吃目前的面包
                sandIndex++;
                studentsFlag[stuIndex] = true;
                num++;
                if (num == students.length) {
                    break;
                }
                // 下一个未离开的学生作为第一位
                while (true) {
                    stuIndex++;
                    if (stuIndex == students.length) {
                        // 初始指针
                        stuIndex = 0;
                    }
                    if (!studentsFlag[stuIndex]) {
                        stuStartIndex = stuIndex;
                        break;
                    }
                }
                initFlag = false;
            } else {
                // 排队的第一个学生不喜欢吃目前的面包 跑到队伍后面-即指针指向下一个
                while (true) {
                    stuIndex++;
                    if (stuIndex == students.length) {
                        // 初始指针
                        stuIndex = 0;
                    }
                    if (!studentsFlag[stuIndex]) {
                        break;
                    }
                }
            }

            if (!initFlag && stuStartIndex == stuIndex) {
                initFlag = true;
            } else if (stuStartIndex == stuIndex) {
                // 当前面包没有任何人喜欢吃
                break;
            }
        }

        return students.length - num;
    }

    /**
     * 示例 1：
     * <p>
     * 输入：students = [1,1,0,0], sandwiches = [0,1,0,1]
     * 输出：0
     * 解释：
     * - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = [1,0,0,1]。
     * - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = [0,0,1,1]。
     * - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = [0,1,1]，三明治栈为 sandwiches = [1,0,1]。
     * - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = [1,1,0]。
     * - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = [1,0]，三明治栈为 sandwiches = [0,1]。
     * - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = [0,1]。
     * - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = [1]，三明治栈为 sandwiches = [1]。
     * - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = []，三明治栈为 sandwiches = []。
     * 所以所有学生都有三明治吃。
     * <p>
     * 示例 2：
     * <p>
     * 输入：students = [1,1,1,0,0,1], sandwiches = [1,0,0,0,1,1]
     * 输出：3
     * <p>
     * [0,0,0,1,0,1,1,1,1,0,1]
     * [0,0,0,1,0,0,0,0,0,1,0]
     */
    @Test
    public void try1700() {
        System.out.println("0->" + this.countStudents(new int[]{1, 1, 0, 0}, new int[]{0, 1, 0, 1}));
        System.out.println("3->" + this.countStudents(new int[]{1, 1, 1, 0, 0, 1}, new int[]{1, 0, 0, 0, 1, 1}));
        System.out.println("5->" + this.countStudents(new int[]{0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1}, new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0}));
    }

    /**
     * 779. 第K个语法符号
     * 我们构建了一个包含 n 行索引从 1 开始)的表。首先在第一行我们写上一个 0。接下来的每一行，将前一行中的0替换为01，1替换为10。
     * <p>
     * 例如，对于 n = 3 ，第 1 行是 0 ，第 2 行是 01 ，第3行是 0110 。
     * 给定行数n和序数 k，返回第 n 行中第 k个字符。（k从索引 1 开始）
     */
    public int kthGrammar(int n, int k) {
        // 前两位必定是01
        if (k <= 2) {
            return k == 1 ? 0 : 1;
        }

        // 思路-第n行的个数是2^n
        // 每一行的前一半是上一行  后一半是上一行的变化

        // 当前行的数字一半的个数
        long halfNum = 1L << (n - 1);
        if (k <= halfNum) {
            // 如果是前一半 即上一行第k个
            return kthGrammar(n - 1, k);
        } else {
            // 如果是后一半 即上一行后一半的对应变化
            boolean singleFlag = k % 2 == 1;
            int index = (k + 1) >> 1;
            int temp = this.kthGrammar(n - 1, index);
            if (temp == 1) {
                return singleFlag ? 1 : 0;
            } else {
                return singleFlag ? 0 : 1;
            }
        }
    }

    /**
     * 示例 1:
     * 输入: n = 1, k = 1
     * 输出: 0
     * 解释: 第一行：0
     * <p>
     * 示例 2:
     * 输入: n = 2, k = 1
     * 输出: 0
     * 解释:
     * 第一行: 0
     * 第二行: 01
     * <p>
     * 示例 3:
     * 输入: n = 2, k = 2
     * 输出: 1
     * 解释:
     * 第一行: 0
     * 第二行: 01
     */
    @Test
    public void try779() {
//        System.out.println("0->" + this.kthGrammar(1, 1));
//        System.out.println("0->" + this.kthGrammar(2, 1));
//        System.out.println("1->" + this.kthGrammar(2, 2));
        System.out.println("1->" + this.kthGrammar(3, 3));
    }


    @Test
    public void tttttttttt() {
        System.out.println(1 << 2);
        System.out.println(1 << 3);
    }

    /**
     * 862. 和至少为 K 的最短子数组
     * 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。如果不存在这样的 子数组 ，返回 -1 。
     * <p>
     * 子数组 是数组中 连续 的一部分。
     */
    public int shortestSubarray(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0] == k ? 1 : -1;
        }
        // 先排序
        Arrays.sort(nums);

        // 每个数字都有机会拿或者不拿
        for (int index = 0; index < nums.length; index++) {

            // 拿

            // 不拿
        }


        return -1;
    }

    private int findLess862(int[] nums, int k, int index, int temp, int num) {
        if (index == nums.length) {
            // 已经到最后一位-返回不存在
            return -1;
        }
        if (temp == k) {
            // 已经相等-返回已取个数
            return num;
        }

        // 下一位
        index++;
        // 拿
        int get = this.findLess862(nums, k, index, temp + nums[index], num++);
        // 不拿
        int noGet = this.findLess862(nums, k, index, temp, num);

        if (get == -1 && noGet == -1) {
            // 拿不拿都找不到-全找不到
            return -1;
        }
        return Math.min(get, noGet);
    }


    /**
     * 示例 1：
     * <p>
     * 输入：nums = [1], k = 1
     * 输出：1
     * 示例 2：
     * <p>
     * 输入：nums = [1,2], k = 4
     * 输出：-1
     * 示例 3：
     * <p>
     * 输入：nums = [2,-1,2], k = 3
     * 输出：3
     */
    @Test
    public void try862() {
        System.out.println();
    }
}
