public class CoreJava {
    public static void main(String[] args) {
        Hello();
        System.out.print("\n");

        PrintingOutPut();
        System.out.print("\n\n");

        Operator();
        System.out.print("\n");

        ControlFlow(75);
        System.out.print("\n");

        Loops();
        System.out.print("\n\n");

        Calculator();
    }   
    
    // Hello World Challenge
    public static void Hello(){
        System.out.println("Hello World!");
    }

    // Printing Output Challenge
    public static void PrintingOutPut(){
        String name = "Dennis Keithly";
        int age = 22;
        double height = 5.9;

        System.out.printf("Name:%s,Age:%d,Height:%.1f", name, age, height);
    }

    // Operator Challenge
    public static void Operator(){
        int a = 20;
        int b = 10;

        System.out.println("Addition: " + (a+b));
        System.out.println("Subtraction: " + (a-b));
        System.out.println("Multiplication: " + (a*b));
        System.out.println("Division: " + (a/b));

        boolean aIsGreater = a > b;

        boolean bOverZero = b > 0 && aIsGreater;

        System.out.println("a is greater than b?  " + (aIsGreater ? "true" : "false"));
        System.out.println("Is a > b and b > 0?  " + (bOverZero ? "true" : "false"));
    }

    // Control Flow Challenge
    // I am assuming we are not given a grade letter and are instead calculating it
    // I am also assuming no edge case handling like no grades below zero or above 100
    public static void ControlFlow(int score){
        if(score > 50)
            System.out.println("Passed");
        else
            System.out.println("Failed");

        char grade;

        if(score <= 60)
            grade = 'D';
        else if (score < 75)
            grade = 'C';
        else if (score < 90)
            grade = 'B';
        else
            grade = 'A';

        System.out.println("Grade: " + grade);
    }

    // Loops Challenge
    public static void Loops(){
        System.out.print("For Loop: ");
        for(int i = 1; i <= 5; i++){
            System.out.print(i +" ");
        }

        System.out.print("\nWhile Loop: ");

        int i = 1;
        while(i <= 5){
            System.out.print(i + " ");
            i++;
        }

        System.out.print("\nDo-While Loop: ");
        i = 1;

        do { 
            System.out.print(i + " ");
            i++;
        } while (i <= 5);
    }

    // Calculator Challenge
    public static void Calculator(){
        double num1 = 7;
        double num2 = 0;
        char operator = '/';

        String again = "y";

        while(again.equals("y")){
            if(operator == '/')
            {
                if(num2 == 0)
                    System.out.println("Cannot divide by zero");
                else
                    System.out.println("Result: " + num1/num2);
            }
            else if(operator == '*')
                System.out.println("Result: " + num1*num2);
            else if (operator == '+')
                System.out.println("Result: " + (num1+num2));
            else if (operator == '-')
                System.out.println("Result: " + (num1-num2));
            else 
                System.out.println("Unrecognized command");
            again = "n";
        }
        System.out.println("Thank you for using the calculator");
    }
}
