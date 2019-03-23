package api.controller;

import api.provider.LocationProvider;
import api.provider.UserProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserController {

    /**
     * Contact report personal scope report controller.
     *
     * @param request HttpServletRequest
     * @return JSON Result
     */
    @GET
    @Path("/list")
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, @QueryParam("userId") int userId) {
        JSONObject data = UserProvider.getUserInfo(request, userId);

        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }

}
