package com.app.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class DateTimeService {
    public static String getCurrentTime()
    {
        LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        DateTimeFormatter tdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return tdf.format(ldt);
    }
}
