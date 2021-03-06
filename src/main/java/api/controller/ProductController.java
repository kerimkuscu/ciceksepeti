package api.controller;

import api.provider.ProductLocationProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

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
