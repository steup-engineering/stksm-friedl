/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.retain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sascha
 */
@XmlRootElement(name = "millAxis")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetainMillAxis implements Serializable {

    private static final long serialVersionUID = -6750150560154553901L;

    @XmlAttribute(name = "toolOffset")
    private double toolOffset;

    public double getToolOffset() {
        return toolOffset;
    }

    public void setToolOffset(double toolOffset) {
        this.toolOffset = toolOffset;
    }
}
