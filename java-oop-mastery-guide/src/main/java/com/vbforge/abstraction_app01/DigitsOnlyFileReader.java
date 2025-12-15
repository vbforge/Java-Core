package com.vbforge.abstraction_app01;

public class DigitsOnlyFileReader extends AbstractFileReader{

    public DigitsOnlyFileReader(String filePath) {
        super(filePath);
    }

    @Override
    protected String parseLine(String line) {
        return line.replaceAll("[^0-9]", "");
    }
}
