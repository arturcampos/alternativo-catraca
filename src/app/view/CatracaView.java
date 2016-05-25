/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.view;

import app.control.CatracaController;
import java.util.HashMap;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author ARTUR
 */
public class CatracaView extends javax.swing.JFrame {

    final CatracaController catracaController;
    /**
     * Creates new form CatracaView
     */
    public CatracaView() {
        initComponents();
        inputLinhaDigitavel.requestFocus();
        catracaController = new CatracaController();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputLinhaDigitavel = new javax.swing.JTextField();
        labelResposta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(235, 235, 235));
        setForeground(java.awt.Color.lightGray);

        inputLinhaDigitavel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        inputLinhaDigitavel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputLinhaDigitavel.setToolTipText("");
        inputLinhaDigitavel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputLinhaDigitavelKeyPressed(evt);
            }
        });

        labelResposta.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        labelResposta.setText("Futuro Pré-Vestibular Alternativo");
        labelResposta.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelResposta, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addComponent(inputLinhaDigitavel))
                .addGap(142, 142, 142))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(labelResposta, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138)
                .addComponent(inputLinhaDigitavel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void inputLinhaDigitavelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputLinhaDigitavelKeyPressed
        System.out.println("Iniciando processamento dos dados\n");
        String linhaDigitavel = inputLinhaDigitavel.getText();
        HashMap<String, Object> retorno = catracaController.novoEvento(linhaDigitavel);
        String mensagem = (String)retorno.get("mensagem");
        labelResposta.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        labelResposta.setText(mensagem);
        
        long time = System.currentTimeMillis();
        do{
            time +=1;
        }while(time < (time + 100l));
        
        labelResposta.setText("Futuro Pré-Vestibular Alternativo");
        
    }//GEN-LAST:event_inputLinhaDigitavelKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CatracaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatracaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatracaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatracaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CatracaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputLinhaDigitavel;
    private javax.swing.JLabel labelResposta;
    // End of variables declaration//GEN-END:variables
}
