# java-chess

## 체스 게임 설명

- 체스 게임은 화이트 팀과 블랙 팀으로 나뉜다
- 체스 게임이 시작되면 화이트 팀부터 한 수씩 턴이 진행된다

## 1-2단계 요구사항

콘솔 UI에서 체스 게임을 할 수 있는 기능을 구현한다.  
1단계는 체스 게임을 할 수 있는 체스판을 초기화한다.  
체스판에서 말의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.

### 입력 요구사항

- [x] 체스 게임을 시작할지 여부를 입력받을 수 있다.
- [x] 체스 게임을 종료할지 여부를 입력받을 수 있다.
- [x] 체스 말의 이동정보를 입력받을 수 있다
  ```
  move b2 b3
  ```
- [x] 체스 게임의 진행 상황을 보여주는 여부를 입력받을 수 있다
- [x] 요구하는 형식의 입력이 들어오지 않으면 예외를 발생시킨다.

### 출력 요구사항

- [x] 체스판의 상태를 출력할 수 있다.
    - [x] 체스판의 초기 상태를 출력할 수 있다
    - [x] 매 턴마다 최신화된 체스판의 상태를 출력할 수 있다
- [x] 체스판에서 각 진영은 대문자(검은색)와 소문자(흰색)로 출력한다.
- [x] 체스 게임에서 각 진영의 점수를 출력할 수 있어야 한다
- [x] 체스 게임에서 어느 진영이 이겼는지 결과를 볼 수 있어야 한다

### 도메인 요구사항

- [x] 체스 보드 초기화
    - [x] 게임 시작시 체스판을 초기화할 수 있다.
    - [x] 체스판의 초기상태는 다음과 같다.
      ```
      RNBQKBNR
      PPPPPPPP
      ........
      ........
      ........
      ........
      pppppppp
      rnbqkbnr
      ```
        - [x] 체스판의 열은 왼쪽부터 a-h까지의 알파벳으로 이루어져 있다.
        - [x] 체스판의 행은 밑부터 1-8까지의 숫자로 이루어져 있다.
        - [x] 기물들은 화이트 또는 블랙의 색깔을 가진다.

    - [x] 기물 이동
        - [x] 기물들의 위치는 체스보드 범위를 벗어날 수 없다.
        - [x] 기물들은 각자의 행마법을 따라 이동할 수 있다
            - [x] 룩은 직선으로 이동할 수 있다
            - [x] 비숍은 대각선으로 이동할 수 있다
            - [x] 퀸은 직선/대각선으로 이동할 수 있다
            - [x] 킹은 방향과 무관하게 1칸 이동할 수 있다
            - [x] 나이트는 알파벳 L자 모양으로 앞으로 두칸 이동한 다음 왼쪽, 오른쪽 으로 한칸 움직일 수 있다
            - [x] 폰의 행마법
                - [x] 폰은 초기 상태에서 한칸 또는 두칸씩 전진할 수 있다
                - [x] 폰은 초기 상태에서 움직인 이후에는 한칸씩만 전진할 수 있다
                - [x] 상대 기물이 한칸 대각선에 있다면, 이동할 수 있다

    - [x] 체스 보드
        - [x] 체스보드 상태를 업데이트 할 수 있다
            - [x] 기물들이 이동할 수 있다면 기물의 위치를 옮긴다
            - [x] 기물들이 이동할 수 없다면 기물의 위치를 옮기지 않는다
                - [x] 도착지에 같은 팀의 기물이 있다면 이동할 수 없다
                - [x] 비숍/룩/퀸은 이동 경로에 다른 기물이 있다면 이동할 수 없다
    - [x] 체스 게임
        - [x] 턴에 맞는 행마인지 판단할 수 있다
            - [x] 체스 게임은 white팀부터 시작한다
        - [x] 킹이 잡혔을 때 게임을 종료한다
        - [x] 현재 남아있는 말의 점수를 구한다
            - 각 말의 점수는 다음과 같다
            - ```
          queen : 9점
          rook :  5점
          knight : 2.5점
          pawn : 1점 (단, 같은 세로줄에 같은 색 폰이 있는 경우 0.5점)
          ```

---

### 실행 방법

1단계 : `docker-compose -p chess up -d` 를 실행하여 DB를 설정한다
2단계 : Chessapplication의 메서드를 실행한다

- board : 체스 게임의 보드 현황 저장
- turn : 체스 게임의 턴 저장

