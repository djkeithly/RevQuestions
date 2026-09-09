package challenges;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

class DateTime {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        //dateChecker();

        //ageCheck(in);

        birthdayCheck(in);

        in.close();
    }

    public static void dateChecker(){
        LocalDate today = LocalDate.now();
        System.out.println("1. Today's date: " + today);
        System.out.println("2. The current year: " + today.getYear());
        System.out.println("3. The current month: " + today.getMonth());
        System.err.println("4. The current day of the month: " + today.getDayOfMonth());
    }

    public static void ageCheck(Scanner in){
        System.out.print("Birth Year: ");
        int year = in.nextInt();
        System.out.print("Birth Month (in number form): ");
        int month = in.nextInt();
        System.out.print("Birth Day: ");
        int dayOfMonth = in.nextInt();

        LocalDate now = LocalDate.now();

        // Get the years that passed.
        year = now.getYear() - year;

        // Check if their birthday has passed this year
        if(month <= now.getMonthValue() && now.getDayOfMonth() <= dayOfMonth){
            System.out.println("You are " + year + " years old");
        }
        else{
            year -= 1;
            System.out.println("You are " + year + " years old");
        }
    }

    public static void birthdayCheck(Scanner in){
        System.out.print("Birth Month (in number form): ");
        int month = in.nextInt();
        System.out.print("Birth Day: ");
        int dayOfMonth = in.nextInt();

        LocalDate now = LocalDate.now();

        // If the birthday is now print 0 and Happy Birthday
        if(now.getMonthValue() == month && now.getDayOfMonth() == dayOfMonth){
            System.out.println("0 Days until your birthday. Happy Birthday!");
            return;
        }

        // Check if we are before or after the birthday
        int days;

        // Create a dateChecker
        LocalDate checker;

        //System.out.println(month + " n: " + now.getMonthValue());
        //System.out.println(dayOfMonth + " n: " + now.getDayOfMonth());

        // If before birthday, set days to 0 then count up
        // If after birthday, set days to 365 then remove days between birthday and now
        if(now.getMonthValue() < month || (now.getMonthValue() == month && dayOfMonth > now.getDayOfMonth() )){
            days = 0;
            for(int i = now.getMonthValue(); i < month; i++){
                checker = LocalDate.of(now.getYear(), i, 1);
                days += checker.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
            }
            days += dayOfMonth - now.getDayOfMonth();

            System.out.println(days + " days until your birthday");
        } else {
            days = 365;
            for(int i = month; i < now.getMonthValue(); i++){
                checker = LocalDate.of(now.getYear(), i, 1);
                days -= checker.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
            }
            days += dayOfMonth - now.getDayOfMonth();

            System.out.println(days + " days until your birthday");
        }
    }
}
