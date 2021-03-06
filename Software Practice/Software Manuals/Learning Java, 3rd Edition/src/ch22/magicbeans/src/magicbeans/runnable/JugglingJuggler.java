package magicbeans.runnable;

/**
 * This file is generated by the NetBeans GUI editor. It is posted here for
 * experimental and demo purposes. Changes made here will overwritten and lost.
 *
 * @author Beppe Sabatini
 */
public class JugglingJuggler extends javax.swing.JFrame {

	private static final long serialVersionUID = 2289211642037660293L;

    public JugglingJuggler() {
        initComponents();
    }

    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        juggler1 = new magicbeans.sunw.demo.juggler.Juggler();
        dial1 = new magicbeans.Dial();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(200, 425));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 160, -1, -1));

        jButton2.setText("Stop");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));
        getContentPane().add(juggler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        dial1.addDialListener(new magicbeans.DialListener() {
            public void dialAdjusted(magicbeans.DialEvent evt) {
                dial1DialAdjusted(evt);
                dial1DialAdjusted1(evt);
            }
        });
        getContentPane().add(dial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        jTextPane1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTextPane1.setText("Learning Java, 3rd Edition, pp. 757-759, 766-768.");
        jScrollPane1.setViewportView(jTextPane1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 140, 70));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        juggler1.setJuggling(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        juggler1.stopJuggling();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void dial1DialAdjusted(magicbeans.DialEvent evt) {//GEN-FIRST:event_dial1DialAdjusted
        juggler1.setAnimationRate(0);
    }//GEN-LAST:event_dial1DialAdjusted

    private void dial1DialAdjusted1(magicbeans.DialEvent evt) {//GEN-FIRST:event_dial1DialAdjusted1
        juggler1.setAnimationRate(dial1.getValue());
    }//GEN-LAST:event_dial1DialAdjusted1

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
            java.util.logging.Logger.getLogger(JugglingJuggler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JugglingJuggler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JugglingJuggler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JugglingJuggler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JugglingJuggler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private magicbeans.Dial dial1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private magicbeans.sunw.demo.juggler.Juggler juggler1;
    // End of variables declaration//GEN-END:variables
}
