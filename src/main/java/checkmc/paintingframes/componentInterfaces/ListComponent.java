package checkmc.paintingframes.componentInterfaces;

import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.ArrayList;

public interface ListComponent extends ComponentV3 {
    ArrayList<Identifier> getValue();
    void addValue(String add);
}
