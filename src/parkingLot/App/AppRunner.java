package parkingLot.App;

import parkingLot.controller.TicketController;
import parkingLot.repositories.GateRepository;
import parkingLot.repositories.ParkingLotRepository;
import parkingLot.repositories.TicketRepository;
import parkingLot.repositories.VehicleRepository;
import parkingLot.services.TicketService;

public class AppRunner {

    public static void main(String[] args) {
        GateRepository gateRepository = new GateRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        TicketRepository ticketRepository = new TicketRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        TicketService ticketService= new TicketService(gateRepository,vehicleRepository,parkingLotRepository,ticketRepository);

        TicketController ticketController = new TicketController(ticketService);


    }
}
