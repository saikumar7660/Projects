package parkingLot.controller;

import parkingLot.dtos.IssueTicketRequest;
import parkingLot.dtos.IssueTicketResponse;
import parkingLot.dtos.ResponseStatus;
import parkingLot.exceptions.GateNotFoundException;
import parkingLot.exceptions.ParkingLotFullException;
import parkingLot.exceptions.ParkingLotNotFoundException;
import parkingLot.models.Ticket;
import parkingLot.services.TicketService;

public class TicketController {

    private TicketService ticketService;
    public static String TICKET_ISSUED_MESSAGE = "Ticket issued";
    public static String INVALID_GATE = "Gate is Not Valid";
    public static String INVALID_PARKING_LOT = "Parking Lot is Not Valid";
    public static String PARKING_LOT_FULL = "Parking Lot is Full";

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public IssueTicketResponse issueTicket(IssueTicketRequest ticketRequest) {
        Ticket ticket = null;
        try {
            ticket = ticketService.issueTicket(ticketRequest);
        } catch (GateNotFoundException e) {
            return new IssueTicketResponse(null, ResponseStatus.FAILURE, INVALID_GATE);
        } catch (ParkingLotNotFoundException e) {
            return new IssueTicketResponse(null, ResponseStatus.FAILURE, INVALID_PARKING_LOT);
        } catch (ParkingLotFullException e) {
            return new IssueTicketResponse(null, ResponseStatus.FAILURE, PARKING_LOT_FULL);

        }

        return new IssueTicketResponse(ticket, ResponseStatus.SUCCESS, TICKET_ISSUED_MESSAGE);

    }
}
