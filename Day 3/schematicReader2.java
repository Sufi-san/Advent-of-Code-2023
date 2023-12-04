import java.io.FileReader;
import java.io.BufferedReader;
// import java.util.Arrays;
import java.lang.StringBuffer;

/*
    I have been unable to optimize this code to the best of my abilities, please provide
    suggestions or send 'pull requests' if you know how to optimize the code
*/

public class schematicReader2 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("EngineSchematic.txt"))) {
            int totalRatioSum = 0; // to store the calculated sum of all valid part numbers

            // Creating a String array that will contain the required lines for identifying
            // valid numbers
            String[] requiredLines = new String[3];

            /*
             * The below loop selects three lines from the file and then sends them for
             * checking to the 'findGearRatioSum' which returns the sum of products of
             * numbers
             * adjacent to gear symbol (*)
             * This method enable performing the required operations while reading the whole
             * file only once
             */

            // Unable to fully explain the code using comments, barely solved this one
            // myself :`-)

            boolean firstIteration = true;
            while (br.ready()) {
                if (firstIteration) {
                    firstIteration = false;
                    requiredLines[0] = br.readLine();
                    requiredLines[1] = br.readLine();
                    requiredLines[2] = br.readLine();
                } else {
                    requiredLines[0] = requiredLines[1];
                    requiredLines[1] = requiredLines[2];
                    requiredLines[2] = br.readLine();
                }
                totalRatioSum += findGearRatioSum(requiredLines);
            }
            System.out.println(totalRatioSum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int findGearRatioSum(String[] requiredLines) {
        int gearRatioSum = 0, gearIndex = 0, lastGearIndex = requiredLines[1].lastIndexOf("*");
        if (lastGearIndex < 0)
            return gearRatioSum;
        boolean checkLine = true;
        while (checkLine) {
            boolean twoDigitsInOneLine = false;
            for (int i = gearIndex; i < requiredLines[1].length(); i++) {
                if (requiredLines[1].charAt(i) == '*') {
                    gearIndex = i;
                    break;
                }
            }
            int[][] digitIndices = { { -1, -1 }, { -1, -1 } };
            for (int i = 0; i <= 2; i++) {
                twoDigitsInOneLine = checkIfSpecialCondition(gearIndex, i, requiredLines, digitIndices);
            }
            if (!twoDigitsInOneLine) {
                for (int i = 0; i <= 2; i++) {
                    for (int j = gearIndex - 1; j <= gearIndex + 1; j++) {
                        char chr = requiredLines[i].charAt(j);
                        if (!Character.isDigit(chr))
                            continue;
                        if (digitIndices[0][0] == -1) {
                            digitIndices[0][0] = i;
                            digitIndices[0][1] = j;
                            continue;
                        }
                        if (i != digitIndices[0][0]) {
                            digitIndices[1][0] = i;
                            digitIndices[1][1] = j;
                        }
                    }
                    if (digitIndices[0][0] >= 0 && digitIndices[1][0] >= 0)
                        break;
                }
            }
            if (digitIndices[0][1] >= 0 && digitIndices[1][1] >= 0)
                gearRatioSum += extractGearRatio(digitIndices, requiredLines);
            if (gearIndex == lastGearIndex)
                checkLine = false;
            gearIndex++;
        }
        return gearRatioSum;
    }

    static boolean checkIfSpecialCondition(int gearIndex, int lineIndex, String[] requiredLines, int[][] digitIndices) {
        char chr1 = requiredLines[lineIndex].charAt(gearIndex - 1);
        char chr2 = requiredLines[lineIndex].charAt(gearIndex + 1);
        boolean isNotRepeated = !Character.isDigit(requiredLines[lineIndex].charAt(gearIndex));
        boolean isSpecial = Character.isDigit(chr1) && Character.isDigit(chr2) && isNotRepeated;
        if (isSpecial) {
            digitIndices[0][0] = digitIndices[1][0] = lineIndex;
            digitIndices[0][1] = gearIndex - 1;
            digitIndices[1][1] = gearIndex + 1;
        }
        return isSpecial;
    }

    static int extractGearRatio(int[][] digitIndices, String[] requiredLines) {
        int firstNumber = getExactNumber(digitIndices[0][1], digitIndices[0][0], requiredLines);
        int secondNumber = getExactNumber(digitIndices[1][1], digitIndices[1][0], requiredLines);
        return firstNumber * secondNumber;
    }

    static int getExactNumber(int mid, int rowIndex, String[] requiredLines) {
        int start = mid, end = mid;
        while (start > 0 && Character.isDigit(requiredLines[rowIndex].charAt(start - 1)))
            start--;
        while (end < requiredLines[1].length() && Character.isDigit(requiredLines[rowIndex].charAt(end)))
            end++;
        return Integer.parseInt(requiredLines[rowIndex].substring(start, end));
    }
}
