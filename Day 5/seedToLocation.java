import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class seedToLocation {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("puzzleInput.txt"))) {
            StringBuffer listString = new StringBuffer(br.readLine());
            int colonIndex = listString.indexOf(":");

            ArrayList<Long> inpList = new ArrayList<>();
            strArrToLongAL(listString.substring(colonIndex + 2, listString.length()).split(" "), inpList);

            ArrayList<long[]> convLists = new ArrayList<>();
            int[] convListIndices = new int[7];
            int convListIndex = 0;

            br.readLine(); // Reading the second empty line
            while(br.ready()) {
                System.out.println(listString);
                listString.replace(0, listString.length(), br.readLine());
                if(listString.length() == 0) continue;
                if(listString.toString().contains(":")){
                    convListIndices[convListIndex] = convLists.size();
                    convListIndex++;
                    continue;
                }
                strArrToLongArr(listString.substring(0, listString.length()).split(" "), convLists);
            }
            for(int i = 0, j = 1; i < convListIndices.length; i++) {
                int startIndex = convListIndices[i];
                int endIndex = (j == convListIndices.length)? convLists.size(): convListIndices[j];
                convertSrcToDest(inpList, convLists, startIndex, endIndex);
                j++;
            }
            System.out.println(java.util.Collections.min(inpList));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void strArrToLongAL(String[] strArr, ArrayList<Long> inpList) {
        for(int i = 0; i < strArr.length; i++) inpList.add(Long.parseLong(strArr[i].trim()));
    }

    static void strArrToLongArr(String[] strArr, ArrayList<long[]> convLists) {
        long[] mapCoords = new long[3];
        for(int i = 0; i < strArr.length; i++) mapCoords[i] = Long.parseLong(strArr[i].trim());
        convLists.add(mapCoords);
    }

    static void convertSrcToDest(ArrayList<Long> inpList, ArrayList<long[]> convLists, int start, int end) {
        for(int i = 0; i < inpList.size(); i++) {
            long input = inpList.get(i);
            for(int j = start; j < end; j++) {
                long source = convLists.get(j)[1], dest = convLists.get(j)[0];
                long rangeCount = convLists.get(j)[2];
                if(input >= source && input < source + rangeCount) {
                    long distFromSource = input - source;
                    inpList.set(i, dest + distFromSource);
                    break;
                }
            }
        }
    }
}
