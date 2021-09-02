package com.company.research_zookeeper.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-08-22 10:39:59
 */
@Data
@Slf4j
@ToString
@NoArgsConstructor
public class ZooBean {

    private String key;

    private String value;
}
