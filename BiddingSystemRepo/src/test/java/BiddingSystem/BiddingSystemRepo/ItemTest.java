package BiddingSystem.BiddingSystemRepo;

import BiddingSystem.BiddingSystemRepo.Model.Entity.User;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// API Integration tests
public class ItemTest extends BaseTestClass {


    protected String token;

    @BeforeEach
    void login() throws Exception {

        String response = mockMvc.perform(
                        post("/api/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                            {
                                              "email": "kacoLudiq@abv.bg",
                                              "password": "ivoIstinata"
                                            }
                                        """)
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        token = JsonPath.read(response, "$.token");
    }


    @Test
    void addingItem_shouldFail_whenUserAddItemsWithSameName() throws Exception {
        mockMvc.perform(
                        post("/api/v1/item/addItem")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "name": "Picture2",
                                                    "description": "best picturefff ever trust",
                                                    "itemCategoryEnum": "ENTERTAINMENT_AND_MEDIA",
                                                    "itemConditionEnum": "VINTAGE"
                                                }
                                        """)
                )
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                        post("/api/v1/item/addItem")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "name": "Picture2",
                                                    "description": "best picturefff ever trust",
                                                    "itemCategoryEnum": "ENTERTAINMENT_AND_MEDIA",
                                                    "itemConditionEnum": "VINTAGE"
                                                }
                                        """)
                )
                .andExpect(status().is4xxClientError());
    }


}
