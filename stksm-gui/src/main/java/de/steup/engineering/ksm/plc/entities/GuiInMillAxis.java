/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.entities;

/**
 *
 * @author sascha
 */
public class GuiInMillAxis {

    private boolean jogPos;
    private boolean jogNeg;
    private double dest;

    public boolean isJogPos() {
        return jogPos;
    }

    public void setJogPos(boolean jogPos) {
        this.jogPos = jogPos;
    }

    public boolean isJogNeg() {
        return jogNeg;
    }

    public void setJogNeg(boolean jogNeg) {
        this.jogNeg = jogNeg;
    }

    public double getDest() {
        return dest;
    }

    public void setDest(double dest) {
        this.dest = dest;
    }

}
