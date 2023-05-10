/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author ThePandogs
 */
public class LogExcepcion {

    private LocalDateTime data;
    private LocalTime time;
    File archivoExcepciones;

    /**
     * Contruye un objeto al cual podremos llamar para guardar la informacion
     * sobre la excepcion
     */
    public LogExcepcion() {
        archivoExcepciones = new File("src/Log/Exception.txt");
    }

    /**
     * Añade una exception con respectiva Nombre-Fecha-Hora-Motivo al File
     * archivoExcepciones
     *
     * @param e parametro que pasamos
     */
    public void anadirExcepcionLog(Exception e) {
        data = LocalDateTime.now();
        time = LocalTime.now();
        try (PrintWriter saida = new PrintWriter((new FileWriter(archivoExcepciones, true)))) {

            saida.println("Excepción: " + e.getClass());
            saida.println("Fecha:  " + data.format(DateTimeFormatter.ISO_DATE));
            saida.println("Hora:   " + time.truncatedTo(ChronoUnit.SECONDS));
            saida.println("Motivo: " + e.getLocalizedMessage());
            saida.println();
            
        } catch (IOException ex) {
            Logger.getLogger(LogExcepcion.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println( e.getCause());
    }
}
