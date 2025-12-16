package com.vbforge.fundamentals.classes;

import static com.vbforge.fundamentals.classes.Tree.TRUNK_COLOR;

public class TreeMain {
    public static void main(String[] args) {

        Tree oakTree = new Tree(120, 12, TreeType.OAK);

        oakTree.announceTallTree();

        Tree mapleTree = new Tree(90, 30, TreeType.MAPLE);
        mapleTree.grow();

        mapleTree.announceTallTree();

        /*if (oakTree.height > 100){
            System.out.println("TreeClass " + oakTree.treeType + " is tall!");
        }

        if (mapleTree.height > 100){
            System.out.println("TreeClass " + mapleTree.treeType + " is tall!");
        }*/

        //using static attribute
        System.out.println(TRUNK_COLOR);
        //or static method
        Tree.announceTree();

    }
}
