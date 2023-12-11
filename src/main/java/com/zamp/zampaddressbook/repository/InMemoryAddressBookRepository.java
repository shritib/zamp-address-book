package com.zamp.zampaddressbook.repository;

import com.zamp.zampaddressbook.models.AddressBook;
import com.zamp.zampaddressbook.models.Contact;
import com.zamp.zampaddressbook.models.QueryType;
import com.zamp.zampaddressbook.models.SearchQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class InMemoryAddressBookRepository implements AddressBookRepository {

    private final Map<String, AddressBook> addressBookMap;
    private final Map<String, String> addressBookNameToIdMap;
    private final AtomicInteger addressBookIdGenerator;
    private final AtomicInteger contactIdGenerator;

    private final Map<String, List<Contact>> contactsMap;

    public InMemoryAddressBookRepository() {
        addressBookMap = new HashMap<>();
        addressBookNameToIdMap = new HashMap<>();
        addressBookIdGenerator = new AtomicInteger(1);
        contactIdGenerator = new AtomicInteger(1);
        contactsMap = new HashMap<>();
    }

    @Override
    public String createAddressBook(AddressBook addressBook) {
        if(addressBookMap.containsKey(addressBook.getAddressBookName()))
            throw new RuntimeException("Address book already exists");
        long now = System.currentTimeMillis();
        addressBook.setCreatedAtMillis(now);
        addressBook.setUpdatedAtMillis(now);
        addressBook.setId(String.valueOf(addressBookIdGenerator.getAndAdd(1)));
        addressBookMap.put(addressBook.getId(), addressBook);
        addressBookNameToIdMap.put(addressBook.getAddressBookName(), addressBook.getId());
        return addressBook.getId();
    }

    @Override
    public AddressBook getAddressBookByName(String name) {
        if(!addressBookNameToIdMap.containsKey(name))
            throw new RuntimeException("Address book with name not found");
        String id = addressBookNameToIdMap.get(name);
        return addressBookMap.get(id);
    }

    @Override
    public Contact addContact(String addressBookID, Contact contact) {
        List<Contact> contacts = contactsMap.get(addressBookID);
        if(contacts == null)
            contacts = new ArrayList<>();
        contact.setContactId(String.valueOf(contactIdGenerator.getAndAdd(1)));
        contacts.add(contact);
        contactsMap.put(addressBookID, contacts);
        return contact;
    }

    @Override
    public List<Contact> searchContacts(SearchQuery searchQuery) {
        List<Contact> contacts = contactsMap.getOrDefault(searchQuery.getAddressBookID(), Collections.emptyList());
        String searchQueryLowerCase = searchQuery.getQuery().toLowerCase();

        Predicate<Contact> predicate;
        if(searchQuery.getQueryType().equals(QueryType.NAME)) {
            predicate = getNameSearchPredicate(searchQueryLowerCase);
        } else if(searchQuery.getQueryType().equals(QueryType.PHONE_NUMBER)) {
            predicate = getPhoneNumberSearchPredicate(searchQueryLowerCase);
        } else {
            predicate = getNameSearchPredicate(searchQueryLowerCase).or(
                    getPhoneNumberSearchPredicate(searchQueryLowerCase)
            );
        }
        return contacts.stream().filter(predicate).toList();
    }

    private Predicate<Contact> getNameSearchPredicate(String query) {
        return c -> c.getFirstName().toLowerCase().startsWith(query) ||
                c.getLastName().toLowerCase().startsWith(query);
    }

    private Predicate<Contact> getPhoneNumberSearchPredicate(String query) {
        return c -> c.getPhoneNumber().toLowerCase().startsWith(query);
    }
}
