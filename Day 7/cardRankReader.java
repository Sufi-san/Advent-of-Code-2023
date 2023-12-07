import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class cardRankReader {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("puzzleInput.txt"))) {
            ArrayList<String[]> cardCollection = new ArrayList<>();

            HashMap<Character, Integer> charValues = new HashMap<>();
            charValues.put('A', 14);
            charValues.put('K', 13);
            charValues.put('Q', 12);
            charValues.put('J', 11);
            charValues.put('T', 10);
            for (int i = 9; i > 1; i--) {
                charValues.put(Integer.toString(i).charAt(0), i);
            }

            cardCollection.add(br.readLine().split(" "));
            while (br.ready()) {
                String[] cardItem = br.readLine().split(" ");
                for (int i = 0; i < cardCollection.size(); i++) {
                    if (newCardIsSmaller(cardItem[0], cardCollection.get(i)[0], charValues)) {
                        cardCollection.add(i, cardItem);
                        break;
                    }
                    if (i == cardCollection.size() - 1) {
                        cardCollection.add(cardItem);
                        break;
                    }
                }
            }
            int totalWinnings = 0;
            for (int i = 0; i < cardCollection.size(); i++) {
                totalWinnings += (i + 1) * Integer.parseInt(cardCollection.get(i)[1]);
            }
            System.out.println(totalWinnings);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean newCardIsSmaller(String newCard, String listCard, HashMap<Character, Integer> charValues) {
        int newCardVal = getCardValue(newCard), listCardVal = getCardValue(listCard);
        if (newCardVal == listCardVal) {
            for (int i = 0; i < newCard.length(); i++) {
                int newCardCharVal = charValues.get(newCard.charAt(i));
                int listCardCharVal = charValues.get(listCard.charAt(i));
                if (newCardCharVal == listCardCharVal)
                    continue;
                return newCardCharVal < listCardCharVal;
            }
            return true;
        }
        return newCardVal < listCardVal;
    }

    static int getCardValue(String card) {
        HashMap<Character, Integer> cardKeys = new HashMap<>();
        for (int i = 0; i < card.length(); i++) {
            if (cardKeys.containsKey(card.charAt(i))) {
                cardKeys.put(card.charAt(i), cardKeys.get(card.charAt(i)) + 1);
                continue;
            }
            cardKeys.put(card.charAt(i), 1);
        }
        int mapLength = cardKeys.size();
        if (mapLength == 5 || mapLength == 4)
            return 6 - mapLength;
        if (mapLength == 1)
            return 7;
        if (mapLength == 2) {
            if (cardKeys.values().contains(4))
                return 6;
            return 5;
        }
        if (cardKeys.values().contains(3))
            return 4;
        return 3;
    }
}