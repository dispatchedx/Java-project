import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class wow {

}

class Book {
    String customerName;
    static int bookNumber; //unique
    int arrival;
    int daysOfResidence;
    int people;
    int count = 0;
    Room room = null;

    public Book() {
        this.bookNumber = ++count;
    }

    public Book(String cName, int arrival, int dResidence, int people) {
        this.customerName = cName;
        this.bookNumber = ++count;
        this.arrival = arrival;
        this.daysOfResidence = dResidence;
        this.people = people;
      //  this.room = room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getDaysOfResidence() {
        return daysOfResidence;
    }

    public void setDaysOfResidence(int daysOfResidence) {
        this.daysOfResidence = daysOfResidence;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public static int getBookNumber() {
        return bookNumber;
    }
}

class Room {
    static int roomNumber; //unique
    int maxCapacity;
    double pricePerson;
    static int r_count = 0;
    Book[] availability = new Book[30];                                                          //availability 2 is WRONG

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public double getPricePerson() {
        return pricePerson;
    }

    public void setPricePerson(double pricePerson) {
        this.pricePerson = pricePerson;
    }

    public static int getRoomNumber() {
        return roomNumber;
    }

    public static void setRoomNumber(int roomNumber) {
        Room.roomNumber = roomNumber;
    }

    public Room(){
        this.roomNumber= ++r_count;
    }

    public Room(int mCapacity, double pPrice) {
        this.roomNumber = ++r_count;
        this.maxCapacity = mCapacity;
        this.pricePerson = pPrice;
    }

   //


    boolean addBook(Book new_book) {
        if (new_book.people <= this.maxCapacity) {
            for(int i=new_book.getArrival();i < new_book.getArrival() + new_book.getDaysOfResidence(); i++) {
                if(availability[i]==null) {
                    availability[i] = new_book;
                    new_book.setRoom(this);
                }
                else
                {
                    System.out.println("Unfortunately the room is already booked on " +i);
                }
            }
            return true;
        } else
            System.out.println("The room is either already booked or is too small");
            return false;
    }

    double pricing() {
        double final_price=0;
        for(int i=0; i<availability.length; i++)
        {
            if (availability[i]!=null)
            final_price+=pricePerson*availability[i].getPeople();
        }
        return final_price;
    }

    boolean cancelBook(int cancelBookNumber) {
        availability[cancelBookNumber]=null;
        return true;
    }

    int percentage() {
        int percentCount=0;
        for(int i=0;i<availability.length;i++)
        {
            if (availability[i]!=null)
            percentCount++;
        }
        return (percentCount/30)*100;
    }

}

class RoomTA extends Room {
    double pricePerDay;

    public RoomTA(int mCapacity, double pPrice, double pricePerDay) {
        super(mCapacity, pPrice);
        this.pricePerDay = pricePerDay;
    }

    @Override
    double pricing() {
        double final_price=0;
        for (int i=0; i<availability.length; i++)
        {
            if(availability[i]!=null)
            final_price+=pricePerDay;
        }
            return final_price;
    }
}

class RoomTB extends RoomTA {
    int discountPerDay;

    public RoomTB(int mCapacity, double pPrice, double pricePerDay, int discountPerDay) {
        super(mCapacity, pPrice, pricePerDay);
        this.discountPerDay = discountPerDay;
        //this.roomNumber = ++r_count;

    }

    @Override
    double pricing() {
        int days = 5;
        int pricePerDay = 50;
        int discountPerDay = 10;
        double tempPrice = pricePerDay;
        double finalPayment = 0;
        for (int i = days; i > 0; i--) {
            if (tempPrice >= pricePerDay / 2) {
                finalPayment += tempPrice;
                tempPrice -= discountPerDay;
            } else {
                tempPrice = pricePerDay / 2;
                finalPayment += tempPrice;
            }
        }
        return finalPayment;
    }

    @Override
    boolean cancelBook(int cancelBookNumber) {
        System.out.println("You cannot cancel a booking on this room.");
        return false;
    }

}

class RoomTC extends Room {
    int leastPeople;
    int leastDays;

    public RoomTC(int mCapacity, double pPrice, int leastPeople, int leastDays) {
        super(mCapacity, pPrice);
        this.leastDays = leastDays;
        this.leastPeople = leastPeople;
    }

    boolean addBook(Book new_book) {
        if (new_book.people <= this.maxCapacity && new_book.people >= this.leastPeople
                && new_book.daysOfResidence >= this.leastDays) {
            for (int i = new_book.getArrival(); i <= new_book.getArrival() + new_book.getDaysOfResidence(); i++) {
                if (availability[i] != null) {
                    availability[i] = new_book;
                    new_book.room = (this);
                } else {
                    System.out.println("Unfortunately the room is booked on " + i);
                }
            }
            return true;
        } else {
            System.out.println("Unfortunately there are too many/few people to book this room.");
            return false;
        }
    }
}
class RoomTD extends Room {
    boolean wifi;
    public RoomTD(int mCapacity, double pPrice, boolean wifi){
        super(mCapacity, pPrice);
        this.wifi=wifi;
    }

