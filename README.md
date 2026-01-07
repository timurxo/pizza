# Pizza App

fullstack app for ordering pizza

# Prerequisites

- java: jdk 17 or higher
- nodejs: v18 or higher
- docker (optional)

# Structure

- `pizza-api`: java api
- `pizza-app`: angular ui

---

# --- Locally OR Docker ---

# Start locally

# 1. Start the api - pizza-api

The backend runs on port `8080`

- cd pizza-api
- ./mvnw spring-boot:run

# 2. Start the ui - pizza-app

- cd pizza-app
- npm i
- ng serve

in browser go to http://localhost:4200

---

# Run with DOCKER 
- cd pizza (home directory for both apps)
- docker compose up --build

- ui: http://localhost
- api: http://localhost:8080

To stop it:
- docker compose down
