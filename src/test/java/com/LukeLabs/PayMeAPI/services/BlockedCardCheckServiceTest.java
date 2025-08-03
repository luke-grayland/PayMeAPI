package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlockedCardCheckServiceTest {
    private CardRepository cardRepository;
    private BlockedCardCheckService sut;

    @BeforeEach
    public void setup() {
        cardRepository = mock(CardRepository.class);
        sut = new BlockedCardCheckService(cardRepository);
    }

    @Test
    public void checkByCardId_cardNotFound_returnsFailure() {
        //Assemble
        UUID cardId = UUID.randomUUID();
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        //Act
        Result<String> result = sut.CheckByCardID(cardId);

        //Assert
        assertFalse(result.isSuccess());
        assertNotEquals("", result.getErrorMessage());
    }

    @Test
    public void checkByCardId_cardIsBlocked_returnsFailure() {
        //Assemble
        Card card = new Card();
        card.setStatus(CardStatusConstants.BLOCKED);

        UUID cardId = UUID.randomUUID();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        //Act
        Result<String> result = sut.CheckByCardID(cardId);

        //Assert
        assertFalse(result.isSuccess());
        assertNotEquals("", result.getErrorMessage());
    }

    @ParameterizedTest
    @CsvSource({
            CardStatusConstants.ACTIVE,
            CardStatusConstants.INACTIVE,
            CardStatusConstants.CANCELLED
    })
    public void checkByCardId_cardIsActive_returnsFailure(String status) {
        //Assemble
        Card card = new Card();
        card.setStatus(status);

        UUID cardId = UUID.randomUUID();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        //Act
        Result<String> result = sut.CheckByCardID(cardId);

        //Assert
        assertTrue(result.isSuccess());
    }
}
