package BiddingSystem.BiddingSystemRepo.Controller;

import BiddingSystem.BiddingSystemRepo.Service.AuctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService){
        this.auctionService = auctionService;
    }


    @PostMapping("/addAuction")
    public ResponseEntity<?> addItem(@RequestBody Long itemId){
        auctionService.addItemToAuction(itemId);
        return ResponseEntity.ok("Added successfully");
    }

}
