package com.company.sharding_sphere;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-26 20:21:48
 */
public class DclBean {

    private static volatile DclBean dclBean;

    public DclBean() {
    }

    public static DclBean getInstance() {
        // 业务代码
        if (dclBean == null) {
            // doubled checks lock
            synchronized (DclBean.class) {
                if (dclBean == null) {
                    dclBean = new DclBean();
                }
            }
        }
        return dclBean;
    }
}
