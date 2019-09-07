package com.javasea.orm.springdata.multidatasource.repository.backup;

import com.javasea.orm.springdata.multidatasource.entity.backup.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author F嘉阳
 * @date 2019-03-30 9:12
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
