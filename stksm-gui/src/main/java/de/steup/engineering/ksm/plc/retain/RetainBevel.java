/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.retain;

import de.steup.engineering.ksm.Main;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sascha
 */
@XmlRootElement(name = "bevel")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetainBevel implements Serializable {

    private static final long serialVersionUID = -1511750761293701439L;

    @XmlAttribute(name = "widthOffset")
    private double widthOffset;
    @XmlElement(name = "motors")
    private final RetainFace motors[] = new RetainFace[Main.BEVEL_MOTOR_COUNT];

    public RetainBevel() {
        for (int i = 0; i < Main.BEVEL_MOTOR_COUNT; i++) {
            motors[i] = new RetainFace();
        }
    }

    public static void update(RetainBevel[] dst, RetainBevel[] src) {
        if (src == null) {
            return;
        }

        for (int i = 0; i < Math.min(src.length, dst.length); i++) {
            dst[i].update(src[i]);
        }
    }

    public void update(RetainBevel src) {
        if (src == null) {
            return;
        }

        widthOffset = src.widthOffset;

        RetainFace.update(motors, src.motors);
    }

    public double getWidthOffset() {
        return widthOffset;
    }

    public void setWidthOffset(double widthOffset) {
        this.widthOffset = widthOffset;
    }

    public RetainFace[] getMotors() {
        return motors;
    }
}
