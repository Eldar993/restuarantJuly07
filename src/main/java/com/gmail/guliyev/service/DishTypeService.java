package com.gmail.guliyev.service;

import com.gmail.guliyev.dto.DishTypeDto;
import com.gmail.guliyev.entity.DishType;
import com.gmail.guliyev.repository.DishTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishTypeService {
    private final DishTypeRepository dishTypeRepository;

    public DishTypeService(DishTypeRepository dishTypeRepository) {
        this.dishTypeRepository = dishTypeRepository;
    }

    public boolean create(DishType dishType) {
        if (dishType.getId() != null) {
            return false;
        }
        dishTypeRepository.saveAndFlush(dishType);
        return true;
    }

    public List<DishType> findAll() {
        return dishTypeRepository.findAll();
    }

    public DishType findById(Long id) {
        DishType dishtype = dishTypeRepository.findDishTypeById(id);
        return dishtype;
    }

    public void deleteById(Long id) {
        dishTypeRepository.deleteById(id);
    }

    public DishType update(DishType dishType) {
        Optional<DishType> updatedDishType = dishTypeRepository.findById(dishType.getId());
        if (updatedDishType == null) {
            return null;
        }
        updatedDishType.get().setId(dishType.getId());
        updatedDishType.get().setTitle(dishType.getTitle());

        final DishType result = dishTypeRepository.saveAndFlush(updatedDishType.get());
        return result;
    }


    public static DishTypeDto toDto(DishType entity) {
        if (entity == null) {
            return null;
        }
        DishTypeDto result = new DishTypeDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());

        return result;
    }

    public static List<DishTypeDto> toDto(List<DishType> dishTypes) {
        return dishTypes
                .stream()
                .map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    public static DishType toEntity(DishTypeDto dto) {
        if (dto == null) {
            return null;
        }
        DishType result = new DishType();
        result.setId(dto.getId());
        result.setTitle(dto.getTitle());

        return result;
    }

    public void deleteAll() {
        dishTypeRepository.deleteAll();
    }
}
