# AdiImageView

AdiImageView is a custom ImageView for Android with a built-in loading indicator and simplified functions to load images from URLs or Android resources. It also supports setting corner radius, making it versatile for use as a circular image view.

## Features
- Load images from URLs, Android resources, or URIs.
- Display a loading indicator while fetching images.
- Customize corner radius for images.
- Use as a circular image view by setting corner radius to half of the view size.
- Handle errors with a default error image.

## Installation

Add the following dependency in your `build.gradle` file:

```groovy
implementation("io.github.adityagaikwad-1257:adiimageview:1.0.1")
```

## Usages in layouts

```xml
<com.ag.adiimageview.AdiImageView
    android:id="@+id/image"
    android:layout_width="200dp"
    android:layout_height="200dp"

    app:srcCompat="@drawable/ic_launcher_background"
    app:loadingBackground="@color/white"
    app:imageCornerRadius="5dp"
    app:errorSrc="@drawable/bg_jw_error" />
```

## Usage in Java/Kotlin

```kotlin
// Load Android resource
binding.image.loadImage(R.drawable.ic_launcher_background)

// Load image Uri
binding.image.loadImage(Uri.parse("<your-image-path>"))

// Load Android Drawable
binding.image.loadImage(AppCompatResources.getDrawable(this, R.drawable.ic_launcher_background))

// Load remote image url
binding.image.loadImage("<your-image-url>")
```

