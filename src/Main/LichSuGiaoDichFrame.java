/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.HoaDonChiTietDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDao;
import DAO.NhanVienDAO;
import DAO.SKKMDAO;
import DAO.SanPhamDAO;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Untils.MsgBox;
import Untils.Xcurrency;
import Untils.Xdate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class LichSuGiaoDichFrame extends javax.swing.JFrame {

    int pageIndexDS;
    int pageIndexCT;
    int row = -1;
    DefaultTableModel modelDS;
    DefaultTableModel modelCT;
    SanPhamDAO daoSP;
    HoaDonDAO daoHD;
    KhachHangDao daoKH;
    NhanVienDAO daoNV;
    HoaDonChiTietDAO daoHDCT;
    SKKMDAO daoSKKM;

    public LichSuGiaoDichFrame() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtBD = new com.toedter.calendar.JDateChooser();
        txtKT = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDS = new javax.swing.JTable();
        btnPre = new javax.swing.JButton();
        lblSo = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTenKh = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayThanhToan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCT = new javax.swing.JTable();
        btnNextct = new javax.swing.JButton();
        btnPrect = new javax.swing.JButton();
        lblSoct = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        txtGhiChu = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(241, 194, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("LỊCH SỬ GIAO DỊCH");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(304, 304, 304))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Ngày bắt đầu:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Ngày kết thúc:");

        tblDS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HĐ", "Mã KH", "Tên KH", "Tên NV", "Ngày thanh toán", "Khuyến mãi", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDSMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblDS);

        btnPre.setText("<<");

        lblSo.setText("1");

        btnNext.setText(">>");

        btnLoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(btnPre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBD, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnLoc)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtKT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtBD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPre)
                    .addComponent(lblSo)
                    .addComponent(btnNext))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        tabs.addTab("Danh sách thanh toán", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Mã hóa đơn:");

        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Mã Khách hàng:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Tên nhân viên:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tên Khách hàng:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Ngày thanh toán:");

        tblCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCT);

        btnNextct.setText(">>");

        btnPrect.setText("<<");

        lblSoct.setText("1");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tổng tiền:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Ghi chú:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                .addComponent(txtMaKH))
                            .addComponent(txtNgayThanhToan)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGhiChu)
                                    .addComponent(txtTenKh))))
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(btnPrect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNextct)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtNgayThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtGhiChu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrect)
                    .addComponent(lblSoct)
                    .addComponent(btnNextct))
                .addContainerGap())
        );

        tabs.addTab("Chi tiết thanh toán", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        int ngayBD;
        int ngayKT;
        if (txtBD.getDate() == null) {
            ngayBD = 20000101;
        } else {
            ngayBD = Integer.parseInt(Xdate.toString(txtBD.getDate(), "yyyyMMdd"));
        }
        if (txtKT.getDate() == null) {
            ngayKT = 20220101;
        } else {
            ngayKT = Integer.parseInt(Xdate.toString(txtKT.getDate(), "yyyyMMdd"));
        }
        if (ngayKT < ngayBD) {
            MsgBox.alert(this, "Ngày kết thúc phải sau ngày bắt đầu");
            return;
        } else {
            fillTableDS();
            lblSo.setText(pageIndexDS + 1 + "");
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void tblDSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblCT.getSelectedRow();
            this.fillToChiTiet();
        }
    }//GEN-LAST:event_tblDSMouseClicked

    private void tblDSMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSMousePressed

    }//GEN-LAST:event_tblDSMousePressed

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
            java.util.logging.Logger.getLogger(LichSuGiaoDichFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LichSuGiaoDichFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LichSuGiaoDichFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LichSuGiaoDichFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LichSuGiaoDichFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextct;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnPrect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSo;
    private javax.swing.JLabel lblSoct;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblCT;
    private javax.swing.JTable tblDS;
    private com.toedter.calendar.JDateChooser txtBD;
    private javax.swing.JTextField txtGhiChu;
    private com.toedter.calendar.JDateChooser txtKT;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgayThanhToan;
    private javax.swing.JTextField txtTenKh;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    private void init() {
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("LỊCH SỬ GIAO DỊCH");
        daoSP = new SanPhamDAO();
        daoKH = new KhachHangDao();
        daoHD = new HoaDonDAO();
        daoSKKM = new SKKMDAO();
        daoHDCT = new HoaDonChiTietDAO();
        tabs.setSelectedIndex(0);
        txtMaHD.setEnabled(false);
        txtMaKH.setEnabled(false);
        txtTenKh.setEnabled(false);
        txtTenNV.setEnabled(false);
        txtTongTien.setEnabled(false);
        txtNgayThanhToan.setEnabled(false);
        txtGhiChu.setEnabled(false);

        modelDS = (DefaultTableModel) tblDS.getModel();
        modelCT = (DefaultTableModel) tblCT.getModel();
        fillTableDS();
        updateStatusPage();
    }

    private void fillToChiTiet() {
        modelCT = (DefaultTableModel) tblCT.getModel();
        modelCT.setRowCount(0);
        try {
            int maHD = (int) tblDS.getValueAt(this.tblDS.getSelectedRow(), 0);
            HoaDon hd = daoHD.selectById(maHD);
            if (hd != null) {

                tabs.setSelectedIndex(1);
                List<HoaDonChiTiet> lst = new HoaDonChiTietDAO().selectByAll(maHD);
                lblSo.setText(pageIndexDS + 1 + "");
                for (HoaDonChiTiet hdct : lst) {
                    modelCT.addRow(new Object[]{hdct.getMaSP(), new SanPhamDAO().selectById(hdct.getMaSP()).getTenSanPham(),
                        hdct.getSoLuong(), new SanPhamDAO().selectById(hdct.getMaSP()).getDonGia()});

                }
                this.setForm(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void fillTableDS() {
        modelDS = (DefaultTableModel) tblDS.getModel();
        modelDS.setRowCount(0);
        try {
            String ngayBD = "";
            String ngayKT = "";
            if (txtBD.getDate() == null) {
                ngayBD = "2000-01-01";
            } else {
                ngayBD = Xdate.toString(txtBD.getDate(), "yyyy-MM-dd");
            }
            if (txtKT.getDate() == null) {
                ngayKT = "2022-01-01";
            } else {
                ngayKT = Xdate.toString(txtKT.getDate(), "yyyy-MM-dd");
            }
            List<HoaDon> lst = daoHD.selectPagingFull(1, pageIndexDS, ngayBD, ngayKT);
            lblSo.setText(pageIndexDS + 1 + "");
            for (HoaDon hd : lst) {
                modelDS.addRow(new Object[]{hd.getMaHD(), hd.getMaKH(), new KhachHangDao().selectById(hd.getMaKH()).getTenKH(),
                    new NhanVienDAO().selectById(hd.getMaNV()).getTenNV(), hd.getNgayTao(), daoSKKM.selectById1(hd.getMaSKKM()).getGiaTriKM(), hd.getGhiChu()});

            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn");
        }
    }

  String getTien() {
        int tongTien = 0;
        for (int i = 0; i < tblCT.getRowCount(); i++) {
            tongTien += ((Integer.parseInt(tblCT.getValueAt(i, 2).toString()) * Integer.parseInt(tblCT.getValueAt(i, 3).toString()))-
                    ((((Integer.parseInt(tblCT.getValueAt(i, 2).toString()) * Integer.parseInt(tblCT.getValueAt(i, 3).toString())))/100)*(Double.parseDouble(tblDS.getValueAt(i, 5).toString()))));
        }
        return Xcurrency.toCurrency(tongTien);
    }

    private void setForm(HoaDon hd) {
        txtMaHD.setText(String.valueOf(hd.getMaHD()));
        txtMaKH.setText(String.valueOf(hd.getMaKH()));
        txtTenKh.setText(new KhachHangDao().selectById(hd.getMaKH()).getTenKH());
        txtTenNV.setText(new NhanVienDAO().selectById(hd.getMaNV()).getTenNV());
        txtNgayThanhToan.setText(hd.getNgayTao());
        txtGhiChu.setText(hd.getGhiChu());
        txtTongTien.setText(getTien());
    }

    void updateStatusPage() {
        String ngayBD = "";
        String ngayKT = "";
        if (txtBD.getDate() == null) {
            ngayBD = "2000-01-01";
        } else {
            ngayBD = Xdate.toString(txtBD.getDate(), "yyyy-MM-dd");
        }
        if (txtKT.getDate() == null) {
            ngayKT = "2022-01-01";
        } else {
            ngayKT = Xdate.toString(txtKT.getDate(), "yyyy-MM-dd");
        }
        boolean firstDS = pageIndexDS == 0;
        boolean firstLT = pageIndexCT == 0;
        boolean lastDS = daoHD.selectPagingFull(1, pageIndexDS + 1, ngayBD, ngayKT).isEmpty();
        boolean lastLT = daoHD.selectPagingFull(0, pageIndexCT + 1, ngayBD, ngayKT).isEmpty();
        btnPre.setEnabled(!firstDS);
        btnPrect.setEnabled(!firstLT);
        btnNext.setEnabled(!lastDS);
        btnNextct.setEnabled(!lastLT);
    }
}
