package iu;

import Swing.ScrollBar;
import Swing.component.NumeroComponent;
import Swing.component.UserComponent;
import Swing.component.ModelNumero;
import modelo.Usuario;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Iterator;
import javax.swing.SwingUtilities;
import modelo.Gestion;

/**
 *
 * @author ThePandogs
 *
 * La clase `LoginForm` representa un panel de inicio de sesión en la interfaz
 * de usuario. Permite a los usuarios seleccionar su nombre de usuario, ingresar
 * su contraseña y acceder a la aplicación.
 */
public class LoginForm extends javax.swing.JPanel {

    Gestion gestion;
    Interfaz interfaz;
    Usuario usuarioSeleccionado;

    /**
     * Constructor de la clase LoginForm.
     *
     * @param gestion la instancia de Gestion utilizada en el programa
     * @param interfaz la instancia de Interfaz utilizada en el programa
     */
    public LoginForm(Gestion gestion, Interfaz interfaz) {
        this.gestion = gestion;
        this.interfaz = interfaz;
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
        init();

    }

    private void init() {
        cargarTecladoNumerico();
        cargarUsuarios();
    }

    /**
     *
     * Añade un usuario al panel de usuarios y le asocia un MouseListener para
     * activar la interacción con el usuario cuando se hace clic con el botón
     * izquierdo del ratón sobre él. También actualiza el nombre del usuario en
     * la interfaz y habilita el campo de contraseña para introducir la clave de
     * acceso.
     *
     * @param data objeto Usuario que contiene la información del usuario a
     * añadir
     */
    public void addUser(Usuario data) {
        UserComponent user = new UserComponent();
        user.setData(data);
        user.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    usuarioSeleccionado = data;
                    setSelected(user);
                    nombre.setText(data.getItemName());
                    if (!campoPassword.isEditable()) {
                        campoPassword.setEditable(true);
                        advertencia.setText("");
                    }

                    botonLogin.setBackground(new java.awt.Color(94, 156, 255));
                }
            }
        });
        panelUsuarios.add(user);
        panelUsuarios.repaint();
        panelUsuarios.revalidate();
    }

    /**
     * Carga los usuarios desde el array de usuarios almacenados en gestion y
     * los añade al panel de usuarios.
     *
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    private void cargarUsuarios() {

        Iterator<Usuario> usuarios = gestion.getUsuarios().iterator();

        while (usuarios.hasNext()) {

            addUser(usuarios.next());

        }
    }

    /**
     *
     * Carga los componentes del teclado numérico en la interfaz de usuario.
     *
     * Incluye los números del 1 al 9, el botón de borrar (C), el número 0
     *
     * y la flecha izquierda como botón de retroceso.
     */
    private void cargarTecladoNumerico() {

        for (int i = 1; i < 10; i++) {
            addNumero(new ModelNumero(String.valueOf(i)));
        }
        addNumero(new ModelNumero("C"));
        addNumero(new ModelNumero("0"));

        char flechaIzquierda = '\u2190';
        addNumero(new ModelNumero(Character.toString(flechaIzquierda)));
    }

    /**
     *
     * Añade un botón numérico al teclado numérico.
     *
     * @param data los datos que se muestran en el botón numérico.
     */
    private void addNumero(ModelNumero data) {
        NumeroComponent numero = new NumeroComponent();
        numero.setData(data);
        numero.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (campoPassword.isEditable()) {
                        interaccionTecladoNumerico(data);
                    }

                }
            }

        });
        tecladoNumerico.add(numero);
        tecladoNumerico.repaint();
        tecladoNumerico.revalidate();
    }

    /**
     *
     * Maneja la interacción del teclado numérico en la aplicación.
     *
     * Si el valor de "data" es "C", se borra el contenido del campo de
     * contraseña.
     *
     * Si el valor de "data" es "←" caracter (\u2190), se elimina el último
     * caracter ingresado en el campo de contraseña.
     *
     * Si el valor de "data" es un dígito, se agrega ese dígito al final del
     * campo de contraseña.
     *
     * @param data objeto de tipo ModelNumero que representa la información del
     * botón de teclado numérico presionado
     */
    private void interaccionTecladoNumerico(ModelNumero data) {
        //LimpiarPanel
        if (data.getNumero().equals("C")) {
            campoPassword.setText("");
        } else {
            String passwordString = new String(campoPassword.getPassword());

            //Eliminar un caracter
            if (data.getNumero().equals(String.valueOf('\u2190'))) {
                if (passwordString.isEmpty()) {
                    return;
                }
                StringBuilder sb = new StringBuilder(passwordString);
                sb.deleteCharAt(passwordString.length() - 1);
                passwordString = sb.toString();

            } //Anadir un caracter
            else {
                passwordString += data.getNumero();
            }
            campoPassword.setText(passwordString);

        }
    }

    /**
     *
     * Selecciona un componente de usuario y deselecciona los demás componentes
     * de usuario en el panel de usuarios. También limpia el campo de contraseña
     * y establece el foco en el campo de contraseña.
     *
     * @param user el componente de usuario a seleccionar
     */
    private void setSelected(Component user) {
        for (Component com : panelUsuarios.getComponents()) {
            UserComponent i = (UserComponent) com;
            if (i.isSelected()) {
                i.setSelected(false);
            }
        }
        ((UserComponent) user).setSelected(true);
        campoPassword.setText("");
        campoPassword.requestFocus();
    }

    /**
     * Devuelve la posición actual del panel en relación a la posición de la
     * vista del JScrollPane.
     *
     * @return la ubicación del panel en un objeto Point
     *
     */
    public Point getPanelItemLocation() {
        Point p = scroll.getLocation();
        return new Point(p.x, p.y - scroll.getViewport().getViewPosition().y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        panelUsuarios = new Swing.PanelComponentes();
        panelNumerico = new javax.swing.JPanel();
        nombre = new javax.swing.JLabel();
        inicioSesion = new javax.swing.JLabel();
        advertencia = new javax.swing.JLabel();
        tecladoNumerico = new Swing.PanelComponentes();
        campoPassword = new swing.Password();
        botonLogin = new swing.ButtonRedondeado();

        setOpaque(false);

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelUsuarios);

        panelNumerico.setBackground(new java.awt.Color(255, 255, 255));

        nombre.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        nombre.setForeground(new java.awt.Color(102, 102, 102));
        nombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        inicioSesion.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        inicioSesion.setForeground(new java.awt.Color(102, 102, 102));
        inicioSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inicioSesion.setText("Inicio Sesión");

        advertencia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        advertencia.setForeground(new java.awt.Color(255, 102, 102));
        advertencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tecladoNumerico.setMaximumSize(new java.awt.Dimension(100, 100));
        tecladoNumerico.setLayout(new java.awt.GridLayout(4, 3, 10, 10));

        campoPassword.setEditable(false);
        campoPassword.setText("password");
        campoPassword.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        campoPassword.setPreferredSize(new java.awt.Dimension(100, 50));

        botonLogin.setBackground(new java.awt.Color(102, 102, 102));
        botonLogin.setForeground(new java.awt.Color(255, 255, 255));
        botonLogin.setText("Acceder");
        botonLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        botonLogin.setPreferredSize(new java.awt.Dimension(100, 50));
        botonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNumericoLayout = new javax.swing.GroupLayout(panelNumerico);
        panelNumerico.setLayout(panelNumericoLayout);
        panelNumericoLayout.setHorizontalGroup(
            panelNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelNumericoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(advertencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tecladoNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inicioSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelNumericoLayout.setVerticalGroup(
            panelNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNumericoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(inicioSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(campoPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(advertencia, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tecladoNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelNumerico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLoginActionPerformed
        if (!campoPassword.isEditable()) {
            advertencia.setText("* Elige un Usuario!");
        } else if (gestion.comprobarUsuario(nombre.getText(), new String(campoPassword.getPassword()))) {
            gestion.setCamareroActual(gestion.getLocales().get(0).getCamareros().get(usuarioSeleccionado.getIdEmpleado()));
            interfaz.cambiarFormulario(interfaz.getLocalForm());
        } else {

            advertencia.setText("* PIN incorrecto!");
            campoPassword.setText("");
            campoPassword.requestFocus();
        }
    }//GEN-LAST:event_botonLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel advertencia;
    private swing.ButtonRedondeado botonLogin;
    private swing.Password campoPassword;
    private javax.swing.JLabel inicioSesion;
    private javax.swing.JLabel nombre;
    private javax.swing.JPanel panelNumerico;
    private Swing.PanelComponentes panelUsuarios;
    private javax.swing.JScrollPane scroll;
    private Swing.PanelComponentes tecladoNumerico;
    // End of variables declaration//GEN-END:variables
}
