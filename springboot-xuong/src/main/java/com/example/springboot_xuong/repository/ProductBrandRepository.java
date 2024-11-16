package com.example.springboot_xuong.repository;

import com.example.springboot_xuong.entity.ProductBrand;
import com.example.springboot_xuong.entity.ProductBrandId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, ProductBrandId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductBrand pb WHERE pb.productId = :productId AND pb.brandId = :brandId")
    void deleteByProductIdAndBrandId(@Param("productId") Long productId, @Param("brandId") Long brandId);

    boolean existsByProductId(Long productId);
}
