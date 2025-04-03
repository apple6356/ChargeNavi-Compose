# ChargeNavi Compose


<h3>프로젝트 개요</h3>
이 앱은 지도에 충전소 위치를 표시해주고, 충전소별 리뷰를 통한 정보 공유 커뮤니티 앱입니다. <br>

<h3>기술스택</h3>

| 구분 | 스킬 |
| :---:  | :---: |
| Language | Kotlin |
| Architecture | MVVM |
| UI/UX | Jetpack Compose, Material3 UI/UX |
| Networking | Retrofit2, OkHttp, GSON |
| Jetpack | ViewModel, Navigation |
| Asynchronous | Coroutine, Flow, StateFlow |
| Image Loading | Coil |
| Map & LBS | NaverMap, Fused Location Source |

<h3>기능</h3>

<h4>지도</h4>
<p>
  <img src="https://github.com/user-attachments/assets/87ac48c2-9380-4479-8e7a-b2bd752a671a" width=30% height=30%>
</p>

<p>
  <ul>
    <li>충전소 위치를 API로 가져와 해당 좌표에 마커로 표시</li>
    <li>마커를 클릭 후 경로 안내를 선택하면 네이버 API로 경로를 가져와 현재 위치에서 충전소까지의 경로를 표시</li>
    <li>충전소 정보를 선택 시 충전소의 정보를 API로 가져와 각 충전기의 타입과 현재 충전기의 상태를 사용자에게 제공</li>
    <li>충전소에 대해 리뷰를 작성, 조회 할 수 있음</li>
  </ul>
</p>

<h4>즐겨찾기</h4>
<p>
  <img src="https://github.com/user-attachments/assets/90d4f202-8bd8-4e7e-baa4-68765efff832" width=30% height=30%>
</p>

<p>
  <ul>
    <li>충전소를 즐겨찾기하면 즐겨찾기 화면에서 즐겨찾기한 충전소의 정보를 모아서 제공</li>
  </ul>
</p>

<h4>마이페이지</h4>
<p>
  <img src="https://github.com/user-attachments/assets/4071c1d9-465d-4b0d-9063-d6c09c125133" width=30% height=30%>
</p>

<p>
  <ul>
    <li>네이버 아이디로 로그인 가능</li>
    <li>프로필 관리 화면에서 닉네임을 변경 가능</li>
    <li>리뷰 관리에서 본인이 작성한 리뷰를 볼 수 있고 리뷰를 삭제 가능</li>
  </ul>
</p>
