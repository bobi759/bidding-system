package BiddingSystem.BiddingSystemRepo.Service;

import BiddingSystem.BiddingSystemRepo.DTO.ItemDTO.RegisterItemDTO;
import BiddingSystem.BiddingSystemRepo.Model.Entity.Auction;
import BiddingSystem.BiddingSystemRepo.Model.Entity.Item;
import BiddingSystem.BiddingSystemRepo.Model.Entity.User;
import BiddingSystem.BiddingSystemRepo.Model.Enum.AuctionStatusEnum;
import BiddingSystem.BiddingSystemRepo.Repository.AuctionRepository;
import BiddingSystem.BiddingSystemRepo.Repository.ItemRepository;
import BiddingSystem.BiddingSystemRepo.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService  {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final AuctionRepository auctionRepository;

    public UserService(UserRepository userRepository, ItemRepository itemRepository, ModelMapper modelMapper, AuctionRepository auctionRepository){
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.auctionRepository = auctionRepository;
    }

    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User getUserByUserId(Long userId){
        return userRepository.findUserById(userId);
    }


    public Item addItem(RegisterItemDTO registerItemDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = getUserByUserId(userId);

        Item newItem = modelMapper.map(registerItemDTO, Item.class);
        newItem.setOwner(user);

        itemRepository.save(newItem);
        return newItem;
    }

    public void addItemToAuction(Long itemId){

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found with id " + itemId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = getUserByUserId(userId);

        Auction auction = new Auction();
        auction.setItem(item);
        auction.setAuctionStatusEnum(AuctionStatusEnum.ACTIVE);
        auction.setOwner(user);

        auctionRepository.save(auction);
    }

}
