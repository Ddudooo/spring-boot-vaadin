package com.example.vaadin.domain.page.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;

@Route("test")
public class TestView extends Div {

    @JsonIgnore
    private static final long serialVersionUID = 6529115447324019653L;

    public TestView() {
        add(
            new Button(
                "Click me!",
                event -> {
                    Notification.show("Hello world!");
                }
            )
        );
    }
}
