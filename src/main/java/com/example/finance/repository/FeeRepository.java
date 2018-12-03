package com.example.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finance.model.Fee;

/**
 * Fee のRepositoryインタフェースです。
 * 
 * @author Akira Abe
 *
 */
public interface FeeRepository extends JpaRepository<Fee, Long> {

}
