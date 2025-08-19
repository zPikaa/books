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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final BookService bookService;
    private final ObjectMapper objectMapper;

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
    public void testThatUpdateBookSuccessfullyReturnsHttp200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity saved = bookService.save(bookEntity.getIsbn(), bookEntity);

        BookDTO bookDTO = TestDataUtil.createTestBookDtoA(null);
        bookDTO.setIsbn(saved.getIsbn());

        String bookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/%s".formatted(bookDTO.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
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
    public void testThatUpdateBookSuccessfullyReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity saved = bookService.save(bookEntity.getIsbn(), bookEntity);

        BookDTO bookDTO = TestDataUtil.createTestBookDtoA(null);
        bookDTO.setIsbn(saved.getIsbn());
        bookDTO.setTitle("Updated");

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
        bookService.save(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(bookEntity.getTitle())
        );
    }

    @Test
    public void testThatFindOneBookSuccessfullyReturnsHttpStatus200WhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/%s".formatted(bookEntity.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindOneBookSuccessfullyReturnsHttpStatus404WhenBookDoesntExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/not-existing-book-isbn")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsHttpStatus200() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity.getIsbn(), bookEntity);

        BookDTO bookDTO = TestDataUtil.createTestBookDtoA(null);
        bookDTO.setTitle("Updated");
        String bookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/%s".formatted(bookEntity.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.save(bookEntity.getIsbn(), bookEntity);

        BookDTO bookDTO = TestDataUtil.createTestBookDtoA(null);
        bookDTO.setTitle("Updated");
        String bookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/%s".formatted(bookEntity.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDTO.getTitle())
        );
    }

    @Test
    public void testThatDeleteBookSuccessfullyReturnsHttpStatus204() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedEntity = bookService.save(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/%s".formatted(savedEntity.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
