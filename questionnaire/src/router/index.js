import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import { PageViewAuditService } from '../api/page-view-audit.service';

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      meta: { id: 'HOME' },
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      meta: { id: 'ABOUT' },
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    },
    {
      path: '/register',
      name: 'register',
      meta: { id: 'REGISTER' },
      component: () => import('../views/Register.vue')
    },
    {
      path: '/account',
      name: 'account',
      meta: { id: 'CREATE_ACCOUNT' },
      component: () => import('../views/CreateAccount.vue')
    },
    {
      path: '/signin',
      name: 'signin',
      meta: { id: 'SIGN_IN' },
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
      meta: { id: 'CONSENT' },
      component: () => import('../views/Consent.vue')
    },
    {
      path: '/questionnaire/:section',
      name: 'riskAssessment',
      meta: { id: 'RISK_ASSESSMENT' },
      component: () => import('../views/RiskAssessment.vue')
    },
    {
      path: '/submit',
      name: 'submit',
      meta: { id: 'RISK_ASSESSMENT_SUBMIT' },
      component: () => import('../views/Submit.vue')
    },
    {
      path: '/referral',
      name: 'referral',
      meta: { id: 'REFERRAL' },
      component: () => import('../views/Referral.vue')
    },
    {
      path: '/participant-details',
      name: 'participantDetails',
      meta: { id: 'PARTICIPANT_DETAILS' },
      component: () => import('../views/ParticipantDetails.vue')
    },
    {
      path: '/end',
      name: 'end',
      meta: { id: 'RISK_ASSESSMENT_COMPLETE' },
      component: () => import('../views/End.vue')
    },
    {
      path: '/reset/init',
      name: 'passwordResetInit',
      meta: { id: 'PASSWORD_RESET_BEGIN' },
      component: () => import('../views/PasswordResetInit.vue')
    },
    {
      path: '/reset/finish',
      name: 'passwordResetFinish',
      meta: { id: 'PASSWORD_RESET_COMPLETE' },
      component: () => import('../views/PasswordResetFinish.vue')
    },
    {
      path: '/canriskreport',
      name: 'canRiskReport',
      meta: { id: 'CAN_RISK_REPORT' },
      component: () => import('../views/CanRisk.vue')
    },
    {
      path: '/accessibility',
      name: 'accessibility',
      meta: { id: 'ACCESSIBILITY' },
      component: () => import('../views/Accessibility.vue')
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
router.beforeEach((to, from, next) => {
  if (to.meta && to.meta.id) {
    PageViewAuditService.logPageView(to.meta.id);
  }

  next()
});

export default router
