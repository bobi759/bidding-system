package BiddingSystem.BiddingSystemRepo.DTO.UserDTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterResponseDTO {

    private String username;

    private String email;

    private int age;
}
