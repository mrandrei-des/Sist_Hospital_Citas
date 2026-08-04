const filtroEstado = document.getElementById('filtEstado');
const filtroEspecialidad = document.getElementById('filtEspecialidades');
const filtroMedicos = document.getElementById('filtMedicos');
const filtroFechaInicio = document.getElementById('filtFechaInicio');
const filtroFechaFin = document.getElementById('filtFechaFin');
// change

filtroEstado.addEventListener('change', ()=> {

});

filtroEspecialidad.addEventListener('change', ()=> {
    const especialidadSeleccionada = filtroEspecialidad.value;
    if(especialidadSeleccionada !== '') {
        actualizarOptionesMedico(especialidadSeleccionada);
    }
});

function actualizarOptionesMedico(especialidadSeleccionada) {
    let endPoint = '/api/citas/medicos/especialidad/'
    endPoint += especialidadSeleccionada == '0' ? '' : especialidadSeleccionada

    fetch(endPoint)
        .then(response => response.json())
        .then(listaMedicos => {
            if(listaMedicos != null) {
                renderizarOptionsMedicos(listaMedicos);
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar los médicos:', error));
}

function renderizarOptionsMedicos(listaMedicos) {
    filtroMedicos.innerHTML = '';

    let option = document.createElement('option');
    option.setAttribute('value', 0);
    option.textContent = 'Todos';
    filtroMedicos.appendChild(option);
    
    listaMedicos.forEach(medico => {
        option = document.createElement('option');
        option.setAttribute('value', medico.id);
        option.textContent = `${medico.nombre} ${medico.primerApellido} ${medico.segundoApellido}`;
        filtroMedicos.appendChild(option);
    });
}

filtroMedicos.addEventListener('change', ()=> {

});

filtroFechaInicio.addEventListener('change', ()=> {
    if(rangoFechasValido(filtroFechaInicio.value, filtroFechaFin.value)) {
    }
});

filtroFechaFin.addEventListener('change', ()=> {
    if(rangoFechasValido(filtroFechaInicio.value, filtroFechaFin.value)) {

    }
});

// "/api/citas/filter"
/*
{
    "filtEstado": null,
    "filtEspecialidad": null,
    "filtMedico": 2,
    "filtFechaInicio": null,
    "filtFechaFin": null
}
*/
function rangoFechasValido(fechaInicio, fechaFin) {
    if(fechaInicio === '' || fechaFin === '') return false;
    return fechaInicio < fechaFin;
}