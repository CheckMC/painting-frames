package checkmc.paintingframes.componentInterfaces;

import checkmc.paintingframes.frames.Frame;
import org.ladysnake.cca.api.v3.component.ComponentV3;

public interface StringComponent extends ComponentV3 {
    String getValue();
    void setValue(Frame frame);
}
