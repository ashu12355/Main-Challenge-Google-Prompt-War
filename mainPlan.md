# Backend Plan: Student Well-being Tracker for Exam Seasons

## Tech Stack & Core Configuration
- **Framework:** Java, Spring Boot 3.x, Maven
- **Database:** MySQL
- **Database Migrations:** Flyway (managed via `src/main/resources/db/migration/`)
- **AI Integration:** Spring AI / Gemini API for personalized wellness nudges

## Database Schema (Flyway Scripts)
- `V1__init_student_auth.sql`: Creates `users` table (id, username, password_hash, target_exam). Target exams include: NEET, JEE, CUET, CAT, GATE, UPSC, Boards.
- `V2__create_mood_and_triggers.sql`: Creates `mood_logs` (id, user_id, mood_score, trigger_tag, timestamp) and `journals` (id, user_id, entry_text, creation_date).
- `V3__create_wellness_nudges.sql`: Creates `wellness_nudges` (id, user_id, nudge_text, context_tag, is_read).

## Feature Architectures
1. **Exam-phase Mood Tracker with Trigger Logging**
   - **Code Quality**: Clean REST Controller handling inputs mapping to structured ENUMs for mood types and triggers.
   - **Security**: Endpoint protected with Spring Security + stateless JWT context to ensure logs are strictly private to the student.
   - **Efficiency**: Indexed database timestamps for fast aggregate retrieval of weekly/monthly mood trends.
   
2. **Guided Emotional Reflection Journal**
   - **Code Quality**: Structured repository layer utilizing JPA data mapping for automated transaction processing.
   - **Security**: Strict data validation and sanitization preventing Cross-Site Scripting (XSS) injection within the journal text entries.
   
3. **Personalized Wellness Nudges Based on Mood Data**
   - **Code Quality**: Decoupled service layer calling the Gemini API. The system prompt must inject the student's target exam (e.g., "JEE"), their recent mood trend, and their latest triggers to generate highly relevant, concise wellness support.
   - **Efficiency**: Async processing using `@Async` to fire AI text-generation tasks without blocking the main user request threads.

## REST API Endpoints
- `POST /api/v1/auth/login` & `POST /api/v1/auth/register`
- `POST /api/v1/moods`: Log current mood and specific stress trigger.
- `GET /api/v1/moods/analytics`: Fetch data aggregated for weekly trend graphs.
- `POST /api/v1/journals`: Commit evening reflection response text.
- `GET /api/v1/nudges/personalized`: Invoke AI to return context-aware wellness support.