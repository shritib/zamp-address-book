package com.zamp.zampaddressbook.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Address {

    String streetAddress1;

    String streetAddress2;

    String city;

    String state;

    String countryISO2;

    String zipCode;
}
