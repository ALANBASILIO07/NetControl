/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Files.Sucursales;
import javax.swing.JOptionPane;

/**
 *
 * @author Alan Basilio
 */
public class CrudSucursales extends javax.swing.JFrame
{

    /**
     * Creates new form CrudAntenas
     */
    public CrudSucursales()
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        guardarSucursal = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        gerente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nombreM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        direccionM = new javax.swing.JTextField();
        gerenteM = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        comboSucursal = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        comboElimina = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaElimina = new javax.swing.JTable();

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

        jTabbedPane1.setBackground(new java.awt.Color(0, 102, 204));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        guardarSucursal.setText("Guardar");
        guardarSucursal.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                guardarSucursalActionPerformed(evt);
            }
        });
        jPanel2.add(guardarSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, -1, -1));

        jLabel9.setText("Agregue los datos de la sucursal");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        jLabel10.setText("Nombre");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, -1, -1));

        jLabel11.setText("Gerente");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, -1, -1));
        jPanel2.add(gerente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 310, -1));

        jLabel12.setText("Dirección");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, -1, -1));
        jPanel2.add(direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 310, -1));

        nombre.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nombreActionPerformed(evt);
            }
        });
        jPanel2.add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 310, -1));

        jTabbedPane1.addTab("Agregar Sucursal", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Nombre");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, -1));

        nombreM.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nombreMActionPerformed(evt);
            }
        });
        jPanel3.add(nombreM, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 310, -1));

        jLabel5.setText("Gerente");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, -1, -1));

        jLabel6.setText("Dirección");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, -1, -1));

        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, -1, -1));
        jPanel3.add(direccionM, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 310, -1));
        jPanel3.add(gerenteM, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 310, -1));

        jLabel8.setText("Modifique los datos de los campos que desea actualizar");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, -1));

        comboSucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        comboSucursal.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                comboSucursalItemStateChanged(evt);
            }
        });
        jPanel3.add(comboSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 240, -1));

        jLabel1.setText("Seleccione una sucursal");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, -1, -1));

        jTabbedPane1.addTab("Modificar Sucursal", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        comboElimina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        comboElimina.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                comboEliminaItemStateChanged(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Selecione la sucursal que desea eliminar");

        tablaElimina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Nombre", "Gerente", "Dirección", "Torres Dependientes"
            }
        ));
        jScrollPane1.setViewportView(tablaElimina);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(comboElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(278, 278, 278))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(comboElimina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton3)
                .addGap(100, 100, 100))
        );

        jTabbedPane1.addTab("Eliminar Sucursal", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreMActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nombreMActionPerformed
    {//GEN-HEADEREND:event_nombreMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreMActionPerformed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nombreActionPerformed
    {//GEN-HEADEREND:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void guardarSucursalActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_guardarSucursalActionPerformed
    {//GEN-HEADEREND:event_guardarSucursalActionPerformed
        if (nombre.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Faltó por llenar el campo Nombre", "Alerta", JOptionPane.WARNING_MESSAGE);
            nombre.requestFocus();
        } else if (gerente.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Faltó por llenar el campo Gerente", "Alerta", JOptionPane.WARNING_MESSAGE);
            gerente.requestFocus();
        } else if (direccion.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Faltó por llenar el campo Dirección", "Alerta", JOptionPane.WARNING_MESSAGE);
            direccion.requestFocus();
        } else
        {
            Sucursales s = new Sucursales();
            s.altaSucursal(nombre, gerente, direccion);
        }
    }//GEN-LAST:event_guardarSucursalActionPerformed

    private void comboSucursalItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_comboSucursalItemStateChanged
    {//GEN-HEADEREND:event_comboSucursalItemStateChanged
        Sucursales s = new Sucursales();
        s.selecSucursal(comboSucursal, nombreM, gerenteM, direccionM);
    }//GEN-LAST:event_comboSucursalItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Sucursales s = new Sucursales();
        s.agregaCombo(comboSucursal);
    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        if (comboSucursal.getSelectedIndex() == -1 || nombreM.getText().isEmpty() || gerenteM.getText().isEmpty() || direccionM.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado alguna sucursal o faltan campos por llenar", "Alerta", JOptionPane.WARNING_MESSAGE);
            comboSucursal.requestFocus();
        } else
        {
            Sucursales s = new Sucursales();

            // Llamar al método para modificar la sucursal
            s.modificarSucursal(nombreM, gerenteM, direccionM);

            // Limpiar campos después de modificar
            nombre.setText("");
            gerente.setText("");
            direccion.setText("");

            // Reiniciar el JComboBox para que refleje los cambios
            comboSucursal.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        Sucursales s = new Sucursales();
        s.eliminarSucursal(comboElimina);
        s.agregaCombo(comboElimina);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTabbedPane1MouseClicked
    {//GEN-HEADEREND:event_jTabbedPane1MouseClicked
        Sucursales s = new Sucursales();
        s.agregaCombo(comboElimina);
        s.agregaCombo(comboSucursal);
        s.consultaGeneralSucursales(tablaElimina);
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void comboEliminaItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_comboEliminaItemStateChanged
    {//GEN-HEADEREND:event_comboEliminaItemStateChanged
        Sucursales s = new Sucursales();
        s.consultaSucursalEspecifica(tablaElimina, comboElimina);
    }//GEN-LAST:event_comboEliminaItemStateChanged

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
            java.util.logging.Logger.getLogger(CrudSucursales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(CrudSucursales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(CrudSucursales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(CrudSucursales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new CrudSucursales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboElimina;
    private javax.swing.JComboBox<String> comboSucursal;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField direccionM;
    private javax.swing.JTextField gerente;
    private javax.swing.JTextField gerenteM;
    private javax.swing.JButton guardarSucursal;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombreM;
    private javax.swing.JTable tablaElimina;
    // End of variables declaration//GEN-END:variables
}