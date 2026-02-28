/**
 * Client API pour le backend du portfolio.
 *
 * Toutes les requêtes passent par la fonction `request`, qui centralise
 * l'appel fetch, la gestion d'erreur (try/catch/finally) et les logs.
 * En cas d'échec HTTP (res.ok === false), une erreur avec status est levée.
 *
 * @module api/service
 */

const API_BASE = import.meta.env.VITE_API_URL || '/api'

/**
 * En développement, log dans la console ; en production, on peut désactiver.
 * @private
 */
function log(level, message, data = null) {
  if (import.meta.env.DEV && typeof console !== 'undefined') {
    if (level === 'error') console.error('[API]', message, data ?? '')
    else if (level === 'warn') console.warn('[API]', message, data ?? '')
    else console.debug('[API]', message, data ?? '')
  }
}

/**
 * Effectue une requête HTTP GET vers l'API et retourne le JSON.
 * Un seul try pour l'appel fetch + vérification res.ok, un catch pour logger et relancer,
 * un finally pour tracer la fin du traitement.
 *
 * @param {string} path - Chemin relatif (ex. '/profile', '/projects')
 * @param {RequestInit} [options={}] - Options fetch (headers, etc.)
 * @returns {Promise<unknown>} Corps JSON de la réponse
 * @throws {Error} Si res.ok est false (err.status = code HTTP) ou si fetch/json échoue
 */
async function request(path, options = {}) {
  const url = `${API_BASE}${path}`
  log('debug', `GET ${url}`)
  try {
    const res = await fetch(url, {
      headers: { 'Content-Type': 'application/json', ...options.headers },
      ...options,
    })
    try {
      if (!res.ok) {
        const err = new Error(res.statusText || 'API error')
        err.status = res.status
        log('error', `HTTP ${res.status} ${url}`, err.message)
        throw err
      }
      const text = await res.text()
      const contentType = res.headers.get('content-type') || ''
      if (!contentType.includes('application/json') || (!text.trim().startsWith('{') && !text.trim().startsWith('['))) {
        const err = new Error(
          "L'API a renvoyé du HTML au lieu de JSON. Sur Netlify : définissez la variable VITE_API_URL (ex. https://ton-service.onrender.com/api) puis redéployez."
        )
        err.status = res.status
        log('error', `Réponse non-JSON ${url}`, text.slice(0, 200))
        throw err
      }
      const data = JSON.parse(text)
      log('debug', `OK ${url}`)
      return data
    } catch (e) {
      if (e.status != null) throw e
      log('error', `Parse/response ${url}`, e)
      throw e
    }
  } catch (e) {
    log('error', `Request failed ${url}`, e)
    throw e
  } finally {
    log('debug', `request done: ${path}`)
  }
}

/**
 * Objet API exposant les méthodes pour le profil, projets, compétences et snippets.
 */
export const api = {
  /** Récupère le profil (accueil). @returns {Promise<object>} */
  getProfile() {
    return request('/profile')
  },

  /** Récupère la liste des projets. @returns {Promise<object[]>} */
  getProjects() {
    return request('/projects')
  },

  /**
   * Récupère un projet par son slug.
   * @param {string} slug - Slug du projet (ex. "mon-projet")
   * @returns {Promise<object>}
   */
  getProjectBySlug(slug) {
    return request(`/projects/slug/${encodeURIComponent(slug)}`)
  },

  /**
   * Récupère les extraits de code d'un projet.
   * @param {number} projectId - Id du projet
   * @returns {Promise<object[]>}
   */
  getSnippetsByProjectId(projectId) {
    return request(`/snippets/project/${projectId}`)
  },

  /** Récupère la liste des compétences. @returns {Promise<object[]>} */
  getSkills() {
    return request('/skills')
  },
}
