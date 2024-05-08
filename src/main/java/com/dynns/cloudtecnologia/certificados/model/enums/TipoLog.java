package com.dynns.cloudtecnologia.certificados.model.enums;

public enum TipoLog {
    USER_CERTIFICADO_INSTALADO,
    USER_CONTATO_CERTIFICADO_ATUALIZADO,
    ADMIN_CERTIFICADO_DELETADO,
    ADMIN_CERTIFICADO_ENVIADO_EMAIL,
    ADMIN_ALTERACAO_PREFERENCIAS,
    ADMIN_DELETAR_CERTIFICADOS_VENCIDOS,
    ADMIN_ATUALIZAR_CERTIFICADOS;

    public static TipoLog fromString(String texto) {
        for (TipoLog tipo : TipoLog.values()) {
            if (tipo.name().equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhuma enumeração com o nome correspondente: " + texto);
    }

}
