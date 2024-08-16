package com.qualifierAnnotasion;

import org.springframework.stereotype.Component;

@Component("excel")
public class ExcelFileReader implements Reader {

    @Override
    public String readFile() {
        return "Excel File";
    }
}
