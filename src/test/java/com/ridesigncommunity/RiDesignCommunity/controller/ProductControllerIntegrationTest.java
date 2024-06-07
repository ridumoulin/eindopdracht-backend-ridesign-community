package com.ridesigncommunity.RiDesignCommunity.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Any setup code if necessary
    }

    @AfterEach
    void tearDown() {
        // Any teardown code if necessary
    }

    @Test
    void createProduct() throws Exception {
        String jsonInput = """
                {
                    "productTitle": "Eikenhouten bed Natura",
                    "images": [],
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
                    "productId": 7
                }
                """;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        int createdId = jsonNode.get("productId").asInt();

        String locationHeader = result.getResponse().getHeader("Location");
        assertThat("Location header should match the expected URL pattern",
                locationHeader, matchesPattern(".*/products/" + createdId));
    }
}

