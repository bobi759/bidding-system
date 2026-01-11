package BiddingSystem.BiddingSystemRepo.DTO.BidDTO;

import BiddingSystem.BiddingSystemRepo.DTO.UserDTO.AuctionBidderDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class BidDTO {

    private BigDecimal price;

    @JsonIgnore
    private AuctionBidderDTO user;

    private ZonedDateTime createdAt;

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }
}