package com.example.goldenraspberry.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                producerWins.computeIfAbsent(producer, k -> new ArrayList<>()).add(m.getYear());
            }
        }

        List<Map<String, Object>> minList = new ArrayList<>();
        List<Map<String, Object>> maxList = new ArrayList<>();
        int minInterval = Integer.MAX_VALUE;
        int maxInterval = Integer.MIN_VALUE;

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> years = entry.getValue();
            if (years.size() < 2) {
                continue;
            }

            years.sort(Integer::compareTo);

            for (int i = 1; i < years.size(); i++) {
                int interval = years.get(i) - years.get(i - 1);

                Map<String, Object> map = new HashMap<>();
                map.put("producer", entry.getKey());
                map.put("interval", interval);
                map.put("previousWin", years.get(i - 1));
                map.put("followingWin", years.get(i));

                if (interval < minInterval) {
                    minInterval = interval;
                    minList.clear();
                    minList.add(map);
                } else if (interval == minInterval) {
                    minList.add(map);
                }

                if (interval > maxInterval) {
                    maxInterval = interval;
                    maxList.clear();
                    maxList.add(map);
                } else if (interval == maxInterval) {
                    maxList.add(map);
                }
            }
        }
        if (minList.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("producer", null);
            map.put("interval", 0);
            map.put("previousWin", null);
            map.put("followingWin", null);
            minList.add(map);
        }

        if (maxList.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("producer", null);
            map.put("interval", 0);
            map.put("previousWin", null);
            map.put("followingWin", null);
            maxList.add(map);
        }

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("min", minList);
        result.put("max", maxList);
        return result;
    }
}
