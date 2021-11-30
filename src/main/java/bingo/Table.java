package bingo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {

    /*
     * Solution description
     * 1. At the start - create 9 bingo buckets with number we use to fill 6 ticket strips [1,2,..,9], [10,11,..,19],..,[80,81,..,90]
     * 2. Shuffle numbers in this bingo buckets to guarantee that numbers from bingo buckets are taken as a random
     * 3. Each ticket has 9 ticket buckets with size = 3 (ticket bucket corresponding to ticket column)
     * 4. Fill each ticket first row with first value from Bingo Bucket - now we have first row of each ticket filled with numbers
     * 5. Fill each ticket with missing six numbers from Bingo Buckets
     * 6. Guarantee asc order in ticket column, fill blanks, randomize blank position in ticket columns
     * */

    public static Ticket[] generateTicketsStrip() {
        int[][] bingoBuckets = generateBingoBuckets();
        Ticket[] tickets = generateEmptyBingoTickets(6);

        //Shuffle numbers in bingo buckets
        shuffleBingoBuckets(bingoBuckets);

        //Fill each ticket first row with first value from Bingo Bucket
        fillEachTicketFirstRowWithNumbersFromBingoBuckets(tickets, bingoBuckets);

        //Now each ticket has 1st row filled with numbers and 6 numbers missing - we have to put this numbers to tickets
        addMissingNumbersToTickets(tickets, bingoBuckets);

        //sort ASC in ticket bucket and fill blanks
        sortAscInTicketBucketAndFillBlanksAndRandomizeSpacePosition(tickets);

        return tickets;
    }

    private static int[][] generateBingoBuckets() {
        int[][] bucket = new int[9][];
        bucket[0] = makeSequence(1, 9);
        bucket[1] = makeSequence(10, 19);
        bucket[2] = makeSequence(20, 29);
        bucket[3] = makeSequence(30, 39);
        bucket[4] = makeSequence(40, 49);
        bucket[5] = makeSequence(50, 59);
        bucket[6] = makeSequence(60, 69);
        bucket[7] = makeSequence(70, 79);
        bucket[8] = makeSequence(80, 90);
        return bucket;
    }

    private static Ticket[] generateEmptyBingoTickets(int ticketNumber) {
        Ticket[] tickets = new Ticket[ticketNumber];

        for (int j = 0; j < ticketNumber; j++) {
            tickets[j] = new Ticket();
        }
        return tickets;
    }


    private static void shuffleBingoBuckets(int[][] bingoBuckets) {
        shuffleArray(bingoBuckets[0]);
        shuffleArray(bingoBuckets[1]);
        shuffleArray(bingoBuckets[2]);
        shuffleArray(bingoBuckets[3]);
        shuffleArray(bingoBuckets[4]);
        shuffleArray(bingoBuckets[5]);
        shuffleArray(bingoBuckets[6]);
        shuffleArray(bingoBuckets[7]);
        shuffleArray(bingoBuckets[8]);
    }

    private static void fillEachTicketFirstRowWithNumbersFromBingoBuckets(Ticket[] tickets, int[][] bingoBuckets) {
        for (int i = 0; i < tickets.length; i++) {
            for (int ticketColumnIndex = 0; ticketColumnIndex < 9; ticketColumnIndex++) {
                tickets[i].addToTicketBucket(ticketColumnIndex, bingoBuckets[ticketColumnIndex][0]);
                //Remove already used number from Bingo Bucket
                bingoBuckets[ticketColumnIndex] = removeFirstNumberFromBucket(bingoBuckets[ticketColumnIndex]);
            }
        }
    }

    private static void addMissingNumbersToTickets(Ticket[] tickets, int[][] bingoBuckets) {
        for (int i = 0; i < tickets.length; i++) {
            int nextSixNumbersFromBucket[] = getNextSixNumbersFromBuckets(bingoBuckets);
            for (int j = 0; j < nextSixNumbersFromBucket.length; j++) {
                int nextNumber = nextSixNumbersFromBucket[j];
                //In future we should add next number to random ticket bucket choosen from column 1-9
                tickets[i].addToTicketBucket(j, nextNumber);
            }
        }
    }

    private static void sortAscInTicketBucketAndFillBlanksAndRandomizeSpacePosition(Ticket[] tickets) {
        for (int i = 0; i < tickets.length; i++) {
            tickets[i].sortAscInTicketBucket();
            tickets[i].fillBlanks();
            tickets[i].randomizeSpacePositionInBuckets();
        }
    }

    private static int[] getNextSixNumbersFromBuckets(int[][] buckets) {
        ArrayList<Integer> nextSixNumbersFromBuckets = new ArrayList<>();

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].length == 0) {
                continue;
            }
            //Get first bucket value
            nextSixNumbersFromBuckets.add(Integer.valueOf(buckets[i][0]));
            //Remove from bucket first used number
            buckets[i] = removeFirstNumberFromBucket(buckets[i]);
            if (nextSixNumbersFromBuckets.size() == 6) {
                break;
            }
            //Start from first bucket
            if (i == 8) {
                i = 0;
            }
        }
        return nextSixNumbersFromBuckets.stream().mapToInt(i -> i).toArray();
    }

    private static int[] makeSequence(int begin, int end) {
        if (end < begin)
            return null;

        int[] ret = new int[++end - begin];
        for (int i = 0; begin < end; )
            ret[i++] = begin++;
        return ret;
    }

    private static void shuffleArray(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
    }

    private static int[] removeFirstNumberFromBucket(int[] bucket) {
        return Arrays.copyOfRange(bucket, 1, bucket.length);
    }
}
