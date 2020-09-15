// External Commands - used by tests

Cypress.Commands.add('unregisterParticipant', (nhsNumber) => {
  cy.setParticipantEmail(nhsNumber, null)
  cy.setParticipantPasswordHash(nhsNumber, null)
})

Cypress.Commands.add('registerParticipant', (nhsNumber, emailAddress, passwordHash) => {
  cy.setParticipantEmail(nhsNumber, emailAddress)
  cy.setParticipantPasswordHash(nhsNumber, passwordHash)
})

Cypress.Commands.add('resetConsent', (nhsNumber) => {
  cy.resetConsentAnswers(nhsNumber)
  cy.resetConsentAnswerItems(nhsNumber)
})

Cypress.Commands.add('resetQuestionnaire', (nhsNumber) => {
  cy.resetQuestionnaireAnswers(nhsNumber)
  cy.wait(500)
  cy.resetQuestionnaireAnswerItems(nhsNumber)
  cy.wait(500)
})

Cypress.Commands.add('getQuestionnaireAnswers', (nhsNumber) => {
  return cy.task('query', {
    sql: `
      SELECT answer.*, question.identifier
      FROM answer, question, answer_group, answer_section, answer_response, procedure, participant, identifiable_data
      WHERE answer.question_id = question.id
      AND answer.answer_group_id = answer_group.id
      AND answer_group.answer_section_id = answer_section.id
      AND answer_section.answer_response_id = answer_response.id
      AND procedure.risk_assessment_response_id = answer_response.id
      AND participant.procedure_id = procedure.id
      AND participant.identifiable_data_id = identifiable_data.id
      AND identifiable_data.nhs_number = $1
    `,
    values: [nhsNumber]
  })
})

Cypress.Commands.add('getQuestionnaireAnswerItems', (nhsNumber) => {
  return cy.task('query', {
    sql: `
      SELECT answer_item.*, question.identifier, question_item.identifier AS item_identifier
      FROM answer_item, question, question_item, answer, answer_group, answer_section, answer_response, procedure, participant, identifiable_data
      WHERE answer_item.question_item_id = question_item.id
      AND question_item.question_id = question.id
      AND answer_item.answer_id = answer.id
      AND answer.answer_group_id = answer_group.id
      AND answer_group.answer_section_id = answer_section.id
      AND answer_section.answer_response_id = answer_response.id
      AND procedure.risk_assessment_response_id = answer_response.id
      AND participant.procedure_id = procedure.id
      AND participant.identifiable_data_id = identifiable_data.id
      AND identifiable_data.nhs_number = $1
    `,
    values: [nhsNumber]
  })
})


// Internal Commands - only used within this file

Cypress.Commands.add('setParticipantEmail', (nhsNumber, emailAddress) => {
  cy.task('query', {
    sql: `
      UPDATE identifiable_data
      SET email = $1
      FROM participant
      WHERE identifiable_data.id = participant.identifiable_data_id
      AND identifiable_data.nhs_number = $2
    `,
    values: [emailAddress, nhsNumber]
  })
})

Cypress.Commands.add('setParticipantPasswordHash', (nhsNumber, passwordHash) => {
  cy.task('query', {
    sql: `
      UPDATE jhi_user
      SET password_hash = $1
      FROM participant, identifiable_data
      WHERE participant.user_id = jhi_user.id
      AND identifiable_data.id = participant.identifiable_data_id
      AND identifiable_data.nhs_number = $2
    `,
    values: [passwordHash, nhsNumber]
  })
})

Cypress.Commands.add('resetConsentAnswers', (nhsNumber) => {
  cy.task('query', {
    sql: `
      UPDATE answer
      SET number = null, ticked = null, units = null
      FROM answer_group, answer_section, answer_response, procedure, participant, identifiable_data
      WHERE answer.answer_group_id = answer_group_id
      AND answer_group.answer_section_id = answer_section.id
      AND answer_section.answer_response_id = answer_response.id
      AND procedure.consent_response_id = answer_response.id
      AND participant.procedure_id = procedure.id
      AND participant.identifiable_data_id = identifiable_data.id
      AND identifiable_data.nhs_number = $1
    `,
    values: [nhsNumber]
  })
})

Cypress.Commands.add('resetConsentAnswerItems', (nhsNumber) => {
  cy.task('query', {
    sql: `
      UPDATE answer_item
      SET selected = false
      FROM answer, answer_group, answer_section, answer_response, procedure, participant, identifiable_data
      WHERE answer_item.answer_id = answer.id
      AND answer.answer_group_id = answer_group_id
      AND answer_group.answer_section_id = answer_section.id
      AND answer_section.answer_response_id = answer_response.id
      AND procedure.consent_response_id = answer_response.id
      AND participant.procedure_id = procedure.id
      AND participant.identifiable_data_id = identifiable_data.id
      AND identifiable_data.nhs_number = $1
    `,
    values: [nhsNumber]
  })
})

Cypress.Commands.add('resetQuestionnaireAnswers', (nhsNumber) => {
  cy.task('query', {
    sql: `
      UPDATE answer
      SET number = null, ticked = null, units = null
      FROM answer_group, answer_section, answer_response, procedure, participant, identifiable_data
      WHERE answer.answer_group_id = answer_group.id
      AND answer_group.answer_section_id = answer_section.id
      AND answer_section.answer_response_id = answer_response.id
      AND procedure.risk_assessment_response_id = answer_response.id
      AND participant.procedure_id = procedure.id
      AND participant.identifiable_data_id = identifiable_data.id
      AND identifiable_data.nhs_number = $1
    `,
    values: [nhsNumber]
  })
})

Cypress.Commands.add('resetQuestionnaireAnswerItems', (nhsNumber) => {
  cy.task('query', {
    sql: `
      UPDATE answer_item
      SET selected = false
      FROM answer, answer_group, answer_section, answer_response, procedure, participant, identifiable_data
      WHERE answer_item.answer_id = answer.id
      AND answer.answer_group_id = answer_group.id
      AND answer_group.answer_section_id = answer_section.id
      AND answer_section.answer_response_id = answer_response.id
      AND procedure.risk_assessment_response_id = answer_response.id
      AND participant.procedure_id = procedure.id
      AND participant.identifiable_data_id = identifiable_data.id
      AND identifiable_data.nhs_number = $1
    `,
    values: [nhsNumber]
  })

})
