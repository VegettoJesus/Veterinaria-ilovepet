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
    
    const mes = $('#select-mes').val() || new Date().getMonth() + 1; 
    const anio = $('#select-anio').val() || new Date().getFullYear(); 
    
    $.ajax({
        url: '/detalleEmpleado/modal/' + empleadoId,
        method: 'GET',
        data: {
            anio: anio,
            mes: mes
        },
        success: function (data) {
            console.log(data);

            const empleado = data.empleado;
            const resumen = data.resumenAsistencia;
            const imagen = empleado.imagen ? empleado.imagen :
                (empleado.sexo === 'F' ? '../imagenes/perfil-mujer.jpg' : '../imagenes/perfil-hombre.jpg');

            let roles = (empleado.tipoRol || []).map(rol => rol.nombre).join(', ');

            let anioSelect = '<select id="select-anio" class="form-select estilo-letra-mascota">';
            let mesSelect = '<select id="select-mes" class="form-select estilo-letra-mascota">';

            // Rango de años (5 años antes hasta 5 años después)
            let currentYear = new Date().getFullYear();
            for (let i = currentYear - 5; i <= currentYear + 5; i++) {
                anioSelect += `<option value="${i}" ${i === anio ? 'selected' : ''}>${i}</option>`;
            }
            anioSelect += '</select>';

            // Meses (1-12)
            const months = [
                'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
            ];
            for (let i = 0; i < 12; i++) {
                mesSelect += `<option value="${i + 1}" ${i + 1 === mes ? 'selected' : ''}>${months[i]}</option>`;
            }
            mesSelect += '</select>';

            // Contenido dinámico del modal
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

            function renderGrafico(asistencias, tardanzas, faltas) {
                if (window.miGrafico) {
                    window.miGrafico.destroy();
                }

                const ctx = document.getElementById('graficoAsistencia').getContext('2d');
                window.miGrafico = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ['Resumen'],
                        datasets: [
                            {
                                label: 'Asistencias',
                                data: asistencias,
                                backgroundColor: 'rgb(75, 192, 192)',
                            },
                            {
                                label: 'Tardanzas',
                                data: tardanzas,
                                backgroundColor: 'rgb(255, 205, 86)',
                            },
                            {
                                label: 'Faltas',
                                data: faltas,
                                backgroundColor: 'rgb(255, 99, 132)',
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            title: {
                                display: true,
                                text: 'Resumen mensual de asistencia',
                                font: {
                                    size: 18,
                                    family: 'letra-mascotas',
                                },
                                color: 'white',
                            },
                            tooltip: {
                                backgroundColor: '#333',
                                titleFont: {
                                    size: 14,
                                    family: 'letra-mascotas'
                                },
                                bodyFont: {
                                    size: 14,
                                    family: 'letra-mascotas'
                                },
                                callbacks: {
                                    label: function(context) {
                                        return context.dataset.label + ': ' + context.raw;
                                    }
                                }
                            },
                            legend: {
                                labels: {
                                    font: {
                                        size: 14,
                                        family: 'letra-mascotas'
                                    },
                                    color: 'white'
                                }
                            }
                        },
                        scales: {
                            x: {
                                ticks: {
                                    font: {
                                        size: 14,
                                        family: 'letra-mascotas'
                                    },
                                    color: 'white'
                                },
                                grid: {
                                    color: 'rgba(255,255,255,0.1)' 
                                }                              
                            },
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    font: {
                                        size: 14,
                                        family: 'letra-mascotas'
                                    },
                                    color: 'white'
                                },
                                grid: {
                                    color: 'white'
                                }
                            }
                        }
                    }
                });
            }

            const bloqueGrafico = `
                <div class="p-4 mt-4 rounded" style="background-color: #212529; color: white;">
                    <div class="row">
                        <div class="col-md-6">
                            <label for="select-anio" class="form-label fw-bold estilo-letra-mascota">Año:</label>
                            ${anioSelect}
                        </div>
                        <div class="col-md-6">
                            <label for="select-mes" class="form-label fw-bold estilo-letra-mascota">Mes:</label>
                            ${mesSelect}
                        </div>
                    </div>
                    <canvas id="graficoAsistencia" width="400" height="200" class="mt-4"></canvas>
                </div>
            `;
            
            $('#modal-body-content2').html(bloqueGrafico);

            // Renderizar gráfico inicial
            const asistencias = [resumen.Asistencia || 0];
            const tardanzas = [resumen.Tardanza || 0];
            const faltas = [resumen.Falta || 0];
            renderGrafico(asistencias, tardanzas, faltas);

            // Actualizar gráfico cuando cambian año o mes
            $('#select-anio, #select-mes').on('change', function () {
                const nuevoAnio = $('#select-anio').val();
                const nuevoMes = $('#select-mes').val();
                
                $.ajax({
                    url: '/detalleEmpleado/modal/' + empleadoId,
                    method: 'GET',
                    data: {
                        anio: nuevoAnio,
                        mes: nuevoMes
                    },
                    success: function (data) {
                        const resumen = data.resumenAsistencia;

                        const asistencias = [resumen.Asistencia || 0];
                        const tardanzas = [resumen.Tardanza || 0];
                        const faltas = [resumen.Falta || 0];

                        renderGrafico(asistencias, tardanzas, faltas);
                    }
                });
            });

            $('#detalleModal').modal('show');
        }
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
function setFechasInicioPorSemana() {
  const hoy = new Date();
  const primerDiaMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);
  
  const diaSemana = primerDiaMes.getDay();
  const offset = diaSemana === 0 ? -6 : 1 - diaSemana;
  const lunesSemana = new Date(primerDiaMes);
  lunesSemana.setDate(primerDiaMes.getDate() + offset);
  
  const dias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'];

  const inputsFechaInicio = document.querySelectorAll('input[name="fechaInicio[]"]');
  const inputsFechaFin = document.querySelectorAll('input[name="fechaFin[]"]');

  dias.forEach((dia, index) => {
    const fecha = new Date(lunesSemana);
    fecha.setDate(lunesSemana.getDate() + index);

    const fechaISO = fecha.toISOString().split('T')[0];

    if (inputsFechaInicio[index]) inputsFechaInicio[index].value = fechaISO;
    if (inputsFechaFin[index]) inputsFechaFin[index].value = fechaISO; 
  });
}

