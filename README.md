# Smart Pantry Dashboard

A simple inventory management app: staff can view stock, admins can add items and restock existing ones.

**Stack:** Spring Boot (Java) + Spring Security, H2 in-memory database, React + TypeScript (Vite).

## How to Run

### Backend
```
cd backend
./mvnw spring-boot:run
```
Runs on `http://localhost:8080`. Uses an in-memory H2 database, so no setup or Docker required — data resets on every restart.

### Frontend
```
cd frontend
npm install
npm run dev
```
Runs on `http://localhost:5173`.

### Login credentials (hardcoded, in-memory)
- `user` / `password` — role `USER` (read-only)
- `admin` / `password` — role `ADMIN` (can add items, restock, delete)

## Decisions & Trade-offs

- **H2 over PostgreSQL:** the assignment notes this is a non-functional difference, so H2 in-memory was chosen to avoid Docker setup entirely and keep the project runnable with a single command.
- **Login verification:** Basic Auth has no concept of a "login" server-side — credentials are checked per-request, not per-session. The login form verifies typed credentials by sending them on a request to `GET /api/items` and checking whether the server accepts them, then stores them in component state for reuse on subsequent requests (as the assignment specifies).
- **Admin detection on the frontend** is done client-side (`username === "admin"`) purely to decide what UI to show (e.g. the Restock button). This is not a security boundary — the backend independently enforces role-based authorization on every write endpoint regardless of what the frontend displays.
- **CORS** is explicitly configured to allow only `http://localhost:5173`, matching the frontend's dev server origin.
- Added the optional `DELETE /api/items/{id}` endpoint, restricted to `ADMIN`, and an `Add Item` form for admins.
- Skipped: PostgreSQL/Docker, backend input validation (e.g. rejecting negative quantities), the optional integration tests (401/403/201 checks), and logout functionality.

## What I'd Improve With More Time

- Input validation on the backend (e.g. reject negative/zero quantities) with proper error responses.
- Integration tests covering authorization (401 without credentials, 403 for wrong role, 201 for admin).
- Logout functionality and better loading/error states in the UI.
- A dedicated way to determine role from the backend rather than inferring admin status from the username client-side.
