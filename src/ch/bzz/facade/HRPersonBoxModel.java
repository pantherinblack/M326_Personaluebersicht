package ch.bzz.facade;

import ch.bzz.interfaces.ModelListener;

import javax.swing.*;

/**
 * Supplies a Combobox or a list with the needed Model, to utilize the data of hr-people
 * @author Kevin
 * @since 20.06.2022
 * @version 1.0
 */
public class HRPersonBoxModel extends DefaultComboBoxModel<String> implements ModelListener {

    /**
     * adds listener to the MainFacade
     */
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

    /**
     * returns the number of hr-people
     * @return size
     */
    public int getSize() {
        return MainFacade.getInstance().getHRPeople().size();
    }

    /**
     * gives back the name of the person requested.
     * @param index the requested index
     * @return name
     */
    public String getElementAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getFullName();
    }

    /**
     * gives back the password of the requested person
     * @param index the requested index
     * @return password
     */
    public String getPasswordAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getPwd();
    }

    /**
     * gives back the uuid of the person for further use.
     * @param index the requested index
     * @return uuid
     */
    public String getUuidAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getUuid();
    }

    /**
     * giives back the mode of the person requested
     * @param index the requested index
     * @return mode
     */
    public int getModeAt(int index) {
        return MainFacade.getInstance().getHRPeople().get(index).getModus();
    }
}
