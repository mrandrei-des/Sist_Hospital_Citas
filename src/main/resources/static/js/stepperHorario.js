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

        switch (stepperActive.value) {
            case 2: // CARGAR MÉDICOS Y NOMBRE DE LA ESPECIALIDAD SELECCIONADA
                
                break;
            case 3: // CARGAR HORARIOS DEL MÉDICO SELECCIONADO
                
                break;
            case 4: // CARGAR ESPECIALIDAD, MÉDICOS PARA DEJAR LISTO EL RESUMEN
                    // se debe preparar el nombre de la especialidad y el médico, tomar el id del data-
                
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

const cardsContainerMedicos = document.getElementById('containerMedicos');
const cardsMedicos = cardsContainerMedicos.querySelectorAll('.card__item');
cardsMedicos.forEach(card => {
    card.addEventListener('click', ()=> {
        selectCard(cardsMedicos, card);
        cardSelectedUpdateValue(card);

        const paredStepperPanel = cardsContainerMedicos.closest('.stepper__panel');
        paredStepperPanel.setAttribute('data-valid', 'true');
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

// Esta función se llama al momento de renderizar los cards
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
            cardValue = parseInt(card.getAttribute('data-especialidad'));
            document.getElementById('idEspecialidad').value = cardValue;
            document.getElementById('idEspecialidad').setAttribute('data-especialidad', cardValue);
            paragraphValue.textContent = cardValue;
            paragraphValue.setAttribute('data-value', cardValue);
            break;

        case 'medico':
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

// EVENTO MANUAL PARA PROBAR EL FUNCIONAMIENTO, CUANDO YA SE CONECTE AL BACKEND SE LLAMA A UNA FUNCIÓN
/*
const daysContainer = document.getElementById('daysContainer');
const listScheduleSpaces = daysContainer.querySelectorAll('.btn.btn__opcion__horario');
listScheduleSpaces.forEach(space => {
    space.addEventListener('click', ()=> {
        selectScheduleSpace(daysContainer, space);
        updateScheduleSpaceSelected(space);
        
        const parentStepperPanel = daysContainer.closest('.stepper__panel');
        parentStepperPanel.setAttribute('data-valid', 'true');
    });
});
*/

// se llama esta función al renderizar
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

const searchBoxEspecialidad = document.getElementById('searchInpEspecialidad');
const searchBoxMedico = document.getElementById('searchInpMedico');

function findEspecialidades(nameEspecialidad) {
    fetch(`/especialidades/search/${nameEspecialidad}`)
        .then(response => response.json())
        .then(listaEspecialidades => {
            if(listaEspecialidades.length > 0) {
                renderizarEspecialidades(listaEspecialidades);

                // SE RESETEA EL PANEL DE ESPECIALIDADES
                document.getElementById('panelEspecialidades').setAttribute('data-valid', 'false');
                // SE RESETEA EL PÁRRAFO DEL RESUMEN
                const paragraphValue = getParagraphSummary('especialidad');
                paragraphValue.textContent = '-';
                paragraphValue.setAttribute('data-value', '');
                // SE RESETEA EL INPUT DEL FORM DE RESUMEN
                const inpEspecialidad = document.getElementById('idEspecialidad');
                inpEspecialidad.value = 0;
                inpEspecialidad.setAttribute('data-especialidad', 0);
            }else {
                alert("¡No se encontraron registros!")
            }
        }).catch(error => console.error('Error al cargar las especialidades:', error));
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

function findMedicos(nameMedico) {
    console.log('Buscar en la API MEDICOS y procesar');
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
const buscarMedicos = debounce(findMedicos, 500);

searchBoxEspecialidad.addEventListener('input', (e)=> {
    buscarEspecialidades(e.target.value);
});

searchBoxMedico.addEventListener('input', (e)=> {
    buscarMedicos(e.target.value);
});