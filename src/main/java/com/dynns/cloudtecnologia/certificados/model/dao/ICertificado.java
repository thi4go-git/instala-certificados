package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import java.util.List;

public interface ICertificado {

    void save(Certificado certificado);

    List<Certificado> findAllFilter(Certificado filter);

    Certificado findById(int id);

    Certificado findByIdNotBytes(int id);

    void deletarCertificado(int idCertificado);

    void deletarCertificadosVencidos();

    boolean certificadoExists(Certificado certificado);

}
