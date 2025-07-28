package org.gbl.admin.in.http.controller;

import org.gbl.admin.FlightAdminApi;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AdminController {

    @Autowired
    protected FlightAdminApi flightAdminApi;
}
