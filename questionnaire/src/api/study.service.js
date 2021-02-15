import ApiService from './api.service'

export const StudyService = {
  async isStudyCodeAvailable (studyCode) {
    const response = await ApiService.get('study-ids/' + studyCode)
    return response.data
  },

  async getStudyCode () {
    const response = await ApiService.get('study-ids/current')
    return response.data
  }
}
