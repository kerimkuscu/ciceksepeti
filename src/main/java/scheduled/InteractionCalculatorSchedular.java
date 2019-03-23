package scheduled;

import dto.Location;
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

            System.out.println("Scheduling Interaction catcher ...");
            Timer tradeListChecker = new Timer();
            tradeListChecker.schedule(new CatchInteractions(), 0, (CONTROL_RANGE_IN_SECONDS * 1000));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startCatchingInteractions(){

        System.out.println("Catching interactions ...");
        ArrayList<Integer> userList = interactionRepository.getActiveUsers(CONTROL_RANGE_IN_SECONDS);
        String now = interactionRepository.getNow();

        long start = System.currentTimeMillis();
        for(Integer userId : userList){

            ArrayList<Location> locations = interactionRepository.getLocations(CONTROL_RANGE_IN_SECONDS, userId, now);
            HashMap<Integer, Integer> closestPoints = new HashMap<>();

            for(Location location : locations){

                ArrayList<Integer> closestProductsId = interactionRepository.getClosestProducts(location.getX(), location.getY(), location.getMarketId());
                closestPoints = removeDifferences(closestProductsId, closestPoints);
                for(Integer productId : closestProductsId){

                    if(closestPoints.containsKey(productId)){
                        if((closestPoints.get(productId) + 1) >= INTERACTION_BORDER){
                            System.out.println("Creating interaction: " + userId + " - " + productId);
                            interactionRepository.createInteraction(userId,productId,location.getTimeStamp());
                            closestPoints.remove(productId);
                            continue;
                        }else {
                            closestPoints.put(productId, (closestPoints.get(productId) + 1));
                        }

                    }else {
                        closestPoints.put(productId, 1);
                    }

                }

            }

        }

        System.out.println("Total time to catch interactions: " + (System.currentTimeMillis() - start));

    }

    public HashMap<Integer, Integer> removeDifferences(ArrayList<Integer> productIdList, HashMap<Integer, Integer> map){
        HashMap<Integer, Integer> newMap = new HashMap<>();

        for(Integer productId : productIdList){
            if(map.containsKey(productId)){
                newMap.put(productId, map.get(productId));
            }
        }

        return newMap;
    }

    public class CatchInteractions extends TimerTask {
        @Override
        public void run() {
            startCatchingInteractions();
        }
    }

}
