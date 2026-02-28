/**
 * Tests unitaires du client API (api/service.js).
 * Mock de fetch pour isoler la logique request et les méthodes api.
 */
import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { api } from './service.js'

describe('api/service', () => {
  const originalFetch = globalThis.fetch

  afterEach(() => {
    globalThis.fetch = originalFetch
  })

  describe('request (via api methods)', () => {
    it('getProfile retourne le JSON quand la réponse est OK', async () => {
      const mockProfile = { id: 1, name: 'Jane', title: 'Dev' }
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.resolve(mockProfile),
      })

      const result = await api.getProfile()

      expect(result).toEqual(mockProfile)
      expect(fetch).toHaveBeenCalledWith(
        expect.stringContaining('/profile'),
        expect.objectContaining({ headers: expect.any(Object) })
      )
    })

    it('getProjects retourne un tableau', async () => {
      const mockProjects = [{ id: 1, title: 'Projet A' }]
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.resolve(mockProjects),
      })

      const result = await api.getProjects()

      expect(result).toEqual(mockProjects)
      expect(fetch).toHaveBeenCalledWith(expect.stringContaining('/projects'), expect.any(Object))
    })

    it('getProjectBySlug encode le slug dans l’URL', async () => {
      const mockProject = { id: 1, slug: 'mon-projet' }
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.resolve(mockProject),
      })

      await api.getProjectBySlug('mon-projet')

      expect(fetch).toHaveBeenCalledWith(expect.stringMatching(/\/projects\/slug\/mon-projet/), expect.any(Object))
    })

    it('getSnippetsByProjectId appelle /snippets/project/{id}', async () => {
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.resolve([]),
      })

      await api.getSnippetsByProjectId(42)

      expect(fetch).toHaveBeenCalledWith(expect.stringMatching(/\/snippets\/project\/42/), expect.any(Object))
    })

    it('getSkills retourne la liste des compétences', async () => {
      const mockSkills = [{ id: 1, name: 'Java', category: 'Backend' }]
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.resolve(mockSkills),
      })

      const result = await api.getSkills()

      expect(result).toEqual(mockSkills)
      expect(fetch).toHaveBeenCalledWith(expect.stringContaining('/skills'), expect.any(Object))
    })

    it('lance une erreur avec status quand res.ok est false', async () => {
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: false,
        status: 404,
        statusText: 'Not Found',
      })

      await expect(api.getProfile()).rejects.toMatchObject({
        message: expect.any(String),
        status: 404,
      })
    })

    it('relance l’erreur si le JSON échoue', async () => {
      globalThis.fetch = vi.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.reject(new Error('Invalid JSON')),
      })

      await expect(api.getProfile()).rejects.toThrow('Invalid JSON')
    })
  })
})
