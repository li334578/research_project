package com.company.sharding_sphere.other;


import org.omg.SendingContext.RunTime;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-05-10 21:46:02
 */
public class CalMemory {
    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println(maxMemory);
        System.out.println(maxMemory/1024);
        System.out.println(totalMemory/1024);
        // -Xms1024m -Xmx1024m -XX +printGCDetails
    }
}
