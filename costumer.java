import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Customer extends Person {
    

    private ArrayList<Account> accounts = new ArrayList<>();
    static final int accCount = 3;
    

    public Customer(String nationalCode, String name, String birthDate , String password) {
        this.nationalCode = nationalCode;
        this.name = name;
        this.birthDate = birthDate;
        this.password = password;
    }
    
    @Override
    public String Nation(){
        return this.nationalCode;
    }

    public void transfer() {
        try {
            
            FileInputStream f = new FileInputStream("accounts-" + this.nationalCode + ".dat");
             DataInputStream r = new DataInputStream(f);
            
            while (r.available() > 0) {
                String line = r.readUTF();
                String[] myArray = line.split("\\|");
                int id = Integer.parseInt(myArray[0]);
                myArray[1] = myArray[1].stripTrailing(); // remove \n
                int balance = Integer.parseInt(myArray[1]);
                accounts.add(new Account(balance, id));
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("how much do you want to transfer: ");
        int money = scanner.nextInt();
    
        System.out.println("enter origin account ID: ");
        int originID = scanner.nextInt();
    
        System.out.println("enter aim account ID: ");
        int aimID = scanner.nextInt();
    
        int find1 = 0;
        
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Account account1 = iterator.next();
            if (originID == account1.getID()) {
                System.out.println("origin account was found.");
                
                
                Iterator<Account> iterator2 = accounts.iterator();
                while (iterator2.hasNext()) {
                    Account account2 = iterator2.next();
                    if (aimID == account2.getID()) {
                        System.out.println("aim account was found.");
                        find1 = 1;
                        
                        if (account1.withdraw(money , this.nationalCode)) {
                            account2.deposit(money , this.nationalCode);
                            newBalance(account1.getID(), account1.getBalance() , this.nationalCode);
                            newBalance(account2.getID(), account2.getBalance() , this.nationalCode); //updating account balance
                            /*try {
                                FileWriter f1 = new FileWriter("transfers.txt", true);
                                BufferedWriter w1 = new BufferedWriter(f1);
                                w1.write("origin account ID: " + originID + "|" + " aim account ID: " + aimID + "| transfer cash: " + money + "\n");
                                w1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                            try {
                                FileOutputStream fileOut = new FileOutputStream("transfers.dat" , true);
                                DataOutputStream out = new DataOutputStream(fileOut);

                                out.writeUTF("origin account ID: " + originID + "|" + " aim account ID: " + aimID + "| transfer cash: " + money + "\n");
                                out.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("operation done!");
                            break;
                        }
                        break;
                    }
                }
            }
        }
    
        if (find1 == 0) {
            boolean found = false;
            
            Iterator<Customer> customerIterator = Bank.getClients().iterator();
            outerLoop:
            while (customerIterator.hasNext() && !found) {
                Customer customer = customerIterator.next();
                Iterator<Account> accountIterator = customer.getAccounts().iterator();
                while (accountIterator.hasNext() && !found) {
                    Account account3 = accountIterator.next();
                    if (account3.getID() == aimID) {
                        System.out.println("aim account was found.");
                        /*try {
                            FileWriter f = new FileWriter("permission.txt", true);
                            BufferedWriter w = new BufferedWriter(f);
                            w.write(this.nationalCode + "|" + originID + "|" + aimID + "|" + money + "\n");
                            w.close();
                            System.out.println("your request send to cashier");
                            found = true; 
                            break outerLoop; 
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        try {
                            FileOutputStream fileOut = new FileOutputStream("permission.dat" , true);
                            DataOutputStream out = new DataOutputStream(fileOut);
                            out.writeUTF(this.nationalCode + "|" + originID + "|" + aimID + "|" + money + "\n");
                            out.close();

                            System.out.println("your request send to cashier");
                            found = true; 
                            break outerLoop;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            
        }
        
        accounts.clear();
    }
   
       
    public void accept(){
        
        
        try{
            /*FileReader f1 = new FileReader("accept.txt");
            BufferedReader r1 = new BufferedReader(f1);
        String line;
        while((line = r1.readLine()) != null){

        String[] parts = line.split("\\|");
        int originID = Integer.parseInt(parts[0]);
        int aimID = Integer.parseInt(parts[1]);
        int money = Integer.parseInt(parts[2]);
        int per = Integer.parseInt(parts[3].stripTrailing());*/
        FileInputStream inp = new FileInputStream("accept.dat");
        DataInputStream Dis = new DataInputStream(inp);
            while(Dis.available() >0){
                String acception = Dis.readUTF();

                String[] parts = acception.split("\\|");
                int originID = Integer.parseInt(parts[0]);
                int aimID = Integer.parseInt(parts[1]);
                int money = Integer.parseInt(parts[2]);
                int per = Integer.parseInt(parts[3].stripTrailing());
     
        if(per == 1){
        for(Customer customer1: Bank.getClients()){
            for(Account account1 : customer1.getAccounts()){
                if(account1.getID() == originID){

                
                for(Customer customer2: Bank.getClients()){
                 for(Account account2 : customer2.getAccounts()){
                 if(account2.getID() == aimID){
                   System.out.println("aim account was found.");
                  
                   
                   if(account1.withdraw(money , this.nationalCode) == true){
                      account2.deposit(money , customer2.nationalCode);
                      newBalance(originID, account1.getBalance() , this.nationalCode);
                      newBalance(aimID, account2.getBalance() , customer2.nationalCode);
                     /*  try{
                      FileWriter f2 = new FileWriter("transfers.txt" , true);
                      BufferedWriter w2 = new BufferedWriter(f2);
                      w2.write("origin account ID: "+originID+"|"+" aim account ID: "+aimID+"| transfer cash: "+money+"\n");
                      w2.close();
                      }
                      catch(IOException e){
                        e.printStackTrace();
                      }*/
                    try {
                        FileOutputStream fileOut = new FileOutputStream("transfers.dat");
                        DataOutputStream out = new DataOutputStream(fileOut);
                        out.writeUTF("origin account ID: "+originID+"|"+" aim account ID: "+aimID+"| transfer cash: "+money+"\n");
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                      
                      System.out.println("operation done!");
                      break; 
                   }
                }
            }
                   }
                
}
        }
    }
}

}
        }
    catch(IOException e){
        e.printStackTrace();
    }
    //clear accept file
    try (FileOutputStream fos = new FileOutputStream("accept.dat")) {
        // فایل به صورت خودکار خالی می‌شود
    }
catch(IOException e){}

}
    
    
     

   
    
    
    
    @Override
    public void logIn(){
        //uploading data from accounts file to accounts arraylist
        
        try{
        FileInputStream f1 = new FileInputStream("accounts-"+this.nationalCode+".dat");
        DataInputStream r = new DataInputStream(f1);
        
        while(r.available() > 0){
            String line = r.readUTF();
            String[] myArray = line.split("\\|");
            int id = Integer.parseInt(myArray[0]);
            myArray[1] = myArray[1].stripTrailing(); //remove \n
            int balance = Integer.parseInt(myArray[1]);
            accounts.add(new  Account(balance , id));
            }
        r.close();

        }
        catch(IOException e){}
    

        Scanner scanner = new Scanner(System.in);
         System.out.println("enter your password");
         String pass = scanner.nextLine();
        if(this.password.equals(pass)){
            System.out.println("welcome back Sir!");
            accounts.clear();
            menu();
        }
        else{
            System.out.println("password was not correct!");
        }

    }

    public int getAccountBalance(){
        try{
            FileInputStream f1 = new FileInputStream("accounts-"+this.nationalCode+".dat");
            DataInputStream r = new DataInputStream(f1);
            
            while(r.available() > 0){
                String line = r.readUTF();
                String[] myArray = line.split("\\|");
                int id = Integer.parseInt(myArray[0]);
                myArray[1] = myArray[1].stripTrailing(); //remove \n
                int balance = Integer.parseInt(myArray[1]);
                accounts.add(new  Account(balance , id));
                }
            r.close();
    
            }
            catch(IOException e){}
        
        int cusTotal = 0;
        for(Account account : accounts){
            cusTotal = cusTotal + account.getBalance();
        }
        accounts.clear();
        return cusTotal;
    }

    public ArrayList<Account> getAccounts(){
        try{
            FileInputStream f1 = new FileInputStream("accounts-"+this.nationalCode+".dat");
            DataInputStream r = new DataInputStream(f1);
            
            while(r.available() > 0){
                String line = r.readUTF();
                String[] myArray = line.split("\\|");
                int id = Integer.parseInt(myArray[0]);
                myArray[1] = myArray[1].stripTrailing(); //remove \n
                int balance = Integer.parseInt(myArray[1]);
                accounts.add(new  Account(balance , id));
                }
            r.close();
    
            }
            catch(IOException e){}
        ArrayList<Account> temp = new ArrayList<>(accounts);//make a temporary copy from accounts
        
        accounts.clear();
        return temp;
    }
    public String showInfo(){
        return "costumer: {natioanl code: "+nationalCode+"| name: "+name+"| birth date: "+birthDate+"}";
    }

    public void newBalance(int id , int changed_Balance , String nc){
        //upload file to a temporary list
        try{
            FileInputStream f= new FileInputStream("accounts-"+nc+".dat");
            DataInputStream reader = new DataInputStream(f);
            List<String> lines = new ArrayList<>();
           
            while (reader.available() > 0) {
                String line = reader.readUTF();
                String[] myArray = line.split("\\|");
                int ID = Integer.parseInt(myArray[0]);
                if (ID == id) {
                    myArray[1] = String.valueOf(changed_Balance);
                    line = String.join("|", myArray);
                }
                lines.add(line);
            }
            reader.close();
            //upload new info to main file
            FileOutputStream f1 =new FileOutputStream("accounts-"+nc+".dat");
            DataOutputStream writer = new DataOutputStream(f1);
            for (String updatedLine : lines) {
                writer.writeUTF(updatedLine+"\n");
                
            }
            writer.close();
        }
            catch(IOException e){}
    
    }
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        
        while (run) { 
            System.out.println("enter your choice: 1.setup account 2.acces to account 3.show all acounts 4.transfer 5.exit");
            int op = scanner.nextInt();
            switch(op) {
                case 1:
                    try {
                        FileOutputStream f = new FileOutputStream("accounts-"+this.nationalCode+".dat", true);
                       DataOutputStream w = new DataOutputStream(f);
                        w.close();
                    } catch(IOException e) {}
                    
                    try {
                        FileInputStream f1 = new FileInputStream("accounts-"+this.nationalCode+".dat");
                        DataInputStream r = new DataInputStream(f1);
                        
                        while(r.available() > 0) {
                            String line = r.readUTF();
                            String[] myArray = line.split("\\|");
                            int id = Integer.parseInt(myArray[0]);
                            myArray[1] = myArray[1].stripTrailing(); //remove \n
                            int balance = Integer.parseInt(myArray[1]);
                            accounts.add(new Account(balance, id));
                        }
                        r.close();
                    } catch(IOException e) {}
                    
                    if(accounts.size() < accCount) {
                        System.out.println("enter your ID: ");
                        int ID = scanner.nextInt();
                
                        System.out.println("enter your balance: ");
                        int balance = scanner.nextInt();
                        String nc = this.nationalCode;
                        
                        try {
                            FileOutputStream f = new FileOutputStream("accounts-"+nc+".dat", true);
                            DataOutputStream w = new DataOutputStream(f);
                            w.writeUTF(ID+"|"+balance+"\n");
                            w.close();
                            System.out.println("operation Done!");
                        } catch(IOException e) {}
                    } else {
                        System.out.println("we can't add new account!");
                    }
                    accounts.clear();
                    break;
                    
                case 2:
                    try {
                        FileInputStream f1 = new FileInputStream("accounts-"+this.nationalCode+".dat");
                        DataInputStream r = new DataInputStream(f1);
                        
                        while(r.available() > 0) {
                            String line = r.readUTF();
                            String[] myArray = line.split("\\|");
                            int id = Integer.parseInt(myArray[0]);
                            myArray[1] = myArray[1].stripTrailing(); //remove \n
                            int balance = Integer.parseInt(myArray[1]);
                            accounts.add(new Account(balance, id));
                        }
                        r.close();
                    } catch(IOException e) {}
                   
                    if(!accounts.isEmpty()) {
                        System.out.println("enter account ID: ");
                        int id = scanner.nextInt();
                        boolean running = true;
                        
                        while(running) {
                            for(Account account : accounts) {
                                if(account.getID() == id) {
                                    System.out.println("enter your choice:\n1.balance check\n2.deposit\n3.withdraw\n4.exit");
                                    int option = scanner.nextInt();
                                    
                                    if(option == 1) {
                                        account.check();
                                    } else if(option == 2) {
                                        System.out.println("enter your deposit: ");
                                        int amount = scanner.nextInt();
                                        account.deposit(amount , this.nationalCode);
                                        newBalance(account.getID(), account.getBalance() ,this.nationalCode);
                                    } else if(option == 3) {
                                        System.out.println("enter your withdraw: ");
                                        int amount = scanner.nextInt();
                                        account.withdraw(amount , this.nationalCode);
                                        newBalance(account.getID(), account.getBalance() , this.nationalCode);
                                    } else if(option == 4) {
                                        running = false; 
                                        System.out.println("EXITING...");
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("there is no account");
                    }
                    accounts.clear();
                    break;
                    
                case 3:
                    try {
                        FileInputStream f1 = new FileInputStream("accounts-"+this.nationalCode+".txt");
                        DataInputStream r = new DataInputStream(f1);
                        
                        while(r.available() > 0) {
                            String line = r.readUTF();
                            String[] myArray = line.split("\\|");
                            int id = Integer.parseInt(myArray[0]);
                            myArray[1] = myArray[1].stripTrailing(); //remove \n
                            int balance = Integer.parseInt(myArray[1]);
                            accounts.add(new Account(balance, id));
                        }
                        r.close();
                    } catch(IOException e) {} 
                    
                    if(!accounts.isEmpty()) {
                        System.out.println("all accounts: ");
                        for(Account account: accounts) {
                            account.showAll();
                        }
                    } else {
                        System.out.println("there is no accounts!");
                    }
                    accounts.clear();
                    break;
                    
                case 4:
                    transfer();
                    break;
                    
                case 5:
                    accounts.clear();
                    run = false;
                    System.out.println("exiting...");
                    break; 
                    
                default: 
                    System.out.println("enter a valid choice!");
            }
        }
    }

}

    
    

    

    


