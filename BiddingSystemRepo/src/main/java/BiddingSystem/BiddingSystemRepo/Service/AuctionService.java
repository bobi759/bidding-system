package BiddingSystem.BiddingSystemRepo.Service;

import BiddingSystem.BiddingSystemRepo.DTO.AuctionDTO.AddItemToAuctionDTO;
import BiddingSystem.BiddingSystemRepo.DTO.AuctionDTO.CreateAuctionInput;
import BiddingSystem.BiddingSystemRepo.Exception.AuctionException.AuctionNotFound;
import BiddingSystem.BiddingSystemRepo.Exception.AuctionException.ItemAlreadyInAuction;
import BiddingSystem.BiddingSystemRepo.Exception.ItemExceptions.ItemNotFound;
import BiddingSystem.BiddingSystemRepo.Exception.UserExceptions.UserNotFoundException;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class AuctionService {

    private final ItemRepository itemRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public AuctionService(ItemRepository itemRepository, AuctionRepository auctionRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    public void createAuction(CreateAuctionInput input) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("No such user!"));

        Item Item = itemRepository.findByIdAndOwnerId(input.getItemId(), userId).orElseThrow(() -> new ItemNotFound("Item not found with id " + input.getItemId()));

        if (auctionRepository.existsByItemIdAndOwnerIdAndAuctionStatusEnum(input.getItemId(), userId, AuctionStatusEnum.ACTIVE) ||
                auctionRepository.existsByItemIdAndOwnerIdAndAuctionStatusEnum(input.getItemId(), userId, AuctionStatusEnum.SCHEDULED)) {
            throw new ItemAlreadyInAuction("Current item already in active auction");
        }

        ZonedDateTime startTime =
                (input.getStartingAt() != null)
                        ? input.getStartingAt()
                        : ZonedDateTime.now();

        AuctionStatusEnum initialStatus =
                startTime.isAfter(ZonedDateTime.now())
                        ? AuctionStatusEnum.SCHEDULED
                        : AuctionStatusEnum.ACTIVE;

        Duration auctionDuration =
                (input.getDuration() != null)
                        ? input.getDuration()
                        : Duration.ofDays(1);


        Auction auction = new Auction();
        auction.setItem(Item);
        auction.setOwner(user);
        auction.setStartingAt(startTime);
        auction.setReservePrice(input.getReservePrice());
        auction.setStartingPrice(input.getStartingPrice());
        auction.setAuctionDuration(auctionDuration);
        auction.setAuctionStatusEnum(initialStatus);

        auctionRepository.save(auction);
    }


    //    TODO: Extend the make publish logic
    public void makePublish(Long auctionId) throws Exception {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFound("Item not found with id " + auctionId));

        if (auction.getAuctionStatusEnum() != AuctionStatusEnum.DRAFT) {
            throw new Exception("Invalid change of auction status");
        }

        auction.setAuctionStatusEnum(AuctionStatusEnum.ACTIVE);
        auctionRepository.save(auction);
    }

}
