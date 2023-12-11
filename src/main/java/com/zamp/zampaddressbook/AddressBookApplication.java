package com.zamp.zampaddressbook;

import com.zamp.zampaddressbook.models.AddressBook;
import com.zamp.zampaddressbook.processor.RequestProcessor;
import com.zamp.zampaddressbook.repository.AddressBookRepository;
import com.zamp.zampaddressbook.repository.InMemoryAddressBookRepository;
import com.zamp.zampaddressbook.services.AddressBookService;
import com.zamp.zampaddressbook.services.IAddressBookService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AddressBookApplication {

    private static RequestProcessor requestProcessor;

    public static void main(String[] args) throws IOException {
        System.out.println("App launched");
        bootstrap();
        InputStream inputStream = AddressBookApplication.class.getResourceAsStream("address_book_instructions.txt");
        requestProcessor.processRequests(inputStream);
    }

    private static void bootstrap() {
        AddressBookRepository addressBookRepository = new InMemoryAddressBookRepository();
        IAddressBookService addressBookService = new AddressBookService(addressBookRepository);
        requestProcessor = new RequestProcessor(addressBookService);
    }
}