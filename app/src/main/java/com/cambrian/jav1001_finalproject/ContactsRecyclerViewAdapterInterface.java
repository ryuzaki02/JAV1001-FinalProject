package com.cambrian.jav1001_finalproject;

/**
 * This interface holds definition for methods to be used when contact is tapped or deleted
 */
public interface ContactsRecyclerViewAdapterInterface {
    /**
     * Method of delete contact
     * @param contactModel: ContactModel
     */
    void deleteContact(ContactModel contactModel);
    /**
     * Method of contact tap action
     * @param contactModel: ContactModel
     */
    void contactDidTap(ContactModel contactModel);
}
