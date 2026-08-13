const filtroEstado = document.getElementById('filtEstado');
const filtroEspecialidad = document.getElementById('filtEspecialidades');
const filtroMedicos = document.getElementById('filtMedicos');
const filtroFechaInicio = document.getElementById('filtFechaInicio');
const filtroFechaFin = document.getElementById('filtFechaFin');
const btnExportCSV = document.getElementById('btnExportCSV');

let paginaActual = 1;
let cantidadCitasPorPagina = 5;
let cantidadPaginas = 0;
let cantidadCitas = 0;

btnExportCSV.addEventListener('click', (e)=> {
    btnExportCSV.setAttribute('disabled', 'true');
    exportarInfoCitas();
});

filtroEstado.addEventListener('change', ()=> {
    refrescarTablaCitas(1);
});

filtroEspecialidad.addEventListener('change', ()=> {
    const especialidadSeleccionada = filtroEspecialidad.value;
    if(especialidadSeleccionada !== '') {
        actualizarOptionesMedico(especialidadSeleccionada);
        filtroMedicos.value = '0';
    }
    refrescarTablaCitas(1);
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
    refrescarTablaCitas(1);
});

filtroFechaInicio.addEventListener('change', ()=> {
    rangoFechasValido();
    refrescarTablaCitas(1);
});

filtroFechaFin.addEventListener('change', ()=> {    
    rangoFechasValido();
    refrescarTablaCitas(1);
});

document.addEventListener('DOMContentLoaded', ()=> {
    agregarPaginacionCargaInicial();
});

function agregarPaginacionCargaInicial() {
    consultarCantidadCitas();
}

async function consultarCantidadCitas() {
    const endPoint = '/api/citas/count';
    const response = await fetch(endPoint, {
        method: 'GET'
    });
    const responseData = await response.json();
    if(response.ok) {
        cantidadCitas = responseData;
        btnExportCSV.classList.add('btn__export-visible');
        cantidadPaginas = Math.ceil(cantidadCitas / cantidadCitasPorPagina);
        document.querySelector('.pagination__paragraph strong.pagination__total').textContent = responseData;
        renderizarBotonesPaginacion();
    }else {
        const erroresEncontrados = await response.json();
        console.error(erroresEncontrados);
    }
}

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

function refrescarTablaCitas(numPagina) {
    const objFiltros = {
        "filtEstado": filtroEstado.value == '0' ? null : filtroEstado.value,
        "filtEspecialidad": filtroEspecialidad.value == '0' ? null : filtroEspecialidad.value,
        "filtMedico": filtroMedicos.value == '0' ? null : filtroMedicos.value,
        "filtFechaInicio": filtroFechaInicio.value == '' ? null : filtroFechaInicio.value,
        "filtFechaFin": filtroFechaFin.value == '' ? null : filtroFechaFin.value,
        "pagina": numPagina,
        "cantidadCitasPorPagina": cantidadCitasPorPagina
    };
    paginaActual = numPagina;
    consultarCantidadCitasAplicandoFiltros(objFiltros);
    consultarCitasAplicandoFiltros(objFiltros);
}

async function consultarCantidadCitasAplicandoFiltros(citasMedicasFiltrosDTO){
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    const endPoint = '/api/citas/filter/count';
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
        cantidadCitas = responseData;
        cantidadPaginas = Math.ceil(cantidadCitas / cantidadCitasPorPagina);
        document.querySelector('.pagination__paragraph strong.pagination__total').textContent = responseData;
        renderizarBotonesPaginacion();
    }else {
        const erroresEncontrados = await response.json();
        console.error(erroresEncontrados);
    }
}

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
            renderizarBotonesPaginacion();
            btnExportCSV.classList.add('btn__export-visible');
        }else {
            btnExportCSV.classList.remove('btn__export-visible');
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
        tbodyCitas.appendChild(tr);
    });
}

function renderizarBotonesPaginacion() {
    const paginationItems = document.getElementById('paginationItems');
    paginationItems.innerHTML = '';

    for (let index = 0; index < cantidadPaginas; index++) {
        const boton = document.createElement('button');

        boton.classList.add('btn', 'btn__pagination');
        boton.classList = 'btn btn__pagination' + (paginaActual == (index + 1) ? ' btn__pagination--active' : '') ;

        boton.textContent = (index + 1);
        boton.addEventListener('click', ()=> {
            refrescarTablaCitas(index + 1);
            paginaActual = index + 1;
        });
        paginationItems.appendChild(boton);
    }
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

function paginaSiguiente() {
    if(paginaActual < cantidadPaginas) {
        paginaActual++;
        refrescarTablaCitas(paginaActual);
    }
}

function paginaAnterior() {
    if(paginaActual > 1) {
        paginaActual--;
        refrescarTablaCitas(paginaActual);
    }
}

function exportarInfoCitas() {
    const objFiltros = {
        "filtEstado": filtroEstado.value == '0' ? null : filtroEstado.value,
        "filtEspecialidad": filtroEspecialidad.value == '0' ? null : filtroEspecialidad.value,
        "filtMedico": filtroMedicos.value == '0' ? null : filtroMedicos.value,
        "filtFechaInicio": filtroFechaInicio.value == '' ? null : filtroFechaInicio.value,
        "filtFechaFin": filtroFechaFin.value == '' ? null : filtroFechaFin.value,
        "pagina": 0,
        "cantidadCitasPorPagina": 0
    };
    consultarInfoReporteCsv(objFiltros);
}

async function consultarInfoReporteCsv(citasMedicasFiltrosDTO){   
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    const endPoint = '/api/citas/reporte/export/csv';
    const response = await fetch(endPoint, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(citasMedicasFiltrosDTO)
    });
    let responseData = '';
    if(response.ok) {
        responseData = await response.blob();
        generarReporteCSV(responseData)
    }else {
        responseData = await response.json();
        const erroresEncontrados = responseData;
        console.error(erroresEncontrados);
    }
    btnExportCSV.removeAttribute('disabled');
}

function generarReporteCSV(infoReporte) {
    try {
        const urlParaDescargarReporte = window.URL.createObjectURL(infoReporte);
        const enlace = document.createElement('a');
        enlace.href = urlParaDescargarReporte;
        enlace.download = "ReporteCitas.csv";
        document.body.appendChild(enlace);

        enlace.click();
        enlace.remove();
        
        window.URL.revokeObjectURL(urlParaDescargarReporte);
    } catch (error) {
        console.error(error);
    }
}