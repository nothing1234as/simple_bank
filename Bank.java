import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;




public class Bank {
    
    final static int  cusCount = 10;
    static ArrayList<Customer>  clients = new ArrayList<>();
    static ArrayList<Cashier> cashiers = new ArrayList<>();
    
    
    public void signUp(){
        
      Scanner scanner = new Scanner(System.in);
      System.out.println("1.customer sign up 2.cashier sign up");
      int op1 = scanner.nextInt();
      if(op1 == 1){
        try{
            FileOutputStream f = new FileOutputStream("customers.dat" , true);
            DataOutputStream w = new DataOutputStream(f);
            w.close();
            }
            catch(IOException e){}
            try{
                FileInputStream f1 = new FileInputStream("customers.dat");
                DataInputStream r = new DataInputStream(f1);
                
                while(r.available()>0){

                    String str = r.readUTF();
                    String[] myArray = str.split("\\|");
                   
                    myArray[3] = myArray[3].stripTrailing(); //remove \n
                   
                    clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
                    }
                r.close();
        
                }
                catch(IOException e){}
      if (clients.size() < cusCount){
        
       
                System.out.println("Enter your national code:");
                scanner.nextLine();
                String nationalCode = scanner.nextLine(); 
                
                System.out.println("enter your password: ");
                String password = scanner.nextLine();
       
                System.out.println("Enter your name:");
                String name = scanner.nextLine();
                
                System.out.println("Enter your birth date (DDMMYYYY):");
                String birthDate = scanner.nextLine(); 
                
                try {
                    FileOutputStream f = new FileOutputStream("customers.dat" , true);
                    DataOutputStream w = new DataOutputStream(f);
                    w.writeUTF(nationalCode+"|"+name+"|"+birthDate+"|"+password+"\n");
                    w.close();
                } catch (IOException e) {}
                
                System.out.println("Operation done!"); 
                clients.clear();
      }
      else{
        System.out.println("we can't add new customer");
      }
    }
    if(op1 == 2){
        
        System.out.println("enter your national code: ");
        scanner.nextLine();
        String nc = scanner.nextLine();

        System.out.println("enter your password: ");
        String pass = scanner.nextLine();

        try{
            FileOutputStream f2 = new FileOutputStream("cashiers.dat" , true);
            DataOutputStream w2 = new DataOutputStream(f2);
            w2.writeUTF(pass+"|"+nc+"\n");
            w2.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        System.out.println("operation done!");
        
    }
                
    }

    public static void signIn(){
        
        
        Scanner scanner = new Scanner(System.in);
        
        int flag = 0;
        System.out.println("1.customer login  2.cashier login");
        int op2 = scanner.nextInt();
        if(op2 == 1){
            try {
                FileInputStream f = new FileInputStream("customers.dat");
                DataInputStream r = new DataInputStream(f);
                
                while(r.available() > 0){

                    String str = r.readUTF();
                     String[] myArray = str.split("\\|");
                     myArray[3] = myArray[3].stripTrailing();
                     clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
                }
            } catch (IOException e) {}
        if(clients.isEmpty() == false){
            System.out.println("please enter your national code:");
            scanner.nextLine();
            String NC = scanner.nextLine();
            for (Customer customer : clients){
                if(customer.Nation().equals(NC)){
                   // System.out.println("Welcome back sir!");
                    //customer.menu();
                    customer.logIn();
                    flag += 1;
                    clients.clear();
                    break;
                }
              
            }
            if(flag == 0){
                System.out.println("your input is not correct");
            }
            
        }
        else{
            System.out.println("there is no customer");
        }
    }
    if(op2 == 2){

        try {
            FileInputStream f2 = new FileInputStream("cashiers.dat");
            DataInputStream r2 = new DataInputStream(f2);
            String line2;
            while(r2.available() > 0){
                String str = r2.readUTF();
                   String[] myArray = str.split("\\|");
                   myArray[1] = myArray[1].stripTrailing();
                   cashiers.add(new Cashier(myArray[0], myArray[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       if(cashiers.isEmpty() == false){
           System.out.println("enter your national code: ");
           scanner.nextLine();
           String nc = scanner.nextLine();
           int flag1 = 0;
           for(Cashier cashier: cashiers){
               if(cashier.Nation().equals(nc)){
                cashier.logIn();
                cashiers.clear();
                flag1 = 1;
                break;
               }
           }
           if(flag1 == 0){
              System.out.println("national code is undefined!");
           }
       } 
    }
    
        
    }

    public void Allcustomers(){

        try {
            FileInputStream f = new FileInputStream("customers.dat");
            DataInputStream r = new DataInputStream(f);
            String line;
            while(r.available()>0){
                String str = r.readUTF();
                 String[] myArray = str.split("\\|");
                 myArray[3] = myArray[3].stripTrailing();
                 clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
            }
        } catch (IOException e) {}
        
        System.out.println("All customers:");
                for (Customer customer : clients) {
                    System.out.println(customer.showInfo());
                }
        clients.clear();
    }

    public  void search(String NC){

        try {
            FileInputStream f = new FileInputStream("customers.dat");
            DataInputStream r = new DataInputStream(f);
            String line;
            while(r.available()>0){
                String str = r.readUTF();
                 String[] myArray = str.split("\\|");
                 myArray[3] = myArray[3].stripTrailing();
                 clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
            }
        } catch (IOException e) {}

        int flag = 0;
        for(Customer customer : clients){
            if(customer.Nation().equals(NC)){
               System.out.println(customer.showInfo());
                flag++;
                break;
            }
        }
        if(flag == 0){
            System.out.println("customer not found!");
        }
        clients.clear();
    }

    public void allInfo(String NationCode){

        try {
           FileInputStream f = new FileInputStream("customers.dat");
           DataInputStream r = new DataInputStream(f);
            
            while(r.available()>0){
                String str = r.readUTF();
                 String[] myArray = str.split("\\|");
                 myArray[3] = myArray[3].stripTrailing();
                 clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
            }
        } catch (IOException e) {}

        int flag = 0;
        for(Customer customer : clients){
            if(customer.Nation().equals(NationCode)){
               
               System.out.println(customer.showInfo());
               System.out.println("customer Accounts: ");
               for(Account account : customer.getAccounts()){
                     account.showAll();
               }
               flag++;
               break;
            }
            
        }
       if(flag == 0){
          System.out.println("there is no customer with this national code");
       }

       clients.clear();
    }

    public void totalMoney(){

        try {
            FileInputStream f = new FileInputStream("customers.dat");
            DataInputStream r = new DataInputStream(f);
            
            while(r.available() > 0){
                String line = r.readUTF();
                 String[] myArray = line.split("\\|");
                 myArray[3] = myArray[3].stripTrailing();
                 clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
            }
        } catch (IOException e) {}
        
        int total = 0;

        for (Customer customer : clients) {
            for (Account account : customer.getAccounts()){
                total = total + account.getBalance();
                }
            }
        System.out.println("bank total balance: "+total);
        clients.clear();
    }

    public void sort(){

        try {
            FileInputStream f = new FileInputStream("customers.dat");
           DataInputStream r = new DataInputStream(f);
            
            while(r.available() > 0){
                String line = r.readUTF();
                 String[] myArray = line.split("\\|");
                 myArray[3] = myArray[3].stripTrailing();
                 clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
            }
        } catch (IOException e) {}


        int i , j;
        ArrayList<Integer> sort = new ArrayList<>();
        ArrayList<Integer> finalSort = new ArrayList<>();

        for (Customer customer : clients) {
            sort.add(customer.getAccountBalance());
            }
        

        finalSort = new ArrayList<>(sort);
        Collections.sort(finalSort, Collections.reverseOrder());

        for(i = 0;i < finalSort.size();i++){
            for(j = 0;j < finalSort.size();j++){
                if(finalSort.get(i).equals(sort.get(j))){
                    System.out.println("customer "+j+": total balance = "+sort.get(j));
                    sort.set(j , -1);
                }
            }
        }
        
        clients.clear();
    }


    public static ArrayList<Customer> getClients(){
        
        try {
            FileInputStream f = new FileInputStream("customers.dat");
            DataInputStream r = new DataInputStream(f);
            
            while(r.available() > 0){
                String line = r.readUTF();
                 String[] myArray = line.split("\\|");
                 myArray[3] = myArray[3].stripTrailing();
                 clients.add(new Customer(myArray[0] , myArray[1] , myArray[2] , myArray[3]));
            }
        } catch (IOException e) {}

        ArrayList<Customer> temp = new ArrayList<>(clients);
        clients.clear();
        return temp;
    }
    

}
