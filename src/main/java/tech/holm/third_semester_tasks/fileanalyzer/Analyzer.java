package tech.holm.third_semester_tasks.fileanalyzer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Analyzer {

    public ArrayList<AnalyticsResult> analyzeFileForLetters(MultipartFile file) throws IOException {
        System.out.println("Analyzing: " + file.getOriginalFilename());
        System.out.println(file.getSize() + "Bytes " + file.getContentType());

        /*Finder fil typen og analysere den hvis den passer ind i typen*/
        if(file.getContentType().equals("text/plain")){

            Multiset tokenCounter = HashMultiset.create();

            addLetterToMultisetFromFile(file, tokenCounter);

            return sortEntries(tokenCounter);

        }
        return new ArrayList<>();
    }

    public ArrayList<AnalyticsResult> analyzeUrlForLetters(String url) throws IOException {
        System.out.println("Analyzing: " + url);

        Multiset tokenCounter = HashMultiset.create();

        addLetterToMultisetFromUrl(url, tokenCounter);

        return sortEntries(tokenCounter);
    }

    public ArrayList analyzeFileForWords(MultipartFile file) throws IOException {
        System.out.println("Analyzing: " + file.getOriginalFilename());
        System.out.println(file.getSize() + "Bytes " + file.getContentType());

        /*Finder fil typen og analysere den hvis den passer ind i typen*/
        if(file.getContentType().equals("text/plain")){

            Multiset tokenCounter = HashMultiset.create();

            addWordToMultisetFromFile(file, tokenCounter);

            return sortEntries(tokenCounter);
        }
        return new ArrayList<>();
    }

    private ArrayList<AnalyticsResult> sortEntries(Multiset multiset) {

        ArrayList<Multiset.Entry> unSortedEntries = new ArrayList<>();

        for (Object entry : multiset.entrySet()){
            unSortedEntries.add((Multiset.Entry) entry);
        }

        //notice the method!
        ArrayList<AnalyticsResult> sortedEntries = sortUnsortedEntries(unSortedEntries);
        System.out.println(sortedEntries);

        return sortedEntries;
    }

    private ArrayList<AnalyticsResult> sortUnsortedEntries(ArrayList<Multiset.Entry> unSortedEntries) {
        //getting ready to sort entries
        ArrayList<Multiset.Entry> sortedEntries = new ArrayList<>();

        //sort all the unsorted entries
        while(!unSortedEntries.isEmpty()){

            Multiset.Entry finalEntry = null;
            for (Multiset.Entry entry : unSortedEntries){
                //first entry
                if(finalEntry == null){
                    finalEntry = entry;
                    //the rest
                } else {
                    //is this entry the biggest?
                    if(finalEntry.getCount() < entry.getCount() ){
                        finalEntry = entry;
                    }
                }
            }
            //when done transfer the final and biggest entry to sorted
            sortedEntries.add(finalEntry);
            unSortedEntries.remove(finalEntry);
        }

        //when done sorting, convert the results to our custom datatype for easier data retrieval
        ArrayList<AnalyticsResult> analyticsResults = new ArrayList<AnalyticsResult>();

        Double total = new Double(0);
        //create Analytics results, and count the total amount of tokens for percentage calculation
        for(Multiset.Entry e : sortedEntries){
            analyticsResults.add(new AnalyticsResult((String) e.getElement(), e.getCount()));
            total = total.doubleValue() + e.getCount();
        }

        //percentage calculation based on the total amount of tokens
        for(AnalyticsResult result : analyticsResults){
            result.calculateTokenPercentage(total);
        }

        //done
        return analyticsResults;
    }



    private void addWordToMultisetFromFile(MultipartFile file, Multiset tokenCounter) throws IOException {
        //make the scanner
        Scanner scanner = getWordScanner(file);
        addToMultiset(scanner, tokenCounter);
    }

    private Scanner getWordScanner(MultipartFile file) throws IOException {
        Scanner scanner = new Scanner(file.getInputStream());
        return scanner;
    }



    private void addLetterToMultisetFromFile(MultipartFile file, Multiset tokenCounter) throws IOException {
        Scanner scanner = getLetterScanner(file.getInputStream());
        addToMultiset(scanner, tokenCounter);
    }

    private void addLetterToMultisetFromUrl(String urlString, Multiset tokenCounter) throws IOException {
        URL url = new URL(urlString);
        Scanner scanner = getLetterScanner(url.openStream());
        addToMultiset(scanner, tokenCounter);
    }

    private Scanner getLetterScanner(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("");
        return scanner;
    }


    private void addToMultiset(Scanner scanner, Multiset tokenCounter) {
        while (scanner.hasNext()){
            //get token
            String token = scanner.next();

            //scrape off all unnecessary
            token = token.toUpperCase();
            token = token.replaceAll("[^A-Z]","");

            //if theres still something left add it to the multiset
            if(!token.isEmpty()){
                tokenCounter.add(token);
            }
        }
    }




}
