package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.handlers.SafeBetResult;
import com.LukeLabs.PayMeAPI.handlers.SpendLimitHandler;
import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpendLimitHandlerTest {
    private SpendLimitHandler sut;

    @BeforeEach
    void setup() {
        sut = new SpendLimitHandler();
    }

    @Test
    public void performCheck_recentSpendCollectionEmpty_blockNotRequired() {
        //Assemble
        List<SpendEntity> recentSpends = new ArrayList<>();

        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertFalse(result.blockIsRequired());
        assertEquals(result.getHandler(), sut);
    }

    @Test
    public void performCheck_recentSpendsDoNotExceedDailySpendLimit_blockIsNotRequired() {
        //Assemble

        SpendEntity spendEntity1 = new SpendEntity();
        spendEntity1.setAmount(100);

        SpendEntity spendEntity2 = new SpendEntity();
        spendEntity2.setAmount(300);

        List<SpendEntity> recentSpends = new ArrayList<>(Arrays.asList(spendEntity1, spendEntity2));

        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertFalse(result.blockIsRequired());
        assertEquals(result.handler(), sut);
    }

    @Test
    public void performCheck_recentSpendsExceedDailySpendLimit_blockIsRequired() {
        //Assemble

        SpendEntity spendEntity1 = new SpendEntity();
        spendEntity1.setAmount(1500);

        SpendEntity spendEntity2 = new SpendEntity();
        spendEntity2.setAmount(1000);

        List<SpendEntity> recentSpends = new ArrayList<>(Arrays.asList(spendEntity1, spendEntity2));

        //Act
        SafeBetResult result = sut.performCheck(recentSpends);

        //Assert
        assertTrue(result.blockIsRequired());
        assertEquals(result.handler(), sut);
    }
}
