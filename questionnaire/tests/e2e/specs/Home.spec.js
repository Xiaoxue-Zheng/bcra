describe('Home', () => {

  it('should load private policy', () => {
    cy.server()
    cy.visit('/')
    cy.contains('a', 'Privacy Policy - MFT').should("have.attr", "href", "https://mft.nhs.uk/privacy-policy/");
    cy.contains('a', 'Research Privacy Information - MFT').should("have.attr", "href", "https://research.cmft.nhs.uk/getting-involved/gdpr-and-research");
  })
})
