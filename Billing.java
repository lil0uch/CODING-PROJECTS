
/**
 *
 * @author jaz
 */

package projects; 
import java.io.BufferedWriter; 
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.PrintWriter; 
import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Paths; 
import java.nio.file.Path; 
import java.util.*; 
public class Billing {
    
    private static final String BILLING_FILE_PATH = "C:\\Users\\\\padil\\Documents\\NetBeansProjects\\projects\\src\\projects\\BillingReceipt.txt";
    private static final String CUSTOMER_FILE_PATH = "C:\\Users\\\\padil\\Documents\\NetBeansProjects\\projects\\src\\projects\\PaymentReceipt.txt";
    private static final String REGISTER_FILE_PATH = "C:\\Users\\\\padil\\Documents\\NetBeansProjects\\projects\\src\\projects\\RegisteredCustomer.txt"; 
    public static void main(String[]args){
        clear(); 
        gotoxy(10,40);
        LoadingScreen(); 
        clear(); 
        Scanner scan = new Scanner(System.in);
        gotoxy(11,50); 
        System.out.println("ELECTRIC BILLING SYSTEM");
        gotoxy(12,50); 
        System.out.println("[1] PAYMENT RECEIPT");
        gotoxy(13,50); 
        System.out.println("[2] BILLING RECEIPT");
        gotoxy(14,50); 
        System.out.println("[3] REGISTER CUSTOMER");
        gotoxy(15,50); 
        System.out.println("[4] SEARCH CUSTOMER");
        gotoxy(16,50); 
        System.out.println("[5] DOWNLOAD TRANSACTION");
        gotoxy(17,50); 
        System.out.println("[6] EXIT");
        gotoxy(18,50); 
        System.out.print("CHOOSE: "); 
        int choice = scan.nextInt();
        
        switch(choice){
            case 1: 
                PaymentReceipt(); 
                break;
            case 2: 
                BillingReceipt();
                break; 
            case 3:
                RegisterCustomer(); 
                break;
            case 4: 
                SearchInterface(); 
                break; 
            case 5: 
                Download(); 
                break; 
            case 6:
                break; 
            default: 
                clear(); 
                gotoxy(19,50); 
                System.out.println("INVALID INPUT");
                gotoxy(20,50); 
                System.out.println("PRESS ANY KEY TO RETURN");
                
                try{
                    System.in.read(); 
                    main(new String[] {}); 
                }catch(IOException e){
                    System.out.println("ERROR OCCURENCE"); 
                }
        } 
    }
    public static void RegisterCustomer(){
        clear(); 
        Scanner scan = new Scanner(System.in);
        gotoxy(8,50); 
        System.out.println("   ENTER NAME");
             gotoxy(11,50); 
        System.out.print("ENTER FIRST NAME: ");
        String firstName = scan.nextLine();
             gotoxy(12,50); 
        System.out.print("ENTER MIDDLE NAME: ");
        String middleName = scan.nextLine();
             gotoxy(13,50); 
        System.out.print("ENTER LAST NAME: ");
        String lastName = scan.nextLine(); 
             gotoxy(14,50); 
        System.out.print("ENTER ADDRESS: "); 
        String address = scan.nextLine(); 
             gotoxy(15,50); 
        System.out.print("ENTER ID: "); 
        String id = scan.nextLine();  
        
        Registration(firstName, middleName, lastName, address, id);
        clear(); 
             gotoxy(10,40);
        LoadingScreen(); 
        clear(); 
            gotoxy(10,50);  
        System.out.println("\nSUCCESSFUllY REGISTERED");
             gotoxy(11,50); 
        System.out.println("\n\nPRESS ANY KEY TO RETURN"); 
        
        try{
           System.in.read();
           clear();
          main(new String[]{}); 
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
    }
    public static void Registration(String firstName, String middleName, String lastName, String address, String id){
         String content = String.format("NAME: %s %s %s | ADDRESS: %s | ID: %s", firstName, middleName, lastName, address, id); 
         try(BufferedWriter writer = new BufferedWriter(new FileWriter(REGISTER_FILE_PATH, true))){
             writer.write(content); 
             writer.newLine();
             
         }catch(IOException e){ 
             System.out.println("ERROR OCCURENCE IN WRITING THE FILE"); 
         }
    }
    public static void PaymentReceipt(){
        clear(); 
        Scanner scan = new Scanner(System.in);
        gotoxy(10,50);
        System.out.print("ENTER ID: ");  
        String id = scan.nextLine();   
         gotoxy(11,50);
        System.out.print("ENTER DATE OF BILL (MM//DD//YYYY): ");
        String date = scan.nextLine();
        String search = SearchBill(id, date);
        if(search != null){
        clear(); 
        gotoxy(12,20);
        System.out.println(search);
        }else{
              gotoxy(13,50);
            System.out.println("INFORMATION DOES NOT EXIST");
              gotoxy(14,50);
            System.out.println("TYPE YES IF YOU WANT TO SEARCH AGAIN");
              gotoxy(15,50);
            System.out.println("TYPE NO IF YOU WANT TO RETURN");
              gotoxy(16,50);
            System.out.print("CHOICE: "); 
            String choice = scan.nextLine(); 
            switch(choice){
                case "YES": 
                case "Yes":
                case "yes": 
                    PaymentReceipt();
                    break; 
                case "NO": 
                case "No": 
                case "no": 
                    main(new String[] {}); 
                    break; 
                default:
                      gotoxy(17,50);
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
          gotoxy(18,50);
        System.out.print("ENTER THE AMOUNT OF BILL: "); 
        double bill = scan.nextDouble(); 
          gotoxy(19,50);
        System.out.print("ENTER THE AMOUNT OF PAYMENT: "); 
        double payment = scan.nextDouble();
       
        if(payment < bill && payment > 0){
              gotoxy(20,50);
           System.out.println("INVALID PAYMENT");
           }
        else if(payment > bill){
              gotoxy(21,50);
            System.out.println("INVALID PAYMENT"); 
        }
        else if(payment == 0){
              gotoxy(22,50);
            System.out.println("UNPAID");
            String status = "UNPAID"; 
            CreateFilePaymentReceipt(search, payment, status); 
        }
        else{
            String status = "PAID"; 
            CreateFilePaymentReceipt(search, payment, status);
            clear();
            gotoxy(10,50);
            LoadingScreen(); 
            clear(); 
              gotoxy(10,50);
            System.out.println("SUCCESSFUL PAYMENT"); 
        }
          gotoxy(11,50);
        System.out.println("PRESS ANY KEY TO RETURN"); 
        
        try{
           System.in.read();
           clear();
          main(new String[]{}); 
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
        
    }
    public static void CreateFilePaymentReceipt(String SearchResult, double amount, String status){ 
       String content = String.format("%s | PAYMENT AMOUNT: %.3f | STATUS: %s", SearchResult, amount, status);
     
       try(BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH, true))){
        writer.write(content);
        writer.newLine(); 
     
       }catch(IOException e){
         System.out.println("Error occured writing to file");
       }
    }
    public static String SearchPaymentReceipt(String id, String date){
       String line; 
       String result = null; 
          
       try(BufferedReader reader = new BufferedReader (new FileReader(CUSTOMER_FILE_PATH))){
    
          while((line = reader.readLine())!= null){
           if(line.contains("ID: " + id) && line.contains("DATE: " + date)){
              result = line.trim(); 
              break; 
           }
           }
       }catch(IOException e){
           System.out.println("ERROR OCCURED READING THE FILE"); 
       }
       return result; 
    }
    
    
    public static String SearchBill(String id, String date){
       try(BufferedReader reader = new BufferedReader (new FileReader(BILLING_FILE_PATH))){
        String line; 
          while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String idPart = parts[0].trim();
                String datePart = parts[2].trim();
                
                String searchId = idPart.split(": ")[1];
                String searchDate = datePart.split(": ")[1];
                
                if (searchId.equals(id) && searchDate.equals(date)) {
                    return line;
                }
            }
        }catch(IOException e){
           System.out.println("ERROR OCCURED READING THE FILE"); 
       }
       return null; 
    }
    
    
    public static void BillingReceipt(){
        clear(); 
        Scanner scan = new Scanner(System.in);
        gotoxy(10,50); 
        System.out.print("ENTER ID: ");
        String id = scan.nextLine(); 
        String name = getNameById(id);
        if(name == null){
            gotoxy(11,50); 
            System.out.println("ID DOES NOT EXIST");
                  gotoxy(12,50); 
            System.out.println("TYPE YES IF YOU WANT TO SEARCH AGAIN");
                  gotoxy(13,50); 
            System.out.println("TYPE NO IF YOU WANT TO RETURN");
                  gotoxy(14,50); 
            System.out.print("CHOICE: "); 
            String choice = scan.nextLine(); 
            switch(choice){
                case "YES": 
                case "Yes":
                case "yes": 
                    clear();
                    BillingReceipt();
                    break; 
                case "NO": 
                case "No": 
                case "no": 
                    clear();
                    main(new String[] {}); 
                    break; 
                default:
                    System.out.println("INVALID INPUT"); 
                    break; 
            }
        }else{
            gotoxy(15, 50); 
           System.out.println("NAME: " + name);
        }
           gotoxy(16, 50); 
        System.out.print("ENTER DATE (MM/DD/YYYY): ");
        String date = scan.nextLine(); 
        gotoxy(17,50); 
        System.out.print("ENTER DUE DATE: "); 
        String dueDate = scan.nextLine();
          gotoxy(18,50);
        System.out.print("CURRENT METER READING: "); 
        double currentRead = (double) scan.nextDouble(); 
          gotoxy(19,50);
        System.out.print("LAST MONTH METER READ: ");
        double lastRead = (double) scan.nextDouble();
          gotoxy(20,50);
        System.out.print("CHARGE PER kWh: ");
        double charKwh = (double) scan.nextDouble(); 
          gotoxy(21,50);
        System.out.print("FINAL MONTHLY FEE: "); 
        double monthlyFee = scan.nextDouble(); 
        
        
        
        double bill = Formula(currentRead, lastRead, charKwh, monthlyFee); 
        CreateFileBill(id, name, date, bill, dueDate);
        clear(); 
          gotoxy(22,50);
        System.out.printf("BILL AS OF %s", date);
          gotoxy(23,50);
        System.out.print("\nNAME: " + name);
          gotoxy(24,50);
        System.out.printf("\nBILL AMOUNT: %.3f", bill);
          gotoxy(25,50);
        System.out.println("\n\nPRESS ANY KEY TO RETURN");
        
        try{
           System.in.read();
          main(new String[]{}); 
        }
        catch (IOException e){
            e.printStackTrace(); 
        }
    } 
    public static void CreateFileBill(String id, String name, String date, double bill, String dueDate){ 
     
     String content = String.format("ID: %s | NAME: %s | DATE: %s | BILL: %.3f| DUE DATE: %s", id, name, date, bill, dueDate);
     
     try(BufferedWriter writer = new BufferedWriter(new FileWriter(BILLING_FILE_PATH, true))){
     writer.write(content);
     writer.newLine(); 
     
     }catch(IOException e){
         System.out.println("Error occured writing to file");
     }
    }
    public static double Formula(double cr, double lr, double ckWh, double mf){
        double totalKwh = cr - lr; 
        double totalEnergy = ckWh * totalKwh; 
        return totalEnergy + mf; 
    }
    public static void SearchInterface(){ 
        clear(); 
        Scanner scan = new Scanner(System.in);
          gotoxy(10,50);
        System.out.println("SEARCH TRANSACTION"); 
          gotoxy(11,50);
        System.out.println("[1] SEARCH ID AND DATE");
          gotoxy(12,50);
        System.out.println("[2] SEARCH ID");
          gotoxy(13,50);
        System.out.println("[3] EXIT");
          gotoxy(14,50);
        System.out.print("ENTER CHOICE: "); 
        int choice = scan.nextInt();
        
        switch(choice){
            case 1: 
                SearchDate(); 
                break; 
            case 2: 
                SearchID(); 
                break;
            case 3: 
                main(new String[] {}); 
                break; 
            default: 
                clear(); 
                  gotoxy(15,50);
                System.out.println("INCORRECT INPUT");
                  gotoxy(16,40);
                System.out.println("PRESS ANY KEY TO RETURN");
                try{
                    System.in.read();
                    main(new String[]{}); 
                }
                catch (IOException e){
                    e.printStackTrace(); 
                }
                main(new String[] {}); 
                break; 
        }
    } 
    public static void SearchDate(){
        clear(); 
        Scanner scan = new Scanner(System.in);
          gotoxy(10,50);
        System.out.print("ENTER DATE: ");
        String date = scan.nextLine();
          gotoxy(11,50);
        System.out.print("ENTER ID: ");
        String id = scan.nextLine();
        
        String result = SearchByIdAndDate(id, date);
        if(result != null){
        clear(); 
           gotoxy(10,40); 
        LoadingScreen(); 
        clear(); 
        gotoxy(10,50); 
        System.out.println(result);
        }else{
            clear();
              gotoxy(11,50);
            System.out.println("NOT FOUND"); 
        }
          gotoxy(12,50);
        System.out.println("PRESS ANY KEY TO RETURN"); 
        
         try{
           System.in.read(); 
           main(new String[] {}); 
        }catch(IOException e){
           e.printStackTrace(); 
        }
        
    }
    public static String SearchByIdAndDate(String id, String date){ 
        
         try(BufferedReader reader = new BufferedReader (new FileReader(CUSTOMER_FILE_PATH))){
        String line; 
          while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String idPart = parts[0].trim();
                String datePart = parts[2].trim();
                
                String searchId = idPart.split(": ")[1];
                String searchDate = datePart.split(": ")[1];
                
                if (searchId.equals(id) && searchDate.equals(date)) {
                    return line;
                }
            }
        }catch(IOException e){
           System.out.println("ERROR OCCURED READING THE FILE"); 
       }
       return null;  
    }
    
