package parkingLot.strategies;

import parkingLot.exceptions.ParkingLotFullException;
import parkingLot.models.ParkingSlot;
import parkingLot.models.VehicleType;

public interface ParkingPlaceAllotmentStrategy {

    ParkingSlot getParkingSlot(VehicleType vehicleType, Long parkingLotId)
            throws ParkingLotFullException;

}
