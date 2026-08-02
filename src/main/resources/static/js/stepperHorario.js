document.getElementById('btnAnterior').addEventListener('click', (e)=> {
    const stepperActive = document.getElementById('stepperActive');
    if(stepperActive.value < 1) stepperActive.value = 1;
    if(stepperActive.value > 4) stepperActive.value = 4;
    const stepperPanelList = document.querySelectorAll('.stepper__content .stepper__panel');
    const currentPanel = stepperPanelList[parseInt(stepperActive.value) - 1];

    if(stepperActive.value > 1) stepperActive.value = parseInt(stepperActive.value) - 1;
    moverStepperContent(stepperActive.value, 'prev', stepperPanelList);
    moverStepper(stepperActive.value, 'prev');
});

document.getElementById('btnSiguiente').addEventListener('click', (e)=> {
    const stepperActive = document.getElementById('stepperActive');
    if(stepperActive.value < 1) stepperActive.value = 1;
    if(stepperActive.value > 4) stepperActive.value = 4;
    if(stepperActive.value == 4) return;
    
    const stepperPanelList = document.querySelectorAll('.stepper__content .stepper__panel');
    const currentPanel = stepperPanelList[parseInt(stepperActive.value) - 1];
    const parentStepperPanel = currentPanel.closest('.stepper__panel');
    const stepperPanelFooterMessage = parentStepperPanel.querySelector('.stepper__panel-footer-message');

    if(currentStepValid(stepperActive.value, parentStepperPanel)) {
        if(stepperActive.value < 4) stepperActive.value = parseInt(stepperActive.value) + 1;

        let especialidadSelected = null;
        let medicoSelected = null;

        switch (parseInt(stepperActive.value)) {
            case 2:
                    especialidadSelected = document.getElementById('idEspecialidad').getAttribute('data-especialidad');
                    findNombreEspecialidad(especialidadSelected, 'medicos');
                    findMedicos(especialidadSelected, '');
                break;
            case 3: // CARGAR HORARIOS DEL MÉDICO SELECCIONADO
                    especialidadSelected = document.getElementById('idEspecialidad').getAttribute('data-especialidad');
                    medicoSelected = document.getElementById('idMedico').getAttribute('data-medico');
                    findNombreMedico(medicoSelected, 'horario');
                    findRangoFechasSemana();
                    findHorarioMedico(medicoSelected);
                break;
            case 4: // CARGAR ESPECIALIDAD, MÉDICOS PARA DEJAR LISTO EL RESUMEN
                    // se debe preparar el nombre de la especialidad y el médico, tomar el id del data-
                    especialidadSelected = document.getElementById('idEspecialidad').getAttribute('data-especialidad');
                    medicoSelected = document.getElementById('idMedico').getAttribute('data-medico');
                    findNombreEspecialidad(especialidadSelected, 'resumen');
                    findNombreMedico(medicoSelected, 'resumen');
                break;
        
            default:
                break;
        }

        moverStepperContent(stepperActive.value, 'next', stepperPanelList);
        moverStepper(stepperActive.value, 'next');
        stepperPanelFooterMessage.classList.remove('stepper__panel-footer-message--visible');
    }else {
        stepperPanelFooterMessage.classList.add('stepper__panel-footer-message--visible');
    }
});

const cardsContainerEspecialidades = document.getElementById('containerEspecialidades');
const cardsEspecialidades = cardsContainerEspecialidades.querySelectorAll('.card__item');
cardsEspecialidades.forEach(card => {
    card.addEventListener('click', ()=> {
        selectCard(cardsEspecialidades, card);
        cardSelectedUpdateValue(card);
        
        const parentStepperPanel = cardsContainerEspecialidades.closest('.stepper__panel');
        parentStepperPanel.setAttribute('data-valid', 'true');
    })
});

function currentStepValid(stepperActive, parentStepperPanel) {
    return parentStepperPanel.getAttribute('data-valid') == 'true';
}

