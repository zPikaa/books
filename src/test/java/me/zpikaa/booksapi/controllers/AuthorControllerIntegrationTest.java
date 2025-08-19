package me.zpikaa.booksapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.zpikaa.booksapi.TestDataUtil;
import me.zpikaa.booksapi.domain.dto.AuthorDTO;
import me.zpikaa.booksapi.domain.entities.AuthorEntity;
import me.zpikaa.booksapi.services.AuthorService;
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
public class AuthorControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final AuthorService authorService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturnsListOfAuthors() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(authorEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(authorEntity.getAge())
        );
    }

    @Test
    public void testThatFindOneAuthorSuccessfullyReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/%s".formatted(authorEntity.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindOneAuthorSuccessfullyReturnsHttpStatus404WhenAuthorDoesntExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFindOneAuthorSuccessfullyReturnsRequestedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/%s".formatted(authorEntity.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(authorEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntity.getAge())
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttpStatus404WhenAuthorDoesntExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttpStatus200WhenAuthorExistsAndIsUpdated() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        AuthorEntity saved = authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/%s".formatted(saved.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateSuccessfullyUpdatesExistingAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saved = authorService.save(authorEntity);

        AuthorDTO authorDTO = TestDataUtil.createTestAuthorDtoA();
        authorDTO.setId(saved.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/%s".formatted(saved.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(saved.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDTO.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDTO.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHttpStatus200WhenAuthorExistsAndIsUpdated() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saved = authorService.save(authorEntity);

        AuthorDTO authorDTO = TestDataUtil.createTestAuthorDtoA();
        authorDTO.setName("UPDATED");

        String authorJson = objectMapper.writeValueAsString(authorDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/%s".formatted(saved.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saved = authorService.save(authorEntity);

        AuthorDTO authorDTO = TestDataUtil.createTestAuthorDtoA();
        authorDTO.setName("UPDATED");

        String authorJson = objectMapper.writeValueAsString(authorDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/%s".formatted(saved.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(saved.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDTO.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDTO.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorSuccessfullyReturnsHttpStatus204() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedEntity = authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/%s".formatted(savedEntity.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
