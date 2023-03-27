import java.util.ArrayList;
import java.util.List;

public class Route {
    public Instance instance;
    public Order lastOrder;
    public List<Order> path;
    public int[] hasVisit;
    public double  distance , time , sigma ,dual ;
    public double weight ;

    public Route(Instance instance){
        this.instance = instance;
        path = new ArrayList<>();
        path.add(instance.orders[0]);
        hasVisit = new int[(int)Math.ceil(instance.orders.length/32.0)];
        hasVisit[0] += 1;
        lastOrder = instance.orders[0];
        weight = 0;
        sigma = 0;
        dual = 0;
        time = instance.orders[0].readyTime + instance.orders[0].serviceTime;

    }
    public void initAdd(Order order){
        weight += order.demand;
        hasVisit[order.NO/32] += 1<<(order.NO%32);

        path.add(order);
        path.add(instance.orders[instance.orders.length-1]);
        distance = instance.distance[0][order.NO] + instance.distance[order.NO][instance.orders.length -1];
        time += distance ;

    }

    public Route add(Order order , double []duals){
        if((hasVisit[order.NO/32] & (1<<(order.NO%32))) != 0) return null; // 如果已经访问了，这返回空
        if(weight + order.demand > instance.capacity) return null;
        double newTime = Math.max(time + instance.distance[lastOrder.NO][order.NO] , order.readyTime) ;
        if(newTime <= order.dueDate){
            Route newRoute = new Route(instance);
            newRoute.path = new ArrayList<>(path);
            newRoute.path.add(order);
            newRoute.hasVisit = hasVisit.clone();
            newRoute.hasVisit[order.NO/32] += 1<<(order.NO%32);
            newRoute.weight = weight + order.demand;
            newRoute.lastOrder = order;
            newRoute.time = newTime + order.serviceTime;
            newRoute.distance = distance + instance.distance[lastOrder.NO][order.NO];
            newRoute.dual = dual + duals[order.NO];
            newRoute.sigma = newRoute.distance - newRoute.dual;
            return newRoute;
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < path.size(); i++) {
            sb.append(path.get(i).NO);
            sb.append(" ");
        }
        return sb.toString();
    }
}