function moverStepper(stepperActive, typeMove) {
    const iconList = {
        spinner: '<i class="fa-solid fa-spinner"></i>',
        check: '<i class="fa-solid fa-check"></i>',
        shapes: '<i class="fa-solid fa-shapes"></i>',
        doctor: '<i class="fa-solid fa-user-doctor"></i>',
        calendar: '<i class="fa-regular fa-calendar"></i>',
        list: '<i class="fa-solid fa-list-ul"></i>'
    }

    const stepperList = document.querySelectorAll('.stepper__container .stepper__step');
    const stepperToMove = stepperList[stepperActive - 1];

    if(typeMove === 'next') {
        const stepBefore = stepperList[stepperActive - 2];
        // de donde viene
        const stepBeforeIconSection = stepBefore.querySelector('.stepper__icon-section');
        const stepBeforeIconContainer = stepBefore.querySelector('.stepper__icon-container');
        const stepBeforeStatus = stepBefore.querySelector('.stepper__step-status');

        stepBeforeIconSection.classList.replace('stepper__icon-section--active', 'stepper__icon-section--completed');
        stepBeforeIconContainer.classList.replace('stepper__icon-container--active', 'stepper__icon-container--completed');
        stepBeforeIconContainer.innerHTML = iconList['check'];
        stepBeforeStatus.classList.replace('stepper__step-status--active', 'stepper__step-status--completed');
        stepBeforeStatus.textContent = 'Completado';

        // hacia donde va
        const stepNextIconSection = stepperToMove.querySelector('.stepper__icon-section');
        const stepNextIconContainer = stepperToMove.querySelector('.stepper__icon-container');
        const stepNextStatus = stepperToMove.querySelector('.stepper__step-status');
        const stepNextTitle = stepperToMove.querySelector('.stepper__step-title');
        
        stepNextIconSection.classList.add('stepper__icon-section--active');
        stepNextIconContainer.classList.add('stepper__icon-container--active');
        stepNextIconContainer.innerHTML = iconList['spinner'];
        stepNextTitle.classList.add('stepper__step-title--active');
        stepNextStatus.classList.add('stepper__step-status--active');
        stepNextStatus.textContent = 'En proceso';
    }else {
        const stepBefore = stepperList[stepperActive];
        
        // de donde viene
        const stepBeforeIconSection = stepBefore.querySelector('.stepper__icon-section');
        const stepBeforeIconContainer = stepBefore.querySelector('.stepper__icon-container');
        const stepBeforeTitle = stepBefore.querySelector('.stepper__step-title');
        const stepBeforeStatus = stepBefore.querySelector('.stepper__step-status');

        stepBeforeIconSection.classList.remove('stepper__icon-section--active', 'stepper__icon-section--completed');
        stepBeforeIconContainer.classList.remove('stepper__icon-container--active', 'stepper__icon-container--completed');
        stepBeforeIconContainer.innerHTML = iconList[stepBeforeIconContainer.getAttribute('data-icon')];
        stepBeforeTitle.classList.remove('stepper__step-title--active');
        stepBeforeStatus.classList.remove('stepper__step-status--active', 'stepper__step-status--completed');
        stepBeforeStatus.textContent = 'Pendiente';

        // hacia donde va
        const stepPrevIconSection = stepperToMove.querySelector('.stepper__icon-section');
        const stepPrevIconContainer = stepperToMove.querySelector('.stepper__icon-container');
        const stepPrevStatus = stepperToMove.querySelector('.stepper__step-status');

        stepPrevIconSection.classList.replace('stepper__icon-section--completed', 'stepper__icon-section--active');
        stepPrevIconContainer.classList.replace('stepper__icon-container--completed', 'stepper__icon-container--active');
        stepPrevIconContainer.innerHTML = iconList['spinner'];
        stepPrevStatus.classList.replace('stepper__step-status--completed', 'stepper__step-status--active');
        stepPrevStatus.textContent = 'En proceso';
    }
}

function moverStepperContent(stepperActive, typeMove, panelList) {
    const stepperPanelToMove = panelList[stepperActive - 1];
    const currentStepperPanelNumber = typeMove === 'next' ? stepperActive - 2 : stepperActive;
    const currentStepperPanel = panelList[currentStepperPanelNumber];

    stepperPanelToMove.classList.add('stepper__panel--active');
    currentStepperPanel.classList.remove('stepper__panel--active');
}

