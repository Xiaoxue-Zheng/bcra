// ***********************************************************
// This example support/index.js is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './database-commands'
import './answer-commands'
import './navigation-commands'
import './study-generation-commands'
import './register-participant-commands'
import './page-completion-shortcuts'
import './local-storage-persistence-commands'
import './can-risk-commands'
import 'cypress-promise/register'

// Alternatively you can use CommonJS syntax:
// require('./commands')
Cypress.Cookies.defaults({
  whitelist: "XSRF-TOKEN",
  preserve: "session_id"
})
