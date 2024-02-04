package parkingLot.repositories;

import parkingLot.exceptions.ParkingLotNotFoundException;
import parkingLot.models.ParkingLot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLotRepository {

    Map<Long, ParkingLot> parkingLotMap = new HashMap<>();
    public ParkingLot getParkingLotById(Long parkingLotId) throws ParkingLotNotFoundException {

        // if parkingLot exists return its id
        if(parkingLotMap.containsKey(parkingLotId))
            parkingLotMap.get(parkingLotId);
        // if parking lot does not exist, throw an exception

        throw new ParkingLotNotFoundException();
    }
}
