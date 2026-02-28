<script setup>
import AppNavbar from '@/components/AppNavbar.vue'
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <a href="#main" class="skip-link">Aller au contenu principal</a>
    <AppNavbar />
    <main id="main" class="flex-1 container-pro py-8 sm:py-12" tabindex="-1" v-tap-safe>
      <router-view v-slot="{ Component }">
        <transition name="page" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    <footer class="mt-auto border-t border-white/[0.06] py-8 pb-[max(2rem,env(safe-area-inset-bottom))]">
      <div class="container-pro flex flex-col sm:flex-row items-center justify-between gap-4 text-portfolio-muted text-sm">
        <p>Portfolio — Développeur full-stack & DevOps</p>
        <div class="flex items-center gap-6">
          <a href="#" class="hover:text-portfolio-accent transition-colors">Mentions</a>
          <a href="#" class="hover:text-portfolio-accent transition-colors">Contact</a>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.page-enter-active {
  transition: opacity 0.35s ease-out, transform 0.35s ease-out;
}
.page-leave-active {
  transition: opacity 0.25s ease-in, transform 0.25s ease-in;
}
.page-enter-from {
  opacity: 0;
  transform: translateY(8px);
}
.page-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}

.skip-link {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 100;
  padding: 0.75rem 1rem;
  min-height: 44px;
  background: var(--accent);
  color: var(--bg);
  font-size: 0.875rem;
  font-weight: 600;
  text-decoration: none;
  border-radius: 0 0 0.25rem 0;
  transform: translateY(-100%);
  transition: transform 0.2s;
  display: inline-flex;
  align-items: center;
}
.skip-link:focus {
  transform: translateY(0);
  outline: 2px solid currentColor;
  outline-offset: 2px;
}
</style>
