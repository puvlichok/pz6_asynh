package com.example.asyncdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
public class AsyncDemoApplication implements CommandLineRunner {

    private final TaskService taskService;

    public AsyncDemoApplication(TaskService taskService) {
        this.taskService = taskService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AsyncDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Instant startTime = Instant.now();

        // Task 1: Periodic task based on user input
        scheduler.scheduleAtFixedRate(() -> {
            System.out.print("Do you want to execute the task? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(input)) {
                taskService.performPeriodicTask();
            } else if ("N".equals(input)) {
                System.out.println("Task skipped.");
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }, 0, 10, TimeUnit.SECONDS);

        // Task 2: Random interval task
        CompletableFuture.runAsync(() -> {
            Random random = new Random();

            while (true) {
                int interval = random.nextInt(10) + 1; // Random interval between 1 and 10 seconds

                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                taskService.performRandomIntervalTask(startTime);
            }
        });
    }
}

@Service
class TaskService {

    @Async
    public void performPeriodicTask() {
        System.out.println("Periodic task executed at: " + Instant.now());
    }

    @Async
    public void performRandomIntervalTask(Instant startTime) {
        Duration elapsedTime = Duration.between(startTime, Instant.now());
        System.out.println("Random interval task executed. Elapsed time: " + elapsedTime.getSeconds() + " seconds.");
    }
}
