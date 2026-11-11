package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tools.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockMvcTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void GET_book_returns_all_books() throws Exception {
        MvcResult result = mvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertThat(books).hasSize(3);
        assertThat(books.get(1).getTitle()).isEqualTo("Clean Code");
    }

    @TestConfiguration
    static class JsonTestConfiguration {

        @Bean
        JsonMapperBuilderCustomizer jsonMapperIndentCustomizer() {
            return builder -> builder.enable(INDENT_OUTPUT);
        }
    }
}
