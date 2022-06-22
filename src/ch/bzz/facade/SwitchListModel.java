package ch.bzz.facade;

import ch.bzz.interfaces.ModelListener;
import ch.bzz.util.StringListCompare;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * mode for the SwitchList
 *
 * @author Kevin
 * @version 1.2
 * @since 21.06.2022
 */
public class SwitchListModel extends AbstractListModel<String> implements ModelListener {
    public static final int SIDE_LEFT = 0;
    public static final int SIDE_RIGHT = 1;
    public static final int MODE_FUNCTION = 0;
    public static final int MODE_TEAM = 1;
    private final int mode;
    private final int side;
    private String uuid;
    private final MainFacade mF;

    /**
     * initializes the object and defines working parameters.
     *
     * @param side on which it will be shown
     * @param mode what will be shown
     * @param uuid of a person (if needed)
     */
    public SwitchListModel(int side, int mode, String uuid) {
        this.side = side;
        this.mode = mode;
        this.uuid = uuid;
        mF = MainFacade.getInstance();
        mF.addModelListener(this);
        mF.fire();
    }

    /**
     * sets the uuid;
     *
     * @param uuid to be set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
        mF.fire();
    }

    /**
     * removes the listener in the MainFacade
     */
    public void removeSelf() {
        MainFacade.getInstance().removeModelListener(this);
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
                switch (side) {
                    case 0:
                        return mF.getFunctionsByUuid(uuid).size();
                    case 1:
                        Vector<String> all = new Vector<>(new ArrayList<>(mF.getAllFunctions()));
                        Vector<String> person = mF.getFunctionsByUuid(uuid);
                        all.removeIf(function -> StringListCompare.stringContains(person, function));
                        return all.size();
                }
            case 1:
                switch (side) {
                    case 0:
                        return mF.getTeamsByUuid(uuid).size();
                    case 1:
                        Vector<String> all = new Vector<>(new ArrayList<>(mF.getAllTeams()));
                        Vector<String> person = mF.getTeamsByUuid(uuid);
                        all.removeIf(team -> StringListCompare.stringContains(person, team));
                        return all.size();
                }
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
                switch (side) {
                    case 0:
                        return mF.getFunctionsByUuid(uuid).get(index);
                    case 1:
                        Vector<String> all = new Vector<>(new ArrayList<>(mF.getAllFunctions()));
                        Vector<String> person = mF.getFunctionsByUuid(uuid);
                        all.removeIf(function -> StringListCompare.stringContains(person, function));
                        return all.get(index);
                }
            case 1:
                switch (side) {
                    case 0:
                        return mF.getTeamsByUuid(uuid).get(index);
                    case 1:
                        Vector<String> all = new Vector<>(new ArrayList<>(mF.getAllTeams()));
                        Vector<String> person = mF.getTeamsByUuid(uuid);
                        all.removeIf(team -> StringListCompare.stringContains(person, team));
                        return all.get(index);
                }
        }
        return null;
    }

    /**
     * add an element
     *
     * @param element to be added
     */
    public void addElement(String element) {
        if (side == 0) {
            switch (mode) {
                case 0:
                    mF.addFunctionAtPerson(uuid, element);
                    break;
                case 1:
                    mF.addTeamAtPerson(uuid, element);
                    break;
            }
        }
    }

    /**
     * removes an element
     *
     * @param element to be removed
     */
    public void removeElement(String element) {
        if (side == 0) {
            switch (mode) {
                case 0:
                    mF.removeFunctionAtPerson(uuid, element);
                case 1:
                    mF.removeTeamAtPerson(uuid, element);
            }
        }
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
}
