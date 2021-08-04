import axios from 'axios'
import { API_URL } from './config'
import router from '../router/index'

export default {
  init () {
    axios.defaults.baseURL = API_URL
    axios.defaults.withCredentials = true
    axios.interceptors.response.use(function (response) {
      return response
    }, function (error) {
      if (error.response.status === 401) {
        if (!Object.is(error.response.data.path, '/api/account') && !Object.is(window.location.pathname, '/signin')) {
          router.push('/signin')
        }
      }
      return Promise.reject(error)
    })
  },

  query (resource, params) {
    return axios.get(resource, { params: params }).catch(error => {
      throw new Error(`HRYWS ApiService ${error}`)
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
      throw new Error(`HRYWS ApiService ${error}`)
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
