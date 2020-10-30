/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.touchscreen;

import de.steup.engineering.ksm.Main;
import de.steup.engineering.ksm.plc.rest.MachineThread;
import de.steup.engineering.ksm.plc.entities.GuiInMain;
import de.steup.engineering.ksm.plc.entities.GuiOutMain;
import de.steup.engineering.ksm.touchscreen.dialogs.MillSetupDialog;
import de.steup.engineering.ksm.touchscreen.util.MotorData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author sascha
 */
public class ProcessPanel extends JPanel implements UpdatePanelInterface {

    private static final long serialVersionUID = -7928871190853160563L;

    private final List<UpdatePanelInterface> updatePanels = new ArrayList<>();

    public ProcessPanel(Window owner) {
        super();
        setBorder(BorderFactory.createEtchedBorder());

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        JPanel contentPanel = new JPanel();
        BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
        contentPanel.setLayout(contentLayout);

        GuiInMain guiInData = MachineThread.getInstance().getGuiInData();
        GuiOutMain guiOutData = MachineThread.getInstance().getGuiOutData();

        ParamSetNamePanel paramPanel = new ParamSetNamePanel(owner, "Maschinenparameter");
        updatePanels.add(paramPanel);
        contentPanel.add(paramPanel);

        JPanel facePanel = new JPanel(new GridLayout(0, 2));

        List<MotorData> millsMotors = new ArrayList<>();
        for (int i = 0; i < Main.MILL_COUNT; i++) {
            MillSetupDialog msd = new MillSetupDialog(
                    owner,
                    String.format("Fräse %d", i + 1),
                    guiInData.getMills()[i],
                    guiOutData.getMills()[i]);
            millsMotors.add(msd.getMotorData());
            updatePanels.add(msd);
        }
        MotorPanel mp = new MotorPanel(owner, "Fräsen", millsMotors, true, true);
        updatePanels.add(mp);
        facePanel.add(mp);

        List<MotorData> faceMotors = new ArrayList<>();
        for (int i = 0; i < Main.FACE_COUNT; i++) {
            faceMotors.add(new MotorData(
                    String.format("Fl. %d", i + 1),
                    guiInData.getFaces()[i],
                    guiOutData.getFaces()[i]));
        }
        MotorPanel fp = new MotorPanel(owner, "Köpfe", faceMotors, true, true);
        updatePanels.add(fp);
        facePanel.add(fp);

        contentPanel.add(facePanel);

        JPanel rp = new MotorBasePanel("Rollen", 1);
        contentPanel.add(rp);

        List<MotorData> rollMotorsOdd = new ArrayList<>();
        for (int i = 0; i < (Main.ROLLS_COUNT / 2); i++) {
            rollMotorsOdd.add(new MotorData(
                    String.format("Rolle %d", i + 1),
                    guiInData.getRolls()[i],
                    guiOutData.getRolls()[i]));
        }
        MotorPanel rop = new MotorPanel(owner, null, rollMotorsOdd, true, false);
        updatePanels.add(rop);
        rp.add(rop);

        List<MotorData> rollMotorsEven = new ArrayList<>();
        for (int i = Main.ROLLS_COUNT / 2; i < Main.ROLLS_COUNT; i++) {
            rollMotorsEven.add(new MotorData(
                    String.format("Rolle %d", i + 1),
                    guiInData.getRolls()[i],
                    guiOutData.getRolls()[i]));
        }
        MotorPanel rep = new MotorPanel(owner, null, rollMotorsEven, true, false);
        updatePanels.add(rep);
        rp.add(rep);

        DevPanel dp;

        JPanel uniPanel = new JPanel(new GridLayout(0, 2));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1));

        InfoPanel bp = new InfoPanel(owner, "Prozesswerte");
        updatePanels.add(bp);
        uniPanel.add(bp);

        List<MotorData> uniMotors = new ArrayList<>();
        uniMotors.add(new MotorData("Multifnk", guiInData.getUnidevs()[0], guiOutData.getUnidevs()[0]));
        dp = new DevPanel(owner, "Armierung/Wassernase", uniMotors, null, null, false, guiInData.getUnidevs()[0]);
        updatePanels.add(dp);
        uniPanel.add(dp);

        contentPanel.add(uniPanel);

        JPanel bevelPanel = new JPanel();
        bevelPanel.setLayout(new GridLayout(0, 2));

        List<MotorData> bevelUpperMotors = new ArrayList<>();
        bevelUpperMotors.add(new MotorData("Fräser", guiInData.getBevels()[1].getMotors()[0], guiOutData.getBevels()[1].getMotors()[0]));
        bevelUpperMotors.add(new MotorData("Poli 1", guiInData.getBevels()[1].getMotors()[1], guiOutData.getBevels()[1].getMotors()[1]));
        bevelUpperMotors.add(new MotorData("Poli 2", guiInData.getBevels()[1].getMotors()[2], guiOutData.getBevels()[1].getMotors()[2]));
        dp = new DevPanel(owner, "Fase oben", bevelUpperMotors, null, "Fasenbreite [mm]", true, guiInData.getBevels()[1]);
        updatePanels.add(dp);
        bevelPanel.add(dp);

        List<MotorData> bevelLowerMotors = new ArrayList<>();
        bevelLowerMotors.add(new MotorData("Fräser", guiInData.getBevels()[0].getMotors()[0], guiOutData.getBevels()[0].getMotors()[0]));
        bevelLowerMotors.add(new MotorData("Poli 1", guiInData.getBevels()[0].getMotors()[1], guiOutData.getBevels()[0].getMotors()[1]));
        dp = new DevPanel(owner, "Fase unten", bevelLowerMotors, null, "Fasenbreite [mm]", true, guiInData.getBevels()[0]);
        updatePanels.add(dp);
        bevelPanel.add(dp);

        contentPanel.add(bevelPanel);

        add(contentPanel, BorderLayout.PAGE_START);

        update();
    }

    @Override
    public void update() {
        for (UpdatePanelInterface updatePanel : updatePanels) {
            updatePanel.update();
        }
    }

}
