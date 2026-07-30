document.getElementById('btnAnterior').addEventListener('click', (e)=> {
    const stepperActive = document.getElementById('stepperActive');
    if(stepperActive.value < 1) stepperActive.value = 1;
    if(stepperActive.value > 4) stepperActive.value = 4;
    if(stepperActive.value > 1) stepperActive.value = parseInt(stepperActive.value) - 1;
    moverStepperContent(stepperActive.value, 'prev');
    moverStepper(stepperActive.value, 'prev');
});

document.getElementById('btnSiguiente').addEventListener('click', (e)=> {
    const stepperActive = document.getElementById('stepperActive');
    if(stepperActive.value < 1) stepperActive.value = 1;
    if(stepperActive.value > 4) stepperActive.value = 4;
    if(stepperActive.value == 4) return;
    
    if(currentStepValid(stepperActive.value) || stepperActive.value == 3) {
        if(stepperActive.value < 4) stepperActive.value = parseInt(stepperActive.value) + 1;
        moverStepper(stepperActive.value, 'next');
        moverStepperContent(stepperActive.value, 'next');
    }else {
        // usar el contenedor padre para buscar el párrado de mensaje para que este proceso sea más dinámico
        const stepperPanelFooterMessage = document.querySelector('.stepper__panel-footer-message');
        stepperPanelFooterMessage.classList.add('stepper__panel-footer-message--visible');

        // const stepperPanelFooterMessage = document.querySelector('.stepper__panel-footer-message');
        // stepperPanelFooterMessage.classList.remove('stepper__panel-footer-message--visible');
    }
});

function currentStepValid(stepperActive) {
    const stepperPanelList = document.querySelectorAll('.stepper__content .stepper__panel');
    const currentStepper = stepperPanelList[stepperActive - 1];
    const parentStepperPanel = currentStepper.closest('.stepper__panel');
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

function moverStepperContent(stepperActive, typeMove) {
    const stepperPanelList = document.querySelectorAll('.stepper__content .stepper__panel');
    
    const stepperPanelToMove = stepperPanelList[stepperActive - 1];
    const currentStepperPanelNumber = typeMove === 'next' ? stepperActive - 2 : stepperActive;

    const currentStepperPanel = stepperPanelList[currentStepperPanelNumber];
    stepperPanelToMove.classList.add('stepper__panel--active');
    currentStepperPanel.classList.remove('stepper__panel--active');
}

const cardsContainerEspecialidades = document.getElementById('containerEspecialidades');
const cardsEspecialidades = cardsContainerEspecialidades.querySelectorAll('.card__item');
cardsEspecialidades.forEach(card => {
    card.addEventListener('click', ()=> {
        selectCard(cardsEspecialidades, card);
        cardSelectedUpdateValue(card);
        
        const paredStepperPanel = cardsContainerEspecialidades.closest('.stepper__panel');
        paredStepperPanel.setAttribute('data-valid', 'true');
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

// Esta función se llama al momento de renderizar los cards
function selectCard(listCard, cardSelected) {
    listCard.forEach(card => {
        card.classList.remove('card__item--selected');
    });

    cardSelected.classList.add('card__item--selected');
}

function cardSelectedUpdateValue(card) {
    const dataTypeCard = card.getAttribute('data-type-card');
    let cardValue = 0;

    switch (dataTypeCard) {
        case 'especialidad':
            cardValue = parseInt(card.getAttribute('data-especialidad'));
            document.getElementById('idEspecialidad').value = cardValue;
            document.getElementById('idEspecialidad').setAttribute('data-especialidad', cardValue)
            break;
            
        case 'medico':
            cardValue = parseInt(card.getAttribute('data-medico'));
            document.getElementById('idMedico').value = cardValue;
            document.getElementById('idMedico').setAttribute('data-medico', cardValue)
            break;

        default:
            cardValue = -1;
            break;
    }
}