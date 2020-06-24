package com.digitalacademy.loan.service;

import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private static final Logger log = LogManager.getLogger(LoanService.class.getName());

    public LoanInfoModel getLoanInfoById(Long id) throws Exception{
        LoanInfoModel loanInfoModel= new LoanInfoModel();
        log.info("Get loan info by Id: {}", id);
        if(id.equals(1L)){
            loanInfoModel.setId(1L);
            loanInfoModel.setStatus("Ok");
            loanInfoModel.setAccountPayable("101-220-2200");
            loanInfoModel.setAccountReceivable("101-220-2200");
            loanInfoModel.setPrincipleAmount(400000.00);
        }else if(id.equals(2L)){
            log.error("id: {}",id);
            throw new LoanException(
                    LoanError.GET_LOAN_INFO_NOT_FOUND,
                    HttpStatus.BAD_REQUEST
            );
        }
        else{
            log.error("id: {}",id);
            throw new Exception("Test throw new Exception");
        }
        return loanInfoModel;
    }
}

