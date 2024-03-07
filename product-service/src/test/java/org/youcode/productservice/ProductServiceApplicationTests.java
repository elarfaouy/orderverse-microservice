package org.youcode.productservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Disabled
class ProductServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Container
    static PostgreSQLContainer<?> pgvector = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", pgvector::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", pgvector::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", pgvector::getPassword);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                .contentType("application/json")
                .content("{\"name\": \"Product 1\", \"description\": \"Description 1\", \"price\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

}
