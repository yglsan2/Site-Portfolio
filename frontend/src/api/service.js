const API_BASE = import.meta.env.VITE_API_URL || '/api'

async function request(path, options = {}) {
  const url = `${API_BASE}${path}`
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options,
  })
  if (!res.ok) {
    const err = new Error(res.statusText || 'API error')
    err.status = res.status
    throw err
  }
  return res.json()
}

export const api = {
  getProfile() {
    return request('/profile')
  },
  getProjects() {
    return request('/projects')
  },
  getProjectBySlug(slug) {
    return request(`/projects/slug/${encodeURIComponent(slug)}`)
  },
  getSnippetsByProjectId(projectId) {
    return request(`/snippets/project/${projectId}`)
  },
  getSkills() {
    return request('/skills')
  },
}
