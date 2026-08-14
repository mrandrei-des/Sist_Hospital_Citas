package com.hospital.citas.model.dto;

// DTO con la estructura de los atributos para cada espacio de atención médica.
public class EspacioHorarioDTO {
    private boolean isAvailable;
    private boolean isSelected;
    private String text;
    
    public EspacioHorarioDTO() {
    }

    public EspacioHorarioDTO(boolean isAvailable, boolean isSelected, String text) {
        this.isAvailable = isAvailable;
        this.isSelected = isSelected;
        this.text = text;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
