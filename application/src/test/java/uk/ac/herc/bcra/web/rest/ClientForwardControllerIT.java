package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

/**
 * Integration tests for the {@link ClientForwardController} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class ClientForwardControllerIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        ClientForwardController clientForwardController = new ClientForwardController();
        this.restMockMvc = MockMvcBuilders
            .standaloneSetup(clientForwardController, new TestController())
            .build();
    }

    @Test
    @Transactional
    public void getBackendEndpoint() throws Exception {
        restMockMvc.perform(get("/test"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
            .andExpect(content().string("test"));
    }

    @Test
    @Transactional
    public void getClientEndpoint() throws Exception {
        ResultActions perform = restMockMvc.perform(get("/non-existant-mapping"));
        perform
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/"));
    }

    @Test
    @Transactional
    public void getNestedClientEndpoint() throws Exception {
        restMockMvc.perform(get("/admin/user-management"))
            .andExpect(status().isOk())
            .andExpect(forwardedUrl("/"));
    }

    @RestController
    public static class TestController {

        @RequestMapping(value = "/test")
        public String test() {
            return "test";
        }
    }
}
