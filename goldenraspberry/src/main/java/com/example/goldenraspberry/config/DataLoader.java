package com.example.goldenraspberry.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.goldenraspberry.model.Movie;
import com.example.goldenraspberry.repository.MovieRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public DataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new ClassPathResource("movies.csv").getInputStream()))) {

                br.readLine(); // Pular header

                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    Movie m = new Movie();
                    m.setYear(Integer.parseInt(data[0].trim()));
                    m.setTitle(data[1].trim());
                    m.setStudios(data[2].trim());
                    m.setProducers(data[3].trim());
                    m.setWinner(data.length > 4 && "yes".equalsIgnoreCase(data[4].trim()));
                    movieRepository.save(m);
                }
            }
            System.out.println("CSV loaded successfully!");
        }
    }
}
