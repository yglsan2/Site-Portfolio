<script setup>
import { ref, onMounted } from 'vue'
import { api } from '@/api/service'

const profile = ref(null)
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const data = await api.getProfile()
    profile.value = data
  } catch (e) {
    error.value = e.message || 'Impossible de charger le profil.'
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="home-hero min-h-[70vh] flex flex-col justify-center relative overflow-hidden">
    <!-- Fond : gradient doux + motif discret -->
    <div class="absolute inset-0 bg-gradient-to-br from-portfolio-bg via-portfolio-bg to-portfolio-accent/5 pointer-events-none" />
    <div class="absolute inset-0 opacity-[0.03] pointer-events-none" style="background-image: radial-gradient(circle at 1px 1px, var(--text) 1px, transparent 0); background-size: 40px 40px;" />
    <div class="absolute top-1/4 -right-20 w-72 h-72 rounded-full bg-portfolio-accent/10 blur-3xl pointer-events-none" />
    <div class="absolute bottom-1/4 -left-20 w-96 h-96 rounded-full bg-portfolio-accent/5 blur-3xl pointer-events-none" />

    <router-link to="/projets" class="absolute top-6 right-4 sm:right-8 text-sm text-portfolio-muted hover:text-portfolio-accent transition opacity-70 hover:opacity-100 z-10">
      Voir les projets →
    </router-link>

    <div class="relative max-w-3xl w-full px-6 sm:px-10 md:px-12 lg:px-16 mx-auto">
      <div v-if="loading" class="text-portfolio-muted flex items-center gap-2">
        <span class="inline-block w-2 h-2 rounded-full bg-portfolio-accent animate-pulse" />
        Chargement…
      </div>
      <div v-else-if="error" class="text-red-400">{{ error }}</div>

      <template v-else-if="profile">
        <p class="home-item text-sm font-medium tracking-wide text-portfolio-accent uppercase mb-5" style="animation-delay: 0.1s">
          Bonjour, je suis
        </p>
        <h1 class="home-item text-4xl sm:text-5xl lg:text-6xl font-bold text-portfolio-text mb-5 tracking-tight leading-tight" style="animation-delay: 0.2s">
          {{ profile.name }}
        </h1>
        <p class="home-item text-xl sm:text-2xl text-portfolio-accent font-medium mb-8" style="animation-delay: 0.35s">
          {{ profile.title }}
        </p>
        <div class="home-item text-portfolio-muted leading-relaxed whitespace-pre-line max-w-2xl mb-12 text-balance" style="animation-delay: 0.5s">
          {{ profile.bio }}
        </div>
        <div v-if="profile.githubUrl" class="home-item flex flex-wrap gap-4" style="animation-delay: 0.65s">
          <a
            :href="profile.githubUrl"
            target="_blank"
            rel="noopener noreferrer"
            class="inline-flex items-center gap-2 px-5 py-2.5 rounded-lg bg-portfolio-accent/15 text-portfolio-accent border border-portfolio-accent/30 hover:bg-portfolio-accent/25 hover:border-portfolio-accent/50 transition-all duration-200 font-medium"
          >
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
              <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z" clip-rule="evenodd" />
            </svg>
            Voir mon GitHub
          </a>
        </div>
        <!-- Liens rapides -->
        <nav class="home-item mt-20 pt-12 border-t border-white/5 flex flex-wrap gap-8 text-sm" style="animation-delay: 0.8s">
          <router-link to="/projets" class="text-portfolio-muted hover:text-portfolio-accent transition flex items-center gap-2">
            <span class="w-1.5 h-1.5 rounded-full bg-portfolio-accent/60" /> Projets
          </router-link>
          <router-link to="/codes" class="text-portfolio-muted hover:text-portfolio-accent transition flex items-center gap-2">
            <span class="w-1.5 h-1.5 rounded-full bg-portfolio-accent/60" /> Codes
          </router-link>
          <router-link to="/competences" class="text-portfolio-muted hover:text-portfolio-accent transition flex items-center gap-2">
            <span class="w-1.5 h-1.5 rounded-full bg-portfolio-accent/60" /> Compétences
          </router-link>
        </nav>
      </template>
    </div>
  </div>
</template>

<style scoped>
.home-hero {
  padding-top: 2rem;
  padding-bottom: 4rem;
}

.home-item {
  opacity: 0;
  transform: translateY(12px);
  animation: homeFadeIn 0.6s ease-out forwards;
}

@keyframes homeFadeIn {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
