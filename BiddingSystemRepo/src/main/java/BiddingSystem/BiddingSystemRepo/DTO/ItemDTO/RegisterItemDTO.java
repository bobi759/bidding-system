package BiddingSystem.BiddingSystemRepo.DTO.ItemDTO;

import BiddingSystem.BiddingSystemRepo.Model.Entity.User;
import BiddingSystem.BiddingSystemRepo.Model.Enum.ItemCategoryEnum;
import BiddingSystem.BiddingSystemRepo.Model.Enum.ItemConditionEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterItemDTO {

    private String name;

    private String description;

    private ItemCategoryEnum itemCategoryEnum;

    private ItemConditionEnum itemConditionEnum;

}
