import java.applet.Applet;
import java.awt.Graphics;

public class JavaAppletHello extends Applet {
    public void paint(Graphics g) {
        g.drawString("Hello World!", 50, 25);
    }
}
