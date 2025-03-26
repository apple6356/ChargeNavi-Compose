# ChargeNavi Compose


<h3>프로젝트 개요</h3>
이 앱은 지도에 충전소 위치를 표시해주고, 충전소별 리뷰를 통한 정보 공유 커뮤니티 앱입니다. <br>

<h3>기술스택</h3>

| 구분 | 스킬 |
| :---:  | :---: |
| Language | Kotlin |
| Architecture | MVVM |
| Networking | Retrofit2, OkHttp, GSON |
| Jetpack | ViewModel, Navigation |
| Asynchronous | Coroutine, Flow, StateFlow |
| Image Loading | Coil |
| Map & LBS | NaverMap, Fused Location Source |

<h3>기능</h3>

지도 
<p>
<img src="https://github.com/user-attachments/assets/bcf2dd7d-9c87-4350-b851-10fac007c918">
</p>

<p>
  <ul>
    <li>충전소 위치를 API로 가져와 해당 좌표에 마커로 표시</li>
    <li>마커를 클릭 후 경로 안내를 선택하면 네이버 API로 경로를 가져와 현재 위치에서 충전소까지의 경로를 표시</li>
    <li>충전소 정보를 선택 시 충전소의 정보를 API로 가져와 각 충전기의 타입과 현재 충전기의 상태를 사용자에게 제공</li>
    <li>충전소에 대해 리뷰를 작성, 조회 할 수 있음</li>
  </ul>
</p>

즐겨찾기
<p>
<img src="https://github.com/user-attachments/assets/c7541358-581c-4027-b67e-90fec4986301"> <br>
</p>

<p>
  <ul>
    <li>충전소를 즐겨찾기하면 즐겨찾기 화면에서 즐겨찾기한 충전소의 정보를 모아서 제공</li>
  </ul>
</p>

마이페이지
<p>
<img src="https://github.com/user-attachments/assets/e0512ac4-b874-44c2-b1a0-7b25080f3e1d"> <br>
</p>

<p>
  <ul>
    <li>네이버 아이디로 로그인 가능</li>
    <li>프로필 관리 화면에서 닉네임을 변경 가능</li>
    <li>리뷰 관리에서 본인이 작성한 리뷰를 볼 수 있고 리뷰를 삭제 가능</li>
  </ul>
</p>
