package api.controller;

import api.provider.RecommendationProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/recommendation")
public class RecommendationController {

    /**
     * Contact report personal scope report controller.
     *
     * @param request HttpServletRequest
     * @param form    form data for report filters
     * @return JSON Result
     */
    @GET
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, MultivaluedMap<String, String> form) {
        RecommendationProvider recommendationProvider= new RecommendationProvider();
        JSONObject data = recommendationProvider.getRecommendations(request, form);
        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }


}
