package Project1;

import java.util.ArrayList;
import java.io.*;
import java.util.*;

/*
 * Napat Jiaravijit 6613119
 * Teewara Pudpoo 6613254
 * Ruttapon Suppaakkarasolpon 6613270
 */

class MyException extends Exception{
        MyException(String message) {
            super(message);
        }
    }

class Product implements Comparable<Product>{
    private String code, name;
    private int unitPrice,flatComm, q1Comm, q2Comm, q3Comm,q4Comm,totalunit=0, maxUnitSales = 0; ;
     private ArrayList<String> topSalespersons = new ArrayList<>();
    public Product(String cd, String nm, int up, int fc, int q1, int q2, int q3, int q4)
    {
        code = cd; name = nm; unitPrice = up; flatComm = fc; q1Comm = q1; q2Comm = q2; q3Comm = q3; q4Comm = q4;
    }
    public void addUnit(int t, String salespersonName){
        totalunit += t ;
        if (t > maxUnitSales) {
            maxUnitSales = t;
            topSalespersons.clear(); 
            topSalespersons.add(salespersonName);
        } else if (t == maxUnitSales) {
            topSalespersons.add(salespersonName); 
        }
    }
    public ArrayList<String> getTopSalespersons() {
        return topSalespersons;
    }
    public int getUnit(){
        return totalunit ;
    }
    public String getName(){
        return name ;
    }
    
    public String getCode(){
        return code ;
    }
    
    public int getFlatComm(){
        return flatComm ;
    }
    public int getQ(int q){
    
        switch(q){
            case 1 -> { return q1Comm ;}
            case 2 -> { return q2Comm ;}
            case 3 -> { return q3Comm ;}
            case 4 -> { return q4Comm ;}
        }
        return 0 ;
    }
    public int getPrice(){
        return unitPrice ;
    }
    public int compareTo(Product other)
    {
        if (this.totalunit < other.totalunit) return 1;
        else if (this.totalunit > other.totalunit) return -1;
        else return 0;
    }
    
    public void PrintDetails()
    {
        System.out.printf("%-18s (%s)    unit price = %,-7d", name, code,unitPrice);
        System.out.printf("     commissions  >>  flat = %2d%%  Q1 = %2d%%",flatComm,q1Comm);
        System.out.printf("  Q2 = %2d%%  Q3 = %2d%%  Q4 = %2d%% \n", q2Comm,q3Comm,q4Comm);
        
    }

}

class Reimbursement{
    private String type;
    private int travelLimit, mobileLimit;
    public Reimbursement(String tp, int tl, int ml)
    {
        type = tp; travelLimit = tl; mobileLimit = ml;
    }
    public String getType(){
        return type ;
    }
    public int getTlimit(){
        return travelLimit ;
    }
    public int getMlimit(){
        return mobileLimit ;
    }
    public void PrintDetails(){
        String t = null;
        if(type.equalsIgnoreCase("c")) t="commission";
        else if(type.equalsIgnoreCase("s"))t="salary+";
        System.out.printf("%-18s travel limit = %,6d     mobile limit = %,8d \n", t, travelLimit, mobileLimit);
    }
}

abstract class Saleperson{

    private String type, name, productCode ;
    private int q1, q2, q3, q4 ;
    private int travelExpense, mobileExpense ;
    private int total_travel, total_mobile;
    
    public Saleperson(String type, String name, String productCode, int q1, int q2, int q3, int q4)	{
         this.type = type;
         this.name = name;
         this.productCode = productCode;
         this.q1 = q1;
         this.q2 = q2;
         this.q3 = q3;
         this.q4 = q4;
         this.travelExpense = 0 ;
         this.mobileExpense = 0 ;
    }
    public String getName() {
        return name;
    }
    public int getQ(int q){
    
        switch(q){
            case 1 -> { return q1 ;}
            case 2 -> { return q2 ;}
            case 3 -> { return q3 ;}
            case 4 -> { return q4 ;}
            case 0 -> { return q1+q2+q3+q4 ;}
        }
        return 0 ;
    }
        
