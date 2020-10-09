/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm;

import de.steup.engineering.ksm.plc.rest.PlcRestException;
import de.steup.engineering.ksm.plc.rest.MachineThread;
import de.steup.engineering.ksm.touchscreen.MainPanel;
import de.steup.engineering.ksm.touchscreen.dialogs.files.PathConfig;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author sascha
 */
public class Main {

    public static final int FACE_COUNT = 4;
    public static final int MILL_COUNT = 4;
    public static final int ROLLS_COUNT = 25;
    public static final int UNIDEV_COUNT = 1;
    public static final int BEVEL_COUNT = 2;
    public static final int BEVEL_MOTOR_COUNT = 3;

    public static final boolean RIGHT_TO_LEFT = true;

    private static JFrame mainFrame;
    private static PathConfig processPath;
    private static PathConfig paramPath;

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static PathConfig getProcessPath() {
        return processPath;
    }

    public static PathConfig getParamPath() {
        return paramPath;
    }

    /**
     * @param args the command line arguments
     * @throws de.steup.engineering.ksm.plc.rest.PlcRestException
     */
    public static void main(String[] args) throws PlcRestException {

        if (args.length != 3) {
            System.err.println("Usage: ksm <REST service base URL> <Process Directory> <ParamDirectory>");
            System.exit(1);
        }

        final String restBaseUrl = args[0];
        processPath = new PathConfig("Prozess", args[1]);
        paramPath = new PathConfig("Parameter", args[2]);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                MachineThread machineThread = MachineThread.getInstance();
                machineThread.setRestBaseUrl(restBaseUrl);

                JFrame mainFrame = new JFrame("STKSM 10-6B/1+3");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setUndecorated(true);

                MainPanel mp = new MainPanel();

                mainFrame.add(mp);

                mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                mainFrame.setVisible(true);

                machineThread.start();

            }
        });
    }
}
