/**
 * Configuration du routeur Vue (client-side routing).
 *
 * Définit les routes : Accueil, Projets (liste + détail par slug), Codes, Compétences, 404.
 * afterEach met à jour document.title selon meta.title pour chaque navigation.
 *
 * @module router
 */

import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/HomeView.vue'), meta: { title: 'Accueil' } },
  { path: '/projets', name: 'Projects', component: () => import('@/views/ProjectsView.vue'), meta: { title: 'Projets' } },
  { path: '/projets/:slug', name: 'ProjectDetail', component: () => import('@/views/ProjectDetailView.vue'), meta: { title: 'Projet' } },
  { path: '/codes', name: 'Codes', component: () => import('@/views/CodesView.vue'), meta: { title: 'Codes' } },
  { path: '/competences', name: 'Skills', component: () => import('@/views/SkillsView.vue'), meta: { title: 'Compétences' } },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import('@/views/NotFoundView.vue'), meta: { title: 'Page introuvable' } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.afterEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} | Portfolio` : 'Portfolio'
})

export default router
