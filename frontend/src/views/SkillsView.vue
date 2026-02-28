<script setup>
import { ref, onMounted, computed, nextTick } from 'vue'
import { api } from '@/api/service'

const skills = ref([])
const loading = ref(true)
const error = ref(null)
const barsVisible = ref(false)
const skillsRef = ref(null)

const byCategory = computed(() => {
  const map = {}
  for (const s of skills.value) {
    const cat = s.category || 'Autre'
    if (!map[cat]) map[cat] = []
    map[cat].push(s)
  }
  return map
})

const categoriesOrder = ['Backend', 'Frontend', 'Mobile', 'Data', 'DevOps', 'Méthodes', 'Outils', 'Autre']

async function load() {
  loading.value = true
  error.value = null
  try {
    skills.value = await api.getSkills()
  } catch (e) {
    const msg = e.message || 'Impossible de charger les compétences.'
    error.value = msg
    if (import.meta.env.DEV && console?.error) console.error('[SkillsView] load failed:', e)
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await load()
  await nextTick()
  const observer = new IntersectionObserver(
    ([entry]) => {
      if (entry?.isIntersecting) barsVisible.value = true
    },
    { threshold: 0.1, rootMargin: '0px 0px -30px 0px' }
  )
  if (skillsRef.value) observer.observe(skillsRef.value)
})
</script>

<template>
  <div>
    <h1 class="page-title text-2xl sm:text-3xl font-bold text-portfolio-text mb-2">Compétences</h1>
    <p class="text-portfolio-muted text-sm sm:text-base mb-8">Technologies et niveaux maîtrisés.</p>
    <div v-if="loading" class="text-portfolio-muted flex items-center gap-2">
      <span class="inline-block w-2 h-2 rounded-full bg-portfolio-accent animate-pulse" />
      Chargement…
    </div>
    <div v-else-if="error" class="space-y-3">
      <p class="text-red-400">{{ error }}</p>
      <button type="button" class="touch-target-inline text-sm text-portfolio-accent hover:underline focus-visible-ring rounded px-3 py-2" @click="load">Réessayer</button>
    </div>
    <div ref="skillsRef" v-else class="space-y-10">
      <section
        v-for="(cat, catIndex) in categoriesOrder.filter(c => byCategory[c])"
        :key="cat"
        :class="['opacity-0 animate-fade-in-up', `stagger-${Math.min(catIndex + 1, 10)}`]"
      >
        <h2 class="text-lg font-semibold text-portfolio-accent mb-4 flex items-center gap-2">
          <span class="w-1 h-5 rounded-full bg-portfolio-accent/80" aria-hidden="true" />
          {{ cat }}
        </h2>
        <ul class="grid gap-4 sm:grid-cols-2 xl:grid-cols-3">
          <li
            v-for="s in byCategory[cat]"
            :key="s.id"
            class="card-pro"
          >
            <div class="flex justify-between items-start mb-2">
              <span class="font-medium text-portfolio-text">{{ s.name }}</span>
              <span class="text-sm tabular-nums text-portfolio-muted">{{ s.level }}%</span>
            </div>
            <div class="h-2 rounded-full bg-white/10 overflow-hidden">
              <div
                class="h-full rounded-full bg-gradient-to-r from-portfolio-accent to-portfolio-accent-hover transition-all duration-1000 ease-out"
                :style="{ width: barsVisible ? `${s.level}%` : '0%' }"
              />
            </div>
            <div v-if="s.keywords?.length" class="mt-2 flex flex-wrap gap-1.5">
              <span
                v-for="k in s.keywords"
                :key="k"
                class="text-xs px-2 py-0.5 rounded-lg bg-white/5 text-portfolio-muted border border-white/[0.04]"
              >
                {{ k }}
              </span>
            </div>
          </li>
        </ul>
      </section>
    </div>
  </div>
</template>
