<template>
  <div id="app">
    <Navigation></Navigation>
    <router-view/>
    <Footer></Footer>
  </div>
</template>

<script>
import '@/assets/css/content.css'
import '@/assets/css/navigation.css'
import '@/assets/css/footer.css'
import '@/assets/css/buttons.css'
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
