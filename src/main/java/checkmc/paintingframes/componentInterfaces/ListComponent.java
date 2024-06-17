package checkmc.paintingframes.componentInterfaces;

import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.ArrayList;

public interface ListComponent extends ComponentV3 {
    ArrayList<String> getValue();
    void addValue(String add);
}
