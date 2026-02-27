<script setup>
import { ref, onMounted, computed } from 'vue'
import { api } from '@/api/service'

const skills = ref([])
const loading = ref(true)
const error = ref(null)

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

onMounted(async () => {
  try {
    skills.value = await api.getSkills()
  } catch (e) {
    error.value = e.message || 'Impossible de charger les compétences.'
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div>
    <h1 class="text-2xl sm:text-3xl font-bold text-portfolio-text mb-6">Compétences</h1>
    <div v-if="loading" class="text-portfolio-muted">Chargement…</div>
    <div v-else-if="error" class="text-red-400">{{ error }}</div>
    <div v-else class="space-y-8">
      <section
        v-for="cat in categoriesOrder.filter(c => byCategory[c])"
        :key="cat"
      >
        <h2 class="text-lg font-semibold text-portfolio-accent mb-4">{{ cat }}</h2>
        <ul class="grid gap-4 sm:grid-cols-2 xl:grid-cols-3">
          <li
            v-for="s in byCategory[cat]"
            :key="s.id"
            class="card-pro"
          >
            <div class="flex justify-between items-start mb-2">
              <span class="font-medium text-portfolio-text">{{ s.name }}</span>
              <span class="text-sm text-portfolio-muted">{{ s.level }}%</span>
            </div>
            <div class="h-1.5 rounded-full bg-white/10 overflow-hidden">
              <div
                class="h-full rounded-full bg-portfolio-accent transition-all"
                :style="{ width: `${s.level}%` }"
              />
            </div>
            <div v-if="s.keywords?.length" class="mt-2 flex flex-wrap gap-1">
              <span
                v-for="k in s.keywords"
                :key="k"
                class="text-xs px-2 py-0.5 rounded bg-white/5 text-portfolio-muted"
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
