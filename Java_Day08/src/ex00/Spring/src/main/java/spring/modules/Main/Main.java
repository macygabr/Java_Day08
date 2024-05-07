package spring.modules.Main;

import spring.modules.PreProcessor.*;
import spring.modules.Printer.*;
import spring.modules.Renderer.*;

import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        first();
        second();
    }
    private static void first(){
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");
    }

    private static void second(){
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer = context.getBean("printerWithPrefixErrToUpper", Printer.class);
        printer.print("Hello!");
    }
}