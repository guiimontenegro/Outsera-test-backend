package com.example.goldenraspberry.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.goldenraspberry.model.Movie;
import com.example.goldenraspberry.repository.MovieRepository;

@Service
public class AwardService {

    private final MovieRepository movieRepository;

    public AwardService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Map<String, List<Map<String, Object>>> getAwardIntervals() {
        List<Movie> winners = movieRepository.findByWinnerTrueOrderByProducersAscYearAsc();

        Map<String, List<Integer>> producerWins = new HashMap<>();
        for (Movie m : winners) {
            for (String producer : m.getProducers().split(",| and ")) {
                producer = producer.trim();
                producerWins.putIfAbsent(producer, new ArrayList<>());
                producerWins.get(producer).add(m.getYear());
            }
        }

        List<Map<String, Object>> intervals = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> years = entry.getValue();
            if (years.size() < 2) continue;

            for (int i = 1; i < years.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("producer", entry.getKey());
                map.put("interval", years.get(i) - years.get(i - 1));
                map.put("previousWin", years.get(i - 1));
                map.put("followingWin", years.get(i));
                intervals.add(map);
            }
        }

        int minInterval = intervals.stream().mapToInt(i -> (int) i.get("interval")).min().orElse(0);
        int maxInterval = intervals.stream().mapToInt(i -> (int) i.get("interval")).max().orElse(0);

        List<Map<String, Object>> minList = intervals.stream()
                .filter(i -> (int) i.get("interval") == minInterval)
                .collect(Collectors.toList());

        List<Map<String, Object>> maxList = intervals.stream()
                .filter(i -> (int) i.get("interval") == maxInterval)
                .collect(Collectors.toList());

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("min", minList);
        result.put("max", maxList);

        return result;
    }
}
