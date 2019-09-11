package com.intel.meeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IntelMeetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelMeetingApplication.class, args);
    }
}

