import axios from 'axios'
import { API_URL } from '@/common/config'

const ApiService = {
  init () {
    axios.defaults.baseURL = API_URL
  },

  setHeader () {
    // axios.defaults.headers.common['Authorization'] =
    // `Token ${JwtService.getToken()}`
  },

  query (resource, params) {
    return axios.get(resource, params).catch(error => {
      throw new Error(`BCRA ApiService ${error}`)
    })
  },

  get (resource, slug = '') {
    var template
    if (slug === '') {
      template = `${resource}`
    } else {
      template = `${resource}/${slug}`
    }
    return axios.get(template).catch(error => {
      throw new Error(`BCRA ApiService ${error}`)
    })
  },

  post (resource, params) {
    return axios.post(`${resource}`, params)
  },

  update (resource, slug, params) {
    return axios.put(`${resource}/${slug}`, params)
  },

  put (resource, params) {
    return axios.put(`${resource}`, params)
  },

  delete (resource) {
    return axios.delete(resource).catch(error => {
      throw new Error(`[RWV] ApiService ${error}`)
    })
  }
}

export default ApiService

export const QuestionnaireService = {
  get () {
    return ApiService.get('questionnaires')
  }
}
