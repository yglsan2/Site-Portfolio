package dev.portfolio.config;

import dev.portfolio.entity.CodeSnippet;
import dev.portfolio.entity.Project;
import dev.portfolio.entity.Skill;
import dev.portfolio.repository.CodeSnippetRepository;
import dev.portfolio.repository.ProfileRepository;
import dev.portfolio.repository.ProjectRepository;
import dev.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final CodeSnippetRepository codeSnippetRepository;
    private final SkillRepository skillRepository;

    @Override
    public void run(String... args) {
        log.info("DataInitializer: vérification des données initiales");
        try {
            try {
                if (profileRepository.count() == 0) {
                    log.info("Initialisation du profil (accueil)");
                    try {
                        profileRepository.save(dev.portfolio.entity.Profile.builder()
                                .name("Benjamin Moine")
                                .title("Développeur d'applications")
                                .bio("Développeur passionné par les applications et l'infra. Expérience en Java (Jakarta EE, Spring Boot, Maven), Python, JavaScript, modélisation de données et DevOps (Docker, Kubernetes, Ansible, Jenkins, CI/CD). Méthodes agiles (Scrum), tests unitaires et déploiement. Anglais C1.")
                                .email(null)
                                .linkedinUrl(null)
                                .githubUrl("https://github.com/yglsan2")
                                .sortOrder(0)
                                .build());
                    } finally {
                        log.debug("Profil créé");
                    }
                }
                try {
                    if (projectRepository.count() > 0) {
                        log.info("Projets déjà présents, skip seed");
                        projectRepository.findBySlug("userscripts").ifPresent(proj -> {
                            if (proj.getRepoUrl() == null || proj.getRepoUrl().isBlank()) {
                                proj.setRepoUrl("https://github.com/yglsan2/Mes-applications-Userscript-JS-");
                                projectRepository.save(proj);
                                log.info("Mise à jour repoUrl pour Mes applications UserScript");
                            }
                        });
                        return;
                    }
                } finally {
                    log.trace("Vérification count projets");
                }
            } finally {
                log.trace("Vérification profil terminée");
            }
        } catch (Exception e) {
            log.error("Erreur DataInitializer (profil): {}", e.getMessage(), e);
            throw e;
        } finally {
            log.trace("DataInitializer run (phase profil) terminé");
        }

        log.info("Création des projets (uniquement dépôts GitHub réels)");
        List<Project> projects = projectRepository.saveAll(List.of(
                Project.builder()
                        .title("BarrelMCD (Python)")
                        .slug("barrelmcd-python")
                        .description("Outil de modélisation MCD en Python. Interface graphique (Tkinter), génération de schémas, export. Projet personnel complet et utilisable.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Python", "Tkinter", "SQL"))
                        .sortOrder(1)
                        .repoUrl("https://github.com/yglsan2/BarrelMCD-python")
                        .build(),
                Project.builder()
                        .title("Lumières d'Ukraine")
                        .slug("lumieres-ukraine")
                        .description("Plateforme culturelle : bibliothèque virtuelle et événements. Vue 3, i18n (FR, EN, UK, DE, PL), backend Spring Boot avec JWT.")
                        .type(Project.ProjectType.WEBSITE)
                        .technologies(List.of("Vue 3", "Pinia", "Vue I18n", "Spring Boot", "PostgreSQL"))
                        .sortOrder(2)
                        .repoUrl("https://github.com/yglsan2/Ukraine")
                        .build(),
                Project.builder()
                        .title("DokiLight")
                        .slug("dokilight")
                        .description("Chatbot RAG (stage Dokos) : version light de Doki Expert. Recherche par mots-clés dans la doc, PostgreSQL/pgvector, Streamlit, scoring hybride sémantique + conceptuel.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Python", "Streamlit", "PostgreSQL", "pgvector", "SentenceTransformer"))
                        .sortOrder(3)
                        .repoUrl("https://github.com/yglsan2/DokiLight")
                        .build(),
                Project.builder()
                        .title("Noublipo (NopList)")
                        .slug("noublipo")
                        .description("Application liste de courses / shopping list.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(4)
                        .repoUrl("https://github.com/yglsan2/Noublipo")
                        .build(),
                Project.builder()
                        .title("ManyFaces")
                        .slug("manyfaces")
                        .description("Logiciel de création de personnages et PNJ pour jeux de rôle.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(5)
                        .repoUrl("https://github.com/yglsan2/RPGproject-Flutter-3-me-application-sous-flutter-")
                        .build(),
                Project.builder()
                        .title("MoodCast")
                        .slug("moodcast")
                        .description("Application Flutter (météo / ambiance).")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(6)
                        .repoUrl("https://github.com/yglsan2/MoodCast")
                        .build(),
                Project.builder()
                        .title("Carned Beef")
                        .slug("carned-beef")
                        .description("Partage de partitions musicales.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(7)
                        .repoUrl("https://github.com/yglsan2/CarnedBeef")
                        .build(),
                Project.builder()
                        .title("PloufPlouf")
                        .slug("ploufplouf")
                        .description("Logiciel de tirage au sort pédagogique.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(8)
                        .repoUrl("https://github.com/yglsan2/PloufPlouf")
                        .build(),
                Project.builder()
                        .title("Mes applications UserScript")
                        .slug("userscripts")
                        .description("Ensemble d’extensions utilisateur en JavaScript (UserScript) : Lichess, etc.")
                        .type(Project.ProjectType.OPEN_SOURCE)
                        .technologies(List.of("JavaScript", "UserScript"))
                        .sortOrder(9)
                        .repoUrl("https://github.com/yglsan2/Mes-applications-Userscript-JS-")
                        .build()
        ));

        Project barrelMcd = projects.get(0);
        Project lumieresUkraine = projects.get(1);
        Project dokiLight = projects.get(2);
        Project noublipo = projects.get(3);
        Project manyfaces = projects.get(4);
        Project moodcast = projects.get(5);
        Project carnedBeef = projects.get(6);
        Project ploufplouf = projects.get(7);
        Project userscripts = projects.get(8);

        List<CodeSnippet> snippets = new ArrayList<>();

        // --- BarrelMCD (Python) : modélisation MCD, PyQt5 ---
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Point d'entrée")
                .title("Application PyQt5 : main, thème sombre, fenêtre principale")
                .slug("barrel-main").language("python")
                .description("Point d'entrée réel de BarrelMCD : configuration Wayland, chargement du thème sombre et affichage de la MainWindow.")
                .code("""
#!/usr/bin/env python3
# -*- coding: utf-8 -*-
\"\"\" Application principale BarrelMCD \"\"\"

import os, sys
os.environ['QT_QPA_PLATFORM'] = 'xcb'

from PyQt5.QtWidgets import QApplication
from views.main_window import MainWindow
from views.dark_theme import DarkTheme

def main():
    app = QApplication(sys.argv)
    app.setApplicationName("BarrelMCD")
    app.setApplicationVersion("1.0.0")
    DarkTheme.apply_dark_theme(app)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()
""").sortOrder(1).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Modèle de données")
                .title("Classe Attribute : sérialisation to_dict / from_dict")
                .slug("barrel-attribute").language("python")
                .description("Représentation d'un attribut MCD avec type, clé primaire et contraintes ; sérialisation pour sauvegarde/chargement.")
                .code("""
class Attribute:
    \"\"\"Classe représentant un attribut d'entité MCD\"\"\"

    def __init__(self, name="", type_name="VARCHAR(255)", is_primary_key=False):
        self.name = name
        self.type = type_name
        self.is_primary_key = is_primary_key
        self.is_required = False
        self.default_value = None
        self.constraints = []

    def to_dict(self):
        return {
            "name": self.name,
            "type": self.type,
            "is_primary_key": self.is_primary_key,
            "is_required": self.is_required,
            "default_value": self.default_value,
            "constraints": self.constraints.copy()
        }

    @classmethod
    def from_dict(cls, data):
        attr = cls(
            name=data.get("name", ""),
            type_name=data.get("type", "VARCHAR(255)"),
            is_primary_key=data.get("is_primary_key", False)
        )
        attr.is_required = data.get("is_required", False)
        attr.constraints = data.get("constraints", [])
        return attr
""").sortOrder(2).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Vue et signaux")
                .title("Entité MCD : signaux PyQt, ajout d'attribut, mise à jour layout")
                .slug("barrel-entity").language("python")
                .description("Entité graphique avec signaux (renommage, attributs), gestion des attributs et recalcul de la hauteur.")
                .code("""
class EntitySignals(QObject):
    entity_renamed = pyqtSignal(str, str)
    attribute_added = pyqtSignal(str, str)
    attribute_removed = pyqtSignal(str)

class Entity(QGraphicsItem):
    def add_attribute(self, name, type_name, is_primary_key=False, nullable=True, default_value=None):
        attribute = {
            "name": name, "type": type_name, "is_primary_key": is_primary_key,
            "nullable": nullable, "default_value": default_value
        }
        self.attributes.append(attribute)
        self.update_layout()
        self.signals.attribute_added.emit(name, type_name)
        self.update()

    def update_layout(self):
        total_height = 50 + len(self.attributes) * self.attribute_height + self.padding
        if total_height < self.min_height:
            total_height = self.min_height
        self.height = total_height
        self.update()
""").sortOrder(3).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("UI et thème")
                .title("Thème sombre : palette de couleurs et application à l'app")
                .slug("barrel-dark-theme").language("python")
                .description("Classe DarkTheme du dépôt : dictionnaire COLORS (entités, relations, scrollbar), application via QPalette et stylesheet Fusion.")
                .code("""
class DarkTheme:
    COLORS = {
        \"background\": \"#0A0A0A\",
        \"surface\": \"#1A1A1A\",
        \"text_primary\": \"#FFFFFF\",
        \"primary\": \"#00D4FF\",
        \"entity_bg\": \"#1E2A3A\",
        \"entity_border\": \"#2E3A4A\",
        \"entity_selected\": \"#00D4FF\",
        \"relation_bg\": \"#4A1E3A\",
        \"pk_color\": \"#FF6B35\",
    }

    @classmethod
    def apply_dark_theme(cls, app: QApplication):
        palette = QPalette()
        palette.setColor(QPalette.Window, QColor(cls.COLORS[\"background\"]))
        palette.setColor(QPalette.WindowText, QColor(cls.COLORS[\"text_primary\"]))
        palette.setColor(QPalette.Base, QColor(cls.COLORS[\"surface\"]))
        palette.setColor(QPalette.Highlight, QColor(cls.COLORS[\"primary\"]))
        app.setPalette(palette)
        app.setStyle(\"Fusion\")
        app.setStyleSheet(cls._get_modern_dark_stylesheet())
""").sortOrder(4).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("DevOps / lancement")
                .title("Script run_api.sh : venv, dépendances, uvicorn")
                .slug("barrel-run-api").language("shell")
                .description("Lancement de l'API BarrelMCD (FastAPI) : création du venv si absent, pip install, uvicorn sur le port 8000.")
                .code("""
#!/bin/bash
set -e
cd \"$(dirname \"$0\")\"
VENV_DIR=\".venv\"

if [ ! -d \"$VENV_DIR\" ]; then
  echo \"Création de l'environnement virtuel...\"
  python3 -m venv \"$VENV_DIR\"
fi

echo \"Installation des dépendances API...\"
\"$VENV_DIR/bin/pip\" install -q -r api/requirements.txt

echo \"Démarrage de l'API sur http://127.0.0.0:8000\"
exec \"$VENV_DIR/bin/python\" -m uvicorn api.main:app --reload --host 0.0.0.0 --port 8000
""").sortOrder(5).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Modèle et export")
                .title("Export des données entité pour sauvegarde (get_data)")
                .slug("barrel-entity-get-data").language("python")
                .description("Méthode get_data() de l'entité MCD : sérialisation nom, position, attributs et flag is_weak pour export .bar / JSON.")
                .code("""
def get_data(self):
    \"\"\"Retourne les données de l'entité pour export\"\"\"
    return {
        \"name\": self.name,
        \"position\": {\"x\": self.pos().x(), \"y\": self.pos().y()},
        \"attributes\": self.attributes.copy(),
        \"is_weak\": self.is_weak
    }
""").sortOrder(6).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Vue et signaux")
                .title("Dessin de l'entité (paint) : dégradé, titre, attributs avec préfixe PK")
                .slug("barrel-entity-paint").language("python")
                .description("Méthode paint() de Entity : antialiasing, rectangle avec dégradé, titre centré, ligne de séparation, liste d'attributs avec icône clé primaire.")
                .code("""
def paint(self, painter, option, widget):
    painter.setRenderHint(QPainter.Antialiasing, True)
    rect = self.boundingRect()
    corner_radius = 8

    from PyQt5.QtGui import QLinearGradient
    gradient = QLinearGradient(rect.topLeft(), rect.bottomLeft())
    if self.is_selected:
        gradient.setColorAt(0, QColor(self.selected_color).lighter(120))
        gradient.setColorAt(1, QColor(self.selected_color))
    else:
        gradient.setColorAt(0, QColor(self.bg_color).lighter(110))
        gradient.setColorAt(1, self.bg_color)
    painter.setBrush(QBrush(gradient))
    painter.setPen(QPen(self.border_color, 2))
    painter.drawRoundedRect(rect, corner_radius, corner_radius)

    painter.setFont(self.title_font)
    title_rect = QRectF(self.padding, self.padding, self.width - 2 * self.padding, 30)
    painter.drawText(title_rect, Qt.AlignCenter, self.name)
    painter.drawLine(self.padding, 40, self.width - self.padding, 40)

    y_offset = 50
    for attribute in self.attributes:
        prefix = \"🔑 \" if attribute['is_primary_key'] else \"\"
        text = f\"{prefix}{attribute['name']}: {attribute['type']}\"
        painter.drawText(self.padding, y_offset, text)
        y_offset += self.attribute_height
""").sortOrder(7).build());

        // --- Lumières d'Ukraine (dépôt réel : frontend-vue + backend Spring) ---
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Point d'entrée frontend")
                .title("Démarrage de l'app Vue : router + i18n")
                .slug("ukraine-main").language("javascript")
                .description("Fichier main.js du dépôt : on crée l'application Vue, on lui attache le routeur (pages) et l'i18n (langues), puis on l'affiche dans la page (#app). C'est le point de départ de tout le front.")
                .code("""
import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import i18n from './i18n'

const app = createApp(App)
app.use(router)
app.use(i18n)
app.mount('#app')
""").sortOrder(1).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Routage")
                .title("Définition des routes : Livres, Événements, Adhésion, Chatbot…")
                .slug("ukraine-router").language("javascript")
                .description("Chaque route associe une URL (path) à une page (component). Ainsi /books affiche BooksView, /events affiche EventsView. Le routeur évite de recharger toute la page : on change juste la vue affichée (SPA).")
                .code("""
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BooksView from '../views/BooksView.vue'
import EventsView from '../views/EventsView.vue'
import AssociationView from '../views/AssociationView.vue'
import ChatbotView from '../views/ChatbotView.vue'
import AboutView from '../views/AboutView.vue'
import MembershipView from '../views/MembershipView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/books', name: 'books', component: BooksView },
    { path: '/events', name: 'events', component: EventsView },
    { path: '/association', name: 'association', component: AssociationView },
    { path: '/chatbot', name: 'chatbot', component: ChatbotView },
    { path: '/about', name: 'about', component: AboutView },
    { path: '/membership', name: 'membership', component: MembershipView },
  ],
})
export default router
""").sortOrder(2).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Internationalisation (i18n)")
                .title("5 langues : détection navigateur et changement de langue")
                .slug("ukraine-i18n").language("javascript")
                .description("On crée l'i18n avec les 5 langues (fr, en, uk, de, pl). getDefaultLocale() lit d'abord la langue sauvegardée (localStorage), sinon la langue du navigateur, sinon français. setLocale() change la langue et la sauvegarde pour la prochaine visite.")
                .code("""
import { createI18n } from 'vue-i18n'
import fr from './locales/fr.js'
import en from './locales/en.js'
import uk from './locales/uk.js'
import de from './locales/de.js'
import pl from './locales/pl.js'

const messages = { fr, en, uk, de, pl }

function getDefaultLocale() {
  const savedLocale = localStorage.getItem('locale')
  if (savedLocale && messages[savedLocale]) return savedLocale
  const browserLocale = navigator.language.split('-')[0]
  if (messages[browserLocale]) return browserLocale
  return 'fr'
}

const i18n = createI18n({
  legacy: false,
  locale: getDefaultLocale(),
  fallbackLocale: 'fr',
  messages,
})

export function setLocale(locale) {
  if (messages[locale]) {
    i18n.global.locale.value = locale
    localStorage.setItem('locale', locale)
    document.documentElement.lang = locale
  }
}
export default i18n
""").sortOrder(3).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Internationalisation (i18n)")
                .title("Fichier de traduction français : structure nav, accueil, livres")
                .slug("ukraine-locale-fr").language("javascript")
                .description("Chaque langue a un fichier (fr.js, en.js, etc.) qui exporte un objet. Les clés (nav, home, books…) sont utilisées dans les composants avec $t('nav.books') pour afficher le bon texte selon la langue choisie.")
                .code("""
export default {
  meta: {
    languageName: 'Français',
    nativeName: 'Français',
    flag: '🇫🇷',
  },
  nav: {
    home: 'Accueil',
    books: 'Livres',
    events: 'Événements',
    association: 'Association',
    chatbot: 'Chatbot',
    about: 'À propos',
    membership: 'Adhésion',
    selectLanguage: 'Choisir la langue',
  },
  home: {
    hero: {
      title: "Les Lumières d'Ukraine",
      subtitle: "Découvrez la richesse culturelle...",
      exploreButton: 'Explorer',
      joinButton: 'Rejoindre',
    },
    stats: { members: 'Membres', books: 'Livres', events: 'Événements' },
  },
  books: {
    title: 'Bibliothèque Ukrainienne',
    search: 'Rechercher un livre...',
    addBook: 'Ajouter un livre',
  },
}
""").sortOrder(4).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("État partagé (Pinia)")
                .title("Store Pinia : état réactif et valeur dérivée (computed)")
                .slug("ukraine-store-pinia").language("typescript")
                .description("Un store Pinia contient des données (ref) et des fonctions. Ici : count (nombre), doubleCount (calculé automatiquement : count × 2), increment() pour modifier count. Toute la partie front peut utiliser ce store pour partager cet état.")
                .code("""
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }
  return { count, doubleCount, increment }
})
""").sortOrder(5).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Backend Spring Boot")
                .title("Application principale : cache, tâches asynchrones, planification")
                .slug("ukraine-spring-app").language("java")
                .description("Classe de démarrage du backend. @SpringBootApplication active Spring Boot. @EnableCaching permet de mettre en cache des résultats (ex. liste de livres). @EnableAsync et @EnableScheduling permettent d'exécuter des tâches en arrière-plan ou à intervalles.")
                .code("""
package com.ukraine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class LumieresUkraineApplication {

    public static void main(String[] args) {
        SpringApplication.run(LumieresUkraineApplication.class, args);
    }
}
""").sortOrder(6).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Backend Spring Boot")
                .title("API REST : génération de cartes d'adhésion et envoi par email")
                .slug("ukraine-spring-controller").language("java")
                .description("Ce contrôleur expose des endpoints REST sous /api/membership. Par exemple : POST /generate-card génère une carte, POST /send-card l'envoie par email, GET /generate-number crée un numéro d'adhésion unique. Chaque méthode appelle le service métier puis renvoie une réponse HTTP (200 OK ou 400).")
                .code("""
@RestController
@RequestMapping("/api/membership")
@CrossOrigin(origins = "*")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/generate-card")
    public ResponseEntity<?> generateMembershipCard(@RequestBody MembershipCardRequest request) {
        try {
            MembershipCardResponse response = membershipService.generateMembershipCard(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/send-card")
    public ResponseEntity<?> sendMembershipCard(@RequestBody MembershipCardRequest request) {
        try {
            MembershipCardResponse cardResponse = membershipService.generateMembershipCard(request);
            emailService.sendMembershipCard(
                request.getEmailData().getTo(),
                request.getEmailData().getSubject(),
                request.getEmailData().getMessage(),
                cardResponse.getFrontImageUrl(),
                cardResponse.getBackImageUrl(),
                request.getMemberData()
            );
            return ResponseEntity.ok(Map.of(
                "message", "Carte d'adhésion envoyée avec succès",
                "memberNumber", request.getMemberData().getMemberNumber()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/generate-number")
    public ResponseEntity<?> generateMembershipNumber() {
        try {
            String memberNumber = membershipService.generateUniqueMembershipNumber();
            return ResponseEntity.ok(Map.of("memberNumber", memberNumber));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
""").sortOrder(7).build());

        // --- DokiLight : RAG, pgvector, Streamlit ---
        snippets.add(CodeSnippet.builder().project(dokiLight).section("RAG et pgvector")
                .title("Test RAG : connexion PostgreSQL, embedding, similarité vectorielle")
                .slug("dokilight-test-rag").language("python")
                .description("Script réel du dépôt : vérification pgvector, chargement du modèle sentence-transformers et requête de similarité.")
                .code("""
import psycopg
from psycopg.rows import dict_row
from sentence_transformers import SentenceTransformer
import json

def test_rag():
    conn = psycopg.connect(host='localhost', port=5432, dbname='doki_light', user='doki_user', password='doki_password')

    with conn.cursor() as cursor:
        cursor.execute("SELECT * FROM pg_extension WHERE extname = 'vector';")
        if not cursor.fetchone():
            print("Extension pgvector non trouvée")
            return

    model = SentenceTransformer('all-MiniLM-L6-v2')
    test_text = "Ceci est un test de recherche vectorielle"
    embedding = model.encode(test_text)

    with conn.cursor(row_factory=dict_row) as cursor:
        embedding_json = json.dumps(embedding.tolist())
        cursor.execute(\"\"\"
            SELECT e.chunk_text, d.filename,
                   1 - (e.embedding <=> %s::vector) as similarity
            FROM embeddings e
            JOIN documents d ON e.document_id = d.id
            ORDER BY e.embedding <=> %s::vector
            LIMIT 3
        \"\"\", (embedding_json, embedding_json))
        results = cursor.fetchall()
    conn.close()
""").sortOrder(1).build());
        snippets.add(CodeSnippet.builder().project(dokiLight).section("Lancement")
                .title("Lancement Streamlit : vérification des dépendances, subprocess")
                .slug("dokilight-launch").language("python")
                .description("Bootstrap de l'interface Doki Light : vérif des imports (streamlit, psycopg, sentence_transformers, ollama) puis lancement sur le port 8501.")
                .code("""
#!/usr/bin/env python3
\"\"\" Script de lancement pour Doki Light - Version Streamlit \"\"\"

import subprocess
import sys

def main():
    try:
        import streamlit
        import psycopg
        import sentence_transformers
        import ollama
        print("Toutes les dépendances sont installées")
    except ImportError as e:
        print(f"Dépendance manquante: {e}")
        return False

    subprocess.run([
        sys.executable, "-m", "streamlit", "run",
        "doki_light_streamlit.py",
        "--server.port", "8501",
        "--server.address", "localhost"
    ])
    return True

if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1)
""").sortOrder(2).build());

        // --- Mes applications UserScript : Lichess Anti-misclic ---
        snippets.add(CodeSnippet.builder().project(userscripts).section("UserScript Lichess")
                .title("En-tête et persistance : GM_getValue / GM_setValue")
                .slug("userscript-lichess-meta").language("javascript")
                .description("Métadonnées UserScript (Tampermonkey) et persistance de l'état du bouton anti-misclic entre les sessions.")
                .code("""
// ==UserScript==
// @name         Lichess Anti-Misclic
// @namespace    http://tampermonkey.net/
// @version      2
// @description  Ajoute un délai anti-misclick sur Lichess avec un bouton d'activation
// @author       yglsan
// @match        https://lichess.org/*
// @grant        GM_setValue
// @grant        GM_getValue
// @license      OpenGPL 3.0
// ==/UserScript==

(function() {
    'use strict';
    let antiMisclickEnabled = GM_getValue("antiMisclickEnabled", true);

    function toggleMisclickProtection() {
        antiMisclickEnabled = !antiMisclickEnabled;
        GM_setValue("antiMisclickEnabled", antiMisclickEnabled);
        updateButton();
    }

    function updateButton() {
        const button = document.getElementById("toggleMisclickButton");
        if (!button) return;
        button.innerText = antiMisclickEnabled ? "🔴 Anti-Misclick ON" : "⚫ Anti-Misclick OFF";
        button.style.backgroundColor = antiMisclickEnabled ? "#28a745" : "#dc3545";
    }
    // ...
})();
""").sortOrder(1).build());
        snippets.add(CodeSnippet.builder().project(userscripts).section("UserScript Lichess")
                .title("Détection du misclic : délai 750 ms, highlight de la case")
                .slug("userscript-lichess-logic").language("javascript")
                .description("Logique anti-misclic : si deux clics sur l'échiquier en moins de 750 ms, le second est ignoré ; sinon le coup est validé.")
                .code("""
const clickDelay = 750; // ms
const highlightColor = "rgba(255, 0, 0, 0.5)";
let selectedSquare = null;
let lastClickTime = 0;

document.addEventListener('click', (event) => {
    if (!antiMisclickEnabled) return;
    const now = Date.now();
    const square = event.target.closest('.square');
    if (!square) return;

    if (selectedSquare) {
        if (now - lastClickTime < clickDelay) {
            console.log("Misclick détecté : mouvement annulé !");
            resetSelection();
            return;
        }
        console.log("Coup validé : " + selectedSquare.dataset.san + " -> " + square.dataset.san);
        resetSelection();
    } else {
        selectedSquare = square;
        lastClickTime = now;
        highlightSquare(selectedSquare);
    }
});

function highlightSquare(square) {
    square.style.backgroundColor = highlightColor;
    setTimeout(() => {
        if (square === selectedSquare) square.style.backgroundColor = "";
    }, clickDelay);
}

function resetSelection() {
    if (selectedSquare) selectedSquare.style.backgroundColor = "";
    selectedSquare = null;
}
""").sortOrder(2).build());

        // --- Noublipo (Flutter) : liste de courses, Firebase, MultiProvider ---
        snippets.add(CodeSnippet.builder().project(noublipo).section("Point d'entrée")
                .title("Bootstrap : Firebase, SharedPreferences, MultiProvider (liste, rappels, gamification)")
                .slug("noublipo-main").language("dart")
                .description("Initialisation asynchrone : Firebase (sync multi-appareils), StorageService, ReminderService, et injection de nombreux providers (ListProvider, PremiumProvider, GamificationProvider, etc.).")
                .code("""
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  SyncService? syncService;
  try {
    await Firebase.initializeApp();
    syncService = SyncService();
  } catch (_) {
    syncService = null; // Mode local si Firebase indisponible
  }
  final prefs = await SharedPreferences.getInstance();
  final storage = StorageService(prefs);
  final reminderService = ReminderService();
  final planningProvider = PlanningProvider(storage, reminderService);
  final birthdaysProvider = BirthdaysProvider(storage, reminderService);
  runApp(
    MultiProvider(
      providers: [
        Provider.value(value: storage),
        ChangeNotifierProvider(create: (_) => PremiumProvider(storage)),
        ChangeNotifierProvider.value(value: planningProvider),
        ChangeNotifierProvider.value(value: birthdaysProvider),
        ChangeNotifierProvider(create: (_) => ListProvider(storage, syncService, reminderService, planningProvider.updateRecurringLastChecked)),
        ChangeNotifierProvider(create: (_) => CategoryNamesProvider(storage)),
        ChangeNotifierProvider(create: (_) => SettingsProvider(storage)),
        ChangeNotifierProvider(create: (_) => GamificationProvider(storage)),
        ChangeNotifierProvider(create: (_) => ConsumptionProfileProvider(storage)),
      ],
      child: const NoublipoApp(),
    ),
  );
}
""").sortOrder(1).build());

        // --- ManyFaces (Flutter) : RPG, providers, i18n ---
        snippets.add(CodeSnippet.builder().project(manyfaces).section("Point d'entrée")
                .title("Main : ErrorWidget personnalisé, SharedPreferences, MultiProvider (Game, Character, Locale)")
                .slug("manyfaces-main").language("dart")
                .description("Démarrage avec ErrorWidget.builder pour afficher les erreurs en production (texte copiable), puis MultiProvider avec GameProvider, CharacterProvider et LocaleProvider pour l'i18n.")
                .code("""
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  ErrorWidget.builder = (FlutterErrorDetails details) {
    return Material(
      color: Colors.red.shade900,
      child: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text('ERREUR (copiez ce texte)', style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold)),
              SizedBox(
                height: 300,
                child: SingleChildScrollView(
                  child: SelectableText(
                    '${details.exception}\\n\\n${details.stack ?? ''}',
                    style: const TextStyle(color: Colors.white, fontFamily: 'monospace'),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  };
  final prefs = await SharedPreferences.getInstance();
  runApp(MyApp(prefs: prefs));
}

class MyApp extends StatelessWidget {
  final SharedPreferences prefs;
  const MyApp({super.key, required this.prefs});
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => GameProvider(prefs)),
        ChangeNotifierProvider(create: (_) => CharacterProvider()),
        ChangeNotifierProvider(create: (_) => LocaleProvider(prefs)),
      ],
      child: Consumer<LocaleProvider>(
        builder: (context, localeProvider, _) {
          return MaterialApp(
            title: 'ManyFaces',
            theme: AppTheme.lightTheme,
            darkTheme: AppTheme.darkTheme,
            themeMode: ThemeMode.system,
            locale: localeProvider.locale,
            supportedLocales: AppTranslations.supportedLocales,
            localizationsDelegates: const [
              GlobalMaterialLocalizations.delegate,
              AppLocalizations.delegate,
            ],
            home: const HomeScreen(),
          );
        },
      ),
    );
  }
}
""").sortOrder(1).build());

        // --- MoodCast (Flutter) : météo / ambiance ---
        snippets.add(CodeSnippet.builder().project(moodcast).section("Point d'entrée")
                .title("Main : initialisation locale (date FR), NotificationService, MaterialApp")
                .slug("moodcast-main").language("dart")
                .description("Point d'entrée minimal : formatage des dates en français, mise à jour des rappels de routine via NotificationService, puis lancement de l'app avec thème et HomeShell.")
                .code("""
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await initializeDateFormatting('fr_FR', null);
  await NotificationService.updateRoutineReminders();
  runApp(const MoodCastApp());
}

class MoodCastApp extends StatelessWidget {
  const MoodCastApp({super.key});
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MoodCast & WorldFlow',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.light,
      home: const HomeShell(),
    );
  }
}
""").sortOrder(1).build());

        // --- Carned Beef (Flutter) : partitions, Riverpod, go_router ---
        snippets.add(CodeSnippet.builder().project(carnedBeef).section("Point d'entrée")
                .title("Main : initialisation des services (achats, pubs, son, échantillons), ProviderScope, MaterialApp.router")
                .slug("carnedbeef-main").language("dart")
                .description("Bootstrap asynchrone des services métier (PurchaseService, AdsService, SoundService, FreeSamplesService), puis app avec Riverpod (ConsumerWidget), thème et go_router.")
                .code("""
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  try {
    await PurchaseService().initialize();
    await AdsService().initialize();
    await SoundService().initialize();
    await FreeSamplesService().importFreeSamplesIfNeeded();
    runApp(
      const ProviderScope(
        child: CarnedBeefApp(),
      ),
    );
  } catch (e, stackTrace) {
    rethrow;
  }
}

class CarnedBeefApp extends ConsumerWidget {
  const CarnedBeefApp({super.key});
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final smartThemeMode = ref.watch(smartThemeModeProvider);
    final locale = ref.watch(localeProvider);
    return MaterialApp.router(
      title: 'Carned Beef',
      debugShowCheckedModeBanner: false,
      theme: AppThemeEnhanced.lightTheme,
      darkTheme: AppThemeEnhanced.darkTheme,
      themeMode: smartThemeMode,
      locale: locale,
      localizationsDelegates: [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: AppLocalizations.supportedLocales,
      routerConfig: AppRouter.router,
    );
  }
}
""").sortOrder(1).build());

        // --- PloufPlouf (Flutter) : thème Material 3 et scroll ---
        snippets.add(CodeSnippet.builder().project(ploufplouf).section("Point d'entrée et UI")
                .title("MaterialApp : thème Material 3 (ColorScheme), ScrollConfiguration (BouncingScrollPhysics)")
                .slug("ploufplouf-theme").language("dart")
                .description("Application avec thème clair/sombre (ColorScheme.fromSeed), cartes et boutons arrondis, et ScrollConfiguration pour un défilement tactile fluide sur mobile.")
                .code("""
class PloufPloufApp extends StatelessWidget {
  const PloufPloufApp({super.key});
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'PloufPlouf',
      debugShowCheckedModeBanner: false,
      theme: _buildTheme(Brightness.light),
      darkTheme: _buildTheme(Brightness.dark),
      themeMode: ThemeMode.system,
      builder: (context, child) {
        return ScrollConfiguration(
          behavior: ScrollConfiguration.of(context).copyWith(
            physics: const BouncingScrollPhysics(
              parent: AlwaysScrollableScrollPhysics(),
            ),
            scrollbars: true,
            overscroll: true,
          ),
          child: child!,
        );
      },
      home: const TirageEquipesPage(),
    );
  }
  static ThemeData _buildTheme(Brightness brightness) {
    final isDark = brightness == Brightness.dark;
    final colorScheme = ColorScheme.fromSeed(
      seedColor: const Color(0xFF1E3A5F),
      brightness: brightness,
      primary: const Color(0xFF1E3A5F),
      secondary: const Color(0xFF3B5998),
      surface: isDark ? const Color(0xFF1E1E1E) : const Color(0xFFF5F5F5),
    );
    return ThemeData(
      useMaterial3: true,
      colorScheme: colorScheme,
      fontFamily: 'Roboto',
      appBarTheme: AppBarTheme(centerTitle: true, elevation: 0, ...),
      cardTheme: CardThemeData(
        elevation: isDark ? 0 : 2,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
        ...),
      filledButtonTheme: FilledButtonThemeData(
        style: FilledButton.styleFrom(
          minimumSize: const Size(72, 48),
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(14)),
          ...),
      ),
    );
  }
}
""").sortOrder(1).build());

        // --- PloufPlouf : modèle et algorithme de répartition ---
        snippets.add(CodeSnippet.builder().project(ploufplouf).section("Modèle et logique")
                .title("Classe Eleve et répartition équilibrée (genre, incompatibilités, semi-choix)")
                .slug("ploufplouf-equipes").language("dart")
                .description("Modèle Eleve (prénom, nom, participe, volontaire, genre) avec displayName ; algorithme _faireEquipes : répartition filles/garçons, respect des incompatibilités et des choix \"être avec\".")
                .code("""
class Eleve {
  String prenom;
  String nom;
  bool participe;
  bool volontaire;
  String? genre; // \"F\" = fille, \"M\" = garçon

  Eleve({this.prenom = '', this.nom = '', this.participe = false, this.volontaire = false, this.genre});

  String get displayName {
    final p = prenom.trim();
    final n = nom.trim();
    if (p.isEmpty && n.isEmpty) return '';
    if (p.isEmpty) return n;
    if (n.isEmpty) return p;
    return '$p $n';
  }
}

void _faireEquipes(int nbEquipes) {
  final participantIndices = [for (var i = 0; i < _eleves.length; i++) if (_eleves[i].participe) i];
  if (participantIndices.length < nbEquipes) { /* SnackBar erreur */ return; }
  final equipesIndices = List.generate(nbEquipes, (_) => <int>[]);

  if (_repartirFillesGarcons) {
    final filles = [for (final i in participantIndices) if (_eleves[i].genre == 'F') i];
    final garcons = [for (final i in participantIndices) if (_eleves[i].genre == 'M') i];
    final autres = [for (final i in participantIndices) if (_eleves[i].genre != 'F' && _eleves[i].genre != 'M') i];
    filles.shuffle(_random);
    garcons.shuffle(_random);
    autres.shuffle(_random);
    for (var i = 0; i < filles.length; i++) equipesIndices[i % nbEquipes].add(filles[i]);
    for (var i = 0; i < garcons.length; i++) equipesIndices[i % nbEquipes].add(garcons[i]);
    for (var i = 0; i < autres.length; i++) equipesIndices[i % nbEquipes].add(autres[i]);
    violations = _corrigerIncompatiblesDansEquipes(equipesIndices, nbEquipes);
  } else {
    participantIndices.shuffle(_random);
    for (final idx in participantIndices) {
      var bestTeam = -1, bestSize = -1;
      for (var t = 0; t < nbEquipes; t++) {
        if (!_estIncompatibleAvecEquipe(idx, equipesIndices[t])) {
          final size = equipesIndices[t].length;
          if (bestTeam == -1 || size < bestSize) { bestTeam = t; bestSize = size; }
        }
      }
      if (bestTeam == -1) { /* équipe la moins remplie */ }
      equipesIndices[bestTeam].add(idx);
    }
  }
  setState(() { _equipesResultat = ...; });
}
""").sortOrder(2).build());

        codeSnippetRepository.saveAll(snippets);
        log.info("{} snippets de code enregistrés", snippets.size());

        List<Skill> skills = List.of(
                // Backend
                Skill.builder().name("Java").category("Backend").level(90).sortOrder(1).keywords(List.of("Jakarta EE", "Spring Boot", "JPA", "Maven")).build(),
                Skill.builder().name("Spring Boot").category("Backend").level(88).sortOrder(2).keywords(List.of("REST", "Security", "Data JPA", "Validation")).build(),
                Skill.builder().name("Python").category("Backend").level(85).sortOrder(3).keywords(List.of("Streamlit", "pgvector", "Dokos")).build(),
                Skill.builder().name("Tomcat").category("Backend").level(75).sortOrder(4).keywords(List.of("Serveur d'applications", "Java EE")).build(),
                Skill.builder().name("Frappe / ERPNext").category("Backend").level(70).sortOrder(5).keywords(List.of("Framework Python", "ERP", "Frappe")).build(),
                Skill.builder().name("Chatbot RAG").category("Backend").level(78).sortOrder(6).keywords(List.of("DokiLight", "Recherche sémantique", "pgvector", "Scoring hybride")).build(),
                // Frontend
                Skill.builder().name("Vue.js").category("Frontend").level(88).sortOrder(1).keywords(List.of("Vue 3", "Composition API", "Pinia", "Vite", "I18n")).build(),
                Skill.builder().name("Tailwind CSS").category("Frontend").level(85).sortOrder(2).keywords(List.of("Utility-first", "Responsive")).build(),
                Skill.builder().name("JavaScript").category("Frontend").level(86).sortOrder(3).keywords(List.of("ES6+", "UserScript", "DOM", "Fetch")).build(),
                Skill.builder().name("HTML / CSS").category("Frontend").level(88).sortOrder(4).keywords(List.of("Sémantique", "Accessibilité", "Responsive")).build(),
                // Mobile
                Skill.builder().name("Flutter / Dart").category("Mobile").level(82).sortOrder(1).keywords(List.of("MoodCast", "Carned Beef", "Noublipo", "ManyFaces", "PloufPlouf")).build(),
                // Data
                Skill.builder().name("PostgreSQL").category("Data").level(80).sortOrder(1).keywords(List.of("SQL", "pgvector", "Migrations")).build(),
                Skill.builder().name("MySQL").category("Data").level(75).sortOrder(2).keywords(List.of("SQL", "SGBD")).build(),
                Skill.builder().name("SQL Server").category("Data").level(70).sortOrder(3).keywords(List.of("Microsoft", "T-SQL", "SGBD")).build(),
                // DevOps
                Skill.builder().name("Docker").category("DevOps").level(78).sortOrder(1).keywords(List.of("Conteneurisation", "Déploiement")).build(),
                Skill.builder().name("Kubernetes").category("DevOps").level(70).sortOrder(2).keywords(List.of("Orchestration", "Pods")).build(),
                Skill.builder().name("Ansible").category("DevOps").level(72).sortOrder(3).keywords(List.of("Load balancing", "Déploiement")).build(),
                Skill.builder().name("Jenkins").category("DevOps").level(75).sortOrder(4).keywords(List.of("CI", "Pipelines")).build(),
                Skill.builder().name("GitHub Actions / CI-CD").category("DevOps").level(80).sortOrder(5).keywords(List.of("CI/CD", "Workflows")).build(),
                Skill.builder().name("GitLab").category("DevOps").level(78).sortOrder(6).keywords(List.of("CI/CD", "Registry", "Dépôts")).build(),
                Skill.builder().name("Prometheus").category("DevOps").level(72).sortOrder(7).keywords(List.of("Monitoring", "Métriques", "Alerting")).build(),
                Skill.builder().name("Grafana").category("DevOps").level(72).sortOrder(8).keywords(List.of("Tableaux de bord", "Visualisation", "Monitoring")).build(),
                Skill.builder().name("AWS").category("DevOps").level(68).sortOrder(9).keywords(List.of("Cloud", "EC2", "S3", "Déploiement")).build(),
                Skill.builder().name("VirtualBox").category("DevOps").level(75).sortOrder(10).keywords(List.of("Virtualisation", "VM", "Environnements de test")).build(),
                Skill.builder().name("SSH").category("DevOps").level(82).sortOrder(11).keywords(List.of("Sécurisation", "Accès distant", "Clés", "Tunnels")).build(),
                // Méthodes
                Skill.builder().name("SCRUM").category("Méthodes").level(85).sortOrder(1).keywords(List.of("Agilité", "Sprints")).build(),
                Skill.builder().name("Kanban").category("Méthodes").level(82).sortOrder(2).keywords(List.of("Agilité", "Flux", "Tableaux")).build(),
                // Outils
                Skill.builder().name("Git").category("Outils").level(88).sortOrder(1).keywords(List.of("GitHub", "GitLab", "CI/CD")).build(),
                Skill.builder().name("Bash").category("Outils").level(80).sortOrder(2).keywords(List.of("Lignes de commande", "Scripting", "Linux")).build(),
                Skill.builder().name("SonarQube").category("Outils").level(72).sortOrder(3).keywords(List.of("Qualité de code", "Vérifications", "Dette technique")).build()
        );
        skillRepository.saveAll(skills);
        log.info("DataInitializer: {} compétences enregistrées", skills.size());
    }
}
