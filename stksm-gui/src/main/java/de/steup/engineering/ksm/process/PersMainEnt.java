/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.steup.engineering.ksm.process;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sascha
 */
@XmlRootElement(name = "process")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersMainEnt implements Serializable {

    private static final long serialVersionUID = -6939613832654309063L;

    @XmlAttribute(name = "matHeight")
    private double matHeight;
    @XmlAttribute(name = "beltFeed")
    private double beltFeed;
    @XmlElement(name = "face")
    private ArrayList<PersMotorEnt> faces;
    @XmlElement(name = "mill")
    private ArrayList<PersMotorEnt> mills;
    @XmlElement(name = "unidev")
    private ArrayList<PersUnidevEnt> unidevs;
    @XmlElement(name = "bevel")
    private ArrayList<PersBevelEnt> bevels;
    @XmlAttribute(name = "paramSetName")
    private String paramSetName;

    public double getMatHeight() {
        return matHeight;
    }

    public void setMatHeight(double matHeight) {
        this.matHeight = matHeight;
    }

    public double getBeltFeed() {
        return beltFeed;
    }

    public void setBeltFeed(double beltFeed) {
        this.beltFeed = beltFeed;
    }

    public ArrayList<PersBevelEnt> getBevels() {
        return bevels;
    }

    public void setBevels(ArrayList<PersBevelEnt> bevels) {
        this.bevels = bevels;
    }

    public ArrayList<PersMotorEnt> getMills() {
        return mills;
    }

    public void setMills(ArrayList<PersMotorEnt> mills) {
        this.mills = mills;
    }

    public ArrayList<PersMotorEnt> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<PersMotorEnt> faces) {
        this.faces = faces;
    }

    public ArrayList<PersUnidevEnt> getUnidevs() {
        return unidevs;
    }

    public void setUnidevs(ArrayList<PersUnidevEnt> unidevs) {
        this.unidevs = unidevs;
    }

    public String getParamSetName() {
        return paramSetName;
    }

    public void setParamSetName(String paramSetName) {
        this.paramSetName = paramSetName;
    }

}
