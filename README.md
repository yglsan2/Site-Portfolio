# Portfolio personnel

Site portfolio professionnel : backend Java (Spring Boot 3, Java 21, Lombok) et frontend Vue 3 + Vite + Tailwind.

## Stack

- **Backend** : Java 21, Spring Boot 3.4, Jakarta EE, Lombok, H2, logique avec try/catch/finally et logs.
- **Frontend** : Vue 3, Vue Router, Vite 5, Tailwind CSS 3, thème sombre/bleu (#0f0f12, #6c9ef8), responsive.

## Prérequis

- **JDK 21**
- **Maven**
- **Node.js 18+** (frontend)

## Lancer le backend

```bash
cd backend
mvn spring-boot:run
```

API : **http://localhost:8080**

- `GET /api/profile` — Profil (accueil)
- `GET /api/projects` — Projets (dépôts GitHub réels)
- `GET /api/projects/slug/{slug}` — Projet par slug
- `GET /api/snippets/project/{id}` — Extraits de code par projet
- `GET /api/skills` — Compétences

H2 Console : http://localhost:8080/h2-console  
(JDBC URL : `jdbc:h2:mem:portfolio`, user : `sa`, password : vide)

## Lancer le frontend

```bash
cd frontend
npm install
npm run dev
```

Ouvrir **http://localhost:5173**. Le proxy Vite envoie `/api` vers le backend (port 8080).

## Données initiales

Au premier démarrage, le `DataInitializer` crée :

- Un **profil** (accueil) : nom, titre, bio, lien GitHub.
- Des **projets** uniquement issus de dépôts GitHub réels (BarrelMCD-python, Lumières d'Ukraine, DokiLight, Barrel, Noublipo, ManyFaces, MoodCast, Carned Beef, PloufPlouf, UserScripts).
- Des **extraits de code** réels (BarrelMCD, Ukraine, DokiLight).
- Des **compétences** (Java, Spring Boot, Python, Vue, Flutter, DevOps, SCRUM, Kanban, etc.).

## Sécurité et qualité

- CORS configuré pour le frontend (dev).
- Gestion centralisée des exceptions (`GlobalExceptionHandler`) avec logs.
- Services avec règle 4 try / 1 catch / 1 finally et logs aux points stratégiques.
