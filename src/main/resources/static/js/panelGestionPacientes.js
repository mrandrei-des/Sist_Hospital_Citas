let paginaActual = 1;
let cantidadRegistrosPorPagina = 5;
let cantidadPaginas = 0;
let cantidadRegistros = 0;

document.addEventListener('DOMContentLoaded', ()=> {
    agregarPaginacionCargaInicial();
});

function agregarPaginacionCargaInicial() {
    consultarCantidadRegistros();
}

async function consultarCantidadRegistros(){
    const endPoint = '/api/pacientes/count';
    const response = await fetch(endPoint, {
        method: 'GET'
    });
    const responseData = await response.json();
    if(response.ok) {
        cantidadRegistros = responseData;
        cantidadPaginas = Math.ceil(cantidadRegistros / cantidadRegistrosPorPagina);
        document.querySelector('.pagination__paragraph strong.pagination__total').textContent = responseData;
        renderizarBotonesPaginacion();
    }else {
        const erroresEncontrados = await response.json();
        console.error(erroresEncontrados);
    }
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
            refrescarTablaRegistros(index + 1);
            paginaActual = index + 1;
        });
        paginationItems.appendChild(boton);
    }
}

function refrescarTablaRegistros(numPaginaConsultar) {
    const paginationDTO = {
        'numeroPagina': numPaginaConsultar,
        'cantidadRegistrosPorPagina': cantidadRegistrosPorPagina
    }
    consultarRegistrosPaginacion(paginationDTO);
}

async function consultarRegistrosPaginacion(paginationDTO){
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    const endPoint = '/api/pacientes/pagination';
    const response = await fetch(endPoint, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(paginationDTO)
    });
    const responseData = await response.json();
    if(response.ok) {
        if(responseData.length > 0) {
            renderizarRegistrosConsultadas(responseData);
            renderizarBotonesPaginacion();
        }else {
            mostrarMensajeSinRegistros();
        }
    }else {
        const erroresEncontrados = await response.json();
        console.error(erroresEncontrados);
    }
}

function renderizarRegistrosConsultadas(listaRegistros) {
    const contenedorMensajes = document.querySelector('.message-data__container');
    contenedorMensajes.innerHTML = '';
    const tablaRegistros = document.querySelector('.table');
    const tbodyRegistros = tablaRegistros.querySelector('tbody');
    tbodyRegistros.innerHTML = '';
    listaRegistros.forEach(registro => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${registro.identificacion}</td>
            <td>${registro.nombreCompleto}</td>
            <td>${registro.fechaRegistro}</td>
            <td>
                <span class="state__container state__container--${registro.estado === 4 ? 'active':'inactive'}">${registro.estado === 4 ? 'Activo':'Inactivo'}</span>
            </td>
            <td>
                <div class="table__action-container">
                    <a href="/mi-perfil/${registro.id}" class="btn__accion__elemento btn__modificar">
                        <span class="lista__elemento__icono">
                            <i class="fa-solid fa-pencil"></i>
                        </span>
                        <span class="btn__accion__title">Editar</span>
                    </a>
                </div>
            </td>
        `;
        tbodyRegistros.appendChild(tr);
    });
}

function mostrarMensajeSinRegistros(){
    const tablaRegistros = document.querySelector('.table');
    const tbodyRegistros = tablaRegistros.querySelector('tbody');
    tbodyRegistros.innerHTML = '';

    const contenedorMensajes = document.querySelector('.message-data__container');
    contenedorMensajes.innerHTML = '';

    const parafo = document.createElement('p');
    parafo.classList.add('message-data');
    parafo.textContent = 'No se encontraron pacientes.';
    contenedorMensajes.appendChild(parafo);
}

function paginaSiguiente() {
    if(paginaActual < cantidadPaginas) {
        paginaActual++;
        refrescarTablaRegistros(paginaActual);
    }
}

function paginaAnterior() {
    if(paginaActual > 1) {
        paginaActual--;
        refrescarTablaRegistros(paginaActual);
    }
}