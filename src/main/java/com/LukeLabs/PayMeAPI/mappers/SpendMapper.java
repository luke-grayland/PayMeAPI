package com.LukeLabs.PayMeAPI.mappers;

import com.LukeLabs.PayMeAPI.models.Spend;
import com.LukeLabs.PayMeAPI.models.documents.SpendDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpendMapper {
    SpendDocument toSpendDocument(Spend spend);
}
