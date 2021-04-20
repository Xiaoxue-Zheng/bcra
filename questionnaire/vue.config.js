if (process.env.NODE_ENV === 'production') {
  // const { WebpackWarPlugin } = require('webpack-war-plugin');

  module.exports = {
    publicPath: process.env.BASE_URL,
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