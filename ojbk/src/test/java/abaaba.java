import java.util.*;

/**
 * @Author LiuRunYuan
 * @CreateDate 2020/7/29
 */
public class abaaba {

    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
    int n, m;

    public int minimalSteps(String[] maze) {
        n = maze.length;
        m = maze[0].length();
        // 机关 & 石头
        List<int[]> buttons = new ArrayList<int[]>();
        List<int[]> stones = new ArrayList<int[]>();
        // 起点 & 终点
        int sx = -1, sy = -1, tx = -1, ty = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i].charAt(j) == 'M') {
                    buttons.add(new int[]{i, j});
                }
                if (maze[i].charAt(j) == 'O') {
                    stones.add(new int[]{i, j});
                }
                if (maze[i].charAt(j) == 'S') {
                    sx = i;
                    sy = j;
                }
                if (maze[i].charAt(j) == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        }
        int nb = buttons.size();
        int ns = stones.size();
        int[][] startDist = bfs(sx, sy, maze);

        // 边界情况：没有机关
        if (nb == 0) {
            return startDist[tx][ty];
        }
        // 从某个机关到其他机关 / 起点与终点的最短距离。
        int[][] dist = new int[nb][nb + 2];
        for (int i = 0; i < nb; i++) {
            Arrays.fill(dist[i], -1);
        }
        // 中间结果
        int[][][] dd = new int[nb][][];
        for (int i = 0; i < nb; i++) {
            int[][] d = bfs(buttons.get(i)[0], buttons.get(i)[1], maze);
            dd[i] = d;
            // 从某个点到终点不需要拿石头
            dist[i][nb + 1] = d[tx][ty];
        }

        for (int i = 0; i < nb; i++) {
            int tmp = -1;
            for (int k = 0; k < ns; k++) {
                int midX = stones.get(k)[0], midY = stones.get(k)[1];
                if (dd[i][midX][midY] != -1 && startDist[midX][midY] != -1) {
                    if (tmp == -1 || tmp > dd[i][midX][midY] + startDist[midX][midY]) {
                        tmp = dd[i][midX][midY] + startDist[midX][midY];
                    }
                }
            }
            dist[i][nb] = tmp;
            for (int j = i + 1; j < nb; j++) {
                int mn = -1;
                for (int k = 0; k < ns; k++) {
                    int midX = stones.get(k)[0], midY = stones.get(k)[1];
                    if (dd[i][midX][midY] != -1 && dd[j][midX][midY] != -1) {
                        if (mn == -1 || mn > dd[i][midX][midY] + dd[j][midX][midY]) {
                            mn = dd[i][midX][midY] + dd[j][midX][midY];
                        }
                    }
                }
                dist[i][j] = mn;
                dist[j][i] = mn;
            }
        }

        // 无法达成的情形
        for (int i = 0; i < nb; i++) {
            if (dist[i][nb] == -1 || dist[i][nb + 1] == -1) {
                return -1;
            }
        }

        // dp 数组， -1 代表没有遍历到
        int[][] dp = new int[1 << nb][nb];
        for (int i = 0; i < 1 << nb; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < nb; i++) {
            dp[1 << i][i] = dist[i][nb];
        }

        // 由于更新的状态都比未更新的大，所以直接从小到大遍历即可
        for (int mask = 1; mask < (1 << nb); mask++) {
            for (int i = 0; i < nb; i++) {
                // 当前 dp 是合法的
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < nb; j++) {
                        // j 不在 mask 里
                        if ((mask & (1 << j)) == 0) {
                            int next = mask | (1 << j);
                            if (dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j]) {
                                dp[next][j] = dp[mask][i] + dist[i][j];
                            }
                        }
                    }
                }
            }
        }

        int ret = -1;
        int finalMask = (1 << nb) - 1;
        for (int i = 0; i < nb; i++) {
            if (ret == -1 || ret > dp[finalMask][i] + dist[i][nb + 1]) {
                ret = dp[finalMask][i] + dist[i][nb + 1];
            }
        }

        return ret;
    }

    public int[][] bfs(int x, int y, String[] maze) {
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(ret[i], -1);
        }
        ret[x][y] = 0;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int curx = p[0], cury = p[1];
            for (int k = 0; k < 4; k++) {
                int nx = curx + dx[k], ny = cury + dy[k];
                if (inBound(nx, ny) && maze[nx].charAt(ny) != '#' && ret[nx][ny] == -1) {
                    ret[nx][ny] = ret[curx][cury] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        return ret;
    }

    public boolean inBound(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }


    class test {
        /**
         * LCP 13. 寻宝
         * <p>
         * 我们得到了一副藏宝图，藏宝图显示，在一个迷宫中存在着未被世人发现的宝藏。
         * 迷宫是一个二维矩阵，用一个字符串数组表示。它标识了唯一的入口（用 'S' 表示），和唯一的宝藏地点（用 'T' 表示）。
         * 但是，宝藏被一些隐蔽的机关保护了起来。在地图上有若干个机关点（用 'M' 表示），只有所有机关均被触发，才可以拿到宝藏。
         * 要保持机关的触发，需要把一个重石放在上面。迷宫中有若干个石堆（用 'O' 表示），每个石堆都有无限个足够触发机关的重石。但是由于石头太重，我们一次只能搬一个石头到指定地点。
         * 迷宫中同样有一些墙壁（用 '#' 表示），我们不能走入墙壁。剩余的都是可随意通行的点（用 '.' 表示）。石堆、机关、起点和终点（无论是否能拿到宝藏）也是可以通行的。
         * 我们每步可以选择向上/向下/向左/向右移动一格，并且不能移出迷宫。搬起石头和放下石头不算步数。那么，从起点开始，我们最少需要多少步才能最后拿到宝藏呢？如果无法拿到宝藏，返回 -1 。
         * <p>
         * 示例 1：
         * 输入： ["S#O", "M..", "M.T"]
         * 输出：16
         * 解释：最优路线为： S->O, cost = 4, 去搬石头 O->第二行的M, cost = 3, M机关触发 第二行的M->O, cost = 3,
         * 我们需要继续回去 O 搬石头。 O->第三行的M, cost = 4, 此时所有机关均触发 第三行的M->T, cost = 2，去T点拿宝藏。 总步数为16。
         * <p>
         * 示例 2：
         * 输入： ["S#O", "M.#", "M.T"]
         * 输出：-1
         * 解释：我们无法搬到石头触发机关
         * <p>
         * 示例 3：
         * 输入： ["S#O", "M.T", "M.."]
         * 输出：17
         * 解释：注意终点也是可以通行的。
         */
        public int minimalSteps(String[] maze) {
            //机关按钮
            List<int[]> buttons = new ArrayList<>();
            //石头堆
            List<int[]> stones = new ArrayList<>();
            //起点位置
            int startX = -1, startY = -1;
            //终点位置
            int endX = -1, endY = -1;
            for (int y = 0; y < maze.length; y++) {
                for (int x = 0; x < maze[y].length(); y++) {
                    char temp = maze[y].charAt(x);
                    switch (temp) {
                        case 'M':
                            buttons.add(new int[]{x, y});
                            break;
                        case 'O':
                            stones.add(new int[]{x, y});
                            break;
                        case 'S':
                            startX = x;
                            startY = y;
                            break;
                        case 'T':
                            endX = x;
                            endX = y;
                            break;
                    }
                }
            }
            int buttonNum = buttons.size();
            int stoneNum = stones.size();

            //有机关没有石头，直接返回失败
            if (buttonNum > 0 && stoneNum == 0) {
                return -1;
            }
            //计算起点到任意点的距离
            int[][] startDist = bfs(startX, startY, maze);
            //不需要踩机关，直接返回 起点到终点 距离
            if (buttonNum == 0) {
                return startDist[endX][endY];
            }

            //保存机关到其他机关距离
            int[][] button2button = new int[buttonNum][];
            for (int i = 0; i < buttonNum; i++) {

            }
            return -1111111111;
        }

        /**
         * 广度优先搜索
         *
         * @param x
         * @param y
         * @param maze
         * @return
         */
        public int[][] bfs(int x, int y, String[] maze) {
            //记录走过的路
            int[][] ex = new int[maze.length][maze[0].length()];
            //先全部赋值为-1
            for (int[] temp : ex) {
                Arrays.fill(temp, -1);
            }
            //开始点置为0
            ex[x][y] = 0;
            Queue<int[]> queue = new LinkedList<>();
            //开始点放入队列
            queue.offer(new int[]{x, y});

            while (!queue.isEmpty()) {
                //获取需要处理的点
                int[] point = queue.poll();
                int pointX = point[0];
                int pointY = point[1];
                //一共有四种操作  上下左右
                for (int i = 0; i < 4; i++) {
                    //现在探索的位置
                    int nowX = pointX + dx[i];
                    int nowY = pointY + dy[i];
                    //不是边界  &&  不是墙  &&  还没走过
                    if ((nowX >= 0 && x < maze[0].length() && y >= 0 && y < maze.length)
                            && maze[nowX].charAt(nowY) != '#'
                            && ex[nowX][nowY] == -1) {
                        //保存距离
                        ex[nowX][nowY] = ex[pointX][pointY] + 1;
                        queue.offer(new int[]{nowX, nowY});
                    }
                }
            }
            return ex;
        }

        //每次移动的位置  右  左  上  下
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
    }


    class Solution {
        class Node {
            int[] ch = new int[26];
            int flag;

            public Node() {
                flag = -1;
            }
        }

        List<Node> tree = new ArrayList<Node>();

        public List<List<Integer>> palindromePairs(String[] words) {
            tree.add(new Node());
            int n = words.length;
            for (int i = 0; i < n; i++) {
                insert(words[i], i);
            }
            List<List<Integer>> ret = new ArrayList<List<Integer>>();
            for (int i = 0; i < n; i++) {
                int m = words[i].length();
                for (int j = 0; j <= m; j++) {
                    if (isPalindrome(words[i], j, m - 1)) {
                        int leftId = findWord(words[i], 0, j - 1);
                        if (leftId != -1 && leftId != i) {
                            ret.add(Arrays.asList(i, leftId));
                        }
                    }
                    if (j != 0 && isPalindrome(words[i], 0, j - 1)) {
                        int rightId = findWord(words[i], j, m - 1);
                        if (rightId != -1 && rightId != i) {
                            ret.add(Arrays.asList(rightId, i));
                        }
                    }
                }
            }
            return ret;
        }

        public void insert(String s, int id) {
            int len = s.length(), add = 0;
            for (int i = 0; i < len; i++) {
                int x = s.charAt(i) - 'a';
                if (tree.get(add).ch[x] == 0) {
                    tree.add(new Node());
                    tree.get(add).ch[x] = tree.size() - 1;
                }
                add = tree.get(add).ch[x];
            }
            tree.get(add).flag = id;
        }

        public boolean isPalindrome(String s, int left, int right) {
            int len = right - left + 1;
            for (int i = 0; i < len / 2; i++) {
                if (s.charAt(left + i) != s.charAt(right - i)) {
                    return false;
                }
            }
            return true;
        }

        public int findWord(String s, int left, int right) {
            int add = 0;
            for (int i = right; i >= left; i--) {
                int x = s.charAt(i) - 'a';
                if (tree.get(add).ch[x] == 0) {
                    return -1;
                }
                add = tree.get(add).ch[x];
            }
            return tree.get(add).flag;
        }
    }
}
