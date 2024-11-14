/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Files.Clientes;
import Files.Fecha;
import Files.Pagos;
import javax.swing.JOptionPane;

/**
 *
 * @author Alan Basilio
 */
public class Pay extends javax.swing.JFrame
{

    /**
     * Creates new form Pay
     */
    public Pay()
    {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboClientesPago = new javax.swing.JComboBox<>();
        comboMes = new javax.swing.JComboBox<>();
        comboTipo = new javax.swing.JComboBox<>();
        labelFechaPago = new javax.swing.JLabel();
        importePago = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("REALIZAR PAGO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 210, -1));

        jLabel1.setText("Fecha");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, -1, -1));

        jLabel2.setText("Cliente");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        jLabel3.setText("Mes aplicado");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Registrar Pago");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, -1, -1));

        jLabel5.setText("Tipo de pago");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 80, -1));

        jLabel6.setText("Importe");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, -1, -1));

        comboClientesPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jPanel1.add(comboClientesPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 170, -1));

        comboMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        jPanel1.add(comboMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 170, -1));

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Transferencia" }));
        jPanel1.add(comboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 170, -1));

        labelFechaPago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelFechaPago.setText("dd/mm/yyyy");
        jPanel1.add(labelFechaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 170, -1));
        jPanel1.add(importePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 180, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Clientes c = new Clientes();
        c.agregaCombo(comboClientesPago);
        Fecha f = new Fecha();
        f.mostrarFechaEnCampo(labelFechaPago);
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        // Validar que los campos no estén vacíos antes de realizar el pago
        if (comboClientesPago.getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(this, "Faltó seleccionar un cliente", "Alerta", JOptionPane.WARNING_MESSAGE);
            comboClientesPago.requestFocus();
        } else if (comboMes.getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(this, "Faltó seleccionar un mes", "Alerta", JOptionPane.WARNING_MESSAGE);
            comboMes.requestFocus();
        } else if (comboTipo.getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(this, "Faltó seleccionar el tipo de pago", "Alerta", JOptionPane.WARNING_MESSAGE);
            comboTipo.requestFocus();
        } else if (labelFechaPago.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Faltó llenar la fecha de pago", "Alerta", JOptionPane.WARNING_MESSAGE);
            labelFechaPago.requestFocus();
        } else if (importePago.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Faltó ingresar el importe del pago", "Alerta", JOptionPane.WARNING_MESSAGE);
            importePago.requestFocus();
        } else
        {
            // Crear instancia de la clase Pagos y registrar el pago
            Pagos p = new Pagos();
            p.altaPago(comboClientesPago, comboMes, comboTipo, labelFechaPago, importePago);

            // Limpiar el campo de importe después del registro del pago
            importePago.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Pay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Pay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboClientesPago;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JTextField importePago;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelFechaPago;
    // End of variables declaration//GEN-END:variables
}