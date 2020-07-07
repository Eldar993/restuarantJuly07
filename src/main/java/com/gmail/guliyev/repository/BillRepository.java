package com.gmail.guliyev.repository;

import com.gmail.guliyev.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Long> {
    Bill findBillById(Long id);
}
