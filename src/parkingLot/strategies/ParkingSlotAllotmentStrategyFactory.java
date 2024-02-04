package parkingLot.strategies;

public class ParkingSlotAllotmentStrategyFactory {

    public static ParkingPlaceAllotmentStrategy getParkingAllotmentStrategyForType
            (ParkingPlaceAllotmentStrategy parkingPlaceAllotmentStrategy){
        return new SimpleParkingSlotAllotmentStrategy();
    }
}
