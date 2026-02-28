<script setup>
/**
 * Bloc de code avec bouton "Copier". Utilise l'API Clipboard ; try/catch pour les environnements sans clipboard.
 */
import { ref } from 'vue'

const props = defineProps({
  code: { type: String, default: '' },
  language: { type: String, default: '' },
})

const copied = ref(false)

async function copyCode() {
  if (!props.code) return
  try {
    await navigator.clipboard.writeText(props.code)
    copied.value = true
    setTimeout(() => { copied.value = false }, 2000)
  } catch (e) {
    copied.value = false
    if (import.meta.env.DEV && console?.warn) console.warn('[CodeBlock] copy failed (e.g. HTTPS required):', e)
  }
}
</script>

<template>
  <div class="code-block">
    <div class="code-block-header flex items-center justify-between gap-2">
      <span>{{ language }}</span>
      <button
        type="button"
        class="copy-btn touch-target min-h-[44px] min-w-[44px] sm:min-w-0 text-portfolio-muted hover:text-portfolio-accent transition-colors text-xs font-medium px-3 py-2 sm:px-2 sm:py-1 rounded-lg hover:bg-white/5 active:bg-white/10"
        :aria-label="copied ? 'Copié' : 'Copier le code'"
        @click="copyCode"
      >
        {{ copied ? 'Copié !' : 'Copier' }}
      </button>
    </div>
    <div class="code-block-content">
      <pre class="m-0"><code>{{ code }}</code></pre>
    </div>
  </div>
</template>
