package spring.modules.Renderer;

import spring.modules.PreProcessor.PreProcessor;

public class RendererErrImpl implements Renderer {
    
    private PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor){
        this.preProcessor = preProcessor;
    }

    @Override
    public String render(String text) {
        return "\033[31m" + preProcessor.process(text) + "\033[0m";
    }
    
}