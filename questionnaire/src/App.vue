<template>
  <div id="app">
    <Navigation></Navigation>
    <router-view/>
    <Footer></Footer>
  </div>
</template>

<script>
import '@/assets/css/buttons.css'
import '@/assets/css/content.css'
import '@/assets/css/fonts.css'
import '@/assets/css/footer.css'
import '@/assets/css/forms.css'
import '@/assets/css/navigation.css'
import '@/assets/css/components.css'
import Navigation from './components/Navigation'
import Footer from './components/Footer'
export default {
  name: 'app',
  components: {
    'Navigation': Navigation,
    'Footer': Footer
  }
}
</script>
