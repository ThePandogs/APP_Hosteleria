package iu;

import Swing.component.MiModeloTabla;
import Swing.PanelComponentes;
import Swing.WrapLayout;
import Swing.component.ProductoComponent;
import Swing.component.ComponentsContainer;
import Swing.component.ModelNumero;
import Swing.component.NumeroComponent;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
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
        ComponentsContainer grupo = new ComponentsContainer(Color.white, new WrapLayout(WrapLayout.LEFT, 5, 5));
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
                    String valorCalc = calcText.getText();

                    if (valorCalc.contains("-")) {
                        valorCalc = valorCalc.replace("-", "");

                        eliminarProducto(producto, Integer.parseInt(valorCalc));
                        return;
                    } else {
                        int cantidadProductos = Integer.parseInt(valorCalc);
                        if (cantidadProductos == 0) {
                            cantidadProductos = 1;
                        }
                        cuenta.anadirProductosPedido(producto, cantidadProductos);

                    }
                    modelo.actualizarProductos();
                    calcText.setText("0");
                    actualizarPrecioTotal();
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
                        if (valorCalc.isBlank()) {
                            valorCalc = "1";
                        }
                        eliminarProducto(producto, Integer.parseInt(valorCalc));

                    } else {
                        mesa.getCuenta().anadirProductosPedido(producto, Integer.parseInt(valorCalc));
                        modelo.actualizarProductos();
                        calcText.setText("0");

                    }

                }
                actualizarPrecioTotal();
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

    private void eliminarProducto(Producto producto, int cantidad) {
        if (localForm.getGestion().borrarProducto(mesa.getCuenta(), producto, cantidad)) {

            modelo.actualizarProductos();
            actualizarPrecioTotal();
        } else {
            //mostrarJdialog : la cantidad a borrar es superior a la actual
        }
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
            data.setCuenta(new Cuenta(data, localForm.getGestion().getCamareroActual()));
        }
        cargarInformacionCuenta();

    }

    private void cargarInformacionCuenta() {

        tituloMesa.setText("Mesa: " + mesa.getNumero() + " Sala: " + localForm.getSalaActual().getId() + " Camarero: " + mesa.getCuenta().getCamarero().getNombre());
        modelo.setProductos(mesa.getCuenta().getProductos(), mesa.getCuenta().getPedidoProductos());
        actualizarPrecioTotal();
    }

    private void redimensionar(String url, JLabel label) {

        Image image = new ImageIcon(url).getImage();
        // Image newImage = image.getScaledInstance(boton.getWidth(), boton.getHeight(), java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        Image newImage = image.getScaledInstance((int) label.getPreferredSize().getWidth(), (int) label.getPreferredSize().getHeight(), java.awt.Image.SCALE_SMOOTH); // Redimensiona la imagen
        ImageIcon newIcon = new ImageIcon(newImage);
        label.setIcon(newIcon);
    }

    private void actualizarPrecioTotal() {

        String numeroFormateado = String.format("%.2f", mesa.getCuenta().getTotalCuenta());
        precioTotal.setText("Total: " + numeroFormateado + "€");
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
        buttonRedondeado2 = new swing.ButtonRedondeado();
        precioTotal = new javax.swing.JLabel();
        calc = new Swing.PanelRedondeado();
        panelRedondeado1 = new Swing.PanelRedondeado();
        operatorsPanel = new Swing.PanelComponentes();
        numbersPanel = new Swing.PanelComponentes();
        jPanel2 = new javax.swing.JPanel();
        calcText = new javax.swing.JLabel();
        panelRedondeado3 = new Swing.PanelRedondeado();
        productos = new Swing.PanelRedondeado();
        panelProductos = new Swing.PanelComponentes();
        panelGruposProductos = new Swing.PanelRedondeado();
        grupositos = new Swing.PanelComponentes();
        opcionesCuenta = new Swing.PanelRedondeado();
        control = new javax.swing.JPanel();
        tarjeta = new swing.ButtonRedondeado();
        efectivo = new swing.ButtonRedondeado();
        dividir = new swing.ButtonRedondeado();
        buttonRedondeado6 = new swing.ButtonRedondeado();
        buttonRedondeado7 = new swing.ButtonRedondeado();
        buttonRedondeado8 = new swing.ButtonRedondeado();
        buttonRedondeado9 = new swing.ButtonRedondeado();
        buttonRedondeado10 = new swing.ButtonRedondeado();
        buttonRedondeado11 = new swing.ButtonRedondeado();
        buttonRedondeado12 = new swing.ButtonRedondeado();
        atras = new swing.ButtonRedondeado();
        buttonRedondeado13 = new swing.ButtonRedondeado();
        buttonRedondeado14 = new swing.ButtonRedondeado();
        buttonRedondeado15 = new swing.ButtonRedondeado();
        buttonRedondeado16 = new swing.ButtonRedondeado();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1288, 864));

        jPanel1.setBackground(new java.awt.Color(153, 180, 209));
        jPanel1.setPreferredSize(new java.awt.Dimension(1288, 864));

        cabecera.setBackground(new java.awt.Color(255, 255, 255));
        cabecera.setPreferredSize(new java.awt.Dimension(1127, 34));

        tituloMesa.setBackground(new java.awt.Color(255, 255, 255));
        tituloMesa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tituloMesa.setPreferredSize(new java.awt.Dimension(1115, 30));

        javax.swing.GroupLayout cabeceraLayout = new javax.swing.GroupLayout(cabecera);
        cabecera.setLayout(cabeceraLayout);
        cabeceraLayout.setHorizontalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
                .addContainerGap())
        );
        cabeceraLayout.setVerticalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tituloMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        tabla.setBackground(new java.awt.Color(255, 255, 255));
        tabla.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabla.setMinimumSize(new java.awt.Dimension(338, 496));

        scrolltabla.setPreferredSize(new java.awt.Dimension(326, 402));

        tablaProductos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablaProductos.setModel(new MiModeloTabla());
        tablaProductos.setColumnSelectionAllowed(true);
        tablaProductos.setMinimumSize(new java.awt.Dimension(105, 80));
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        scrolltabla.setViewportView(tablaProductos);
        tablaProductos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(120);
            tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(20);
            tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(5);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

            //Columnas centrandas
            tablaProductos.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            tablaProductos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

            // Columnas no sean redimensionables
            tablaProductos.getColumnModel().getColumn(0).setResizable(false);
            tablaProductos.getColumnModel().getColumn(1).setResizable(false);
            tablaProductos.getColumnModel().getColumn(2).setResizable(false);

        }

        buttonRedondeado2.setBackground(new java.awt.Color(153, 180, 209));
        buttonRedondeado2.setForeground(new java.awt.Color(255, 255, 255));
        buttonRedondeado2.setText("EnviarPedido");
        buttonRedondeado2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonRedondeado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedondeado2ActionPerformed(evt);
            }
        });

        precioTotal.setFont(new java.awt.Font("Bahnschrift", 0, 36)); // NOI18N
        precioTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        precioTotal.setText("Total: 0.0€");

        javax.swing.GroupLayout tablaLayout = new javax.swing.GroupLayout(tabla);
        tabla.setLayout(tablaLayout);
        tablaLayout.setHorizontalGroup(
            tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrolltabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(precioTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tablaLayout.setVerticalGroup(
            tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrolltabla, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(precioTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(buttonRedondeado2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        calc.setBackground(new java.awt.Color(255, 255, 255));
        calc.setMaximumSize(new java.awt.Dimension(300, 365));
        calc.setPreferredSize(new java.awt.Dimension(332, 597));

        panelRedondeado1.setBackground(new java.awt.Color(255, 255, 255));

        operatorsPanel.setMaximumSize(new java.awt.Dimension(80, 32767));
        operatorsPanel.setPreferredSize(new java.awt.Dimension(65, 256));
        operatorsPanel.setLayout(new java.awt.GridLayout(4, 1, 5, 5));

        numbersPanel.setMinimumSize(new java.awt.Dimension(150, 256));
        numbersPanel.setPreferredSize(new java.awt.Dimension(205, 250));
        numbersPanel.setLayout(new java.awt.GridLayout(4, 3, 5, 5));

        javax.swing.GroupLayout panelRedondeado1Layout = new javax.swing.GroupLayout(panelRedondeado1);
        panelRedondeado1.setLayout(panelRedondeado1Layout);
        panelRedondeado1Layout.setHorizontalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRedondeado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numbersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(operatorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRedondeado1Layout.setVerticalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRedondeado1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numbersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(operatorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout panelRedondeado3Layout = new javax.swing.GroupLayout(panelRedondeado3);
        panelRedondeado3.setLayout(panelRedondeado3Layout);
        panelRedondeado3Layout.setHorizontalGroup(
            panelRedondeado3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRedondeado3Layout.setVerticalGroup(
            panelRedondeado3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
            .addGroup(calcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRedondeado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        calcLayout.setVerticalGroup(
            calcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelRedondeado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRedondeado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        productos.setBackground(new java.awt.Color(255, 255, 255));
        productos.setPreferredSize(new java.awt.Dimension(519, 597));

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

        panelGruposProductos.setBackground(new java.awt.Color(255, 255, 255));
        panelGruposProductos.setPreferredSize(new java.awt.Dimension(519, 211));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGruposProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grupositos, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );

        opcionesCuenta.setBackground(new java.awt.Color(255, 255, 255));
        opcionesCuenta.setMaximumSize(new java.awt.Dimension(500, 312));
        opcionesCuenta.setMinimumSize(new java.awt.Dimension(338, 211));
        opcionesCuenta.setPreferredSize(new java.awt.Dimension(338, 211));

        control.setBackground(new java.awt.Color(255, 255, 255));
        control.setPreferredSize(new java.awt.Dimension(0, 0));

        tarjeta.setBackground(new java.awt.Color(204, 204, 204));
        tarjeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Magnetic Card_60px.png"))); // NOI18N
        tarjeta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tarjeta.setMaximumSize(new java.awt.Dimension(100, 100));
        tarjeta.setMinimumSize(new java.awt.Dimension(50, 50));
        tarjeta.setPreferredSize(new java.awt.Dimension(60, 60));

        efectivo.setBackground(new java.awt.Color(204, 204, 204));
        efectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Cash_60px.png"))); // NOI18N
        efectivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        efectivo.setMaximumSize(new java.awt.Dimension(100, 100));
        efectivo.setMinimumSize(new java.awt.Dimension(50, 50));
        efectivo.setPreferredSize(new java.awt.Dimension(60, 60));

        dividir.setBackground(new java.awt.Color(204, 204, 204));
        dividir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tax_60px.png"))); // NOI18N
        dividir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dividir.setMaximumSize(new java.awt.Dimension(100, 100));
        dividir.setMinimumSize(new java.awt.Dimension(50, 50));
        dividir.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado6.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRedondeado6.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado6.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado6.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado7.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRedondeado7.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado7.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado7.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado8.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado8.setMaximumSize(null);
        buttonRedondeado8.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado8.setPreferredSize(new java.awt.Dimension(60, 60));
        buttonRedondeado8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedondeado8ActionPerformed(evt);
            }
        });

        buttonRedondeado9.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado9.setMaximumSize(null);
        buttonRedondeado9.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado9.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado10.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado10.setMaximumSize(null);
        buttonRedondeado10.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado10.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado11.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado11.setMaximumSize(null);
        buttonRedondeado11.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado11.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado12.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado12.setMaximumSize(null);
        buttonRedondeado12.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado12.setPreferredSize(new java.awt.Dimension(60, 60));

        atras.setBackground(new java.awt.Color(204, 204, 204));
        atras.setForeground(new java.awt.Color(255, 255, 255));
        atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/undo_60px.png"))); // NOI18N
        atras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        atras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        atras.setIconTextGap(0);
        atras.setMaximumSize(new java.awt.Dimension(100, 100));
        atras.setMinimumSize(new java.awt.Dimension(50, 50));
        atras.setPreferredSize(new java.awt.Dimension(60, 60));
        atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasActionPerformed(evt);
            }
        });

        buttonRedondeado13.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado13.setMaximumSize(null);
        buttonRedondeado13.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado13.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado14.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado14.setMaximumSize(null);
        buttonRedondeado14.setMinimumSize(new java.awt.Dimension(60, 60));
        buttonRedondeado14.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado15.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado15.setMaximumSize(null);
        buttonRedondeado15.setMinimumSize(new java.awt.Dimension(60, 60));
        buttonRedondeado15.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado16.setBackground(new java.awt.Color(204, 204, 204));
        buttonRedondeado16.setMaximumSize(null);
        buttonRedondeado16.setMinimumSize(new java.awt.Dimension(60, 60));
        buttonRedondeado16.setPreferredSize(new java.awt.Dimension(60, 60));

        javax.swing.GroupLayout controlLayout = new javax.swing.GroupLayout(control);
        control.setLayout(controlLayout);
        controlLayout.setHorizontalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlLayout.createSequentialGroup()
                        .addComponent(tarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(efectivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(dividir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRedondeado7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(controlLayout.createSequentialGroup()
                        .addComponent(atras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRedondeado16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(controlLayout.createSequentialGroup()
                        .addComponent(buttonRedondeado8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRedondeado12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );
        controlLayout.setVerticalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(efectivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dividir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRedondeado8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(atras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout opcionesCuentaLayout = new javax.swing.GroupLayout(opcionesCuenta);
        opcionesCuenta.setLayout(opcionesCuentaLayout);
        opcionesCuentaLayout.setHorizontalGroup(
            opcionesCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(control, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );
        opcionesCuentaLayout.setVerticalGroup(
            opcionesCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcionesCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(control, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, 1277, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tabla, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                            .addComponent(opcionesCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addComponent(calc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelGruposProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(calc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelGruposProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tabla, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opcionesCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {opcionesCuenta, panelGruposProductos});

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

    private void atrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atrasActionPerformed
        mesa.getCuenta().getPedidoProductos().clear();
        modelo.vaciarTabla();
        mesa = null;
        localForm.cerrarMesa();
        calcText.setText("0");
    }//GEN-LAST:event_atrasActionPerformed

    private void buttonRedondeado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedondeado2ActionPerformed
        localForm.getGestion().enviarPedido(mesa.getCuenta());
    }//GEN-LAST:event_buttonRedondeado2ActionPerformed

    private void buttonRedondeado8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedondeado8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonRedondeado8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonRedondeado atras;
    private swing.ButtonRedondeado buttonRedondeado10;
    private swing.ButtonRedondeado buttonRedondeado11;
    private swing.ButtonRedondeado buttonRedondeado12;
    private swing.ButtonRedondeado buttonRedondeado13;
    private swing.ButtonRedondeado buttonRedondeado14;
    private swing.ButtonRedondeado buttonRedondeado15;
    private swing.ButtonRedondeado buttonRedondeado16;
    private swing.ButtonRedondeado buttonRedondeado2;
    private swing.ButtonRedondeado buttonRedondeado6;
    private swing.ButtonRedondeado buttonRedondeado7;
    private swing.ButtonRedondeado buttonRedondeado8;
    private swing.ButtonRedondeado buttonRedondeado9;
    private Swing.PanelRedondeado cabecera;
    private Swing.PanelRedondeado calc;
    private javax.swing.JLabel calcText;
    private javax.swing.JPanel control;
    private swing.ButtonRedondeado dividir;
    private swing.ButtonRedondeado efectivo;
    private Swing.PanelComponentes grupositos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private Swing.PanelComponentes numbersPanel;
    private Swing.PanelRedondeado opcionesCuenta;
    private Swing.PanelComponentes operatorsPanel;
    private Swing.PanelRedondeado panelGruposProductos;
    private Swing.PanelComponentes panelProductos;
    private Swing.PanelRedondeado panelRedondeado1;
    private Swing.PanelRedondeado panelRedondeado3;
    private javax.swing.JLabel precioTotal;
    private Swing.PanelRedondeado productos;
    private javax.swing.JScrollPane scrolltabla;
    private Swing.PanelRedondeado tabla;
    private javax.swing.JTable tablaProductos;
    private swing.ButtonRedondeado tarjeta;
    private javax.swing.JLabel tituloMesa;
    // End of variables declaration//GEN-END:variables
}
