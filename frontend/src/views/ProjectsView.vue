<script setup>
/**
 * Page Projets : liste des projets chargés via l'API.
 * Try/catch/finally avec log en dev en cas d'erreur.
 */
import { ref, onMounted } from 'vue'
import { api } from '@/api/service'

const projects = ref([])
const loading = ref(true)
const error = ref(null)

async function load() {
  loading.value = true
  error.value = null
  try {
    projects.value = await api.getProjects()
  } catch (e) {
    const msg = e.message || 'Impossible de charger les projets.'
    error.value = msg
    if (import.meta.env.DEV && console?.error) console.error('[ProjectsView] load failed:', e)
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<template>
  <div>
    <h1 class="page-title text-2xl sm:text-3xl font-bold text-portfolio-text mb-2">Projets</h1>
    <p class="text-portfolio-muted text-sm sm:text-base mb-8">Réalisations techniques et personnels.</p>
    <div v-if="loading" class="text-portfolio-muted flex items-center gap-2">
      <span class="inline-block w-2 h-2 rounded-full bg-portfolio-accent animate-pulse" />
      Chargement…
    </div>
    <div v-else-if="error" class="space-y-3">
      <p class="text-red-400">{{ error }}</p>
      <button type="button" class="touch-target-inline text-sm text-portfolio-accent hover:underline focus-visible-ring rounded px-3 py-2" @click="load">Réessayer</button>
    </div>
    <ul v-else class="grid gap-4 xs:gap-5 sm:grid-cols-2 lg:grid-cols-3">
      <li
        v-for="(p, i) in projects"
        :key="p.id"
        :class="['card-pro opacity-0 animate-fade-in-up', `stagger-${Math.min(i + 1, 10)}`]"
      >
        <h2 class="font-semibold text-portfolio-text mb-1">{{ p.title }}</h2>
        <p class="text-sm text-portfolio-muted mb-2">{{ p.type }}</p>
        <p class="text-sm text-portfolio-muted line-clamp-3 mb-3">{{ p.description }}</p>
        <div class="flex flex-wrap gap-2 mb-3">
          <span
            v-for="t in (p.technologies || []).slice(0, 4)"
            :key="t"
            class="text-xs px-2.5 py-1 rounded-lg bg-white/5 text-portfolio-muted border border-white/[0.04] hover:border-portfolio-accent/20 hover:text-portfolio-accent/90 transition-colors"
          >
            {{ t }}
          </span>
        </div>
        <div class="flex flex-wrap items-center gap-x-4 gap-y-2 sm:gap-y-1 text-sm pt-1">
          <router-link
            :to="{ name: 'ProjectDetail', params: { slug: p.slug } }"
            class="touch-target-inline touch-hit-slop link-accent font-medium inline-flex items-center gap-1 group py-2 -my-1"
          >
            Voir le détail
            <span class="inline-block transition-transform group-hover:translate-x-0.5">→</span>
          </router-link>
          <a
            v-if="p.repoUrl"
            :href="p.repoUrl"
            target="_blank"
            rel="noopener noreferrer"
            class="touch-target-inline touch-hit-slop link-accent inline-flex items-center gap-1.5 py-2 -my-1"
          >
            <svg class="w-4 h-4 shrink-0" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
              <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd" />
            </svg>
            GitHub
          </a>
        </div>
      </li>
    </ul>
  </div>
</template>
