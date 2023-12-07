import java.io.FileReader;
import java.io.BufferedReader;

public class raceReader2 {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("puzzleInput.txt"))) {
            long raceTime = stringToNum(br.readLine());
            long raceDistRecord = stringToNum(br.readLine());
            long numOfWinWays = calcWinWays(raceTime, raceDistRecord);
            System.out.println(numOfWinWays);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static long stringToNum(String str) {
        StringBuffer strBuffer = new StringBuffer();
        for(int i = 0; i < str.length(); i++) {
            if(Character.isDigit(str.charAt(i))) {
                strBuffer.append(str.charAt(i));
            }
        }
        return Long.parseLong(strBuffer.toString());
    }

    static long calcWinWays(long raceTime, long raceDistRecord) {
        long numWays = 0;
        for(long i = 0; i < raceTime; i++) {
            long start = 0;
            long end = raceTime;
            while(start <= end) {
                long mid = start + (end - start) / 2;
                long distCovered = calcDist(mid, raceTime);
                if(distCovered <= raceDistRecord) start = mid + 1;
                else end = mid - 1;
            }
            numWays = raceTime - 2 * start + 1;
        }
        return numWays;
    }

    static long calcDist(long speed, long totalTime) {
        return speed * (totalTime - speed);
    }
}
