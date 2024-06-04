import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StringTask {
    public static void main(String[] args) {

        //Zadanie polegało na przerobienie stringa tak, aby słowa były posortowane wielkością liter, a jeżeli mają tyle samo liter to muszą być posortowane alfabetycznie.
        String x ="ghij, def, abc, ghij, klmno";

        String [] clearArray= x.replaceAll(" ", "").split(",");

        List<String> listOfWordsToCheck= Arrays.stream(clearArray).toList();

        List<String> alphabeticalySortedList= listOfWordsToCheck.stream().sorted().toList();

        List <String> doubleSortedList= alphabeticalySortedList.stream().sorted(Comparator.comparingInt(String::length)).toList();

        System.out.println("Sorted " +doubleSortedList);

        //Teraz spróbujemy w drugą stronę

        List <String> reverseOrder= alphabeticalySortedList.stream().sorted(Comparator.comparingInt(String::length)).sorted(Comparator.reverseOrder()).toList();
        System.out.println(reverseOrder);

    }

}
