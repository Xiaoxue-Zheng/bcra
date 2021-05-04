Cypress.Commands.add('setNumberDontKnowAnswer', (identifier, value) => {
  const elementId = '#' + identifier
  if (value === 'dontknow') {
    cy.get(elementId).check({force: true})
  }
  else {
    cy.get(elementId).parent().parent().find('[type="number"]').clear()
    cy.get(elementId).parent().parent().find('[type="number"]').type(value)
  }
})

Cypress.Commands.add('checkNumberDontKnowAnswer', (identifier, value) => {
  const elementId = '#' + identifier
  if (value === 'dontknow') {
    cy.get(elementId).parent().parent().find('[type="number"]').should('have.value', '')
    cy.get(elementId).should('be.selected')
  }
  else {
    cy.get(elementId).parent().parent().find('[type="number"]').should('have.value', value.toString())
    cy.get(elementId).should('not.be.selected')
  }
})

Cypress.Commands.add('setRadioAnswerItem', itemIdentifier => {
  const elementId = '#' + itemIdentifier
  cy.get(elementId).check({force: true})
})

Cypress.Commands.add('checkRadioAnswerItemIsChecked', itemIdentifier => {
  const elementId = '#' + itemIdentifier
  cy.get(elementId).should('be.checked')
})

Cypress.Commands.add('checkRadioAnswerItemIsNotChecked', itemIdentifier => {
  const elementId = '#' + itemIdentifier
  cy.get(elementId).should('not.be.checked')
})

Cypress.Commands.add('resetCheckboxAnswerItems', (identifier) => {
  const elementId = '#' + identifier
  cy.get(elementId).find('input').each((input, value, collection) => {
    cy.wrap(input).uncheck({force: true})
  })
})

Cypress.Commands.add('setCheckboxAnswerItems', (identifier, itemIdentifiers) => {
  const elementId = '#' + identifier
  cy.get(elementId).find('input').each((input, value, collection) => {
    if (itemIdentifiers.includes(input[0].id)) {
      cy.wrap(input).check({force: true})
    } else {
      cy.wrap(input).uncheck({force: true})
    }
  })
})

Cypress.Commands.add('checkCheckboxAnswerItems', (identifier, itemIdentifiers) => {
  const elementId = '#' + identifier
  cy.get(elementId).find('input').each((input, value, collection) => {
    if (itemIdentifiers.includes(input[0].id)) {
      cy.wrap(input).should('be.checked')
    } else {
      cy.wrap(input).should('not.be.checked')
    }
  })
})

Cypress.Commands.add('setNumberAnswer', (identifier, value) => {
  const elementId = '#' + identifier
  if (value === '') {
    cy.get(elementId).clear()

  } else {
    cy.get(elementId).type(value)
  }
})

Cypress.Commands.add('checkNotReferred', () => {
  cy.url().should('not.equal', Cypress.config().baseUrl + 'submit')
})

Cypress.Commands.add('checkReferredWithCorrectReason', (reason) => {
  cy.url().should('equal', Cypress.config().baseUrl + 'submit')
  cy.contains(reason).should('be.visible')
})

Cypress.Commands.add('checkNumberAnswer', (identifier, value) => {
  const elementId = '#' + identifier
  cy.get(elementId).should('have.value', value.toString())
})

Cypress.Commands.add('setNumberDropdownAnswer', (identifier, value) => {
  const elementId = '#' + identifier
  cy.get(elementId).select(value.toString())
  cy.get(elementId).should('have.value', value.toString())
})

Cypress.Commands.add('checkElementVisibility', (expectedVisibility, identifier) => {
  const elementId = '#' + identifier
  let visibilityClause = expectedVisibility ? 'be.visible' : 'not.be.visible'
  cy.get(elementId).should(visibilityClause)
})

Cypress.Commands.add('checkNumberDropdownAnswer', (identifier, value) => {
  const elementId = '#' + identifier
  let expectedValue = (value === null) ? null : value.toString()
  cy.get(elementId).should('have.value', expectedValue)
})

Cypress.Commands.add('setNumberHeightWeight', (identifier, units, bigValue, smallValue) => {
  const elementId = '#' + identifier
  cy.get(elementId).find('select').select(units.toString())
  if (bigValue === '') {
    cy.get(elementId).find('input').first().clear()
  } else {
    cy.get(elementId).find('input').first().type(bigValue)
  }
  if (smallValue === '') {
    cy.get(elementId).find('input').last().clear()
  } else if (smallValue) {
    cy.get(elementId).find('input').last().type(smallValue)
  }
})

Cypress.Commands.add('checkNumberHeightWeight', (identifier, units, bigValue, smallValue) => {
  const elementId = '#' + identifier
  cy.get(elementId).find('select').should('have.value', units)
  cy.get(elementId).find('input').first().should('have.value', bigValue.toString())

  if (smallValue) {
    cy.get(elementId).find('input').last().should('have.value', smallValue.toString())
  }
})

Cypress.Commands.add('checkNumberAnswerValue', (answers, identifier, value, units) => {
  const answer = answers.find(answer => {
    return answer.identifier === identifier
  })
  expect(answer.number).to.equal(value)
  if (units) {
    expect(answer.units).to.equal(units)
  }
})

Cypress.Commands.add('checkAnswerItemValues', (answerItems, identifier, itemIdentifiers) => {

  const items = answerItems.filter(answerItem => {
    return answerItem.identifier === identifier
  })

  expect(items).to.have.length.at.least(2)

  for (const item of items) {
    if (itemIdentifiers.includes(item.item_identifier)) {
      expect(item.selected).to.equal(true)
    } else {
      expect(item.selected).to.equal(false)
    }
  }
})
