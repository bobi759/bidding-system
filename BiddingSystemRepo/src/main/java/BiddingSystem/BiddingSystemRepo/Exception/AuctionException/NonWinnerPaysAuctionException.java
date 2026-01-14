package BiddingSystem.BiddingSystemRepo.Exception.AuctionException;

import BiddingSystem.BiddingSystemRepo.Exception.UserExceptions.BaseCustomException;
import org.springframework.http.HttpStatus;

public class NonWinnerPaysAuctionException extends BaseCustomException {
    public NonWinnerPaysAuctionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);

    }
}