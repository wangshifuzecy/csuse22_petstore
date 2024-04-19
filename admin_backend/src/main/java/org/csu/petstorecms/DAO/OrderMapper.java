package org.csu.petstorecms.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstorecms.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
