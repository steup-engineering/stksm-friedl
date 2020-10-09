/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.plc.entities;

import de.steup.engineering.ksm.Main;

/**
 *
 * @author sascha
 */
public class GuiOutBevel {

    private final GuiOutWhmStation motors[] = new GuiOutWhmStation[Main.BEVEL_MOTOR_COUNT];

    public GuiOutBevel() {
        for (int i = 0; i < Main.BEVEL_MOTOR_COUNT; i++) {
            motors[i] = new GuiOutWhmStation();
        }
    }

    public static void update(GuiOutBevel[] dst, GuiOutBevel[] src) {
        if (src == null) {
            return;
        }

        for (int i = 0; i < Math.min(src.length, dst.length); i++) {
            dst[i].update(src[i]);
        }
    }

    public void update(GuiOutBevel src) {
        if (src == null) {
            return;
        }

        GuiOutWhmStation.update(motors, src.motors);
    }

    public GuiOutWhmStation[] getMotors() {
        return motors;
    }
}
