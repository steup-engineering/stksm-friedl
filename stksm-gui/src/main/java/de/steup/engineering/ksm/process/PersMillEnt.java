/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sascha
 */
@XmlRootElement(name = "mill")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersMillEnt extends PersMotorEnt {

    private static final long serialVersionUID = -7420784315838580219L;

    @XmlAttribute(name = "destY")
    private double destY;

    @XmlAttribute(name = "destZ")
    private double destZ;

    public double getDestY() {
        return destY;
    }

    public void setDestY(double destY) {
        this.destY = destY;
    }

    public double getDestZ() {
        return destZ;
    }

    public void setDestZ(double destZ) {
        this.destZ = destZ;
    }
}
