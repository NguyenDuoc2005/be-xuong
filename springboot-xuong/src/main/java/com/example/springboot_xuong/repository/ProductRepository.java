package com.example.springboot_xuong.repository;

import com.example.springboot_xuong.request.ProductRequest;
import com.example.springboot_xuong.response.ProductResponse;
import com.example.springboot_xuong.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // hiển thị phân trang
    @Query("SELECT new com.example.springboot_xuong.response.ProductResponse(p.id,pb.brandId,p.productName, b.brandName," +
            " sc.subCateName, p.sellPrice, s.statusName ,p.color,p.quantity,p.originPrice)" +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN p.status s " +
            "JOIN p.productBrands pb " +
            "JOIN pb.brand b order by p.id desc")
    Page<ProductResponse> findAllProductDetails(Pageable pageable);

    // tìm kiếm phân trang
    @Query("SELECT new com.example.springboot_xuong.response.ProductResponse(p.id, pb.brandId, p.productName, b.brandName, " +
            "sc.subCateName, p.sellPrice, s.statusName, p.color, p.quantity, p.originPrice) " +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN sc.category c " +
            "JOIN p.status s " +
            "JOIN p.productBrands pb " +
            "JOIN pb.brand b " +
            "WHERE (:#{#productRequest.name} IS NULL OR p.productName LIKE %:#{#productRequest.name}%) " +
            "AND (:#{#productRequest.price} IS NULL OR p.sellPrice = :#{#productRequest.price}) " +
            "AND (:#{#productRequest.brand} IS NULL OR b.id = :#{#productRequest.brand}) " +
            "AND (:#{#productRequest.category} IS NULL OR c.id = :#{#productRequest.category}) " +
            "AND (:#{#productRequest.status} IS NULL OR s.id = :#{#productRequest.status})")
    Page<ProductResponse> searchProducts(@Param("productRequest") ProductRequest productRequest, Pageable pageable);

}

