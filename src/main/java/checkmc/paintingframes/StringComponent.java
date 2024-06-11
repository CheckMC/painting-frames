package checkmc.paintingframes;

import org.ladysnake.cca.api.v3.component.ComponentV3;

public interface StringComponent extends ComponentV3 {
    String getValue();
    void setValue(Frame frame);
}