function selectCard(listCard, cardSelected) {
    listCard.forEach(card => {
        card.classList.remove('card__item--selected');
    });

    cardSelected.classList.add('card__item--selected');
}

function cardSelectedUpdateValue(card) {
    const dataTypeCard = card.getAttribute('data-type-card');
    const summaryContainer = document.getElementById('summaryContainer');
    const summaryParagraphs = summaryContainer.querySelectorAll('.summary__item-paragraph');
    let paragraphValue = null;
    let cardValue = 0;

    for (let index = 0; index < summaryParagraphs.length; index++) {
        if(summaryParagraphs[index].getAttribute('data-type-item') == dataTypeCard) {
            paragraphValue = summaryParagraphs[index];
            break;
        }
    }

    switch (dataTypeCard) {
        case 'especialidad':
            resetStepperPanels(1);
            cardValue = parseInt(card.getAttribute('data-especialidad'));
            document.getElementById('idEspecialidad').value = cardValue;
            document.getElementById('idEspecialidad').setAttribute('data-especialidad', cardValue);
            paragraphValue.textContent = cardValue;
            paragraphValue.setAttribute('data-value', cardValue);
            break;

        case 'medico':
            resetStepperPanels(2);
            cardValue = parseInt(card.getAttribute('data-medico'));
            document.getElementById('idMedico').value = cardValue;
            document.getElementById('idMedico').setAttribute('data-medico', cardValue);
            paragraphValue.textContent = cardValue;
            paragraphValue.setAttribute('data-value', cardValue);
            break;

        default:
            cardValue = -1;
            break;
    }
}

const searchBoxEspecialidad = document.getElementById('searchInpEspecialidad');
const searchBoxMedico = document.getElementById('searchInpMedico');

