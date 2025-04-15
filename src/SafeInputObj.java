import java.util.Scanner;

    public class SafeInputObj {

        /**
         * The Scanner used by all input methods.
         */
        private Scanner pipe;

        /**
         * Reads from system.in
         */
        public SafeInputObj() {
            this.pipe = new Scanner(System.in);
        }

        /**
         * Overloaded constructor with a scanner parameter.
         * @param scanner
         */
        public SafeInputObj(Scanner scanner) {
            this.pipe = scanner;
        }

        /**
         * Prompts user to enter valid string
         * Loops until valid input
         *
         * @param prompt The prompt message displayed to the user
         * @return A string that is at least 1 character in length
         */
        public String getNonZeroLenString(String prompt) {
            String retString = "";
            do {
                System.out.print(prompt + ": ");
                if (pipe.hasNextLine()) {
                    retString = pipe.nextLine().trim();
                }
            } while (retString.length() == 0);

            return retString;
        }

        /**
         * Prompts user to input an int value in a range (inclusive).
         * Loops until valid input
         *
         * @param prompt Text displayed to user
         * @param low    The lower bound (inclusive)
         * @param high   The upper bound (inclusive)
         * @return int value within range
         */
        public int getRangedInt(String prompt, int low, int high) {
            int retVal = 0;
            boolean done = false;

            do {
                System.out.printf("%s [%d - %d]: ", prompt, low, high);
                if (pipe.hasNextInt()) {
                    retVal = pipe.nextInt();
                    pipe.nextLine(); // clear the buffer
                    if (retVal >= low && retVal <= high) {
                        done = true;
                    } else {
                        System.out.println("Error: " + retVal + " is not in range [" + low + " - " + high + "]");
                    }
                } else {
                    System.out.println("Error: You must enter an integer");
                    pipe.nextLine(); // clear invalid input
                }
            } while (!done);

            return retVal;
        }

        /**
         * Prompts user to input a doubler value in a range (inclusive).
         * Loops until valid input
         *
         * @param prompt Text displayed to user
         * @param low    The lower bound (inclusive)
         * @param high   The upper bound (inclusive)
         * @return double value within range
         */
        public double getRangedDouble(String prompt, double low, double high) {
            double retVal = 0;
            boolean done = false;

            do {
                System.out.printf("%s [%.2f - %.2f]: ", prompt, low, high);
                if (pipe.hasNextDouble()) {
                    retVal = pipe.nextDouble();
                    pipe.nextLine(); // clear the buffer
                    if (retVal >= low && retVal <= high) {
                        done = true;
                    } else {
                        System.out.println("Error: " + retVal + " is not in range [" + low + " - " + high + "]");
                    }
                } else {
                    System.out.println("Error: You must enter a valid number");
                    pipe.nextLine(); // clear invalid input
                }
            } while (!done);

            return retVal;
        }

        /**
         * Prompts the user for a Yes/No confirmation.
         *
         * @param prompt A message to display to the user
         * @return boolean based on user response.
         */
        public boolean getYNConfirm(String prompt) {
            boolean retVal = false;
            boolean done = false;
            String response = "";

            do {
                System.out.print(prompt + " [Y/N]: ");
                if (pipe.hasNextLine()) {
                    response = pipe.nextLine().trim();
                }
                if (response.equalsIgnoreCase("Y")) {
                    retVal = true;
                    done = true;
                } else if (response.equalsIgnoreCase("N")) {
                    retVal = false;
                    done = true;
                } else {
                    System.out.println("Error: please respond with Y or N");
                }
            } while (!done);

            return retVal;
        }

        /**
         * User prompted to enter an int value.
         *
         * @param prompt A message to display to the user
         * @return Int value
         */
        public int getInt(String prompt) {
            int retVal = 0;
            boolean done = false;

            do {
                System.out.print(prompt + ": ");
                if (pipe.hasNextInt()) {
                    retVal = pipe.nextInt();
                    pipe.nextLine(); // clear buffer
                    done = true;
                } else {
                    System.out.println("Error: please enter a valid integer");
                    pipe.nextLine(); // clear invalid input
                }
            } while (!done);

            return retVal;
        }

        /**
         * Prompts the user to enter a double value. Repeats until a valid double is entered.
         *
         * @param prompt A message to display to the user
         * @return The double value entered by the user
         */
        public double getDouble(String prompt) {
            double retVal = 0;
            boolean done = false;

            do {
                System.out.print(prompt + ": ");
                if (pipe.hasNextDouble()) {
                    retVal = pipe.nextDouble();
                    pipe.nextLine();
                    done = true;
                } else {
                    System.out.println("Error: please enter a valid numeric value");
                    pipe.nextLine();
                }
            } while (!done);

            return retVal;
        }

        // Add any other original SafeInput methods similarly,
        // removing static and (Scanner pipe) parameters, etc.
    }
