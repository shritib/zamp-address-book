package com.zamp.zampaddressbook.repository;

import com.zamp.zampaddressbook.models.AddressBook;
import com.zamp.zampaddressbook.models.Contact;
import com.zamp.zampaddressbook.models.SearchQuery;

import java.util.List;

public interface AddressBookRepository {

    String createAddressBook(AddressBook addressBook);

    AddressBook getAddressBookByName(String name);

    Contact addContact(String addressBookID, Contact contact);

    List<Contact> searchContacts(SearchQuery searchQuery);
}
