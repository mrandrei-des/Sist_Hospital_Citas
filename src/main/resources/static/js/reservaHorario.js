function seleccionarEspacio(botonPresionado) {
    if(!botonPresionado.classList.contains('espacio__reservado')) {
        document.querySelectorAll('.btn__opcion__horario').forEach(boton => {

                boton.classList.remove('espacio__seleccionado');
            
        });
        botonPresionado.classList.add('espacio__seleccionado');
    }
}