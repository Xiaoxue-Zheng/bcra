import ApiService from './api.service'

export const CanRiskReportService = {
  async isParticipantsCanRiskReportReady () {
    const response = await ApiService.get('can-risk-report/participant/isready')
    return response.data
  },
  async getCanRiskReportForParticipant () {
    const response = await ApiService.get('can-risk-report/participant/')
    return response.data
  }
}
