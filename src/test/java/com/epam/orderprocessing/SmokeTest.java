package com.epam.orderprocessing;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class SmokeTest {

    @Test
    public void smokeTest() {
        RestTemplate restTemplate = new RestTemplate();

        String homePage = restTemplate.getForObject(url("/"), String.class);

        assertThat(homePage, containsString("Please select one of the following links:"));

        String setupPage = restTemplate.getForObject(url("/setup.jsp"), String.class);

        assertThat(setupPage, containsString("2834 Dawson Drive, Jacksonville, AR 72099"));
        assertThat(setupPage, containsString("Elizabeth T McNulty"));
        assertThat(setupPage, containsString("Limited Report"));

        assertThat(setupPage, containsString("3630 Yorkshire Circle, Kitty Hawk, NC 27949"));
        assertThat(setupPage, containsString("Timothy J Silva"));
        assertThat(setupPage, containsString("Limited Report"));

        assertThat(setupPage, containsString("3188 Friendship Lane, Santa Clara, CA 95054"));
        assertThat(setupPage, containsString("Sherry D Presnell"));
        assertThat(setupPage, containsString(">Limited Plus Report"));

        assertThat(setupPage, containsString("680 Coburn Hollow Road, Peoria, IL 61614"));
        assertThat(setupPage, containsString("Debra C Clary"));
        assertThat(setupPage, containsString("Limited Plus Report"));

        assertThat(setupPage, containsString("2747 Cambridge Court, Highlands, NC 28741"));
        assertThat(setupPage, containsString("Scott M Faris"));
        assertThat(setupPage, containsString("Full Report"));

        String orderProcessingPage = restTemplate.getForObject(url("/order-processing"), String.class);

        assertThat(orderProcessingPage, containsString("2834 Dawson Drive, Jacksonville, AR 72099"));
        assertThat(orderProcessingPage, containsString("Elizabeth T McNulty"));
        assertThat(orderProcessingPage, containsString("Limited Report"));

        assertThat(orderProcessingPage, containsString("3630 Yorkshire Circle, Kitty Hawk, NC 27949"));
        assertThat(orderProcessingPage, containsString("Timothy J Silva"));
        assertThat(orderProcessingPage, containsString("Limited Report"));

        assertThat(orderProcessingPage, containsString("3188 Friendship Lane, Santa Clara, CA 95054"));
        assertThat(orderProcessingPage, containsString("Sherry D Presnell"));
        assertThat(orderProcessingPage, containsString(">Limited Plus Report"));

        assertThat(orderProcessingPage, containsString("680 Coburn Hollow Road, Peoria, IL 61614"));
        assertThat(orderProcessingPage, containsString("Debra C Clary"));
        assertThat(orderProcessingPage, containsString("Limited Plus Report"));

        assertThat(orderProcessingPage, containsString("2747 Cambridge Court, Highlands, NC 28741"));
        assertThat(orderProcessingPage, containsString("Scott M Faris"));
        assertThat(orderProcessingPage, containsString("Full Report"));
    }

    private String url(String path) {
        String baseUrl = "http://localhost:8080/order-processing";
        String envUrl = System.getenv("ORDER_PROCESSING_URL");

        if (envUrl != null && !envUrl.isEmpty()) {
            baseUrl = envUrl;
        }

        return baseUrl + path;
    }
}