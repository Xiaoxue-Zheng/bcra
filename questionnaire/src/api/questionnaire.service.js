import ApiService from './api.service'

export const QuestionnaireService = {
  get () {
    return ApiService.get('questionnaires')
  }
}
