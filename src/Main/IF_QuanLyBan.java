/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.BanChoDAO;
import DAO.BanDAO;
import DAO.LoaiSanPhamDAO;
import DAO.SanPhamDAO;
import Entity.Ban;
import Entity.BanCho;
import Entity.LoaiSp;
import Entity.SanPham;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xcurrency;
import Untils.Xdate;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_QuanLyBan extends javax.swing.JInternalFrame {

    /**
     * Creates new form IF_QuanLyBan
     */
    DefaultComboBoxModel modelCbo;
    BanDAO daoBan;
    int pageIndexTB;
    List<LoaiSp> lstLSP;
    DefaultTableModel modelSP;
    DefaultTableModel modelDS;
    boolean huyMon;
    boolean checkSave;

    public IF_QuanLyBan() {
        initComponents();
        init();
    }

    void updateStatus() {
        boolean check = tblSP.getRowCount() > 0;
        boolean click = lblMaBan.getText().equalsIgnoreCase("CHƯA CHỌN BÀN");

        btnThemVao.setEnabled(!click);
        btnHuyMon.setEnabled(check);
        btnSoLuong.setEnabled(check);
        btnLuuLai.setEnabled(check);
        btnChuyenBan.setEnabled(check);
        btnThanhToan.setEnabled(check);
        btnHoanThanh.setEnabled(check);
        btnLuuLai.setEnabled(huyMon);
    }

    boolean checkSave() {
        if (new BanDAO().selectCount(lblMaBan.getText()) < tblSP.getRowCount() || checkSave) {
            return true;
        }
        return false;
    }

    void huyChuyenBan() {
        if (MsgBox.confirm(this, "Bạn có chắc chắn muốn hủy chuyển bàn?")) {
            btnHuy.setVisible(false);
            btnXacNhan.setVisible(false);
        }
    }

    void setBorder(JLabel lb) {
        Component cm[] = pnlBan.getComponents();
        for (int i = 0; i < cm.length; i++) {
            ((JLabel) cm[i]).setBorder(null);
        }
        lb.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    }

    void chuyenBan() {
        btnXacNhan.setVisible(true);
        btnHuy.setVisible(true);
        tabs.setSelectedIndex(0);
    }

    void fillTableSP(List<BanCho> lst) {
        huyMon = true;
        modelSP.setRowCount(0);
        for (BanCho bc : lst) {
            SanPham sp = new SanPhamDAO().selectById(bc.getMaSP());
            modelSP.addRow(new Object[]{
                bc.getMaSP(),
                sp.getTenSanPham(),
                bc.getSoLuong(),
                sp.getDonViTinh(),
                Xcurrency.toCurrency(bc.getGia()),
                Xcurrency.toCurrency(bc.getGia() * bc.getSoLuong()),
                bc.isHoanThanh() ? "Đã hoàn thành" : "Đang làm"
            });
        }
        fillTableDS();
    }

    void init() {
        setResizable(false);
        setDefaultCloseOperation(2);
        setTitle("QUẢN LÝ BÀN");
        daoBan = new BanDAO();
        prepareGUI();

    }

    void prepareGUI() {
        fillCboTable();
        fillTable();
        fillCboLoai();
        setTableSP();
        setTableDS();
        fillTableDS();
        defaultForm();
    }

    void setTableSP() {
        String h[] = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "ĐVT", "Đơn giá", "Tổng tiền", "Trạng thái"};
        modelSP = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tblSP.setModel(modelSP);
    }

    void save() {
        try {
            new BanChoDAO().delete(lblMaBan.getText());
            for (int i = 0; i < tblSP.getRowCount(); i++) {
                BanCho bc = new BanCho(lblMaBan.getText(),
                        tblSP.getValueAt(i, 0).toString(),
                        Xcurrency.toInt(tblSP.getValueAt(i, 2).toString()),
                        Xcurrency.toInt(tblSP.getValueAt(i, 4).toString()),
                        Xdate.toString(new Date(), "hh:mm:ss - EE, dd/MM/yyyy"),
                        Auth.user.getMaNV(),
                        tblSP.getValueAt(i, 6).toString().equalsIgnoreCase("Đã hoàn thành"));
                new BanChoDAO().insert(bc);
                checkSave = false;
            }
            MsgBox.alert(this, "Cập nhật dữ liệu thành công");
            fillTable();
            defaultForm();
            tabs.setSelectedIndex(0);
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật dữ liệu thất bại");
            e.printStackTrace();
        }
    }

    void updateStatusTable() {
        List<String> str = new BanChoDAO().selectStatusTable();
        for (String x : str) {
            Ban b = new BanDAO().selectById(x);
            b.setSanSang(false);
            new BanDAO().update(b);
        }
    }

    String getCboLoai() {
        if (cboLoai.getSelectedIndex() == 0) {
            return "";
        } else {
            return lstLSP.get(cboLoai.getSelectedIndex() - 1).getMaLoaiSP();
        }
    }

    void fillTableDS() {
        List<SanPham> lst = new SanPhamDAO().selectByLoai("%" + getCboLoai() + "%");
        modelDS.setRowCount(0);
        for (SanPham sp : lst) {
            if ((sp.getMaSP().toUpperCase().contains(txtTimKiemTT.getText().toUpperCase()) || sp.getTenSanPham().toUpperCase().contains(txtTimKiemTT.getText().toUpperCase())) && checkSP(sp.getMaSP())) {;
                modelDS.addRow(new Object[]{sp.getMaSP(), sp.getTenSanPham(), sp.getDonViTinh(), Xcurrency.toCurrency(sp.getDonGia())});
            }
        }
        updateStatus();
    }

    void huyMon() {
        if (MsgBox.confirm(this, "Bạn có chắc chắn muốn hủy món này?")) {
            if (!tblSP.getValueAt(tblSP.getSelectedRow(), 6).equals("Đã hoàn thành")) {
                huyMon = true;
                checkSave = true;
                modelSP.removeRow(tblSP.getSelectedRow());
                fillTableDS();
            } else {
                MsgBox.alert(this, "Món ăn đã hoàn thành không thể hủy món");
            }
        }
    }

    void defaultForm() {
        modelSP.setRowCount(0);
        lblMaBan.setText("CHƯA CHỌN BÀN");
        huyMon = false;
        btnXacNhan.setVisible(false);
        btnHuy.setVisible(false);
        checkSave = false;
        updateStatus();
    }

    boolean checkSP(String maSP) {
        for (int i = 0; i < tblSP.getRowCount(); i++) {
            if (maSP.equals(tblSP.getValueAt(i, 0))) {
                return false;
            }
        }
        return true;
    }

    void setTableDS() {
        String h[] = {"Mã sản phẩm", "Tên sản phẩm", "ĐVT", "Đơn giá"};
        modelDS = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tblDS.setModel(modelDS);
    }

    void updateStatusPage() {
        boolean firstTB = pageIndexTB == 0;
        boolean lastTB = daoBan.selectPagingFull(1, getCboTable(), txtTimKiem.getText(), getRdoSatus(), pageIndexTB + 1).isEmpty();

        btnPre.setEnabled(!firstTB);
        btnNext.setEnabled(!lastTB);

    }

    void fillCboTable() {
        List<String> lst = daoBan.selectTang();
        cboTang.removeAllItems();
        cboTang.addItem("Tất cả");
        lst.forEach((str) -> {
            cboTang.addItem(str);
        });

    }

    void themVao() {
        String sl = MsgBox.promt(this, "Vui lòng nhập số lượng?");
        try {
            int x = Integer.parseInt(sl);
            int row = tblDS.getSelectedRow();
            modelSP.addRow(new Object[]{tblDS.getValueAt(row, 0), tblDS.getValueAt(row, 1), x, tblDS.getValueAt(row, 2), Xcurrency.toCurrency(Xcurrency.toInt(tblDS.getValueAt(row, 3).toString())), Xcurrency.toCurrency(Xcurrency.toInt(tblDS.getValueAt(row, 3).toString()) * x), "Đang làm"});
            modelDS.removeRow(row);
        } catch (Exception e) {
            MsgBox.alert(this, "Số lượng không đúng định dạng");
        }
    }

    String getSelectedTable() {
        Component cm[] = pnlBan.getComponents();
        String tableName = null;
        for (int i = 0; i < cm.length; i++) {
            if (!(((JLabel) cm[i]).getBorder() == null)) {
                tableName = ((JLabel) cm[i]).getToolTipText();
            }
        }
        return tableName;
    }

    boolean checkTable(String maBan) {
        List<String> lst = new BanChoDAO().selectStatusTable();
        for (String str : lst) {
            if (str.equals(maBan)) {
                return false;
            }
        }
        return true;
    }

    void xacNhan() {
        if (checkTable(getSelectedTable())) {
            if (MsgBox.confirm(this, "Bạn có chắc chắn muốn chuyển từ bàn " + lblMaBan.getText() + " đến bàn " + getSelectedTable() + " không?")) {
                new BanChoDAO().chuyenBan(getSelectedTable(), new BanChoDAO().selectByBan(lblMaBan.getText()));
                fillTable();
                defaultForm();
                btnXacNhan.setVisible(false);
                MsgBox.alert(this, "Chuyển bàn thành công!");
            }
        } else {
            MsgBox.alert(this, "Không thể chuyển đến bàn này!!");
        }
    }

    void chinhSua() {
        String sl = MsgBox.promt(this, "Vui lòng nhập số lượng?");
        try {
            int slt = Integer.parseInt(sl);
            int row = tblSP.getSelectedRow();
            if (slt > Integer.parseInt(tblSP.getValueAt(row, 2).toString())) {
                tblSP.setValueAt("Đang làm", row, 6);
            } else {
                tblSP.setValueAt("Đã hoàn thành", row, 6);
            }
            int tongTien = Xcurrency.toInt(tblSP.getValueAt(row, 5).toString()) / Integer.parseInt(tblSP.getValueAt(row, 2).toString()) * slt;
            tblSP.setValueAt(Xcurrency.toCurrency(tongTien), row, 5);
            tblSP.setValueAt(slt, row, 2);
            checkSave = true;
            updateStatus();
        } catch (Exception e) {
            MsgBox.alert(this, "Số lượng không đúng định dạng");
        }
    }

    void fillTable() {
        updateStatusTable();
        List<Ban> lst = daoBan.selectPagingFull(1, getCboTable(), txtTimKiem.getText(), getRdoSatus(), pageIndexTB);
        Component cm[] = pnlBan.getComponents();
        for (int i = 0; i < cm.length - 1; i++) {
            try {
                ((JLabel) cm[i + 1]).setBorder(null);
                ((JLabel) cm[i + 1]).setVisible(true);
                ((JLabel) cm[i + 1]).setText(lst.get(i).getTenBan());
                ((JLabel) cm[i + 1]).setToolTipText(lst.get(i).getMaBan());
                if (!lst.get(i).isSanSang()) {
                    ((JLabel) cm[i + 1]).setBackground(new Color(244, 164, 96));
                } else {
                    ((JLabel) cm[i + 1]).setBackground(new Color(127, 255, 212));
                }
            } catch (Exception e) {
                ((JLabel) cm[i + 1]).setVisible(false);
            }
        }
        ((JLabel) cm[0]).setBackground(new Color(127, 255, 212));
        ((JLabel) cm[0]).setToolTipText("MV");
        lbTable.setText(pageIndexTB + 1 + "");
        updateStatusPage();
    }

    String getCboTable() {
        if (cboTang.getSelectedIndex() == 0) {
            return "";
        } else {
            return cboTang.getSelectedItem().toString();
        }
    }

    void hoanThanh() {
        if (MsgBox.confirm(this, "Xác nhận hoàn thành món?")) {
            BanCho bc = new BanChoDAO().selectByID(lblMaBan.getText(), tblSP.getValueAt(tblSP.getSelectedRow(), 0).toString());
            bc.setHoanThanh(true);
            new BanChoDAO().update(bc);
            tblSP.setValueAt("Đã hoàn thành", tblSP.getSelectedRow(), 6);
        }
    }

    String getRdoSatus() {
        if (rdoALL.isSelected()) {
            return "";
        } else if (rdoTrong.isSelected()) {
            return "1";
        } else if (rdoCoKhach.isSelected()) {
            return "0";
        }
        return null;
    }

    void fillCboLoai() {
        lstLSP = new LoaiSanPhamDAO().selectAll();
        cboLoai.removeAllItems();
        cboLoai.addItem("Tất cả");
        lstLSP.forEach(x -> {
            cboLoai.addItem(x.getTenLoaiSP());
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboTang = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        rdoALL = new javax.swing.JRadioButton();
        rdoTrong = new javax.swing.JRadioButton();
        rdoCoKhach = new javax.swing.JRadioButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lbTable = new javax.swing.JLabel();
        btnChinhSua = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        pnlBan = new javax.swing.JPanel();
        btn1 = new javax.swing.JLabel();
        btn2 = new javax.swing.JLabel();
        btn3 = new javax.swing.JLabel();
        btn4 = new javax.swing.JLabel();
        btn5 = new javax.swing.JLabel();
        btn6 = new javax.swing.JLabel();
        btn7 = new javax.swing.JLabel();
        btn8 = new javax.swing.JLabel();
        btn9 = new javax.swing.JLabel();
        btn10 = new javax.swing.JLabel();
        btn11 = new javax.swing.JLabel();
        btn12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiemTT = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDS = new javax.swing.JTable();
        lblMaBan = new javax.swing.JLabel();
        btnThemVao = new javax.swing.JButton();
        btnLuuLai = new javax.swing.JButton();
        btnHuyMon = new javax.swing.JButton();
        btnSoLuong = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        btnHoanThanh = new javax.swing.JButton();
        btnChuyenBan = new javax.swing.JButton();

        setClosable(true);

        jPanel4.setBackground(new java.awt.Color(255, 217, 102));

        jLabel1.setText("Tìm kiếm:");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel2.setText("Tầng");

        cboTang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTangItemStateChanged(evt);
            }
        });

        jLabel3.setText("Trạng thái:");

        buttonGroup1.add(rdoALL);
        rdoALL.setSelected(true);
        rdoALL.setText("Tất cả");
        rdoALL.setOpaque(false);
        rdoALL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoALLMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoTrong);
        rdoTrong.setText("Còn trống");
        rdoTrong.setOpaque(false);
        rdoTrong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTrongMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoCoKhach);
        rdoCoKhach.setText("Có khách");
        rdoCoKhach.setOpaque(false);
        rdoCoKhach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCoKhachMouseClicked(evt);
            }
        });

        btnPre.setText("<<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lbTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTable.setText("1");

        btnChinhSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/setting.png"))); // NOI18N
        btnChinhSua.setText("Thiết lập bàn ăn");
        btnChinhSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChinhSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnXacNhan.setText("Chuyển bàn");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem)
                    .addComponent(cboTang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChinhSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnPre)
                        .addGap(33, 33, 33)
                        .addComponent(lbTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(btnNext))
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(rdoCoKhach)
                            .addComponent(rdoTrong)
                            .addComponent(rdoALL)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnHuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboTang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoALL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoTrong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoCoKhach)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPre)
                    .addComponent(lbTable)
                    .addComponent(btnNext))
                .addGap(18, 18, 18)
                .addComponent(btnXacNhan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(btnChinhSua)
                .addContainerGap())
        );

        pnlBan.setLayout(new java.awt.GridLayout(3, 4, 10, 10));

        btn1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ship.png"))); // NOI18N
        btn1.setText("Mang về");
        btn1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn1.setOpaque(true);
        btn1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pnlBan.add(btn1);

        btn2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn2.setText("Bàn X");
        btn2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn2.setOpaque(true);
        btn2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn2MouseClicked(evt);
            }
        });
        pnlBan.add(btn2);

        btn3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn3.setText("Bàn X");
        btn3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn3.setOpaque(true);
        btn3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn3MouseClicked(evt);
            }
        });
        pnlBan.add(btn3);

        btn4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn4.setText("Bàn X");
        btn4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn4.setOpaque(true);
        btn4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn4MouseClicked(evt);
            }
        });
        pnlBan.add(btn4);

        btn5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn5.setText("Bàn X");
        btn5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn5.setOpaque(true);
        btn5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn5MouseClicked(evt);
            }
        });
        pnlBan.add(btn5);

        btn6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn6.setText("Bàn X");
        btn6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn6.setOpaque(true);
        btn6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn6MouseClicked(evt);
            }
        });
        pnlBan.add(btn6);

        btn7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn7.setText("Bàn X");
        btn7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn7.setOpaque(true);
        btn7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn7MouseClicked(evt);
            }
        });
        pnlBan.add(btn7);

        btn8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn8.setText("Bàn X");
        btn8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn8.setOpaque(true);
        btn8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn8MouseClicked(evt);
            }
        });
        pnlBan.add(btn8);

        btn9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn9.setText("Bàn X");
        btn9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn9.setOpaque(true);
        btn9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn9MouseClicked(evt);
            }
        });
        pnlBan.add(btn9);

        btn10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn10.setText("Bàn X");
        btn10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn10.setOpaque(true);
        btn10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn10MouseClicked(evt);
            }
        });
        pnlBan.add(btn10);

        btn11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn11.setText("Bàn X");
        btn11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn11.setOpaque(true);
        btn11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn11MouseClicked(evt);
            }
        });
        pnlBan.add(btn11);

        btn12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table.png"))); // NOI18N
        btn12.setText("Bàn X");
        btn12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn12.setOpaque(true);
        btn12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn12MouseClicked(evt);
            }
        });
        pnlBan.add(btn12);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBan, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Danh sách bàn", jPanel1);

        jLabel4.setText("Loại sản phẩm:");

        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoaiItemStateChanged(evt);
            }
        });

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSP);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("DANH SÁCH SẢN PHẨM MÃ BÀN:");

        jLabel6.setText("Tìm kiếm");

        txtTimKiemTT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemTTKeyTyped(evt);
            }
        });

        tblDS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblDS);

        lblMaBan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblMaBan.setForeground(new java.awt.Color(255, 0, 51));
        lblMaBan.setText("CHƯA CHỌN BÀN");

        btnThemVao.setText("Thêm vào");
        btnThemVao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoActionPerformed(evt);
            }
        });

        btnLuuLai.setText("Lưu lại");
        btnLuuLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuLaiActionPerformed(evt);
            }
        });

        btnHuyMon.setText("Hủy món");
        btnHuyMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyMonActionPerformed(evt);
            }
        });

        btnSoLuong.setText("Chỉnh sửa số lượng");
        btnSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoLuongActionPerformed(evt);
            }
        });

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnHoanThanh.setText("Hoàn thành");
        btnHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhActionPerformed(evt);
            }
        });

        btnChuyenBan.setText("Chuyển bàn");
        btnChuyenBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtTimKiemTT)))
                    .addComponent(btnThemVao)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaBan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnHuyMon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSoLuong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHoanThanh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChuyenBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLuuLai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThanhToan))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(txtTimKiemTT)
                    .addComponent(jLabel5)
                    .addComponent(lblMaBan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemVao)
                    .addComponent(btnHuyMon)
                    .addComponent(btnSoLuong)
                    .addComponent(btnThanhToan)
                    .addComponent(btnLuuLai)
                    .addComponent(btnHoanThanh)
                    .addComponent(btnChuyenBan))
                .addGap(14, 14, 14))
        );

        tabs.addTab("Thông tin bàn", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        pageIndexTB = 0;
        fillTable();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cboTangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTangItemStateChanged
        if (cboTang.getItemCount() > 1) {
            pageIndexTB = 0;
            fillTable();
        }
    }//GEN-LAST:event_cboTangItemStateChanged

    private void rdoALLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoALLMouseClicked
        pageIndexTB = 0;
        fillTable();
    }//GEN-LAST:event_rdoALLMouseClicked

    private void rdoTrongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTrongMouseClicked
        pageIndexTB = 0;
        fillTable();
    }//GEN-LAST:event_rdoTrongMouseClicked

    private void rdoCoKhachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCoKhachMouseClicked
        pageIndexTB = 0;
        fillTable();
    }//GEN-LAST:event_rdoCoKhachMouseClicked

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        pageIndexTB--;
        fillTable();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        pageIndexTB++;
        fillTable();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        xacNhan();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        huyChuyenBan();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn2.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn2.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn2);
    }//GEN-LAST:event_btn2MouseClicked

    private void btn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn3.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn3.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn3);
    }//GEN-LAST:event_btn3MouseClicked

    private void btn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn4.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn4.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn4);
    }//GEN-LAST:event_btn4MouseClicked

    private void btn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn5.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn5.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn5);
    }//GEN-LAST:event_btn5MouseClicked

    private void btn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn6.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn6.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn6);
    }//GEN-LAST:event_btn6MouseClicked

    private void btn7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn7MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn7.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn7.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn7);
    }//GEN-LAST:event_btn7MouseClicked

    private void btn8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn8.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn8.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn8);
    }//GEN-LAST:event_btn8MouseClicked

    private void btn9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn9MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn9.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn9.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn9);
    }//GEN-LAST:event_btn9MouseClicked

    private void btn10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn10MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn10.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn10.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn10);
    }//GEN-LAST:event_btn10MouseClicked

    private void btn11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn11MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn11.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn11.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn11);
    }//GEN-LAST:event_btn11MouseClicked

    private void btn12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn12MouseClicked
        if (evt.getClickCount() == 2 && !btnXacNhan.isVisible()) {
            lblMaBan.setText(btn12.getToolTipText());
            fillTableSP(new BanChoDAO().selectByBan(btn12.getToolTipText()));
            tabs.setSelectedIndex(1);
        }
        setBorder(btn12);
    }//GEN-LAST:event_btn12MouseClicked

    private void cboLoaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoaiItemStateChanged
        if (cboLoai.getItemCount() > 1) {
            fillTableDS();
        }
    }//GEN-LAST:event_cboLoaiItemStateChanged

    private void txtTimKiemTTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTTKeyReleased
        fillTableDS();
    }//GEN-LAST:event_txtTimKiemTTKeyReleased

    private void txtTimKiemTTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTTKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemTTKeyTyped

    private void btnThemVaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoActionPerformed
        if (tblDS.getSelectedRow() >= 0) {
            themVao();
            updateStatus();
        } else {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm!");
        }
    }//GEN-LAST:event_btnThemVaoActionPerformed

    private void btnLuuLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuLaiActionPerformed
        save();
    }//GEN-LAST:event_btnLuuLaiActionPerformed

    private void btnHuyMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyMonActionPerformed
        if (tblSP.getSelectedRow() >= 0) {
            huyMon();
        } else {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm!");
        }
    }//GEN-LAST:event_btnHuyMonActionPerformed

    private void btnSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoLuongActionPerformed
        if (tblSP.getSelectedRow() >= 0) {
            chinhSua();
        } else {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm!");
        }
    }//GEN-LAST:event_btnSoLuongActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        if (!checkSave()) {
            new ThanhToanFrame(lblMaBan.getText()).setVisible(true);
            dispose();
        } else {
            MsgBox.alert(this, "Vui lòng lưu lại thay đổi trước khi thanh toán!");
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhActionPerformed
        hoanThanh();
    }//GEN-LAST:event_btnHoanThanhActionPerformed

    private void btnChuyenBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenBanActionPerformed
        if (!checkSave()) {
            chuyenBan();
        } else {
            MsgBox.alert(this, "Vui lòng lưu lại thay đổi trước khi chuyển bàn!");
        }
    }//GEN-LAST:event_btnChuyenBanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn1;
    private javax.swing.JLabel btn10;
    private javax.swing.JLabel btn11;
    private javax.swing.JLabel btn12;
    private javax.swing.JLabel btn2;
    private javax.swing.JLabel btn3;
    private javax.swing.JLabel btn4;
    private javax.swing.JLabel btn5;
    private javax.swing.JLabel btn6;
    private javax.swing.JLabel btn7;
    private javax.swing.JLabel btn8;
    private javax.swing.JLabel btn9;
    private javax.swing.JButton btnChinhSua;
    private javax.swing.JButton btnChuyenBan;
    private javax.swing.JButton btnHoanThanh;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnHuyMon;
    private javax.swing.JButton btnLuuLai;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSoLuong;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemVao;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboTang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTable;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JPanel pnlBan;
    private javax.swing.JRadioButton rdoALL;
    private javax.swing.JRadioButton rdoCoKhach;
    private javax.swing.JRadioButton rdoTrong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDS;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemTT;
    // End of variables declaration//GEN-END:variables
}
