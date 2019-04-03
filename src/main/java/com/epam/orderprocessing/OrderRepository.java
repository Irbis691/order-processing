package com.epam.orderprocessing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByPropertyNameContaining(String propertyName, Pageable pageable);

    Page<Order> findByBorrowerNameContaining(String borrowerName, Pageable pageable);

    Page<Order> findByProductTypeContaining(String productType, Pageable pageable);

    long countByPropertyNameContaining(String propertyName);

    long countByBorrowerNameContaining(String borrowerName);

    long countByProductTypeContaining(String productType);
}