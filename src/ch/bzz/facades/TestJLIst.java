package ch.bzz.facades;

import javax.swing.*;

public class TestJLIst extends JList<String> {
    public TestJLIst() {
        super(new PersonNameListModel());
        getModel();
    }
}
