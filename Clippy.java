import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

class Clippy {

    public static void main(String[] args) {
        Clipboard clippy = new Clipboard("Clippy");        
        System.out.println(clippy.getName());
    }
}
