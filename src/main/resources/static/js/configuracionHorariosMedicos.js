const selectEspecialidades = document.getElementById('especialidades');

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