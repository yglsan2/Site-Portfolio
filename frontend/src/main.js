/**
 * Point d'entrée de l'application Vue du portfolio.
 *
 * - Charge le routeur, la directive tap-safe et les styles.
 * - Détecte le mode tactile et ajoute la classe "touch-device" sur <html>.
 * - Monte l'app sur #app avec gestion d'erreur (try/catch + log).
 *
 * @module main
 */

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import tapSafe from './directives/tapSafe'
import './assets/main.css'

/**
 * Détection explicite du mode tactile (téléphone, tablette, écran tactile).
 * Ajoute la classe "touch-device" sur <html> quand l'appareil est détecté comme tactile.
 * À quoi ça sert : adapter le layout/style en CSS, vérifier en dev, complément des media (pointer: coarse).
 */
const isTouchDevice =
  typeof window !== 'undefined' &&
  (window.matchMedia('(pointer: coarse)').matches || 'ontouchstart' in window)
if (isTouchDevice) {
  document.documentElement.classList.add('touch-device')
}

// Message discret pour les devs qui ouvrent la console
if (typeof console !== 'undefined' && console.log) {
  console.log(
    '%cPortfolio — Vue 3',
    'color: #5b8def; font-weight: 600;',
    '\nBienvenue dans la console. Bonne visite.'
  )
}

try {
  const app = createApp(App)
  app.directive('tap-safe', tapSafe)
  app.use(router).mount('#app')
} catch (e) {
  if (typeof console !== 'undefined' && console.error) {
    console.error('[Portfolio] Erreur au démarrage de l\'application:', e)
  }
  throw e
}
