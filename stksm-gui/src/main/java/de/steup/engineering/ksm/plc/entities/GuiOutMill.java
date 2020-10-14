/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.entities;

import de.steup.engineering.ksm.Main;

/**
 *
 * @author sascha
 */
public class GuiOutMill implements GuiOutStationInterface {

    private final GuiOutMillAxis axis[] = new GuiOutMillAxis[Main.MILL_AXIS_COUNT];

    private boolean active;
    private final GuiOutWhm whm;

    public GuiOutMill() {
        whm = new GuiOutWhm();

        for (int i = 0; i < Main.MILL_AXIS_COUNT; i++) {
            axis[i] = new GuiOutMillAxis();
        }
    }

    public static void update(GuiOutMill[] dst, GuiOutMill[] src) {
        if (src == null) {
            return;
        }

        for (int i = 0; i < Math.min(src.length, dst.length); i++) {
            dst[i].update(src[i]);
        }
    }

    public void update(GuiOutMill src) {
        if (src == null) {
            return;
        }

        active = src.active;
        whm.update(src.whm);
        GuiOutMillAxis.update(axis, src.axis);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public GuiOutWhm getWhm() {
        return whm;
    }

    public GuiOutMillAxis[] getAxis() {
        return axis;
    }

}
