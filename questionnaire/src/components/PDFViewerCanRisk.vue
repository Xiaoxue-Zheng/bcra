<template>
    <div v-if="base64Data" class="pdfContainer">
      <PDFViewer class="pdfViewer" :pdfUrl="getPDFUrlForPage6" />
      <PDFViewer class="pdfViewer" :pdfUrl="getPDFUrlForPage7" />
    </div>
</template>

<script>
import PDFViewer from '../components/PDFViewer.vue'

export default {
  name: 'PDFViewerCanRisk',
  props: ['base64Data'],
  computed: { 
    getPDFUrlFromBase64 () {
      const blob = this.base64StringToBlob(this.base64Data);
      const blobUrl = URL.createObjectURL(blob);
      return blobUrl + "#toolbar=0&navpanes=0&scrollbar=0&embedded=true"
    },
    getPDFUrlForPage6 () {
        return this.getPDFUrlForPage(6)
    },
    getPDFUrlForPage7 () {
        return this.getPDFUrlForPage(7)
    }
  },
  methods: {
    base64StringToBlob (base64Data) {
        const byteCharacters = atob(base64Data)
        const byteNumbers = new Array(byteCharacters.length)
        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i)
        }

        const byteArray = new Uint8Array(byteNumbers)
        const blob = new Blob([byteArray], {type: 'application/pdf'})
        return blob
    },
    getPDFUrlForPage(pageNumber) {
        return this.getPDFUrlFromBase64 + "&page=" + pageNumber
    }
  },
  components: {
      PDFViewer
  }
}
</script>
<style scoped>
.pdfViewer {
  float: left;
  width: 50%;
  height: 700px;
}

.pdfContainer {
    height: 700px;
    pointer-events: none;
}
</style>