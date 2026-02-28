package dev.portfolio.controller;

import dev.portfolio.dto.ProfileDto;
import dev.portfolio.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    void get_whenProfileExists_returns200AndBody() throws Exception {
        ProfileDto dto = ProfileDto.builder()
                .id(1L)
                .name("Jane Dev")
                .title("Dev")
                .githubUrl("https://github.com/jane")
                .build();
        given(profileService.findDefault()).willReturn(Optional.of(dto));

        mockMvc.perform(get("/api/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Dev"))
                .andExpect(jsonPath("$.githubUrl").value("https://github.com/jane"));
    }

    @Test
    void get_whenNoProfile_returns404() throws Exception {
        given(profileService.findDefault()).willReturn(Optional.empty());
        mockMvc.perform(get("/api/profile")).andExpect(status().isNotFound());
    }

    @Test
    void get_whenServiceThrows_returns500() throws Exception {
        given(profileService.findDefault()).willThrow(new RuntimeException("DB error"));
        mockMvc.perform(get("/api/profile")).andExpect(status().is5xxServerError());
    }
}
