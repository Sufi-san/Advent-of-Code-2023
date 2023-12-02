import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.StringBuffer;
// import java.util.Arrays;

public class gameReader2 {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("gamelist.txt"))) {
            int cubeSetPowerSum = 0;
            StringBuffer line = new StringBuffer();
            while(br.ready()) {
                line.replace(0, line.length(), br.readLine());
                // Splitting the String into a String Array with 'empty space' as regex
                String[] gameArray = line.toString().split(" ");
                cubeSetPowerSum += findCubeSetPower(gameArray);
            }
            System.out.println(cubeSetPowerSum);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int findCubeSetPower(String[] gameArray) {
        // Defining variables to store maximum number of cubes required to fulfill game requirements
        int redMax = Integer.MIN_VALUE, greenMax = redMax, blueMax = redMax;

        // Using logic similar to part 1
        for(int i = 2; i < gameArray.length; i+=2) {
            int value = Integer.parseInt(gameArray[i]);
            
            // Checking whether encountered number of cubes for a color is the greatest thus far
            if(gameArray[i + 1].contains("red")) redMax = Math.max(redMax, value);
            else if(gameArray[i + 1].contains("green")) greenMax = Math.max(greenMax, value);
            else blueMax = Math.max(blueMax, value);
        }

        // As power of set is product of required number of cubes we return value of below expression
        return redMax * greenMax * blueMax;
    }
}