<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '@/api/service'

const route = useRoute()
const router = useRouter()
const project = ref(null)
const snippets = ref([])
const loading = ref(true)
const error = ref(null)

const slug = computed(() => route.params.slug)

async function load() {
  if (!slug.value) return
  loading.value = true
  error.value = null
  try {
    project.value = await api.getProjectBySlug(slug.value)
    snippets.value = await api.getSnippetsByProjectId(project.value.id)
  } catch (e) {
    error.value = e.status === 404 ? 'Projet non trouvé.' : (e.message || 'Erreur de chargement.')
    project.value = null
    snippets.value = []
  } finally {
    loading.value = false
  }
}

onMounted(load)
watch(slug, load)

function goBack() {
  router.push({ name: 'Projects' })
}
</script>

<template>
  <div>
    <button
      type="button"
      class="mb-6 text-sm text-portfolio-muted hover:text-portfolio-accent transition flex items-center gap-1"
      @click="goBack"
    >
      ← Retour aux projets
    </button>

    <div v-if="loading" class="text-portfolio-muted">Chargement…</div>
    <div v-else-if="error" class="text-red-400">{{ error }}</div>
    <template v-else-if="project">
      <article class="max-w-3xl">
        <header class="mb-8">
          <h1 class="text-2xl sm:text-3xl font-bold text-portfolio-text mb-2">
            {{ project.title }}
          </h1>
          <p class="text-portfolio-muted mb-4">{{ project.type }}</p>
          <p class="text-portfolio-muted leading-relaxed whitespace-pre-line">
            {{ project.description }}
          </p>
          <div class="flex flex-wrap gap-2 mt-4">
            <span
              v-for="t in (project.technologies || [])"
              :key="t"
              class="text-xs px-2 py-1 rounded bg-white/5 text-portfolio-muted"
            >
              {{ t }}
            </span>
          </div>
          <div class="mt-4 flex flex-wrap gap-4">
            <a
              v-if="project.repoUrl"
              :href="project.repoUrl"
              target="_blank"
              rel="noopener noreferrer"
              class="link-accent text-sm"
            >
              Voir sur GitHub →
            </a>
            <a
              v-if="project.projectUrl"
              :href="project.projectUrl"
              target="_blank"
              rel="noopener noreferrer"
              class="link-accent text-sm"
            >
              Voir le projet
            </a>
          </div>
        </header>

        <section v-if="snippets.length" class="mt-10">
          <h2 class="text-lg font-semibold text-portfolio-accent mb-4">Extraits de code</h2>
          <ul class="space-y-6">
            <li v-for="s in snippets" :key="s.id" class="card-pro">
              <div class="flex items-center gap-2 mb-2">
                <span class="text-xs font-medium text-portfolio-accent">{{ s.section }}</span>
                <span class="text-xs text-portfolio-muted">{{ s.language }}</span>
              </div>
              <h3 class="font-semibold text-portfolio-text mb-2">{{ s.title }}</h3>
              <p class="text-sm text-portfolio-muted mb-3">{{ s.description }}</p>
              <pre class="p-4 rounded-lg bg-black/30 text-sm overflow-x-auto border border-white/5"><code>{{ s.code }}</code></pre>
            </li>
          </ul>
        </section>
      </article>
    </template>
  </div>
</template>
