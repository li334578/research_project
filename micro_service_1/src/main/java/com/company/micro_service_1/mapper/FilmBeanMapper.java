package com.company.micro_service_1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.micro_service_1.bean.FilmBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date 28/9/2022 0028 下午 1:59
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Mapper
public interface FilmBeanMapper extends BaseMapper<FilmBean> {
}
