package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.model.entity.ContatoCertificado;

public interface IContatoCertificado {

    ContatoCertificado retornarContatoCertificado(int idCertificado);

    void salvarContatoCertificado(ContatoCertificado contato);
   
    void atualizarContatoCertificado(ContatoCertificado contato);
}
