import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/Register.vue')
    },
    {
      path: '/account',
      name: 'account',
      component: () => import('../views/CreateAccount.vue')
    },
    {
      path: '/signin',
      name: 'signin',
      component: () => import('../views/SignIn.vue')
    },
    {
      path: '/tabs',
      name: 'tabs',
      component: () => import('../views/Tabs.vue')
    },
    {
      path: '/consent',
      name: 'consent',
      component: () => import('../views/Consent.vue')
    },
    {
      path: '/questionnaire/:section',
      name: 'riskAssessment',
      component: () => import('../views/RiskAssessment.vue')
    },
    {
      path: '/submit',
      name: 'submit',
      component: () => import('../views/Submit.vue')
    },
    {
      path: '/referral',
      name: 'referral',
      component: () => import('../views/Referral.vue')
    },
    {
      path: '/participant-details',
      name: 'participantDetails',
      component: () => import('../views/ParticipantDetails.vue')
    },
    {
      path: '/end',
      name: 'end',
      component: () => import('../views/End.vue')
    },
    {
      path: '/reset/init',
      name: 'passwordResetInit',
      component: () => import('../views/PasswordResetInit.vue')
    },
    {
      path: '/reset/finish',
      name: 'passwordResetFinish',
      component: () => import('../views/PasswordResetFinish.vue')
    },
    {
      path: '/canriskreport',
      name: 'canRiskReport',
      component: () => import('../views/CanRisk.vue')
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})

export default router
