<template>
    <div id="nav" class="home-menu pure-menu pure-menu-horizontal">
        <router-link class="pure-menu-heading" to="/">HRYWS</router-link>
        <ul class="pure-menu-list">
            <li class="pure-menu-item"><router-link class="pure-menu-link" to="/">Home</router-link></li>
            <li v-if="!authenticated" class="pure-menu-item"><router-link class="pure-menu-link" to="/register">Register</router-link></li>
            <li v-if="!authenticated" class="pure-menu-item"><router-link class="pure-menu-link" to="/signin">Sign in</router-link></li>
            <li v-if="authenticated" class="pure-menu-item"><router-link class="pure-menu-link" to="/canriskreport">CanRisk report</router-link></li>
            <li v-if="authenticated" v-on:click="logout" class="pure-menu-item"><a href="#" class="pure-menu-link">Sign out</a></li>
        </ul>
        <IdleChecker></IdleChecker>
    </div>
</template>
<script>
import { createHelpers } from 'vuex-map-fields'
import IdleChecker from '@/components/IdleChecker.vue'

const { mapFields } = createHelpers({
  getterType: 'security/isAuthenticated'
})

export default {
  components: {
    'IdleChecker': IdleChecker
  },
  computed: {
    ...mapFields([
      'authenticated'
    ])
  },
  methods: {
    async logout () {
      await this.$store.dispatch('security/logout')
      if (!this.authenticated) {
        if (this.$router.history.current.path !== '/') {
          this.$router.push('/')
        }
      }
    }
  }
}

</script>
