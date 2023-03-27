import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ESPPRC {
    Instance instance;
    Order[] orders;
    double[] duals;
    Deque<Route> routes;  // list of routes / labels for ESPPRC
    List<Route> addRoutes;
    List<List<Route>> candidateRoutes; // list of candidate routes for ESPPRC

    ESPPRC(Instance instance , double[] duals){
        this.instance = instance;
        orders = instance.orders;
        this.duals = duals;

        routes = new LinkedList<>();
        candidateRoutes = new LinkedList<>();
        for(int i = 0 ; i < instance.numOrders+2 ; i++){
            candidateRoutes.add(new LinkedList<>());
        }
    }

    public List<Route> main(List<Route> Allroutes){
        routes.offer(new Route(instance));
        addRoutes = new LinkedList<>();

        while(!routes.isEmpty()){
            searchNextPoint(routes.poll());
        }
        for(int i = 0 ; i < candidateRoutes.get(instance.numOrders+1).size() ; i++){
            if(candidateRoutes.get(instance.numOrders+1).get(i).sigma > -1e-6) continue;
            addRoutes.add(candidateRoutes.get(instance.numOrders+1).get(i));
            Allroutes.add(candidateRoutes.get(instance.numOrders+1).get(i));
        }
        return  addRoutes;
    }

    private void searchNextPoint(Route route){
        int lastNo = route.path.get(route.path.size() -1).NO;
        if(lastNo == instance.numOrders) return;
        for(int i = 1 ; i < instance.numOrders+2 ; i++){
            Route newRoute = route.add(orders[i] ,duals);
            if(newRoute != null && !beDominated(newRoute)){
                routes.add(newRoute);
            }
        }
    }

    private boolean beDominated(Route route){
        List<Route> competitors = candidateRoutes.get(route.lastOrder.NO);
        int i = 0;
        while(i < competitors.size()){
            if(route.sigma >= competitors.get(i).sigma &&
                    route.weight >= competitors.get(i).weight &&
                    route.time >= competitors.get(i).time
//                    && contains(competitors.get(i).hasVisit , route.hasVisit)
            ){
                return true;
            }
            if(route.sigma <= competitors.get(i).sigma &&
                    route.weight <= competitors.get(i).weight &&
                    route.time <= competitors.get(i).time
//                    && contains( route.hasVisit ,competitors.get(i).hasVisit )
            ){
                competitors.remove(i);
            }else{
                i++;
            }
        }
        candidateRoutes.get(route.lastOrder.NO).add(route);
        return false;
    }

    private boolean contains(int[] big , int[] small){
        for (int i = 0; i < big.length; i++) {
            if((big[i]&small[i]) != small[i]){
                return false;
            }
        }
        return true;
    }
}
