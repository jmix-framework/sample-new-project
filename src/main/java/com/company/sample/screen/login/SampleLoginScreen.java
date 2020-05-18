package com.company.sample.screen.login;

import io.jmix.core.CoreProperties;
import io.jmix.core.Messages;
import io.jmix.core.security.ClientDetails;
import io.jmix.core.security.SecurityContextHelper;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiProperties;
import io.jmix.ui.actions.Action;
import io.jmix.ui.components.LookupField;
import io.jmix.ui.components.PasswordField;
import io.jmix.ui.components.TextField;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.inject.Inject;
import java.util.Locale;

@UiController("sample_LoginScreen")
@UiDescriptor("sample-login-screen.xml")
@Route(path = "login", root = true)
public class SampleLoginScreen extends Screen {

    @Inject
    private TextField<String> usernameField;

    @Inject
    private PasswordField passwordField;

    @Inject
    private LookupField<Locale> localesField;

    @Inject
    private Notifications notifications;

    @Inject
    private Messages messages;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private CoreProperties coreProperties;

    @Inject
    private UiProperties uiProperties;

    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    private void onInit(InitEvent event) {
        usernameField.focus();
        initLocalesField();
        initDefaultCredentials();
    }

    private void initLocalesField() {
        localesField.setOptionsMap(coreProperties.getAvailableLocales());
        localesField.setValue(coreProperties.getAvailableLocales().values().iterator().next());
    }

    private void initDefaultCredentials() {
        usernameField.setValue("admin");
        passwordField.setValue("admin");
    }

    @Subscribe("submit")
    private void onSubmitActionPerformed(Action.ActionPerformedEvent event) {
        login();
    }

    private void login() {
        String username = usernameField.getValue();
        String password = passwordField.getValue() != null ? passwordField.getValue() : "";

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messages.getMessage(getClass(), "emptyUsernameOrPassword"))
                    .show();
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            ClientDetails clientDetails = ClientDetails.builder()
                    .locale(localesField.getValue())
                    .build();
            authenticationToken.setDetails(clientDetails);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHelper.setAuthentication(authentication);

            String mainScreenId = uiProperties.getMainScreenId();
            screenBuilders.screen(this)
                    .withScreenId(mainScreenId)
                    .withOpenMode(OpenMode.ROOT)
                    .build()
                    .show();
        } catch (BadCredentialsException e) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(messages.getMessage(getClass(), "loginFailed"))
                    .withDescription(e.getMessage())
                    .show();
        }
    }
}
