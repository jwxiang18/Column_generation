import java.util.BitSet;

public class Instance {
    public int numOrders;
    public int vechileNum ;
    public int capacity;
    public Order orders[];

    public BitSet[] canNotVisitBitSet;
    public double[][] distance;
    Instance (int numOrders ){
        this.numOrders = numOrders;
        this.orders = new Order[numOrders+2];
        this.distance = new double[numOrders+2][numOrders+2];
        this.canNotVisitBitSet = new BitSet[numOrders+2];
        for (int i = 0; i < numOrders + 2; i++) {
            canNotVisitBitSet[i] = new BitSet(numOrders+2);
        }
    }
}
