import java.io.FileReader;
import java.io.BufferedReader;
// import java.util.Arrays;
import java.lang.StringBuffer;

/*
    I have been unable to optimize this code to the best of my abilities, please provide
    suggestions or send 'pull requests' if you know how to optimize the code
*/

public class schematicReader {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("EngineSchematic.txt"))) {
            int totalSum = 0; // to store the calculated sum of all valid part numbers

            // tell the while loop if the first or last line of file is being read
            boolean isFirstLine = true, isLastLine = false;

            // Creating a String array that will contain the required lines for identifying
            // valid numbers
            String[] requiredLines = new String[3];

            // finding the length of a line of the file (all lines are of same length)
            String firstLine = br.readLine(); // storing the first line as pointer has now moved to second line
            int lineLength = firstLine.length();

            // Creating a place-holder String to send inside the String array in case of
            // first or last line
            StringBuffer placeHolder = new StringBuffer();
            for (int i = 0; i < lineLength; i++)
                placeHolder.append(".");

            /*
             * The below loop selects three lines from the file and then sends them for
             * checking to the 'findLinePartSum' which returns the sum of valid numbers 
             * from that line
             * This method enable performing the required operations while reading the whole
             * file only once
             */
            while (br.ready() || isLastLine) {
                if (isFirstLine) {
                    isFirstLine = false;
                    requiredLines[0] = placeHolder.toString();
                    requiredLines[1] = firstLine;
                    requiredLines[2] = br.readLine();
                    totalSum += findLinePartSum(requiredLines);
                    continue;
                }
                requiredLines[0] = requiredLines[1];
                requiredLines[1] = requiredLines[2];
                if (isLastLine) {
                    requiredLines[2] = placeHolder.toString();
                    isLastLine = false;
                    totalSum += findLinePartSum(requiredLines);
                    continue;
                } else
                    requiredLines[2] = br.readLine();
                isLastLine = !br.ready();
                totalSum += findLinePartSum(requiredLines);
            }
            System.out.println(totalSum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int findLinePartSum(String[] lineSet) {
        int lineSum = 0, numStart = 0, numEnd = 0;
        for (int i = 0; i < lineSet[1].length(); i++) { // Reading the line
            // Extracting numbers from the line
            if (Character.isDigit(lineSet[1].charAt(i))) {
                numStart = i;
                numEnd = numStart;
                while (numEnd < lineSet[1].length() && Character.isDigit(lineSet[1].charAt(numEnd)))
                    numEnd++;

                // Checking if number is valid according to the criteria provided
                if (isValidNum(lineSet, numStart, numEnd)) {
                    lineSum += Integer.parseInt(lineSet[1].substring(numStart, numEnd));
                }
                i = numEnd;
            }
        }
        return lineSum;
    }

    static boolean isValidNum(String[] lineSet, int numStart, int numEnd) {
        char[] charArr = new char[3];
        if (numStart - 1 >= 0) { // Checking for symbol on Left Side & its corners if they exist
            for (int i = 0; i < charArr.length; i++)
                charArr[i] = lineSet[i].charAt(numStart - 1);
            if (checkIfValid(charArr))
                return true;
        }
        if (numEnd < lineSet[1].length()) { // Checking for symbol on Right Side & its corners if they exist
            for (int i = 0; i < charArr.length; i++)
                charArr[i] = lineSet[i].charAt(numEnd);
            if (checkIfValid(charArr))
                return true;
        }

        // Checking above and below the number digits
        char[] charArr2 = new char[(numEnd - numStart) * 2];
        for (int i = 0, j = 0; i < charArr2.length; i += 2) {
            charArr2[i] = lineSet[0].charAt(numStart + j);
            charArr2[i + 1] = lineSet[2].charAt(numStart + j);
            j++;
        }
        return checkIfValid(charArr2);
    }

    static boolean checkIfValid(char[] charArr) {
        boolean isValid = false;
        for (int i = 0; i < charArr.length; i++) {
            // If any character meets the criteria, then return true
            isValid = isValid || (charArr[i] != '.' && !Character.isDigit(charArr[i]));
            if (isValid)
                return isValid;
        }
        return isValid;
    }
}
