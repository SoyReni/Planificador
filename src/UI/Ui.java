/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Datos.ManejadorDeProcesos;
import Datos.Proceso;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author Reni
 */
public class Ui extends JFrame {
    
    public Ui() {
        super();
        
        this._manejador = new ManejadorDeProcesos();
        this._manejador.getProcesosFromFile("MisProcesos.txt");
        this._procesos = new PriorityQueue<>();
        this._penAtencion = new PriorityQueue<>();
        this._pfinalizados = new PriorityQueue<>();
        this._plistos = new PriorityQueue<>();
        
        this.ventana = new JLabel("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.crear();
        this.pack();
        this.setVisible(true);
        Dimension d = new Dimension(1080, 720);
        this.setSize(d);
        this.setResizable(true);
        this.setLocation(300, 20);
        this.setTitle("Planificador de procesos");
    }
    
    private void crear() {
        this.ventana.setLayout(new GridBagLayout());
        GridBagConstraints limites = new GridBagConstraints();
        limites.insets = new Insets(5, 5, 5, 5);
        limites.fill = GridBagConstraints.BOTH;
        limites.anchor = GridBagConstraints.NORTH;
        
        this.t_cpu = new JLabel();
        this.t_cpu.setText("Planeamiento de cpu");
        this.t_cpu.setFont(ftitulos);
        this.t_cpu.setHorizontalAlignment(SwingConstants.CENTER);
        this.t_cpu.setOpaque(false);
        limites.weighty = 0.025;
        limites.weightx = 0.5;
        limites.gridx = 0;
        limites.gridy = 0;
        this.ventana.add(this.t_cpu, limites);
        
        this.t_mem = new JLabel();
        this.t_mem.setText("Memoria");
        this.t_mem.setFont(ftitulos);
        this.t_mem.setHorizontalAlignment(SwingConstants.CENTER);
        this.t_mem.setOpaque(false);
        limites.weightx = 0.4;
        limites.gridx = 2;
        limites.gridy = 0;
        this.ventana.add(this.t_mem, limites);
        
        this.t_panel = new JLabel();
        this.t_panel.setText("Panel de control");
        this.t_panel.setFont(ftitulos);
        this.t_panel.setHorizontalAlignment(SwingConstants.CENTER);
        this.t_panel.setOpaque(false);
        limites.weightx = 0.1;
        limites.gridx = 4;
        limites.gridy = 0;
        this.ventana.add(this.t_panel, limites);
        
        this.cont_procesos = new JLabel();
        this.cont_procesos.setHorizontalAlignment(SwingConstants.CENTER);
        this.cont_procesos.setOpaque(false);
        this.cont_procesos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.cargarProcesos();
        limites.weightx = 0.55;
        limites.weighty = 0.975;
        limites.gridx = 0;
        limites.gridy = 1;
        this.ventana.add(this.cont_procesos, limites);
        
        this.cont_memoria = new JLabel();
        this.cont_memoria.setHorizontalAlignment(SwingConstants.CENTER);
        this.cont_memoria.setOpaque(false);
        this.cont_memoria.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        limites.weightx = 0.3;
        limites.gridx = 2;
        limites.gridy = 1;
        this.ventana.add(this.cont_memoria, limites);
        
        this.cargarPanelControl();
        limites.weightx = 0.15;
        limites.weighty = 0.5;
        limites.gridx = 4;
        limites.gridy = 1;
        this.ventana.add(this.cont_panel, limites);
        
        this.add(ventana, BorderLayout.CENTER);
        
    }
    
    private void cargarPanelControl() {
        GridBagConstraints limites = new GridBagConstraints();
        limites.gridwidth = 5;
        limites.insets = new Insets(5, 5, 5, 5);
        limites.fill = GridBagConstraints.BOTH;
        limites.anchor = GridBagConstraints.NORTH;
        limites.weightx = 1;
        limites.weighty = 1;
        
        this.cont_panel = new JLabel();
        this.cont_panel.setHorizontalAlignment(SwingConstants.LEFT);
        this.cont_panel.setOpaque(false);
        this.cont_panel.setLayout(new GridBagLayout());
        this.cont_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        this.titulo_ACPU = new JLabel();
        this.titulo_ACPU.setHorizontalAlignment(SwingConstants.LEFT);
        this.titulo_ACPU.setOpaque(false);
        this.titulo_ACPU.setText("Algoritmo CPU:");
        limites.gridx = 0;
        limites.gridy = 0;
        this.cont_panel.add(this.titulo_ACPU, limites);
        
        this.ACPU = new JComboBox();
        this.ACPU.setOpaque(false);
        this.ACPU.addItem(FCFS);
        this.ACPU.addItem(SJF);
        this.ACPU.addItem(RR);
        this.ACPU.addItem(HRRN);
        limites.gridx = 0;
        limites.gridy = 1;
        this.cont_panel.add(this.ACPU, limites);
        
        this.titulo_AMem = new JLabel();
        this.titulo_AMem.setHorizontalAlignment(SwingConstants.LEFT);
        this.titulo_AMem.setOpaque(false);
        this.titulo_AMem.setText("Algoritmo memoria");
        limites.gridx = 0;
        limites.gridy = 2;
        this.cont_panel.add(this.titulo_AMem, limites);
        
        this.AMem = new JComboBox();
        this.AMem.addItem("Proximamente");
        this.AMem.setOpaque(false);
        this.AMem.setEnabled(false);
        limites.gridx = 0;
        limites.gridy = 3;
        this.cont_panel.add(this.AMem, limites);
        
        this.titulo_macmem = new JLabel();
        this.titulo_macmem.setHorizontalAlignment(SwingConstants.LEFT);
        this.titulo_macmem.setOpaque(false);
        this.titulo_macmem.setText("Macro de memoria");
        limites.gridx = 0;
        limites.gridy = 4;
        this.cont_panel.add(this.titulo_macmem, limites);
        
        this.macros_mem = new JSpinner(new SpinnerNumberModel(MACROI, MACROMIN, MACROMAX, MACROSTEP));
        this.macros_mem.setOpaque(false);
        ((DefaultEditor) this.macros_mem.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor) this.macros_mem.getEditor()).getTextField().setBackground(Color.WHITE);
        ((DefaultEditor) this.macros_mem.getEditor()).getTextField().setOpaque(true);
        limites.gridx = 0;
        limites.gridy = 5;
        this.cont_panel.add(this.macros_mem, limites);
        
        this.titulo_tiempocpu = new JLabel();
        this.titulo_tiempocpu.setHorizontalAlignment(SwingConstants.LEFT);
        this.titulo_tiempocpu.setOpaque(false);
        this.titulo_tiempocpu.setText("Tiempo de CPU (seg)");
        limites.gridx = 0;
        limites.gridy = 6;
        this.cont_panel.add(this.titulo_tiempocpu, limites);
        
        this.tiempo_CPU = new JSpinner(new SpinnerNumberModel(TIEMPOI, TIEMPOMIN, TIEMPOMAX, TIEMPOSTEP));
        this.tiempo_CPU.setOpaque(false);
        ((DefaultEditor) this.tiempo_CPU.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor) this.tiempo_CPU.getEditor()).getTextField().setBackground(Color.WHITE);
        ((DefaultEditor) this.tiempo_CPU.getEditor()).getTextField().setOpaque(true);
        limites.gridx = 0;
        limites.gridy = 7;
        this.cont_panel.add(this.tiempo_CPU, limites);
        
