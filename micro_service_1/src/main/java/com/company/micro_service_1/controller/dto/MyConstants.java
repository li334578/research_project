package com.company.micro_service_1.controller.dto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.copier.Copier;
import com.company.micro_service_1.bean.Cdk;

import java.util.Arrays;
import java.util.List;

/**
 * @Date 8/3/2022 0008 上午 10:38
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
public class MyConstants {

    public final static String GROUP_NAME = "active-b";
    public final static String NAME_SRV_ADDRESS = "127.0.0.1:9876";
    public final static String TOPIC_NAME = "topic-b";
    public final static String TAG_1 = "at1";
    public final static String TAG_2 = "at2";
    public final static String TAG_3 = "at3";
    public final static String TAG_4 = "at4";
    public final static List<String> TAGS = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4);

    public final static Integer AT1_COUNT = 50;
    public final static Integer AT2_COUNT = 250;
    public final static Integer AT3_COUNT = 500;
    public final static Integer AT4_COUNT = 200;

    public static final Copier<Cdk> CDK_COPIER = () -> {
        Cdk cdk = new Cdk();
        cdk.setUseStatus(1);
        cdk.setUseTime(DateUtil.now());
        return cdk;
    };

}
