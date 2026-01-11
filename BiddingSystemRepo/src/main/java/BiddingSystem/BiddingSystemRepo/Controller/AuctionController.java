package BiddingSystem.BiddingSystemRepo.Controller;

import BiddingSystem.BiddingSystemRepo.DTO.AuctionDTO.AddItemToAuctionDTO;
import BiddingSystem.BiddingSystemRepo.DTO.AuctionDTO.CreateAuctionInput;
import BiddingSystem.BiddingSystemRepo.DTO.AuctionDTO.ExposeAuctionDTO;
import BiddingSystem.BiddingSystemRepo.DTO.AuctionDTO.MakePaymentDTO;
import BiddingSystem.BiddingSystemRepo.Model.Entity.Auction;
import BiddingSystem.BiddingSystemRepo.Service.AuctionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addItem(
            @RequestBody @Valid AddItemToAuctionDTO addItemToAuctionDTO
    ) {

        CreateAuctionInput input = new CreateAuctionInput(
                addItemToAuctionDTO.getItemId(),
                addItemToAuctionDTO.getStartingAt(),
                addItemToAuctionDTO.getAuctionDuration(),
                addItemToAuctionDTO.getStartingPrice(),
                addItemToAuctionDTO.getReservePrice()
        );
        auctionService.createAuction(input);
        return ResponseEntity.ok("Added successfully");
    }

    @Operation(
            summary = "List all bids",
            description = "Returns a list of all auctions with item, owner and bid history info"
    )
    @GetMapping("/")
    public ResponseEntity<?> showAllAuctions(){
        List<ExposeAuctionDTO> auctionList = auctionService.showAllAuctions();
        return ResponseEntity.ok(auctionList);
    }

    public ResponseEntity<?> makeAuctionPayment(@RequestBody MakePaymentDTO makePaymentDTO){
        auctionService.makePayment(makePaymentDTO.getAuctionId());
        return ResponseEntity.ok("Payment Successfully");
    }

}
