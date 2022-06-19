package ch.bzz.facade;

import ch.bzz.interfaces.ModelListener;
import ch.bzz.model.employees.Person;

import javax.swing.*;

public class PersonNameListModel extends DefaultListModel<String> implements ModelListener {
    private String name = null;
    private String function = null;
    private String department = null;
    private String team = null;
    private String sort = null;

    public PersonNameListModel() {
        MainFacade.getInstance().addModelListener(this);
    }

    /**
     * Returns the length of the list.
     *
     * @return the length of the list
     */
    @Override
    public int getSize() {
        return MainFacade.getInstance().sortPeople(name, function, department, team, sort).size();
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    @Override
    public String getElementAt(int index) {
        return MainFacade.getInstance().getPerson(index, name, function, department, team, sort).getFullName();
    }

    public void setElementAt(String element, int index) {
        Person person = MainFacade.getInstance().getPerson(index, name, function, department, team, sort);
        person.setFirstName(element.split(" ")[0]);
        person.setLastName(element.split(" ")[1]);
    }

    public String getUuid(int index) {
        return MainFacade.getInstance().getPerson(index, name, function, department, team, sort).getUuid();
    }

    public void setFilter(String name, String function, String department, String team, String sort) {
        this.name = name;
        this.function = function;
        this.department = department;
        this.team = team;
        this.sort = sort;
        MainFacade.getInstance().fire();
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

    public void removeElementAt(int index) {
        MainFacade.getInstance().removePerson(getUuid(index));
    }

    /**
     * removes the model from the MainFacade
     */
    public void remove() {
        MainFacade.getInstance().removeModelListener(this);
    }
}
