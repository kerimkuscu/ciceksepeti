package scheduled;

import bean.Location;
import repository.InteractionRepository;
import repository.LocationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class InteractionCalculatorSchedular extends BaseSchedular {

    private static final Integer CONTROL_RANGE_IN_SECONDS = 30;
    private static final Integer INTERACTION_BORDER = 3;

    private LocationRepository locationRepository;
    private InteractionRepository interactionRepository;

    public InteractionCalculatorSchedular(){
        super();

        locationRepository = new LocationRepository();
        interactionRepository = new InteractionRepository();

        initialize();
    }

    public void initialize(){
        try{

            Timer tradeListChecker = new Timer();
            tradeListChecker.schedule(new CatchInteractions(), 0, (CONTROL_RANGE_IN_SECONDS * 1000));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startListening(){

        ArrayList<Integer> userList = interactionRepository.getActiveUsers(CONTROL_RANGE_IN_SECONDS);

        for(Integer userId : userList){

            ArrayList<Location> locations = interactionRepository.getLocations(CONTROL_RANGE_IN_SECONDS, userId);
            HashMap<Integer, Integer> closestPoints = new HashMap<>();

            for(Location location : locations){

                ArrayList<Integer> closestProductsId = interactionRepository.getClosestProducts(location.getX(), location.getY(), location.getMarketId());

                for(Integer productId : closestProductsId){

                    if(closestPoints.containsKey(productId)){

                        if((closestPoints.get(productId) + 1) >= INTERACTION_BORDER){
                            interactionRepository.createInteraction(userId,productId);
                            closestPoints.remove(productId);
                            continue;
                        }else {
                            closestPoints.put(productId, (closestPoints.get(productId) + 1));
                        }

                    }else {
                        closestPoints.remove(productId);
                        continue;
                    }

                }

            }

        }

    }

    public class CatchInteractions extends TimerTask {
        @Override
        public void run() {
            startListening();
        }
    }

}
