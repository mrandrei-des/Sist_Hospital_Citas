const selectEspecialidades = document.getElementById('especialidades');
const inpHoraInicio = document.getElementById('horaInicio');
const inpHoraFin = document.getElementById('horaFin');

selectEspecialidades.addEventListener('change', (e)=> {
    const idEspecialidad = selectEspecialidades.value;
    const selectMedicos = document.getElementById('idMedico');
    selectMedicos.innerHTML = '';
    
    fetch(`/api/medicos/consulta/por-especialidad/${idEspecialidad}`)
    .then(response => response.json())
    .then(medicos => {
        if(medicos.length > 0) {
            selectMedicos.innerHTML = '<option value="">Seleccione un médico</option>';
            medicos.forEach(medico => {
                const option = document.createElement('option');
                option.value = medico.id;
                option.textContent = `${medico.nombre} ${medico.primerApellido} ${medico.segundoApellido}`; 
                selectMedicos.appendChild(option);
            });
        }else {
            selectMedicos.innerHTML = '<option value="">Seleccione otra especialidad</option>';
        }
    })
    .catch(error => console.error('Error al cargar los médicos:', error));
});

inpHoraInicio.addEventListener('change', ()=> {
    const contenedorMensajes = document.getElementById('contenedorMensajeHoraInicio');
    const btnGuardar = document.getElementById('btnGuardarHorario');
    var horaInicioValue = inpHoraInicio.value;
    var horaFinValue = inpHoraFin.value;

    horaInicioValue = redondearHora(horaInicioValue);
    if(horaInicioValue != '' && horaFinValue != '') {
        horaFinValue = redondearHora(horaFinValue);
        var [horaInicio, minutosInicio] = horaInicioValue.split(':');
        var [horaFin, minutosFin] = horaFinValue.split(':');
        var mostrarMensaje = false, tipoMensaje = 'superior';

        if(parseInt(horaInicio) > parseInt(horaFin)) mostrarMensaje = true;
        if(parseInt(horaInicio) == parseInt(horaFin)) {
            if(parseInt(minutosInicio) > parseInt(minutosFin)) {
                mostrarMensaje = true;
            }else if(parseInt(minutosInicio) == parseInt(minutosFin)) {
                mostrarMensaje = true;
                tipoMensaje = 'igualdad';
            }
        }

        document.getElementById('contenedorMensajeHoraFin').innerHTML = '';
        btnGuardar.removeAttribute('disabled');
        contenedorMensajes.innerHTML = '';
        
        if(mostrarMensaje) {
            const parrafo = document.createElement('p');
            parrafo.classList.add('form__paragraph--error');
            parrafo.textContent = tipoMensaje == 'superior' ? '¡La hora de inicio no pueder ser mayor a la hora fin!' : '¡La hora de inicio debe ser inferior a la hora de fin!';
            btnGuardar.setAttribute('disabled', true);
            contenedorMensajes.appendChild(parrafo);
        }
    }

    inpHoraInicio.value = horaInicioValue;
    inpHoraFin.value = horaFinValue;
});

inpHoraFin.addEventListener('change', ()=> {
    const contenedorMensajes = document.getElementById('contenedorMensajeHoraFin');
    const btnGuardar = document.getElementById('btnGuardarHorario');
    var horaInicioValue = inpHoraInicio.value;
    var horaFinValue = inpHoraFin.value;

    horaFinValue = redondearHora(horaFinValue);
    if(horaInicioValue != '' && horaFinValue != '') {
        horaInicioValue = redondearHora(horaInicioValue);
        var [horaInicio, minutosInicio] = horaInicioValue.split(':');
        var [horaFin, minutosFin] = horaFinValue.split(':');
        var mostrarMensaje = false, tipoMensaje = 'superior';

        if(parseInt(horaInicio) > parseInt(horaFin)) mostrarMensaje = true;
        if(parseInt(horaInicio) == parseInt(horaFin)) {
            if(parseInt(minutosInicio) > parseInt(minutosFin)) {
                mostrarMensaje = true;
            }else if(parseInt(minutosInicio) == parseInt(minutosFin)) {
                mostrarMensaje = true;
                tipoMensaje = 'igualdad';
            }
        }

        btnGuardar.removeAttribute('disabled');
        document.getElementById('contenedorMensajeHoraInicio').innerHTML = '';
        contenedorMensajes.innerHTML = '';
        
        if(mostrarMensaje) {
            const parrafo = document.createElement('p');
            parrafo.classList.add('form__paragraph--error');
            parrafo.textContent = tipoMensaje == 'superior' ? '¡La hora de fin no pueder ser menor a la hora de inicio!' : '¡La hora de fin debe ser superior a la hora de inicio!';
            btnGuardar.setAttribute('disabled', true);
            contenedorMensajes.appendChild(parrafo);
        }
    }
    inpHoraInicio.value = horaInicioValue;
    inpHoraFin.value = horaFinValue;
});

function redondearHora(horaSelected) {
    let [hora, minutos] = horaSelected.split(':');
    if(minutos >= '00' && minutos <= '15') minutos = '00';
    if(minutos >= '16' && minutos <= '30') minutos = '30';
    if(minutos >= '31' && minutos <= '45') minutos = '30';
    if(minutos >= '46' && minutos <= '59') {
        minutos = '00';
        hora = parseInt(hora) + 1;
    }
    hora = String(hora).padStart(2, '0');
    return `${hora}:${minutos}`;
}