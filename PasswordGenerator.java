import java.util.Random;

public class PasswordGenerator {
    public static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String SPECIAL_SYMBOLS = "!?,.;'[]$#@!&*%=-`<>^";
    public static final String NUMBERS = "0123456789";


    //randomizing the characters and numbers
    private final Random random;

    //constructor
    public PasswordGenerator() {
        random = new Random();
    }

    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase, boolean includeSpecialSymbols, boolean includeNumbers) {

        //using a string builder over string for better efficiency

        StringBuilder passwordBuilder = new StringBuilder();
        String validCharacters = "";
        if (includeUppercase) validCharacters += UPPERCASE_CHARACTERS;
        if (includeLowercase) validCharacters += LOWERCASE_CHARACTERS;
        if (includeSpecialSymbols) validCharacters += SPECIAL_SYMBOLS;
        if (includeNumbers) validCharacters += NUMBERS;



        for (int i = 0; i < length; i++) {
        //generate a random index

            int randomIndex = random.nextInt(validCharacters.length());
            char randomChar = validCharacters.charAt(randomIndex);

            //store char into password bvuilder
            passwordBuilder.append(randomChar);


        }
    return passwordBuilder.toString();
    }
}




