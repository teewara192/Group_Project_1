package Project1;

import java.util.ArrayList;

/*
 *
 * 
 *
 */

class Product{
    private String code, name;
    private int unitPrice,flatComm, q1Comm, q2Comm, q3Comm,q4Comm,totalunit=0, totalprice=0;
    public Product(String cd, String nm, int up, int fc, int q1, int q2, int q3, int q4)
    {
        code = cd; name = nm; unitPrice = up; flatComm = fc; q1Comm = q1; q2Comm = q2; q3Comm = q3; q4Comm = q4;
    }
     /*
    public int compareTo(Product other)
    {
        if (this.totalunit < other.totalunit) return 1;
        else if (this.totalunit > other.totalunit) return -1;
        else return 0;
    }*/
    
    public void PrintDetails()
    {
        System.out.printf("%-18s (%s)    unit price = %,-7d", name, code,unitPrice);
        System.out.printf("     commissions  >>  flat = %2d%%  Q1 = %2d%%",flatComm,q1Comm);
        System.out.printf("  Q2 = %2d%%  Q3 = %2d%%  Q4 = %2d%% \n", q2Comm,q3Comm,q4Comm);
        
    }
    /*
    public void PrintSummary()
    {
        System.out.printf("%-18s total sales = %4d units  = %,12d baht    highest sales by ", name,totalunit, totalprice);
    }*/
    /*
    public void AddTotalUnit(int addUnit)
    {
        totalunit = totalunit + addUnit;
        totalprice = totalunit*unitPrice;
    }
    */
}

class Reimbursement{
    private String type;
    private int travelLimit, mobileLimit;
    public Reimbursement(String tp, int tl, int ml)
    {
        type = tp; travelLimit = tl; mobileLimit = ml;
    }

    public void PrintDetails(){
        String t = null;
        if(tyoe.equalsIgnoreCase("c")) t="commission";
        else if(type.equalsIgnoreCase("s"))t="salary+";
        System.out.printf("%-18s travel limit = %,6d     mobile limit = %,8d \n", t, travelLimit, mobileLimit);
    }
}

abstract class Saleperson{
    private String type, name, productCode ;
    int q1, q2, q3, q4 ;
    int travelExpense, mobileExpense ;
    
    abstract public void payment();
    
}
class salarySale extends Saleperson{
    private int salary ;
    public void payment(){
        // payment type salaryman -> flatcomm plus salary following Project_213_T1.pdf
    }
}
class commSale extends Saleperson{
    public void payment(){
        // payment type commSale -> sigma(qcomm) following Project_213_T1.pdf
    }
}


public class Main {
    private static ArrayList<Product> productList = new ArrayList<Product>() ;
    private static ArrayList<Reimbursement> ReimbursementList = new ArrayList<Reimbursement>() ;
    private static ArrayList<Saleperson> SaleList = new ArrayList<Saleperson>() ;
    
