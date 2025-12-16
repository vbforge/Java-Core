package com.vbforge.oop_pillars.abstraction.abstract_classes.app01;

public class DigitsOnlyFileReader extends AbstractFileReader{

    public DigitsOnlyFileReader(String filePath) {
        super(filePath);
    }

    @Override
    protected String parseLine(String line) {
        return line.replaceAll("[^0-9]", "");
    }
}
