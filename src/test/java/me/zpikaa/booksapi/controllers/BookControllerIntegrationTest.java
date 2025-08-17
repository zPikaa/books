package me.zpikaa.booksapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zpikaa.booksapi.TestDataUtil;
import me.zpikaa.booksapi.domain.dto.BookDTO;
import me.zpikaa.booksapi.domain.entities.BookEntity;
import me.zpikaa.booksapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private MockMvc mockMvc;
    private BookService bookService;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDTO bookDTO = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/%s".formatted(bookDTO.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDTO bookDTO = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/%s".formatted(bookDTO.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDTO.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDTO.getTitle())
        );
    }

    @Test
    public void testThatListBooksSuccessfullyReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksSuccessfullyReturnsListOfBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(bookEntity.getTitle())
        );
    }

}
