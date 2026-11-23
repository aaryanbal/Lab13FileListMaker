import java.util.Scanner;

public class SafeInput
{


    public static String getNonZeroLengthString(Scanner pipe, String prompt)
    {
        String retString = "";

        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();

            if (retString.isEmpty())
            {
                System.out.println("You must enter at least one character");
            }

        } while (retString.isEmpty());

        return retString;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        String retString = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();

            if (retString.matches(regEx))
            {
                done = true;
            }
            else
            {
                System.out.println("Invalid input");
            }

        } while (!done);

        return retString;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        String response = getRegExString(pipe, prompt + " [Y/N]", "[YyNn]");

        return response.equalsIgnoreCase("Y");
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int retValue = 0;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");

            String input = pipe.nextLine();

            try {
                retValue = Integer.parseInt(input);

                if (retValue >= low && retValue <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("Number is out of range");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid number");
            }

        } while (!done);

        return retValue;
    }
}
