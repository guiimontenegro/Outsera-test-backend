package com.example.goldenraspberry;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AwardIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    @Test
    void testGetIntervals() {
        Map<String, Object> response = restTemplate.getForObject("/awards/intervals", Map.class);

        assertThat(response).isNotNull();
        assertThat(response).containsKeys("min", "max");

        List<Map<String, Object>> minList = (List<Map<String, Object>>) response.get("min");
        List<Map<String, Object>> maxList = (List<Map<String, Object>>) response.get("max");

        assertThat(minList).isNotNull().hasSize(1);
        assertThat(maxList).isNotNull().hasSize(1);

        Map<String, Object> minEntry = minList.get(0);
        assertThat(minEntry).containsKeys("producer", "interval", "previousWin", "followingWin");
        assertThat(((Number) minEntry.get("interval")).intValue()).isEqualTo(1);
        int minPrev = ((Number) minEntry.get("previousWin")).intValue();
        int minFollowing = ((Number) minEntry.get("followingWin")).intValue();
        assertThat(minPrev).isLessThan(minFollowing);

        Map<String, Object> maxEntry = maxList.get(0);
        assertThat(maxEntry).containsKeys("producer", "interval", "previousWin", "followingWin");
        assertThat(((Number) maxEntry.get("interval")).intValue()).isEqualTo(13);
        int maxPrev = ((Number) maxEntry.get("previousWin")).intValue();
        int maxFollowing = ((Number) maxEntry.get("followingWin")).intValue();
        assertThat(maxPrev).isLessThan(maxFollowing);
        assertThat(minEntry.get("producer")).isInstanceOf(String.class).asString().isNotBlank();
        assertThat(maxEntry.get("producer")).isInstanceOf(String.class).asString().isNotBlank();
    }
}
