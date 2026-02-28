/**
 * Tests unitaires du composant AppNavbar.
 */
import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import AppNavbar from './AppNavbar.vue'

const routes = [
  { path: '/', name: 'Home', component: { template: '<div>Home</div>' } },
  { path: '/projets', name: 'Projects', component: { template: '<div>Projects</div>' } },
  { path: '/codes', name: 'Codes', component: { template: '<div>Codes</div>' } },
  { path: '/competences', name: 'Skills', component: { template: '<div>Skills</div>' } },
]

async function createWrapper(initialPath = '/') {
  const router = createRouter({
    history: createWebHistory(),
    routes,
  })
  const wrapper = mount(AppNavbar, {
    global: {
      plugins: [router],
    },
  })
  await router.push(initialPath)
  await router.isReady()
  return wrapper
}

describe('AppNavbar', () => {
  it('affiche le lien Portfolio vers l’accueil', async () => {
    const wrapper = await createWrapper('/')
    const brand = wrapper.find('a[href="/"]')
    expect(brand.exists()).toBe(true)
    expect(brand.text()).toMatch(/Portfolio/)
  })

  it('affiche les liens Accueil, Projets, Codes, Compétences', async () => {
    const wrapper = await createWrapper('/')
    expect(wrapper.text()).toMatch(/Accueil/)
    expect(wrapper.text()).toMatch(/Projets/)
    expect(wrapper.text()).toMatch(/Codes/)
    expect(wrapper.text()).toMatch(/Compétences/)
  })

  it('a un bouton menu avec aria-label pour le mobile', async () => {
    const wrapper = await createWrapper('/')
    const button = wrapper.find('button[aria-label="Menu"]')
    expect(button.exists()).toBe(true)
  })
})
