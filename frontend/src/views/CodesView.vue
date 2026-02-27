<script setup>
import { ref, onMounted, watch } from 'vue'
import { api } from '@/api/service'

const projects = ref([])
const selectedProject = ref(null)
const snippets = ref([])
const loading = ref(true)
const loadingSnippets = ref(false)

onMounted(async () => {
  try {
    projects.value = await api.getProjects()
    if (projects.value.length) {
      selectedProject.value = projects.value[0]
    }
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
})

async function loadSnippets(project) {
  selectedProject.value = project
  if (!project) return
  loadingSnippets.value = true
  try {
    snippets.value = await api.getSnippetsByProjectId(project.id)
  } catch (e) {
    snippets.value = []
  } finally {
    loadingSnippets.value = false
  }
}

watch(selectedProject, (p) => {
  if (p) loadSnippets(p)
}, { immediate: true })
</script>

<template>
  <div>
    <h1 class="text-2xl sm:text-3xl font-bold text-portfolio-text mb-6">Codes</h1>
    <p class="text-portfolio-muted mb-6">
      Extraits de code issus des projets, classés par thème (classes métier, méthodes, architecture).
    </p>
    <div v-if="loading" class="text-portfolio-muted">Chargement…</div>
    <div v-else class="flex flex-col lg:flex-row gap-6">
      <aside class="lg:w-56 flex-shrink-0">
        <p class="text-sm font-medium text-portfolio-muted mb-2">Projet</p>
        <ul class="space-y-1">
          <li
            v-for="p in projects"
            :key="p.id"
          >
            <button
              type="button"
              class="w-full text-left px-3 py-2 rounded-lg text-sm transition"
              :class="selectedProject?.id === p.id ? 'bg-portfolio-accent/20 text-portfolio-accent' : 'text-portfolio-muted hover:bg-white/5 hover:text-portfolio-text'"
              @click="loadSnippets(p)"
            >
              {{ p.title }}
            </button>
          </li>
        </ul>
      </aside>
      <div class="flex-1 min-w-0">
        <div v-if="loadingSnippets" class="text-portfolio-muted">Chargement des extraits…</div>
        <div v-else-if="!snippets.length" class="text-portfolio-muted">
          Aucun extrait pour ce projet.
        </div>
        <ul v-else class="space-y-6">
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
      </div>
    </div>
  </div>
</template>
