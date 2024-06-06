package com.ridesigncommunity.RiDesignCommunity.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.matchesPattern;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")

class ProductControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProduct() throws Exception {
        String jsonInput = """
                {
                    "productTitle": "Eikenhouten bed Natura",
                    "images": [
                            "base64_encoded_image_data_1",
                            "base64_encoded_image_data_2"
                            ],
                    "category": "Bedden",
                    "measurements": "180x200x50 cm",
                    "materials": "Eikenhout",
                    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.",
                    "price": 400.00,
                    "username": "RiDumoulin",
                    "deliveryOptions": [
                        "Bezorgen",
                        "Ophalen"
                    ],
                }
                """;

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String createdID = result.getResponse().getContentAsString();
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/products/" + createdID));

    }
}