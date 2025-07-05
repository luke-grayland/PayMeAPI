package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.KYCStatus;
import com.LukeLabs.PayMeAPI.models.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KYCServiceTest {
    private KYCService _sut;

    @BeforeEach
    void setUp() {
        _sut = new KYCService();
    }

    @Test
    public void getKYCStatus_userPreviouslyChecked_returnsSuccess() {
        //Assemble
        int userId = 123;
        ConcurrentHashMap<Integer, Integer> previouslyCheckedUsers = new ConcurrentHashMap<>();
        previouslyCheckedUsers.put(userId, KYCStatus.APPROVED);
        _sut = new KYCService(previouslyCheckedUsers);

        //Act
        Result<Integer> result = _sut.GetKYCStatus(userId);

        //Assert
        assertTrue(result.isSuccess());
        assertEquals(KYCStatus.APPROVED, result.getData());
    }

    @Test
    public void getKYCStatus_newUserNotBlocked_returnsSuccess() {
        //Assemble
        int userId = 686;

        //Act
        Result<Integer> result = _sut.GetKYCStatus(userId);

        //Assert
        assertTrue(result.isSuccess());
        assertEquals(KYCStatus.APPROVED, result.getData());
    }

    @Test
    public void getKYCStatus_userIsBlocked_returnsFailure() {
        //Assemble
        int userId = 789;

        //Act
        Result<Integer> result = _sut.GetKYCStatus(userId);

        //Assert
        assertFalse(result.isSuccess());
        assertNotEquals(null, result.getErrorMessage());
        assertNotEquals("", result.getErrorMessage());
    }

}
