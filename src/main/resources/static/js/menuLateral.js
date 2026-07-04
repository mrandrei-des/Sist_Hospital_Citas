const btnToggleMenuClose = document.getElementById('btnToggleMenuClose');
const toggleMenuClose = document.getElementById('toggleMenuOpen');

const btnToggleMenuOpen = document.getElementById('btnToggleMenuOpen');
const toggleMenuOpen = document.getElementById('toggleMenuOpen');

const contenedorMenuLateral = document.getElementById('contenedorMenuLateral');

btnToggleMenuClose.addEventListener('click', (e) => {
    contenedorMenuLateral.classList.add('ocultar__menu');
    toggleMenuOpen.classList.add('mostrar');
});

btnToggleMenuOpen.addEventListener('click', (e) => {
    contenedorMenuLateral.classList.remove('ocultar__menu');
    toggleMenuOpen.classList.remove('mostrar');
});