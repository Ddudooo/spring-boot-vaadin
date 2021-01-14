package com.example.vaadin.domain.customer.router;

import com.example.vaadin.domain.customer.component.CustomerEditor;
import com.example.vaadin.domain.customer.model.Customer;
import com.example.vaadin.domain.customer.repo.CustomerRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route
public class MainView extends VerticalLayout {

    @JsonIgnore
    private static final long serialVersionUID = 7564533820008054012L;

    private final CustomerRepo repo;
    private final CustomerEditor editor;
    private final Grid<Customer> grid;
    private final TextField filter;
    private final Button addNewBtn;

    public MainView(CustomerRepo repo, CustomerEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Customer.class);
        this.filter = new TextField();
        this.addNewBtn =
            new Button(
                "New Customer",
                VaadinIcon.PLUS.create()
            );

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("uuid", "firstName", "lastName");
        grid.getColumnByKey("uuid").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by last name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        // Initialize listing
        listCustomers(StringUtils.EMPTY);
    }

    private void listCustomers() {
        grid.setItems(repo.findAll());
    }

    private void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            listCustomers();
        } else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
