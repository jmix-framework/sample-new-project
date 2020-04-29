# sample-new-project

An example of extending built-in Jmix widgets

## Widgets extension

* To compile a custom widgetset, you need to declare the following dependencies in the `widgets` and `implementation` configurations and specify the desired widgetset name in `build.gradle`:

```
// Required only for developing custom client-side components
implementation('io.jmix.ui-widgets:jmix-ui-widgets:1.0-SNAPSHOT')

widgets 'io.jmix.ui-widgets:jmix-ui-widgets:1.0-SNAPSHOT'

compileWidgets {
    generate 'com.company.sample.widgets.CustomWidgetSet'
}
```

* The WidgetSet name to use is defined in the `application.properties` file:

```
cuba.web.widgetSet = com.company.sample.widgets.CustomWidgetSet
```

* Exclude `slf4j-jdk14` dependency from the **implementation** configuration:

```
configurations {
    implementation {
        exclude group: 'org.slf4j', module: 'slf4j-jdk14'   // Required only for debug configuration
    }
}
```
