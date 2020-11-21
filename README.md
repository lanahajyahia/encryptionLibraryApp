# encryptionLibraryApp

[![](https://jitpack.io/v/lanahajyahia/encryptionLibraryApp.svg)](https://jitpack.io/#lanahajyahia/encryptionLibraryApp)

An encryption/decryption library for Text and ImageView.<br>
A ImageView will be converted to bitmap and bitmap to base64 string and then encrypted as a string<br>
in this library you can also encrypt/decrypt asynchronously.

## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
  implementation 'com.github.lanahajyahia:encryptionLibraryApp:1.00.01'
}
```
## Usage
![](assets/CustomToastDemo.gif)

first call:
```java                    
  Encryption encryption = null;
        try {
            encryption = Encryption.getInstance(getApplicationContext());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

```
To encrypt or decrypt a string call:
```java    

String string= "hello";
String encryptedString = encryption.AESEncryptionOrNull(string);
encryption.AESDecryptionOrNull(encryptedString);
```

To encrypt or decrypt an ImageView call:
```java    

ImageView image = findViewById(R.id.image);
String imgString = encryption.ImageViewEncryption(image);
image.setImageBitmap(encryption.ImageViewDecryption(imgString);

```



## License

    Copyright 2020 Lana Hajyahia

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
