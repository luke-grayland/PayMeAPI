package com.LukeLabs.PayMeAPI.mappers;

import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.DTOs.CardDTO;
import com.LukeLabs.PayMeAPI.models.ProvisionedCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toCard(ProvisionedCard provisionedCard);
    CardDTO toCardDTO(Card card);
}
