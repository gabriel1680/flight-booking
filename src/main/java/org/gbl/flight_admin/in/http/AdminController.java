package org.gbl.flight_admin.in.http;

import org.gbl.flight_admin.FlightAdminApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/flights")
public abstract class AdminController {

    @Autowired
    protected FlightAdminApi flightAdminApi;
}
