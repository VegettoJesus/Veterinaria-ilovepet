const { defineConfig } = require("cypress");

module.exports = defineConfig({
  env:{
    base_url: 'http://localhost:8080/login',
    base_url2: 'http://localhost:8080/gestionAdmin',
    base_url3: 'http://localhost:8080/GestionRol',
    base_url4: 'http://localhost:8080/gestionMascota',
    base_url5: 'http://localhost:8080/GestionCategoria',
    base_url6: 'http://localhost:8080/gestionProducto',
    base_url7: 'http://localhost:8080/gestionCitas',
    base_url8: 'http://localhost:8080/gestionServicio',
    base_url9: 'http://localhost:8080/gestionPropietario'
  },
  e2e: {
    experimentalSessionAndOrigin:true,
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
