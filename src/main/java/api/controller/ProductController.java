package api.controller;

import api.provider.LocationProvider;
import api.provider.ProductLocationProvider;
import bean.ProductLocation;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/")
public class ProductController {

    /**
     * Contact report personal scope report controller.
     *
     * @param request HttpServletRequest
         * @return JSON Result
     */
    @GET
    @Path("/product")
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, @QueryParam("marketId") Integer marketID) {
        JSONObject data = ProductLocationProvider.getProductLocationList(marketID);
        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }


}
