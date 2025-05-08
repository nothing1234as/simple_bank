import java.util.Scanner;
public class BankMain{
     
    

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    Bank bank = new Bank();
    
    boolean running = true;
    while (running) {
        System.out.println("Enter your choice:\n"                 
                                               +"1. Sign up\n"
                                               +"2. Sign in\n" 
                                               +"3. Show all Costumers\n" 
                                               +"4.total money 5.sorted money\n" 
                                               +"6.search customers by NC\n" 
                                               +"7.Show all customer info\n" 
                                               +"8.Exit");
        String choice = scanner.nextLine();
    
         

        switch (choice) {
            case "1": 
                bank.signUp();
                break;

            case "2": 
                bank.signIn();
                break;

            case "3": 
                bank.Allcustomers();
                break;
            case "4":
                bank.totalMoney();
                break;
            
            case "5":
               bank.sort();  
               break;
            
            case "6":
                System.out.println("enter customer national code: ");
                String nc = scanner.nextLine();
                bank.search(nc);
                break;
            
            case "7":
                 System.out.println("enter customer national code: ");
                 String nationCode = scanner.nextLine();
                 bank.allInfo(nationCode);
                 break;
               
            case "8": 
                running = false;
                System.out.println("Exiting...");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }
    }
    scanner.close();
   
}

    

}