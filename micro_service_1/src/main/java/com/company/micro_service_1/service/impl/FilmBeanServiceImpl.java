package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.FilmBean;
import com.company.micro_service_1.mapper.FilmBeanMapper;
import com.company.micro_service_1.service.FilmBeanService;
import org.springframework.stereotype.Service;

/**
 * @Date 28/9/2022 0028 下午 1:58
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Service
public class FilmBeanServiceImpl extends ServiceImpl<FilmBeanMapper, FilmBean> implements FilmBeanService {
}
