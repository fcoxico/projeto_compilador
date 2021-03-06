import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 12151002588
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Linguagem PF");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser(){
            @Override
            public void approveSelection(){
                File f = getSelectedFile();
                if(f.exists() && getDialogType() == SAVE_DIALOG){
                    int result = JOptionPane.showConfirmDialog(this, "Arquivo já existe, deseja sobrescrever?", "Arquivo existente", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch(result){
                        case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                        case JOptionPane.NO_OPTION:
                        return;
                        case JOptionPane.CLOSED_OPTION:
                        return;
                        case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                        return;
                    }
                }
                super.approveSelection();
            }
        };
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        run = new javax.swing.JButton();
        open = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextPane();
        save = new javax.swing.JButton();

        fileChooser.setDialogTitle("Abrir código");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textarea.setColumns(20);
        textarea.setRows(5);
        textarea.setToolTipText(null);
        jScrollPane1.setViewportView(textarea);

        jLabel1.setText("Saída do compilador");
        jLabel1.setToolTipText(null);

        run.setText("Executar");
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });

        open.setText("Abrir");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });

        clear.setText("Limpar tudo");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        output.setToolTipText(null);
        jScrollPane3.setViewportView(output);
        output.setEditable(false);

        save.setText("Salvar");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(open)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(run)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clear))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(run)
                    .addComponent(open)
                    .addComponent(clear)
                    .addComponent(save))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed

        // Texto do JTextPane, onde aparece a saída
        StyledDocument doc = output.getStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();

        // Toda vez que o botão executar é acionado, limpar o campo de saída
        output.setText("");

        String codigo = textarea.getText();
        
        if(!codigo.isEmpty()){
        
            String saida = "Análise léxica:\n";

            boolean erro_lexico = false;

            // Verifica erro léxico
            try {
                Parser p = new Parser(new Lexer(new StringReader(codigo)));

                Symbol token = p.scan();
                int simbolo = token.sym;

                while(simbolo != Sym.EOF) {

                    // toString do scanner foi sobrescrito para retornar a linha e a coluna
                    String linha_coluna = p.getScanner().toString();

                    saida += linha_coluna;

                    // token em si
                    String token_atual = Sym.terminalNames[simbolo];

                    if(simbolo == Sym.error){
                        erro_lexico = true;
                        token_atual = "ERRO";
                    }

                    // instância do token
                    String valor = token.value.toString();

                    saida += " " + token_atual + " [" + valor + "] " + '\n';

                    token = p.scan();
                    simbolo = token.sym;

                }

                if(erro_lexico == true){
                    saida += "Erro léxico\n";
                }
            } catch (Exception ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(erro_lexico == false){
                saida += "\nAnálise sintática:\n";

                Parser p = new Parser(new Lexer(new StringReader(codigo)));

                try {             
                    p.parse();

                    saida += "Compilação sem erros\n";
                } catch (Exception ex) {
                    saida += p.getScanner().toString() + " Erro sintático\n";

                }
            }

            try {
                doc.insertString(doc.getLength(), saida, attributes);
            } catch (BadLocationException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_runActionPerformed
    
    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
        // Para aceitar somente .pf
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        fileChooser.resetChoosableFileFilters();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Código PF", "pf", "pf"));
        
        // Abrir no diretório de trabalho
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        
        int returnVal = fileChooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
              textarea.read( new FileReader( file.getAbsolutePath() ), null );
            }
            catch (IOException ex){
              System.out.println("Problema em acessar o arquivo " + file.getAbsolutePath());
            }
        }

    }//GEN-LAST:event_openActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        textarea.setText("");
        output.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // Para aceitar somente .pf
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        fileChooser.resetChoosableFileFilters();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Código PF", "pf", "pf"));
        
        // Abrir no diretório de trabalho
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String text = textarea.getText();
            try(FileWriter fw = new FileWriter(fileChooser.getSelectedFile())) {
                fw.write(text);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clear;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton open;
    private javax.swing.JTextPane output;
    private javax.swing.JButton run;
    private javax.swing.JButton save;
    private javax.swing.JTextArea textarea;
    // End of variables declaration//GEN-END:variables
}