        this.titulo_cuantum = new JLabel();
        this.titulo_cuantum.setHorizontalAlignment(SwingConstants.LEFT);
        this.titulo_cuantum.setOpaque(false);
        this.titulo_cuantum.setText("Quantum");
        limites.gridx = 0;
        limites.gridy = 8;
        this.cont_panel.add(this.titulo_cuantum, limites);
        
        this.quantum = new JSpinner(new SpinnerNumberModel(QUANTUMI, QUANTUMMIN, QUANTUMMAX, QUANTUMSTEP));
        this.quantum.setOpaque(false);
        ((DefaultEditor) this.quantum.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor) this.quantum.getEditor()).getTextField().setBackground(Color.WHITE);
        ((DefaultEditor) this.quantum.getEditor()).getTextField().setOpaque(true);
        limites.gridx = 0;
        limites.gridy = 9;
        this.cont_panel.add(this.quantum, limites);
        
        this.go = new JButton();
        this.go.setText("Run");
        this.go.setHorizontalAlignment(SwingConstants.CENTER);
        this.go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarRafagas();
            }
            
        });
        limites.gridx = 0;
        limites.gridy = 10;
        this.cont_panel.add(this.go, limites);
        
        this.pause = new JButton();
        this.pause.setText("Pause");
        this.pause.setHorizontalAlignment(SwingConstants.CENTER);
        this.pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausar();
            }
        });
        limites.gridx = 0;
        limites.gridy = 11;
        this.cont_panel.add(this.pause, limites);
        
        this.stop = new JButton();
        this.stop.setText("Stop");
        this.stop.setHorizontalAlignment(SwingConstants.CENTER);
        this.stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parar();
            }
        });
        limites.gridx = 0;
        limites.gridy = 12;
        this.cont_panel.add(this.stop, limites);
        
        this.t = new JLabel();
        this.t.setOpaque(false);
        this.t.setText("T");
        this.t.setHorizontalAlignment(SwingConstants.LEFT);
        limites.gridx = 0;
        limites.gridy = 13;
        this.cont_panel.add(this.t, limites);
        
        this.cpu = new JLabel();
        this.cpu.setOpaque(true);
        this.cpu.setBackground(VERDE);
        limites.gridx = 0;
        limites.gridy = 14;
        this.cont_panel.add(this.cpu, limites);
        
    }
    
    private void pausar() {
        if (paused) {
            paused = false;
            this.pause.setText("Pause");
        } else {
            paused = true;
            this.pause.setText("Continue");
        }
    }
    
    private void parar() {
        if (!stopped) {
            stopped = true;
            paused = false;
            this.pause.setText("Pause");
            this.cpu.setBackground(VERDE);
            this.t.setText("T");
            this.go.setEnabled(true);
            this.ACPU.setEnabled(true);
            this.macros_mem.setEnabled(true);
            this.quantum.setEnabled(true);
            this.tiempo_CPU.setEnabled(true);
        }
    }
    
    private void cargarProcesos() {
        this.cont_procesos.removeAll();
        GridBagConstraints limites = new GridBagConstraints();
        limites.insets = new Insets(5, 5, 5, 5);
        limites.weightx = 0.5;
        limites.weighty = 0.5;
        limites.fill = GridBagConstraints.BOTH;
        limites.anchor = GridBagConstraints.NORTH;
        
        this.cont_procesos.setLayout(new GridBagLayout());
        
        PriorityQueue<Proceso> p = this._manejador.getProcesos();
        PriorityQueue<Proceso> l = this._manejador.getProcesosListos();
        PriorityQueue<Proceso> a = this._manejador.getProcesosEnAtencion();
        PriorityQueue<Proceso> f = this._manejador.getProcesosFinalizados();
        
        this._procesosLista = new CuadroProcesos("Lista de procesos", p, this.algoritmo.equals(HRRN));
        this._listos = new CuadroProcesos("Procesos listos", l, this.algoritmo.equals(HRRN));
        this._enAtencion = new CuadroProcesos("Procesos siendo atendidos", a, this.algoritmo.equals(HRRN));
        this._finalizados = new CuadroProcesos("Procesos finalizados", f, this.algoritmo.equals(HRRN));
        
        limites.gridx = 0;
        limites.gridy = 0;
        this.cont_procesos.add(this._procesosLista, limites);
        
        limites.gridx = 1;
        limites.gridy = 0;
        this.cont_procesos.add(this._listos, limites);
        
        limites.gridx = 0;
        limites.gridy = 1;
        this.cont_procesos.add(this._enAtencion, limites);
        
        limites.gridx = 1;
        limites.gridy = 1;
        this.cont_procesos.add(this._finalizados, limites);
    }
    
    private void iniciarRafagas() {
        cpuTime = (double) this.tiempo_CPU.getValue() * 1000;
        this.go.setEnabled(false);
        this.ACPU.setEnabled(false);
        this.macros_mem.setEnabled(false);
        this.quantum.setEnabled(false);
        this.tiempo_CPU.setEnabled(false);
        algoritmo = this.ACPU.getSelectedItem().toString();
        quant = (int) this.quantum.getValue();        
        this._manejador.getProcesosFromFile(PATH);
        cont = 0;
        flag = true;
        proc = true;
        paused = false;
        this.pause.setText("Pause");
        stopped = false;
        timer = new Timer((int) cpuTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paused) {
                } else if (stopped || !_manejador.doseguir()) {
                    _manejador.clear();
                    cargarProcesos();
                    parar();
                    ((Timer) e.getSource()).stop();
                } else {
                    if (!flag) {
                        ((Timer) e.getSource()).stop();
                    } else {
                        cpu.setBackground(color);
                        t.setText("T" + cont);
                        while (proc) {
                            proc = _manejador.recibirProceso(cont);
                        }
                        proc = true;
                        cont++;
                        switch (algoritmo) {
                            case FCFS ->
                                _manejador.fcfs(cont);
                            case SJF ->
                                _manejador.sjf(cont);
                            case RR ->
                                _manejador.RoundRobin(cont, quant);
                            case HRRN ->
                                _manejador.HRRN(cont);
                            default -> {
                            }
                        }
                        cargarProcesos();
                        if (cont > MAX || !_manejador.doseguir()) {
                            go.setEnabled(true);
                            flag = false;
                        }
                        if (cont % 2 == 0) {
                            color = VERDE;
                        } else {
                            color = Color.GREEN;
                        }
                        
                    }
                }
            }
        }
        );
        timer.setRepeats(true);
        timer.start();
        this._manejador.empezar();
    }
    
    private JLabel ventana;
    private JLabel t_cpu;
    private JLabel t_mem;
    private JLabel t_panel;
    
    private JLabel cont_panel;
    private JLabel titulo_ACPU;
    private JComboBox ACPU;
    private JLabel titulo_AMem;
    private JComboBox AMem;
    private JLabel titulo_macmem;
    private JSpinner macros_mem;
    private JLabel titulo_tiempocpu;
    private JSpinner tiempo_CPU;
    private JLabel titulo_cuantum;
    private JSpinner quantum;
    private JButton go;
    private JButton pause;
    private JButton stop;
    private JLabel t;
    private JLabel cpu;
    
    private JLabel cont_procesos;
    private JLabel cont_memoria;
    
    private ManejadorDeProcesos _manejador;
    private PriorityQueue<Proceso> _procesos;
    private PriorityQueue<Proceso> _plistos;
    private PriorityQueue<Proceso> _penAtencion;
    private PriorityQueue<Proceso> _pfinalizados;
    private CuadroProcesos _listos;
    private CuadroProcesos _procesosLista;
    private CuadroProcesos _enAtencion;
    private CuadroProcesos _finalizados;
    private boolean proc = true;
    private boolean flag = true;
    private boolean paused = false;
    private boolean stopped = false;
    private int cont = 0;
    private Timer timer;
    private Color color;
    private double cpuTime = 1000;
    private String algoritmo = FCFS;
    private int quant;    
    
    private static final int MACROI = 1;
    private static final int MACROMIN = 0;
    private static final int MACROMAX = 10;
    private static final int MACROSTEP = 1;
    private static final Color VERDE = new Color(34, 139, 34);
    
    private static final double TIEMPOI = 1;
    private static final double TIEMPOMIN = 0.5;
    private static final double TIEMPOMAX = 5;
    private static final double TIEMPOSTEP = 0.5;
    
    private static final int QUANTUMI = 4;
    private static final int QUANTUMMIN = 1;
    private static final int QUANTUMMAX = 8;
    private static final int QUANTUMSTEP = 1;
    
    private static final String FCFS = "FCFS";
    private static final String SJF = "SJF expulsivo";
    private static final String RR = "Round Robin";
    private static final String HRRN = "HRRN";
    
    private static final Font ftitulos = new Font("sans serif", Font.BOLD, 14);
    private static final String PATH = "MisProcesos.txt";
    private static final int MAX = 1000;
}