function findEspecialidades(nameEspecialidad) {
    fetch(`/reserva/especialidades/search/${nameEspecialidad}`)
        .then(response => response.json())
        .then(listaEspecialidades => {
            if(listaEspecialidades.length > 0) {
                renderizarEspecialidades(listaEspecialidades);
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar las especialidades:', error));
}

function findNombreEspecialidad(idEspecialidad, panelPorActualizar) {
    fetch(`/reserva/especialidades/${idEspecialidad}`)
        .then(response => response.json())
        .then(especialidadEncontrada => {
            if(especialidadEncontrada != null) {
                if(panelPorActualizar == 'medicos') {
                    actualizarNombreEspecialidadPanelMedicos(especialidadEncontrada);
                }else {
                    actualizarNombreEspecialidadPanelResumen(especialidadEncontrada);
                }
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar las especialidades:', error));
}

function findNombreMedico(idMedico, panelPorActualizar) {
    fetch(`/reserva/medicos/${idMedico}`)
        .then(response => response.json())
        .then(medicoEncontrado => {
            if(medicoEncontrado != null) {
                if(panelPorActualizar == 'horario') {
                    actualizarNombreMedicoPanelHorario(medicoEncontrado);
                }else {
                    actualizarNombreMedicoPanelResumen(medicoEncontrado);
                }
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar los médicos:', error));
}

function findMedicos(idEspecialidad, nameMedico) {
    let endPoint = `/reserva/medicos/search/${idEspecialidad}`;
    endPoint += nameMedico != '' ? `/${nameMedico}` : '';

    fetch(endPoint)
        .then(response => response.json())
        .then(listaMedicos => {
            if(listaMedicos.length > 0) {
                renderizarMedicos(listaMedicos);
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar los médicos:', error));
}

function findHorarioMedico(idMedico) {
    const idUsuario = document.getElementById('idUsuario').value;
    fetch(`/reserva/horario/${idUsuario}/${idMedico}`)
        .then(response => response.json())
        .then(horarioEncontrado => {
            if(horarioEncontrado != null) {
                renderizarHorarioMedico(horarioEncontrado);
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar el horario del médico:', error));
}

function findRangoFechasSemana() {
    fetch(`/reserva/horario/fechas`)
        .then(response => response.json())
        .then(rangoFechas => {
            if(rangoFechas != null) {
                actualizarRangoFechasPanelHorario(rangoFechas);
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar el horario del médico:', error));
}

function  actualizarRangoFechasPanelHorario(rangoFechas) {
    const panelHorario = document.getElementById('panelHorario');
    const scheduleTitle = panelHorario.querySelector('.schedule__title');
    scheduleTitle.textContent = `Espacios semana del ${rangoFechas[0]} al ${rangoFechas[1]}`;
}

function getParagraphSummary(dataTypeCard) {
    const summaryContainer = document.getElementById('summaryContainer');
    const summaryParagraphs = summaryContainer.querySelectorAll('.summary__item-paragraph');

    for (let index = 0; index < summaryParagraphs.length; index++) {
        if(summaryParagraphs[index].getAttribute('data-type-item') == dataTypeCard) {
            return summaryParagraphs[index];
        }
    }
}

function renderizarEspecialidades(listaEspecialidades) {
    const containerEspecialidadesCards = document.getElementById('containerEspecialidades');
    containerEspecialidadesCards.innerHTML = '';
    
    listaEspecialidades.forEach(especialidad => {
        const divCardItem = document.createElement('div');
        divCardItem.classList.add('card__item');
        divCardItem.setAttribute('data-type-card', 'especialidad');
        divCardItem.setAttribute('data-especialidad', especialidad.id);

        divCardItem.innerHTML = 
        `
            <span class="card__icon-container">
                <i class="fa-solid fa-check"></i>
            </span>
            <h3 class="card__title">${especialidad.descripcion}</h3>
            <p class="card__paragraph">Cantidad de médicos: <strong>${especialidad.cantidad}</strong></p>
        `

        divCardItem.addEventListener('click', ()=> {
            const cardsEspecialidades = containerEspecialidadesCards.querySelectorAll('.card__item');
            selectCard(cardsEspecialidades, divCardItem);
            cardSelectedUpdateValue(divCardItem);
            const parentStepperPanel = containerEspecialidadesCards.closest('.stepper__panel');
            parentStepperPanel.setAttribute('data-valid', 'true');
        })
        containerEspecialidadesCards.appendChild(divCardItem);
    });
}

function renderizarMedicos(listaMedicos) {
    const containerMedicosCards = document.getElementById('containerMedicos');
    containerMedicosCards.innerHTML = '';
    
    listaMedicos.forEach(medico => {
        const divCardItem = document.createElement('div');
        divCardItem.classList.add('card__item');
        divCardItem.setAttribute('data-type-card', 'medico');
        divCardItem.setAttribute('data-medico', medico.id);

        divCardItem.innerHTML = 
        `
            <span class="card__icon-container">
                <i class="fa-solid fa-check"></i>
            </span>
            <h3 class="card__title">${medico.nombre + ' ' + medico.primerApellido + ' ' + medico.segundoApellido}</h3>
        `

        divCardItem.addEventListener('click', ()=> {
            const cardsMedicos = containerMedicosCards.querySelectorAll('.card__item');
            selectCard(cardsMedicos, divCardItem);
            cardSelectedUpdateValue(divCardItem);
            const parentStepperPanel = containerMedicosCards.closest('.stepper__panel');
            parentStepperPanel.setAttribute('data-valid', 'true');
        })
        containerMedicosCards.appendChild(divCardItem);
    });
}

function renderizarHorarioMedico(horarioEncontrado){
    const daysContainer = document.getElementById('daysContainer');
    daysContainer.innerHTML = '';

    horarioEncontrado.forEach(diaHorario => {
        //Renderizar los días
        const scheduleDay = document.createElement('div');
        scheduleDay.classList.add('schedule__day');

        const scheduleDayInfo = document.createElement('div');
        scheduleDayInfo.classList.add('schedule__day-info');
        scheduleDayInfo.innerHTML = 
        `
            <p class="schedule__day-name">${diaHorario.nombreDia}</p>
            <p class="schedule__date">${diaHorario.fechaFormateada}</p>
        `;

        scheduleDay.appendChild(scheduleDayInfo);

        // Renderizar las horas de ese cada día
        const listaEspacios = diaHorario.listaEspacios;
        const scheduleDayDetails = document.createElement('div');
        scheduleDayDetails.classList.add('schedule__details');

        listaEspacios.forEach(espacio => {
            const buttonEspacio = document.createElement('button');
            buttonEspacio.classList.add('btn', 'btn__opcion__horario');
            buttonEspacio.setAttribute('type', 'button');
            buttonEspacio.setAttribute('data-fecha', diaHorario.fecha);
            buttonEspacio.setAttribute('data-hora', espacio);
            buttonEspacio.textContent = espacio;

            buttonEspacio.addEventListener('click', ()=> {
                selectScheduleSpace(daysContainer, buttonEspacio);
                updateScheduleSpaceSelected(buttonEspacio);
                const parentStepperPanel = daysContainer.closest('.stepper__panel');
                parentStepperPanel.setAttribute('data-valid', 'true');
            });
            scheduleDayDetails.appendChild(buttonEspacio);
        });
        scheduleDay.appendChild(scheduleDayDetails);
        daysContainer.appendChild(scheduleDay);
    });
}

function selectScheduleSpace(daysContainer, spaceSelected) {
    const spacesSelected = daysContainer.querySelectorAll('.btn.btn__opcion__horario.espacio__seleccionado');
    
    spacesSelected.forEach(space => {
        space.classList.remove('espacio__seleccionado');
    });

    spaceSelected.classList.add('espacio__seleccionado');
}

function updateScheduleSpaceSelected(spaceSelected) {
    const summaryContainer = document.getElementById('summaryContainer');
    const summaryParagraphs = summaryContainer.querySelectorAll('.summary__item-paragraph');
    let dataUpdated = false;

    let dateSelected = spaceSelected.getAttribute('data-fecha');
    let hourSelected = spaceSelected.getAttribute('data-hora');

    document.getElementById('fecha').value = dateSelected;
    document.getElementById('fecha').setAttribute('data-fecha', dateSelected);
    document.getElementById('hora').value = hourSelected;
    document.getElementById('hora').setAttribute('data-hora', hourSelected);

    for (let index = 0; index < summaryParagraphs.length; index++) {
        if(summaryParagraphs[index].getAttribute('data-type-item') == 'fecha') {
            summaryParagraphs[index].textContent = dateSelected;
        }else if(summaryParagraphs[index].getAttribute('data-type-item') == 'hora') {
            summaryParagraphs[index].textContent = hourSelected;
        }

        if(dataUpdated) break;
    }
}

function actualizarNombreEspecialidadPanelMedicos(especialidadEncontrada) {
    const panelMedico = document.getElementById('panelMedicos');
    const titlePanel = panelMedico.querySelector('.stepper__panel-title');
    titlePanel.innerHTML = `Médicos de: <strong>${especialidadEncontrada.descripcion}</strong></h2>`;
}

function actualizarNombreMedicoPanelHorario(medicoEncontrado) {
    const panelHorario = document.getElementById('panelHorario');
    const titlePanel = panelHorario.querySelector('.stepper__panel-title');
    titlePanel.innerHTML = `Horarios disponibles de: <strong>${medicoEncontrado.nombre + ' ' + medicoEncontrado.primerApellido + ' ' + medicoEncontrado.segundoApellido}</strong></h2>`;
}

function actualizarNombreMedicoPanelResumen(medicoEncontrado) {
    const summaryContainer = document.getElementById('summaryContainer');
    const summaryItems = summaryContainer.querySelectorAll('.summary__item');
    summaryItems.forEach(item => {
        const summaryParagraph = item.querySelector('.summary__item-paragraph');
        if(summaryParagraph.getAttribute('data-type-item') == 'medico') {
            summaryParagraph.textContent = medicoEncontrado.nombre + ' ' + medicoEncontrado.primerApellido + ' ' + medicoEncontrado.segundoApellido;
        }
    });
}

function actualizarNombreEspecialidadPanelResumen(especialidadEncontrada) {
    const summaryContainer = document.getElementById('summaryContainer');
    const summaryItems = summaryContainer.querySelectorAll('.summary__item');
    summaryItems.forEach(item => {
        const summaryParagraph = item.querySelector('.summary__item-paragraph');
        if(summaryParagraph.getAttribute('data-type-item') == 'especialidad') {
            summaryParagraph.textContent = especialidadEncontrada.descripcion;
        }
    });
}

function debounce(func, delay) {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    };
}

const buscarEspecialidades = debounce(findEspecialidades, 1000);
const buscarMedicos = debounce(findMedicos, 1000);

searchBoxEspecialidad.addEventListener('input', (e)=> {
    buscarEspecialidades(e.target.value);
});

searchBoxMedico.addEventListener('input', (e)=> {
    const idEspecialidad = document.getElementById('idEspecialidad').value;
    buscarMedicos(idEspecialidad, e.target.value);
});

function resetStepperPanels(indexStart){
    const listaPaneles = document.querySelectorAll('.stepper__panel');

    // RECORRE CADA PANEL PARA RESETEAR EL ATRIBUTO DE DATA-VALID
    for (let i = indexStart; i < listaPaneles.length; i++) {
        let panel = listaPaneles[i];
        // CUANDO LLEGA AL ÚLTIMO PANEL DEBE RESETEAR DIFERENTE
        // DEBE LIMPIAR EL PÁRRAFO E INPUTS DEL FORM
        if(i == 3) { 
            let summaryItems = panel.querySelectorAll('.summary__item');
            for (let j = indexStart; j < summaryItems.length - 1; j++) {
                let summaryItem = summaryItems[j];                
                let summaryParagraph = summaryItem.querySelector('.summary__item-paragraph');
                summaryParagraph.textContent = '-';
                summaryParagraph.setAttribute('data-value', '');
                
                if (j == 2) {
                    summaryItem = summaryItems[j + 1];
                    summaryParagraph = summaryItem.querySelector('.summary__item-paragraph');
                    summaryParagraph.textContent = '-';
                    summaryParagraph.setAttribute('data-value', '');
                }
            }

            let inputsHidden = panel.querySelectorAll('input');
            for (let j = indexStart; j < inputsHidden.length - 1; j++) {
                switch (j) {
                    case 0:
                        const inputEspecialidad = document.getElementById('idEspecialidad');
                        inputEspecialidad.value = '0';
                        inputEspecialidad.setAttribute('data-especialidad', 0);
                        break;
                    case 1:
                        const inputMedico = document.getElementById('idMedico');
                        inputMedico.value = '0';
                        inputMedico.setAttribute('data-medico', 0);
                        break;
                    case 2:
                        const inputFecha = document.getElementById('fecha');
                        inputFecha.value = '2026-01-01';
                        inputFecha.setAttribute('data-fecha', '2026-01-01');
                        
                        const inputHora = document.getElementById('hora');
                        inputHora.value = '08:00';
                        inputHora.setAttribute('data-hora', '08:00');
                        break;
                    default:
                        break;
                }
            }
        }else {
            panel.setAttribute('data-valid', 'false');
        }
    }
}

const btnProcesarReserva = document.getElementById('btnProcesarReserva'); 
btnProcesarReserva.addEventListener('click', (e)=> {
    e.preventDefault();
    btnProcesarReserva.setAttribute('disabled', 'true');
    procesarReserva();
    btnProcesarReserva.removeAttribute('disabled');
});

async function procesarReserva() {
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    const idMedico = document.getElementById('idMedico').value;
    const reservaCitasReservaDTO = {
        "idEspecialidad": 0,
        "idMedico": idMedico,
        "idUsuario": document.getElementById('idUsuario').value,
        "fecha": document.getElementById('fecha').value,
        "hora": document.getElementById('hora').value
    };

    const response = await fetch('/reserva/citas/reservar', {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(reservaCitasReservaDTO)
    });
    if(response.ok) {
        window.location.href = '/confirmar-reserva'
    }else if (response.status === 400){
        // alguna validación no se cumplió
        const erroresEncontrados = await response.json();
        mostrarErroresEnResumen(erroresEncontrados);
    }else {
        alert("El horario ya fue tomado por alguien más. Seleccione uno nuevo.")
        findHorarioMedico(idMedico);
    }
}

function mostrarErroresEnResumen(errores) {
    if (errores !== null) {
        const errorParagraph = document.getElementById('summaryErrorParagraph');
        for (const key in errores) {
            errorParagraph.textContent = errores[key];
        }
        errorParagraph.classList.add('stepper__panel-footer-message--visible');
    }
}