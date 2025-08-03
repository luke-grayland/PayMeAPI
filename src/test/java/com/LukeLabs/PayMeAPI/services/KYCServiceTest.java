package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.KYCStatus;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.utilities.cache.LimitedCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KYCServiceTest {
    private KYCService sut;

    @BeforeEach
    void setup() {
        sut = new KYCService();
    }

    @Test
    public void getKYCStatus_userPreviouslyChecked_returnsSuccess() {
        //Assemble
        int userId = 123;
        LimitedCache<Integer, Integer> previouslyCheckedUsers = new LimitedCache<>(1000);
        previouslyCheckedUsers.put(userId, KYCStatus.APPROVED);
        sut = new KYCService(previouslyCheckedUsers);

        //Act
        Result<Integer> result = sut.GetKYCStatus(userId);

        //Assert
        assertTrue(result.isSuccess());
        assertEquals(KYCStatus.APPROVED, result.getData());
    }

    @Test
    public void getKYCStatus_newUserNotBlocked_returnsSuccess() {
        //Assemble
        int userId = 686;

        //Act
        Result<Integer> result = sut.GetKYCStatus(userId);

        //Assert
        assertTrue(result.isSuccess());
        assertEquals(KYCStatus.APPROVED, result.getData());
    }

    @Test
    public void getKYCStatus_userIsBlocked_returnsFailure() {
        //Assemble
        int userId = 789;

        //Act
        Result<Integer> result = sut.GetKYCStatus(userId);

        //Assert
        assertFalse(result.isSuccess());
        assertNotEquals(null, result.getErrorMessage());
        assertNotEquals("", result.getErrorMessage());
    }

}
