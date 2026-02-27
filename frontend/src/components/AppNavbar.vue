<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const menuOpen = ref(false)

const navLinks = [
  { path: '/', label: 'Accueil' },
  { path: '/projets', label: 'Projets' },
  { path: '/codes', label: 'Codes' },
  { path: '/competences', label: 'Compétences' },
]

const isActive = (path) => path === '/' ? route.path === '/' : route.path.startsWith(path)
</script>

<template>
  <header class="sticky top-0 z-50 border-b border-white/5 bg-portfolio-bg/95 backdrop-blur">
    <nav class="container-pro flex items-center justify-between h-14 sm:h-16">
      <router-link to="/" class="text-lg font-semibold text-portfolio-text hover:text-portfolio-accent transition">
        Portfolio
      </router-link>

      <!-- Desktop -->
      <ul class="hidden md:flex items-center gap-6">
        <li v-for="link in navLinks" :key="link.path">
          <router-link
            :to="link.path"
            class="text-sm font-medium transition"
            :class="isActive(link.path) ? 'text-portfolio-accent' : 'text-portfolio-muted hover:text-portfolio-text'"
          >
            {{ link.label }}
          </router-link>
        </li>
      </ul>

      <!-- Mobile menu button -->
      <button
        type="button"
        class="md:hidden p-2 text-portfolio-muted hover:text-portfolio-text"
        aria-label="Menu"
        @click="menuOpen = !menuOpen"
      >
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path v-if="!menuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </nav>

    <!-- Mobile menu -->
    <div v-show="menuOpen" class="md:hidden border-t border-white/5 py-4 px-4">
      <ul class="flex flex-col gap-2">
        <li v-for="link in navLinks" :key="link.path">
          <router-link
            :to="link.path"
            class="block py-2 px-3 rounded-lg text-sm font-medium transition"
            :class="isActive(link.path) ? 'text-portfolio-accent bg-white/5' : 'text-portfolio-muted hover:text-portfolio-text hover:bg-white/5'"
            @click="menuOpen = false"
          >
            {{ link.label }}
          </router-link>
        </li>
      </ul>
    </div>
  </header>
</template>
