package com.company.sample.screens.main;

import com.company.sample.widgets.CustomButton;
import com.vaadin.ui.Layout;
import io.jmix.ui.components.AppWorkArea;
import io.jmix.ui.components.VBoxLayout;
import io.jmix.ui.components.Window;
import io.jmix.ui.screen.*;

import javax.annotation.Nullable;
import javax.inject.Inject;

@UiController("sample_MainScreen")
@UiDescriptor("sample-main-screen.xml")
@LoadDataBeforeShow
public class SampleMainScreen extends Screen implements Window.HasWorkArea {

    @Inject
    private AppWorkArea workArea;

    @Nullable
    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Inject
    private VBoxLayout customBtnBox;

    @Subscribe
    public void onInit(InitEvent event) {
        CustomButton button = new CustomButton();
        button.setColor("#ff00ff");
        button.setCaption("Custom Button");
        customBtnBox.unwrap(Layout.class).addComponent(button);
    }
}
