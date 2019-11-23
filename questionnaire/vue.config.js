if (process.env.NODE_ENV === 'production') {
  // const { WebpackWarPlugin } = require('webpack-war-plugin');

  module.exports = {
    publicPath: '/questionnaire',
    productionSourceMap: false,
    // configureWebpack: {
    //   plugins: [
    //     new WebpackWarPlugin({
    //         archiveName: 'questionnaire'
    //     })
    //   ]
    // }
  }
}