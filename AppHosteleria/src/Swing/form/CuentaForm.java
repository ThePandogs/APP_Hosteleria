package Swing.form;

import Swing.MiModeloTabla;
import Swing.PanelComponentes;
import Swing.WrapLayout;
import Swing.component.ProductoComponent;
import Swing.component.ComponentsContainer;
import Swing.component.ModelNumero;
import Swing.component.NumeroComponent;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import modelo.Cuenta;
import modelo.GrupoProducto;
import modelo.Local;
import modelo.Mesa;
import modelo.Producto;

public class CuentaForm extends javax.swing.JPanel {

    Local local;
    ArrayList<ComponentsContainer> gruposProductos;
    LocalForm localForm;
    Mesa mesa;
    MiModeloTabla modelo;

    public CuentaForm(Local local, LocalForm localForm) {

        initComponents();
        this.local = local;
        this.localForm = localForm;
        gruposProductos = new ArrayList();

        init();

    }

    private void init() {
        this.setVisible(false);
        cargarTodosProductos();
        inicializarTecladoNumerico();
        if (!gruposProductos.isEmpty()) {
            panelProductos.add(gruposProductos.get(1));
        }
        modelo = (MiModeloTabla) tablaProductos.getModel();

    }

    private void cargarTodosProductos() {
        cargarGrupoProductos(local);
        Iterator<ComponentsContainer> gruposIte = gruposProductos.iterator();
        while (gruposIte.hasNext()) {
            cargarProductos(gruposIte.next());
        }

    }

    private void cargarGrupoProductos(Local local) {
        Iterator<GrupoProducto> grupoProductos = local.getGruposProductos().iterator();
        while (grupoProductos.hasNext()) {
            addGrupo(grupoProductos.next());
        }
    }

    private void cambiarGrupoProductos(ComponentsContainer nuevoGrupo) {
        panelProductos.removeAll();
        panelProductos.add(nuevoGrupo);
        panelProductos.revalidate(); // actualiza el layout del panel
        panelProductos.repaint(); // repinta el panel
    }

