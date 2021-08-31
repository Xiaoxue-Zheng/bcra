<template>
    <div v-if="pdfUrl">
      <iframe height="100%" width=100% :src="pdfUrl" style="overflow: hidden" ></iframe>
    </div>
</template>

<script>
export default {
  name: 'PDFViewer',
  props: ['pdfUrl']
}
</script>
<style scoped>
div {
  width: 100%;
}

iframe {
    border: none;
}
</style>
