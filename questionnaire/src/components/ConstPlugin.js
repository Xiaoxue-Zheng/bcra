const Constant = {
  DONT_KNOW: 'dontknow'
}
Constant.install = function (Vue) {
  Vue.prototype.$getConst = (key) => {
    return Constant[key]
  }
}
export default Constant
