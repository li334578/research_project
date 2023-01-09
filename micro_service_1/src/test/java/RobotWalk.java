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

    public static void main(String[] args) {
        System.out.println(ways1(4,2,4,4));
    }
}
