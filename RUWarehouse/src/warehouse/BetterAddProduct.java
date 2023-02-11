package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        Warehouse house = new Warehouse();
        int length =  StdIn.readInt();
        for (int i = 0; i < length; i++) {
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            house.betterAddProduct(id, name, stock, day, demand);
        }

        StdOut.println(house);
    }
}
