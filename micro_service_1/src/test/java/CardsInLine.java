import java.util.Arrays;

/**
 * @Date 10/1/2023 0010 下午 2:06
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
public class CardsInLine {

    public static int win1(int[] arr) {
        return Integer.max(g1(arr, 0, arr.length - 1), f1(arr, 0, arr.length - 1));
    }

    // 后手
    public static int g1(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        return Integer.min(f1(arr, L + 1, R),
                f1(arr, L, R - 1));
    }

    // 先手
    public static int f1(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        return Integer.max(arr[L] + g1(arr, L + 1, R),
                arr[R] + g1(arr, L, R - 1));
    }

    public static int win2(int[] arr) {
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(fmap[i], -1);
            Arrays.fill(gmap[i], -1);
        }
        return Integer.max(g2(arr, 0, N - 1, fmap, gmap), f2(arr, 0, N - 1, fmap, gmap));
    }

    // 后手
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int result;
        if (L == R) {
            result = 0;
        } else {
            result = Integer.min(f2(arr, L + 1, R, fmap, gmap),
                    f2(arr, L, R - 1, fmap, gmap));
        }
        gmap[L][R] = result;
        return result;
    }

    // 先手
    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }
        int result;
        if (L == R) {
            result = arr[L];
        } else {
            result = Integer.max(arr[L] + g2(arr, L + 1, R, fmap, gmap),
                    arr[R] + g2(arr, L, R - 1, fmap, gmap));
        }
        fmap[L][R] = result;
        return result;
    }

    public static int win3(int[] arr) {
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < fmap.length; i++) {
            fmap[i][i] = arr[i];
        }

        for (int startCol = 1; startCol < N; startCol++) {
            int L = 0;
            int R = startCol;
            while (R < N) {
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
        int[] arr1 = new int[]{5,3,4,5};
        System.out.println(win1(arr1));
    }
}
