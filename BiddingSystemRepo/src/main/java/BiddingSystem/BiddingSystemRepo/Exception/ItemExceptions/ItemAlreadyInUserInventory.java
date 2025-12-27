package BiddingSystem.BiddingSystemRepo.Exception.ItemExceptions;

import BiddingSystem.BiddingSystemRepo.Exception.UserExceptions.BaseCustomException;
import org.springframework.http.HttpStatus;

public class ItemAlreadyInUserInventory extends BaseCustomException {
    public ItemAlreadyInUserInventory(String message) {
        super(message, HttpStatus.BAD_REQUEST);

    }
}
