import ApiService from './api.service'

export const ParticipantDetailsService = {
  updateParticipantDetails (participantDetails) {
    return ApiService.post('participants/details', participantDetails)
  },
  async hasCompletedParticipantDetails () {
    let completeDetails = await ApiService.get('/participants/details')
    return completeDetails.data
  }
}
