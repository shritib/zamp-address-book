package com.zamp.zampaddressbook.processor;

import com.zamp.zampaddressbook.AddressBookApplication;
import com.zamp.zampaddressbook.models.*;
import com.zamp.zampaddressbook.services.IAddressBookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RequestProcessor {

    private final IAddressBookService addressBookService;

    public RequestProcessor(IAddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    public void processRequests(InputStream is) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null;) {
            String[] data = line.split(" ");
            String instructionType = data[0];
            if("CREATE_ADDRESS_BOOK".equalsIgnoreCase(instructionType)) {
                createAddressBook(data[1]);
            } else if ("ADD_CONTACT".equalsIgnoreCase(instructionType)) {
                addContact(data[1]);
            } else if("SEARCH_CONTACT".equalsIgnoreCase(instructionType)) {
                searchContact(data[1], data[2], data[3]);
            }
        }
    }

    private void searchContact(String queryType, String addressBookName, String searchQueryValue) {
        AddressBook addressBook = addressBookService.getAddressBookByName(addressBookName);
        SearchQuery searchQuery = SearchQuery.builder()
                .addressBookID(addressBook.getId())
                .queryType(QueryType.getQueryTypeFromValue(queryType))
                .query(searchQueryValue)
                .build();
        List<Contact> contacts = addressBookService.searchContacts(searchQuery);
        System.out.printf("Search query result, query: %s, result: %s\n", searchQuery, contacts);
    }

    private void addContact(String data) {
        String[] fields = data.split(",");
        AddressBook addressBook = addressBookService.getAddressBookByName(fields[0]);
        Contact contact = Contact.builder()
                .firstName(fields[1])
                .lastName(fields[2])
                .phoneNumber(fields[3])
                .address(
                        Address.builder()
                            .streetAddress1(fields[4])
                            .streetAddress2(fields[5])
                            .city(fields[6])
                            .state(fields[7])
                            .countryISO2(fields[8])
                            .zipCode(fields[9])
                        .build()
                ).build();
        Contact result = addressBookService.addContact(addressBook.getId(), contact);
        System.out.printf("Added contact: %s, %s\n",  addressBook.getAddressBookName(), result);
    }

    private void createAddressBook(String data) {
        String[] fields = data.split(",");
        AddressBook addressBook = AddressBook.builder()
                .addressBookName(fields[0])
                .createdByEmail(fields[1])
                .build();
        String addressBookId = addressBookService.createAddressBook(addressBook);
        System.out.printf("Created address book: %s\n", addressBookId);
    }
}
