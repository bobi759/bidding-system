package BiddingSystem.BiddingSystemRepo.Controller;


import BiddingSystem.BiddingSystemRepo.DTO.ItemDTO.RegisterItemDTO;
import BiddingSystem.BiddingSystemRepo.Response.UserResponseDTO.CreateItemResponseDTO;
import BiddingSystem.BiddingSystemRepo.Service.ItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/item")
@SecurityRequirement(name = "authorization")
@Validated

// TODO: CRUD OPERATIONS ABOUT ITEM
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addItem(@Valid @RequestBody RegisterItemDTO registerItemDTO) throws Exception {
        CreateItemResponseDTO response = itemService.addItem(registerItemDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUserItems(){
        return ResponseEntity.ok(itemService.getAllItems());
    }


}
