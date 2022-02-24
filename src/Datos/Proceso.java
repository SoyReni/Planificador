/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 *
 * @author Reni
 */
public class Proceso implements Comparable<Proceso> {

    public Proceso(int llegada, String nombre, int prio, int cpuIni) {
        super();
        this._llegada = llegada;
        this._nombre = nombre;
        this._prio = prio;
        this._cpuIni = cpuIni;
        this._cpuRest = cpuIni;
        this._tespera = -1;
        this._rr = (float) 1.0;
        this.pos = 0;
    }

    /**
     * Orden natural, por orden de llegada
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Proceso o) {
        return this.getLlegada() - o.getLlegada();
    }

    public void calcularRR() {
        float rr = 1;
        if (this.getTespera() > 0) {
            float t = (float)this.getTespera();
            float w = (float)this.getCpuIni();
            rr = (float) (w + t) / t;
            this._rr = rr;
        } else {
            this._rr = rr;
        }
    }

    public float getRr() {
        return _rr;
    }

    public void setRr(float rr) {
        this._rr = rr;
    }

    public void setTespera() {
        this._tespera++;
    }

    public int getTespera() {
        return this._tespera;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int nuevo) {
        this.pos = nuevo;
    }

    public int getLlegada() {
        return _llegada;
    }

    public String getNombre() {
        return _nombre;
    }

    public int getPrio() {
        return _prio;
    }

    public int getCpuIni() {
        return _cpuIni;
    }

    public int getCpuRest() {
        return _cpuRest;
    }

    public void setCpuRest() {
        this._cpuRest--;
    }

    private int _llegada;
    private String _nombre;
    private int _prio;
    private int _cpuIni;
    private int _cpuRest;
    private int _tespera;
    private float _rr;
    private int pos;
}
