// https://docs.cypress.io/guides/guides/plugins-guide.html

// if you need a custom webpack configuration you can uncomment the following import
// and then use the `file:preprocessor` event
// as explained in the cypress docs
// https://docs.cypress.io/api/plugins/preprocessors-api.html#Examples

/* eslint-disable import/no-extraneous-dependencies, global-require, arrow-body-style */
// const webpack = require('@cypress/webpack-preprocessor')

const { Pool } = require('pg')

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'bcra',
  password: 'password',
  port: 5432,
})

module.exports = (on, config) => {
  // on('file:preprocessor', webpack({
  //  webpackOptions: require('@vue/cli-service/webpack.config'),
  //  watchOptions: {}
  // }))

  on("task", {
    query ({ sql, values }) {
        return pool.query( sql, values )
    }
  })

  return Object.assign({}, config, {
    fixturesFolder: 'tests/e2e/fixtures',
    integrationFolder: 'tests/e2e/specs',
    screenshotsFolder: 'tests/e2e/screenshots',
    videosFolder: 'tests/e2e/videos',
    supportFile: 'tests/e2e/support/index.js'
  })
}
