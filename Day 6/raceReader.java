import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class raceReader {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("puzzleInput.txt"))) {
            ArrayList<Integer> raceTimes = new ArrayList<>();
            stringArrToIntAL(br.readLine().split(":")[1].split(" "), raceTimes);

            ArrayList<Integer> raceDistRecords = new ArrayList<>();
            stringArrToIntAL(br.readLine().split(":")[1].split(" "), raceDistRecords);

            int productOfWinWays = calcWinsProduct(raceTimes, raceDistRecords);
            System.out.println(productOfWinWays);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void stringArrToIntAL(String[] strArr, ArrayList<Integer> list) {
        for(int i = 0; i < strArr.length; i++) {
            if(strArr[i].trim().length() == 0) continue;
            list.add(Integer.parseInt(strArr[i].trim()));
        }
    }

    static int calcWinsProduct(ArrayList<Integer> raceTimes, ArrayList<Integer> raceDistRecords) {
        int product = 1;
        for(int i = 0; i < raceTimes.size(); i++) {
            int start = 0;
            int end = raceTimes.get(i);
            while(start <= end) {
                int mid = start + (end - start) / 2;
                int distCovered = calcDist(mid, raceTimes.get(i));
                if(distCovered <= raceDistRecords.get(i)) start = mid + 1;
                else end = mid - 1;
            }
            int numWays = raceTimes.get(i) - 2 * start + 1;
            product *= numWays;
        }
        return product;
    }

    static int calcDist(int speed, int totalTime) {
        return speed * (totalTime - speed);
    }
}
