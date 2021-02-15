import ApiService from './api.service'

export const QuestionnaireService = {
  async getConsent () {
    const response = await ApiService.get('questionnaires/consent')
    return response.data
  },

  async getRiskAssessment () {
    const response = await ApiService.get('questionnaires/risk-assessment')
    return response.data
  }
}