    public static void main(String[] args) {
        Main mainApp = new Main() ;
        
        //test init
        //mainApp.initProduct();
        //mainApp.initReimbursement();
        //mainApp.initSaleperson();
    }    
    
    
    //initailize arraylist
    //woosen.
    public void initProduct(){
        String path = "src/main/Java/Project1/";
        String name = "products.txt";
        Scanner filescan= null;
        boolean filenotopen = true;
        
        try{
            String inFileName = path + name;
            File inFile = new File(inFileName);
            if(!inFile.exists()){
              throw new FileNotFoundException();
            }
            filenotopen = false;
            filescan = new Scanner(inFile);
            System.out.printf("Read from %s\n", inFileName);
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }
        
        Scanner scan = new Scanner(System.in);
        
        
        while(filenotopen){
            try{
                String newFileName;
                System.out.println("Enter correct file name: ");
                newFileName = scan.nextLine();
                String inFileName = path + newFileName;
                File inFile = new File(inFileName);
                if(!inFile.exists()){
                    throw new FileNotFoundException();
                }
                filenotopen = false;
                filescan =new Scanner(inFile);
                System.out.printf("Read from %s\n", inFileName);
            }
            catch(FileNotFoundException e){
                System.err.println(e);
            }
        }
        String line = null;
        int countline = 0;
        
         
        while(filescan.hasNextLine()){
            line = filescan.nextLine();

            if(countline==0){
                countline++;
                continue;
            }

            else{
                String col[] = line.split(",");

                try{
                    String code = col[0].trim();
                    String pdName = col[1].trim();
                    double unitPrice = Double.parseDouble(col[2].trim());
                    if (unitPrice<0){
                        throw new MyException("For input string: "+unitPrice);
                    }
                    double flatComm = Double.parseDouble(col[3].trim());
                    if (flatComm<0){
                        throw new MyException("For input string: "+flatComm);
                    }
                    double q1 = Double.parseDouble(col[4].trim());
                    if (q1<0){
                        throw new MyException("For input string: "+q1);
                    }
                    double q2 = Double.parseDouble(col[5].trim());
                    if (q2<0){
                        throw new MyException("For input string: "+q2);
                    }
                    double q3 = Double.parseDouble(col[6].trim());
                    if (q3<0){
                        throw new MyException("For input string: "+q3);
                    }
                    double q4 = Double.parseDouble(col[7].trim());
                    if (q4<0){
                        throw new MyException("For input string: "+q4);
                    }

                    productList.add(new Product(code,pdName,(int)unitPrice,(int)flatComm,(int)q1,(int)q2,(int)q3,(int)q4));
                    

               }
                catch(MyException e){
                    System.err.println(e);
                    System.err.println(line+"\n");
                }
                catch(Exception e){
                    System.err.println(e);
                    System.err.println(line+"\n");
                }
            }
        }
        for(Product n: Pd){
            n.PrintDetails();
        }
        System.out.println("\n");
    }
    public void initReimbursement(){
        String path = "src/main/Java/Project1/";
        String name = "reimbursements.txt";
        Scanner filescan= null;
        boolean filenotopen = true;
        
        try{
            String inFileName = path + name;
            File inFile = new File(inFileName);
            if(!inFile.exists()){
              throw new FileNotFoundException();
            }
            filescan = new Scanner(inFile);
            filenotopen = false;
            System.out.printf("Read from %s\n", inFileName);
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }
        
        Scanner scan = new Scanner(System.in);
        
        
        while(filenotopen){
            try{
                String newFileName;
                System.out.println("Enter correct file name: ");
                newFileName = scan.nextLine();
                String inFileName = path + newFileName;
                File inFile = new File(inFileName);
                if(!inFile.exists()){
                    throw new FileNotFoundException();
                }
                filenotopen = false;
                filescan =new Scanner(inFile);
                System.out.printf("Read from %s\n", inFileName);
                
            }
            catch(FileNotFoundException e){
                System.err.println(e);
            }
        }
        
        String line = null;
        int countline = 0;
        
        
            
        while(filescan.hasNextLine()){
            line = filescan.nextLine();

            if(countline==0){
                countline++;
                continue;
            }

            else{
                String col[] = line.split(",");

                try{

                    String type = col[0].trim();
                    if(!(type.equalsIgnoreCase("c"))&&!(type.equalsIgnoreCase("s"))){
                        throw new MyException("For input string: "+ type);
                    }
                    double travelLimit = Double.parseDouble(col[1].trim());
                    if (travelLimit<0){
                        throw new MyException("For input string: "+travelLimit);
                    }
                    double mobileLimit = Double.parseDouble(col[2].trim());
                    if (mobileLimit<0){
                        throw new MyException("For input string: "+mobileLimit);
                    }
                    

                    ReimbursementList.add(new Reimbursement(type,(int)travelLimit,(int)mobileLimit));

               }
                catch(MyException e){
                    System.err.println(e);
                    System.err.println(line+"\n");
                }
                catch(Exception e){
                    System.err.println(e);
                    System.err.println(line+"\n");
                }
            }
        }
        for(Reimbursement n : Re){
            n.PrintDetails();
        }
        System.out.println("\n");
    }    

    //ken.
    public void initSaleperson(){}
    public void initExpense(){}
    
    
    public void summary(){
        //summary sale 
    }
    
}