    public String getCode() {
        return productCode;
    }
    
    public void setTravelExpense(int travelExpense) {
        this.travelExpense = travelExpense;
    }
    
    public void setMobileExpense(int mobileExpense) {
        this.mobileExpense = mobileExpense;
    }

    public void settotalTravelExpense(int travelExpense) {
        this.total_travel += travelExpense;
    }
    public int getTravelExpense() {
        return this.travelExpense ;
    }
    
    public void settotalMobileExpense(int mobileExpense) {
        this.total_mobile += mobileExpense;
    }
    public int getMobileExpense() {
        return mobileExpense;
    }
    public int gettotalTravelExpense() {
        return this.total_travel ;
    }
    public int gettotalMobileExpense() {
        return this.total_mobile ;
    }
    abstract public void payment(ArrayList<Product> p,ArrayList<Reimbursement> r);
    
}
class salarySale extends Saleperson{
    private int salary ;

    public salarySale(String type, String name, String productCode, int q1, int q2, int q3, int q4, int salary) {
        super(type, name, productCode, q1, q2, q3, q4);
        this.salary = salary;
    }
    
    public int getSalary() {
        return salary;
    }
    public void payment(ArrayList<Product> p,ArrayList<Reimbursement> r){
        // payment type commSale -> sigma(qcomm) following Project_213_T1.pdf
        String pdName = "Not exists" ;
        Product pd = null ;
        Reimbursement rm = null ;
        int amountSell = this.getQ(0);
        for(Product pl : p){
            if((pl.getCode()).equals(this.getCode()))   {
                pd = pl ;
                pdName = pd.getName() ;
            }
        }
        for(Reimbursement rl : r){
            if((rl.getType()).equals("s")) { rm = rl ;}
        }
        
        int sum = amountSell * pd.getFlatComm()* pd.getPrice()/100 ;
        int texcess = ((this.getTravelExpense()-rm.getTlimit())>0) ? this.getTravelExpense()-rm.getTlimit() :  0 ;
        int mexcess = ((this.getMobileExpense()-rm.getMlimit())>0) ? this.getMobileExpense()-rm.getMlimit() :  0 ;
        
        System.out.printf("%-7s %-10s >> %-18s= %,10d baht\n",this.getName(),"salary+","total salary",this.getSalary()*12);
        System.out.printf("%-18s >> %-18s %-18s Q1(%3d)   Q2(%3d)   Q3(%3d)   Q4(%3d)\n"," ",pdName," ",this.getQ(1),this.getQ(2),this.getQ(3),this.getQ(4)) ;
        System.out.printf("%-18s >> %-18s= %,10d baht     %-7s=%,7d units\n"," ","total commission",sum,"total",amountSell) ;
        System.out.printf("%-18s >> %-18s= %,10d baht     %-7s=%,7d baht\n"," ","travel expense",this.getTravelExpense(),"excess",texcess) ;
        System.out.printf("%-18s >> %-18s= %,10d baht     %-7s=%,7d baht\n"," ","mobile expense",this.getMobileExpense(),"excess",mexcess) ;
        System.out.printf("%-18s >> %-18s= %,10d baht\n"," ","total payment",this.getSalary()*12+sum-texcess-mexcess) ;
        
        pd.addUnit(amountSell,this.getName());
    }
}
class commSale extends Saleperson{

    public commSale(String type, String name, String productCode, int q1, int q2, int q3, int q4) {
        super(type, name, productCode, q1, q2, q3, q4);
    }
 
