package ch.bzz.facade;

import ch.bzz.interfaces.ModelListener;

import javax.swing.*;

public class HRPersonBoxModel extends DefaultComboBoxModel<String> implements ModelListener {
    public HRPersonBoxModel() {
        MainFacade.getInstance().addModelListener(this);
    }

    /**
     * removes the model from the MainFacade
     */
    public void removeSelf() {
        MainFacade.getInstance().addModelListener(this);
    }

    /**
     * @param source the <code>ListModel</code> that changed, typically "this"
     * @param index0 one end of the new interval
     * @param index1 the other end of the new interval
     */
    @Override
    public void fireContentsChanged(Object source, int index0, int index1) {
        if (index1 == -1) super.fireContentsChanged(source, index0, getSize());
        else super.fireContentsChanged(source, index0, index1);
    }

    public int getSize() {
        return MainFacade.getInstance().getHRPeople().size();
    }

    public String getElementAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getFullName();
    }

    public String getPasswordAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getPwd();
    }

    public String getUuidAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getUuid();
    }


    public int getModeAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getModus();
    }
}
