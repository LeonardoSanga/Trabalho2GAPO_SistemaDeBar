/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.avaliacao2_leonardo_minguini.ctr;

import br.com.avaliacao2_leonardo_minguini.dao.ConexaoDAO;
import br.com.avaliacao2_leonardo_minguini.dao.VendaDAO;
import br.com.avaliacao2_leonardo_minguini.dto.VendaDTO;
import javax.swing.JTable;
/**
 *
 * @author gabri
 */
public class VendaCTR {
    VendaDAO vendaDAO = new VendaDAO();
    
    public VendaCTR() {
        
    }
    
    public String inserirVenda(VendaDTO vendaDTO, JTable produtos) {
        try {
            if(vendaDAO.inserirVenda(vendaDTO, produtos)) {
                return "Venda Cadastrada com Sucesso!!!";
            } else {
                return "Venda NÃO Cadastrada!!!";
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return "Venda NÃO Cadastrada!!!";
        }
    }
    
    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}

