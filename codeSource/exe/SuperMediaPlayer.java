package exe;
import javafx.application.Application;
import gui.*;

public class SuperMediaPlayer{
    public static void main(String[] args) {
        if(args.length != 3 && args.length!= 1){
            System.out.println("usage: java SuperMediaPlayer <fichierLibrairieLocale> [server address] [pPort]");
            System.exit(0);
        }
        new Thread() {
            @Override
                public void run() {
                    javafx.application.Application.launch(MediaPlayerGui.class, args);
                }
        }.start();        
    }
}

