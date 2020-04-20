# sample-new-project

An example of extending built-in Jmix themes

## Theme extension

Jmix provides built-in compiled themes for convenience and demo purpose. In real projects, themes must be extended.

En extended theme must comply with the following requirements:

* A theme must have a unique name
* A theme with its resources must be located in the `src/main/themes/<theme-name>` directory
* `styles.scss` is an entry point to compile the theme

```
@import "custom-theme-defaults";
@import "addons";
@import "custom-theme";
 
.custom-theme {
  @include addons;
  @include custom-theme;
}
```
where `addons` is a special file that is automatically generated based on the added theme add-ons.

* To compile a custom theme, the following dependencies must be defined for the `themes` configuration in `build.gradle`:

```
dependencies {
    themes 'io.jmix.ui-themes:jmix-ui-themes:1.0-SNAPSHOT'
}
```

* The theme name to use is defined in the `application.properties` file:

```
cuba.web.theme = custom-theme
```ˆ
