package org.gbl.flight_admin.in.http.controller;

import org.gbl.flight_admin.FlightAdminApi;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AdminController {

    @Autowired
    protected FlightAdminApi flightAdminApi;
}
