package bingo;

import java.util.*;

public class Ticket {
    private static final Integer TICKET_BUCKETS_SIZE = 9;
    private static final Integer BUCKET_NUMBER_SIZE = 3;
    List<List<Integer>> ticketBuckets = new ArrayList<>(TICKET_BUCKETS_SIZE);

    public Ticket() {
        for (int i = 0; i < 9; i++) {
            ticketBuckets.add(new ArrayList<>(BUCKET_NUMBER_SIZE));
        }
    }

    public void addToTicketBucket(int bucketNumber, int value) {
        if (ticketBuckets.get(bucketNumber).size() < 3) {
            ticketBuckets.get(bucketNumber).add(value);
        }
    }

    public void sortAscInTicketBucket() {
        for (int i = 0; i < 9; i++) {
            Collections.sort(ticketBuckets.get(i));
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 3; i++) {

            result +=
                    String.format("%-10d%-10d%-10d%-10d%-10d%-10d%-10d%-10d%-10d%n",
                            ticketBuckets.get(0).get(i),
                            ticketBuckets.get(1).get(i),
                            ticketBuckets.get(2).get(i),
                            ticketBuckets.get(3).get(i),
                            ticketBuckets.get(4).get(i),
                            ticketBuckets.get(5).get(i),
                            ticketBuckets.get(6).get(i),
                            ticketBuckets.get(7).get(i),
                            ticketBuckets.get(8).get(i)
                    );
        }
        return result;
    }

    public void fillBlanks() {
        for (int i = 0; i < 9; i++) {
            int missingBlanksNumber = BUCKET_NUMBER_SIZE - ticketBuckets.get(i).size();
            //fill empty cells with 0
            for (int j = 0; j < missingBlanksNumber; j++) {
                ticketBuckets.get(i).add(0);
            }
        }
    }

    public void randomizeSpacePositionInBuckets() {
        for (int i = 0; i < 9; i++) {
            Random random = new Random();
            int spaceIndex = random.ints(0, 3)
                    .findFirst()
                    .getAsInt();

            if (spaceIndex == 0) {
                ticketBuckets.get(i).add(0, 0);
            } else if (spaceIndex == 1) {
                ticketBuckets.get(i).add(1, 0);
            }
            if (ticketBuckets.get(i).size() > 3) {
                ticketBuckets.get(i).remove(3);
            }
        }
    }
}
