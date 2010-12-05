package com.mp.domain.access

enum AccessFilterType {

    UNRESTRICTED_ACCESS('UNRESTRICTED_ACCESS'),
    SUBSCRIPTION('SUBSCRIPTION'),
    PERMISSION('PERMISSION')

    final String name

    AccessFilterType(String name) {
        this.name = name
    }

    public String toString() {
        return name
    }

}
