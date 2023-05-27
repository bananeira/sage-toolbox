package com.sage.sagetoolbox.controller.uhh.pros.prot.server;

import com.sage.sagetoolbox.model.uhh.pros.prot.server.Client;
import com.sage.sagetoolbox.model.uhh.pros.prot.server.Feedback;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("")
public class ProsProtAPIController {

    private final ArrayList<Client> clients = new ArrayList<>();

    private boolean feedbackAllowed = true;
    private final Feedback feedback = new Feedback();


    @GetMapping(path = "internal/messages", produces = "application/json")
    public ArrayList<Client> getMessages() {

        System.out.println("Internal message request");
        return this.clients;

    }

    @PostMapping(path = "internal/messages")
    public void onMessage(@RequestParam("id") String id, @RequestParam("a") String answer) {

        Client c = getClient(id);

        if (c == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }

        c.answers.add(answer);

    }

    @GetMapping(path = "internal/feedback", produces = "application/json")
    public Feedback getFeedback() {

        return this.feedback;

    }


    @PostMapping(path = "internal/feedback")
    public void onFeedback(@RequestParam(name = "off", required = false) String off) {

        this.feedbackAllowed = (off == null);

    }

    @PostMapping(path = "connect")
    public void onConnect(@RequestParam("id") String id) {

        Client c = new Client(id);

        this.clients.add(c);

    }

    @GetMapping(path = "client")
    public Client onClient(@RequestParam("id") String id) {


        Client c = getClient(id);

        if (c == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }

        return c;

    }

    @PostMapping(path = "ask")
    public void onAsk(@RequestParam(name = "id") String id, @RequestParam(name = "q") String question) {

        Client c = getClient(id);

        if (c == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }

        c.questions.add(question);

    }

    @GetMapping(path = "feedback")
    public String getFeedbackAllowed() {

        return "{\"feedback\":" + feedbackAllowed + "}";

    }

    @PostMapping(path = "feedback")
    public void onFeedbackAdd(

            @RequestParam(name = "id") String id,
            @RequestParam(name = "s") String s,
            @RequestParam(name = "w") String w,
            @RequestParam(name = "o") String o,
            @RequestParam(name = "t") String t

    ) {

        this.feedback.ids.add(id);
        this.feedback.strengths.add(s);
        this.feedback.weaknesses.add(w);
        this.feedback.opportunities.add(o);
        this.feedback.threads.add(t);

    }

    private Client getClient(String id) {

        for (Client c : clients) {

            if (c.id.equals(id)) {

                return c;

            }

        }

        return null;

    }

}
