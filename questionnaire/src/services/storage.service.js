export const StorageService = {
  get (key) {
    var value = window.localStorage.getItem(key)
    if (value) return JSON.parse(value)
    else return null
  },

  set (key, value) {
    return window.localStorage.setItem(key, JSON.stringify(value))
  },

  remove (key) {
    window.localStorage.removeItem(key)
  },

  clear () {
    window.localStorage.clear()
  }
}
