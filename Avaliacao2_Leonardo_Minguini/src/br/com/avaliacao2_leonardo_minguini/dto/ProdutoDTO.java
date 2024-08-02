/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.avaliacao2_leonardo_minguini.dto;

/**
 *
 * @author gabri
 */
public class ProdutoDTO {
    private String nome_prod, desc_prod;
    private double p_custo_prod, p_venda_prod;
    private int id_prod;

    public String getNome_prod() {
        return nome_prod;
    }

    public void setNome_prod(String nome_prod) {
        this.nome_prod = nome_prod;
    }

    public String getDesc_prod() {
        return desc_prod;
    }

    public void setDesc_prod(String desc_prod) {
        this.desc_prod = desc_prod;
    }

    public double getP_custo_prod() {
        return p_custo_prod;
    }

    public void setP_custo_prod(double p_custo_prod) {
        this.p_custo_prod = p_custo_prod;
    }

    public double getP_venda_prod() {
        return p_venda_prod;
    }

    public void setP_venda_prod(double p_venda_prod) {
        this.p_venda_prod = p_venda_prod;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }
}
