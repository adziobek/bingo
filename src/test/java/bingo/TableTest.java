package bingo;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TableTest {

    private static Ticket[] ticketsStrip;

    @BeforeClass
    public static void setUp() {
        ticketsStrip = generateTicketsStrip();
    }

    @Test
    public void test_bingoStripsConsistOfSixTickets() {
        assertThat(ticketsStrip.length).isEqualTo(6);
    }

    @Test
    public void test_everyNumber1_90AppearAcrossTicketsAndNoDuplicates() {
        List<Integer> range = IntStream.rangeClosed(1, 90)
                .boxed().collect(Collectors.toList());
        List<Integer> allNumberFromTickets = new ArrayList<>();

        for (Ticket ticket : ticketsStrip) {
            for (int ticketColumnIndex = 0; ticketColumnIndex < 9; ticketColumnIndex++) {

                allNumberFromTickets.addAll(ticket.ticketBuckets.get(ticketColumnIndex));
            }
            allNumberFromTickets.removeIf(ticketNumber -> ticketNumber.equals(0));
        }
        assertThat(allNumberFromTickets.size()).isEqualTo(90);
        assertThat(allNumberFromTickets.containsAll(range)).isTrue();
    }

    @Test
    public void test_everyTicketColumnHasNever3Blanks() {
        for (Ticket ticket : ticketsStrip) {
            for (int ticketColumnIndex = 0; ticketColumnIndex < 9; ticketColumnIndex++) {
                assertTicketColumnHasNever3Blanks(ticket.ticketBuckets.get(ticketColumnIndex));
            }
        }
    }

    private void assertTicketColumnHasNever3Blanks(List<Integer> columnNumbers) {
        assertThat(Collections.frequency(columnNumbers, 0)).isLessThan(3);
    }

    @Test
    public void test_everyTicketColumnHasNumberOrderedASC() {
        for (Ticket ticket : ticketsStrip) {
            for (int ticketColumnIndex = 0; ticketColumnIndex < 9; ticketColumnIndex++) {
                assertTicketColumnHasNumberOrderedASC(ticket.ticketBuckets.get(ticketColumnIndex));
            }
        }
    }

    private void assertTicketColumnHasNumberOrderedASC(List<Integer> columnNumbers) {
        columnNumbers.removeIf(ticketNumber -> ticketNumber.equals(0));
        assertThat(columnNumbers.stream().sorted().collect(Collectors.toList())
                .equals(columnNumbers)).isTrue();
    }


    @Test
    public void test_bingoTicketsSize() {
        for (Ticket ticket : ticketsStrip) {
            assertTicketColumnSize(ticket);
            assertTicketRowSize(ticket);
        }
    }

    @Test
    public void test_bingoTicketColumn() {
        for (Ticket ticket : ticketsStrip) {
            assertTicketColumnSize(ticket);
            assertTicketRowSize(ticket);
        }
    }

    private void assertTicketColumnSize(Ticket ticket) {
        assertThat(ticket.ticketBuckets.size()).isEqualTo(9);

    }

    private void assertTicketRowSize(Ticket ticket) {
        for (int ticketColumnIndex = 0; ticketColumnIndex < 9; ticketColumnIndex++) {

            assertThat(ticket.ticketBuckets.get(ticketColumnIndex).size()).isEqualTo(3);
        }
    }

    public static Ticket[] generateTicketsStrip() {
        Ticket[] ticketsStrip = Table.generateTicketsStrip();
        for (int i = 0; i < ticketsStrip.length; i++) {
            int ticketNumber = i + 1;
            System.out.println("Ticket " + ticketNumber);
            System.out.println(ticketsStrip[i]);
        }
        return ticketsStrip;
    }
}