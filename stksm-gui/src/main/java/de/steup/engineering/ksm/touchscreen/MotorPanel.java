/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.touchscreen;

import de.steup.engineering.ksm.Main;
import de.steup.engineering.ksm.plc.rest.MachineThread;
import de.steup.engineering.ksm.plc.entities.GuiInMain;
import de.steup.engineering.ksm.plc.entities.GuiInStationInterface;
import de.steup.engineering.ksm.plc.entities.GuiOutStationInterface;
import de.steup.engineering.ksm.touchscreen.dialogs.StringMouseListener;
import de.steup.engineering.ksm.touchscreen.dialogs.StringSetter;
import de.steup.engineering.ksm.touchscreen.util.MachButtonListener;
import de.steup.engineering.ksm.touchscreen.util.MotorData;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author sascha
 */
public class MotorPanel extends MotorBasePanel implements UpdatePanelInterface {

    private static final long serialVersionUID = 5400429730103122718L;

    private final List<JTextField> captionText = new ArrayList<>();
    private final List<JButton> enaButton = new ArrayList<>();
    private final List<MotorData> motors;
    private Color buttonDefaultColor = null;

    public MotorPanel(Window owner, String title, List<MotorData> motors, boolean hasLabel, boolean hasEnable) {
        super(title, motors.size());

        motors = new ArrayList<>(motors);
        if (Main.RIGHT_TO_LEFT) {
            Collections.reverse(motors);
        }
        this.motors = motors;

        if (hasLabel) {
            for (int i = 0; i < motors.size(); i++) {
                final MotorData motor = motors.get(i);
                final JTextField nt = new JTextField();
                nt.setEditable(false);
                nt.setHorizontalAlignment(JTextField.CENTER);
                nt.setText(motor.getEffCaption());
                nt.setBackground(Color.LIGHT_GRAY);
                if (motor.isCaptionEditable()) {
                    StringSetter setter = new StringSetter() {

                        @Override
                        public void setValue(String value) {
                            motor.setCaption(value);
                        }
                    };
                    nt.addMouseListener(new StringMouseListener(owner, "Motor Name", nt, 1, 16, setter));
                }
                captionText.add(nt);
                add(nt);
            }
        }

        if (hasEnable) {
            for (int i = 0; i < motors.size(); i++) {
                final MotorData motor = motors.get(i);

                final JButton nb = new JButton("aktiv");
                if (buttonDefaultColor == null) {
                    buttonDefaultColor = nb.getBackground();
                }
                enaButton.add(nb);
                add(nb);

                final GuiInStationInterface stationIn = motor.getInData();
                nb.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        synchronized (MachineThread.getInstance().getGuiInData()) {
                            stationIn.setEna(!stationIn.isEna());
                            if (stationIn.isEna()) {
                                nb.setBackground(Color.green);
                            } else {
                                nb.setBackground(buttonDefaultColor);
                            }
                        }
                    }
                });
            }
        }

        boolean hasSetupAction = false;
        for (int i = 0; i < motors.size(); i++) {
            final MotorData motor = motors.get(i);

            if (motor.getSetupAction() != null) {
                hasSetupAction = true;
            }

            final JButton nb = new JButton("manu");
            final Color defaultColor = nb.getBackground();
            add(nb);

            final GuiInStationInterface stationIn = motor.getInData();
            final GuiOutStationInterface stationOut = motor.getOutData();
            nb.addMouseListener(new MachButtonListener() {
                @Override
                protected void stateChanged(GuiInMain guiInData, boolean pressed) {
                    stationIn.setManu(pressed);
                }
            });

            MachineThread.getInstance().addUpdateListener(new Runnable() {

                @Override
                public void run() {
                    final boolean state = stationOut.isActive();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            if (state) {
                                nb.setBackground(Color.red);
                            } else {
                                nb.setBackground(defaultColor);
                            }
                        }
                    });

                }
            });
        }

        if (hasSetupAction) {
            for (int i = 0; i < motors.size(); i++) {
                final MotorData motor = motors.get(i);

                JButton setupButton = new JButton("Einrichten ...");
                setupButton.addActionListener(motor.getSetupAction());
                add(setupButton);
            }
        }

    }

    @Override
    public void update() {
        GuiInMain guiInData = MachineThread.getInstance().getGuiInData();
        synchronized (guiInData) {
            for (int i = 0; i < motors.size(); i++) {
                MotorData motor = motors.get(i);

                if (!captionText.isEmpty()) {
                    captionText.get(i).setText(motor.getEffCaption());
                }

                if (!enaButton.isEmpty()) {
                    JButton nb = enaButton.get(i);
                    if (motor.getInData().isEna()) {
                        nb.setBackground(Color.green);
                    } else {
                        nb.setBackground(buttonDefaultColor);
                    }
                }
            }
        }
    }
}
