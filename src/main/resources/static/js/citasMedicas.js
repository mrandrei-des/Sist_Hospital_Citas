const filtroEstado = document.getElementById('filtEstado');
const filtroEspecialidad = document.getElementById('filtEspecialidades');
const filtroMedicos = document.getElementById('filtMedicos');
const filtroFechaInicio = document.getElementById('filtFechaInicio');
const filtroFechaFin = document.getElementById('filtFechaFin');

filtroEstado.addEventListener('change', ()=> {
    refrescarTablaCitas();
});

filtroEspecialidad.addEventListener('change', ()=> {
    const especialidadSeleccionada = filtroEspecialidad.value;
    if(especialidadSeleccionada !== '') {
        actualizarOptionesMedico(especialidadSeleccionada);
        filtroMedicos.value = '0';
    }
    refrescarTablaCitas();
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
    option.setAttribute('selected', true);
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
    refrescarTablaCitas();
});

filtroFechaInicio.addEventListener('change', ()=> {
    rangoFechasValido();
    refrescarTablaCitas();
});

filtroFechaFin.addEventListener('change', ()=> {    
    rangoFechasValido();
    refrescarTablaCitas();
});


function rangoFechasValido() {
    if(filtroFechaInicio.value != '' && filtroFechaFin.value != '') {
        let [annoIni, mesIni, diaIni] = filtroFechaInicio.value.split('-');
        let [annoFin, mesFin, diaFin] = filtroFechaFin.value.split('-');

        if(parseInt(annoIni) > parseInt(annoFin)) filtroFechaFin.value = '';
        if(parseInt(annoIni) == parseInt(annoFin)) {
            if(parseInt(mesIni) > parseInt(mesFin)) filtroFechaFin.value = '';
            if(parseInt(mesIni) == parseInt(mesFin)) {
                if(parseInt(diaIni) > parseInt(diaFin)) filtroFechaFin.value = '';
            };
        }
    }
}

function refrescarTablaCitas() {
    const objFiltros = {
        "filtEstado": filtroEstado.value == '0' ? null : filtroEstado.value,
        "filtEspecialidad": filtroEspecialidad.value == '0' ? null : filtroEspecialidad.value,
        "filtMedico": filtroMedicos.value == '0' ? null : filtroMedicos.value,
        "filtFechaInicio": filtroFechaInicio.value == '' ? null : filtroFechaInicio.value,
        "filtFechaFin": filtroFechaFin.value == '' ? null : filtroFechaFin.value
    };

    consultarCitasAplicandoFiltros(objFiltros);
}

// "/api/citas/filter"
async function consultarCitasAplicandoFiltros(citasMedicasFiltrosDTO){
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    const endPoint = '/api/citas/filter';
    const response = await fetch(endPoint, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(citasMedicasFiltrosDTO)
    });
    const responseData = await response.json();
    if(response.ok) {
        if(responseData.length > 0) {
            renderizarCitasConsultadas(responseData);
        }else {
            mostrarMensajeSinRegistros();
        }
    }else {
        const erroresEncontrados = await response.json();
        console.error(erroresEncontrados);
    }
}

function renderizarCitasConsultadas(listaCitas) {
    const contenedorMensajes = document.querySelector('.message-data__container');
    contenedorMensajes.innerHTML = '';
    const tablaCitas = document.querySelector('.table');
    const tbodyCitas = tablaCitas.querySelector('tbody');
    tbodyCitas.innerHTML = '';
    listaCitas.forEach(cita => {
        const tr = document.createElement('tr');

        let td = document.createElement('td');
        td.textContent = cita.paciente;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = cita.especialidad;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = cita.medico;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = `${cita.fechaFormateada} ${cita.hora}`;
        tr.appendChild(td);
        
        td = document.createElement('td');
        const span = document.createElement('span');
        span.classList.add('state__container');

        if(cita.idEstado == 1) {
            span.classList.add('state__container--pending');
            span.textContent = 'Pendiente';
        }else if(cita.idEstado == 2) {
            span.classList.add('state__container--active');
            span.textContent = 'Confirmada';
        }else if(cita.idEstado == 7) { 
            span.classList.add('state__container--canceled');
            span.textContent = 'Cancelada';
        }

        td.appendChild(span);
        tr.appendChild(td);

        td = document.createElement('td');
        if(cita.idEstado == 1 || cita.idEstado == 2) {
            const div = document.createElement('div');
            div.classList.add('table__action-container');

            const enlaceCancelar = document.createElement('a');
            enlaceCancelar.classList.add('btn__accion__elemento', 'btn__cancelar');
            enlaceCancelar.setAttribute('href', '/citas/cancel/' + cita.id);
            enlaceCancelar.innerHTML = 
            `
                <span class="lista__elemento__icono">
                    <i class="fa-solid fa-times"></i>
                </span>
                <span class="btn__accion__title">Cancelar</span>
            `;
            div.appendChild(enlaceCancelar);

            if(cita.idEstado == 1) {
                const enlaceConfirmar = document.createElement('a');
                enlaceConfirmar.classList.add('btn__accion__elemento', 'btn__confirmar');
                enlaceConfirmar.setAttribute('href', '/citas/confirm/' + cita.id);
                enlaceConfirmar.innerHTML = 
                `
                    <span class="lista__elemento__icono">
                        <i class="fa-solid fa-check"></i>
                    </span>
                    <span class="btn__accion__title">Confirmar</span>
                `;
                div.appendChild(enlaceConfirmar);
            }
            td.appendChild(div);
        }
        tr.appendChild(td);
        tbodyCitas.appendChild(tr);
    });
}

function mostrarMensajeSinRegistros(){
    const tablaCitas = document.querySelector('.table');
    const tbodyCitas = tablaCitas.querySelector('tbody');
    tbodyCitas.innerHTML = '';

    const contenedorMensajes = document.querySelector('.message-data__container');
    contenedorMensajes.innerHTML = '';

    const parafo = document.createElement('p');
    parafo.classList.add('message-data');
    parafo.textContent = 'No se encontraron citas con los filtros aplicados.';
    contenedorMensajes.appendChild(parafo);
}