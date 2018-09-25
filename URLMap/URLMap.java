import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.TreeMap;

public class URLMap {

    /*
     * Function to convert timestamp to MM/dd/yyyy format
     */
    private static String convertEpochToDate(final long epoch) {
        final Date date = new Date(epoch);
        final DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        final String formatted = format.format(date);
        return formatted;
    }

    private static long epochCalculations(final String timestamp) {
        long epoch = Long.parseLong(timestamp) * 1000;
        epoch = (long) Math.floor(epoch / (24 * 60 * 60 * 1000));
        epoch = epoch * (24 * 60 * 60 * 1000);
        return epoch;
    }

    /*
     * Function to create the Date to URL map Key-Date Value-all the URL hits on
     * that Date
     */

    private static Map<Long, Map<String, Integer>> createDateToURLMap(final String[] inputList) {
        final Map<Long, Map<String, Integer>> dateToURLMap = new TreeMap<>();

        for (final String line : inputList) {
            final String[] lineString = line.split("\\|");
            final long epoch = epochCalculations(lineString[0]);

            Map<String, Integer> urlToCountMap = dateToURLMap.get(epoch);
            if (urlToCountMap == null) {
                urlToCountMap = new HashMap<>();
            }
            Integer count = urlToCountMap.get(lineString[1]);
            if (count == null) {
                count = 0;
            }
            count++;
            urlToCountMap.put(lineString[1], count);
            dateToURLMap.put(epoch, urlToCountMap);
        }
        return dateToURLMap;
    }

    /*
     * Function to display the URL hit count report URLs are listed in the
     * decreasing order of hit count
     */

    private static void generateURLReport(final String[] inputList) {
        final Map<Long, Map<String, Integer>> resultMap = createDateToURLMap(inputList);

        for (final Entry<Long, Map<String, Integer>> e : resultMap.entrySet()) {
            System.out.println(convertEpochToDate(e.getKey()) + " GMT");
            final Map<String, Integer> urlToCountMap = e.getValue();
            final List<Entry<String, Integer>> list = new LinkedList<>(urlToCountMap.entrySet());
            Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (final Entry<String, Integer> en : list) {
                System.out.println(en.getKey() + " " + en.getValue());
            }
        }
    }

    /*
     * Main Function input- File with name input.txt output- url hit count
     * report
     */

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        try {
            final String filename = "input.txt";
            final File f = new File(filename);
            if (f.exists()) {
                String[] inputList = null;
                final List<String> dataList = new ArrayList<>();
                try (final FileInputStream fstream = new FileInputStream(filename);
                     final DataInputStream datastream = new DataInputStream(fstream);
                     final BufferedReader buffer = new BufferedReader(new InputStreamReader(datastream));) {
                    String dataitem;
                    while ((dataitem = buffer.readLine()) != null) {
                        dataList.add(dataitem);
                    }
                    inputList = dataList.toArray(new String[dataList.size()]);
                } catch (final Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                generateURLReport(inputList);
            } else {
                System.out.println("input.txt is not present in current directory");
            }

        } catch (final Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

/*
 * Time Complexity = O(n) where n = number of input lines in the input file
 */