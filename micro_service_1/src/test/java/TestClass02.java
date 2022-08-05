import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.company.micro_service_1.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

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

    @Test
    public void testMethod3() {
//        Student student = new Student(1L, "张三", 18);
//        Student clone = ObjectUtil.clone(student);
//        System.out.println(student.hashCode());
//        System.out.println(clone.hashCode());
    }

    @Test
    public void testMethod4() {
        String username = "lwb008";
        int index = Math.abs(username.hashCode()) % username.length();
        System.out.println(index);

        Digester md5 = new Digester(DigestAlgorithm.MD5);
        Digester md52 = new Digester(DigestAlgorithm.MD5);
        String c = String.valueOf(username.charAt(index));
        md5.setSalt(c.getBytes(StandardCharsets.UTF_8));
        String hex = md5.digestHex("123456");
        String hex2 = md52.digestHex("123456");
        System.out.println(hex);
        System.out.println(hex2);
    }
}
