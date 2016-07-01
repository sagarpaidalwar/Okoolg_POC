package com.atmecs.glooko.runner;

import java.io.File;

public class TestScriptPackage {

    String pack = "";

    public String getPack() {
        return pack;
    }

    public void getPackage(File node) {
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                getPackage(new File(node, filename));
            }
        } else if (node.isFile()) {
            pack = pack + "," + node.getParent().replace("\\", ".").split("java.")[1];

        }
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

}
