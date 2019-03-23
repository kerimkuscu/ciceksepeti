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
     * @return JSON Result
     */
    @GET
    @Path("/")
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, @QueryParam("userId") int userId) {
        RecommendationProvider recommendationProvider= new RecommendationProvider();
        JSONObject data = recommendationProvider.getRecommendations(request, userId);
        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }


}
