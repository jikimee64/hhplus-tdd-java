### 과제 평가 기준

**[ 요구사항 ]**

- [ ] PATCH  `/point/{id}/charge` : 포인트를 충전한다.
  - [ ] 포인트는 100원 단위로 충전 가능하다.
    - [ ] 100원 미만의 금액은 충전할 수 없다.
    - [ ] 포인트는 한번에 최대 1,000,000원까지 충전 가능하다.
    - [ ] 포인트 충전 성공 및 실패 내역을 기록한다.
- [ ] PATCH `/point/{id}/use` : 포인트를 사용한다.
  - [ ] 포인트는 한번에 최소 100원, 최대 1,000,000원까지 사용 가능하다.
  - [ ] 잔고가 부족할 경우, 포인트 사용은 실패한다.
  - [ ] 포인트 사용 성공 및 실패 내역을 기록한다.
- [ ] GET `/point/{id}` : 포인트를 조회한다.
- [ ] GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- [ ] 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다.  
※ 분산 환경은 고려하지 않는다.

**[ 패키지 구성 ]**
- 패키지 구성은 다음과 같이 구성한다.
    - application 
    - controller
    - domain
    - infra
      - persistence

**[ Step 1 평가 ]**
- [ ] 테스트 케이스의 작성 및 작성 이유를 주석의 작성 여부
- [ ] 프로젝트 내의 주석에 필요한 기능의 작성 여부
- [ ] 단위테스트 구현 여부

**[ Step 2 평가 ]**
- [ ] 로컬에서 동시성 제어 및 관련 통합 테스트 작성 여부

**[ 과제 제출 ]**
- 코드 리뷰 받고 싶은 부분들을 PR 형태로 제공
  - PR 내용에 집중적으로 리뷰해줬으면 하는 내용 작성
- 프로젝트 셋팅을 위한 커밋들은 제외