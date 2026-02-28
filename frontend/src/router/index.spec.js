/**
 * Tests unitaires du routeur Vue.
 */
import { describe, it, expect } from 'vitest'
import router from './index.js'

describe('router', () => {
  it('a les routes définies avec name et meta.title', () => {
    const routes = router.getRoutes()
    const names = routes.map((r) => r.name).filter(Boolean)
    expect(names).toContain('Home')
    expect(names).toContain('Projects')
    expect(names).toContain('ProjectDetail')
    expect(names).toContain('Codes')
    expect(names).toContain('Skills')
    expect(names).toContain('NotFound')
  })

  it('route / a le nom Home et meta.title Accueil', () => {
    const route = router.getRoutes().find((r) => r.path === '/')
    expect(route).toBeDefined()
    expect(route.name).toBe('Home')
    expect(route.meta?.title).toBe('Accueil')
  })

  it('route /projets a meta.title Projets', () => {
    const route = router.getRoutes().find((r) => r.path === '/projets')
    expect(route).toBeDefined()
    expect(route.meta?.title).toBe('Projets')
  })

  it('route /projets/:slug a le nom ProjectDetail', () => {
    const route = router.getRoutes().find((r) => r.path === '/projets/:slug')
    expect(route).toBeDefined()
    expect(route.name).toBe('ProjectDetail')
  })

  it('route 404 a le nom NotFound', () => {
    const route = router.getRoutes().find((r) => r.name === 'NotFound')
    expect(route).toBeDefined()
    expect(route.meta?.title).toBe('Page introuvable')
  })
})
