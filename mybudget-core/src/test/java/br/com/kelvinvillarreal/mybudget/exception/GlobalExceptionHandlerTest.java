package br.com.kelvinvillarreal.mybudget.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for GlobalExceptionHandler
 */
@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test controller for throwing ResourceNotFoundException
     */
    @RestController
    @RequestMapping("/test")
    public static class TestController {
        @GetMapping("/not-found")
        public void notFound() {
            throw new ResourceNotFoundException("Resource not found with id: 123");
        }

        @GetMapping("/business-error")
        public void businessError() {
            throw new BusinessException("Insufficient budget balance");
        }

        @GetMapping("/success")
        public String success() {
            return "OK";
        }
    }

    @Test
    void testResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Resource not found with id: 123"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testBusinessException() throws Exception {
        mockMvc.perform(get("/test/business-error"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Business Rule Violation"))
                .andExpect(jsonPath("$.message").value("Insufficient budget balance"));
    }

    @Test
    void testSuccessResponse() throws Exception {
        mockMvc.perform(get("/test/success"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }
}
