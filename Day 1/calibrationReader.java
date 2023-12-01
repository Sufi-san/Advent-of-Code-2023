import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuffer;

public class calibrationReader {
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
        int start = 0, end = calibString.length() - 1; // pointers at start and end

        // looping from both ends till digit found:
        while(!Character.isDigit(calibString.charAt(start))) start++;
        while(!Character.isDigit(calibString.charAt(end))) end--;

        // parsing Integer from obtained digit character
        int firstDigit = Integer.parseInt(Character.toString(calibString.charAt(start)));
        int lastDigit = Integer.parseInt(Character.toString(calibString.charAt(end)));

        return firstDigit * 10 + lastDigit;
    }
}