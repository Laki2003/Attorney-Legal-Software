import GUI.gui;
import config.db;
import schema.Attorney;


public class App {
  

 

    public static void main(String[] args) throws Exception {
Attorney a = new Attorney("gLIGORIJE", "BARdun", 54, "fdafg", "dgfs", "dsg");
System.out.println(a.getId());
gui g= new gui();
}
}