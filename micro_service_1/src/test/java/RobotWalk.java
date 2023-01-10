import java.util.Arrays;

/**
 * @Date 9/1/2023 0009 下午 1:18
 * @Description 机器人到达指定位置方法数
 * @Version 1.0.0
 * @Author liwenbo
 */
public class RobotWalk {
    /*
    * 假设有排成一行的N个位置，记为1~N，N一定大于或等于2。开始时机器人在其中的M位置上（M一定是1~N中的一个），
      机器人可以往左走或者往右走，如果机器人来到1位置，那么下一步只能往右来到2位置；
      如果机器人来到N位置，那么下一步只能往左来到N-1位置。
      规定，机器人必须走K步，最终能来到P位置（P也一定是1~N中的一个）的方法有多少种。
      给定4个参数N、M、K、P，返回方法数。
    * */


    public static int ways1(int N, int start, int aim, int K) {
        return process1(start, K, aim, N);
    }

    /**
     * @param cur   机器人当前来到的位置
     * @param reset 机器人还能走多少步
     * @param aim   最终目标
     * @param N     有哪些位置
     * @return 机器人从cur出发走过reset步后最终停在aim的方法数是s
     */
    public static int process1(int cur, int reset, int aim, int N) {
        if (reset == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return process1(2, reset - 1, aim, N);
        }
        if (cur == N) {
            return process1(N - 1, reset - 1, aim, N);
        }
        return process1(cur - 1, reset - 1, aim, N) + process1(cur + 1, reset - 1, aim, N);
    }


    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        return process2(start, K, aim, N, dp);
    }

    /**
     * @param cur   机器人当前来到的位置 1-N
     * @param reset 机器人还能走多少步 0-K
     * @param aim   最终目标
     * @param N     有哪些位置
     * @param dp    缓存表
     * @return 机器人从cur出发走过reset步后最终停在aim的方法数是s
     */
    public static int process2(int cur, int reset, int aim, int N, int[][] dp) {
        // 从顶向下的动态规划 也叫 记忆化搜索
        if (dp[cur][reset] != -1) {
            return dp[cur][reset];
        }
        int result;
        if (reset == 0) {
            result = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            result = process2(2, reset - 1, aim, N, dp);
        } else if (cur == N) {
            result = process2(N - 1, reset - 1, aim, N, dp);
        } else {
            result = process2(cur - 1, reset - 1, aim, N, dp) + process2(cur + 1, reset - 1, aim, N, dp);
        }
        dp[cur][reset] = result;
        return result;
    }

    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        // cur = 0 rest = 0 这一行不用
        // rest = 0 1. cur = aim 是1 2. 其他是0
        dp[aim][0] = 1;
        // cur = 1 依赖于 左下角 dp[2][rest-1]的值
        // cur = N 依赖于 左上角 dp[N-1][rest-1]的值
        // cur = n 依赖于 左上角 + 左下角 dp[n-1][rest-1] + dp[n+1][rest-1]


        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2; cur < N; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        return dp[start][K];
    }
    public static void main(String[] args) {
        System.out.println(ways1(4, 2, 4, 4));
        System.out.println(ways2(4, 2, 4, 4));
        System.out.println(ways3(4, 2, 4, 4));
        System.out.println("======");
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
