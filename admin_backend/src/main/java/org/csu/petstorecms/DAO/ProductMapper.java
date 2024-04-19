package org.csu.petstorecms.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstorecms.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends BaseMapper<Product> {
}
