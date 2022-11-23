describe('Proyecto veterinaria - Productos', () => {
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
  it('Registrar Categoria', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#productos').click()
    cy.location('pathname').should('eq','/menu-producto')
    cy.get('#btnGestionarProducto').click()
    cy.location('pathname').should('eq','/gestionProducto')
    cy.get('#btnCategoria').click()
    cy.location('pathname').should('eq','/GestionCategoria')
    cy.get('#registrarCategoria').click()
    cy.location('pathname').should('eq','/formularioCategoria')
    cy.get('#nombre').type('Accesorio')
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/GestionCategoria')  
  })
  it('Editar Categoria', () => {
    cy.visit(Cypress.env('base_url5'))
    cy.get('#btnEditarCategoria').click()
    cy.location('pathname').should('eq','/formularioCategoria/1')
    cy.get('#nombre').clear()
    cy.get('#nombre').type('Comida')
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/GestionCategoria')  
  })
  it('Registrar Producto', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#productos').click()
    cy.location('pathname').should('eq','/menu-producto')
    cy.get('#btnGestionarProducto').click()
    cy.location('pathname').should('eq','/gestionProducto')
    cy.get('#btnRegistrarProducto').click()
    cy.get('#codigo').type('P001')
    cy.get('#nombre').type('Galleta de Perro')
    cy.get('#descripcion').type('Alimenta a tu mascota con las mejores galletas!')
    cy.get('#nombre_Proveedor').type('RicoCan')
    cy.get('#ruc').type('98218759201')
    cy.get('#marca').type('RicoCan')
    cy.get('#precio').clear()
    cy.get('#precio').type('25')
    cy.get('#fecha_Vencimiento').type('2022-12-03')
    cy.get('#stock').type('25')
    cy.get("[name=\'categoria\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/gestionProducto')  
  })
  it('Editar Producto', () => {
    cy.visit(Cypress.env('base_url6'))
    cy.get('#btnEditarProducto').click()
    cy.location('pathname').should('eq','/formularioProducto/1')
    cy.get('#codigo').clear()
    cy.get('#codigo').type('P001')
    cy.get('#nombre').clear()
    cy.get('#nombre').type('Comida para perro')
    cy.get('#descripcion').clear()
    cy.get('#descripcion').type('Alimenta a tu mascota con las mejor comida!')
    cy.get('#nombre_Proveedor').clear()
    cy.get('#nombre_Proveedor').type('DogShow')
    cy.get('#ruc').clear()
    cy.get('#ruc').type('98218759201')
    cy.get('#marca').clear()
    cy.get('#marca').type('DogShow')
    cy.get('#precio').clear()
    cy.get('#precio').type('25')
    cy.get('#fecha_Vencimiento').type('2022-12-03')
    cy.get('#stock').clear()
    cy.get('#stock').type('25')
    cy.get("[name=\'categoria\']").select('1').invoke('val').then((value)=>{
      cy.log('selected value -'+value)
    })
    cy.get('#btn-registrar').click()
    cy.location('pathname').should('eq','/gestionProducto')  
  })
  it('Ingresar Catalogo Producto', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#productos').click()
    cy.location('pathname').should('eq','/menu-producto')
    cy.get('#btnCatalogoProducto').click()
    cy.location('pathname').should('eq','/catalogoProducto')
    cy.get('#btnRegresarProducto').click()
    cy.location('pathname').should('eq','/menu-producto')
  })/*
  it('Eliminar Producto', () => { 
    cy.visit(Cypress.env('base_url6'))
    cy.get('#btn-eliminar-producto').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })
  it('Eliminar Categoria', () => { 
    cy.visit(Cypress.env('base_url5'))
    cy.get('#btn-eliminar-categoria').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })*/
  it('Cerrar Sesion Usuario Default', () => {
    
    cy.visit(Cypress.env('base_url6'))
    cy.get('#logout') .click()
    cy.location('pathname').should('eq','/login')
  })
})
