
import java.util.*;


// javac SeptemberThird.java
// java SeptemberThird

// Using one scanner as it prevents crashing when running all methods in sequence
public class SeptemberThird {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        REPL(in);

        PasswordValid(in);

        WordAnalyzer(in);

        in.close();
    }


    // REPL APP Challenge
    // When reading primitives, the read needed to be added so that it would keep reading strings.
    public static void REPL(Scanner in){
        // Can't break outside of a loop in a switch. While will run until false
        boolean again = true;

        String input;

        while(again){
            int firstNumber;
            int secondNumber;
            String reverseString;

            input = in.nextLine();

            switch(input.toLowerCase()){
                case("help"):
                    System.out.println("Avaliable Commands:: \n\tadd\n\tsubtract\n\tmultiply\n\tdivide\n\trandom\n\treverse\n\tquit\n");
                    break;
                case("add"):
                    System.out.print("First Number: ");
                    firstNumber = in.nextInt();
                    System.out.print("Second Number: ");
                    secondNumber = in.nextInt();
                    System.out.println("Result: " + (firstNumber + secondNumber) + "\n");
                    in.nextLine();
                    break;
                case("subtract"):
                    System.out.print("First Number: ");
                    firstNumber = in.nextInt();
                    System.out.print("Second Number: ");
                    secondNumber = in.nextInt();
                    System.out.println("Result: " + (firstNumber - secondNumber) + "\n");
                    in.nextLine();
                    break;
                case("multiply"):
                    System.out.print("First Number: ");
                    firstNumber = in.nextInt();
                    System.out.print("Second Number: ");
                    secondNumber = in.nextInt();
                    System.out.println("Result: " + (firstNumber * secondNumber) + "\n");
                    in.nextLine();
                    break;
                case("divide"):
                    System.out.print("First Number: ");
                    firstNumber = in.nextInt();
                    System.out.print("Second Number: ");
                    secondNumber = in.nextInt();
                    System.out.println("Result: " + (firstNumber / secondNumber) + "\n");
                    in.nextLine();
                    break;
                case("random"):
                    System.out.print("Minimum: ");
                    firstNumber = in.nextInt();
                    System.out.print("Maximum: ");
                    secondNumber = in.nextInt();

                    if(firstNumber > secondNumber){
                        System.out.println("Minimum must be less than maximum\n");
                        break;
                    }
                    System.out.println("Result: " + ((int) Math.floor((Math.random() * (secondNumber + 1 - firstNumber) + firstNumber))) + "\n");
                    in.nextLine();
                    break;
                case("reverse"):
                    System.out.print("Enter Text: ");
                    reverseString = in.nextLine();
                    for(int i = reverseString.length() - 1; i >= 0; i--){
                        System.out.print(reverseString.charAt(i));
                    }
                    System.out.print("\n\n");
                    break;
                case("quit"):
                    System.out.println("Goodbye!");
                    again = false;
                    break;
                default:
                    System.out.println("Invalid Command\n");
                    break;
            }
        }
    }

    // Password Validator
    public static void PasswordValid(Scanner in){

        boolean upper;
        boolean lower;
        boolean number;

        while(true){
            // Reset cases every time a password is rejected so they can't hit one condition every rejection and get by
            upper = false;
            lower = false;
            number = false;

            System.out.print("Create a password: ");
            // Unlike last problem doesn't require an extra in.nextLIne()
            String password = in.nextLine();

            // Quit early if condition failed
            if(password.length() < 8){
                System.out.println("Password Rejected:\n-Must contain an uppercase letter\n-Must contain a number\n-Must be at least 8 characters\n");
                continue;
            }

            // Hit every case
            for(int i = 0; i < password.length(); i++){
                if(Character.isUpperCase(password.charAt(i)))
                    upper = true;
                if(Character.isLowerCase(password.charAt(i)))
                    lower = true;
                if(Character.isDigit(password.charAt(i)))
                    number = true;

                // Quit early if everything checks out
                if(upper && lower && number)
                    break;
            }

            // Break loop if it all checks out
            if(upper && lower && number)
                break;
            else 
                System.out.println("Password Rejected:\n-Must contain an uppercase letter\n-Must contain a number\n-Must be at least 8 characters\n");
        }
        System.out.println("Password accepted!");
    }

    // Word Analyzer
    public static void WordAnalyzer(Scanner in){
        System.out.print("Enter a word: ");
        String word = in.nextLine();
        System.out.print("\n");

        int spaces = 0;
        int vowels = 0;
        int consonants = 0;
        int digits = 0;

        for(int i = 0; i < word.length(); i++){
            if(Character.isDigit(word.charAt(i)))
                digits++;
            else if(Character.isWhitespace(word.charAt(i)))
                spaces++;
            else if(Character.isLetter(word.charAt(i))) {
                // I assume y is not being counted as a vowel
                switch (word.charAt(i)) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case'u':
                        vowels++;
                        break;
                    default:
                        consonants++;
                }
            }
        }

        System.out.println("Characters: " + word.length());
        System.out.println("Vowels: " + vowels);
        System.out.println("Consonants: " + consonants);
        System.out.println("Digits: " + digits);
        System.out.println("Spaces " + spaces);
    }
}
