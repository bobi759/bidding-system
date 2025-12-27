package BiddingSystem.BiddingSystemRepo.Service;

import BiddingSystem.BiddingSystemRepo.Model.Entity.Auction;
import BiddingSystem.BiddingSystemRepo.Model.Entity.Item;
import BiddingSystem.BiddingSystemRepo.Model.Entity.User;
import BiddingSystem.BiddingSystemRepo.Model.Enum.AuctionStatusEnum;
import BiddingSystem.BiddingSystemRepo.Repository.AuctionRepository;
import BiddingSystem.BiddingSystemRepo.Repository.ItemRepository;
import BiddingSystem.BiddingSystemRepo.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    private final ItemRepository itemRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public AuctionService(ItemRepository itemRepository, AuctionRepository auctionRepository, UserRepository userRepository){
        this.itemRepository = itemRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }


    public void addItemToAuction(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found with id " + itemId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findUserById(userId);

        Auction auction = new Auction();
        auction.setItem(item);
        auction.setAuctionStatusEnum(AuctionStatusEnum.ACTIVE);
        auction.setOwner(user);

        auctionRepository.save(auction);
    }


}
