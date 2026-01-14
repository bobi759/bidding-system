package BiddingSystem.BiddingSystemRepo.DTO.PaymentDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SuccessfulDepositWithdrawDTO {

    private BigDecimal newBalanceBeforeFee;

    private BigDecimal newBalanceAfterFee;

    private BigDecimal fee;

    private String message;

}