function abrirModalHorario(empleadoId) {
  document.getElementById('empleadoIdSeleccionado').value = empleadoId || '';
  var modal = new bootstrap.Modal(document.getElementById('modalHorario'));
  modal.show();

  setFechasInicioPorSemana();

  const checkboxes = document.querySelectorAll('.descanso-checkbox');

  checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function () {
      const row = this.closest('tr');

      const horaEntrada = row.querySelector('input[name="horaEntrada[]"]');
      const horaSalida = row.querySelector('input[name="horaSalida[]"]');

      if (this.checked) {

        if (horaEntrada) {
          horaEntrada.value = '';
          horaEntrada.setAttribute('readonly', 'readonly');
        }
        if (horaSalida) {
          horaSalida.value = '';
          horaSalida.setAttribute('readonly', 'readonly');
        }
      } else {
        if (horaEntrada) horaEntrada.removeAttribute('readonly');
        if (horaSalida) horaSalida.removeAttribute('readonly');
      }
    });
  });
}

function detalleModalHorario(idEmpleado) {
  $.ajax({
    url: '/horario/mes/' + idEmpleado,
    type: 'GET',
    success: function(horarios) {
      console.log(horarios);
      let tabla = `
        <table class="table table-dark table-hover w-100 text-center">
          <thead class="table-dark">
            <tr>
              <th class="text-center text-black estilo-letra-mascota">Fecha Inicio</th>
              <th class="text-center text-black estilo-letra-mascota">Fecha Fin</th>
              <th class="text-center text-black estilo-letra-mascota">Día</th>
              <th class="text-center text-black estilo-letra-mascota">Entrada</th>
              <th class="text-center text-black estilo-letra-mascota">Salida</th>
              <th class="text-center text-black estilo-letra-mascota">Descanso</th>
              <th class="text-center text-black estilo-letra-mascota">Acciones</th>
            </tr>
          </thead>
          <tbody>`;

      horarios.forEach(h => {
        const isDescanso = (!h.horaEntrada && !h.horaSalida);
        const checked = isDescanso ? 'checked' : '';
        const readonly = isDescanso ? 'readonly' : '';
        const claseReadonly = isDescanso ? 'descanso-checkbox' : '';

        tabla += `
          <tr data-id="${h.id}">
            <td class="text-black estilo-letra-mascota">${h.fechaInicio}</td>
            <td class="text-black estilo-letra-mascota">${h.fechaFin}</td>
            <td class="text-black estilo-letra-mascota">${h.diaSemana}</td>
            <td class="text-black estilo-letra-mascota"><input type="time" class="form-control form-control-sm ${claseReadonly}" value="${h.horaEntrada || ''}" ${readonly} id="entrada-${h.id}" onchange="marcarCambio(${h.id})"></td>
            <td class="text-black estilo-letra-mascota"><input type="time" class="form-control form-control-sm ${claseReadonly}" value="${h.horaSalida || ''}" ${readonly} id="salida-${h.id}" onchange="marcarCambio(${h.id})"></td>
            <td class="text-black estilo-letra-mascota">
              <input type="checkbox" class="form-check-input descanso-checkbox" ${checked} onchange="toggleDescanso(${h.id})" id="descanso-${h.id}">
            </td>
            <td class="text-black estilo-letra-mascota">
              <button class="btn btn-success btn-sm" onclick="guardarHorario(${h.id})">Guardar</button>
            </td>
          </tr>`;
      });

      tabla += `</tbody></table>`;
      $('#contenedorHorarios').html(tabla);
      $('#detalleModalHorario').modal('show');
    },
    error: function() {
      alert("Error al cargar los horarios");
    }
  });
}

