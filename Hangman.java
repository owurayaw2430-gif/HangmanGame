import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static void main(String[] args) {
        // Objects created from classes
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Word bank and game settings
        String[] words = {"JAVA", "COMPUTER", "KEYBOARD", "PROGRAM", "VARIABLE",
                "LOOP", "STRING", "OBJECT", "CLASS", "METHOD"};
        final int MAX_WRONG = 6;

        System.out.println("=================================");
        System.out.println("      WELCOME TO HANGMAN!");
        System.out.println("=================================");

        String playAgain = "Y";

        while (playAgain.equals("Y")) {

            // Random class picks the secret word
            String secret = words[random.nextInt(words.length)];

            String guessedLetters = "";   // every letter the player has tried
            String wrongLetters = "";     // only the incorrect letters
            int wrongCount = 0;
            boolean won = false;

            // Main game loop: runs until the player wins or runs out of attempts
            while (wrongCount < MAX_WRONG && !won) {

                // Build the hidden word display using _ for unguessed letters
                String display = "";
                int revealed = 0;
                for (int i = 0; i < secret.length(); i++) {
                    char letter = secret.charAt(i);
                    if (guessedLetters.indexOf(letter) >= 0) {
                        display += letter + " ";
                        revealed++;
                    } else {
                        display += "_ ";
                    }
                }

                // Check for a win before asking for another guess
                if (revealed == secret.length()) {
                    won = true;
                    break;
                }

                // Math methods: Math.max keeps attempts from going below 0,
                // Math.round turns progress into a whole-number percent
                int remaining = Math.max(0, MAX_WRONG - wrongCount);
                long percent = Math.round((double) revealed / secret.length() * 100);

                System.out.println();
                System.out.printf("Word: %s (%d letters)%n", display, secret.length());
                System.out.printf("Progress: %d%%%n", percent);
                System.out.printf("Wrong guesses: %s%n", wrongLetters.equals("") ? "none" : wrongLetters);
                System.out.printf("Attempts remaining: %d of %d%n", remaining, MAX_WRONG);
                System.out.print("Guess a letter: ");

                String input = scanner.nextLine().trim().toUpperCase();

                // Validate the input
                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Please enter exactly ONE letter.");
                } else if (guessedLetters.indexOf(input) >= 0) {
                    System.out.printf("You already guessed '%s'. Try another letter.%n", input);
                } else {
                    guessedLetters += input;

                    if (secret.indexOf(input) >= 0) {
                        System.out.printf("Good job! '%s' is in the word.%n", input);
                    } else {
                        wrongCount++;
                        wrongLetters += input + " ";
                        System.out.printf("Sorry, '%s' is not in the word.%n", input);
                    }
                }
            }

            // Show the result
            System.out.println();
            if (won) {
                System.out.printf("You win! The word was %s.%n", secret);
                System.out.printf("You made %d wrong guess(es).%n", wrongCount);
            } else {
                System.out.printf("Game over! The word was %s.%n", secret);
            }

            // Play again?
            System.out.print("\nPlay again? (Y/N): ");
            playAgain = scanner.nextLine().trim().toUpperCase();
            if (playAgain.length() > 0) {
                playAgain = playAgain.substring(0, 1);   // only look at first character
            }
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}