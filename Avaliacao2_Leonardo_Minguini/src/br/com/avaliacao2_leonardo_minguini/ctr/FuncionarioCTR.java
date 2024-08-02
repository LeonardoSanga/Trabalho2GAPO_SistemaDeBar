/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.avaliacao2_leonardo_minguini.ctr;
import java.sql.ResultSet;
import br.com.avaliacao2_leonardo_minguini.dto.FuncionarioDTO;
import br.com.avaliacao2_leonardo_minguini.dao.FuncionarioDAO;
import br.com.avaliacao2_leonardo_minguini.dao.ConexaoDAO;

public class FuncionarioCTR {
    
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    
    public FuncionarioCTR() {
        
    }
    
    public String inserirFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            
            if(funcionarioDAO.inserirFuncionario(funcionarioDTO)) {
                return "Funcionário Cadastrado com Sucesso!!!";
            } else {
                return "Funcionário NÃO cadastrado!!!";
            }
        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Funcionário NÃO cadastrado!!!";
        }
    }
    
    public ResultSet consultarFuncionario(FuncionarioDTO funcionarioDTO, int opcao) {
        ResultSet rs = null;
        
        rs = funcionarioDAO.consultarFuncionario(funcionarioDTO, opcao);
        
        return rs;
    }
    
    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
    
    public String alterarFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            
            if(funcionarioDAO.alterarFuncionario(funcionarioDTO)) {
                return "Funcionário Alterado com Sucesso!!!";
            } else {
                return "Funcionário NÃO Alterado!!!";
            }
        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Funcionário NÃO Alterado!!!";
        }
    }
    
    public String excluirFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            
            if(funcionarioDAO.excluirFuncionario(funcionarioDTO)) {
                return "Funcionário Excluído com Sucesso!!!";
            } else {
                return "Funcionário NÃO Excluído!!!";
            }
        }
        
        catch(Exception e) {
            System.out.println(e.getMessage());
            return "Funcionário NÃO Excluído!!!";
        }
    }
    
    public String logarFuncionario(FuncionarioDTO funcionarioDTO) {
        return funcionarioDAO.logarFuncionario(funcionarioDTO);
    }
} // fecha classe

