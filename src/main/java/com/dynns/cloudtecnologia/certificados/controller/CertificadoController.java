package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.extrator.CertificadoExtrator;
import com.dynns.cloudtecnologia.certificados.model.dao.ICertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.view.table.CertificadoModelTable;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CertificadoController {

    private final ICertificado certificadoDAO;
    private final CertificadoModelTable modelo;

    public CertificadoController(ICertificado certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
        this.modelo = new CertificadoModelTable();
    }

    public Certificado findById(int id) {
        return certificadoDAO.findById(id);
    }

    public AbstractTableModel preencherTabelaCertificados(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificados(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosVencidos(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificadosVencidos(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosAtivos(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificadosAtivos(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosVencemAte30Dias(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificadosVencemAte30Dias(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosPesquisa(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificados(certificadosList);
        return modelo;
    }

    public int retornarQtdeRegistros() {
        return modelo.retornarQtdeRegistros();
    }

    public int retornarExpira(int linhaSelecionada) {
        return modelo.retornarExpira(linhaSelecionada);
    }

    public String retornarNome(int linhaSelecionada) {
        return modelo.retornarNome(linhaSelecionada);
    }

    public String retornarDataVencimentoSTR(int linhaSelecionada) {
        return modelo.retornarDataVencimentoSTR(linhaSelecionada);
    }

    public String retornarAlias(int linhaSelecionada) {
        return modelo.retornarAlias(linhaSelecionada);
    }

    public String retornarDescricao(int linhaSelecionada) {
        return modelo.retornarDescricao(linhaSelecionada);
    }

    public int retornarId(int linhaSelecionada) {
        return modelo.retornarId(linhaSelecionada);
    }

    public void deletarCertificado(int idCertificado) {
        certificadoDAO.deletarCertificado(idCertificado);
    }

    public void deletarCertificadosVencidos() {
        certificadoDAO.deletarCertificadosVencidos();
    }

    public void processarCertificadosPasta(String caminhoPasta) {
        CertificadoExtrator extrator = new CertificadoExtrator(caminhoPasta);
        extrator.processarCertificadosPasta();
    }

}
