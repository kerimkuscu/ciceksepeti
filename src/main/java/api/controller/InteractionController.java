package api.controller;

import api.provider.InteractionProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/interaction")
public class InteractionController {

    /**
     * Contact report personal scope report controller.
     *
     * @param request HttpServletRequest
     * @return JSON Result
     */
    @GET
    @Path("/")
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, @QueryParam("userId") int userId) {
        JSONObject data = InteractionProvider.getInteractions(request, userId);
        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }


}
