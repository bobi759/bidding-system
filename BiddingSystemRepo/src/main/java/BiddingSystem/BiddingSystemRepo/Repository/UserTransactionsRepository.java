package BiddingSystem.BiddingSystemRepo.Repository;

import BiddingSystem.BiddingSystemRepo.Model.Entity.UserTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTransactionsRepository extends JpaRepository<UserTransactions, Long> {

    List<UserTransactions> findByUser_Id(Long userId);

}
