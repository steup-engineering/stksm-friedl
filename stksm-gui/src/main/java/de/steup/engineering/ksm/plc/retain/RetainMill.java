/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.retain;

import de.steup.engineering.ksm.Main;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sascha
 */
@XmlRootElement(name = "mill")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetainMill extends RetainFace {

    private static final long serialVersionUID = -6750150560154553901L;

    @XmlAttribute(name = "zReference")
    private int zReference;

    @XmlElement(name = "axis")
    private final RetainMillAxis axis[] = new RetainMillAxis[Main.MILL_AXIS_COUNT];

    public RetainMill() {
        for (int i = 0; i < Main.MILL_AXIS_COUNT; i++) {
            axis[i] = new RetainMillAxis();
        }
    }

    public int getzReference() {
        return zReference;
    }

    public void setzReference(int zReference) {
        this.zReference = zReference;
    }

    public RetainMillAxis[] getAxis() {
        return axis;
    }

}
