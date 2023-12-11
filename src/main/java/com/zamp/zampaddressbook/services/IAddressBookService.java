package com.zamp.zampaddressbook.services;

import com.zamp.zampaddressbook.models.AddressBook;
import com.zamp.zampaddressbook.models.Contact;
import com.zamp.zampaddressbook.models.SearchQuery;

import java.util.List;

public interface IAddressBookService {
    String createAddressBook(AddressBook addressBook);

    AddressBook getAddressBookByName(String addressBookName);

    Contact addContact(String addressBookID, Contact contact);

    List<Contact> searchContacts(SearchQuery searchQuery);
}
