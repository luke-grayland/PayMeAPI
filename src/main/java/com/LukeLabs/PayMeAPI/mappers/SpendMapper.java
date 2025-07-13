package com.LukeLabs.PayMeAPI.mappers;

import com.LukeLabs.PayMeAPI.models.Spend;
import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpendMapper {
    SpendEntity toSpendDocument(Spend spend);
}
