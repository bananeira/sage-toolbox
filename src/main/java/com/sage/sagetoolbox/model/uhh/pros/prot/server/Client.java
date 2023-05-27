package com.sage.sagetoolbox.model.uhh.pros.prot.server;

import java.util.ArrayList;

public class Client {

    public String id;
    public ArrayList<String> questions = new ArrayList<>();
    public ArrayList<String> answers = new ArrayList<>();

    public Client(String id) {

        this.id = id;

    }

}