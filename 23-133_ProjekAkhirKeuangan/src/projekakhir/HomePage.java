/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projekakhir;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JFileChooser;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class HomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    private int userId;
    public HomePage(int userId) {
        this.userId = userId; // Simpan userId
        initComponents();
        
        loadUserData();
        loadCategories();
        
        // Set listener untuk tombol "SIMPAN"
        jButton5.addActionListener(e -> saveBalance());
        jButton2.addActionListener(e -> saveTransaction());

        jTabbedPane1.addChangeListener(e -> {
            if (jTabbedPane1.getSelectedIndex() == 2) { // Index 2 untuk tab Laporan
                loadTransactionHistory();
                calculateRemainingBalance();
            }
        });

        jButton1.addActionListener(e -> jTabbedPane1.setSelectedIndex(1));

        jButton3.addActionListener(e -> {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diperbarui!");
                return;
            }
        
            // Ambil data yang telah diubah dari tabel
            String tanggal = jTable1.getValueAt(selectedRow, 0).toString();
            String deskripsi = jTable1.getValueAt(selectedRow, 1).toString();
            String kategori = jTable1.getValueAt(selectedRow, 2).toString();
            String jumlahStr = jTable1.getValueAt(selectedRow, 3).toString();
            String type = jTable1.getValueAt(selectedRow, 4).toString();
        
            try {
                double jumlah = Double.parseDouble(jumlahStr);
        
                // Update data di database
                try (Connection conn = Koneksi.getConnection()) {
                    String sql = "UPDATE transactions SET description = ?, category_id = (SELECT category_id FROM categories WHERE name = ?), amount = ?, type = ? WHERE user_id = ? AND date = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, deskripsi); 
                    pst.setString(2, kategori); 
                    pst.setDouble(3, jumlah);
                    pst.setString(4, type);
                    pst.setInt(5, userId);
                    pst.setString(6, tanggal);
        
                    int updated = pst.executeUpdate();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(this, "Transaksi berhasil diperbarui!");
                        loadTransactionHistory();
                    } else {
                        JOptionPane.showMessageDialog(this, "Pembaruan transaksi gagal!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Kesalahan saat memperbarui transaksi: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
            }
        });

        jButton4.addActionListener(e -> {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin dihapus!");
                return;
            }
        
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus transaksi ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        
            String tanggal = jTable1.getValueAt(selectedRow, 0).toString();
            String deskripsi = jTable1.getValueAt(selectedRow, 1).toString();
        
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "DELETE FROM transactions WHERE user_id = ? AND date = ? AND description = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, userId);
                pst.setString(2, tanggal);
                pst.setString(3, deskripsi);
        
                int deleted = pst.executeUpdate();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus!");
        
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.removeRow(selectedRow);
        
                    calculateRemainingBalance();

                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(this, "Penghapusan transaksi gagal!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Kesalahan saat menghapus transaksi: " + ex.getMessage());
            }
        });    

        jButton6.addActionListener(e -> printToPDF());
        
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("APLIKASI MANAJEMEN KEUANGAN PRIBADI");

        jLabel10.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_INCOME-removebg-preview (1).png")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(0, 204, 153));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SALDO TOTAL :");

        jTable2.setBackground(new java.awt.Color(204, 204, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "SALDO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTextField1.setBackground(new java.awt.Color(204, 204, 255));

        jButton5.setBackground(new java.awt.Color(0, 51, 51));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("SIMPAN");

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_HARTA-removebg-preview.png")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("UTAMA", jPanel3);

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TANGGAL");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("DESKRIPSI");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("KATEGORI");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("JUMLAH");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "HARIAN", "MINGGUAN", "BULANAN" }));

        jButton2.setBackground(new java.awt.Color(51, 102, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO3-removebg-preview (2).png")); // NOI18N
        jButton2.setText("TAMBAHKAN");

        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_KOIN-removebg-preview.png")); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_COIN_2-removebg-preview (1).png")); // NOI18N

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("TYPE");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Income", "Expense" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(143, 143, 143))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox3, 0, 318, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(jLabel17)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(273, 273, 273))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(274, 274, 274))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel12)))
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        jTabbedPane1.addTab("TRANSAKSI", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        jTable1.setBackground(new java.awt.Color(204, 204, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TANGGAL", "DESKRIPSI", "KATEGORI", "JUMLAH", "TYYPE"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(0, 255, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("CREATE");

        jButton3.setBackground(new java.awt.Color(102, 255, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("UPDATE");

        jButton4.setBackground(new java.awt.Color(255, 51, 51));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("DELETE");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Action");

        jButton6.setBackground(new java.awt.Color(0, 51, 51));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_PRINT-removebg-preview.png")); // NOI18N
        jButton6.setText("PRINT");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SISA SALDO :");

        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_ECES-removebg-preview (1).png")); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Downloads\\LOGO_ECES-removebg-preview (1).png")); // NOI18N

        btnLogout.setBackground(new java.awt.Color(255, 204, 102));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setText("LOGOUT");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(91, 91, 91)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(btnLogout)))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnLogout)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jButton1)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(jButton6))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("LAPORAN", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // Membuka halaman login
            Login login = new Login(); // Pastikan Anda sudah memiliki kelas LoginPage
            login.setVisible(true);

            // Menutup atau menyembunyikan halaman utama (Homepage)
            this.dispose(); // Menutup jendela Homepage
            // atau gunakan this.setVisible(false); jika ingin hanya menyembunyikan Homepage
    }//GEN-LAST:event_btnLogoutActionPerformed

    /**
     * Memuat data saldo untuk user dan menampilkannya di GUI.
     */
    private void loadUserData() {
        try (Connection conn = Koneksi.getConnection()) {
            String balanceSql = "SELECT total_balance FROM balances WHERE user_id = ?";
            PreparedStatement balancePst = conn.prepareStatement(balanceSql);
            balancePst.setInt(1, userId);
            ResultSet balanceRs = balancePst.executeQuery();

            double totalBalance = 0.0;
            if (balanceRs.next()) {
                totalBalance = balanceRs.getDouble("total_balance");
            }

            // Menampilkan saldo total langsung di GUI
            jLabel1.setText("Saldo Total: " + totalBalance);

            jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    {totalBalance} 
                },
                new String[] {
                    "SALDO"
                }
            ));

            jTextField4.setText(String.valueOf(totalBalance));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Kesalahan memuat data saldo: " + e.getMessage());
        }
    }
    

    /**
     * Menyimpan saldo yang diinput oleh user ke database, menggantikan saldo sebelumnya.
     */
    private void saveBalance() {
        String inputSaldo = jTextField1.getText();
        if (inputSaldo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Saldo tidak boleh kosong!");
            return;
        }

        try {
            // Parsing input saldo ke double
            double saldoBaru = Double.parseDouble(inputSaldo);

            try (Connection conn = Koneksi.getConnection()) {
                // Memulai transaksi untuk memastikan operasi atomik
                conn.setAutoCommit(false);

                // Query untuk mengecek apakah user_id sudah ada di database
                String checkSql = "SELECT total_balance FROM balances WHERE user_id = ?";
                boolean saldoAda = false;

                try (PreparedStatement checkPst = conn.prepareStatement(checkSql)) {
                    checkPst.setInt(1, userId);
                    try (ResultSet rs = checkPst.executeQuery()) {
                        saldoAda = rs.next(); // Jika ada hasil, saldo sudah ada
                    }
                }

                if (saldoAda) {
                    // Jika saldo ada, update saldo
                    String updateSql = "UPDATE balances SET total_balance = ? WHERE user_id = ?";
                    try (PreparedStatement updatePst = conn.prepareStatement(updateSql)) {
                        updatePst.setDouble(1, saldoBaru);
                        updatePst.setInt(2, userId);
                        updatePst.executeUpdate();
                    }
                } else {
                    // Jika saldo belum ada, insert saldo baru
                    String insertSql = "INSERT INTO balances (user_id, total_balance) VALUES (?, ?)";
                    try (PreparedStatement insertPst = conn.prepareStatement(insertSql)) {
                        insertPst.setInt(1, userId);
                        insertPst.setDouble(2, saldoBaru);
                        insertPst.executeUpdate();
                    }
                }

                // Commit transaksi setelah operasi selesai
                conn.commit();
                JOptionPane.showMessageDialog(this, "Saldo berhasil disimpan!");

                // Memuat data user yang baru
                loadUserData(); 

            } catch (SQLException e) {
                // Rollback jika terjadi kesalahan
                JOptionPane.showMessageDialog(this, "Kesalahan menyimpan saldo: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input saldo harus berupa angka!");
        }
    }



    private void loadCategories() {
        try (Connection conn = Koneksi.getConnection()) {
            String sql = "SELECT name FROM categories";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            jComboBox3.removeAllItems();
            jComboBox3.addItem("Pilih");
    
            while (rs.next()) {
                jComboBox3.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Kesalahan memuat kategori: " + e.getMessage());
        }
    }

    private void saveTransaction() {
        String tanggal = jTextField2.getText();
        String deskripsi = jTextField3.getText();
        String kategori = (String) jComboBox3.getSelectedItem();
        String jumlahStr = jTextField5.getText();
        String type = (String) jComboBoxType.getSelectedItem();
        if (tanggal.isEmpty() || deskripsi.isEmpty() || kategori.equals("Pilih") || jumlahStr.isEmpty() || type.equals("Pilih")) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
    
        try {
            double jumlah = Double.parseDouble(jumlahStr);
    
            try (Connection conn = Koneksi.getConnection()) {
                String getCategoryIdSql = "SELECT category_id FROM categories WHERE name = ?";
                PreparedStatement getCategoryIdPst = conn.prepareStatement(getCategoryIdSql);
                getCategoryIdPst.setString(1, kategori);
                ResultSet rs = getCategoryIdPst.executeQuery();
                int categoryId = 0;
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                } else {
                    JOptionPane.showMessageDialog(this, "Kategori tidak ditemukan!");
                    return;
                }
    
                String insertSql = "INSERT INTO transactions (user_id, category_id, date, description, amount, type) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(insertSql);
                pst.setInt(1, userId);
                pst.setInt(2, categoryId);
                pst.setString(3, tanggal);
                pst.setString(4, deskripsi);
                pst.setDouble(5, jumlah);
                pst.setString(6, type);
                pst.executeUpdate();

                // String updateBalanceSql = "UPDATE balances SET total_balance = total_balance + ? WHERE user_id = ?";
                // if ("expense".equals(type)) {
                //     jumlah = -jumlah; 
                // }
                // PreparedStatement updateBalancePst = conn.prepareStatement(updateBalanceSql);
                // updateBalancePst.setDouble(1, jumlah);
                // updateBalancePst.setInt(2, userId);
                // updateBalancePst.executeUpdate();
    
                JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!");
    
                jTextField2.setText("");
                jTextField3.setText("");
                jComboBox3.setSelectedIndex(0);
                jComboBoxType.setSelectedIndex(0);
                jTextField5.setText("");
    
                loadUserData();
                loadTransactionHistory();
    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Kesalahan menyimpan transaksi: " + e.getMessage());
            }
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
        }
    }
    
    

    private void loadTransactionHistory() {
        try (Connection conn = Koneksi.getConnection()) {
            String sql = "SELECT date, description, (SELECT name FROM categories WHERE categories.category_id = transactions.category_id) AS category, amount, type FROM transactions WHERE user_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            
            DefaultTableModel model = new DefaultTableModel(new String[]{"TANGGAL", "DESKRIPSI", "KATEGORI", "JUMLAH", "TYPE"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return true;
                }
            };
            jTable1.setModel(model);
            
            
            
            while (rs.next()) {
                String date = rs.getString("date");
                String description = rs.getString("description");
                String category = rs.getString("category");
                double amount = rs.getDouble("amount");
                String type = rs.getString("type");
                
                model.addRow(new Object[]{date, description, category, amount, type});
            }
            
            jTable1.setModel(model);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Kesalahan memuat data transaksi: " + e.getMessage());
        }
    }

    private void calculateRemainingBalance() {
        try (Connection conn = Koneksi.getConnection()) {
            String balanceSql = "SELECT total_balance FROM balances WHERE user_id = ?";
            PreparedStatement balancePst = conn.prepareStatement(balanceSql);
            balancePst.setInt(1, userId);
            ResultSet balanceRs = balancePst.executeQuery();
            
            double remainingBalance = 0.0;
            if (balanceRs.next()) {
                remainingBalance = balanceRs.getDouble("total_balance");
            }
            
            String transactionSql = "SELECT amount, type FROM transactions WHERE user_id = ?";
            PreparedStatement transactionPst = conn.prepareStatement(transactionSql);
            transactionPst.setInt(1, userId);
            ResultSet transactionRs = transactionPst.executeQuery();
            
            while (transactionRs.next()) {
                double amount = transactionRs.getDouble("amount");
                String type = transactionRs.getString("type");
                
                if ("income".equals(type)) {
                    remainingBalance += amount; 
                } else if ("expense".equals(type)) {
                    remainingBalance -= amount; 
                }
            }
            
            jTextField4.setText(String.valueOf(remainingBalance));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Kesalahan menghitung sisa saldo: " + e.getMessage());
        }
    }

    private void printToPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan PDF");
        int userSelection = fileChooser.showSaveDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
            
            Document document = new Document(PageSize.A4);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
    
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                Paragraph title = new Paragraph("Laporan Keuangan Pribadi", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                
                document.add(new Paragraph(" "));
    
                PdfPTable table = new PdfPTable(jTable1.getColumnCount());
                table.setWidthPercentage(100);
    
                Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    PdfPCell header = new PdfPCell(new Phrase(jTable1.getColumnName(i), headerFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                }
    
                Font dataFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                for (int row = 0; row < jTable1.getRowCount(); row++) {
                    for (int col = 0; col < jTable1.getColumnCount(); col++) {
                        String cellValue = jTable1.getValueAt(row, col).toString();
                        PdfPCell cell = new PdfPCell(new Phrase(cellValue, dataFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }
    
                document.add(table);
    
                document.add(new Paragraph(" "));
                Paragraph balanceParagraph = new Paragraph("Sisa Saldo: " + jTextField4.getText(), headerFont);
                balanceParagraph.setAlignment(Element.ALIGN_RIGHT);
                document.add(balanceParagraph);
    
                document.close();
                JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan di " + filePath);
            } catch (FileNotFoundException | DocumentException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Gagal menyimpan laporan: " + ex.getMessage());
            }
        }
    }

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
