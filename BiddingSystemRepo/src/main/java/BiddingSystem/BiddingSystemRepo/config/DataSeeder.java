package BiddingSystem.BiddingSystemRepo.config;

import BiddingSystem.BiddingSystemRepo.Model.Entity.User;
import BiddingSystem.BiddingSystemRepo.Repository.AuctionRepository;
import BiddingSystem.BiddingSystemRepo.Repository.ItemRepository;
import BiddingSystem.BiddingSystemRepo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSeeder {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, AuctionRepository auctionRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
        this.auctionRepository = auctionRepository;
    }

    @Bean
    CommandLineRunner initDatabase(
    ) {
        return args -> {

            User user1 = new User();
            user1.setEmail("user1@gmail.com");
            user1.setUsername("theBigFish");
            user1.setPassword(passwordEncoder.encode("StrongPass123"));
            user1.setAge(30);


            User user2 = new User();
            user2.setEmail("user2@gmail.com");
            user2.setUsername("theSmallFish");
            user2.setPassword(passwordEncoder.encode("WeakPass987"));
            user2.setAge(33);

            userRepository.save(user1);
            userRepository.save(user2);



        };
    }
}
