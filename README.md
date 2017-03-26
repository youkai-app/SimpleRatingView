# SimpleRatingView

An android custom view for emoji style rating selection

<p align="center">
<img src="https://cloud.githubusercontent.com/assets/2550945/24333705/24b04c7c-125d-11e7-84ee-aab6154a3874.gif" />
</p>

<p align="center"><i>The rating emoji in this GIF are not included with the library</i></p>

<p align="right">
<a href='https://github.com/youkai-app/SimpleRatingView/latest'><img height="48" alt='Get apk' src='https://cloud.githubusercontent.com/assets/2550945/21590907/dd74e0f0-d0ff-11e6-971f-d429148fd03d.png'/></a>
</p>

## Download
```gradle
    compile 'app.youkai.simpleratingview:library:1.0.0'
```
**Note:** You might have to add `jcenter()` to your repositories.

## Usage
Usage is extremely simple:
```xml
    <app.youkai.simpleratingview.SimpleRatingView
        android:id="@+id/simpleRatingView"
        android:layout_width="wrap_content"
        android:layout_height="64dp" />
```
**Note:** Rating items are wrapped by 8dp margins on all outer sides and scale up to match the parent height (retaining a 1:1 aspect ratio). 
```java
    simpleRatingView.setListener(new SimpleRatingView.OnRatingSelectedListener() {
        @Override
        public void onRatingSelected(SimpleRatingView.Rating rating) {
            currentRating.setText("Current rating: " + getString(rating.getStringRes()));
        }
    });

    .
    .
    .

    simpleRatingView.getRating();
```
## Customization
### Rating emoji
The "emoji" are [vector files](https://github.com/youkai-app/SimpleRatingView/tree/master/library/src/main/res/drawable) in the drawable resource directory. To use your own drawables, simply add them to your app's drawable directory and make sure their names match the names of the drawables in the library. This way, you will override the default drawables.

You can also override selection background drawables.
### Rating names
Just like the emoji drawables, you can override the strings too. All you have to do is to add strings with the same names to your own strings file. You can find all the strings in the library [here](https://github.com/youkai-app/SimpleRatingView/blob/master/library/src/main/res/values/strings.xml).

## License
```
Copyright (C) 2017  Abdullah Khwaja, İhsan Işık, Matthew Dias

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
Apache License Version 2.0 ([LICENSE](/LICENSE))