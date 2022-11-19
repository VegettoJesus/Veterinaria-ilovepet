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
  it('Registrar Cita', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#Citas').click()
    cy.location('pathname').should('eq','/gestionCitas')
    cy.get('#btnRegistrarCita').click()
    cy.location('pathname').should('eq','/formularioCitas')
    cy.get("[name=\'empleado\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get("[name=\'propietario\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get("[name=\'mascota\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get("[name=\'servicio\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get('#fecha_cita').type('2022-10-19')
    cy.get('#hora_cita').select('8: 00 - 9: 00 a.m.')   
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/gestionCitas')  
  })
  it('Editar Cita', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#Citas').click()
    cy.location('pathname').should('eq','/gestionCitas')
    cy.get('#btneditarCita').click()
    cy.location('pathname').should('eq','/formularioCitas/1')
    cy.get("[name=\'empleado\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get("[name=\'propietario\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get("[name=\'mascota\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get("[name=\'servicio\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get('#fecha_cita').clear() 
    cy.get('#fecha_cita').type('2022-10-20')
    cy.get('#hora_cita').select('10: 00 - 11: 00 a.m.')  
    cy.get('#observaciones').type('La mascota se encuentra bien')

    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/gestionCitas')  
  })
  /*
  it('Eliminar Cita', () => { 
    cy.visit(Cypress.env('base_url7'))
    cy.get('#btn-eliminar-cita').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })*/
  it('Cerrar Sesion Usuario Default', () => {
    
    cy.visit(Cypress.env('base_url6'))
    cy.get('#logout') .click()
    cy.location('pathname').should('eq','/login')
  })
})
