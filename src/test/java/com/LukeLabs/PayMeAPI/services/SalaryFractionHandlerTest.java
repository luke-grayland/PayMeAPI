package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.handlers.SafeBetResult;
import com.LukeLabs.PayMeAPI.handlers.SalaryFractionHandler;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.entities.KYCProfileEntity;
import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;
import com.LukeLabs.PayMeAPI.repositories.KYCProfileRepository;
import com.LukeLabs.PayMeAPI.utilities.errorHandling.CardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SalaryFractionHandlerTest {
    private SalaryFractionHandler sut;
    private KYCProfileRepository kycProfileRepositoryMock;
    private CardRepository cardRepositoryMock;
    private final List<SpendEntity> recentSpends = new ArrayList<>();
    private final int userId = 123;
    private Card card;
    private KYCProfileEntity kycProfileEntity;

    @BeforeEach
    public void setup() {
        card = new Card();
        card.setUserID(userId);

        kycProfileEntity = new KYCProfileEntity();
        kycProfileEntity.setMonthlyIncome(2000);

        kycProfileRepositoryMock = mock(KYCProfileRepository.class);
        cardRepositoryMock = mock(CardRepository.class);

        sut = new SalaryFractionHandler(kycProfileRepositoryMock, cardRepositoryMock);
    }

    @Test
    public void performCheck_recentSpendListEmpty_blockNotRequired() {
        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertFalse(result.blockIsRequired());
        assertEquals(sut, result.handler());
    }
    
    @Test
    public void performCheck_cardNotFound_exceptionIsThrown() {
        //Assemble
        AddSpendToRecentSpends(100);

        //Act & Assert
        assertThrows(CardNotFoundException.class, () -> {
            sut.performCheck(recentSpends);
        });
    }

    @Test
    public void performCheck_kycProfileNotFound_blockNotRequired() {
        //Assemble
        AddSpendToRecentSpends(100);
        AddSpendToRecentSpends(200);
        when(cardRepositoryMock.findById(recentSpends.getFirst().getCardId())).thenReturn(Optional.of(card));
        when(kycProfileRepositoryMock.findByUserId(userId)).thenReturn(Optional.empty());

        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertFalse(result.blockIsRequired());
        assertEquals(sut, result.handler());
    }

    @Test
    public void performCheck_salaryFractionNotExceeded_blockNotRequired() {
        //Assemble
        AddSpendToRecentSpends(100);
        AddSpendToRecentSpends(200);
        when(cardRepositoryMock.findById(recentSpends.getFirst().getCardId())).thenReturn(Optional.of(card));
        when(kycProfileRepositoryMock.findByUserId(userId)).thenReturn(Optional.of(kycProfileEntity));

        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertFalse(result.blockIsRequired());
        assertEquals(sut, result.handler());
    }

    @Test
    public void performCheck_salaryFractionExceeded_blockIsRequired() {
        //Assemble
        AddSpendToRecentSpends(300);
        AddSpendToRecentSpends(400);
        when(cardRepositoryMock.findById(recentSpends.getFirst().getCardId())).thenReturn(Optional.of(card));
        when(kycProfileRepositoryMock.findByUserId(userId)).thenReturn(Optional.of(kycProfileEntity));

        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertTrue(result.blockIsRequired());
        assertEquals(sut, result.handler());
    }

    private void AddSpendToRecentSpends(int amount) {
        SpendEntity spend = new SpendEntity();
        UUID cardId = UUID.randomUUID();

        spend.setAmount(amount);
        spend.setCardId(cardId);

        recentSpends.add(spend);
    }
}
