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
public class GuiOutMillAxis {

    private double posMach;
    private double posTool;
    private int errCode;
    private double destJogInc;

    public static void update(GuiOutMillAxis[] dst, GuiOutMillAxis[] src) {
        if (src == null) {
            return;
        }

        for (int i = 0; i < Math.min(src.length, dst.length); i++) {
            dst[i].update(src[i]);
        }
    }

    public void update(GuiOutMillAxis src) {
        if (src == null) {
            return;
        }

        posMach = src.posMach;
        posTool = src.posTool;
        errCode = src.errCode;
        destJogInc = src.destJogInc;
    }

    public double getPosMach() {
        return posMach;
    }

    public void setPosMach(double posMach) {
        this.posMach = posMach;
    }

    public double getPosTool() {
        return posTool;
    }

    public void setPosTool(double posTool) {
        this.posTool = posTool;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public double getDestJogInc() {
        return destJogInc;
    }

}