---

## 1-2단계 개선사안

### 1차 리뷰 개선 사안

#### domain

- board /ChessBoardCreator : 초기 위치 데이터 와 생성 책임 분리
    - NomalPieceSetting : 게임 초기 위치 데이터 저장 및 전달
    - ChesBoardCreator : 보드판 초기화 및 생성
- piece : 추상화 준위 일관화
    - 기존 : instanceOf를 통해 객체마다 행마 가능여부 판단
    - 수정 : chessBoard를 넘겨주어 piece가 행마 가능여부 판단
    - 수정 이유 : instanceOf 사용 지양 및 추상화 준위 유지를 통한 다형성 활용
- game/ChessGame : 턴제 도입
- position / Direction : 8방향 도메인
- position / DirectionJudge : 두 위치에 따른 방향 판단 책임
- position / Position
    - findPath 메서드 : start에서 destination까지의 직선/대각선 경유 경로 반환
    - 메서드 단순화 및 일관성 확대
- util/retryHelper : 오류 발생시 재입력을 도와주는 유틸 클래스

#### controller

- 게임 흐름 구체화
    - start 입력 받기
        - 예외 입력 시, `start를 입력해야 게임이 시작됩니다.` 안내 메세지 출력
        - 정상 입력 시, 게임 객체 생성 및 초기 게임판 상태 출력
    - 잘못된 커멘드 입력 시 오류 발생
    - end가 입력되기 전까지 game 실행
        - while(true)로 인한 무한루프 위험 개선

#### 기타 리팩터링 사안

- 테스트 커버리지 확대
- 클린코드화

---

### 2차 리뷰 개선사안

#### domain

- 8방향 도메인 enum 변수명 변경 ex) N > UP
- 널 오브젝트 패턴을 통한 양방향 의존관계 제거
    - NullPiece : 빈 Piece를 나타내는 싱글톤 클래스
- 폰 추상클래스화
    - Pawn : 폰의 행마 가능 공통 로직 구현
    - BlackPawn / WhitePawn : 팀별 전진 정보 및 첫 스타트 라인 판단 로직 오버라이딩
- chessBoard-canMove 메서드 시그니처 변경
    - 기존 : canMove(Position start, Position destination ChessBoard board)
    - 수정 : canMove(Position start, Position destination Piece destinationPiece)
- 행마 판단 로직 책임의 명확한 분리
    - Chessboard : 경로가 모두 비어있는지 / 경로를 특정하지 못하면 빈 리스트 반환
    - Piece : 본인의 행마법에 맞는지

#### controller

- 커맨드 인터페이스 구현
    - 각 커맨드의 실행 로직을 다형성을 통해 실행로직 단순화
    - 커맨드 확장성 강화

---

## 3-4단계 리뷰 개선사안

### 1차 리뷰 개선사안

- sql 파일 첨부
    - docker/mysql/init/create_table.sql : 초기 테이블 생성
    - docker/mysql/init/insert_data.sql : 초기 데이터 생성

- service layer 생성
    - ChessGameService : db와 domain간의 상호작용 캡슐화
    - DBService : boardDao와 TurnDao간의 상호작용 캡슐화

- DB의 생성 로직 제거 : 단순 접근 책임을 가지도록 리팩터링
- connetion 관리 객체 분리
- status 안내 메시지 추가
- FakeBoardDao 객체를 통한 boardDaoImpl 테스트

### 2차 리뷰 개선사안

#### domain

- rename : MINUS_SCORE > PAWN_MINUS_SCORE
- DbService 삭제 : ChessGameService로 책임 이전
- database
    - Dbconnector : connection 연결 책임
    - ConnectionPool : connectionPool 구현
- db
    - BoardDaoImpl : 초기 테이블 insert문 구현
    - TurnDaoImpl : 초기 턴 insert문 구현

### test

- FakeBoardDao와 FakeTurnDao를 활용한 ChessGameServiceTest 구현

### 3차 리뷰 개선사안

#### domain

- dao : 보드 및 턴 초기화 오류 해결
- ScoreCalculator: 팀의 기물이 없는 경우 0점을 반환하도록 변경
- database/ConnectionPool : Connection이 모두 사용중일 때 기다리는 기능 추가

#### test

- test용 fakeDao 패키지 이동 : test/service > test/dao/fakedao
- setUp 함수를 통한 중복 코드 분리
- outputView가 test 내에 사용되지 않도록 의존성 제거

