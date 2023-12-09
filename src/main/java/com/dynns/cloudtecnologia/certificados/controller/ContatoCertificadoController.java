package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.model.dao.ContatoCertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.dao.IContatoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ContatoCertificado;
import javax.swing.JOptionPane;

public class ContatoCertificadoController {

    private final IContatoCertificado contatoCertificadoDAO;

    public ContatoCertificadoController() {
        this.contatoCertificadoDAO = new ContatoCertificadoDAO();
    }

    public ContatoCertificado retornarContatoCertificado(int idCertificado) {
        return contatoCertificadoDAO.retornarContatoCertificado(idCertificado);
    }

    public void salvarContatoCertificado(ContatoCertificado contatoUpdate) {
        ContatoCertificado contatoBD = contatoCertificadoDAO.retornarContatoCertificado(contatoUpdate.getIdCertificado());
        if (contatoBD.getId() == 0) {
            contatoCertificadoDAO.salvarContatoCertificado(contatoUpdate);
            JOptionPane.showMessageDialog(null, "Contato Salvo Com Sucesso!");
        } else {
            contatoBD.setNomeContato(contatoUpdate.getNomeContato());
            contatoBD.setTelefoneContato(contatoUpdate.getTelefoneContato());
            contatoBD.setCelularContato(contatoUpdate.getCelularContato());
            contatoBD.setEmailContato(contatoUpdate.getEmailContato());

            contatoCertificadoDAO.atualizarContatoCertificado(contatoBD);
            JOptionPane.showMessageDialog(null, "Contato Atualizado Com Sucesso!");
        }
    }
}
