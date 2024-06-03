import org.apache.commons.lang3.math.Fraction;

public class Terminator2 {

    public static void main(String[] args) {
        Fraction fraction1= Fraction.getFraction(1,2);
        Fraction fraction2=Fraction.getFraction(2,3);
        addFraction(fraction1,fraction2);

    }

    public static Fraction addFraction(Fraction fraction1, Fraction fraction2){
            int newDenominator=fraction1.getDenominator()*fraction2.getDenominator();
            int fraction1numerator=fraction1.getNumerator()*fraction2.getDenominator();
            int fraction2numerator=fraction2.getNumerator()*fraction1.getDenominator();

            int newNumerator=fraction1numerator+fraction2numerator;

            Fraction result= Fraction.getFraction(newNumerator,newDenominator);
        System.out.println(result);
        return result;
        }
}
