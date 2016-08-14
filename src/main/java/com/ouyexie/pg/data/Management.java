package com.ouyexie.pg.data;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Management implements Serializable {
    private String manager;
    private String managerAddition;
    private String managee;
    private String identification;

    public Management(String manager, String managee, String identification) {
        this.manager = manager;
        this.managee = managee;
        this.identification = identification;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Management)) {
            return false;
        }
        Management p = (Management) o;
        return Objects.equals(p.manager, manager) && Objects.equals(p.managee, managee) && Objects.equals(p.identification, identification);
    }

    @Override
    public int hashCode() {
        return 41 ^ 3 * (manager == null ? 0 : manager.hashCode()) + 41 ^ 2
                * (managee == null ? 0 : managee.hashCode()) + 41
                * (identification == null ? 0 : identification.hashCode());
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagee() {
        return managee;
    }

    public void setManagee(String managee) {
        this.managee = managee;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getManagerAddition() {
        return managerAddition;
    }

    public void setManagerAddition(String managerAddition) {
        this.managerAddition = managerAddition;
    }
}
