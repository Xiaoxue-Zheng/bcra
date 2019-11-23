import axios from 'axios'
import { API_URL } from '@/common/config'

const RESPONSE_STATUS_UNAUTHORIZED = 401

const ApiService = {
  init () {
    axios.defaults.baseURL = API_URL
    axios.defaults.withCredentials = true
  },

  query (resource, params) {
    return axios.get(resource, params).catch(error => {
      throw new Error(`BCRA ApiService ${error}`)
    })
  },

  getTemplateFromSlug (slug, resource) {
    if (slug === '') {
      return `${resource}`
    } else {
      return `${resource}/${slug}`
    }
  },

  get (resource, slug = '') {
    var template = this.getTemplateFromSlug(slug, resource)
    return axios.get(template).catch(error => {
      throw new Error(`BCRA ApiService ${error}`)
    })
  },

  post (resource, params) {
    return axios.post(`${resource}`, params)
  },

  postFormData (url, formData) {
    return axios({
      method: 'post',
      url: url,
      data: formData,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
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

export const AnswerService = {
  create (answerResponse) {
    return ApiService.post('answer-responses', answerResponse)
  }
}

export const SecurityService = {
  login (username, password) {
    var formData = new FormData()
    formData.set('username', username)
    formData.set('password', password)
    return new Promise((resolve) => {
      ApiService.postFormData('authentication', formData).then(() => {
        resolve('SUCCESS')
      }).catch((error) => {
        if (!error.response) {
          resolve('ERROR')
        } else if (error.response.status === RESPONSE_STATUS_UNAUTHORIZED) {
          resolve('UNAUTHORIZED')
        } else {
          resolve('ERROR')
        }
      })
    })
  },
  logout () {
    return ApiService.post('logout')
  },
  getUser () {
    return ApiService.get('account')
  }
}
