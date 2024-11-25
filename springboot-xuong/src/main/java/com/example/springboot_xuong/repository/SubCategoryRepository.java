package com.example.springboot_xuong.repository;

import com.example.springboot_xuong.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findBySubCateName(String subCateName);

    List<SubCategoryProjection> findAllBy();

}
