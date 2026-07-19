const btnToggleMenuClose = document.getElementById('btnToggleMenuClose');
// const toggleMenuClose = document.getElementById('toggleMenuOpen');

const btnToggleMenuOpen = document.getElementById('btnToggleMenuOpen');
const toggleMenuOpen = document.getElementById('toggleMenuOpen');

const contenedorMenuLateral = document.getElementById('sidebarMenu');

btnToggleMenuClose.addEventListener('click', (e) => {
    contenedorMenuLateral.classList.add('sidebar-menu--hidden');
    toggleMenuOpen.classList.add('sidebar-menu__toggle-container--show');
});

btnToggleMenuOpen.addEventListener('click', (e) => {
    contenedorMenuLateral.classList.remove('sidebar-menu--hidden');
    toggleMenuOpen.classList.remove('sidebar-menu__toggle-container--show');
});