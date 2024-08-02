/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.avaliacao2_leonardo_minguini.ctr;

import java.sql.ResultSet;
import br.com.avaliacao2_leonardo_minguini.dto.FornecedorDTO;
import br.com.avaliacao2_leonardo_minguini.dto.ProdutoDTO;
import br.com.avaliacao2_leonardo_minguini.dao.ProdutoDAO;
import br.com.avaliacao2_leonardo_minguini.dao.ConexaoDAO;
/**
 *
 * @author gabri
 */
public class ProdutoCTR {
    ProdutoDAO produtoDAO = new ProdutoDAO();
    
    public ProdutoCTR() {
        
    }
    
    
    
    public String alterarProduto(ProdutoDTO produtoDTO, FornecedorDTO fornecedorDTO) {
        try {
            if(produtoDAO.alterarProduto(produtoDTO, fornecedorDTO)) {
                return "Produto Alterado Com Sucesso!!!";
                
            }else {
                return "Produto NÃO Alterado!!!";
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Alterado!!!";
        }
    }
    
    public String excluirProduto(ProdutoDTO produtoDTO) {
        try {
            if(produtoDAO.excluirProduto(produtoDTO)) {
                return "Produto Excluído Com Sucesso!!!";
                
            }else {
                return "Produto NÃO Excluído!!!";
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Produto NÃO Excluído!!!";
        }
    }
    
    public ResultSet consultarProduto(ProdutoDTO produtoDTO, int opcao) {
        ResultSet rs = null;
        
        rs = produtoDAO.consultarProduto(produtoDTO, opcao);
        return rs;
    }
    
    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}