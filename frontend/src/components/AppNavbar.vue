<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const menuOpen = ref(false)

function onKeydown(e) {
  if (e.key === 'Escape') menuOpen.value = false
}

onMounted(() => {
  window.addEventListener('keydown', onKeydown)
})
onUnmounted(() => {
  window.removeEventListener('keydown', onKeydown)
})

const navLinks = [
  { path: '/', label: 'Accueil' },
  { path: '/projets', label: 'Projets' },
  { path: '/codes', label: 'Codes' },
  { path: '/competences', label: 'Compétences' },
]

const isActive = (path) => path === '/' ? route.path === '/' : route.path.startsWith(path)
</script>

<template>
  <header class="sticky top-0 z-50 border-b border-white/[0.06] bg-portfolio-bg/90 backdrop-blur-md">
    <nav class="container-pro flex items-center justify-between h-14 sm:h-16 min-h-[44px]">
      <router-link
        to="/"
        class="touch-target-inline text-lg font-semibold text-portfolio-text hover:text-portfolio-accent transition-colors duration-200 inline-flex items-center py-2 -my-2"
      >
        Portfolio
      </router-link>

      <!-- Desktop -->
      <ul class="hidden md:flex items-center gap-1">
        <li v-for="link in navLinks" :key="link.path">
          <router-link
            :to="link.path"
            class="nav-link relative px-3 py-2 text-sm font-medium rounded-lg transition-colors duration-200"
            :class="isActive(link.path) ? 'text-portfolio-accent' : 'text-portfolio-muted hover:text-portfolio-text'"
          >
            {{ link.label }}
            <span
              v-if="isActive(link.path)"
              class="absolute bottom-1 left-3 right-3 h-0.5 rounded-full bg-portfolio-accent"
              aria-hidden="true"
            />
          </router-link>
        </li>
      </ul>

      <!-- Mobile menu button : cible tactile 44px -->
      <button
        type="button"
        class="md:hidden touch-target touch-hit-slop min-w-[48px] min-h-[48px] flex items-center justify-center -mr-2 rounded-xl text-portfolio-muted hover:text-portfolio-text hover:bg-white/5 active:bg-white/10 transition"
        aria-label="Menu"
        :aria-expanded="menuOpen"
        @click="menuOpen = !menuOpen"
      >
        <svg class="w-6 h-6 shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path v-if="!menuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </nav>

    <!-- Mobile menu -->
    <Transition name="menu">
      <div v-show="menuOpen" class="md:hidden border-t border-white/[0.06] bg-portfolio-card/80 backdrop-blur-sm py-2 px-4">
        <ul class="flex flex-col gap-1">
          <li v-for="link in navLinks" :key="link.path">
            <router-link
              :to="link.path"
              class="touch-target-inline flex items-center min-h-[48px] py-3 px-4 rounded-xl text-sm font-medium transition active:bg-white/10"
              :class="isActive(link.path) ? 'text-portfolio-accent bg-portfolio-accent/10' : 'text-portfolio-muted hover:text-portfolio-text hover:bg-white/5'"
              @click="menuOpen = false"
            >
              {{ link.label }}
            </router-link>
          </li>
        </ul>
      </div>
    </Transition>
  </header>
</template>

<style scoped>
.menu-enter-active,
.menu-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}
.menu-enter-from,
.menu-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}
</style>
