package com.vbforge.fundamentals.keywords.this_keyword;

public class ThisKeywordMain {
    public static void main(String[] args) {

        ThisKeyword thisKeyword = new ThisKeyword(10, 20);
        thisKeyword.display();

        //or

        ThisKeyword thisKeyword2 = new ThisKeyword();
        thisKeyword2.setData(30, 40);
        thisKeyword2.display();

    }
}
