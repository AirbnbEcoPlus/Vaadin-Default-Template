package fr.endide.application.views.moncompte;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.endide.application.data.entity.User;
import fr.endide.application.data.service.UserRepository;
import fr.endide.application.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.security.RolesAllowed;

@PageTitle("Mon Compte")
@Route(value = "mon-compte", layout = MainLayout.class)
@RolesAllowed({"ADMIN","USER"})
@Uses(Icon.class)
public class MonCompteView extends Div {

    private TextField firstName = new TextField("Prénom");
    private TextField lastName = new TextField("Nom de famille");
    private PasswordField password = new PasswordField("Mot de Passe");
    private PasswordField repassword = new PasswordField("Retaper le Mot de Passe");
    private Button save = new Button("Save");

    UserRepository service;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();

    private Component createTitle() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName().toString();
        return new H3("Mon compte : " + currentPrincipalName);
    }
    @Autowired
    public MonCompteView(UserRepository service){
        addClassName("mon-compte-view");
        this.service = service;
        User user = service.findByEmail(currentPrincipalName);
        firstName.setValue(user.getFirstName());
        lastName.setValue(user.getLastName());
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if(!password.isEmpty() || !repassword.isEmpty()) {
                if(password.getValue().equals(repassword.getValue())) {
                    user.setHashedPassword(new BCryptPasswordEncoder().encode(password.getValue()));
                } else {
                    Notification.show("Les mots de passe ne correspondent pas");
                }
            }
            user.setFirstName(firstName.getValue());
            user.setLastName(lastName.getValue());
            service.save(user);
            Notification.show("Saved");
        });

        buttonLayout.add(save);
        add(createTitle(), createFormLayout(), buttonLayout);
    }


    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.add(firstName, lastName, password, repassword);
        return formLayout;
    }

}