    public void payment(ArrayList<Product> p,ArrayList<Reimbursement> r){
        // payment type commSale -> sigma(qcomm) following Project_213_T1.pdf
        String pdName = "Not exists" ;
        Product pd = null ;
        Reimbursement rm = null ;
        for(Product pl : p){
            if((pl.getCode()).equals(this.getCode()))   {
                pd = pl ;
                pdName = pd.getName() ;
            }
        }
        for(Reimbursement rl : r){
            if((rl.getType()).equals("c")) { rm = rl ;}
        }
        int amountSell = this.getQ(0);
        int sum = (this.getQ(1)*pd.getPrice()*pd.getQ(1)/100)+(this.getQ(2)*pd.getPrice()*pd.getQ(2)/100)+(this.getQ(3)*pd.getPrice()*pd.getQ(3)/100)+(this.getQ(4)*pd.getPrice()*pd.getQ(4)/100);
        int texcess = ((this.getTravelExpense()-rm.getTlimit())>0) ? this.getTravelExpense()-rm.getTlimit() :  0 ;
        int mexcess = ((this.getMobileExpense()-rm.getMlimit())>0) ? this.getMobileExpense()-rm.getMlimit() :  0 ;
        System.out.printf("%-7s %-10s >> %-18s= %,10d baht\n",this.getName(),"commission","total salary",0);
        System.out.printf("%-18s >> %-18s %-18s Q1(%3d)   Q2(%3d)   Q3(%3d)   Q4(%3d)\n"," ",pdName," ",this.getQ(1),this.getQ(2),this.getQ(3),this.getQ(4)) ;
        System.out.printf("%-18s >> %-18s= %,10d baht     %-7s=%,7d units\n"," ","total commission",sum,"total",amountSell) ;
        System.out.printf("%-18s >> %-18s= %,10d baht     %-7s=%,7d baht\n"," ","travel expense",this.getTravelExpense(),"excess",texcess) ;
        System.out.printf("%-18s >> %-18s= %,10d baht     %-7s=%,7d baht\n"," ","mobile expense",this.getMobileExpense(),"excess",mexcess) ;
        System.out.printf("%-18s >> %-18s= %,10d baht\n"," ","total payment",sum-texcess-mexcess) ;
    
        pd.addUnit(amountSell, this.getName());
    }
}


public class Project1 {
    private static ArrayList<Product> productList = new ArrayList<Product>() ;
    private static ArrayList<Reimbursement> ReimbursementList = new ArrayList<Reimbursement>() ;
    private static ArrayList<Saleperson> SaleList = new ArrayList<Saleperson>() ;
    
