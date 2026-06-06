# Frontend Plan: Well-being Dashboard & Interaction UI

## Tech Stack
- HTML5, Vanilla JavaScript, Tailwind CSS (via CDN)
- Location: Accessible entirely within `src/main/resources/static/`

## Feature Outlines & User Action Flows

### Feature 01: Core - Exam-phase Mood Tracker with Trigger Logging
- **User Actions**: Students tap a mood emoji button (Excellent, Anxious, Burned Out, Tired) and select an exam-related trigger tag (e.g., "Mock Test Marks", "Syllabus Backlog", "Peer Pressure").
- **Expected Outcomes**: Interactive, accessible visual pattern line graphs mapping emotional scores against major study event timelines.
- **Relevance**: Surfaces specific stress patterns tied directly to milestones along the exam journey (e.g., JEE/NEET prep weeks).
- **Accessibility**: High-contrast active states and full `aria-label` tags for all clickable interactive emojis.

### Feature 02: Core - Guided Emotional Reflection Journal
- **User Actions**: Students answer 2–3 specific, highly tailored guided prompts appearing in text boxes during evening hours.
- **Expected Outcomes**: Chronological timeline dashboard displaying self-reflection entries along with dynamic insights regarding prevailing emotional patterns.
- **Relevance**: Journaling prompts dynamically adjust based on current exam milestones (e.g., Revision Week vs. Result Eve) to limit repetitive negative rumination.
- **Accessibility**: Form field elements explicitly use matching semantic `<label>` hooks supporting automated screen reader navigation.

### Feature 03: Core - Personalized Wellness Nudges Based on Mood Data
- **User Actions**: Students view their main dashboard feed during active navigation or tracking histories.
- **Expected Outcomes**: Automatically generated, concise, context-aware notification alerts presenting adaptive wellness exercises or pacing support tips.
- **Relevance**: System responds rapidly to declining mood analytics, distributing targeted actionable support structures precisely during heavy workload phases.

## State Handling & Visual Indicators
- Fetch requests handle secure authorization headers passing JWT parameters.
- Active CSS skeleton loaders manage state visibility smoothly while the AI compiles personalization items.