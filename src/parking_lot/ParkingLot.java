package parking_lot;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class ParkingLot {
    List<ParkingFloor> parkingFloors;
    List<Entrance> entrances;
    List<Exit> exits;

    Address address;
    String parkingLotName;

    public abstract boolean isParkingSpaceAvailableForVehicle(Vehicle vehicle);
    public abstract boolean updateParkingAttendant(ParkingAttendant parkingAttendant, int gateId);
}

class ParkingFloor {

    int levelId;
    boolean isFull;
    List<ParkingSpace> parkingSpaces;

    ParkingDisplayBoard parkingDisplayBoard;
}

class Gate{
    int gateId;

    ParkingAttendant parkingAttendant;
}

abstract class Entrance extends Gate{
    public abstract ParkingTicket getParkingTicket(Vehicle vehicle);
}

abstract class Exit extends Gate{
    public abstract ParkingTicket payForParking(ParkingTicket parkingTicket, PaymentType paymentType);
}

class Address{
    String country;
    String state;
    String city;
    String street;
    String pinCode; //ZipCode
}

class ParkingSpace{
    int spaceId;
    boolean isFree;
    double costPerHour;
    Vehicle vehicle;
    ParkingSpaceType parkingSpaceType;
}

abstract class ParkingDisplayBoard{
    Map<ParkingSpaceType, Integer> freeSpotsAvailableMap;

    public abstract void updateFreeSpotsAvailable(ParkingSpaceType parkingSpaceType, int spaces);
}

class Account{
    String name;
    String email;
    String password;
    String empId;
    Address address;
}

abstract class Admin extends Account{
    public abstract boolean addParkingFloor(ParkingLot parkingLot, ParkingFloor parkingFloor);
    public abstract boolean addParkingSpace(ParkingFloor parkingFloor, ParkingSpace parkingSpace);
    public abstract boolean addParkingDisplayBoard(ParkingFloor parkingFloor, ParkingDisplayBoard parkingDisplayBoard);
    /*
    * And so on
    * */
}

abstract class ParkingAttendant extends Account{
    Payment paymentService;

    public abstract boolean processVehicleEntry(Vehicle vehicle);
    public abstract PaymentInfo processPayment(ParkingTicket parkingTicket, PaymentType paymentType);
}

class Vehicle{
    String licenseNumber;
    VehicleType vehicleType;
    ParkingTicket parkingTicket;
    PaymentInfo paymentInfo;
}

abstract class ParkingTicket{
    int ticketId;
    int levelId;
    int spaceId;
    Date vehicleEntryDateTime;
    Date vehicleExitDateTime;
    ParkingSpaceType parkingSpaceType;
    double totalCost;
    ParkingTicketStatus parkingTicketStatus;

    public abstract void updateTotalCost();
    public abstract void updateVehicleExitTime(Date vehicleExitDateTime);
}

enum PaymentType{
    CASH, CREDIT_CARD, DEBIT_CARD, UPI
}

enum ParkingSpaceType{
    BIKE_PARKING, CAR_PARKING, TRUCK_PARKING
}

abstract class Payment{
    public abstract PaymentInfo  makePayment(ParkingTicket parkingTicket, PaymentType paymentType);
}

class PaymentInfo{
    double amount;
    Date paymentDate;
    int transactionId;
    ParkingTicket parkingTicket;
    PaymentStatus paymentStatus;
}

enum VehicleType{
    BIKE, CAR, TRUCK
}

enum ParkingTicketStatus{
    PAID, ACTIVE
}

enum PaymentStatus{
    UNPAID, PENDING, COMPLETED, DECLINED, CANCELLED, REFUNDED
}