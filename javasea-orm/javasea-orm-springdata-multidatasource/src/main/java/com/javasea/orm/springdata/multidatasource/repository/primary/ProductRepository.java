package com.javasea.orm.springdata.multidatasource.repository.primary;

import com.javasea.orm.springdata.multidatasource.entity.primary.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author F嘉阳
 * @date 2019-03-30 9:12
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
