describe('Proyecto veterinaria', () => {
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
  it('Registrar Servicio', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#Servicios').click()
    cy.location('pathname').should('eq','/menu-servicio')
    cy.get('#btnGestionarServicio').click()
    cy.location('pathname').should('eq','/gestionServicio')
    cy.get('#btnRegistrarServicio').click()
    cy.location('pathname').should('eq','/formularioServicio')
    cy.get('#descripcion').type('Baño')
    cy.get('#precio').clear()
    cy.get('#precio').type('30')
    cy.get('#tipoMascota').select('Perro')
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/gestionServicio')  
  })
  it('Editar Servicio', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#Servicios').click()
    cy.location('pathname').should('eq','/menu-servicio')
    cy.get('#btnGestionarServicio').click()
    cy.location('pathname').should('eq','/gestionServicio')
    cy.get('#editarServ').click()
    cy.location('pathname').should('eq','/formularioServicio/1')
    cy.get('#descripcion').clear()
    cy.get('#descripcion').type('Baño')
    cy.get('#precio').clear()
    cy.get('#precio').type('20')
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/gestionServicio')  
  })
  it('Ingresar Catalogo Servicio', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#Servicios').click()
    cy.location('pathname').should('eq','/menu-servicio')
    cy.get('#btnCatalogoServicio').click()
    cy.location('pathname').should('eq','/catalogoServicios')
    cy.get('#btnRegresar').click()
  })
  /*
  it('Eliminar Servicio', () => { 
    cy.visit(Cypress.env('base_url8'))
    cy.get('#btn-eliminar-servicio').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })*/
  it('Cerrar Sesion Usuario Default', () => {
    
    cy.visit(Cypress.env('base_url6'))
    cy.get('#logout') .click()
    cy.location('pathname').should('eq','/login')
  })
})
