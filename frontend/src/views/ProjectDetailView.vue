<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '@/api/service'
import CodeBlock from '@/components/CodeBlock.vue'

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
    try {
      snippets.value = await api.getSnippetsByProjectId(project.value.id)
    } catch (e2) {
      if (import.meta.env.DEV && console?.warn) console.warn('[ProjectDetailView] snippets load failed, keeping project', e2)
      snippets.value = []
    }
  } catch (e) {
    error.value = e.status === 404 ? 'Projet non trouvé.' : (e.message || 'Erreur de chargement.')
    project.value = null
    snippets.value = []
    if (import.meta.env.DEV && console?.error) console.error('[ProjectDetailView] load failed:', e)
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
      class="touch-target-inline mb-6 text-sm text-portfolio-muted hover:text-portfolio-accent transition-colors flex items-center gap-1.5 group py-2 -my-2 active:opacity-80"
      @click="goBack"
    >
      <span class="inline-block transition-transform group-hover:-translate-x-0.5">←</span>
      Retour aux projets
    </button>

    <div v-if="loading" class="text-portfolio-muted flex items-center gap-2">
      <span class="inline-block w-2 h-2 rounded-full bg-portfolio-accent animate-pulse" />
      Chargement…
    </div>
    <div v-else-if="error" class="space-y-3">
      <p class="text-red-400">{{ error }}</p>
      <button type="button" class="touch-target-inline text-sm text-portfolio-accent hover:underline focus-visible-ring rounded px-3 py-2" @click="load">Réessayer</button>
    </div>
    <template v-else-if="project">
      <article class="max-w-3xl">
        <header class="mb-10">
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
              class="text-xs px-2.5 py-1 rounded-lg bg-white/5 text-portfolio-muted border border-white/[0.04]"
            >
              {{ t }}
            </span>
          </div>
          <div class="mt-5 flex flex-wrap gap-4">
            <a
              v-if="project.repoUrl"
              :href="project.repoUrl"
              target="_blank"
              rel="noopener noreferrer"
              class="touch-target-inline link-accent text-sm inline-flex items-center gap-1 group py-2"
            >
              Voir sur GitHub
              <span class="transition-transform group-hover:translate-x-0.5">→</span>
            </a>
            <a
              v-if="project.projectUrl"
              :href="project.projectUrl"
              target="_blank"
              rel="noopener noreferrer"
              class="touch-target-inline link-accent text-sm inline-flex items-center gap-1 group py-2"
            >
              Voir le projet
              <span class="transition-transform group-hover:translate-x-0.5">→</span>
            </a>
          </div>
        </header>

        <section v-if="snippets.length" class="mt-10">
          <h2 class="text-lg font-semibold text-portfolio-accent mb-4">Extraits de code</h2>
          <ul class="space-y-6">
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
        </section>
      </article>
    </template>
  </div>
</template>
