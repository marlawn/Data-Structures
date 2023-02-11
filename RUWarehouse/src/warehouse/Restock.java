package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        Warehouse house = new Warehouse();
        int numQ = StdIn.readInt();
        for (int i = 0; i < numQ; i++) {
            String instruction = StdIn.readString();
            if (instruction.equals("add")) {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                house.addProduct(id, name, stock, day, demand);
            }
            if (instruction.equals("restock")) {
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                house.restockProduct(id, amount);
            }
        }

        StdOut.println(house);
    }
}
