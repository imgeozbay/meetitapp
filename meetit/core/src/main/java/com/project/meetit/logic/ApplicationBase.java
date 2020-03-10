package com.project.meetit.logic;

import com.project.meetit.dboperations.model.User;

public class ApplicationBase {

    private static ApplicationBase applicationBase;

    private User currentUser;

    private ApplicationBase()
    {

    }

    public static ApplicationBase getInstance()
    {
        if (applicationBase == null)
        {
            applicationBase = new ApplicationBase();
        }
        return applicationBase;
    }

    public static ApplicationBase getApplicationBase() {
        return applicationBase;
    }

    public static void setApplicationBase(ApplicationBase applicationBase) {
        ApplicationBase.applicationBase = applicationBase;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
