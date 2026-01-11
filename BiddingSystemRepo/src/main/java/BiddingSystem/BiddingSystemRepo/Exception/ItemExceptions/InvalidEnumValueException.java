package BiddingSystem.BiddingSystemRepo.Exception.ItemExceptions;

import BiddingSystem.BiddingSystemRepo.Exception.UserExceptions.BaseCustomException;
import org.springframework.http.HttpStatus;

public class InvalidEnumValueException extends BaseCustomException {

    public InvalidEnumValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
