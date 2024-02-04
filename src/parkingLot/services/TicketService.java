package parkingLot.services;

import parkingLot.dtos.IssueTicketRequest;
import parkingLot.exceptions.GateNotFoundException;
import parkingLot.exceptions.ParkingLotFullException;
import parkingLot.exceptions.ParkingLotNotFoundException;
import parkingLot.models.*;
import parkingLot.repositories.GateRepository;
import parkingLot.repositories.ParkingLotRepository;
import parkingLot.repositories.TicketRepository;
import parkingLot.repositories.VehicleRepository;
import parkingLot.strategies.ParkingPlaceAllotmentStrategy;
import parkingLot.strategies.ParkingSlotAllotmentStrategyFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class TicketService {

    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;

    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;

    public TicketService(GateRepository gateRepository, VehicleRepository vehicleRepository, ParkingLotRepository parkingLotRepository, TicketRepository ticketRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket issueTicket(IssueTicketRequest ticketRequest) throws GateNotFoundException, ParkingLotNotFoundException, ParkingLotFullException {
        // set time
        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date());

        // get gate details
        Gate gate = gateRepository.findGatebyGateId(ticketRequest.getGateId());
        ticket.setEntryGate(gate);

        //get vehicle details

        Vehicle vehicle = vehicleRepository.getVehicleByNumber(ticketRequest.getVehicleNumber());
        if(vehicle == null){
            // crete vehicle
            vehicle = new Vehicle(ticketRequest.getVehicleType(), ticketRequest.getVehicleNumber(), ticketRequest.getOwnerName());
            vehicleRepository.save(vehicle);
        }
        ticket.setVehicle(vehicle);


        // get parkingLot
        ParkingLot parkingLot = parkingLotRepository.getParkingLotById(ticketRequest.getParkingLotId());

        // for above parking Lot get allotment strategy
        ParkingPlaceAllotmentStrategy allotmentStrategy = parkingLot.getAllotmentStrategy();
        ParkingPlaceAllotmentStrategy parkingAllotmentRule =
                ParkingSlotAllotmentStrategyFactory.getParkingAllotmentStrategyForType(allotmentStrategy);
        ParkingSlot parkingSlot =  parkingAllotmentRule.getParkingSlot(ticketRequest.getVehicleType(), ticketRequest.getParkingLotId());

        // set the parkingSlot to the ticket
        ticket.setParkingSlot(parkingSlot);

        // Set Ticket Number
        ticket.setNumber(ticketRequest.getOwnerName()+UUID.randomUUID());

        // save the Ticket in the Repository

        return ticketRepository.save(ticket);
    }
}