    private void addGrupo(GrupoProducto data) {
        ComponentsContainer grupo = new ComponentsContainer(Color.white, new WrapLayout(WrapLayout.LEFT, 10, 10));
        grupo.setData(data);

        ProductoComponent botonSala = new ProductoComponent();
        botonSala.setData(data);
        botonSala.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {

                    cambiarGrupoProductos(grupo);
                }
            }
        });
        grupositos.add(botonSala);
        gruposProductos.add(grupo);
    }

    private void cargarProductos(ComponentsContainer grupoProducto) {

        Iterator<Producto> grupoProductos = grupoProducto.getDataGrupo().getProductos().iterator();
        while (grupoProductos.hasNext()) {

            addProducto(grupoProductos.next(), grupoProducto);
        }
    }

    private void addProducto(Producto producto, ComponentsContainer grupoProducto) {
        ProductoComponent productoComponent = new ProductoComponent();
        productoComponent.setData(producto);
        productoComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                Cuenta cuenta = mesa.getCuenta();
                if (SwingUtilities.isLeftMouseButton(me)) {

                    int cantidadProductos = Integer.parseInt(calcText.getText());
                    if (cantidadProductos == 0) {
                        cantidadProductos = 1;
                    }

                    cuenta.anadirProductosPedido(producto, cantidadProductos);
                    modelo.actualizarProductos();
                    calcText.setText("0");
                }
            }

        }
        );

        grupoProducto.add(productoComponent);

        grupoProducto.repaint();

        grupoProducto.revalidate();
    }

    private void inicializarTecladoNumerico() {
        for (int i = 1; i < 10; i++) {
            addNumber(new ModelNumero(String.valueOf(i)), numbersPanel);
        }

        addNumber(new ModelNumero("0"), numbersPanel);
        addNumber(new ModelNumero("+"), operatorsPanel);
        addNumber(new ModelNumero("-"), operatorsPanel);
        addNumber(new ModelNumero("C"), operatorsPanel);
        addNumber(new ModelNumero("✓"), operatorsPanel);
    }

    private void addNumber(ModelNumero data, PanelComponentes panel) {
        NumeroComponent numberButton = new NumeroComponent();
        numberButton.setData(data);
        numberButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    manejarInteraccionTecladoNumerico(data);
                }
            }
        });

        panel.add(numberButton);
        panel.repaint();
        panel.revalidate();
    }

    private void manejarInteraccionTecladoNumerico(ModelNumero data) {
        String buttonPressed = data.getNumero();
        String valorCalc = calcText.getText();
        switch (buttonPressed) {
            case "C" ->
                calcText.setText("0");
            case "✓" -> {
                int selectedRow = tablaProductos.getSelectedRow();

                if (selectedRow != -1) {

                    int productoValorDefault = 3; // Columna que contiene el objeto en la tabla
                    Producto producto = (Producto) modelo.getValueAt(selectedRow, productoValorDefault);

                    if (valorCalc.contains("-")) {
                        valorCalc = valorCalc.replace("-", "");

                        if (localForm.getGestion().borrarProducto(mesa.getCuenta(), producto, Integer.parseInt(valorCalc))) {

                            modelo.actualizarProductos();

                        } else {
                            //mostrarJdialog : la cantidad a borrar es superior a la actual
                        }

                    } else {
                        mesa.getCuenta().anadirProductosPedido(producto, Integer.parseInt(valorCalc));
                        modelo.actualizarProductos();
                        calcText.setText("0");

                    }

                }
            }
            case "+" ->
                interaccionBotonOperador("+", "-");
            case "-" ->
                interaccionBotonOperador("-", "+");
            default ->
                interaccionBotonNumero(buttonPressed);
        }
        // Realizar alguna acción cuando se presiona el botón "✓"
    }

    private void interaccionBotonOperador(String currentOperator, String oppositeOperator) {
        String currentText = calcText.getText();

        if (!currentText.contains(currentOperator) && !currentText.contains(oppositeOperator)) {
            if (currentText.equals("0")) {
                calcText.setText(currentOperator);
            } else {
                calcText.setText(currentOperator + currentText);
            }
        } else {
            calcText.setText(currentText.replace(oppositeOperator, currentOperator));
        }
    }

    private void interaccionBotonNumero(String buttonPressed) {
        String currentText = calcText.getText();

        if (currentText.equals("0")) {
            calcText.setText(buttonPressed);
        } else {
            calcText.setText(currentText + buttonPressed);
        }
    }

    public void setData(Mesa data) {
        mesa = data;
        if (data.getCuenta() == null) {
            data.setCuenta(new Cuenta());
        }
        cargarInformacionCuenta();
    }

    private void cargarInformacionCuenta() {

        tituloMesa.setText("Mesa: " + mesa.getNumero() + " Sala: " + localForm.getSalaActual().getId());
        modelo.setProductos(mesa.getCuenta().getProductos(), mesa.getCuenta().getPedidoProductos());

    }

    private void redimensionar(String url, JLabel label) {

        Image image = new ImageIcon(url).getImage();
        // Image newImage = image.getScaledInstance(boton.getWidth(), boton.getHeight(), java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        Image newImage = image.getScaledInstance((int) label.getPreferredSize().getWidth(), (int) label.getPreferredSize().getHeight(), java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        ImageIcon newIcon = new ImageIcon(newImage);
        label.setIcon(newIcon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cabecera = new Swing.PanelRedondeado();
        tituloMesa = new javax.swing.JLabel();
        tabla = new Swing.PanelRedondeado();
        scrolltabla = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        calc = new Swing.PanelRedondeado();
        panelRedondeado1 = new Swing.PanelRedondeado();
        operatorsPanel = new Swing.PanelComponentes();
        numbersPanel = new Swing.PanelComponentes();
        jPanel2 = new javax.swing.JPanel();
        calcText = new javax.swing.JLabel();
        productos = new Swing.PanelRedondeado();
        panelProductos = new Swing.PanelComponentes();
        panelGruposProductos = new Swing.PanelRedondeado();
        grupositos = new Swing.PanelComponentes();
        opcionesCuenta = new Swing.PanelRedondeado();
        buttonRedondeado1 = new swing.ButtonRedondeado();
        jButton1 = new javax.swing.JButton();
        panelRedondeado2 = new Swing.PanelRedondeado();

        setOpaque(false);

        jPanel1.setBackground(new java.awt.Color(153, 180, 209));

        cabecera.setPreferredSize(new java.awt.Dimension(874, 30));

        tituloMesa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tituloMesa.setPreferredSize(new java.awt.Dimension(55, 30));

        javax.swing.GroupLayout cabeceraLayout = new javax.swing.GroupLayout(cabecera);
        cabecera.setLayout(cabeceraLayout);
        cabeceraLayout.setHorizontalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
                .addContainerGap())
        );
        cabeceraLayout.setVerticalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tituloMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        tabla.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabla.setPreferredSize(new java.awt.Dimension(500, 375));

        tablaProductos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablaProductos.setModel(new MiModeloTabla());
        tablaProductos.setColumnSelectionAllowed(true);
        tablaProductos.setMinimumSize(new java.awt.Dimension(105, 80));
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        scrolltabla.setViewportView(tablaProductos);
        tablaProductos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tablaLayout = new javax.swing.GroupLayout(tabla);
        tabla.setLayout(tablaLayout);
        tablaLayout.setHorizontalGroup(
            tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrolltabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        tablaLayout.setVerticalGroup(
            tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrolltabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        calc.setMaximumSize(new java.awt.Dimension(284, 365));
        calc.setPreferredSize(new java.awt.Dimension(284, 365));

        operatorsPanel.setMaximumSize(new java.awt.Dimension(80, 32767));
        operatorsPanel.setPreferredSize(new java.awt.Dimension(60, 262));
        operatorsPanel.setLayout(new java.awt.GridLayout(4, 1, 5, 5));

        numbersPanel.setMinimumSize(new java.awt.Dimension(200, 256));
        numbersPanel.setPreferredSize(new java.awt.Dimension(200, 256));
        numbersPanel.setLayout(new java.awt.GridLayout(4, 3, 5, 5));

        javax.swing.GroupLayout panelRedondeado1Layout = new javax.swing.GroupLayout(panelRedondeado1);
        panelRedondeado1.setLayout(panelRedondeado1Layout);
        panelRedondeado1Layout.setHorizontalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRedondeado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numbersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(operatorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
        );
        panelRedondeado1Layout.setVerticalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(operatorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelRedondeado1Layout.createSequentialGroup()
                .addComponent(numbersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        calcText.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        calcText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        calcText.setText("0");
        calcText.setMinimumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calcText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calcText, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout calcLayout = new javax.swing.GroupLayout(calc);
        calc.setLayout(calcLayout);
        calcLayout.setHorizontalGroup(
            calcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRedondeado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(calcLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        calcLayout.setVerticalGroup(
            calcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelRedondeado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        productos.setPreferredSize(new java.awt.Dimension(600, 375));

        panelProductos.setLayout(new javax.swing.OverlayLayout(panelProductos));

        javax.swing.GroupLayout productosLayout = new javax.swing.GroupLayout(productos);
        productos.setLayout(productosLayout);
        productosLayout.setHorizontalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        productosLayout.setVerticalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelGruposProductosLayout = new javax.swing.GroupLayout(panelGruposProductos);
        panelGruposProductos.setLayout(panelGruposProductosLayout);
        panelGruposProductosLayout.setHorizontalGroup(
            panelGruposProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGruposProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grupositos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelGruposProductosLayout.setVerticalGroup(
            panelGruposProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGruposProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grupositos, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );

        buttonRedondeado1.setText("Atras");
        buttonRedondeado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedondeado1ActionPerformed(evt);
            }
        });

        jButton1.setText("EnviarPedido");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout opcionesCuentaLayout = new javax.swing.GroupLayout(opcionesCuenta);
        opcionesCuenta.setLayout(opcionesCuentaLayout);
        opcionesCuentaLayout.setHorizontalGroup(
            opcionesCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesCuentaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(buttonRedondeado1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        opcionesCuentaLayout.setVerticalGroup(
            opcionesCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesCuentaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(opcionesCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonRedondeado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRedondeado2Layout = new javax.swing.GroupLayout(panelRedondeado2);
        panelRedondeado2.setLayout(panelRedondeado2Layout);
        panelRedondeado2Layout.setHorizontalGroup(
            panelRedondeado2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRedondeado2Layout.setVerticalGroup(
            panelRedondeado2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(opcionesCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tabla, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                .addGap(3, 3, 3)))
                        .addGap(5, 5, 5)
                        .addComponent(calc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelGruposProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                                .addGap(5, 5, 5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panelRedondeado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addComponent(tabla, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addComponent(calc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelGruposProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRedondeado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(opcionesCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRedondeado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedondeado1ActionPerformed
        mesa.getCuenta().getPedidoProductos().clear();
        modelo.vaciarTabla();
        mesa = null;
        localForm.cerrarMesa();
        calcText.setText("0");
    }//GEN-LAST:event_buttonRedondeado1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        localForm.getGestion().enviarPedido(mesa.getCuenta());
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonRedondeado buttonRedondeado1;
    private Swing.PanelRedondeado cabecera;
    private Swing.PanelRedondeado calc;
    private javax.swing.JLabel calcText;
    private Swing.PanelComponentes grupositos;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private Swing.PanelComponentes numbersPanel;
    private Swing.PanelRedondeado opcionesCuenta;
    private Swing.PanelComponentes operatorsPanel;
    private Swing.PanelRedondeado panelGruposProductos;
    private Swing.PanelComponentes panelProductos;
    private Swing.PanelRedondeado panelRedondeado1;
    private Swing.PanelRedondeado panelRedondeado2;
    private Swing.PanelRedondeado productos;
    private javax.swing.JScrollPane scrolltabla;
    private Swing.PanelRedondeado tabla;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JLabel tituloMesa;
    // End of variables declaration//GEN-END:variables
}
