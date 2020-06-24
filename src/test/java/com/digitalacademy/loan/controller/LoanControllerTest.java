package com.digitalacademy.loan.controller;


import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import com.digitalacademy.loan.service.LoanService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private MockMvc mvc;

    private static final String urlLoan = "/loan/info/";

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanService);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info by Id equal 1 should return loan")
    @Test
    void TestGetLoanInfoByIdEquals1() throws Exception{
        LoanInfoModel loanInfoModel = new LoanInfoModel();
        loanInfoModel.setId(1L);
        loanInfoModel.setStatus("Ok");
        loanInfoModel.setAccountPayable("101-220-2200");
        loanInfoModel.setAccountReceivable("101-220-2200");
        loanInfoModel.setPrincipleAmount(400000.00);

        when(loanService.getLoanInfoById(1L)).thenReturn(loanInfoModel);

        MvcResult mvcResult= mvc.perform(get(urlLoan + ""+ 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp= new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status= new JSONObject(resp.getString("status"));
        JSONObject data= new JSONObject(resp.getString("data"));

        assertEquals("0",status.get("code").toString());
        assertEquals("success",status.get("message").toString());
        assertEquals(1,data.get("id"));
        assertEquals("Ok",data.get("status"));
        assertEquals("101-220-2200",data.get("account_payable"));
        assertEquals("101-220-2200",data.get("account_receivable"));
        assertEquals(400000,data.get("principle_amount"));
    }

    @DisplayName("Test get loan info by Id equal 2 should Throw Loan Exception ")
    @Test
    void TestGetLoanInfoByIdEquals2() throws Exception{
        Long loanParam = 2L;
        when(loanService.getLoanInfoById(loanParam)).thenThrow(new LoanException(LoanError.GET_LOAN_INFO_NOT_FOUND, HttpStatus.NOT_FOUND));

        MvcResult mvcResult= mvc.perform(get(urlLoan + ""+ loanParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp= new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status= new JSONObject(resp.getString("status"));

        assertEquals("LOAN002",status.get("code").toString());
        assertEquals("Loan information not found.",status.get("message").toString());
    }

    @DisplayName("Test get loan info by Id equal 3 should Throw Exception ")
    @Test
    void TestGetLoanInfoByIdEquals3() throws Exception{
        Long loanParam = 3L;
        when(loanService.getLoanInfoById(loanParam)).thenThrow(new Exception("Test throw new Exception"));

        MvcResult mvcResult= mvc.perform(get(urlLoan + ""+ loanParam))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp= new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status= new JSONObject(resp.getString("status"));

        assertEquals("LOAN001",status.get("code").toString());
        assertEquals("cannot get loan information.",status.get("message").toString());
    }
}
