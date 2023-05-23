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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
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
        buttonRedondeado2 = new swing.ButtonRedondeado();
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
        buttonRedondeado3 = new swing.ButtonRedondeado();
        buttonRedondeado4 = new swing.ButtonRedondeado();
        buttonRedondeado5 = new swing.ButtonRedondeado();
        buttonRedondeado6 = new swing.ButtonRedondeado();
        buttonRedondeado7 = new swing.ButtonRedondeado();
        buttonRedondeado8 = new swing.ButtonRedondeado();
        buttonRedondeado9 = new swing.ButtonRedondeado();
        buttonRedondeado10 = new swing.ButtonRedondeado();
        buttonRedondeado11 = new swing.ButtonRedondeado();
        buttonRedondeado12 = new swing.ButtonRedondeado();
        Atras = new swing.ButtonRedondeado();
        buttonRedondeado13 = new swing.ButtonRedondeado();
        buttonRedondeado14 = new swing.ButtonRedondeado();
        buttonRedondeado15 = new swing.ButtonRedondeado();
        buttonRedondeado16 = new swing.ButtonRedondeado();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1138, 814));

        jPanel1.setBackground(new java.awt.Color(153, 180, 209));
        jPanel1.setPreferredSize(new java.awt.Dimension(1138, 814));

        cabecera.setBackground(new java.awt.Color(255, 255, 255));
        cabecera.setPreferredSize(new java.awt.Dimension(1127, 34));

        tituloMesa.setBackground(new java.awt.Color(255, 255, 255));
        tituloMesa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tituloMesa.setPreferredSize(new java.awt.Dimension(55, 30));

        javax.swing.GroupLayout cabeceraLayout = new javax.swing.GroupLayout(cabecera);
        cabecera.setLayout(cabeceraLayout);
        cabeceraLayout.setHorizontalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
                .addContainerGap())
        );
        cabeceraLayout.setVerticalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tituloMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        tabla.setBackground(new java.awt.Color(255, 255, 255));
        tabla.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabla.setPreferredSize(new java.awt.Dimension(338, 546));

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

        javax.swing.GroupLayout tablaLayout = new javax.swing.GroupLayout(tabla);
        tabla.setLayout(tablaLayout);
        tablaLayout.setHorizontalGroup(
            tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrolltabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tablaLayout.setVerticalGroup(
            tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrolltabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRedondeado2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        calc.setBackground(new java.awt.Color(255, 255, 255));
        calc.setMaximumSize(new java.awt.Dimension(300, 365));
        calc.setPreferredSize(new java.awt.Dimension(300, 546));

        panelRedondeado1.setBackground(new java.awt.Color(255, 255, 255));

        operatorsPanel.setMaximumSize(new java.awt.Dimension(80, 32767));
        operatorsPanel.setPreferredSize(new java.awt.Dimension(60, 256));
        operatorsPanel.setLayout(new java.awt.GridLayout(4, 1, 5, 5));

        numbersPanel.setMinimumSize(new java.awt.Dimension(150, 256));
        numbersPanel.setPreferredSize(new java.awt.Dimension(180, 256));
        numbersPanel.setLayout(new java.awt.GridLayout(4, 3, 5, 5));

        javax.swing.GroupLayout panelRedondeado1Layout = new javax.swing.GroupLayout(panelRedondeado1);
        panelRedondeado1.setLayout(panelRedondeado1Layout);
        panelRedondeado1Layout.setHorizontalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRedondeado1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numbersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(operatorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRedondeado1Layout.setVerticalGroup(
            panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRedondeado1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRedondeado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numbersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(operatorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
        productos.setPreferredSize(new java.awt.Dimension(478, 546));

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
        panelGruposProductos.setPreferredSize(new java.awt.Dimension(478, 211));

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
                .addComponent(grupositos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        opcionesCuenta.setBackground(new java.awt.Color(255, 255, 255));
        opcionesCuenta.setPreferredSize(new java.awt.Dimension(338, 211));

        control.setBackground(new java.awt.Color(255, 255, 255));
        control.setPreferredSize(new java.awt.Dimension(330, 199));

        buttonRedondeado3.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado3.setText("Tarjeta");
        buttonRedondeado3.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado3.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado3.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado4.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado4.setText("Efectivo");
        buttonRedondeado4.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado4.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado4.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado5.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado5.setText("Dividir");
        buttonRedondeado5.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado5.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado5.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado6.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado6.setText("buttonRedondeado4");
        buttonRedondeado6.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado6.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado6.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado7.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado7.setText("buttonRedondeado3");
        buttonRedondeado7.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado7.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado7.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado8.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado8.setText("buttonRedondeado3");
        buttonRedondeado8.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado8.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado8.setPreferredSize(new java.awt.Dimension(60, 60));
        buttonRedondeado8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRedondeado8ActionPerformed(evt);
            }
        });

        buttonRedondeado9.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado9.setText("buttonRedondeado3");
        buttonRedondeado9.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado9.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado9.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado10.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado10.setText("buttonRedondeado3");
        buttonRedondeado10.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado10.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado10.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado11.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado11.setText("buttonRedondeado3");
        buttonRedondeado11.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado11.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado11.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado12.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado12.setText("buttonRedondeado3");
        buttonRedondeado12.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado12.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado12.setPreferredSize(new java.awt.Dimension(60, 60));

        Atras.setBackground(new java.awt.Color(255, 204, 204));
        Atras.setForeground(new java.awt.Color(255, 255, 255));
        Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-chevron-izquierda-en-círculo-100.png"))); // NOI18N
        Atras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Atras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Atras.setIconTextGap(0);
        Atras.setMaximumSize(new java.awt.Dimension(100, 100));
        Atras.setMinimumSize(new java.awt.Dimension(50, 50));
        Atras.setOpaque(true);
        Atras.setPreferredSize(new java.awt.Dimension(60, 60));
        Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtrasActionPerformed(evt);
            }
        });

        buttonRedondeado13.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado13.setText("buttonRedondeado3");
        buttonRedondeado13.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRedondeado13.setMinimumSize(new java.awt.Dimension(50, 50));
        buttonRedondeado13.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado14.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado14.setText("buttonRedondeado3");
        buttonRedondeado14.setMaximumSize(new java.awt.Dimension(250, 250));
        buttonRedondeado14.setMinimumSize(new java.awt.Dimension(60, 60));
        buttonRedondeado14.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado15.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado15.setText("buttonRedondeado3");
        buttonRedondeado15.setMaximumSize(new java.awt.Dimension(250, 250));
        buttonRedondeado15.setMinimumSize(new java.awt.Dimension(60, 60));
        buttonRedondeado15.setPreferredSize(new java.awt.Dimension(60, 60));

        buttonRedondeado16.setBackground(new java.awt.Color(255, 204, 204));
        buttonRedondeado16.setText("buttonRedondeado3");
        buttonRedondeado16.setMaximumSize(new java.awt.Dimension(250, 250));
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
                        .addComponent(buttonRedondeado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(controlLayout.createSequentialGroup()
                        .addComponent(buttonRedondeado8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(controlLayout.createSequentialGroup()
                        .addComponent(Atras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(buttonRedondeado16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );
        controlLayout.setVerticalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRedondeado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRedondeado5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(Atras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(control, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );
        opcionesCuentaLayout.setVerticalGroup(
            opcionesCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcionesCuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(control, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(opcionesCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(calc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelGruposProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opcionesCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtrasActionPerformed
        mesa.getCuenta().getPedidoProductos().clear();
        modelo.vaciarTabla();
        mesa = null;
        localForm.cerrarMesa();
        calcText.setText("0");
    }//GEN-LAST:event_AtrasActionPerformed

    private void buttonRedondeado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedondeado2ActionPerformed
        localForm.getGestion().enviarPedido(mesa.getCuenta());
    }//GEN-LAST:event_buttonRedondeado2ActionPerformed

    private void buttonRedondeado8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRedondeado8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonRedondeado8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonRedondeado Atras;
    private swing.ButtonRedondeado buttonRedondeado10;
    private swing.ButtonRedondeado buttonRedondeado11;
    private swing.ButtonRedondeado buttonRedondeado12;
    private swing.ButtonRedondeado buttonRedondeado13;
    private swing.ButtonRedondeado buttonRedondeado14;
    private swing.ButtonRedondeado buttonRedondeado15;
    private swing.ButtonRedondeado buttonRedondeado16;
    private swing.ButtonRedondeado buttonRedondeado2;
    private swing.ButtonRedondeado buttonRedondeado3;
    private swing.ButtonRedondeado buttonRedondeado4;
    private swing.ButtonRedondeado buttonRedondeado5;
    private swing.ButtonRedondeado buttonRedondeado6;
    private swing.ButtonRedondeado buttonRedondeado7;
    private swing.ButtonRedondeado buttonRedondeado8;
    private swing.ButtonRedondeado buttonRedondeado9;
    private Swing.PanelRedondeado cabecera;
    private Swing.PanelRedondeado calc;
    private javax.swing.JLabel calcText;
    private javax.swing.JPanel control;
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
    private Swing.PanelRedondeado productos;
    private javax.swing.JScrollPane scrolltabla;
    private Swing.PanelRedondeado tabla;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JLabel tituloMesa;
    // End of variables declaration//GEN-END:variables
}
