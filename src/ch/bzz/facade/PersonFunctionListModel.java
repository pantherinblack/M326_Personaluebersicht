package ch.bzz.facade;

import ch.bzz.interfaces.ChangesModel;

import javax.swing.*;

public class PersonFunctionListModel extends DefaultListModel<String> implements ChangesModel {
    private String uuid;
    public PersonFunctionListModel(String uuid) {
        MainFacade.getInstance().addModel(this);
        this.uuid = uuid;
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
        return MainFacade.getInstance().getFunctionsByUuid(uuid).size();
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public String getElementAt(int index) {
        return MainFacade.getInstance().getFunctionsByUuid(uuid).get(index);
    }


    @Override
    public void setElementAt(String element, int index) {
        MainFacade.getInstance().addFunction(uuid, element, index);
    }
}
