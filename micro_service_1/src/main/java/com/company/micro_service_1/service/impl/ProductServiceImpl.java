package com.company.micro_service_1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.micro_service_1.bean.Product;
import com.company.micro_service_1.bean.Student;
import com.company.micro_service_1.mapper.ProductMapper;
import com.company.micro_service_1.mapper.StudentMapper;
import com.company.micro_service_1.service.ProductService;
import com.company.micro_service_1.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Product)表服务实现类
 *
 * @author makejava
 * @since 2022-07-09 11:25:07
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Resource
    private ProductMapper productMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Product queryById(Long id) {
        return this.productMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param product     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Product> queryByPage(Product product, PageRequest pageRequest) {
        long total = this.productMapper.count(product);
        return new PageImpl<>(this.productMapper.queryAllByLimit(product, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param product 实例对象
     * @return 实例对象
     */
    @Override
    public Product insert(Product product) {
        this.productMapper.insert(product);
        return product;
    }

    /**
     * 修改数据
     *
     * @param product 实例对象
     * @return 实例对象
     */
    @Override
    public Product update(Product product) {
        this.productMapper.update(product);
        return this.queryById(product.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.productMapper.deleteById(id) > 0;
    }
}