    @Override
    double pricing() {
        double final_price=0;
        for(int i=0; i<availability.length; i++)
        {
            if (availability[i]!=null)
                final_price+=pricePerson*availability[i].getPeople();
        }
        return final_price*1.2;
    }
}

class RoomTE extends Room{
    boolean couple;
    double pricePerDay;
    public RoomTE(int maxCapacity,double pPrice, double pricePerDay, boolean couple) {
        super(maxCapacity,pPrice);
        this.maxCapacity=2;
        this.couple = couple;
        this.pricePerDay=pricePerDay;
        if(couple)
            this.pricePerDay*=0.8;
    }

    @Override
    double pricing() {

        int final_price=0;
        for(int i=0; i<availability.length; i++)
        {
            if (availability[i]!=null)
                final_price+=pricePerson*availability[i].getPeople();
        }
        if (couple) {
            final_price *= 0.8;
        }
        return final_price;

    }
}

class Hotel{
    String hotelName;
    ArrayList<Room> rooms_List = new ArrayList<>();
    ArrayList<Book> books_List = new ArrayList<>();

    void add_Room(Room newroom){
        rooms_List.add(newroom);
    }

    Room get_room_by_code(int room_id) {
        for (int i = 0; i <= rooms_List.size(); ) {
            if (rooms_List.get(room_id).getRoomNumber() == room_id)
                //   if(rooms_List.contains(room_id.))
                return rooms_List.get(room_id);
        }
            return null;
    }

    Book get_book_by_code(int book_id){
        for (int i = 0; i <= books_List.size(); ) {
        if(books_List.get(book_id).getBookNumber() == book_id)
            return books_List.get(book_id);
        }
            return null;
    }

    boolean add_book_to_room(Book book, int roomcode) {
        boolean test = get_room_by_code(roomcode).addBook(book);
        if (test) {
            books_List.add(book);
            System.out.println("Booking was successful.");
            return true;
        } else {
            System.out.println("Booking was unsuccessful.");
            return false;

        }
    }
    int add_booking(Book book){
        boolean test;
        for(int i=0; i<=rooms_List.size(); i++){
            for(int j=0; j<rooms_List.get(i).availability.length; j++)
            if (rooms_List.get(i).availability[j]==null){
                test = get_room_by_code(i).addBook(book);
                if (test){
                    books_List.add(book);
                    System.out.println("Booking was successful.");
                    return get_room_by_code(i).getRoomNumber();
                }
            }
        }
        System.out.println("Booking was unsuccessful.");
        return 0;
    }
    void cancel_booking(int book_code){
        boolean test;
        test = get_book_by_code(book_code).getRoom().cancelBook(book_code);
        if (test){
            books_List.remove(book_code);
            System.out.println("Booking cancellation successful.");
        }
        else
            System.out.println("Booking cancellation unsuccessful.");
    }

    double calculate_income(int room_code){
        return rooms_List.get(room_code).pricing();
    }

    double calculate_income() {
        double final_income = 0;
        for (int i = 0; i < rooms_List.size(); i++) {
            final_income += rooms_List.get(i).pricing();
        }
        return final_income;
    }

    void booking_plan(){
        System.out.println("Room    01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30");
        for(int i=0; i<rooms_List.size(); i++){
            System.out.println(" "+rooms_List.get(i).getRoomNumber()+ "  ");
            for (int j=0; j<rooms_List.get(i).availability.length; j++){
                if (rooms_List.get(i).availability[j]==null)
                    System.out.print("_ ");
                else
                    System.out.print("* ");
            }
        }
        System.out.println("");
    }
}

class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random ran = new Random();
        int input = 0;

        Hotel Winston = new Hotel();
        RoomTA RA1 = new RoomTA(4, 10, 40);
        Winston.add_Room(RA1);
        RoomTB RB1 = new RoomTB(4, 10, 50, 5);
        Winston.add_Room(RB1);
        RoomTC RC1 = new RoomTC(6, 50, 4, 5);
        Winston.add_Room(RC1);
        RoomTE RE1 = new RoomTE(2, 25, 50, true);
        Winston.add_Room(RE1);
        RoomTE RE2 = new RoomTE(3, 20, 50, false);
        Winston.add_Room(RE2);
        RoomTD RD1 = new RoomTD(3, 40, true);
        Winston.add_Room(RD1);
        RoomTA RA2 = new RoomTA(1, 30, 30);
        Winston.add_Room(RA2);
        RoomTA RA3 = new RoomTA(2, 5, 15);
        Winston.add_Room(RA3);
        RoomTC RC2 = new RoomTC(7, 15, 4, 2);
        Winston.add_Room(RC2);
        RoomTB RB2 = new RoomTB(10, 5, 100, 10);
        Winston.add_Room(RB2);


