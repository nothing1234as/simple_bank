
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account{
    private int balance;
    private int ID;
    
    
    
    public Account(int balance , int ID){
        this.balance = balance;
        this.ID = ID;
    }

    public int Info(){
        return ID;
    }

    public void showAll(){
        System.out.println("account{balance: "+balance+"| ID: "+ID+"}");
    }
    
    public void check(){
        System.out.println("your account balance is"+balance);
    }

    public boolean withdraw(int amount , String nationalCode){
      if(balance >= amount){
       balance = balance - amount;
       
       
       try {
    
    FileOutputStream fileOut = new FileOutputStream("transaction" + nationalCode + ".dat", true);
    DataOutputStream out = new DataOutputStream(fileOut);
    
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");
    String formattedDateTime = now.format(formatter);
    
    System.out.println("operation done.");
    System.out.println("Date and time: " + formattedDateTime);
    System.out.println("your new account balance: " + balance);
    
  
    out.writeUTF("withdraw: " + formattedDateTime + " | current balance: " + balance + "\n");
    out.close();
    } 
    catch(IOException e) {
             e.printStackTrace();   
    }
       
              

       return true;
      }
      else{
        System.out.println("not enough money!");
        return false;
      }

    }

    public void deposit(int amount , String nationalCode) {
        
            balance = balance + amount;
            
            
            try {
    
                FileOutputStream fileOut = new FileOutputStream("transaction" + nationalCode + ".dat", true);
                DataOutputStream out = new DataOutputStream(fileOut);
                
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");
                String formattedDateTime = now.format(formatter);
                
                System.out.println("operation done.");
                System.out.println("Date and time: " + formattedDateTime);
                System.out.println("your new account balance: " + balance);
                
              
                out.writeUTF("withdraw: " + formattedDateTime + " | current balance: " + balance + "\n");
                out.close();
                } 
                catch(IOException e) {
                         e.printStackTrace();   
                }

            
           
            
     }

    public int getBalance(){
        return balance;
    }

    public int getID(){
        return ID;
    }

    //all withdraw and deposit operations in accounts history
    public void getInfo(String nc){
        System.out.println("all transactions");
        try{
        FileInputStream f = new FileInputStream("transaction"+nc+".dat");
        DataInputStream r = new DataInputStream(f);
       
        while(r.available() > 0){
            String line = r.readUTF();
            System.out.println(line);

        }
        }
        catch(IOException e){}

        
       
    }

}