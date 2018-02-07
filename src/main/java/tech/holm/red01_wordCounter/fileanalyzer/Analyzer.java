package tech.holm.red01_wordCounter.fileanalyzer;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.springframework.web.multipart.MultipartFile;
import tech.holm.red01_wordCounter.fileanalyzer.AnalyticsResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileAnalyzer {

    public ArrayList<AnalyticsResult> analyzeFileForLetters(MultipartFile file) throws IOException {
        System.out.println("Analyzing: " + file.getOriginalFilename());
        System.out.println(file.getSize() + "Bytes " + file.getContentType());

        /*Finder fil typen og analysere den hvis den passer ind i typen*/
        if(file.getContentType().equals("text/plain")){

            Multiset tokenCounter = HashMultiset.create();

            addLetterToMultiset(file, tokenCounter);

            return sortEntries(tokenCounter);

        }
        return new ArrayList<>();
    }

    public ArrayList analyzeFileForWords(MultipartFile file) throws IOException {
        System.out.println("Analyzing: " + file.getOriginalFilename());
        System.out.println(file.getSize() + "Bytes " + file.getContentType());

        /*Finder fil typen og analysere den hvis den passer ind i typen*/
        if(file.getContentType().equals("text/plain")){

            Multiset tokenCounter = HashMultiset.create();

            addWordToMultiset(file, tokenCounter);

            return sortEntries(tokenCounter);
        }
        return new ArrayList<>();
    }

    private ArrayList<AnalyticsResult> sortEntries(Multiset multiset) {

        ArrayList<Multiset.Entry> unSortedEntries = new ArrayList<>();

        for (Object entry : multiset.entrySet()){
            unSortedEntries.add((Multiset.Entry) entry);
        }

        ArrayList<AnalyticsResult> sortedEntries = sortEntryArraylist(unSortedEntries);
        System.out.println(sortedEntries);

        return sortedEntries;
    }

    private ArrayList<AnalyticsResult> sortEntryArraylist(ArrayList<Multiset.Entry> unSortedEntries) {
        ArrayList<Multiset.Entry> sortedEntries = new ArrayList<>();
        while(!unSortedEntries.isEmpty()){
            Multiset.Entry finalEntry = null;

            for (Multiset.Entry entry : unSortedEntries){
                if(finalEntry == null){
                    finalEntry = entry;
                } else {
                    if(finalEntry.getCount() < entry.getCount() ){
                        finalEntry = entry;
                    }
                }
            }

            sortedEntries.add(finalEntry);

            unSortedEntries.remove(finalEntry);

        }

        ArrayList<AnalyticsResult> analyticsResults = new ArrayList<AnalyticsResult>();

        Double total = new Double(0);
        for(Multiset.Entry e : sortedEntries){
            analyticsResults.add(new AnalyticsResult((String) e.getElement(), e.getCount()));
            total = total.doubleValue() + e.getCount();
        }

        for(AnalyticsResult result : analyticsResults){
            result.calculateTokenPercentage(total);
        }

        return analyticsResults;
    }

    private void addWordToMultiset(MultipartFile file, Multiset tokenCounter) throws IOException {
        Scanner scanner = getWordScanner(file);
        addToMultiset(scanner, tokenCounter);
    }

    private void addLetterToMultiset(MultipartFile file, Multiset tokenCounter) throws IOException {
        Scanner scanner = getLetterScanner(file);
        addToMultiset(scanner, tokenCounter);
    }

    private void addToMultiset(Scanner scanner, Multiset tokenCounter) {
        while (scanner.hasNext()){
            String token = scanner.next();

            token = token.toUpperCase();
            token = token.replaceAll("[^A-Z]","");
            if(!token.isEmpty()){
                tokenCounter.add(token);
            }


        }

        //prints final set
        System.out.println(tokenCounter.elementSet());
    }

    private Scanner getLetterScanner(MultipartFile file) throws IOException {
        Scanner scanner = new Scanner(file.getInputStream());
        scanner.useDelimiter("");
        return scanner;
    }

    private Scanner getWordScanner(MultipartFile file) throws IOException {
        Scanner scanner = new Scanner(file.getInputStream());
        return scanner;
    }
}