    public static void SearchID(){
        clear(); 
        Scanner scan = new Scanner(System.in); 
          gotoxy(10,50);
        System.out.print("ENTER ID: ");
        String id = scan.nextLine(); 
        String result = SearchById(id); 
        if(result != null){
        clear(); 
        gotoxy(10,40); 
        LoadingScreen();
        clear(); 
        gotoxy(10,50); 
        System.out.println(result);
        }else{

              gotoxy(11,50);
            System.out.println("NOT FOUND"); 
        }
          gotoxy(12,50);
        System.out.println("PRESS ANY KEY TO RETURN"); 
        
        try{
           System.in.read(); 
           main(new String[] {}); 
        }catch(IOException e){
           e.printStackTrace(); 
        }
    }
    public static String SearchById(String id){
      try(BufferedReader reader = new BufferedReader (new FileReader(CUSTOMER_FILE_PATH))){
        String line; 
          while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String idPart = parts[0].trim();
                
                String searchId = idPart.split(": ")[1];
                
                if (searchId.equals(id)) {
                    return line;
                }
            }
        }catch(IOException e){
           System.out.println("ERROR OCCURED READING THE FILE"); 
       }
       return null;  
    }
    public static void Download(){ 
        clear(); 
        Scanner scan = new Scanner(System.in);
          gotoxy(10,50);
        System.out.println("[1] DOWNLOAD PAYMENT RECEIPT");
          gotoxy(11,50);
        System.out.println("[2] DOWNLOAD BILLING RECEIPT");
          gotoxy(12,50);
        System.out.println("[3] EXIT");
          gotoxy(13,50);
        System.out.print("CHOICE: "); 
        int choice = scan.nextInt(); 
        
        switch(choice){ 
            case 1: 
                DownloadPaymentReceipt(); 
                break; 
            case 2: 
                DownloadBillReceipt(); 
                break;
            case 3:
                main(new String[] {}); 
                break;
            default:
                clear(); 
                gotoxy(14,50);
                System.out.println("INCORRECT INPUT"); 
                Download(); 
                break; 
        }
    }
    
    public static void DownloadPaymentReceipt(){
     clear();  
     Scanner scan = new Scanner(System.in); 
        gotoxy(10,50); 
        System.out.print("ENTER ID: "); 
        String id = scan.nextLine(); 
        gotoxy(11,50); 
        System.out.print("ENTER DATE OF BILL: "); 
        String date = scan.nextLine(); 
        
        String record = SearchPaymentReceipt(id, date); 
        if(record == null){
            clear(); 
              gotoxy(12,50);
            System.out.println("INFORMATION DOES NOT EXIST");
              gotoxy(13,50);
            System.out.println("TYPE YES IF YOU WANT TO SEARCH AGAIN");
              gotoxy(14,50);
            System.out.println("TYPE NO IF YOU WANT TO RETURN");
              gotoxy(15,50);
            System.out.print("CHOICE: "); 
            String choice = scan.nextLine(); 
            switch(choice){
                case "YES": 
                case "Yes":
                case "yes": 
                    PaymentReceipt(); 
                    break; 
                case "NO": 
                case "No": 
                case "no": 
                    main(new String[] {}); 
                    break; 
                default:
                      gotoxy(16,50);
                    System.out.println("INVALID INPUT");
                    break; 
            }
        }else{
        String sanitizedDate = date.replaceAll("[^a-zA-Z0-9]", "-");
        String filename = String.format("\\Payment_Receipt_%s_%s.csv", id, sanitizedDate); 
        String receipt = String.format("%s", record); 
        String directory = "C:\\Users\\padil\\Documents\\Payment Receipt";
        String filePath = directory + filename; 
       
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(receipt);
            writer.newLine();  
            clear();
            gotoxy(10,40); 
            LoadingScreen();
            clear(); 
              gotoxy(10,50);
            System.out.println("SUCCESSFULLY DOWNLOADED");
        } catch (IOException e) {
            System.out.println("Error occurred writing to file");
        }
        }
        
        try{ 
            Thread.sleep(10); 
        }catch(InterruptedException e){
            e.printStackTrace(); 
        } 
          gotoxy(11,50);
        System.out.println("PRESS ANY KEY TO RETURN");
        try{
            System.in.read(); 
            Download();
        }catch(IOException e){
            System.out.println("ERROR OCCURED");
        }
        
        
    }
        public static void DownloadBillReceipt(){
        clear(); 
        Scanner scan = new Scanner(System.in);
        gotoxy(10,50); 
        System.out.print("ENTER ID: "); 
        String id = scan.nextLine(); 
          gotoxy(11,50);
        System.out.print("ENTER DATE OF BILL: "); 
        String date = scan.nextLine(); 
        
        String record = SearchBill(id, date); 
        if(record == null){
              gotoxy(12,50);
            System.out.println("INFORMATION DOES NOT EXIST");
              gotoxy(13,50);
            System.out.println("TYPE YES IF YOU WANT TO SEARCH AGAIN");
              gotoxy(14,50);
            System.out.println("TYPE NO IF YOU WANT TO RETURN");
              gotoxy(15,50);
            System.out.print("CHOICE: "); 
            String choice = scan.nextLine(); 
            switch(choice){
                case "YES": 
                case "Yes":
                case "yes": 
                    DownloadBillReceipt(); 
                    break; 
                case "NO": 
                case "No": 
                case "no": 
                    main(new String[] {}); 
                    break; 
                default:
                      gotoxy(16,50);
                    System.out.println("INVALID INPUT");
                    break; 
            }
        }else{
        String sanitizedDate = date.replaceAll("[^a-zA-Z0-9]", "-");
        String filename = String.format("\\BILL_%s_%s.csv", id, sanitizedDate); 
        String receipt = String.format("%s", record); 
        String directory = "C:\\Users\\padil\\Documents\\Billing Receipt";
        String filePath = directory + filename; 
       
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(receipt);
            writer.newLine();  
            clear(); 
            gotoxy(10,40); 
            LoadingScreen(); 
              clear();
            gotoxy(10,50);
            System.out.println("SUCCESSFULLY DOWNLOADED");
        } catch (IOException e) {
            System.out.println("ERROR OCCURED WRITING TO FILE");
        }
        }  
        
        try{ 
            Thread.sleep(10); 
        }catch(InterruptedException e){
            e.printStackTrace(); 
        }
          gotoxy(11,50);
        System.out.println("PRESS ANY KEY TO RETURN");
        try{
            System.in.read(); 
            Download();
        }catch(IOException e){
            System.out.println("ERROR OCCURED");
        }
        
    }
    
      public static String getNameById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(REGISTER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(" \\| ");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String currentId = parts[2].trim();
                    currentId = currentId.replace("ID: ", "").trim(); 
                  
                    if (currentId.equals(id)) {
                        return name.replace("NAME: ", "").trim();
                    }
                } else {
                    System.out.println("Line does not have exactly 3 parts: " + line); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the file: " + e.getMessage());
        }
        return null;
    }
    
     public static void clear(){ 
        try{
        if(System.getProperty("os.name").contains("Windows")){
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor(); 
        }else{
            System.out.println("\033[H\033[2J");
            System.out.flush();
        }
        }catch (IOException | InterruptedException ex) {
            System.out.println("Error clearing console: " + ex.getMessage());
        }
    }
    
    public static void LoadingScreen(){
        int total = 50; 
        
        for(int i = 0; i < total ; i++){ 
            try{
                Thread.sleep(25); 
            }catch(InterruptedException e){
                e.printStackTrace(); 
            }
            System.out.print("="); 
            System.out.flush(); 
        }
        System.out.println(); 
    }
    public static void gotoxy(int row, int col){
        System.out.printf("\033[%d;%dH", row, col);
        System.out.flush(); 
    }
}





    