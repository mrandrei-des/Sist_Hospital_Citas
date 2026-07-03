const contenedorNotificacion = document.getElementById('contenedorNotificacion');
contenedorNotificacion.addEventListener('animationend', (e)=> {
    if(e.animationName === 'retrocederProgreso') {
        contenedorNotificacion.remove();
    }
});