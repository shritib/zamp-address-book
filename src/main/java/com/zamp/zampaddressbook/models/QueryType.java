package com.zamp.zampaddressbook.models;

public enum QueryType {
    NAME, PHONE_NUMBER, GENERIC;

    public static QueryType getQueryTypeFromValue(String value) {
        if(QueryType.NAME.toString().equalsIgnoreCase(value))
            return QueryType.NAME;
        if(QueryType.PHONE_NUMBER.toString().equalsIgnoreCase(value))
            return QueryType.PHONE_NUMBER;
        if(QueryType.GENERIC.toString().equalsIgnoreCase(value))
            return QueryType.GENERIC;
        return QueryType.GENERIC;
    }

}
