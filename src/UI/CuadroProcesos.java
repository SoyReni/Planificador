/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Datos.Proceso;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.PriorityQueue;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Reni
 */
public class CuadroProcesos extends JLabel {

    public CuadroProcesos(String titulo, PriorityQueue<Proceso> procesos, boolean rr) {
        this._titulo = titulo;
        this._procesos = procesos;
        this._rr = rr;
        this._datos = getDatos();
        crearCuadro();
    }

    private void crearCuadro() {
        this.removeAll();
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints limites = new GridBagConstraints();
        String columnNames[] = {"Llegada", "Nombre", "Prioridad", "CPU inicial", "CPU rest"};
        String columnNamesrr[] = {"Llegada", "Nombre", "Prioridad", "CPU inicial", "CPU rest", "RR"};

        this.tituloContenedor = new JLabel();
        this.tituloContenedor.setFont(ftitulos);
        this.tituloContenedor.setText(_titulo);
        limites.gridx = 0;
        limites.gridy = 0;
        limites.weightx = 1;
        limites.weighty = 0.01;
        limites.insets = new Insets(5, 5, 5, 5);
        limites.fill = GridBagConstraints.BOTH;
        limites.anchor = GridBagConstraints.NORTH;
        this.add(this.tituloContenedor, limites);

        if (this._rr) {
            this.procesosContenedor = new JTable(_datos, columnNamesrr);
        } else {
            this.procesosContenedor = new JTable(_datos, columnNames);
        }
        this.scrollTabla = new JScrollPane(this.procesosContenedor);
        this.scrollTabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollTabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        limites.weightx = 1;
        limites.weighty = 0.95;
        limites.gridy = 1;
        this.add(this.scrollTabla, limites);

    }

    private Object[][] getDatos() {
        int len = this._procesos.size();
        PriorityQueue<Proceso> queue = this._procesos;
        if (this._rr) {
            Object[][] ret = new Object[len][6];
            for (int i = 0; i < len; i++) {
                Proceso p = queue.poll();
                ret[i][0] = p.getLlegada();
                ret[i][1] = p.getNombre();
                ret[i][2] = p.getPrio();
                ret[i][3] = p.getCpuIni();
                ret[i][4] = p.getCpuRest();
                ret[i][5] = p.getRr(); 
            }
            return ret;
        } else {
            Object[][] ret = new Object[len][5];
            for (int i = 0; i < len; i++) {
                Proceso p = queue.poll();
                ret[i][0] = p.getLlegada();
                ret[i][1] = p.getNombre();
                ret[i][2] = p.getPrio();
                ret[i][3] = p.getCpuIni();
                ret[i][4] = p.getCpuRest();
            }
            return ret;
        }
    }

    private JScrollPane scrollTabla;
    private JLabel tituloContenedor;
    private JTable procesosContenedor;

    private static final Font ftitulos = new Font("sans serif", Font.BOLD, 14);

    private Object[][] _datos;
    private String _titulo;
    private PriorityQueue<Proceso> _procesos;
    private boolean _rr = false;
}
