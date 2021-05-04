// External Commands - used by tests
Cypress.Commands.add('clearTables', (tables) => {
  clearNextTable(tables, 0)
})

function clearNextTable(tables, currentTableIx) {
  return deleteAllFromTable(tables[currentTableIx]).then(() => {
    if (currentTableIx == tables.length-1) {
      return 'DONE'
    } else {
      return clearNextTable(tables, currentTableIx+1)
    }
  })
}

function deleteAllFromTable(tableName) {
  return cy.task('query', {
      sql: "DELETE FROM " + tableName
  })
}