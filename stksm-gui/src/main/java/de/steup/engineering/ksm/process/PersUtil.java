/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.process;

import de.steup.engineering.ksm.plc.entities.GuiInBevel;
import de.steup.engineering.ksm.plc.entities.GuiInMain;
import de.steup.engineering.ksm.plc.entities.GuiInMill;
import de.steup.engineering.ksm.plc.entities.GuiInMillAxis;
import de.steup.engineering.ksm.plc.entities.GuiInStationInterface;
import de.steup.engineering.ksm.plc.entities.GuiInUnidev;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author sascha
 */
public class PersUtil {

    private final Log logger
            = LogFactory.getLog(PersUtil.class.toString());

    public PersMainEnt loadProcess(Window owner, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersMainEnt.class);
            Unmarshaller u = context.createUnmarshaller();
            return (PersMainEnt) u.unmarshal(file);
        } catch (Exception ex) {
            logger.error("Fehler beim laden der Prozessdaten", ex);
            JOptionPane.showMessageDialog(owner,
                    String.format("Datei %s kann nicht gelesen werden.", file.getName()),
                    "Fehler beim Laden",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void saveProcess(Window owner, PersMainEnt process, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersMainEnt.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(process, file);
        } catch (Exception ex) {
            logger.error("Fehler beim speichern der Prozessdaten", ex);
            JOptionPane.showMessageDialog(owner,
                    String.format("Datei %s kann nicht geschrieben werden.", file.getName()),
                    "Fehler beim Speichern",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<PersMotorEnt> motorsPlcToPers(GuiInStationInterface plcMotors[]) {
        ArrayList<PersMotorEnt> motors = new ArrayList<>();
        for (int i = 0; i < plcMotors.length; i++) {
            GuiInStationInterface plcMotor = plcMotors[i];
            PersMotorEnt motor = new PersMotorEnt();
            motor.setIndex(i);
            motor.setEna(plcMotor.isEna());
            motor.setCaption(plcMotor.getCaption());
            motors.add(motor);
        }
        return motors;
    }

    private void motorsPersToPlc(ArrayList<PersMotorEnt> motors, GuiInStationInterface plcMotors[]) {
        if (motors == null) {
            return;
        }
        for (PersMotorEnt motor : motors) {
            int i = motor.getIndex();
            if (i >= 0 && i < plcMotors.length) {
                GuiInStationInterface plcMotor = plcMotors[i];
                plcMotor.setEna(motor.isEna());
                plcMotor.setCaption(motor.getCaption());
            }
        }
    }

    private ArrayList<PersMillEnt> millsPlcToPers(GuiInMill plcMills[]) {
        ArrayList<PersMillEnt> mills = new ArrayList<>();
        for (int i = 0; i < plcMills.length; i++) {
            GuiInMill plcMill = plcMills[i];
            GuiInMillAxis[] plcMillAxis = plcMill.getAxis();
            PersMillEnt mill = new PersMillEnt();
            mill.setIndex(i);
            mill.setEna(plcMill.isEna());
            mill.setCaption(plcMill.getCaption());
            mill.setDestY(plcMillAxis[0].getDest());
            mill.setDestZ(plcMillAxis[1].getDest());
            mills.add(mill);
        }
        return mills;
    }

    private void millsPersToPlc(ArrayList<PersMillEnt> mills, GuiInMill plcMills[]) {
        if (mills == null) {
            return;
        }
        for (PersMillEnt mill : mills) {
            int i = mill.getIndex();
            if (i >= 0 && i < plcMills.length) {
                GuiInMill plcMill = plcMills[i];
                GuiInMillAxis[] plcMillAxis = plcMill.getAxis();
                plcMill.setEna(mill.isEna());
                plcMill.setCaption(mill.getCaption());
                plcMillAxis[0].setDest(mill.getDestY());
                plcMillAxis[1].setDest(mill.getDestZ());
            }
        }
    }

    private ArrayList<PersUnidevEnt> unidevsPlcToPers(GuiInUnidev plcUnidevs[]) {
        ArrayList<PersUnidevEnt> unidevs = new ArrayList<>();
        for (int i = 0; i < plcUnidevs.length; i++) {
            GuiInUnidev plcUnidev = plcUnidevs[i];
            PersUnidevEnt unidev = new PersUnidevEnt();
            unidev.setIndex(i);
            unidev.setEna(plcUnidev.isEna());
            unidev.setMarginStart(plcUnidev.getMarginStart());
            unidev.setMarginEnd(plcUnidev.getMarginEnd());
            unidevs.add(unidev);
        }
        return unidevs;
    }

    private void unidevsPersToPlc(ArrayList<PersUnidevEnt> unidevs, GuiInUnidev plcUnidevs[]) {
        if (unidevs == null) {
            return;
        }
        for (PersUnidevEnt unidev : unidevs) {
            int i = unidev.getIndex();
            if (i >= 0 && i < plcUnidevs.length) {
                GuiInUnidev plcUnidev = plcUnidevs[i];
                plcUnidev.setEna(unidev.isEna());
                plcUnidev.setMarginStart(unidev.getMarginStart());
                plcUnidev.setMarginEnd(unidev.getMarginEnd());
            }
        }
    }

    private ArrayList<PersBevelEnt> bevelsPlcToPers(GuiInBevel plcBevels[]) {
        ArrayList<PersBevelEnt> bevels = new ArrayList<>();
        for (int i = 0; i < plcBevels.length; i++) {
            GuiInBevel plcBevel = plcBevels[i];
            PersBevelEnt bevel = new PersBevelEnt();
            bevel.setIndex(i);
            bevel.setWidth(plcBevel.getWidth());
            bevel.setMarginStart(plcBevel.getMarginStart());
            bevel.setMarginEnd(plcBevel.getMarginEnd());
            bevel.setMotors(motorsPlcToPers(plcBevel.getMotors()));
            bevels.add(bevel);
        }
        return bevels;
    }

    private void bevelsPersToPlc(ArrayList<PersBevelEnt> bevels, GuiInBevel plcBevels[]) {
        if (bevels == null) {
            return;
        }
        for (PersBevelEnt bevel : bevels) {
            int i = bevel.getIndex();
            if (i >= 0 && i < plcBevels.length) {
                GuiInBevel plcBevel = plcBevels[i];
                plcBevel.setWidth(bevel.getWidth());
                plcBevel.setMarginStart(bevel.getMarginStart());
                plcBevel.setMarginEnd(bevel.getMarginEnd());
                motorsPersToPlc(bevel.getMotors(), plcBevel.getMotors());
            }
        }
    }

    public void loadProcess(Window owner, GuiInMain plcData, File file) {
        PersMainEnt process = loadProcess(owner, file);
        if (process == null) {
            return;
        }

        plcData.setMatHeight(process.getMatHeight());
        plcData.setBeltFeed(process.getBeltFeed());
        motorsPersToPlc(process.getFaces(), plcData.getFaces());
        millsPersToPlc(process.getMills(), plcData.getMills());
        unidevsPersToPlc(process.getUnidevs(), plcData.getUnidevs());
        bevelsPersToPlc(process.getBevels(), plcData.getBevels());
        plcData.setParamSetName(process.getParamSetName());
    }

    public void saveProcess(Window owner, GuiInMain plcData, File file) {
        PersMainEnt process = new PersMainEnt();
        process.setMatHeight(plcData.getMatHeight());
        process.setBeltFeed(plcData.getBeltFeed());
        process.setFaces(motorsPlcToPers(plcData.getFaces()));
        process.setMills(millsPlcToPers(plcData.getMills()));
        process.setUnidevs(unidevsPlcToPers(plcData.getUnidevs()));
        process.setBevels(bevelsPlcToPers(plcData.getBevels()));
        process.setParamSetName(plcData.getParamSetName());
        saveProcess(owner, process, file);
    }
}
