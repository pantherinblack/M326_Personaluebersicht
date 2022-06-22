package ch.bzz.testing;

import ch.bzz.view.component.BasicPerson;

import javax.swing.*;

public class BasicPersonTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new BasicPerson(BasicPerson.MODE_SHOW));
        frame.setVisible(true);
        frame.pack();
    }
}
