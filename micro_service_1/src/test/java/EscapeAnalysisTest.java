import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName EscapeAnalysisTest
 * @Description 逃逸分析
 * @Author Wenbo Li
 * @Date 3/6/2023 上午 11:30
 * @Version 1.0
 */
@Slf4j
public class EscapeAnalysisTest {

    public static void main(String[] args) {
        long a1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            alloc();
        }
        long a2 = System.currentTimeMillis();
        log.info("cost {} ms", a2 - a1);
        try {
            Thread.sleep(1000000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User();
    }

    static class User {

    }
}
