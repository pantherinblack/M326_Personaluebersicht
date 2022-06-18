package ch.bzz.facade;

import ch.bzz.interfaces.ChangesModel;

import javax.swing.*;

/**
 * Model representing the functions or teams of a person, defined by an uuid
 * @author Kevin
 * @version 1.5
 * @since 17.06.2022
 */
public class ParticipationListModel extends DefaultListModel<String> implements ChangesModel {
    private final String uuid;
    private final int mode;
    public static final int MODE_FUNCTION = 0;
    public static final int MODE_TEAM = 1;

    /**
     * constructor for the ListModel, for a person
     * @param uuid of the person
     */
    public ParticipationListModel(String uuid, int mode) {
        MainFacade.getInstance().addModel(this);
        this.uuid = uuid;
        this.mode = mode;
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
     * @param element what the component is to be set to
     * @param index   the specified index
     */
    @Override
    public void setElementAt(String element, int index) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().setFunction(uuid, element, index);
                break;
            case 1:
                MainFacade.getInstance().setTeam(uuid,element,index);
                break;
        }
    }

    public void addElement(String element) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().addFunction(uuid,element);
                break;
            case 1:
                MainFacade.getInstance().addTeam(uuid,element);
        }
    }

    public void removeElementAt(int index) {
        switch (mode) {
            case 0:
                MainFacade.getInstance().removeFunction(uuid,getElementAt(index));
                break;
            case 1:
                MainFacade.getInstance().removeTeam(uuid,getElementAt(index));
        }
    }

    /**
     * removes the model from the MainFacade
     */
    public void remove() {
        MainFacade.getInstance().removeModel(this);
    }
}
