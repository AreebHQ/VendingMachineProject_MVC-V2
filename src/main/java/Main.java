import com.Controller.VendingMachineController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

       /* ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();*/



        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com");
        appContext.refresh();

        VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();

    }
}
