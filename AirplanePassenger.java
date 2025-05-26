class AirplanePassenger {
    int row;    //Passenger's assigned row
    char position;  // Seat Position
    int slowness;   // Slowness factor ( 1,2,3,4)
    int ordernumber;    //Unique arrival number

    //Constructor that initialises the variables
    public AirplanePassenger(int row, char position, int slowness, int order) {
        this.row = row;
        this.position = position;
        this.slowness = slowness;
        this.ordernumber = order;
    }

    public String toString() {  // Method will return row, seat position and slowness in string format
        return String.format("(%d, %c, %d)", row, position, slowness);
    }
}