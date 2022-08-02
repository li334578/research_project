import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Date 1/8/2022 0001 下午 1:54
 * @Description 测试类
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class TestClass02 {

    @Test
    public void testMethod1() {
        BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(10);
        bitMapBloomFilter.add("aaa");
        bitMapBloomFilter.add("ccc");
        bitMapBloomFilter.add("bbb");
        // 查找
        boolean abc = bitMapBloomFilter.contains("abc");


        long mNum = NumberUtil.div(String.valueOf(10), String.valueOf(5)).longValue();
        long size = mNum * 1024L * 1024L * 8L;

        System.out.println(mNum);
        System.out.println(size);
    }


    @Test
    public void testMethod2() {

        String content = "ZZZaaabbbccc中文1234";
        String resultExtractMulti = ReUtil.extractMulti("(\\w)aa(\\w)", content, "$1-$2");

        System.out.println(resultExtractMulti);
    }
}
