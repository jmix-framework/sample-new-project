package com.company.sample.screen.main;

import io.jmix.core.Messages;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.ThemeResource;
import io.jmix.ui.component.Window;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;

@UiController("sample_MainScreen")
@UiDescriptor("sample-main-screen.xml")
@LoadDataBeforeShow
public class SampleMainScreen extends Screen implements Window.HasWorkArea {

    private static final String APP_LOGO_IMAGE = "application.logoImage";
    
    @Inject
    private AppWorkArea workArea;

    @Subscribe
    public void onInit(InitEvent event) {
        initLogoImage();
    }

    @Nullable
    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    private void initLogoImage() {
        Image logoImage = (Image) getWindow().getComponent("logoImage");
        String logoImagePath = getBeanLocator().get(Messages.class)
                .getMessage(APP_LOGO_IMAGE);

        if (logoImage != null
                && StringUtils.isNotBlank(logoImagePath)
                && !APP_LOGO_IMAGE.equals(logoImagePath)) {
            logoImage.setSource(ThemeResource.class).setPath(logoImagePath);
        }
    }
}
