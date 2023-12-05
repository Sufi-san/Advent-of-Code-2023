import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuffer;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;

public class cardPileReader2 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("cardPile.txt"))) {
            int totalCardInstances = 0;

            StringBuffer listString = new StringBuffer(br.readLine());
            ArrayList<int[]> cardID_Matches = new ArrayList<>();

            boolean firstLine = true;
            while (br.ready()) {
                if (!firstLine)
                    listString.replace(0, listString.length(), br.readLine());
                calcCardMatches(listString, cardID_Matches);
                firstLine = false;
            }
            totalCardInstances = calcAllCardInstances(cardID_Matches);
            System.out.println(totalCardInstances);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void calcCardMatches(StringBuffer listString, ArrayList<int[]> cardID_Matches) {
        int cardsMatched = 0;
        int colonIndex = listString.indexOf(":");
        int seperatorIndex = listString.indexOf("|");

        String[] cardTitleList = listString.substring(0, colonIndex).split(" ");
        int cardID = Integer.parseInt(cardTitleList[cardTitleList.length - 1].trim());
        String[] winningNums = listString.substring(colonIndex + 2, seperatorIndex - 1).split(" ");
        String[] ourNums = listString.substring(seperatorIndex + 1, listString.length()).split(" ");

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
                    cardsMatched++;
                }
            }
        }
        cardID_Matches.add(new int[] { cardID, cardsMatched });
    }

    static int calcAllCardInstances(ArrayList<int[]> cardID_Matches) {
        int totalCardInstances = 0;
        HashMap<Integer, Integer> resultantCards = new HashMap<>();
        for (int i = cardID_Matches.size() - 1; i >= 0; i--) {
            int cardInstances = 0;
            int start = cardID_Matches.get(i)[0], end = start + cardID_Matches.get(i)[1];
            for (int j = start + 1; j <= end; j++) {
                cardInstances += resultantCards.get(j);
            }
            resultantCards.put(start, cardInstances + 1);
        }
        for (int i = 1; i <= cardID_Matches.get(cardID_Matches.size() - 1)[0]; i++) {
            totalCardInstances += resultantCards.get(i);
            System.out.println("Card ID: " + i + " Resultant Cards: " + resultantCards.get(i));
        }
        return totalCardInstances;
    }
}
