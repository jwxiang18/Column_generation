public class Instance {
    public int numOrders;
    public int vechileNum ;
    public int capacity;
    public Order orders[];

    public double[][] distance;
    Instance (int numOrders ){
        this.numOrders = numOrders;
        this.orders = new Order[numOrders+2];
        this.distance = new double[numOrders+2][numOrders+2];
    }
}
