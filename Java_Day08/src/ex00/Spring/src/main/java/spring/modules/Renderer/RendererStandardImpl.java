package spring.modules.Renderer;
import spring.modules.PreProcessor.PreProcessor;
public class RendererStandardImpl implements Renderer {

    private PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public String render(String text) {
        return text;
    }
    
}