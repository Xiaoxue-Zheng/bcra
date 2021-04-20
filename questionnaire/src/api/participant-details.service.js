import ApiService from './api.service'

export const ParticipantDetailsService = {
  updateParticipantDetails (participantDetails) {
    return ApiService.post('participants/details', participantDetails)
  }
}
