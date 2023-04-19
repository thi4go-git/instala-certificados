
package br.com.instalador.service;

import br.com.instalador.model.entity.Certificado;
import java.util.List;

public interface ICertificadoDAO {

    List<Certificado> retornarListaDeCertificados();

    List<Certificado> retornarListaDeCertificadosNome(String nome);

    String retornarCaminhoDaPasta();

    String retornarSenhaMaster();

    String retornarSenhaCertificado();

    void atualizarPreferencias(String local_pasta, String senha_certificado);

    boolean deletarCertificadosVencidos();
    
        boolean deletarCertificadosDuplicados();

    boolean consultarExistenciaCertifiado(Certificado certificado);

    boolean inserirCertificado(Certificado certificado);

    Certificado baixarCertificadoPorID(int id);

    boolean atualizarCertificado(Certificado certificado);

    boolean deletarCertificado(int id);
}
