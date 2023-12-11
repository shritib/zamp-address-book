package com.zamp.zampaddressbook.models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressBook {

    String id;

    String addressBookName;

    String createdByEmail;

    Long createdAtMillis;

    Long updatedAtMillis;
}
