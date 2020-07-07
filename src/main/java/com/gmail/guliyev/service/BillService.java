package com.gmail.guliyev.service;


import com.gmail.guliyev.dto.BillDto;
import com.gmail.guliyev.entity.Bill;
import com.gmail.guliyev.repository.BillRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public boolean create(Bill bill) {
        if (bill.getId() != null) {
            return false;
        }
        billRepository.saveAndFlush(bill);
        return true;
    }

    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    public Bill findById(Long id) {
        Bill bill = billRepository.findBillById(id);
        return bill;
    }

    public void deleteById(Long id) {
        billRepository.deleteById(id);
    }

    public Bill update(Bill bill) {
        Optional<Bill> updatedBill = billRepository.findById(bill.getId());
        if (updatedBill == null) {
            return null;
        }
        updatedBill.get().setId(bill.getId());
        updatedBill.get().setOrder(bill.getOrder());
        updatedBill.get().setCompletedAt(bill.getCompletedAt());
        updatedBill.get().setPaidSum(bill.getPaidSum());

        final Bill result = billRepository.saveAndFlush(updatedBill.get());
        return result;
    }

    public static BillDto toDto(Bill entity) {
        if (entity == null) {
            return null;
        }
        BillDto result = new BillDto();
        result.setId(entity.getId());
        result.setOrder(entity.getOrder());
        result.setCompletedAt(entity.getCompletedAt());
        result.setPaidSum(entity.getPaidSum());

        return result;
    }

    public static List<BillDto> toDto(List<Bill> bills) {
        return bills
                .stream()
                .map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    public static Bill toEntity(BillDto dto) {
        if (dto == null) {
            return null;
        }
        Bill result = new Bill();
        result.setId(dto.getId());
        result.setOrder(dto.getOrder());
        result.setCompletedAt(dto.getCompletedAt());
        result.setPaidSum(dto.getPaidSum());
        return result;
    }

}
