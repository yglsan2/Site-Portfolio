package dev.portfolio.controller;

import dev.portfolio.dto.ProjectDto;
import dev.portfolio.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    void list_returns200AndArray() throws Exception {
        ProjectDto p1 = ProjectDto.builder().id(1L).title("Projet A").slug("projet-a").sortOrder(0).build();
        given(projectService.findAll()).willReturn(List.of(p1));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].slug").value("projet-a"));
    }

    @Test
    void list_whenEmpty_returns200AndEmptyArray() throws Exception {
        given(projectService.findAll()).willReturn(List.of());
        mockMvc.perform(get("/api/projects")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getById_whenExists_returns200() throws Exception {
        ProjectDto dto = ProjectDto.builder().id(10L).title("Mon API").slug("mon-api").sortOrder(0).build();
        given(projectService.findById(10L)).willReturn(Optional.of(dto));

        mockMvc.perform(get("/api/projects/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.slug").value("mon-api"));
    }

    @Test
    void getById_whenNotExists_returns404() throws Exception {
        given(projectService.findById(999L)).willReturn(Optional.empty());
        mockMvc.perform(get("/api/projects/999")).andExpect(status().isNotFound());
    }

    @Test
    void getBySlug_whenExists_returns200() throws Exception {
        ProjectDto dto = ProjectDto.builder().id(5L).title("Barrel").slug("barrelmcd-python").sortOrder(0).build();
        given(projectService.findBySlug("barrelmcd-python")).willReturn(Optional.of(dto));

        mockMvc.perform(get("/api/projects/slug/barrelmcd-python"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("barrelmcd-python"));
    }

    @Test
    void getBySlug_whenNotExists_returns404() throws Exception {
        given(projectService.findBySlug("inconnu")).willReturn(Optional.empty());
        mockMvc.perform(get("/api/projects/slug/inconnu")).andExpect(status().isNotFound());
    }
}
