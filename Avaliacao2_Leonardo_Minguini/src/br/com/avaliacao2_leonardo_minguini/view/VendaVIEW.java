/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.avaliacao2_leonardo_minguini.view;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import br.com.avaliacao2_leonardo_minguini.dto.VendaDTO;
import br.com.avaliacao2_leonardo_minguini.ctr.VendaCTR;
import br.com.avaliacao2_leonardo_minguini.dto.ProdutoDTO;
import br.com.avaliacao2_leonardo_minguini.ctr.ProdutoCTR;
import java.text.DecimalFormat;

import java.util.Date;

/**
 *
 * @author gabri
 */
public class VendaVIEW extends javax.swing.JInternalFrame {

    VendaCTR vendaCTR = new VendaCTR();
    VendaDTO vendaDTO = new VendaDTO();
    ProdutoCTR produtoCTR = new ProdutoCTR();
    ProdutoDTO produtoDTO = new ProdutoDTO();
    
    boolean dinheiro_selected = false;
    
    ResultSet rs;
    DefaultTableModel modelo_jtl_consultar_pro;
    DefaultTableModel modelo_jtl_consultar_pro_selecionado;
    /**
     * Creates new form VendaVIEW
     */
    public VendaVIEW() {
        initComponents();
        
        apresentaEscondeTroco(false);
        
        liberaCampos(false);
        
        liberaBotoes(true, false, false, true);
        
        modelo_jtl_consultar_pro = (DefaultTableModel) jtl_consultar_pro.getModel();
        modelo_jtl_consultar_pro_selecionado = (DefaultTableModel) jtl_consultar_pro_selecionado.getModel();
    }
    
    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2 );
    }
    
    private void gravar() {
        vendaDTO.setDat_vend(new Date());
        vendaDTO.setVal_vend(Double.parseDouble(TotalVenda.getText()));
        vendaDTO.setTipo_pag_vend(String.valueOf(tipo_pag.getSelectedItem()));
        
        JOptionPane.showMessageDialog(null, 
                vendaCTR.inserirVenda(vendaDTO, jtl_consultar_pro_selecionado));
    }
    
    private void preencheTabelaProduto(String nome_prod) {
        try {
            modelo_jtl_consultar_pro.setNumRows(0);
            produtoDTO.setNome_prod(nome_prod);
            rs = produtoCTR.consultarProduto(produtoDTO, 1);
            
            while(rs.next()) {
                modelo_jtl_consultar_pro.addRow(new Object[]{
                    rs.getString("id_prod"),
                    rs.getString("nome_prod"),
                    rs.getDouble("p_venda_prod")
                });
            }
        }
        catch(Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }
    
    private void calculaTotalVenda() {
        try {
            double total = 0;
            for(int cont = 0; cont < jtl_consultar_pro_selecionado.getRowCount(); cont++) {
                total += (Double.parseDouble(String.valueOf(
                          jtl_consultar_pro_selecionado.getValueAt(cont,2))) * 
                            Integer.parseInt(String.valueOf(
                            jtl_consultar_pro_selecionado.getValueAt(cont, 3))));
            }
            TotalVenda.setText(String.valueOf(total));
            
        }
        catch(Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }
    
    private void adicionaProdutoSelecionado(int id_prod, String nome_prod, double p_venda_prod) {
        try {
            modelo_jtl_consultar_pro_selecionado.addRow(new Object[] {
                id_prod,
                nome_prod,
                p_venda_prod
            });
            
        }
        catch(Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }
    
    private void removeProdutoSelecionado(int linha_selecionada) {
        try {
            if(linha_selecionada >= 0) {
                modelo_jtl_consultar_pro_selecionado.removeRow(linha_selecionada);
                calculaTotalVenda();
            }
        }catch(Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }

    private void liberaCampos(boolean a) {
        pesquisa_nome_pro.setEnabled(a);
        btnPesquisarPro.setEnabled(a);
        jtl_consultar_pro.setEnabled(a);
        btnProAdd.setEnabled(a);
        btnProRem.setEnabled(a);
        jtl_consultar_pro_selecionado.setEnabled(a);
        tipo_pag.setEnabled(a);
        TotalVenda.setText("0.00");
    }
    
    private void limpaCampos() {
        pesquisa_nome_pro.setText("");
        modelo_jtl_consultar_pro.setNumRows(0);
        modelo_jtl_consultar_pro_selecionado.setNumRows(0);
    }
    
    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnSair.setEnabled(d);
    }
    
    private boolean verificaPreenchimento() {
       if("Não selecionado".equals(tipo_pag.getSelectedItem())) {
           JOptionPane.showMessageDialog(null, "É necessário selecionar uma forma de pagamento");
           tipo_pag.requestFocus();
           return false;
       }
        
       if(jtl_consultar_pro_selecionado.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "É necessário adicionar pelo menos um produto pedido");
            jtl_consultar_pro_selecionado.requestFocus();
            return false;
        }
        else{
            int verifica = 0;
            for(int cont = 0; cont<jtl_consultar_pro_selecionado.getRowCount(); cont++) {
                if(String.valueOf(jtl_consultar_pro_selecionado.getValueAt(
                    cont, 3)).equalsIgnoreCase("null")) {
                    verifica++;
                }
            }
            if(verifica > 0) {
                JOptionPane.showMessageDialog(null, "A quantidade de cada produto vendido deve ser informada");
                jtl_consultar_pro_selecionado.requestFocus();
                return false;
                    
            }
            else {
                return true;
            }
        }
    }
    
    private void apresentaEscondeTroco(boolean a) {
        label_valor_pag.setVisible(a);
        valor_pag.setVisible(a);
        label_troco.setVisible(a);
        troco.setVisible(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pesquisa_nome_pro = new javax.swing.JTextField();
        btnPesquisarPro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_consultar_pro = new javax.swing.JTable();
        btnProAdd = new javax.swing.JButton();
        btnProRem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consultar_pro_selecionado = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        tipo_pag = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        TotalVenda = new javax.swing.JLabel();
        label_valor_pag = new javax.swing.JLabel();
        valor_pag = new javax.swing.JTextField();
        label_troco = new javax.swing.JLabel();
        troco = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Vendas");

        jLabel3.setText("Produto:");

        btnPesquisarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarProActionPerformed(evt);
            }
        });

        jtl_consultar_pro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Valor"
            }
        ));
        jScrollPane1.setViewportView(jtl_consultar_pro);

        btnProAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/prod_add.png"))); // NOI18N
        btnProAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProAddActionPerformed(evt);
            }
        });

        btnProRem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/prod_rem.png"))); // NOI18N
        btnProRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProRemActionPerformed(evt);
            }
        });

        jtl_consultar_pro_selecionado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Valor", "QTD"
            }
        ));
        jtl_consultar_pro_selecionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtl_consultar_pro_selecionadoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jtl_consultar_pro_selecionado);

        jLabel2.setText("Forma de Pagamento:");

        tipo_pag.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não selecionado", "Dinheiro", "Pix", "Cartão de Crédito", "Cartão de Débito" }));
        tipo_pag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_pagActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Total Venda:");

        TotalVenda.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TotalVenda.setForeground(new java.awt.Color(51, 255, 0));
        TotalVenda.setText("0.00");

        label_valor_pag.setText("Valor pago:");

        valor_pag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                valor_pagKeyReleased(evt);
            }
        });

        label_troco.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        label_troco.setForeground(new java.awt.Color(255, 0, 0));
        label_troco.setText("Troco:");

        troco.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        troco.setForeground(new java.awt.Color(102, 255, 0));
        troco.setText("0.00");

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/novo.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/avaliacao2_leonardo_minguini/view/imagens/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa_nome_pro, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPesquisarPro))
                            .addComponent(jScrollPane2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnProAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnProRem)
                        .addGap(223, 223, 223)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(label_troco)
                        .addGap(18, 18, 18)
                        .addComponent(troco)
                        .addGap(115, 115, 115))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(label_valor_pag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(valor_pag, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tipo_pag, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(TotalVenda)
                                .addGap(9, 9, 9)))
                        .addContainerGap(50, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnNovo)
                        .addGap(54, 54, 54)
                        .addComponent(btnSalvar)
                        .addGap(55, 55, 55)
                        .addComponent(btnCancelar)
                        .addGap(55, 55, 55)
                        .addComponent(btnSair)
                        .addGap(252, 252, 252))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(456, 456, 456))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisarPro)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(pesquisa_nome_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProAdd)
                            .addComponent(btnProRem))
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(TotalVenda))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tipo_pag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_valor_pag)
                            .addComponent(valor_pag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_troco)
                            .addComponent(troco))))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnSair))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed

        liberaCampos(true);
        liberaBotoes(false, true, true, true);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        modelo_jtl_consultar_pro.setNumRows(0);
        liberaBotoes(true, false, false, true);
        
        if(dinheiro_selected == true) {
            apresentaEscondeTroco(false);
            tipo_pag.setSelectedItem("Não selecionado");
            valor_pag.setText("");
            troco.setText("0.00");
            
            dinheiro_selected = false;
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
         this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnPesquisarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarProActionPerformed
        preencheTabelaProduto(pesquisa_nome_pro.getText());
    }//GEN-LAST:event_btnPesquisarProActionPerformed

    private void btnProAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProAddActionPerformed
        adicionaProdutoSelecionado(
            Integer.parseInt(String.valueOf(jtl_consultar_pro.getValueAt(
                jtl_consultar_pro.getSelectedRow(), 0))),
            String.valueOf(jtl_consultar_pro.getValueAt(jtl_consultar_pro.getSelectedRow(), 1)),
            Double.parseDouble(String.valueOf(jtl_consultar_pro.getValueAt(
                jtl_consultar_pro.getSelectedRow(), 2))));
    }//GEN-LAST:event_btnProAddActionPerformed

    private void btnProRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProRemActionPerformed
        removeProdutoSelecionado(jtl_consultar_pro_selecionado.getSelectedRow());
    }//GEN-LAST:event_btnProRemActionPerformed

    private void jtl_consultar_pro_selecionadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtl_consultar_pro_selecionadoKeyReleased
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            calculaTotalVenda();
        }
    }//GEN-LAST:event_jtl_consultar_pro_selecionadoKeyReleased

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(verificaPreenchimento()) {
            gravar();
            limpaCampos();
            liberaCampos(false);
            liberaBotoes(true, false, false, true);
            
            if(dinheiro_selected == true) {
            apresentaEscondeTroco(false);
            tipo_pag.setSelectedItem("Não selecionado");
            valor_pag.setText("");
            troco.setText("0.00");
            
            dinheiro_selected = false;
        }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void tipo_pagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_pagActionPerformed
        if("Dinheiro".equals(tipo_pag.getSelectedItem())) {
            apresentaEscondeTroco(true);
            
            dinheiro_selected = true;
        } else {
            if(dinheiro_selected == true) {
                apresentaEscondeTroco(false);
                
                dinheiro_selected = false;
            }
        }
    }//GEN-LAST:event_tipo_pagActionPerformed

    private void valor_pagKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_valor_pagKeyReleased
        double valor_troco = Double.parseDouble(valor_pag.getText()) - Double.parseDouble(TotalVenda.getText());
        
        //DecimalFormat df = new DecimalFormat("#.00");
        String valor_troco_formatado = String.format("%.2f", valor_troco);
        
        troco.setText(String.valueOf(valor_troco_formatado));
    }//GEN-LAST:event_valor_pagKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TotalVenda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarPro;
    private javax.swing.JButton btnProAdd;
    private javax.swing.JButton btnProRem;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtl_consultar_pro;
    private javax.swing.JTable jtl_consultar_pro_selecionado;
    private javax.swing.JLabel label_troco;
    private javax.swing.JLabel label_valor_pag;
    private javax.swing.JTextField pesquisa_nome_pro;
    private javax.swing.JComboBox<String> tipo_pag;
    private javax.swing.JLabel troco;
    private javax.swing.JTextField valor_pag;
    // End of variables declaration//GEN-END:variables
}
