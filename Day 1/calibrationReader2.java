import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuffer;
import java.util.HashMap;

public class calibrationReader2 {
    public static void main(String[] args) {
        int sumOfAll = 0;

        // Using BufferedReader to utilise the .readLine() method
        // Using FileReader to read the File
        try(BufferedReader br = new BufferedReader(new FileReader("calibrationFile.txt"))) {

            // StringBuffer to utilise the memory assigned to old String reference again
            StringBuffer line = new StringBuffer(""); 
            while(br.ready()) {
                line.replace(0, line.length(), br.readLine());
                sumOfAll += getCalibValue(line);
                System.out.println(line + ", Calibration Value: " + getCalibValue(line));
            }
            System.out.println(sumOfAll);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    static int getCalibValue(StringBuffer calibString) {
        // Creating a HashMap and mapping numerical values to their spelled counterpart
        HashMap<String, Integer> numMap = new HashMap<>();
        numMap.put("zero", 0);
        numMap.put("one", 1);
        numMap.put("two", 2);
        numMap.put("three", 3);
        numMap.put("four", 4);
        numMap.put("five", 5);
        numMap.put("six", 6);
        numMap.put("seven", 7);
        numMap.put("eight", 8);
        numMap.put("nine", 9);

        int firstDigit = findFirstDigit(calibString, numMap); // gets the digit at tens place
        int lastDigit = findLastDigit(calibString, numMap); // gets the digit at units place

        return firstDigit * 10 + lastDigit; // returns the actual calibration value
    }

    static int findFirstDigit(StringBuffer calibString, HashMap<String, Integer> numMap) {
        int start = 0, end = 0; // loading both pointers at beginning of String
        while(start < calibString.length()) {
            // If numeric value is encountered at start pointer, return it.
            if(Character.isDigit(calibString.charAt(start))) return Integer.parseInt(Character.toString(calibString.charAt(start)));

            // If the word formed between the pointer indexes is a number-spelling, return it
            StringBuffer word = new StringBuffer(calibString.substring(start, end + 1));
            if(numMap.containsKey(word.toString())) return numMap.get(word.toString());

            // As no number spelling between 0 to 9 exceeds 5 characters, we can reset pointers
            if(end - start + 1 == 5) {
                start++;
                end = start;
            }

            // If end pointer is at boundary, only start should increment.
            // Else increment end pointer
            if(end == calibString.length() - 1) start++;
            else end++;
        }
        return -1;
    }

    static int findLastDigit(StringBuffer calibString, HashMap<String, Integer> numMap) {
        int start = calibString.length() - 1, end = start; // loading pointers at end of String
        while(end >= 0) {
            // If numeric value is encountered at end pointer, return it.
            if(Character.isDigit(calibString.charAt(end))) return Integer.parseInt(Character.toString(calibString.charAt(end)));

            // If the word formed between the pointer indexes is a number-spelling, return it
            StringBuffer word = new StringBuffer(calibString.substring(start, end + 1));
            if(numMap.containsKey(word.toString())) return numMap.get(word.toString());

            // As no number spelling between 0 to 9 exceeds 5 characters, we can reset pointers
            if(end - start + 1 == 5) {
                end--;
                start = end;
            }

            // If start pointer is at boundary, only end should decrement.
            // Else decrement start pointer.
            if(start == 0) end--;
            else start--;
        }
        return -1;
    }
}