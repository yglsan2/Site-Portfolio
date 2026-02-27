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
                        .title("Barrel (UserScript MCD)")
                        .slug("barrel-userscript")
                        .description("Petite app MCD en JavaScript (UserScript). Génération de schémas côté client.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("JavaScript", "UserScript"))
                        .sortOrder(4)
                        .repoUrl("https://github.com/yglsan2/Barrel")
                        .build(),
                Project.builder()
                        .title("Noublipo (NopList)")
                        .slug("noublipo")
                        .description("Application liste de courses / shopping list.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(5)
                        .repoUrl("https://github.com/yglsan2/Noublipo")
                        .build(),
                Project.builder()
                        .title("ManyFaces")
                        .slug("manyfaces")
                        .description("Logiciel de création de personnages et PNJ pour jeux de rôle.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(6)
                        .repoUrl("https://github.com/yglsan2/RPGproject-Flutter-3-me-application-sous-flutter-")
                        .build(),
                Project.builder()
                        .title("MoodCast")
                        .slug("moodcast")
                        .description("Application Flutter (météo / ambiance).")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(7)
                        .repoUrl("https://github.com/yglsan2/MoodCast")
                        .build(),
                Project.builder()
                        .title("Carned Beef")
                        .slug("carned-beef")
                        .description("Partage de partitions musicales.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(8)
                        .repoUrl("https://github.com/yglsan2/CarnedBeef")
                        .build(),
                Project.builder()
                        .title("PloufPlouf")
                        .slug("ploufplouf")
                        .description("Logiciel de tirage au sort pédagogique.")
                        .type(Project.ProjectType.SOFTWARE)
                        .technologies(List.of("Flutter", "Dart"))
                        .sortOrder(9)
                        .repoUrl("https://github.com/yglsan2/PloufPlouf")
                        .build(),
                Project.builder()
                        .title("Mes applications UserScript")
                        .slug("userscripts")
                        .description("Ensemble d’extensions utilisateur en JavaScript (UserScript) : Lichess, etc.")
                        .type(Project.ProjectType.OPEN_SOURCE)
                        .technologies(List.of("JavaScript", "UserScript"))
                        .sortOrder(10)
                        .repoUrl("https://github.com/yglsan2/Mes-applications-Userscript-JS-")
                        .build()
        ));

        Project barrelMcd = projects.get(0);
        Project lumieresUkraine = projects.get(1);
        Project dokiLight = projects.get(2);

        List<CodeSnippet> snippets = new ArrayList<>();
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Classes et constructeurs")
                .title("Classe Python et __init__")
                .slug("barrel-classe").language("python")
                .description("Classe Entity du projet BarrelMCD : constructeur __init__, attributs name et attributes pour modéliser une entité MCD.")
                .code("""
class Entity:
    def __init__(self, name: str):
        self.name = name
        self.attributes: list[str] = []

    def add_attribute(self, attr_name: str) -> None:
        self.attributes.append(attr_name)
""").sortOrder(1).build());
        snippets.add(CodeSnippet.builder().project(barrelMcd).section("Fonctionnalité complète")
                .title("Génération d'un schéma MCD")
                .slug("barrel-generate").language("python")
                .description("Génération du schéma MCD à partir de la liste d'entités (projet BarrelMCD-python).")
                .code("""
def generate_mcd_diagram(entities: list[Entity]) -> str:
    lines = []
    for entity in entities:
        lines.append(f"[ {entity.name} ]")
        for attr in entity.attributes:
            lines.append(f"  - {attr}")
        lines.append("")
    return "\\n".join(lines)
""").sortOrder(2).build());

        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Vue 3 et bibliothèque")
                .title("Composant Vue 3 : liste de livres")
                .slug("ukraine-vue-livres").language("javascript")
                .description("Lumières d'Ukraine : interface Vue 3 (Composition API), affichage de la bibliothèque virtuelle, réactivité avec ref/computed, Pinia.")
                .code("""
<script setup>
import { ref, computed } from 'vue'
import { useBookStore } from '@/stores/books'

const bookStore = useBookStore()
const books = computed(() => bookStore.books)

function selectBook(book) {
  bookStore.setSelected(book)
}
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
    <article v-for="book in books" :key="book.id" @click="selectBook(book)">
      <h3>{{ book.title }}</h3>
      <p>{{ book.author }}</p>
    </article>
  </div>
</template>
""").sortOrder(1).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Internationalisation (i18n)")
                .title("5 langues : FR, EN, UK, DE, PL")
                .slug("ukraine-i18n").language("javascript")
                .description("Plateforme multilingue Lumières d'Ukraine : Vue I18n, sélecteur de langue.")
                .code("""
import { createI18n } from 'vue-i18n'

const i18n = createI18n({
  legacy: false,
  locale: 'fr',
  fallbackLocale: 'en',
  messages: {
    fr: { nav: { books: 'Livres', events: 'Événements' } },
    en: { nav: { books: 'Books', events: 'Events' } },
    uk: { nav: { books: 'Книги', events: 'Події' } },
    de: { nav: { books: 'Bücher', events: 'Veranstaltungen' } },
    pl: { nav: { books: 'Książki', events: 'Wydarzenia' } }
  }
})
""").sortOrder(2).build());
        snippets.add(CodeSnippet.builder().project(lumieresUkraine).section("Backend Spring Boot")
                .title("API livres et événements, sécurité JWT")
                .slug("ukraine-spring").language("java")
                .description("Backend Lumières d'Ukraine : Spring Boot, JWT, endpoints bibliothèque et événements.")
                .code("""
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> list(
            @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(bookService.findAll(lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
""").sortOrder(3).build());

        snippets.add(CodeSnippet.builder().project(dokiLight).section("RAG et recherche")
                .title("Chatbot RAG : recherche et scoring")
                .slug("dokilight-rag").language("python")
                .description("DokiLight : recherche vectorielle et scoring hybride pour retrouver les sections de documentation pertinentes.")
                .code("""
# Recherche par similarité sémantique (pgvector) + scoring conceptuel
def find_relevant_sections(query: str, top_k: int = 5) -> list[Section]:
    embedding = embed(query)
    candidates = vector_store.search(embedding, top_k=top_k * 2)
    scored = [(s, hybrid_score(s, query)) for s in candidates]
    return sorted(scored, key=lambda x: -x[1])[:top_k]
""").sortOrder(1).build());

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
