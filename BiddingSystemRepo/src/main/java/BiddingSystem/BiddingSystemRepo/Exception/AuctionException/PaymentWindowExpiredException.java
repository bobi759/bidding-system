package BiddingSystem.BiddingSystemRepo.Exception.AuctionException;

import BiddingSystem.BiddingSystemRepo.Exception.UserExceptions.BaseCustomException;
import org.springframework.http.HttpStatus;

public class PaymentWindowExpiredException extends BaseCustomException {
    public PaymentWindowExpiredException(String message) {
        super(message, HttpStatus.BAD_REQUEST);

    }
}