package BiddingSystem.BiddingSystemRepo.Controller;

import BiddingSystem.BiddingSystemRepo.DTO.BidDTO.CreateBidDTO;
import BiddingSystem.BiddingSystemRepo.DTO.BidDTO.CreateBidInput;
import BiddingSystem.BiddingSystemRepo.DTO.PaymentDTO.SuccessfulDepositWithdrawDTO;
import BiddingSystem.BiddingSystemRepo.Model.Entity.SystemBalance;
import BiddingSystem.BiddingSystemRepo.Service.SystemBalanceService;
import BiddingSystem.BiddingSystemRepo.Service.UserBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Tag(
        name = "User Balance",
        description = "Operations related to user balance management such as deposit, withdrawal, and balance viewing. **Disclaimer:** operations beside view balance apply a fee."
)
@RestController
@RequestMapping("/api/v1/user/balance")
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    public UserBalanceController(UserBalanceService userBalanceService) {
        this.userBalanceService = userBalanceService;
    }

    @Operation(
            summary = "Deposit funds into your account"
    )
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam BigDecimal amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        SuccessfulDepositWithdrawDTO dto = userBalanceService.deposit(userId, amount);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Withdraw funds from your account"
    )
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam BigDecimal amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        SuccessfulDepositWithdrawDTO dto = userBalanceService.withdraw(userId, amount);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "View user's balance"

    )
    @GetMapping
    public ResponseEntity<?> viewBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        BigDecimal userBalance = userBalanceService.viewBalance(userId);
        return ResponseEntity.ok("User balance: " + userBalance);
    }

    @Operation(
            summary = "View transaction history",
            description = "Returns deposit and withdrawal transactions for the currently logged-in user"
    )
    @GetMapping("/transactions")
    public ResponseEntity<?> viewMyTransactions() {

        return ResponseEntity.ok(
                userBalanceService.getMyTransactions()
        );
    }


}