function marcarCambio(id) {
  $(`tr[data-id='${id}']`).addClass("table-warning");
}

function toggleDescanso(id) {
  const isChecked = $(`#descanso-${id}`).is(':checked');
  const entrada = $(`#entrada-${id}`);
  const salida = $(`#salida-${id}`);

  if (isChecked) {
    entrada.prop('readonly', true).addClass('descanso-checkbox').val('');
    salida.prop('readonly', true).addClass('descanso-checkbox').val('');
  } else {
    entrada.prop('readonly', false).removeClass('descanso-checkbox');
    salida.prop('readonly', false).removeClass('descanso-checkbox');
  }

  marcarCambio(id);
}

function guardarHorario(idHorario) {
  const checkbox = $(`#descanso-${idHorario}`).is(':checked');
  const entrada = checkbox ? null : $(`#entrada-${idHorario}`).val();
  const salida = checkbox ? null : $(`#salida-${idHorario}`).val();

  // Validar solo si no es descanso
  if (!checkbox && entrada && salida) {
    const horaEntrada = entrada.split(':').map(Number);
    const horaSalida = salida.split(':').map(Number);

    const minutosEntrada = horaEntrada[0] * 60 + horaEntrada[1];
    const minutosSalida = horaSalida[0] * 60 + horaSalida[1];

    if (minutosSalida <= minutosEntrada) {
      // Marcar como error
      const fila = $(`tr[data-id='${idHorario}']`);
      fila.removeClass("table-warning table-success").addClass("table-danger");

      Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'error',
        title: 'La hora de salida debe ser mayor que la hora de entrada',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
      });
      return;
    }
  }

  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  $.ajax({
    url: '/horario/actualizar',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({
      id: idHorario,
      horaEntrada: entrada,
      horaSalida: salida
    }),
    headers: {
      'X-Requested-With': 'XMLHttpRequest',
      [csrfHeader]: csrfToken,
    },
    success: function () {
      const fila = $(`tr[data-id='${idHorario}']`);
      fila.removeClass("table-warning table-danger").addClass("table-success");

      Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'success',
        title: 'Horario actualizado correctamente',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
      });
    },
    error: function (xhr) {
      Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'error',
        title: 'Error al actualizar el horario',
        text: xhr.responseText,
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
      });
    }
  });
}
