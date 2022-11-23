describe('Proyecto veterinaria - Gestion_Empleado', () => {
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
  it('Entrar al Gestionar Rol', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#btnRegRol').click({force:true})
    cy.location('pathname').should('eq', '/GestionRol')
  })
  it('Registrar Rol Cajero', () => {
      
    cy.visit(Cypress.env('base_url3'))
    cy.get('#btnRegistrarRoles').click({force:true})
    cy.location('pathname').should('eq','/formularioRol')
    cy.get('#nombre').type('Cajero')
    .should('have.value','Cajero');
    cy.get('#btn-registrar').click({force:true})
    cy.location('pathname').should('eq','/GestionRol')  
  })
  it('Modificar Rol Cajero', () => {
      
    cy.visit(Cypress.env('base_url3'))
    cy.get('#btn-editar-rol').click()
    cy.location('pathname').should('contain','/formularioRol/')
    cy.get('#nombre').clear().type('Medico')
    .should('have.value','Medico');
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/GestionRol')  
  })
  it('Registrar Empleado', () => {
      
    cy.visit(Cypress.env('base_url2'))
    cy.get('#btnRegEmpleado').click()
    cy.location('pathname').should('eq','/formularioEmpleado')
    cy.get('#dni').type('73189018')
    cy.get('#nombre').type('Adriana')
    cy.get('#apellido').type('Valverde')
    cy.get('#direccion').type('Av. Los Jardines 123')
    cy.get('#email').type('adriana17@gmail.com')
    cy.get('#celular').type('987654321')
    cy.get('#sexo').select('M')
    cy.get('#fechaNacimiento').type('2002-04-12')
    cy.get('#usuario').type('Adri17')
    cy.get('#password').type('123')
    cy.get("[name=\'tipoRol\']").select('7').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get('#btn-registrar').click()
  })
  it('Editar Empleado', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#btnEditar').click()
    cy.location('pathname').should('eq','/formularioEmpleado/1')
    cy.get('#dni').clear()
    cy.get('#dni').type('73189018')
    cy.get('#nombre').clear()
    cy.get('#nombre').type('Adriana')
    cy.get('#apellido').clear()
    cy.get('#apellido').type('Valverde')
    cy.get('#direccion').clear()
    cy.get('#direccion').type('Av. Los Jardines 123')
    cy.get('#email').clear()
    cy.get('#email').type('adriana17@gmail.com')
    cy.get('#celular').clear()
    cy.get('#celular').type('987654321')
    cy.get('#sexo').select('F')
    cy.get('#fechaNacimiento').type('2002-04-12')
    cy.get('#usuario').clear()
    cy.get('#usuario').type('Adri17')
    cy.get('#password').clear()
    cy.get('#password').type('123')
    cy.get("[name=\'tipoRol\']").select('7').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get('#btn-registrar').click()
  })
  it('Ver Empleado', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#btn-ver').click()
    cy.location('pathname').should('eq','/detalleEmpleado/1')
    cy.get('#btn-regresar').click()
    cy.location('pathname').should('eq','/gestionAdmin')  
  })
  /*
  it('Eliminar Empleado', () => { 
    cy.visit(Cypress.env('base_url2'))
    cy.get('#btn-eliminar-empleado').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })
  it('Eliminar Rol Cajero', () => { 
    cy.visit(Cypress.env('base_url3'))
    cy.get('#btn-eliminar-rol').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })*/
  it('Cerrar Sesion Usuario Default', () => {
    
    cy.visit(Cypress.env('base_url6'))
    cy.get('#logout') .click()
    cy.location('pathname').should('eq','/login')
  })
})
