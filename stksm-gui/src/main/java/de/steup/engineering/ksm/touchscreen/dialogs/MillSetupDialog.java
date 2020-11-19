/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.touchscreen.dialogs;

import de.steup.engineering.ksm.Main;
import de.steup.engineering.ksm.plc.entities.GuiInMain;
import de.steup.engineering.ksm.plc.entities.GuiInMill;
import de.steup.engineering.ksm.plc.entities.GuiInMillAxis;
import de.steup.engineering.ksm.plc.entities.GuiOutMain;
import de.steup.engineering.ksm.plc.entities.GuiOutMill;
import de.steup.engineering.ksm.plc.entities.GuiOutMillAxis;
import de.steup.engineering.ksm.plc.rest.MachineThread;
import de.steup.engineering.ksm.touchscreen.UpdatePanelInterface;
import de.steup.engineering.ksm.touchscreen.util.MachButtonListener;
import de.steup.engineering.ksm.touchscreen.util.MotorData;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author sascha
 */
public class MillSetupDialog extends JDialog implements UpdatePanelInterface {

    private static final int TEXT_FIELD_COLUMNS = 10;

    private final MotorData motorData;
    private final AxisControls yControls;
    private final AxisControls zControls;

    private static class AxisControls {

        final GuiInMillAxis inData;
        final GuiOutMillAxis outData;

        final JLabel nameLabel;
        final JLabel posMachLabel;
        final JLabel posToolLabel;
        final JLabel errLabel;
        final JPanel jogPanel;
        final JTextField destField;

        public AxisControls(Window owner, GuiInMillAxis inData, GuiOutMillAxis outData, String name) {
            this.inData = inData;
            this.outData = outData;

            nameLabel = new JLabel(name);
            posMachLabel = new JLabel();
            posToolLabel = new JLabel();
            errLabel = new JLabel();

            jogPanel = new JPanel();
            GridLayout layout = new GridLayout(0, 2);
            layout.setHgap(4);
            jogPanel.setLayout(layout);

            JButton jogNegBtn = new JButton("-");
            jogNegBtn.addMouseListener(new MachButtonListener() {
                @Override
                protected void stateChanged(GuiInMain guiInData, boolean pressed) {
                    inData.setJogNeg(pressed);
                }
            });
            jogPanel.add(jogNegBtn);

            JButton jogPosBtn = new JButton("+");
            jogPosBtn.addMouseListener(new MachButtonListener() {
                @Override
                protected void stateChanged(GuiInMain guiInData, boolean pressed) {
                    inData.setJogPos(pressed);
                }
            });
            jogPanel.add(jogPosBtn);

            FloatSetter destSetter = new FloatSetter() {
                @Override
                public void setValue(double value) {
                    GuiInMain guiInData = MachineThread.getInstance().getGuiInData();
                    synchronized (guiInData) {
                        inData.setDest(value);
                    }
                }
            };

            destField = new JTextField(TEXT_FIELD_COLUMNS);
            destField.setEditable(false);
            destField.setBackground(Color.WHITE);
            destField.addMouseListener(new FloatMouseListener(owner, "Zielposition " + name, destField, -999.0, 999.0, destSetter));

            // force initial update
            update();
        }

        public void updateOutData() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    GuiOutMain guiOutData = MachineThread.getInstance().getGuiOutData();
                    synchronized (guiOutData) {
                        errLabel.setText(String.format("%d", outData.getErrCode()));
                        posMachLabel.setText(String.format("%.0f", outData.getPosMach()));
                        posToolLabel.setText(String.format("%.1f", outData.getPosTool()));
                    }
                }
            });
        }

        public void update() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    GuiInMain guiInData = MachineThread.getInstance().getGuiInData();
                    synchronized (guiInData) {
                        destField.setText(Double.toString(inData.getDest()));
                    }
                }
            });
        }
    }

    public MillSetupDialog(Window owner, String defaultCaption, GuiInMill inData, GuiOutMill outData) {
        super(owner, defaultCaption, ModalityType.APPLICATION_MODAL);

        ActionListener setupAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                MillSetupDialog.this.setVisible(true);
            }
        };

        this.motorData = new MotorData(defaultCaption, inData, outData, setupAction);

        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        GridLayout layout = new GridLayout(0, 1 + Main.MILL_AXIS_COUNT);
        layout.setHgap(4);
        layout.setVgap(10);
        setLayout(layout);

        GuiInMillAxis[] inAxis = inData.getAxis();
        GuiOutMillAxis[] outAxis = outData.getAxis();

        yControls = new AxisControls(this, inAxis[0], outAxis[0], "Y-Achse");
        zControls = new AxisControls(this, inAxis[1], outAxis[1], "Z-Achse");

        MachineThread.getInstance().addUpdateListener(new Runnable() {

            @Override
            public void run() {
                yControls.updateOutData();
                zControls.updateOutData();
            }
        });

        add(rightAlligned(new JLabel()));
        add(yControls.nameLabel);
        add(zControls.nameLabel);

        add(rightAlligned(new JLabel("Fehler:")));
        add(yControls.errLabel);
        add(zControls.errLabel);

        add(rightAlligned(new JLabel("Maschinenposition:")));
        add(yControls.posMachLabel);
        add(zControls.posMachLabel);

        add(rightAlligned(new JLabel("Werkzeugposition:")));
        add(yControls.posToolLabel);
        add(zControls.posToolLabel);

        add(rightAlligned(new JLabel("Manuell verfahren:")));
        add(yControls.jogPanel);
        add(zControls.jogPanel);

        add(rightAlligned(new JLabel("Zielposition:")));
        add(yControls.destField);
        add(zControls.destField);

        JButton closeButton = new JButton("Schliessen");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        MillSetupDialog.this.setVisible(false);
                    }
                });
            }
        });

        add(new JLabel());
        add(new JLabel());
        add(closeButton);

        closeButton.requestFocus();

        pack();
        setLocationRelativeTo(owner);
    }

    private JLabel rightAlligned(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    public MotorData getMotorData() {
        return motorData;
    }

    @Override
    public void update() {
        yControls.update();
        zControls.update();
    }

}
