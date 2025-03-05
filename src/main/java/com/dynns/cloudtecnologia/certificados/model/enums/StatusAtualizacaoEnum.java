package com.dynns.cloudtecnologia.certificados.model.enums;

public enum StatusAtualizacaoEnum {
    CERTIFICADO_NOVO, SENHA_DIVERGENTE, CERTIFICADO_JA_EXISTE, ERRO_DE_PROCESSAMENTO, CERTIFICADO_VENCIDO;

    public static StatusAtualizacaoEnum fromString(String texto) {
        for (StatusAtualizacaoEnum tipo : StatusAtualizacaoEnum.values()) {
            if (tipo.name().equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhuma enumeração com o nome correspondente: " + texto);
    }

}
