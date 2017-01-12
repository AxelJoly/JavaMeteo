package sample.Main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Controller.MainController;
import sample.jfx.MeteoView;
import com.apple.eawt.Application;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class Main extends JFrame{

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public Main(){
        try {
            Application macApp = Application.getApplication();
            macApp.setDockIconImage(new ImageIcon (getClass().getResource("icon.png")).getImage());
        }catch(Exception e){

        }
    }

    public static void main(String[] args) {
            new Main();

        try {
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    MeteoView.Launch(latch);
                    //Application.launch(FieldJavaFxView.class, new String[]{});
                }

            }).start();

            latch.await(); // wait start in FieldJavaFxView class finish, thx CountDownLatch
            //Thread.sleep(1000);


        } catch (InterruptedException e) {

        }


        MeteoView jfxView = MeteoView.getInstance();
        MainController controller = new MainController(jfxView);




        jfxView.initialize(controller);

        jfxView.display();

        // controller.displayViews();

        // set default value
    }

}