package com.zamp.zampaddressbook.models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchQuery {

    QueryType queryType;

    String query;

    String addressBookID;

}
