// SPDX-License-Identifier: MIT
package com.mercedesbenz.sechub.webserver.page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.mercedesbenz.sechub.testframework.spring.WithMockJwtUser;
import com.mercedesbenz.sechub.testframework.spring.YamlPropertyLoaderFactory;
import com.mercedesbenz.sechub.webserver.security.SecurityTestConfiguration;

@WebMvcTest(HomeController.class)
@Import(SecurityTestConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.yml", factory = YamlPropertyLoaderFactory.class)
@ActiveProfiles("oauth2-enabled")
class HomeControllerTest {

    private final MockMvc mockMvc;
    private final RequestPostProcessor requestPostProcessor;

    @Autowired
    HomeControllerTest(MockMvc mockMvc, RequestPostProcessor requestPostProcessor) {
        this.mockMvc = mockMvc;
        this.requestPostProcessor = requestPostProcessor;
    }

    @Test
    void home_page_is_not_accessible_anonymously() throws Exception {
        /* @formatter:off */
        mockMvc
                .perform(get("/home"))
                .andExpect(status().is3xxRedirection());
        /* @formatter:on */
    }

    @Test
    @WithMockJwtUser
    void home_page_is_accessible_with_authenticated_user() throws Exception {
        /* execute & test */

        /* @formatter:off */
        mockMvc
                .perform(get("/home").with(requestPostProcessor))
                .andExpect(status().isOk())
                .andReturn();
        /* @formatter:on */
    }
}
