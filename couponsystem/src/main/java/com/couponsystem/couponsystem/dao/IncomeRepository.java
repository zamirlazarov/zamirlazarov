package com.couponsystem.couponsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.couponsystem.couponsystem.beans.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

}
