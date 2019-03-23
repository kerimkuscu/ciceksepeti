package api.controller;

import api.provider.LocationProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

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
    @GET
    @Path("/location")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, @QueryParam("x") int x, @QueryParam("y") int y, @QueryParam("marketId") int marketId,
                                                    @QueryParam("userId") int userId) {
        JSONObject data = LocationProvider.saveLocation(request, x, y, marketId, userId);

        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }


}