    public static void main(String[] args) {
        Project1 mainApp = new Project1() ;
        
        //test init
        mainApp.initProduct();
        mainApp.initReimbursement();
        mainApp.initSaleperson();
        mainApp.initExpense();

        mainApp.summary();
        
    }    
    
    
    //initailize arraylist
    //woosen.
    public void initProduct(){
        String path = "src/main/java/Project1/";
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
        for(Product n: productList){
            n.PrintDetails();
        }
        System.out.println("\n");
    }
    public void initReimbursement(){
        String path = "src/main/java/Project1/";
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
        catch(Exception e){
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
        for(Reimbursement n : ReimbursementList){
            n.PrintDetails();
        }
        System.out.println("\n");
    }    

    //ken.
    public void initSaleperson() {
            
            
            
            String spath = "salespersons_errors.txt";
            
            Boolean open = false ;

            while(!open) {
                
                String path = "src/main/java/Project1/" + spath;
                File InFile = new File(path);
                open = false ;
                
                try(Scanner scan = new Scanner(InFile) ;){
                    open = true;
                    System.out.println("Read from " + path);
                    scan.nextLine();
                    while(scan.hasNext()){
                        String input   = scan.nextLine();
                        try {
                        String [] cols = input.split(",");
                        String type = cols[0].trim();
                        if(!type.equals("s") && !type.equals("c")) {
                            throw new IllegalArgumentException(type);
                        }
                        String name = cols[1].trim();
                        String product = cols[2].trim();
                        int flag = 0;
                        for(Product n : productList){
                            if(n.getCode().equals(product)) {
                                flag = 1;
                            }
                        }
                        if(flag == 0) {
                            throw new MyException("for input: " + product);
                        }
                        int q1 = Integer.parseInt(cols[3].trim());
                        if(q1 < 0) {
                            throw new MyException("for input: " + q1);
                        }
                        int q2 = Integer.parseInt(cols[4].trim());
                        if(q2 < 0) {
                            throw new MyException("for input: " + q2);
                        }
                        int q3 = Integer.parseInt(cols[5].trim());
                        if(q3 < 0) {
                            throw new MyException("for input: " + q3);
                        }
                        int q4 = Integer.parseInt(cols[6].trim());
                        if(q4 < 0) {
                            throw new MyException("for input: " + q4);
                        }
                        if(type.equals("s")) {
                            int salary = Integer.parseInt(cols[7].trim());
                            if(salary < 0) {
                            throw new MyException("for input: " + salary);
                        }
                            SaleList.add(new salarySale(type,name,product,q1,q2,q3,q4,salary));
                        }
                        if(type.equals("c")) {
                            SaleList.add(new commSale(type,name,product,q1,q2,q3,q4));
                        }
                        }
                        catch (Exception e) {
                            System.err.println(e);
                            System.err.println(input + " --> skip this line");
                            System.err.println();
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                    System.out.println("New file name = ");
                    spath = (new Scanner(System.in)).next() ;
                    System.out.println("");
                }
            }

        System.out.println("");    
    }


    public void initExpense() {
        
            
            
            String spath = "expensess.txt";
          
            Boolean open = true;
            while(open) {
                
                String path = "src/main/java/Project1/"+spath;
                File InFile = new File(path);
                try(Scanner scan = new Scanner(InFile);){
                    open = false;
                    System.out.println("Read from " + path);
                    scan.nextLine();
                    while(scan.hasNext()){
                        String input   = scan.nextLine();
                        try {
                        String [] cols = input.split(",");
                        String name = cols[0].trim();
                        int travel = Integer.parseInt(cols[1].trim());
                        int mobile = Integer.parseInt(cols[2].trim());
                        System.out.printf("%-5s, %7d, %7d   >>   ", name, travel, mobile);
                        boolean found = false;
                        for (Saleperson sp : SaleList) {
                                
                                if (sp.getName().equals(name)) {
                                found = true;
                                sp.setTravelExpense(travel);
                                sp.setMobileExpense(mobile);
                                sp.settotalTravelExpense(travel);
                                sp.settotalMobileExpense(mobile);
                                System.out.printf("total = %,7d, %,7d\n", sp.gettotalTravelExpense(), sp.gettotalMobileExpense());
                                break;
                                }
                                
                    }
                        if(!found) {
                            System.out.printf("not exist\n");
                        }
                        }
                        catch (Exception e) {
                            System.err.println(e);
                            System.err.println(input);
                            System.err.println();
                        }
                    }
            
                }
                catch(Exception e){
                    System.err.println(e);
                    System.out.println("New file name = ");
                    spath = (new Scanner(System.in)).next();
                }
            }
            
        System.out.println("");  
        
    }
    
    
    public void summary(){
        //summary sale
        System.out.println("=".repeat(10) + " Process Payments " + "=".repeat(10)) ;
        for(Saleperson s : SaleList){
            s.payment(productList,ReimbursementList);
            System.out.println("") ;
        }
    
        System.out.println("=".repeat(10) + " Summary " + "=".repeat(10));
        System.out.println("");
        
        Collections.sort(productList);
        for(Product p : productList){
            System.out.printf("%-20s total sales = %,5d units = %,10d baht",p.getName(),p.getUnit(),p.getPrice()*p.getUnit());
            System.out.printf("     highest sales by ");
            
            ArrayList<String> topSalespersons = p.getTopSalespersons();
            int count = 0 ;
            for (String salesperson : topSalespersons) {
                count++ ;
                System.out.print(salesperson);
                if(topSalespersons.size()!=count)   {System.out.print(",") ;}
            }
            System.out.println("");
        }
        
    }
}
