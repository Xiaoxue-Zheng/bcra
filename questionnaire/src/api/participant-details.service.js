import ApiService from './api.service'

export const ParticipantDetailsService = {
  async updateParticipantDetails (participantDetails) {
    return await ApiService.post('participants/details', participantDetails)
  }
}
