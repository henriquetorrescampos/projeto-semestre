package com.projeto_final.projeto_puc_go.Entity;

public enum ManagerType {
    ADMINISTRATIVE("Administrativo"),
    TECHNICAL("Técnico"),
    PERSONAL("Pessoal"),
    OTHER("Outro"); // Adicionado para cobrir casos não especificados

    private final String displayValue;

    ManagerType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    // Método estático para obter o ManagerType a partir do displayValue
    public static ManagerType fromDisplayValue(String displayValue) {
        for (ManagerType type : ManagerType.values()) {
            if (type.getDisplayValue().equalsIgnoreCase(displayValue)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ManagerType display value: " + displayValue);
    }
}