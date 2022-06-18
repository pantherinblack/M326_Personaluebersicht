package ch.bzz.facade;

import ch.bzz.interfaces.ChangesModel;
import ch.bzz.model.company.Department;

import javax.swing.*;

/**
 * Model representing the base-date of the application
 * @author Kevin
 * @since 18.06.2022
 * @version 1.0
 */
public class DataListModel extends DefaultComboBoxModel<String> implements ChangesModel {
    private final int mode;
    public static final int MODE_FUNCTION = 0;
    public static final int MODE_TEAM = 1;
    public static final int MODE_DEPARTMENT = 2;

    /**
     * constructor sets the mode of the Model
     * @param mode to be used
     */
    public DataListModel(int mode) {
        MainFacade.getInstance().addModel(this);
        this.mode = mode;
    }

    /**
     * removes the model from the MainFacade
     */
    public void remove() {
        MainFacade.getInstance().removeModel(this);
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
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    @Override
    public int getSize() {
        switch (mode) {
            case 0:
                return MainFacade.getInstance().getAllFunctions().size();
            case 1:
                return MainFacade.getInstance().getAllTeams().size();
            case 2:
                return MainFacade.getInstance().getAllDepartments().size();
        }
        return 0;
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public String getElementAt(int index) {
        switch (mode) {
            case 0:
                return MainFacade.getInstance().getAllFunctions().get(index);
            case 1:
                return MainFacade.getInstance().getAllTeams().get(index);
            case 2:
                return MainFacade.getInstance().getAllDepartments().get(index).getName();
        }
        return null;
    }

    /**
     * adds am element
     * @param element   the component to be added
     */
    public void addElement(String element) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().getAllFunctions().add(element);
                break;
            case 1:
                MainFacade.getInstance().getAllTeams().add(element);
                break;
            case 2:
                MainFacade.getInstance().getAllDepartments().add(new Department(element));
                break;
        }
    }

    /**
     * removes am element at a position
     * @param index   the index of the object to remove
     */
    public void removeElementAt(int index) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().getAllFunctions().remove(index);
                break;
            case 1:
                MainFacade.getInstance().getAllTeams().remove(index);
                break;
            case 2:
                MainFacade.getInstance().getAllDepartments().remove(index);
                break;
        }
    }
}
