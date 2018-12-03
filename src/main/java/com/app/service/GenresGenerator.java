package com.app.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GenresGenerator {
    public static List<String> addGenres() {
        List<String> genres = new ArrayList<>();

        ApplicationContext appContext = new ClassPathXmlApplicationContext();

        Resource resource = appContext.getResource("https://gist.githubusercontent.com/sampsyo/1241307/raw/208ab2e4b5b576ebc51d801b039f93ee2bbc33ea/genres.txt");

        try {
            InputStream is = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                genres.add(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return genres;
    }
}
