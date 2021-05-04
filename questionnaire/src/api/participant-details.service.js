import ApiService from './api.service'

export const ParticipantDetailsService = {
  async updateParticipantDetails (participantDetails) {
    return ApiService.post('participants/details', participantDetails)
  }
}
