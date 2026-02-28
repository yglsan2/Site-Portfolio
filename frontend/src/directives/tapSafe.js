/**
 * Directive v-tap-safe (sur un conteneur type main) :
 * si l’utilisateur a glissé le doigt au-delà du seuil, le clic sur un lien/bouton est ignoré.
 * Améliore la justesse tap vs scroll sur tactile.
 */
const THRESHOLD = 14

/** Extrait la position du doigt depuis un événement touch (touchstart/touchend). */
function getTouchPoint(e) {
  if (e.touches?.length) return { x: e.touches[0].clientX, y: e.touches[0].clientY }
  if (e.changedTouches?.length) return { x: e.changedTouches[0].clientX, y: e.changedTouches[0].clientY }
  return null
}

/** Distance euclidienne entre deux points (pour détecter scroll vs tap). */
function distance(a, b) {
  if (!a || !b) return 0
  return Math.hypot(b.x - a.x, b.y - a.y)
}

/** Directive : mounted enregistre les listeners, unmounted les retire. */
export default {
  mounted(el) {
    let start = null
    let scrollDetected = false

    const onTouchStart = (e) => {
      start = getTouchPoint(e)
      scrollDetected = false
    }

    const onTouchEnd = (e) => {
      if (!start) return
      const end = getTouchPoint(e)
      if (distance(start, end) > THRESHOLD) scrollDetected = true
      start = null
    }

    const onClick = (e) => {
      const inside = el.contains(e.target)
      if (inside && scrollDetected) {
        e.preventDefault()
        e.stopPropagation()
      }
      scrollDetected = false
    }

    el._tapSafe = { onTouchStart, onTouchEnd, onClick }
    el.addEventListener('touchstart', onTouchStart, { passive: true })
    el.addEventListener('touchend', onTouchEnd, { passive: true })
    el.addEventListener('click', onClick, true)
  },

  unmounted(el) {
    const { onTouchStart, onTouchEnd, onClick } = el._tapSafe || {}
    if (onTouchStart) el.removeEventListener('touchstart', onTouchStart)
    if (onTouchEnd) el.removeEventListener('touchend', onTouchEnd)
    if (onClick) el.removeEventListener('click', onClick)
    delete el._tapSafe
  },
}
