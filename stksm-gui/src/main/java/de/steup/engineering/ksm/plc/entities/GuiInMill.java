/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.steup.engineering.ksm.Main;

/**
 *
 * @author sascha
 */
public class GuiInMill implements GuiInStationInterface {

    private final GuiInMillAxis axis[] = new GuiInMillAxis[Main.MILL_AXIS_COUNT];

    private boolean manu;
    private boolean ena;

    @JsonIgnore
    private String caption;

    public GuiInMill() {
        for (int i = 0; i < Main.MILL_AXIS_COUNT; i++) {
            axis[i] = new GuiInMillAxis();
        }
    }

    @Override
    public boolean isEna() {
        return ena;
    }

    @Override
    public void setEna(boolean ena) {
        this.ena = ena;
    }

    @Override
    public boolean isManu() {
        return manu;
    }

    @Override
    public void setManu(boolean manu) {
        this.manu = manu;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public boolean isCaptionEditable() {
        return true;
    }

    public GuiInMillAxis[] getAxis() {
        return axis;
    }

}
