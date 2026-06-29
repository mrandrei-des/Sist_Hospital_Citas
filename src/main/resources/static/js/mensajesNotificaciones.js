const contenedorMensaje = document.getElementById('contenedorMensaje');
contenedorMensaje.addEventListener('animationend', (e)=> {
    if(e.animationName === 'retrocederProgreso') {
        contenedorMensaje.remove();
    }
});