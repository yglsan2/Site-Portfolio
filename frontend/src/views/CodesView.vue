<script setup>
import { ref, onMounted, watch } from 'vue'
import { api } from '@/api/service'
import CodeBlock from '@/components/CodeBlock.vue'

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
    if (import.meta.env.DEV && console?.error) console.error('[CodesView] load projects failed:', e)
    projects.value = []
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
    if (import.meta.env.DEV && console?.warn) console.warn('[CodesView] loadSnippets failed:', e)
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
    <h1 class="page-title text-2xl sm:text-3xl font-bold text-portfolio-text mb-2">Codes</h1>
    <p class="text-portfolio-muted text-sm sm:text-base mb-8">
      Extraits de code issus des projets, classés par thème (classes métier, méthodes, architecture).
    </p>
    <div v-if="loading" class="text-portfolio-muted flex items-center gap-2">
      <span class="inline-block w-2 h-2 rounded-full bg-portfolio-accent animate-pulse" />
      Chargement…
    </div>
    <div v-else class="flex flex-col lg:flex-row gap-8">
      <aside class="lg:w-56 flex-shrink-0">
        <p class="text-sm font-medium text-portfolio-muted mb-3">Projet</p>
        <ul class="space-y-2">
          <li v-for="p in projects" :key="p.id">
            <button
              type="button"
              class="touch-target-inline w-full text-left min-h-[48px] px-4 py-3 rounded-xl text-sm font-medium transition-all duration-200 active:bg-white/10"
              :class="selectedProject?.id === p.id ? 'bg-portfolio-accent/20 text-portfolio-accent border border-portfolio-accent/30' : 'text-portfolio-muted hover:bg-white/5 hover:text-portfolio-text border border-transparent'"
              @click="loadSnippets(p)"
            >
              {{ p.title }}
            </button>
          </li>
        </ul>
      </aside>
      <div class="flex-1 min-w-0">
        <div v-if="loadingSnippets" class="text-portfolio-muted flex items-center gap-2">
          <span class="inline-block w-2 h-2 rounded-full bg-portfolio-accent animate-pulse" />
          Chargement des extraits…
        </div>
        <div v-else-if="!snippets.length" class="text-portfolio-muted py-8">
          Aucun extrait pour ce projet.
        </div>
        <ul v-else class="space-y-6">
          <li v-for="(s, i) in snippets" :key="s.id" :class="['card-pro opacity-0 animate-fade-in-up', `stagger-${Math.min(i + 1, 10)}`]">
            <div class="flex items-center gap-2 mb-2">
              <span class="text-xs font-medium text-portfolio-accent">{{ s.section }}</span>
              <span class="text-xs text-portfolio-muted">{{ s.language }}</span>
            </div>
            <h3 class="font-semibold text-portfolio-text mb-2">{{ s.title }}</h3>
            <p class="text-sm text-portfolio-muted mb-3">{{ s.description }}</p>
            <CodeBlock :code="s.code" :language="s.language" />
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
