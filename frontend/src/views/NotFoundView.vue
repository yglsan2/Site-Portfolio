<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
</script>

<template>
  <div class="max-w-md mx-auto py-16 text-center">
    <p class="text-6xl font-bold text-portfolio-muted/40 mb-4" aria-hidden="true">404</p>
    <h1 class="text-xl font-semibold text-portfolio-text mb-2">Page introuvable</h1>
    <p class="text-portfolio-muted text-sm mb-8">
      Cette page n’existe pas ou a été déplacée.
    </p>
    <router-link
      to="/"
      class="touch-target-inline inline-flex items-center gap-2 min-h-[48px] px-5 py-3 rounded-xl bg-portfolio-accent/15 text-portfolio-accent border border-portfolio-accent/30 hover:bg-portfolio-accent/25 font-medium text-sm transition-colors focus-visible-ring active:bg-portfolio-accent/20"
    >
      Retour à l’accueil
    </router-link>
  </div>
</template>
