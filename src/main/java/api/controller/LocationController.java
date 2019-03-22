package api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/")
public class LocationController {

    /**
     * Contact report personal scope report controller.
     *
     * @param request HttpServletRequest
     * @param form    form data for report filters
     * @return JSON Result
     */
    @POST
    @Path("/location")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces("application/json;charset=utf-8")
    public String getNewContactReportPersonalList(@Context HttpServletRequest request, MultivaluedMap<String, String> form) {
        System.out.println(form.toString() + "\n" + form.get("marketId").toString());

        return "selam";
    }


}
