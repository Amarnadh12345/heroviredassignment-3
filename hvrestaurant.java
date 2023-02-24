import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class FoodOrder {
    
      File NewFile = new File("billingDetails.csv");
      static Scanner sc1= new Scanner(System.in);
      static ArrayList<ArrayList<String>> Itemsmenu = new ArrayList<ArrayList<String>>();
      static ArrayList<ArrayList<String>> Foodplan = new ArrayList<ArrayList<String>>();

      
        static void Readingfile(String fileName,int fn){
            try{
                String line="";
                Scanner br= new Scanner(new FileReader(fileName));
                while((br.hasNext())){
                    line =br.nextLine();
                    String[] fooditems1= line.split(",");
                    List<String> fixedLengthList = Arrays.asList(fooditems1);
                    ArrayList<String> foodItems = new ArrayList<String>(fixedLengthList);
                    if(fn==0){
                        Itemsmenu.add(foodItems);
                    }
                    else{
                        Foodplan.add(foodItems);
                    }
        
                }
                br.close();
            }catch(Exception e){
                System.out.println("runtime error");
            }
        }
        static double CalculateAmount(int id,int q){
            double v=0;
            for(int i=0;i<Itemsmenu.size();i++){
                ArrayList<String> dump = Itemsmenu.get(i);
                if(id== Integer.parseInt(dump.get(0)));
                    v += (q*Integer.parseInt(dump.get(2)));

            }
            return v;

            }
            static String ConfirmOrder(String m[]){
                int Amount =0;
                System.out.println();
                System.out.println("Ordered Items---->>>");
                for(int k=0;k<m.length;k+=2){
                    for(int j=0;j<Itemsmenu.size();j++){
                        ArrayList<String> dump= Itemsmenu.get(j);
                        if(Integer.parseInt(m[k])==Integer.parseInt(dump.get(0))){
                            System.out.println("[ " + dump.get(1) + "    ||  Cost  :  Rs."
                            + Integer.parseInt(m[k + 1]) * Integer.parseInt(dump.get(2)) + " ||  Qty : "
                            + Integer.parseInt(m[k + 1]) + " ]");
                    Amount += Integer.parseInt(m[k + 1]) * Integer.parseInt(dump.get(2));
                }

                        }
                    }
                    System.out.println();
                    System.out.println("Total bill :rs."+Amount);
                    System.out.println();
                    System.out.println("enter y to confirm order");
                    char c=sc1.next().charAt(0);
                    if(c=='y')
                        return ",Approved";
                        return ",Cancelled";
                }
                static void createNewOrder(){
                    String st= "\n",food="";
                    ArrayList<String> dummy = Foodplan.get(Foodplan.size()-1);
                    int lastIndex= Integer.parseInt(dummy.get(0));
                    double totalAmount=0;
                    int j = 1, it, count;
                    while(true){
                        System.out.println();
                        System.out.println("Enter Order details--->>");
                        System.out.print("Item:"+j+ "Enter item id:");
                        it=sc1.nextInt();
                        System.out.print("Item:" + j + "  Quantity : ");
                        count = sc1.nextInt();
                        j++;
                        totalAmount += CalculateAmount(it, count);
                        food += String.valueOf(it) + " " + String.valueOf(count) + " ";
                        System.out.println();
                        System.out.println("press y to place another order");
                        char c=sc1.next().charAt(0);
                        if(c!='y')
                            break;
                    }
                    LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-dd-MMM");
        String formattedDate = myDateObj.format(myFormatObj);
        st += String.valueOf((lastIndex + 1)) + "," + formattedDate + "," + String.valueOf(totalAmount) + ',' + food;
        String[] m = food.split(" ");
        st += ConfirmOrder(m);
        sc1.nextLine();
        try {
            byte[] ByteInput = st.getBytes();
            FileOutputStream FileWrite = new FileOutputStream("billingDetails.csv", true);
            FileWrite.write(ByteInput);
            Foodplan.clear();
            Readingfile("billingDetails.csv", 1);
            FileWrite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void AddingIntoFile() {
        try {
            NewFile.delete();
            if (NewFile.createNewFile()) {
                System.out.println("Status updated in file");
            }
            for (ArrayList<String> arrayList : Foodplan) {
                String listString = String.join(",", arrayList);
                listString += "\n";
                byte[] ByteInput = listString.getBytes();
                FileOutputStream FileWrite = new FileOutputStream("billingDetails.csv",
                        true);
                FileWrite.write(ByteInput);
                FileWrite.close();
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    void CheckBillStatus() {
        System.out.print("Enter order Id : ");
        int n = sc1.nextInt();
        for (int j = 0; j < Foodplan.size(); j++) {
            ArrayList<String> dump = Foodplan.get(j);
            if (n == Integer.parseInt(dump.get(0))) {
                System.out.println(dump);
                System.out.println("press 'a' to place Order");
                char c = sc1.next().charAt(0);
                if (c == 'a')
                    Foodplan.get(j).set(4, "Approved");
                else
                    Foodplan.get(j).set(4, "Cancelled");
            }
        }
        sc1.nextLine();
        AddingIntoFile();
    }

    void CollectionOfDay() {
        System.out.print("Enter the date in Format year-day-month :");
        String date = sc1.nextLine();
        double Coll = 0.0;
        for (ArrayList<String> dump : Foodplan) {
            if ((dump.get(1)).equals(date)) {
                Coll += (Double.parseDouble(dump.get(2)));
            }
        }
        System.out.println(date + " day's Total Collection is  Rs." + Coll);
    }

    FoodOrder() {
        Readingfile("menuList.csv", 0);
        Readingfile("billingDetails.csv", 1);
    }
}



public class hvrestaurant {
    static Scanner sc = new Scanner(System.in);
    static String menuItems[] = { "Enter New Order", "Edit Bill Status", "See Collection of a day" };
    static FoodOrder orderA = new FoodOrder();

    static void Menulist() {
        System.out.println();
        System.out.println("Welcome to 'alpha hotel' , Hyderabad");
        System.out.println("------------------------------------");
        for (int i = 0; i < 3; i++)
            System.out.println((i + 1) + "." + menuItems[i]);
    }

    static void ExecutingMenu() {
        System.out.println();
        System.out.print("Enter your Choice : ");
        int item = sc.nextInt();
        if (item == 1)
            orderA.createNewOrder();
        else if (item == 2)
            orderA.CheckBillStatus();
        else if (item == 3) {
            orderA.CollectionOfDay();
        } else
            System.out.println("Entered Choice not in List");
    }

    public static void main(String[] args) {
        while (true) {
            Menulist();
            ExecutingMenu();
            System.out.println("Press 'y' to return 'Main_Menu'");
            char ch = sc.next().charAt(0);
            if (ch != 'y')
                break;
        }
    }

}
