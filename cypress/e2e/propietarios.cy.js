describe('Proyecto veterinaria - Propietarios', () => {
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
  it('Registrar Propietario', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#agProp').click()
    cy.location('pathname').should('eq','/gestionPropietario')
    cy.get('#btnRegPropietario').click()
    cy.location('pathname').should('eq','/formularioPropietario')
    cy.get('#nomPropietario').type('Rodrigo Jorge Alonso Gomez Velasquez')
    cy.get('#direccion').type('Av Villa Maria del Triunfo')
    cy.get('#correo').type('rodrigoval@gmail.com')
    cy.get('#celPropietario').type('954254114')
    cy.get('#btn-Propietario').click()   
  })
  it('Editar Propietario', () => {
    cy.visit(Cypress.env('base_url2'))
    cy.get('#agProp').click()
    cy.location('pathname').should('eq','/gestionPropietario')
    cy.get('#editProp').click()
    cy.location('pathname').should('eq','/formularioPropietario/1')
    cy.get('#nomPropietario').clear()
    cy.get('#nomPropietario').type('Alvaro Gongora Renteria')
    cy.get('#direccion').clear()
    cy.get('#direccion').type('Av Manzanales')
    cy.get('#correo').clear()
    cy.get('#correo').type('alvarogongora@gmail.com')
    cy.get('#celPropietario').clear()
    cy.get('#celPropietario').type('999999991')
    cy.get('#btn-Propietario').click()  
  })
  /*
  it('Eliminar Propietario', () => { 
    cy.visit(Cypress.env('base_url9'))
    cy.get('#btn-eliminar-propietario').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
    cy.get('.swal2-confirm').click({force:true})
  })*/
  it('Cerrar Sesion Usuario Default', () => {
    
    cy.visit(Cypress.env('base_url6'))
    cy.get('#logout') .click()
    cy.location('pathname').should('eq','/login')
  })
})

