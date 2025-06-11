## Fitness Backend Application

### Overview
This is a Spring Boot-based backend application designed to manage fitness-related data, including workouts, exercises, and user progress. The application provides a RESTful API for creating, retrieving, updating, and deleting fitness records, with support for pagination, filtering, and report generation in multiple formats (CSV, PDF, Excel).


### Features

- **Workout Management**: Log, retrieve, update, and filter workouts by type, date range, and pagination.
- **Exercise Management**: Create, retrieve, update, and delete exercises with optional filtering by name.
- **Progress Tracking**: Record and track user progress, including weight and date-based filtering, with exportable reports.
- **API Responses**: Standardized API responses using a custom ApiResponse wrapper for consistent success and error handling.
- **Pagination and Sorting**: All list endpoints support pagination and customizable sorting.
- **Report Generation**: Export progress data in CSV, PDF, or Excel formats.

### Technologies

- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For database interactions.
- **Lombok**: To reduce boilerplate code (e.g., @RequiredArgsConstructor).
- **Java 21**: Programming language version.
- **Maven**: Dependency management and build tool.
- **Database**: Configurable for any JPA-compatible database (e.g., PostgreSQL, MySQL).
- **Validation**: Bean validation for incoming DTOs using @Valid.

### API Endpoints
The API is versioned under /api/v1 and consists of three main resources: Workouts, Exercises, and Progress.

#### Workouts (/api/v1/workouts)

- GET /: Retrieve paginated workouts with optional filters for workoutType, startDate, endDate, and sorting.
- GET /{id}: Retrieve a workout by its UUID.
- POST /: Create a new workout using WorkoutDTO.
- PUT /{id}: Update an existing workout by UUID.

#### Exercises (/api/v1/exercises)

- GET /: Retrieve paginated exercises with optional filtering by name and sorting.
- GET /{id}: Retrieve an exercise by its UUID.
- POST /: Create a new exercise using ExerciseDTO.
- PUT /{id}: Update an existing exercise by UUID.
- DELETE /{id}: Delete an exercise by UUID.

#### Progress (/api/v1/progress)

- GET /: Retrieve paginated progress records with optional filters for minWeight, maxWeight, startDate, endDate, and sorting.
- GET /{id}: Retrieve a progress record by its UUID.
- POST /: Record new progress using ProgressDTO.
- PUT /{id}: Update an existing progress record by UUID.
- GET /export: Export progress data in CSV, PDF, or Excel format (specified by reportFormat parameter).



### Example API Request
Create a Workout
```
curl -X POST http://localhost:8080/api/v1/workouts \
-H "Content-Type: application/json" \
-d '{
    "workoutType": "Cardio",
    "workoutDate": "2025-06-11T10:00:00",
    "duration": 30
}'

// Response:

{
  "status": "success",
  "message": "Workout created successfully",
  "data": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "workoutType": "Cardio",
    "workoutDate": "2025-06-11T10:00:00",
    "duration": 30
  }
}
```
