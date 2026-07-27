document.getElementById('btnAnterior').addEventListener('click', (e)=> {
    const stepperActive = document.getElementById('stepperActive');
    if(stepperActive.value < 1) stepperActive.value = 1;
    if(stepperActive.value > 4) stepperActive.value = 4;
    if(stepperActive.value > 1) stepperActive.value = parseInt(stepperActive.value) - 1;
    moverStepper(stepperActive.value, 'prev');
});

document.getElementById('btnSiguiente').addEventListener('click', (e)=> {
    const stepperActive = document.getElementById('stepperActive');
    if(stepperActive.value < 1) stepperActive.value = 1;
    if(stepperActive.value > 4) stepperActive.value = 4;
    if(stepperActive.value < 4) stepperActive.value = parseInt(stepperActive.value) + 1;
    moverStepper(stepperActive.value, 'next');
});

function moverStepper(stepperActive, typeMove) {
    const stepperList = document.querySelectorAll('.stepper__container .stepper__step');
    const stepperToMove = stepperList[stepperActive - 1];
    
    if(typeMove === 'next') {
        const stepBefore = stepperList[stepperActive - 2];
        // el anterior marcarlo como completo

        const stepBeforeIconSection = stepBefore.querySelector('.stepper__icon-section');
        const stepBeforeIconContainer = stepBefore.querySelector('.stepper__icon-container');

        stepBeforeIconSection.classList.replace('stepper__icon-section--active', 'stepper__icon-section--completed');

        stepBeforeIconContainer.classList.replace('stepper__icon-container--active', 'stepper__icon-container--completed');

        stepBeforeIconContainer.innerHTML = '<i class="fa-solid fa-check"></i>';
    }else {
        const stepBefore = stepperList[stepperActive];
        // el siguiente marcarlo como pendiente
    }


    // stepperToMove siempre es active en proceso
}