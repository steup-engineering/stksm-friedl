/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.touchscreen.retain;

import de.steup.engineering.ksm.plc.retain.RetainMill;
import de.steup.engineering.ksm.plc.retain.RetainMillAxis;
import de.steup.engineering.ksm.touchscreen.dialogs.FloatSetter;
import de.steup.engineering.ksm.touchscreen.dialogs.IntegerSetter;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author sascha
 */
public class MillRetainPanel extends PosOffsetRetainPanel {

    private final static ZReferencePos Z_REFERENCE_TOP = new ZReferencePos(1, "oben");
    private final static ZReferencePos Z_REFERENCE_MID = new ZReferencePos(0, "mitte");
    private final static ZReferencePos Z_REFERENCE_BOT = new ZReferencePos(-1, "unten");

    private final static ZReferencePos[] Z_REFERENCE_LIST = {
        Z_REFERENCE_TOP,
        Z_REFERENCE_MID,
        Z_REFERENCE_BOT
    };

    private static class ZReferencePos {

        private final int reference;
        private final String label;

        public ZReferencePos(int reference, String label) {
            this.reference = reference;
            this.label = label;
        }

        public int getReference() {
            return reference;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public MillRetainPanel(Window owner, String title, final RetainMill retainData) {
        super(owner, title, retainData);

        final RetainMillAxis[] axis = retainData.getAxis();

        IntegerSetter zReferenceSetter = new IntegerSetter() {
            @Override
            public void setValue(int value) {
                retainData.setzReference(value);
            }
        };
        addZReferenceItem(labelConst, textConst, "Materialbezugspunkt Z", retainData.getzReference(), zReferenceSetter);

        FloatSetter toolXOffsetSetter = new FloatSetter() {
            @Override
            public void setValue(double value) {
                axis[0].setToolOffset(value);
            }
        };
        addParamItem(owner, labelConst, textConst, "Werkzeug-Offset Y [mm]", -999.0, 999.0, axis[0].getToolOffset(), toolXOffsetSetter);

        FloatSetter toolYOffsetSetter = new FloatSetter() {
            @Override
            public void setValue(double value) {
                axis[1].setToolOffset(value);
            }
        };
        addParamItem(owner, labelConst, textConst, "Werkzeug-Offset Z [mm]", -999.0, 999.0, axis[1].getToolOffset(), toolYOffsetSetter);

    }

    protected JComboBox addZReferenceItem(GridBagConstraints labelConst, GridBagConstraints textConst, String labelText, int deflt, IntegerSetter setter) {
        JLabel label = new JLabel(labelText + ": ");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        add(label, labelConst);
        labelConst.gridy++;

        final JComboBox comboBox = new JComboBox(Z_REFERENCE_LIST);
        comboBox.setEditable(false);
        comboBox.setBackground(Color.WHITE);

        if (deflt > 0) {
            comboBox.setSelectedItem(Z_REFERENCE_TOP);
        } else if (deflt < 0) {
            comboBox.setSelectedItem(Z_REFERENCE_BOT);
        } else {
            comboBox.setSelectedItem(Z_REFERENCE_MID);
        }

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object item = comboBox.getSelectedItem();
                if (item instanceof ZReferencePos) {
                    setter.setValue(((ZReferencePos) item).getReference());
                }
            }
        });

        add(comboBox, textConst);
        textConst.gridy++;

        return comboBox;
    }

}
