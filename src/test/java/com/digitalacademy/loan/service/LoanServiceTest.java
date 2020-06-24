package com.digitalacademy.loan.service;


import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanServiceTest {

    @InjectMocks
    LoanService loanService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService();
    }

    @DisplayName("Test get loan info by Id equal 1")
    @Test
    void TestGetLoanInfoByIdEquals1() throws Exception{
        LoanInfoModel loanInfoModel = loanService.getLoanInfoById(1L);
        assertEquals("1",loanInfoModel.getId().toString());
        assertEquals("Ok",loanInfoModel.getStatus());
        assertEquals("101-220-2200",loanInfoModel.getAccountPayable());
        assertEquals("101-220-2200",loanInfoModel.getAccountReceivable());
        assertEquals(400000.00,loanInfoModel.getPrincipleAmount());
    }

    @DisplayName("Test get loan info by Id equal 2 should throw loan exception info not found")
    @Test
    void TestGetLoanInfoByIdEquals2() throws Exception{
        Long reqParam = 2L;
        LoanException thrown = assertThrows(LoanException.class,
                ()->loanService.getLoanInfoById(reqParam),"Expected loanInfoById(reqParam) to throw, but it didn't");

        assertEquals(400,thrown.getHttpStatus().value());
        assertEquals("LOAN002",thrown.getLoanError().getCode());
        assertEquals("Loan infomation not found.",thrown.getLoanError().getMessage());
    }

    @DisplayName("Test get loan info by Id equal 3 should throw exception :Test throw new Exception")
    @Test
    void TestGetLoanInfoByIdEquals3() throws Exception{
        Long reqParam = 3L;
        Exception thrown = assertThrows(Exception.class,
                ()->loanService.getLoanInfoById(reqParam),"Expected loanInfoById(reqParam) to throw, but it didn't");

        assertEquals("Test throw new Exception",thrown.getMessage());
    }
}
