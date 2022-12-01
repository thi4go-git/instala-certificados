package br.com.instalador.controle;

import br.com.instalador.model.entity.Certificado;
import br.com.instalador.model.repository.CertificadoRepository;
import br.com.instalador.service.CertificadoModeloTabela;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TelaController {

    CertificadoRepository dao;
    CertificadoModeloTabela modelo = new CertificadoModeloTabela();

    public TelaController() {
        dao = new CertificadoRepository();
    }

    //
    public String retornarSenhaMaster() {
        return dao.retornarSenhaMaster();
    }

    public String retornarCaminhoDaPasta() {
        return dao.retornarCaminhoDaPasta();
    }

    public String retornarSenhaCertificado() {
        return dao.retornarSenhaCertificado();
    }

    public AbstractTableModel preencherTabelaCERTIFICADOS_BD() {
        modelo.preencherTabelaCERTIFICADOS_BD(this);
        return modelo;
    }

    public int retornarTOTAL_CERTIFICADOS() {
        return modelo.retornarTOTAL_CERTIFICADOS();
    }

    public AbstractTableModel preencherTabelaCERTIFICADOS_TODOS() {
        modelo.preencherTabelaCERTIFICADOS_TODOS();
        return modelo;
    }

    public AbstractTableModel preencherTabelaCERTIFICADOS_VENCIDOS() {
        modelo.preencherTabelaCERTIFICADOS_VENCIDOS();
        return modelo;
    }

    public AbstractTableModel preencherTabelaCERTIFICADOS_ATIVOS() {
        modelo.preencherTabelaCERTIFICADOS_ATIVOS();
        return modelo;
    }

    public AbstractTableModel preencherTabelaCERTIFICADOS_BD_PERSONALIZADA(String NOME_PESQUISA) {
        modelo.preencherTabelaCERTIFICADOS_BD_PERSONALIZADA(NOME_PESQUISA, this);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCERTIFICADOS_VENCER_1_MES() {
        modelo.preencherTabelaCERTIFICADOS_BD_VENCER_1_MES(this);
        return modelo;
    }

    public String retornarTempoExpirar(int linhaSelecionada) {
        return modelo.retornarTempoExpirar(linhaSelecionada);
    }

    public String retornarDtVencimento(int linhaSelecionada) {
        return modelo.retornarDtVencimento(linhaSelecionada);
    }

    public String retornarNOME_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return modelo.retornarNOME_CERTIFICADO_SELECIONADO(linhaSelecionada);
    }

    public String retornarALIAS_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return modelo.retornarALIAS_CERTIFICADO_SELECIONADO(linhaSelecionada);
    }

    public String retornarDescricao(int linhaSelecionada) {
        return modelo.retornarDescricao(linhaSelecionada);
    }

    public int retornarID_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return modelo.retornarID_CERTIFICADO_SELECIONADO(linhaSelecionada);
    }

    public boolean deletarCertificado(int id) {
        return dao.deletarCertificado(id);
    }

    public boolean consultarExistenciaCertifiado(Certificado certificado) {
        return dao.consultarExistenciaCertifiado(certificado);
    }

    public boolean atualizarCertificado(Certificado certificado) {
        return dao.atualizarCertificado(certificado);
    }

    public boolean inserirCertificado(Certificado certificado) {
        return dao.inserirCertificado(certificado);
    }

    public Certificado baixarCertificadoPorID(int id) {
        return dao.baixarCertificadoPorID(id);
    }

    public List<Certificado> retornarListaDeCertificados() {
        return dao.retornarListaDeCertificados();
    }

    public List<Certificado> retornarListaDeCertificadosNome(String nome) {
        return dao.retornarListaDeCertificadosNome(nome);
    }

    public void atualizarPreferencias(String local_pasta, String senha_certificado) {
        dao.atualizarPreferencias(local_pasta, senha_certificado);
    }

    public boolean deletarCertificadosVencidos() {
        return dao.deletarCertificadosVencidos();
    }

    public List<Certificado> retornarListaDeCertificadosVencer1Mes() {
        return dao.retornarListaDeCertificadosVencerNDias();
    }
}
