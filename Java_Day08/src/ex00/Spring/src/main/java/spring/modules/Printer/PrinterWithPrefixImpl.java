package spring.modules.Printer;

import spring.modules.Renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer{
    
    private String prefix;
    private Renderer renderer;
    
    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void print(String text) {
        System.out.println(renderer.render(prefix + " " + text));
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
