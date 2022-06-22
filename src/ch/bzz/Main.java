package ch.bzz;

import ch.bzz.facade.MainFacade;
import ch.bzz.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFacade.getInstance();
        new MainFrame();
    }
}
