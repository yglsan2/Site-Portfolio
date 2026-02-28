package dev.portfolio.controller;

import dev.portfolio.dto.CodeSnippetDto;
import dev.portfolio.service.CodeSnippetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CodeSnippetController.class)
@AutoConfigureMockMvc(addFilters = false)
class CodeSnippetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CodeSnippetService codeSnippetService;

    @Test
    void listByProject_returns200AndArray() throws Exception {
        CodeSnippetDto s1 = CodeSnippetDto.builder().id(1L).projectId(10L).title("Snippet 1").slug("s1").language("java").sortOrder(0).build();
        given(codeSnippetService.findByProjectId(10L)).willReturn(List.of(s1));

        mockMvc.perform(get("/api/snippets/project/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].projectId").value(10))
                .andExpect(jsonPath("$[0].slug").value("s1"));
    }

    @Test
    void listByProject_whenEmpty_returns200AndEmptyArray() throws Exception {
        given(codeSnippetService.findByProjectId(99L)).willReturn(List.of());

        mockMvc.perform(get("/api/snippets/project/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
