package com.company.sample.screen.main;

import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Window;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

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

}
