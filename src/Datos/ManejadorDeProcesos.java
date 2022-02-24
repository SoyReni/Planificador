/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reni
 */
public class ManejadorDeProcesos {

    public ManejadorDeProcesos() {
        clear();
    }

    /*Lee procesos del archivo*/
    public void getProcesosFromFile(String path) {
        this.clear();
        int cont = 1;
        try {
            clear();
            URL url = getClass().getResource(path);
            File archivo = new File(url.getPath());
            String linea;
            //carga un pq por orden de llegada
            PriorityQueue<Proceso> queue = new PriorityQueue<>();
            FileReader fr = new FileReader(archivo);
            BufferedReader lector = new BufferedReader(fr);
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    int llegada = parseInt(datos[0]);
                    String nombre = datos[1];
                    int prio = parseInt(datos[2]);
                    int cpuIni = parseInt(datos[3]);
                    Proceso p = new Proceso(llegada, nombre, prio, cpuIni);
                    p.setPos(cont);
                    cont++;
                    this.siguiente = cont;
                    queue.add(p);
                }
            }
            this._procesos = queue;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorDeProcesos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(ManejadorDeProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clear() {
        this._lista = new PriorityQueue<>();
        this._enAtencion = new PriorityQueue<>();
        this._listos = new PriorityQueue<>();
        this._finalizados = new PriorityQueue<>();
    }

    public PriorityQueue<Proceso> getProcesos() {
        PriorityQueue<Proceso> lista = new PriorityQueue<>(this._lista);
        return lista;
    }

    public PriorityQueue<Proceso> getProcesosListos() {
        PriorityQueue<Proceso> listos = new PriorityQueue<>(this._listos);
        return listos;
    }

    public PriorityQueue<Proceso> getProcesosEnAtencion() {
        PriorityQueue<Proceso> enAt = new PriorityQueue<>(this._enAtencion);
        return enAt;
    }

    public PriorityQueue<Proceso> getProcesosFinalizados() {
        PriorityQueue<Proceso> fin = new PriorityQueue<>(this._finalizados);
        return fin;
    }

    public void sortProcesosCPURest(PriorityQueue<Proceso> p) {
        Comparator<Proceso> restSort = Comparator.comparing(Proceso::getCpuRest);
        PriorityQueue<Proceso> ret = new PriorityQueue<>(restSort);

        boolean flag = true;
        while (flag) {
            Proceso proceso = p.poll();
            if (proceso == null) {
                flag = false;
            } else {
                ret.add(proceso);
            }
        }
        this._listos = ret;
    }

    public void sortProcesosRR (PriorityQueue<Proceso> p){
        Comparator<Proceso> sort = Comparator.comparing(Proceso::getRr).reversed();
        PriorityQueue<Proceso> ret = new PriorityQueue<>(sort);
        boolean flag = true;
        while (flag) {
            Proceso proceso = p.poll();
            if (proceso == null) {
                flag = false;
            } else {
                proceso.setTespera();
                proceso.calcularRR();
                ret.add(proceso);
            }
        }
        this._listos = ret;
    }
    
    public void sortProcesosTurno(PriorityQueue<Proceso> p) {
        Comparator<Proceso> turnoSort = Comparator.comparing(Proceso::getPos);
        PriorityQueue<Proceso> ret = new PriorityQueue<>(turnoSort);

        boolean flag = true;
        while (flag) {
            Proceso proceso = p.poll();
            if (proceso == null) {
                flag = false;
            } else {
                ret.add(proceso);
            }
        }
        this._listos = ret;
    }

    /*Algoritmos*/
    public boolean recibirProceso(int t) {
        Proceso p = this._procesos.poll();
        if (p != null) {
            if (p.getLlegada() == t) {
                this._lista.add(p);
                this._listos.add(p);
                return true;
            } else {
                this._procesos.add(p);
                return false;
            }
        }
        return false;
    }

    public boolean doseguir() {
        return this.seguir;
    }

    public void fcfs(int t) {
        int enAtencion = this._enAtencion.size();
        System.out.println(enAtencion);
        int enEspera = this._listos.size();
        System.out.println(enEspera);
        if (enAtencion <= 0) {
            if (enEspera <= 0) {
                this.terminar();
            } else {
                Proceso p = this._listos.poll();
                p.setCpuRest();
                this._enAtencion.add(p);
            }
        } else {
            int rest = this._enAtencion.peek().getCpuRest();
            if (rest <= 0) {
                Proceso p = this._enAtencion.poll();
                this._finalizados.add(p);
                if (enEspera > 0) {
                    Proceso p2 = this._listos.poll();
                    p2.setCpuRest();
                    this._enAtencion.add(p2);
                } else {
                    this.terminar();
                }
            } else {
                Proceso p = this._enAtencion.poll();
                p.setCpuRest();
                this._enAtencion.add(p);
            }
        }
    }

    public void sjf(int t) {
        this.sortProcesosCPURest(this._listos);
        int enAtencion = this._enAtencion.size();
        int enEspera = this._listos.size();
        if (enAtencion <= 0) {
            if (enEspera <= 0) {
                this.terminar();
            } else {
                Proceso p = this._listos.poll();
                p.setCpuRest();
                this._enAtencion.add(p);
            }
        } else {
            int rest = this._enAtencion.peek().getCpuRest();
            if (rest <= 0) {
                Proceso p = this._enAtencion.poll();
                this._finalizados.add(p);
                if (enEspera <= 0) {
                    this.terminar();
                } else {
                    Proceso p2 = this._listos.poll();
                    p2.setCpuRest();
                    this._enAtencion.add(p2);
                }
            } else {
                if (enEspera > 0) {
                    int restEspera = this._listos.peek().getCpuRest();
                    if (rest > restEspera) {
                        Proceso p = this._enAtencion.poll();
                        Proceso p2 = this._listos.poll();
                        p2.setCpuRest();
                        this._enAtencion.add(p2);
                        this._listos.add(p);
                    } else {
                        Proceso p = this._enAtencion.poll();
                        p.setCpuRest();
                        this._enAtencion.add(p);
                    }
                } else {
                    Proceso p = this._enAtencion.poll();
                    p.setCpuRest();
                    this._enAtencion.add(p);
                }
            }
        }
    }

    public void RoundRobin(int t, int q) {
        this.sortProcesosTurno(this._listos);
        this.quantum = q;
        int enAtencion = this._enAtencion.size();
        int enEspera = this._listos.size();
        if (enAtencion <= 0) {
            if (enEspera <= 0) {
                this.terminar();
            } else {
                Proceso p = this._listos.poll();
                p.setCpuRest();
                this._enAtencion.add(p);
                this.conteo++;
            }
        } else {
            if (conteo >= quantum) {
                conteo = 0;
                if (enEspera <= 0) {
                    Proceso p = this._enAtencion.poll();
                    if (p.getCpuRest() <= 0) {
                        this._finalizados.add(p);
                        this.terminar();
                    } else {
                        p.setCpuRest();
                        this._enAtencion.add(p);
                    }
                } else {
                    Proceso p = this._enAtencion.poll();
                    Proceso p2 = this._listos.poll();
                    p2.setCpuRest();
                    this._enAtencion.add(p2);
                    if (p.getCpuRest() <= 0) {
                        this._finalizados.add(p);
                    } else {
                        p.setPos(this.siguiente);
                        this.siguiente++;
                        this._listos.add(p);
                    }
                    this.conteo++;
                }
            } else {
                Proceso p = this._enAtencion.poll();
                if (p.getCpuRest() <= 0) {
                    this._finalizados.add(p);
                    if (enEspera <= 0){
                        this.terminar();
                    } else {
                        this.conteo = 0;
                        Proceso pro = this._listos.poll();
                        pro.setCpuRest();
                        conteo++; 
                        this._enAtencion.add(pro); 
                    }
                } else {
                    p.setCpuRest();
                    this._enAtencion.add(p);
                    conteo++;
                }
            }
        }

    }
    
    public void HRRN(int t){
        this.sortProcesosRR(this._listos);
        int enAtencion = this._enAtencion.size();
        int enEspera = this._listos.size();
        if (enAtencion <= 0) {
            if (enEspera <= 0) {
                this.terminar();
            } else {
                Proceso p = this._listos.poll();
                p.setCpuRest();
                this._enAtencion.add(p);
            }
        } else {
            int rest = this._enAtencion.peek().getCpuRest();
            if (rest <= 0) {
                Proceso p = this._enAtencion.poll();
                this._finalizados.add(p);
                if (enEspera <= 0) {
                    this.terminar();
                } else {
                    Proceso p2 = this._listos.poll();
                    p2.setCpuRest();
                    this._enAtencion.add(p2);
                }
            } else {
                if (enEspera > 0) {
                    float rr = this._enAtencion.peek().getRr();
                    float rrEspera = this._listos.peek().getRr();
                    if (rr < rrEspera) {
                        Proceso p = this._enAtencion.poll();
                        Proceso p2 = this._listos.poll();
                        p2.setCpuRest();
                        this._enAtencion.add(p2);
                        this._listos.add(p);
                    } else {
                        Proceso p = this._enAtencion.poll();
                        p.setCpuRest();
                        this._enAtencion.add(p);
                    }
                } else {
                    Proceso p = this._enAtencion.poll();
                    p.setCpuRest();
                    this._enAtencion.add(p);
                }
            }
        }
    }

    public void terminar() {
        clear(); 
        this.seguir = false;
    }

    public void empezar() {
        this.seguir = true;
    }

    private PriorityQueue<Proceso> _listos;
    private PriorityQueue<Proceso> _procesos;
    private PriorityQueue<Proceso> _lista;
    private PriorityQueue<Proceso> _enAtencion;
    private PriorityQueue<Proceso> _finalizados;

    private boolean seguir = true;
    private boolean proc = true;

    private int quantum = 4;
    private int conteo = 0;
    private int siguiente = 0;

    private final int LLEGADA = 1;
    private final int PRIORIDAD = 2;
    private final int CPUREST = 3;
    private final int RR = 4;
}