        while (input != 99) {

          /*  int arrival0 = ran.nextInt(30) + 1;
            int days0 = ran.nextInt(31 - arrival0) + 1;
            int people0 = ran.nextInt(5);
            String[] listName = new String[]{"Johnny", "Walker", "Smith", "Andrea", "Watermelon", "Bird", "Exile", "Nothing", "Lee", "Ayumi"};
            int posit = ran.nextInt(9);
            String clientName = listName[posit];
            Book time = new Book(clientName, arrival0, days0, people0);
            Winston.add_booking(time);
            if (ran.nextInt(3) == 0) {
                int posit0 = ran.nextInt(Winston.books_List.size());
                Winston.cancel_booking(Winston.books_List.get(posit0).getBookNumber()); */

                System.out.println("Choose an option");
                System.out.println("1. Add a booking ");
                System.out.println("2. Cancel a booking  ");
                System.out.println("3. View hotel's booking ");
                System.out.println("4. View all the rooms of the hotel");
                System.out.println("5. View booking plan");
                System.out.println("6. View income");
                System.out.println("99. exit");
                System.out.println("Press any other key to refresh");
                try {
                    input = scan.nextInt();
                } catch (java.util.InputMismatchException IME) {
                    IME.printStackTrace();
                }
                switch (input) {
                    case 1:
                        Book newbook = new Book();
                        String cName;
                        int arrival = 0;
                        int days = 0;
                        int people = 0;
                        boolean code = false;
                        int room_id = 0;
                        try {
                            scan.nextLine();
                            System.out.println("What is your name?");
                            cName = scan.nextLine();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                            cName = scan.nextLine();
                        }

                        newbook.setCustomerName(cName);

                        try {

                            System.out.println("When will you arrive?");
                            arrival = scan.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                            arrival = scan.nextInt();
                        }

                        newbook.setArrival(arrival);

                        try {
                            System.out.println("How many days will you stay?");
                            days = scan.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                            days = scan.nextInt();
                        }

                        newbook.setDaysOfResidence(days);

                        try {
                            System.out.println("How many people will stay?");
                            people = scan.nextByte();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                            people = scan.nextByte();
                        }

                        newbook.setPeople(people);


                        try {
                            System.out.println("Would you like a specific room? (true for yes false for no)");
                            code = scan.nextBoolean();
                            scan.nextLine();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                        }

                        if (code) {
                            try {
                                System.out.println("Which is the room's number?");
                                room_id = scan.nextInt();
                            } catch (java.util.InputMismatchException e) {
                                System.out.println(e);
                                room_id = scan.nextInt();
                            }

                            if (Winston.get_room_by_code(room_id) != null) {
                                Winston.add_book_to_room(newbook, room_id);

                            }
                        } else {

                            Winston.add_booking(newbook);

                        }

                        break;
                    case 2:
                        int cancel_id = 0;
                        try {
                            System.out.println("What is the booking ID you want to cancel?");
                            cancel_id = scan.nextInt();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                            cancel_id = scan.nextInt();
                        }
                        Winston.cancel_booking(cancel_id);
                        break;
                    case 3:
                        System.out.println("Booking id Name of Customer Room id");
                        for (int i = 0; i <= Winston.books_List.size() - 1; i++) {
                            System.out.print(Winston.books_List.get(i).getBookNumber() + "\t ");
                            System.out.print(Winston.books_List.get(i).getCustomerName() + " \t");
                            System.out.println(Winston.books_List.get(i).getRoom().getRoomNumber());
                        }
                        break;
                    case 4:
                        System.out.println("room code percentage Income from this room");
                        for (int i = 0; i < Winston.rooms_List.size(); i++) {
                            System.out.print(Winston.rooms_List.get(i).getRoomNumber() + " ");
                            System.out.print(Winston.rooms_List.get(i).percentage() + " ");
                            System.out.println(Winston.rooms_List.get(i).pricing());
                        }
                        break;
                    case 5:
                        Winston.booking_plan();
                        break;
                    case 6:
                        int room_ids = 1;
                        code = false;
                        try {
                            System.out.println("Do you want the income for a certain room (true for yes false for no)");
                            code = scan.nextBoolean();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println(e);
                        }

                        if (code) {
                            try {
                                System.out.println("What is the room number");
                                room_ids = scan.nextInt();
                            } catch (java.util.InputMismatchException e) {
                                System.out.println(e);
                                room_ids = scan.nextInt();
                            }
                            if (Winston.get_room_by_code(room_ids) != null) {
                                System.out.println(Winston.calculate_income(room_ids));
                            }

                        } else {
                            System.out.println(Winston.calculate_income());
                        }
                        break;
                }
            }
        }
    }

