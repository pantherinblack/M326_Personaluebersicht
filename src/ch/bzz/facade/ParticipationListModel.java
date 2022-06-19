package ch.bzz.facade;

import ch.bzz.interfaces.ModelListener;

import javax.swing.*;

/**
 * Model representing the functions or teams of a person, defined by an uuid
 *
 * @author Kevin
 * @version 1.5
 * @since 17.06.2022
 */
public class ParticipationListModel extends DefaultListModel<String> implements ModelListener {
    public static final int MODE_FUNCTION = 0;
    public static final int MODE_TEAM = 1;
    private final String uuid;
    private final int mode;

    /**
     * constructor for the ListModel, for a person
     *
     * @param uuid of the person
     */
    public ParticipationListModel(String uuid, int mode) {
        MainFacade.getInstance().addModelListener(this);
        this.uuid = uuid;
        this.mode = mode;
    }

    /**
     * updates elements
     *
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
                return MainFacade.getInstance().getFunctionsByUuid(uuid).size();
            case 1:
                return MainFacade.getInstance().getTeamsByUuid(uuid).size();
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
                return MainFacade.getInstance().getFunctionsByUuid(uuid).get(index);
            case 1:
                return MainFacade.getInstance().getTeamsByUuid(uuid).get(index);
        }
        return null;
    }


    /**
     * Sets the element at the specific position
     *
     * @param element what the component is to be set to
     * @param index   the specified index
     */
    @Override
    public void setElementAt(String element, int index) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().setFunctionAtPerson(uuid, element, index);
                break;
            case 1:
                MainFacade.getInstance().setTeamAtPerson(uuid, element, index);
                break;
        }
    }

    /**
     * adds an element
     *
     * @param element the component to be added
     */
    public void addElement(String element) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().addFunctionAtPerson(uuid, element);
                break;
            case 1:
                MainFacade.getInstance().addTeamAtPerson(uuid, element);
        }
    }

    /**
     * removes a specific element using an index
     *
     * @param index the index of the object to remove
     */
    public void removeElementAt(int index) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().removeFunctionAtPerson(uuid, getElementAt(index));
                break;
            case 1:
                MainFacade.getInstance().removeTeamAtPerson(uuid, getElementAt(index));
        }
    }

    /**
     * removes the model from the MainFacade
     */
    public void remove() {
        MainFacade.getInstance().removeModelListener(this);
    }
}
