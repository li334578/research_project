import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.micro_service_1.MicroService1Application;
import com.company.micro_service_1.bean.FilmBean;
import com.company.micro_service_1.service.FilmBeanService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest(classes = MicroService1Application.class)
public class MainTest {

    @Resource
    private FilmBeanService filmBeanService;

    @Resource
    private Redisson redisson;
    private RBloomFilter<String> bikanBloomFilter;

    private BitMapBloomFilter bitMapBloomFilter;

    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        List<Integer> collect = Stream.iterate(0, i -> i + 1).limit(10000).collect(Collectors.toList());
        Runnable runnable = () -> {
//            for (Integer integer : collect) {
//                num.getAndAdd(1);
//            }
            for (int i = 0; i < 1000000000; i++) {
                num.getAndAdd(1);
            }
            log.info(Thread.currentThread().getName() + "执行结束!");
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        log.info("num = " + num);
    }

    @Test
    public void test() {
//        QueryWrapper<FilmBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.ge("id", 254);
//        filmBeanService.remove(queryWrapper);
        List<FilmBean> list = filmBeanService.list();
        list.stream().filter(item -> StrUtil.isNotEmpty(item.getMagnet()) && item.getMagnet().contains("[")).forEach(item -> {
            item.setMagnet(item.getMagnet().replace("[电影天堂www.dytt89.com]", ""));
            item.setMagnet(item.getMagnet().replace("[电影天堂www.dy2018.com]", ""));
        });
        filmBeanService.saveOrUpdateBatch(list);

    }

    @Test
    public void testMethod1_5() {
        String uriStepOne = "/html/gndy/jddyy/index_{}.html";
//        bikanBloomFilter = redisson.getBloomFilter("bikan");
//        bikanBloomFilter.tryInit(3000L, 0.03);

        initBloomFilter();
        // 共 24页
        for (int i = 1; i <= 67; i++) {
            List<FilmBean> fileBeanList;
            if (i != 1) {
                fileBeanList = getFileBeanList(StrUtil.format(uriStepOne, i));
            } else {
                fileBeanList = getFileBeanList("/html/gndy/jddyy/index.html");
            }
            filmBeanService.saveBatch(fileBeanList);
            log.info("第{}页保存完成 保存了{}条数据", i, fileBeanList.size());
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initBloomFilter() {
        bitMapBloomFilter = new BitMapBloomFilter(2500);
        QueryWrapper<FilmBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("title");
        List<FilmBean> list = filmBeanService.list(queryWrapper);
        list.forEach(item -> bitMapBloomFilter.add(item.getTitle()));
    }

    private List<FilmBean> getFileBeanList(String uriStepOne) {
        List<FilmBean> filmBeans = new ArrayList<>();
        String baseUri = "https://www.dy2018.com";
        String resultStepOne = HttpUtil.get(baseUri + uriStepOne);
        String regexStepOne = "<a href=\"(.*)\" class=\"ulink\" title=\".*《(.*)》(.*)\">.*</a>";
        Pattern patternOne = Pattern.compile(regexStepOne);
        ReUtil.findAll(patternOne, resultStepOne, m -> {
            String all = m.group(0);
            String href = m.group(1);
            String title_1 = m.group(2);
            String title_2 = m.group(3);
            FilmBean filmBean = new FilmBean();
            buildStepTwo(baseUri + href, filmBean);
            String title = StrUtil.format("{}-{}", title_1, title_2);
            filmBean.setTitle(title);
            if (!bitMapBloomFilter.contains(title)) {
                // 已经存在 不入库
                bitMapBloomFilter.add(title);
                filmBeans.add(filmBean);
                log.error("{} 将要入库", title);
            } else {
                log.error("{} 已存在 不入库", title);
            }
        });
        return filmBeans;
    }

    @Test
    public void testMethod1_6() {
        buildStepTwo("https://www.dy2018.com/i/106139.html", new FilmBean());
    }

    private void buildStepTwo(String url, FilmBean filmBean) {
        String result = HttpUtil.get(url);
        String scopeRegex = "<span>评分：<strong class=\"rank\">([\\d|\\.]*)</strong></span>";
        String scope = ReUtil.get(scopeRegex, result, 1);

        String typeRegexStepTwo = "<a href='/\\d/'>(.{3})</a>";
        List<String> allTypeList = ReUtil.getAllGroups(Pattern.compile(typeRegexStepTwo), result, false, true);

        String updateTimeRegex = "<span class=\"updatetime\">发布时间：(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})</span>";
        String updateTime = ReUtil.get(updateTimeRegex, result, 1);
        String magnetRegex = "<td style.*>(magnet:?\\S*)</a>\\S";
        String magnet = ReUtil.get(magnetRegex, result, 1);
        filmBean.setScope(scope).setType(allTypeList.toString()).setUpdateTime(updateTime).setMagnet(magnet);
    }
}