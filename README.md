# 🌊 MarineMatch  
**AI-powered mobile app to identify marine species and promote ocean conservation.**

 ![image](https://github.com/user-attachments/assets/8a8a0f33-7e51-4813-b9d9-32408ef739a5)

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/ae592eec-f89f-4926-9cb8-d777f2acdefe" />


[**📱 Mobile App Code**](https://github.com/dkaty123/MarineMatch)  

[**🌐 Visit Website**](https://dkaty123.github.io/MarineMatchWeb/index.html)

[ **🥇Award Winner Article (National Youth Climate Awards)**](https://www.nationalycaa.org/2024-winners/dev-katyal)


## 🔍 Overview

MarineMatch is a cross-platform initiative designed to educate, empower, and engage users in marine conservation.  

It uses **image recognition** powered by computer vision to identify over **60+ marine species**, providing insights into **overfishing**, **plastic pollution**, and biodiversity loss.

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/296a8cba-408e-4445-b823-84ae32a33101" />

---

## 🚀 Features

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/ad8a2bf6-0696-4205-90a4-786d9769465f" />

### 🔬 AI-Powered Image Recognition
- Built with **TensorFlow Lite** to classify **600+ fish species** locally on-device.
- Trained on a curated dataset of marine species from government and NGO sources.
- Optimized for **offline** and **low-bandwidth** environments.

### 🌐 Cross-Platform Support
- **Android Native App:** Built in **Java** using **Android Studio** with support for CameraX, file storage, and integrated TensorFlow Lite models.
- **Web App:** Interactive marine visualization built using **HTML5**, **Tailwind CSS**, and **vanilla JavaScript**, deployed via **GitHub Pages** and **Netlify**.

### 🐠 Interactive Species Guide
- Select species by region, type, or conservation status.
- Learn ecological roles, IUCN status, and human impact statistics.
- Mobile app also caches species profiles for offline learning.

### 📊 Ocean Impact Dashboard
- Visualize how overfishing and plastic pollution are affecting biodiversity.
- Dynamic graphs (coming soon) rendered with **Chart.js**.

### 🧭 Educational Toolkit
- Step-by-step conservation guides for youth and educators.
- Infographics on sustainable seafood practices and marine laws.

---

## 🛠️ Tech Stack

### 🔧 Mobile App

| Component              | Details                                                                 |
|------------------------|-------------------------------------------------------------------------|
| Language               | Java (Android SDK 33)                                                   |
| IDE                    | Android Studio with Gradle                                              |
| Libraries              | TensorFlow Lite, CameraX, Glide, Material Design                        |
| Model Format           | `.tflite` (quantized)                                                   |
| Deployment             | Android APK (locally sideloaded or via Play Console)                   |
| Storage                | RoomDB (for species info caching)                                       |

### 🌐 Web App

| Component              | Details                                                                 |
|------------------------|-------------------------------------------------------------------------|
| Markup/Style           | HTML5, Tailwind CSS                                                     |
| Scripts                | Vanilla JavaScript (ES6+)                                               |
| Deployment             | GitHub Pages / Netlify                                                  |
| Visualization          | DOM-based SVG, future support for D3.js / Chart.js                     |

---

## 📱 How It Works

1. **Explore Species (Web + Mobile)**  
   Use the dropdown menu or map-based filter to explore marine biodiversity by region, type, or status.

2. **Capture or Upload Image (Mobile Only)**  
   Snap a photo of a fish species. The image is processed locally using a quantized neural network.

3. **Classification & Feedback**  
   The model returns the closest matching species label and confidence score.

4. **Learn & Take Action**  
   The app presents info on threats to the species and what conservation steps users can take.

---

## 👥 Acknowledgements

Developed by **Dev Katyal**  
With support from:  
- 🧠 Local fishers and marine biologists (25+ interviews)  
- 🌿 Conservation partners  
- 🏆 Ingenious+ Award by the Governor General of Ontario

---

## 🐬 Join the Movement

Help protect our oceans — one match at a time.

<img width="1830" height="1230" alt="image" src="https://github.com/user-attachments/assets/db7e116a-5d82-43dc-a94d-02b52492d005" />

<img width="1868" height="1228" alt="image" src="https://github.com/user-attachments/assets/8306bbee-6f70-4676-8845-28348c73838f" />

# Awards
 Winner of the 2024 Youth Climate Activism Award, BowSeat Ocean Awarness Contest
 Winner of multiple grants inlcluding the GenSea Ocean Grant and OceanWise Grant
