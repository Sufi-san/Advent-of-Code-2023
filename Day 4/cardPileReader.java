import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuffer;
import java.util.Set;
import java.util.HashSet;

public class cardPileReader {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("cardPile.txt"))) {
            int cardPilePointSum = 0;

            StringBuffer listString = new StringBuffer(br.readLine());
            int colonIndex = listString.indexOf(":");
            int seperatorIndex = listString.indexOf("|");

            boolean firstLine = true;
            while (br.ready()) {
                if (!firstLine)
                    listString.replace(0, listString.length(), br.readLine());
                String[] winningNums = listString.substring(colonIndex + 2, seperatorIndex - 1).split(" ");
                String[] ourNums = listString.substring(seperatorIndex + 1, listString.length()).split(" ");
                cardPilePointSum += calcCardPoints(ourNums, winningNums);
                firstLine = false;
            }
            System.out.println(cardPilePointSum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int calcCardPoints(String[] winningNums, String[] ourNums) {
        int totalCardPoint = 0;
        boolean firstMatch = true;
        Set<String> ourNumSet = new HashSet<>();
        StringBuffer listElement = new StringBuffer();
        for (int i = 0; i < ourNums.length; i++) {
            listElement.replace(0, listElement.length(), ourNums[i]);
            if (listElement.toString().trim().length() > 0)
                ourNumSet.add(listElement.toString());
        }
        for (int i = 0; i < winningNums.length; i++) {
            listElement.replace(0, listElement.length(), winningNums[i]);
            if (listElement.toString().trim().length() > 0) {
                if (ourNumSet.contains(listElement.toString().trim())) {
                    if (firstMatch) {
                        firstMatch = false;
                        totalCardPoint++;
                        continue;
                    }
                    totalCardPoint *= 2;
                }
            }
        }
        return totalCardPoint;
    }
}
