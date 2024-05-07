package spring.modules.Printer;
import spring.modules.Renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer{

    private Renderer renderer;
    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        System.out.println(text + " " + java.time.LocalDateTime.now());
    }
    
}
