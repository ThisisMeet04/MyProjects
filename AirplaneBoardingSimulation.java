    import java.util.*;

/*
*   PART B
*
* This code involves simulation of Airplane boarding of different airplane size like n=10, n=20, n=50
* Key components of this class are shuffling, priority queue and comparisons and efficiency of seat arrangements
* and prioritisation of passengers with the use of prioriy queue
*
*

 */
    // Seat class representing each passenger's seat and slowness
    class Seat {
        int row;
        char position;
        int slowness;

        Seat(int row, char position, int slowness) {
            this.row = row;
            this.position = position;
            this.slowness = slowness;
        }
    }

    // Custom priority queue using a binary heap with a comparator for different boarding strategies
    class CustomPriorityQueue {
        private List<Seat> heap;
        private Comparator<Seat> comparator;

        CustomPriorityQueue(Comparator<Seat> comparator) {
            this.heap = new ArrayList<>();
            this.comparator = comparator;
        }

        // Inserts a new seat into the priority queue
        void insert(Seat seat) {
            heap.add(seat);
            heapifyUp(heap.size() - 1);
        }

        // Builds the heap from an existing list of seats
        void buildHeap(List<Seat> seats) {
            heap.clear();
            heap.addAll(seats);
            for (int i = heap.size() / 2 - 1; i >= 0; i--) {
                heapifyDown(i);
            }
        }

        // Removes and returns the highest priority seat
        Seat remove() {
            if (heap.isEmpty()) return null;
            Seat top = heap.get(0);
            Seat last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            return top;
        }

        boolean isEmpty() {
            return heap.isEmpty();
        }

        // Adjusts the heap upwards during insertion
        private void heapifyUp(int index) {
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                if (comparator.compare(heap.get(index), heap.get(parentIndex)) >= 0) {
                    break;
                }
                swap(index, parentIndex);
                index = parentIndex;
            }
        }

        //Adjusts the heap downwards during removal
        private void heapifyDown(int index) {
            int size = heap.size();
            while (index < size / 2) {
                int leftChild = 2 * index + 1;
                int rightChild = leftChild + 1;
                int smallest = leftChild;

                if (rightChild < size && comparator.compare(heap.get(rightChild), heap.get(leftChild)) < 0) {
                    smallest = rightChild;
                }

                if (comparator.compare(heap.get(index), heap.get(smallest)) <= 0) {
                    break;
                }

                swap(index, smallest);
                index = smallest;
            }
        }

        //Swapping of the elements in the heap
        private void swap(int i, int j) {
            Seat temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    // Boarding strategy comparators which compares seats
    class BackToFrontComparator implements Comparator<Seat> {
        public int compare(Seat s1, Seat s2) {
            return Integer.compare(s2.row, s1.row); // Prioritize back rows first
        }
    }

    class RandomBoardingComparator implements Comparator<Seat> {
        public int compare(Seat s1, Seat s2) {
            return 0; // Random order, no preference
        }
    }

    public class AirplaneBoardingSimulation {

        // Generate seats for airplane of n rows with randomized slowness
        private static List<Seat> generateSeats(int n) {
            List<Seat> seats = new ArrayList<>();
            char[] positions = {'A', 'B', 'C', 'D'};
            Random random = new Random();

            for (int row = 1; row <= n; row++) {
                for (char pos : positions) {
                    int slowness = random.nextInt(4) + 1;
                    seats.add(new Seat(row, pos, slowness));
                }
            }
            return seats;
        }

        // Run boarding simulation for a specific strategy
        private static int simulateBoarding(List<Seat> seats, Comparator<Seat> comparator, int numRows) {
            CustomPriorityQueue queue = new CustomPriorityQueue(comparator);
            queue.buildHeap(seats);

            int totalTime = 0;
            while (!queue.isEmpty()) {
                Seat currentSeat = queue.remove();
                totalTime += currentSeat.slowness;
            }
            return totalTime;
        }

        public static void main(String[] args) {
            int[] rowSizes = {10, 20, 50};
            int numSimulations = 10;

            for (int n : rowSizes) {
                System.out.println("Airplane size: " + n + " rows");
                int[] totalTimes = new int[2];

                for (int sim = 0; sim < numSimulations; sim++) {
                    List<Seat> seats = generateSeats(n);
                    Collections.shuffle(seats);

                    // Simulate for each strategy
                    totalTimes[0] += simulateBoarding(seats, new BackToFrontComparator(), n);
                    totalTimes[1] += simulateBoarding(seats, new RandomBoardingComparator(), n);
                }

                // Display results
                System.out.printf("Average time for back-to-front: %.2f seconds\n", totalTimes[0] / (double) numSimulations);
                System.out.printf("Average time for random boarding: %.2f seconds\n", totalTimes[1] / (double) numSimulations);
                System.out.println();
            }
        }
    }
