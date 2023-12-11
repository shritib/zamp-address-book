package com.zamp.zampaddressbook.services;

import com.zamp.zampaddressbook.models.AddressBook;
import com.zamp.zampaddressbook.models.Contact;
import com.zamp.zampaddressbook.models.SearchQuery;
import com.zamp.zampaddressbook.repository.AddressBookRepository;

import java.util.List;

public class AddressBookService implements IAddressBookService {

    private final AddressBookRepository addressBookRepository;

    public AddressBookService(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @Override
    public String createAddressBook(AddressBook addressBook) {
        return addressBookRepository.createAddressBook(addressBook);
    }

    @Override
    public AddressBook getAddressBookByName(String addressBookName) {
        return addressBookRepository.getAddressBookByName(addressBookName);
    }

    @Override
    public Contact addContact(String addressBookID, Contact contact) {
        return addressBookRepository.addContact(addressBookID, contact);
    }

    @Override
    public List<Contact> searchContacts(SearchQuery searchQuery) {
        return addressBookRepository.searchContacts(searchQuery);
    }
}
