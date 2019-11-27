import axios from 'axios'
import { API_URL } from './config'

export default {
  init () {
    axios.defaults.baseURL = API_URL
    axios.defaults.withCredentials = true
  },

  query (resource, params) {
    return axios.get(resource, { params: params }).catch(error => {
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
