package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import java.util.List;

public interface ICertificado {

    List<Certificado> findAllFilter(Certificado filter);

    Certificado findById(int id);

    void deletarCertificado(int idCertificado);

    void deletarCertificadosVencidos();

}
