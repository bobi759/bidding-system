package BiddingSystem.BiddingSystemRepo.Controller;


import BiddingSystem.BiddingSystemRepo.Service.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }




}
