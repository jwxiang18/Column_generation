import gurobi.GRBException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, GRBException {

        Instance instance = ReadData.readData("C101.txt" , 100);
        CG cg = new CG(instance);
        cg.MP();
    }
}