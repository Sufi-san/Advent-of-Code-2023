import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.StringBuffer;
// import java.util.Arrays;
import java.util.HashMap;

public class gameReader {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("gamelist.txt"))) {
            int validGameIDSum = 0;
            StringBuffer line = new StringBuffer();
            while(br.ready()) {
                line.replace(0, line.length(), br.readLine());
                // Splitting the String into a String Array with 'empty space' as regex
                String[] gameArray = line.toString().split(" ");
                validGameIDSum += checkIfValidGame(gameArray);
            }
            System.out.println(validGameIDSum);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int checkIfValidGame(String[] gameArray) {
        // Retrieving Game ID from the String Array
        int gameID = Integer.parseInt(gameArray[1].substring(0, gameArray[1].length() - 1));
        boolean validGame = true; // boolean to check if Game is Valid

        // Storing Maximum Cube Limit for each color in HashMap:
        HashMap<String, Integer> colorLimit = new HashMap<>();
        colorLimit.put("red", 12);
        colorLimit.put("green", 13);
        colorLimit.put("blue", 14);

        // Filtering the Color names inside the array: (Removing commas and colons)
        for(int i = 3; i < gameArray.length; i+=2) {
            if(gameArray[i].contains("red")) gameArray[i] = "red";
            else if(gameArray[i].contains("green")) gameArray[i] = "green";
            else gameArray[i] = "blue";
        }

        // The color of cube whose count a numerical value represents is consequent to it
        // Using above fact, we check whether the number of cubes of each color are not over the limit
        for(int i = 2; i < gameArray.length; i+=2) {
            if(Integer.parseInt(gameArray[i]) > colorLimit.get(gameArray[i + 1])) validGame = false;
        }

        // Return the Game ID for summation if it is valid
        return (validGame)? gameID: 0;
    }
}