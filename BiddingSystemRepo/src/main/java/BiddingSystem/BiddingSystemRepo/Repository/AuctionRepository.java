package BiddingSystem.BiddingSystemRepo.Repository;

import BiddingSystem.BiddingSystemRepo.Model.Entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction,Long> {


}
