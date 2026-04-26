package com.lela.backend;

import com.lela.backend.entity.Lesson;
import com.lela.backend.entity.User;
import com.lela.backend.repository.LessonRepository;
import com.lela.backend.repository.ProgressRepository;
import com.lela.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LessonCompletionFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private LessonRepository lessonRepository;

    private Long userId;
    private Long lessonId;

    @BeforeEach
    void setUp() {
        progressRepository.deleteAll();

        User user = userRepository.findByEmail("alice@lela.local").orElseThrow();
        user.setPoints(0);
        user = userRepository.save(user);
        userId = user.getId();

        Lesson lesson = lessonRepository.findAll()
                .stream()
                .filter(current -> "Lesson 1: Greetings".equals(current.getTitle()))
                .findFirst()
                .orElseThrow();
        lessonId = lesson.getId();
    }

    @Test
    void shouldCompleteLessonSuccessfully() throws Exception {
        mockMvc.perform(post("/api/lessons/{id}/complete", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":" + userId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Lesson completed successfully"))
                .andExpect(jsonPath("$.userId").value(userId.intValue()))
                .andExpect(jsonPath("$.lessonId").value(lessonId.intValue()))
                .andExpect(jsonPath("$.pointsEarned").value(15))
                .andExpect(jsonPath("$.totalPoints").value(15))
                .andExpect(jsonPath("$.rewardUnlocked").value(false));
    }

    @Test
    void shouldReturnAlreadyCompletedOnDuplicateRequest() throws Exception {
        mockMvc.perform(post("/api/lessons/{id}/complete", lessonId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":" + userId + "}"));

        mockMvc.perform(post("/api/lessons/{id}/complete", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":" + userId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Lesson was already completed earlier"))
                .andExpect(jsonPath("$.pointsEarned").value(0))
                .andExpect(jsonPath("$.totalPoints").value(15))
                .andExpect(jsonPath("$.rewardUnlocked").value(false));
    }

    @Test
    void shouldReturnNotFoundWhenLessonDoesNotExist() throws Exception {
        mockMvc.perform(post("/api/lessons/{id}/complete", 999999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":" + userId + "}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(containsString("Lesson not found")))
                .andExpect(jsonPath("$.path").value("/api/lessons/999999/complete"));
    }
}