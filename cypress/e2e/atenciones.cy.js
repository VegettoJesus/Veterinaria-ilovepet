describe('Proyecto veterinaria - Citas', () => {
    beforeEach("Visitar Login Usuario Default", () => {
      cy.session("Login", () => {
        cy.visit(Cypress.env('base_url'))
        cy.get("[name='username']")
          .type("administrador@hotmail.com")
        cy.get("[name='password']")
          .type("123")
        cy.get("[type='submit']").click()
      })
    })
    it('Mascota Atendida', () => {
        cy.visit(Cypress.env('base_url10'))
        cy.get('#btneditarAtencion').click()
        cy.location('pathname').should('eq','/formularioAtencion/1')
        cy.get("[name=\'Estado\']").select('Atendido').invoke('val').then((value)=>{
            cy.log('selected value -'+value)
        })
        cy.get('#observaciones').type('Mascota con pulgas')
        cy.get('#tratamiento').type('Advantix cada 3 dias')
        cy.get('#btn-registrar').click()
        cy.location('pathname').should('eq','/gestionAtencion')  
    })
    it('Cerrar Sesion Usuario Default', () => {
      
      cy.visit(Cypress.env('base_url6'))
      cy.get('#logout') .click()
      cy.location('pathname').should('eq','/login')
    })
  })