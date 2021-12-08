import ApiService from './api.service'

export const PageViewAuditService = {
  async logPageView (pageIdentifier) {
    return await ApiService.post('page-view-audit', { 'pageIdentifier': pageIdentifier } )
  }
}
