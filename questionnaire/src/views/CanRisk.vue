<template>
  <div class="content">
    <h1>CanRisk Report</h1>
    <div v-if="!canRiskReportReady">
      <p>Your risk report will be available to view here once it has been uploaded.
        Please contact the study team if you have any questions.</p>
    </div>

    <div v-if="canRiskReportReady">
      <div class="loadingNotif" v-if="!canRiskReport">
        <div><strong>Loading CanRisk report, please wait...</strong></div>
        <div>Loading times vary based on the size of the report and speed of your internet connection.</div>
      </div>
      <div v-if="canRiskReport">
        <div>The pages below show your breast cancer risk in more detail. If you would like to discuss this further, or if
          you have any questions, please contact the study team.</div>
        <PDFViewerCanRisk class="canRiskPdf" :base64Data="canRiskReportBase64Data" />
      </div>
    </div>
  </div>
</template>

<script>
import { CanRiskReportService } from '@/api/can-risk-report.service'
import PDFViewerCanRisk from '../components/PDFViewerCanRisk.vue'

export default {
  data () {
    return {
      canRiskReportReady: false,
      canRiskReport: null,
      canRiskReportBase64Data: null,
      error: null
    }
  },
  async created () {
    this.canRiskReportReady = await CanRiskReportService.isParticipantsCanRiskReportReady()
    if (this.canRiskReportReady) {
      this.canRiskReport = await CanRiskReportService.getCanRiskReportForParticipant()
      this.canRiskReportBase64Data = this.canRiskReport.fileData
    }
  },
  components: {
    PDFViewerCanRisk
  },
  methods: {
  }
}
</script>

<style scoped>
  .canRiskPdf {
    margin-top: 20px;
  }

  .loadingNotif {
    text-align: center;
  }
</style>
