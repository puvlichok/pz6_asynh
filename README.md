# AsyncDemoApplication

This Spring Boot application demonstrates the use of asynchronous tasks with `@Async` and `CompletableFuture`. The application performs two main tasks: a periodic task based on user input and a task executed at random intervals.

## Features

1. **Periodic Task:**
   - Periodically prompts the user to decide whether to execute a task.
   - Executes the task if the user chooses "Y", otherwise skips the task.

2. **Random Interval Task:**
   - Executes a task at random intervals between 1 and 10 seconds.
   - Prints the elapsed time since the application started.

## Prerequisites

- Java Development Kit (JDK) 8 or higher.
- Maven or Gradle for dependency management.

## Running the Project

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd <repository-directory>
