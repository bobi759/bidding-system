package BiddingSystem.BiddingSystemRepo;


import BiddingSystem.BiddingSystemRepo.Model.Entity.User;
import BiddingSystem.BiddingSystemRepo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
// Give me Spring but for testing (set it up the same way)
@AutoConfigureMockMvc
// Create fake HTTP client that will send requests
public class BaseTestClass {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("kacoLudiq@abv.bg");
        user.setPassword(passwordEncoder.encode("ivoIstinata"));

        userRepository.save(user);
    }

}
