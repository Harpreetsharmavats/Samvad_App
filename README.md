# 📱 Samvad App with Stream SDK

A sleek and modern chat application built using the **Stream Chat SDK** for Android. Connect with others in real-time, create channels, and enjoy seamless communication with an intuitive UI.

---

## ✨ Features

- 🔐 User authentication&#x20;
- 💬 Real-time messaging with Stream Chat SDK
- 📃 List of active channels
- ➕ Create new chat channels
- 📲 One-on-one and group chats
- 🌙 Clean and modern UI (Screens: Login, Channels, Chat, Create Channel)

---

## 🚀 Screenshots


![Login Screen](https://github.com/user-attachments/assets/a9a371df-6703-42c8-a775-3e1be32c5a6b)

---

## 🛠️ Built With

- **Kotlin**
- **Stream Chat SDK** – Real-time chat infrastructure
- **Android Jetpack Components** – ViewModel, LiveData, Navigation
- **MVVM Architecture**
- **Material Design UI**

---

## ⚙️ Setup Instructions

1. **Clone the Repository**

```bash
git clone https://github.com/your-username/chat-app-stream-sdk.git
cd chat-app-stream-sdk
```

2. **Open in Android Studio**

- File > Open > Select the project folder

3. **Add Stream API Keys**

- Go to `local.properties` or directly in your `strings.xml` and add:
  ```xml
  <string name="stream_api_key">YOUR_STREAM_API_KEY</string>
  ```

4. **Build & Run**

- Run the app on an emulator or a physical device.

---

## 📆 Dependencies

```groovy
// Stream Chat SDK
implementation "io.getstream:stream-chat-android-ui-components:5.x.x"

// AndroidX and Material
implementation 'androidx.appcompat:appcompat:1.x.x'
implementation 'com.google.android.material:material:1.x.x'
```

> 💡 *Replace versions with latest stable releases.*

---

## 🙌 Acknowledgements

- [Stream Chat SDK](https://getstream.io/chat/sdk/android/)
- [Android Developers](https://developer.android.com)

