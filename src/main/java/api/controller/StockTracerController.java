package api.controller;

import api.provider.ProductLocationProvider;
import api.provider.StockTracerProvider;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public class StockTracerController {

    /**
     * Contact report personal scope report controller.
     *
     * @param request HttpServletRequest
     * @return JSON Result
     */
    @GET
    @Path("/stock")
    @Produces("application/json;charset=utf-8")
    public Response getNewContactReportPersonalList(@Context HttpServletRequest request, @QueryParam("marketId") Integer marketID) {
        JSONObject data = StockTracerProvider.getStockTracerData(marketID);
        return Response.status(data.getInt("status")).entity(data.toString()).build();
    }

}
