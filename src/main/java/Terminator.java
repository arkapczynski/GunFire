import org.apache.commons.lang3.math.Fraction;

public class Terminator {

    public static void main(String[] args) {
        int[]fraction1={1,2};
        int[]fraction2={2,3};
        addFraction(fraction1,fraction2);
        addFraction2("1/2","2/3");
        Fraction frac1= Fraction.getFraction(1,2);
        Fraction frac2=Fraction.getFraction(2,3);
        addFraction3(frac1,frac2);
    }


    public static int[] addFraction(int[] fraction1, int[] fraction2){
        int newDenominator=fraction1[1]*fraction2[1];
        int fraction1numerator=fraction1[0]*fraction2[1];
        int fraction2numerator=fraction2[0]*fraction1[1];

        int newNumerator=fraction1numerator+fraction2numerator;

        System.out.println(newNumerator+"/"+newDenominator);
        int[]result={newNumerator,newDenominator};
        return result;
    }

    public static String addFraction2(String fraction1, String fraction2) {
        String [] frac1=fraction1.split("/");
        String [] frac2=fraction2.split("/");
        int[] fra1={Integer.parseInt(frac1[0]),Integer.parseInt(frac1[1])};
        int[] fra2={Integer.parseInt(frac2[0]),Integer.parseInt(frac2[1])};
        int newDenominator = fra1[1] * fra2[1];
        int fraction1numerator = fra1[0] * fra2[1];
        int fraction2numerator = fra2[0] * fra1[1];
        int newNumerator = fraction1numerator + fraction2numerator;

        String finalResult= (newNumerator+"/"+newDenominator);
        System.out.println(finalResult);
        return finalResult;
    }

    public static Fraction addFraction3(Fraction fraction1, Fraction fraction2){
        int newDenominator=fraction1.getDenominator()*fraction2.getDenominator();
        int fraction1numerator=fraction1.getNumerator()*fraction2.getDenominator();
        int fraction2numerator=fraction2.getNumerator()*fraction1.getDenominator();

        int newNumerator=fraction1numerator+fraction2numerator;

        Fraction result= Fraction.getFraction(newNumerator,newDenominator);
        System.out.println(result);
        return result;
    }
}
