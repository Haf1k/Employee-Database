import java.util.InputMismatchException;
import java.util.Scanner;

public class Tests {
	// Testovani zda bylo zadano cele cislo
		static int number(Scanner sc) {
			int number = 0;
			try {
				number = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.print("Zadavejte pouze cela cisla.");
				sc.nextLine();
				number = number(sc);
			}
			return number;
		}
}
