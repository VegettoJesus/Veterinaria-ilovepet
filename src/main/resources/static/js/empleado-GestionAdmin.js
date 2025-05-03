const Toast = Swal.mixin({
  toast: true,
  position: 'top-end',
  showConfirmButton: false,
  timer: 3500,
  timerProgressBar: true
});

$(document).ready(function () {
    $('.btn-detalle-empleado').click(function () {
      const empleadoId = $(this).data('id');
      $.ajax({
        url: '/detalleEmpleado/modal/' + empleadoId,
        method: 'GET',
        success: function (empleado) {
          const imagen = empleado.imagen ? empleado.imagen :
            (empleado.sexo === 'F' ? '../imagenes/perfil-mujer.jpg' : '../imagenes/perfil-hombre.jpg');
          
          let roles = empleado.tipoRol.map(rol => rol.nombre).join(', ');

          const contenido = `
            <div class="row">
              <div class="col-md-6" style="place-self: center;">
                <img src="${imagen}" style="width: 337px; height: 337px; object-fit: cover; border-radius: 15px;" class="img-fluid mx-auto d-block" alt="perfil">
              </div>
              <div class="col-md-6">
                <p><strong class="text-diseño-detalle">DNI:</strong> <span class="estilo-letra-mascota">${empleado.dni}</span></p>
                <p><strong class="text-diseño-detalle">Celular:</strong> <span class="estilo-letra-mascota">${empleado.celular}</span></p>
                <p><strong class="text-diseño-detalle">Dirección:</strong> <span class="estilo-letra-mascota">${empleado.direccion}</span></p>
                <p><strong class="text-diseño-detalle">Correo:</strong> <span class="estilo-letra-mascota">${empleado.email}</span></p>
                <p><strong class="text-diseño-detalle">Fecha Nacimiento:</strong> <span class="estilo-letra-mascota">${empleado.fechaNacimiento}</span></p>
                <p><strong class="text-diseño-detalle">Sexo:</strong> <span class="estilo-letra-mascota">${empleado.sexo}</span></p>
                <p><strong class="text-diseño-detalle">Usuario:</strong> <span class="estilo-letra-mascota">${empleado.usuario}</span></p>
                <p><strong class="text-diseño-detalle">Roles:</strong> <span class="estilo-letra-mascota">${roles}</span></p>
              </div>
            </div>
          `;
          $('#detalleModalLabel').text(`Detalle ${empleado.nombre} ${empleado.apellido}`);
          $('#modal-body-content').html(contenido);
          $('#detalleModal').modal('show');
        },
      });
    });  
  });

  document.addEventListener('DOMContentLoaded', function () {
    document.body.addEventListener('click', function (e) {
        if (e.target.closest('.btn-eliminar-empleado')) {
            e.preventDefault();
            const button = e.target.closest('.btn-eliminar-empleado');
            const empleadoId = button.getAttribute('data-id');
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

            if (!empleadoId) {
                return;
            }

            Swal.fire({
                title: '¿Estás seguro?',
                text: "Esta acción eliminará permanentemente al usuario.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                  fetch(`/gestionAdmin/eliminar/${empleadoId}`, {
                    method: 'DELETE',
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest',
                        [csrfHeader]: csrfToken,
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error en la solicitud DELETE');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        Swal.fire('¡Eliminado!', data.message, 'success');
                        button.closest('tr').remove();
                    } else {
                        Swal.fire('Error', data.message, 'error');
                    }
                
                    if (data.status === 'success') {
                        Toast.fire({ icon: 'success', title: data.message });
                    } else if (data.status === 'error') {
                        Toast.fire({ icon: 'error', title: data.message });
                    }
                })
                .catch(error => {
                });
                
                }
            });
        }
    });
});

function abrirModalRoles() {
  
  tablaContactos = $("#tablaRoles").DataTable({
      destroy: true, 
      searching: false,
      processing: false,
      responsive: true,
      ordering: false,
      paging: true, 
      lengthMenu: [10, 25, 50, 100], 
      ajax: {
          url: '/gestionAdmin/roles', 
          type: "GET",
          dataSrc: 'data'
      },
      columnDefs: [
        {
            targets: '_all',
            className: 'text-center estilo-letra-mascota' 
        }
      ],
      columns: [
          {
              data: 'id', 
              render: function (data) {
                  return data; 
              }
          },
          {
              data: 'nombre', 
              render: function (data) {
                  return data; 
              }
          },
          {
            data: null,
            render: function (data) {
              return "<button class=\"btn boton-tabla btn-warning\" type=\"button\" onclick=\"editar('" + data.id + "', '" + data.nombre + "');\" title=\"Editar Rol\"><span class=\"bi bi-pencil\"></span></button>&nbsp;&nbsp;&nbsp;" +
              "<button class=\"btn boton-tabla btn-danger\" type=\"button\" onclick=\"eliminar('" + data.id + "')\"><span class=\"bi bi-trash\"></span></button>";
            }
          }
      ],
      language: {
          "processing": "Procesando...",
          "zeroRecords": "No se encontraron resultados",
          "emptyTable": "No se encontraron registros",
          "search": "Buscar:",
          "info": "Mostrando _START_ a _END_ de _TOTAL_ registros",
          "lengthMenu": "Mostrar _MENU_ registros por página"
      }
  });
    $('#formRol').on('submit', function (event) {
      event.preventDefault(); 
      var idRol = $('#id').val(); 
      var nombreRol = $('#nombre').val(); 
      var csrfToken = $('input[name="_csrf"]').val();

      $.ajax({
          url: '/guardarRol',
          type: 'POST',
          data: { 
            id: idRol,
            nombre: nombreRol 
          },
          headers: {
              'X-CSRF-TOKEN': csrfToken 
          },
          success: function(response) {
            if (response.status === 'success') {
                tablaContactos.ajax.reload(null, false);  
                $('#id').val('0');
                $('#nombre').val('');
                Swal.fire('¡Registrado!', response.message, 'success');
                Toast.fire({ icon: 'success', title: response.message });
            } else {
                Swal.fire('Error', response.message, 'error');
                Toast.fire({ icon: 'error', title: response.message });
            }
          },
          error: function(xhr, status, error) {
              console.error("Error al guardar el rol:", error);
              Swal.fire('Error', 'Hubo un problema al guardar el rol', 'error');
          }
      });
  });

  $('#modalRol').modal('show');
}

function eliminar(id) {
  
  Swal.fire({
      title: '¿Estás seguro de eliminar este rol?',
      text: "Esta acción no se puede deshacer.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
  }).then((result) => {
      if (result.isConfirmed) {
        
          var csrfToken = $('input[name="_csrf"]').val();

          $.ajax({
              url: '/eliminarRol/' + id,
              type: 'GET',
              headers: {
                  'X-CSRF-TOKEN': csrfToken 
              },
              success: function(response) {
                
                  tablaContactos.row($("#row_" + id)).remove().draw();
                  Swal.fire(
                      'Eliminado!',
                      'El rol ha sido eliminado correctamente.',
                      'success'
                  );
                  tablaContactos.ajax.reload(null, false);  
                  Toast.fire({ icon: 'success', title: 'Rol eliminado con éxito' });
              },
              error: function(xhr, status, error) {
                  Swal.fire('Error', 'Hubo un problema al eliminar el rol', 'error');
              }
          });
      }
  });
}

function editar(id, nombre) {
  $('#id').val(id);  
  $('#nombre').val(nombre);  
  $('#modalRol').modal('show');
}
