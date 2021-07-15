<template>
  <div>
    <div hidden v-if="authenticated">
      <v-idle display='none' :duration="600" @idle="onIdle" :events="['mousemove','mousedown','touchstart', 'mouseover','keydown']"/>
    </div>
    <IdleModal :modalShow="idleModalShow" title='Warning' submitButtonText='I am still using this page'
               cancelButtonText="Logout"
               @submit="continueOperation" @hideModal="stopOperationAndRedirect">
      <div>
        <h5 style="white-space: break-spaces">You have been inactive for more than 10 minutes. Your session is about to expire. Unsaved changes will be lost.</h5>
        <p style="white-space: break-spaces">Click the "I am still using this page" button below to delay time out for 15 minutes or "Logout" to end your session.</p>
      </div>
    </IdleModal>
  </div>
</template>

<script>
import { createHelpers } from 'vuex-map-fields'
import Modal from '@/components/Modal.vue'
import { SecurityService } from '@/api/security.service'

const { mapFields } = createHelpers({
  getterType: 'security/isAuthenticated'
})

export default {
  name: 'IdleChecker',
  components: {
    'IdleModal': Modal
  },
  computed: {
    ...mapFields([
      'authenticated'
    ])
  },
  data () {
    return {
      idleModalShow: false
    }
  },
  methods: {
    async logout () {
      await this.$store.dispatch('security/logout')
      if (!this.authenticated) {
        if (this.$router.history.current.path !== '/') {
          this.$router.push('/')
        }
      }
    },

    onIdle () {
      this.idleModalShow = true
    },

    continueOperation () {
      this.hideIdleModal()
      SecurityService.getUser().then(() => {
      }).catch(() => {
        this.redirectToSignIn()
      })
    },

    async stopOperationAndRedirect () {
      this.hideIdleModal()
      await this.$store.dispatch('security/logout')
      this.redirectToSignIn()
    },

    redirectToSignIn () {
      this.$router.push('/signin')
    },

    hideIdleModal () {
      this.idleModalShow = false
    }
  }
}
</script>

<style scoped>

</style>
