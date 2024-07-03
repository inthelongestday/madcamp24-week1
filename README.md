# Memories 🫧

## Outline

---

![Memories Logo](./app/src/main/res/drawable/ic_launcher.jpg)

> 기록하세요. 연락처 속 가까운 사람들과의 여행을 지역별, 기간별로.

---

- **Contacts**
- **Gallery**
- **Memories**

---

## Team

---

**김이겸**

[GitHub Profile](https://github.com/inthelongestday)

**김철호**

[GitHub Profile](https://github.com/Cheoroo)

---

## Tech Stack

---

**Frontend:** Java

**IDE:** Android Studio

**Version Control:** Git / GitHub

---

## Introduction

이 프로젝트는 연락처와 여행을 기록하고 관리할 수 있는 간단한 어플리케이션입니다.

> **Experience**

- **Contacts:** 친구의 연락처를 저장하고 편집, 추가, 삭제할 수 있습니다.
- **Gallery:** Memories 탭에서 찍은 사진들을 확인하고, 사진별로 간단한 메모를 남길 수 있습니다.
- **Memories:** 여행을 기록할 수 있습니다.
  - 함께한 친구를 태그하고, 장소에서의 사진을 찍고, 간단한 메모와 날짜를 저장할 수 있습니다.
  - 여행 기록을 편집, 추가, 삭제할 수 있습니다.
  - 여행 기록은 지역별, 월별로 모아서 볼 수 있습니다.

---

## Details

---

### **Contacts**

연락처 목록을 확인할 수 있습니다. Create, Read, Update, Delete 기능을 제공합니다.

- **ContactDTO**: 개별 연락처를 관리.
- **ContactData**: 전체 연락처 목록을 관리.
- **ContactFragment**: 연락처 전체 목록을 보여줌.
- **RecyclerView**: 연락처 목록을 표시.

**CRUD 기능 설명:**

- **Create**:
  - 새로운 연락처 생성.
  - `FloatingActionButton`을 눌러 `ContactEditFragment`로 이동.
- **Read**:
  - 저장된 모든 연락처를 가나다순으로 정렬하여 보여줌.
  - 개별 연락처 터치 시 상세 정보 조회 가능.
- **Update**:
  - 기존 연락처의 상세 페이지에서 편집 가능.
  - 연락처 정보가 수정될 경우, 해당 정보가 태그된 여행 기록에서도 자동으로 업데이트.
- **Delete**:
  - 상세 페이지에서 삭제 가능.
  - 삭제 시 확인 메시지 표시.

### **Gallery**

여행 시 찍은 사진을 확인하고, 사진별로 간단한 메모를 저장할 수 있습니다.

- **GalleryDTO**: 개별 사진 데이터를 관리.
- **GalleryData**: 전체 사진 목록을 관리.
- **GalleryFragment**: 정사각형 그리드로 사진 목록을 표시.
- **RecyclerView**: 사진 목록을 표시.

**기능 설명:**

- 개별 사진을 선택하면 원본 사진과 간단한 메모 작성 가능.

### **Memories**

여행을 기록할 수 있습니다.

- **기능 명세**:
  - 지역별로 구분된 한국 지도에서 지역을 선택하여 여행 기록 조회.
  - 개별 여행 기록 클릭 시 세부 정보 표시.
  - 새로운 여행 기록 생성:
    - 카메라로 사진 촬영 후 기록 생성.
    - `TravelRecordEditFragment`를 통해 기록 편집.
  - 연락처 태깅 기능:
    - 여행 기록에 친구를 태그.
    - 태그된 연락처와 기록 연동.

---

## APK link

[Download APK](https://drive.google.com/file/d/1lU7pAMHEU86U7wbKSck1Gcwk_qp0HhbU/view?usp=drive_link)

---

## Extra

**개발 진행 과정:**

Git Flow를 기반으로 함

1. individual branch (not main)
2. local test
3. push & PR
4. review & merge
5. test
6. bug report & solve

(반복)
