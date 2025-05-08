import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


class Cashier extends Person {
   private String password;
   private String nationalCode;
   
  ArrayList<Integer> bool = new ArrayList<>();
  
  public Cashier(String password , String nationalCode ){
    this.password = password;
    this.nationalCode = nationalCode;
   }


  @Override 
  public void logIn(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("enter your password: ");
    String pass = scanner.nextLine();
    if(this.password.equals(pass)){
        System.out.println("welcome back cash!");
        permission();
        
    }

  }

  @Override
  public String Nation(){
    return this.nationalCode;
  }

  static void permission(){
      /*try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            while (dis.available() > 0) {
                // خواندن هر رشته به صورت جداگانه
                String transaction = dis.readUTF();
                
                // تقسیم رشته به بخش‌های مختلف
                String[] parts = transaction.split(" \\| ");
                if (parts.length >= 2) {
                    String operation = parts[0].split(": ")[1];
                    String dateTime = parts[1].split(": ")[1];
                    String balance = parts[2].split(": ")[1];
                    
                    System.out.println("Operation: " + operation);
                    System.out.println("Date/Time: " + dateTime);
                    System.out.println("Balance: " + balance);
                    System.out.println("--------------------");
                }
            }
        }*/

        
   
   
        try {
       /*  FileReader f = new FileReader("permission.txt");
        BufferedReader r = new BufferedReader(f);
        String line;
        while((line = r.readLine()) != null){
          String[] myArray = line.split("\\|");
          System.out.println("origin ID:"+myArray[1]);
          System.out.println("aim ID: "+myArray[2]);
          System.out.println("money: "+myArray[3]);
          System.out.println("1.accept 2.deny");
          Scanner scanner = new Scanner(System.in);
          int per = scanner.nextInt();*/

          FileInputStream fis = new FileInputStream("permission.dat");
            DataInputStream dis = new DataInputStream(fis);
            while(dis.available() > 0){
              String acception = dis.readUTF();

              String[] parts = acception.split("\\|");
              System.out.println("origin ID:"+parts[1]);
              System.out.println("aim ID: "+parts[2]);
              System.out.println("money: "+parts[3]);
              
              
              System.out.println("1.accept 2.deny");
              Scanner scanner = new Scanner(System.in);
              int per = scanner.nextInt();
         
         
          FileOutputStream f1 = new FileOutputStream("accept.dat" , true);
          DataOutputStream w1 = new DataOutputStream(f1);
          parts[3] = parts[3].stripTrailing();
          w1.writeUTF(parts[1]+"|"+parts[2]+"|"+parts[3]+"|"+per+"\n");
          w1.close();

          for(Customer customer : Bank.getClients()){
            if(customer.nationalCode.equals(parts[0].stripTrailing())){
                 customer.accept();
            }
          }
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    //clear permission file
    /*try {
        FileWriter f = new FileWriter("permission.txt");
        BufferedWriter r = new BufferedWriter(f);
        r.write("");
        r.close();

      
    } catch (IOException e) {
      e.printStackTrace();
    }*/
    try (FileOutputStream fos = new FileOutputStream("permission.dat")) {
            // فایل به صورت خودکار خالی می‌شود
        }
    catch(IOException e){}
  
  }

  

  



}
        


    

