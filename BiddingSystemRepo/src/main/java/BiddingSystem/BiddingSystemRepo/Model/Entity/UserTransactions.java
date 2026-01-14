package BiddingSystem.BiddingSystemRepo.Model.Entity;

import BiddingSystem.BiddingSystemRepo.Model.Enum.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class UserTransactions extends BaseEntity{

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    private BigDecimal amount;

    private BigDecimal fee;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;


}
