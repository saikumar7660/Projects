package parkingLot.strategies;

import parkingLot.exceptions.ParkingLotFullException;
import parkingLot.models.ParkingSlot;
import parkingLot.models.VehicleType;

public class SimpleParkingSlotAllotmentStrategy implements ParkingPlaceAllotmentStrategy{
    @Override
    public ParkingSlot getParkingSlot(VehicleType vehicleType, Long parkingLotId) throws ParkingLotFullException {
//        ParkingLot parkingLot = parkingLotRepository.getParkingLotWithGate(gate);
//        List<ParkingSpot> parkingSpotList = ParkingSpotRepository.getParkingSpotsByParkingLot();
//
//        for(ParkingSpot parkingSpot: parkingSpotList){
//            if(parkingSpot.getParkingSpotStatus().equals(ParkingSpotStatus.AVAILABLE)
//                    && parkingSpot.getSupportedVehicleTypes().contains(vehicleType)){
//                return parkingSpot;
//            }
//        }
        return null;
    }
}
