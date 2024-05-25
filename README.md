<div align="center">
  <h5>Chameleon - Tollgate</h5>
</div>

---

<br/>

## 프로젝트 소개

<p style="text-align:center;">
    <img src="https://github.com/B0X12/chameleon-tollgate/assets/86587863/abde4500-0dab-4945-aa77-173f28a731e3" width=100% />
</p>

<br/>

<div align="center" style="margin-top: 1em; margin-bottom: 3em;">
    Window 로그인 기반 MFA 솔루션,<br/>
    <i>Tollgate.</i>
</div>

<br/>
<br/>

    [소개글]

    𝙏𝙤𝙡𝙡𝙜𝙖𝙩𝙚는 Windows 시스템에 로그인 시 기본으로 제공되는 패스워드 인증 방식에
    OTP, QR, 지문, 안면인증, USB 인증과 같은 추가적인 인증 방식을 도입할 수 있도록 하는
    MFA 솔루션입니다.
    
    사용자는 로그인 시 이용할 인증 방식을 자유롭게 선택할 수 있으며,
    윈도우 프로그램과 안드로이드에서 로그인 성공/실패 기록을 인증 항목별로 조회할 수 있습니다.    

[🔗 프로젝트 상세 소개 페이지 ➔](https://box0.notion.site/323047a5936a4e26918f9dc6a2bcf290?pvs=4)

<br/>

## 📄 목차

- [🖼 실제 화면](#-실제-화면)
  - [💻 Window](#-window)
  - [📱 Android](#-android)
- [💿 소개 영상](#-소개-영상)
- [🛠 사용 기술](#-사용-기술)
- [🏛 아키텍처](#-아키텍처)
- [📑 발표 자료](#-발표-자료)

<br/>

## 🖼 실제 화면

### 💻 Window

| ![pc_1](https://github.com/B0X12/chameleon-tollgate/assets/86587863/e7f44f69-92de-4b68-b290-be7f7d52cffd) | ![pc_2](https://github.com/B0X12/chameleon-tollgate/assets/86587863/9b6bb389-8d8d-4f5b-bea2-0b2631edbdc6) |
|:---------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
|                                   **⬜ 초기화면** </br> 서버 주소 등록, 로그인 및 회원가입                                   |                                                **⬜ 소개화면**                                                 |

| ![pc_3](https://github.com/B0X12/chameleon-tollgate/assets/86587863/70f04a85-32cf-4a41-9e31-8155fbdccc42) |
|:---------------------------------------------------------------------------------------------------------:|
|                                   **⬜ 메인화면** </br> 서버 주소 등록, 로그인 및 회원가입                                   |

| ![pc_4](https://github.com/B0X12/chameleon-tollgate/assets/86587863/3b782853-9566-4f38-ad22-0f0aa81cbceb) | ![pc_6](https://github.com/B0X12/chameleon-tollgate/assets/86587863/6282a34c-74e3-4019-9326-8c96c835a11b) | ![pc_5](https://github.com/B0X12/chameleon-tollgate/assets/86587863/82ad34ce-bb33-4ea9-8c71-f04c530f1126) | 
|:---------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
|                                        **⬜ 회원정보 수정** </br> 비밀번호 변경                                        |                                **⬜ PC 등록 현황 관리** </br> 등록된 PC 정보 조회, 별칭 변경                                |                               **⬜ USB 등록 현황 관리** </br> 등록된 USB 정보 조회, 별칭 변경                               |

<br/>

### 📱 Android

| ![app_1](https://github.com/B0X12/chameleon-tollgate/assets/86587863/2b43aab6-cb87-4d17-a43b-650a3784ddcc) | ![app_2](https://github.com/B0X12/chameleon-tollgate/assets/86587863/dfd2086a-0f6f-4892-bb28-61534dce08bf) |
|:----------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
|                              **⬜ 메인화면** </br> QR 코드 스캐너, OTP 생성기, 지문/패턴/안면 등록                              |                         **⬜ 인증 내역 화면**<br/>각 인증 방법 별 성공/실패 여부 및<br/> 기기명, 시도 날짜 조회                         |

<br/>

## 💿 소개 영상
🔗 클릭하면 유튜브 링크로 연결됩니다.

[![소개 영상](https://github.com/B0X12/chameleon-tollgate/assets/86587863/15019a4f-2380-4fd1-b12e-edecbd48740e)](https://youtu.be/VRhODdBRZms?si=Ly1eyNT5P8Cy88Y2)

<br/>

## 🛠 사용 기술

| **분류**               | **기술 스택**   |
|----------------------|----------------------|
| **공통 개발환경**          | ![Windows 10](https://img.shields.io/badge/Windows%2010-0078D6?style=flat-square&logo=windows&logoColor=white) ![Android 11](https://img.shields.io/badge/Android%2011-3DDC84?style=flat-square&logo=android&logoColor=white)<br/> ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white) ![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat-square&logo=sqlite&logoColor=white)                                                            |
| **백엔드 (API Server)** | ![Java 11](https://img.shields.io/badge/Java%2011-007396?style=flat-square&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)<br/> ![Spring Tool Suite](https://img.shields.io/badge/STS-6DB33F?style=flat-square&logo=spring&logoColor=white) ![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=flat-square&logo=eclipse&logoColor=white) |
| **윈도우 (Window App)** | ![C++](https://img.shields.io/badge/C%2B%2B-00599C?style=flat-square&logo=c%2B%2B&logoColor=white) ![C#](https://img.shields.io/badge/C%23-239120?style=flat-square&logo=c-sharp&logoColor=white) ![Win32 API](https://img.shields.io/badge/Win32%20API-0078D6?style=flat-square&logo=windows&logoColor=white)<br/> ![Visual Studio 2019](https://img.shields.io/badge/Visual%20Studio%202019-5C2D91?style=flat-square&logo=visual-studio&logoColor=white)                                                                                                                                                                                                                                                                                                                                                         |
| **안드로이드 (App)**      | ![Java 11](https://img.shields.io/badge/Java%2011-007396?style=flat-square&logo=java&logoColor=white)<br/> ![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=flat-square&logo=android-studio&logoColor=white)<br/> ![zxing](https://img.shields.io/badge/zxing-000000?style=flat-square&logo=java&logoColor=white) ![gson](https://img.shields.io/badge/gson-000000?style=flat-square&logo=java&logoColor=white) ![patternLockView](https://img.shields.io/badge/patternLockView-000000?style=flat-square&logo=java&logoColor=white) ![biometric](https://img.shields.io/badge/biometric-000000?style=flat-square&logo=java&logoColor=white) ![OpenCV](https://img.shields.io/badge/OpenCV-5C3EE8?style=flat-square&logo=opencv&logoColor=white) |
| **기타** | ![FCM](https://img.shields.io/badge/FCM-4479A1?style=flat-square&logo=firebase&logoColor=white) 



<br/>

## 🏛 아키텍처

<img src="https://github.com/B0X12/chameleon-tollgate/assets/86587863/d5c1891b-8856-4249-ab4f-c5ac93ee2cca" width=60% />

<br/>

## 📑 발표 자료

🥈 교내 프로젝트 경진대회 우수상 수상 (15개 팀 중 2위)

| ![표지](https://github.com/B0X12/chameleon-tollgate/assets/86587863/a4a5ec44-f042-45f7-a420-e7b8e24a671f) |
|:-------------------------------------------------------------------------------------------------------:|
|                                                   표지                                                    |

| ![프로젝트 개요](https://github.com/B0X12/chameleon-tollgate/assets/86587863/22b9e542-5c91-49bb-a4f0-9fa814b3dac3) |
|:------------------------------------------------------------------------------------------------------------:|
|                                                (1-1) 프로젝트 개요                                                 |

| ![프로젝트 소개](https://github.com/B0X12/chameleon-tollgate/assets/86587863/d02885d4-a739-4a56-996a-6c94abac389b) |
|:------------------------------------------------------------------------------------------------------------:|
|                                                (1-2) 프로젝트 소개                                                 |

| ![UI 및 기능 소개](https://github.com/B0X12/chameleon-tollgate/assets/86587863/8b73a19e-294d-40e9-bdf9-fe605d1c23cd) | ![UI 및 기능 소개-1](https://github.com/B0X12/chameleon-tollgate/assets/86587863/ca0d2452-169b-4c55-81c0-b825b587d05f) |
|:---------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------:|
|                                                 (2) UI 및 기능 소개                                                  |                                                    (2-1) 초기화면                                                     |

| ![UI 및 기능 소개](https://github.com/B0X12/chameleon-tollgate/assets/86587863/50bbb206-47de-42f3-8a13-944b218d381c) |
|:---------------------------------------------------------------------------------------------------------------:|
|                                                   (2-2) 메인화면                                                    |

| ![UI 및 기능 소개-3](https://github.com/B0X12/chameleon-tollgate/assets/86587863/2d7670ba-8cf1-4a30-b487-4667375e2d19) | ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/f30e7ee3-b53b-43cd-863b-f3eba4197e68) |
|:-----------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
|                                                 (2-3) 회원 정보 관리화면                                                  |                                               (2-4) PC 관리 화면                                               |

| ![UI 및 기능 소개-5](https://github.com/B0X12/chameleon-tollgate/assets/86587863/0df5bd81-ec3c-49af-a2fd-8684f9bcfa98) |
|:-----------------------------------------------------------------------------------------------------------------:|
|                                                 (2-5) 앱 화면                                                        |

| ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/9e350203-32ef-4744-92ac-0c20071984af) |
|:----------------------------------------------------------------------------------------------------------:|
|                                 (3-1) 기술 소개 - Client Window PC - 서버간 통신 방법                                 |

| ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/7e0f6224-5e5d-4742-b4db-0543fb53978d) |
|:----------------------------------------------------------------------------------------------------------:|
|                                      (3-2) Client Android - 서버간 통신 방법                                      |

| ![presentation4](https://github.com/B0X12/chameleon-tollgate/assets/86587863/ebd2b6df-25b7-44f3-aa84-4f5be4073cdd) |
|:------------------------------------------------------------------------------------------------------------------:|
|                                                 (3-3) USB 인증 기술 구현                                                 |

| ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/14539a22-58ed-429d-949d-0322ebac9d31) | ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/a3a79a0f-d9f7-4e00-b07a-c27f3d400d98) |
|:----------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
|                                           (3-4) OTP 인증 기술 구현 - 1                                           |                                           (3-5) OTP 인증 기술 구현 - 2                                           |

| ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/a550ee81-d1cb-48fc-9422-65afaff10e9f) | ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/08fe3a02-64d2-4d45-bd1c-fe9980a7c96e) |
|:----------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
|                                             (3-6) 지문 인증 기술 구현                                              |                                             (3-7) 안면 인증 기술 구현                                              |

| ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/c35ec604-62ed-4fcc-af42-b48adacdca69) | ![image](https://github.com/B0X12/chameleon-tollgate/assets/86587863/5ecfc9a4-f81f-459c-a69f-2bd1d9fee2fc) |
|:----------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
|                                             (3-8) 패턴 인증 기술 구현                                              |                                             (3-9) QR 인증 기술 구현                                              |

<br/>

---

<br/>

<img src="https://github.com/B0X12/chameleon-tollgate/assets/86587863/37d19a5f-1a98-47ca-819d-422227d1a6da" width=10% />

<br/>
<br/>

> 사용 언어 및 라이브러리, 역할 등의 자세한 내용은 여기에 <br/>
[tollgate notion ➔](https://box0.notion.site/323047a5936a4e26918f9dc6a2bcf290?pvs=4)
