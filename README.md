# pomo-together

# 포모도로 기법

'IT에 몸담은 이들을 위한 지적생산기술' 책에서 발췌했습니다.

- 오늘 1일분의 태스크를 만든다.
- 태스크의 크기를 포모도로 개수로 계산한다.
- 1 포모도로 동안에는 태스크를 변경하지 않고 하나에 집중한다.
- 만약 자신 또는 타인에 의한 간섭이 발생한다면 그것을 기록한다.
- 1 포모도로에서 집중상태가 계속된다면, 서서 몇 발자국 기분 전환한다.

# 그룹포모도로 규칙

1. 다음 1 포모도로 동안 수행할 작업을 정한다.
2. 작업의 이름을 선언한다.
3. 4 포모도로(약 2시간)를 수행한다. (1 포모도로마다 5분 휴식 포함)
4. 대 휴식(15분) 간 휴식
5. 그룹끼리 회고(15분)를 진행한다.
6. 다시 반복

# 유즈케이스

- 사용자는 그룹을 생성할 수 있습니다.
- 포모도로 타이머는 그룹마다 할당됩니다.
- 그룹을 생성한 사용자는 타이머 관리자 역할을 부여받습니다.
- 그룹을 생성한 사용자는 해당 그룹에 참여한 사용자에게 타이머 관리자 역할을 부여할 수 있습니다.
- 그룹을 생성한 사용자는 포모도로 플로우 관리자 역할을 부여받습니다.
- 그룹을 생성한 사용자는 해당 그룹에 참여한 사용자에게 포모도로 플로우 관리자 역할을 부여할 수 있습니다.
- 타이머 관리자는 해당 그룹의 타이머를 시작/멈춤/초기화할 수 있습니다.
- 포모도로 플로우 관리자는 각 시간 간격의 길이와 순서를 설정할 수 있습니다. 시간 간격의 종류는 아래와 같습니다.
    - 포모도로: 태스트에 집중해야하는 시간 간격
    - 휴식: 태스크에 대한 집중을 이완시켜 휴식을 취한 시간 간격
    - 회고: 진행한 태스크의 결과나 진행상태를 정리하고 공유하는 시간 간격
- 포모도로 플로우는 포모도로, 휴식, 회고를 중복을 허용하며 임의의 순서대로 나열한 것
- 사용자는 각 포모도로, 휴식, 회고에 이름을 부여할 수 있습니다. (태깅)
- 사용자는 자신이 참여한 그룹 목록을 확인할 수 있다.
- 사용자는 자신이 참여한 그룹의 포모도로 플로우를 확인할 수 있다.
- 사용자는 자신이 참여한 그룹의 타이머 시간을 확인할 수 있다.

# 유비쿼터스 언어

- 시간 간격: 일정한 길이를 지닌 특정 시간 단위(타이머를 이용해 현재 어떤 상태인지 체크한다)
- 태스크: 수행할 작업
- 포모도로: 태스크에 집중해야하는 시간 간격
- 휴식: 태스크에 대한 집중을 이완시키고 휴식하는 시간 간격
- 회고: 진행한 태스크의 결과나 진행상태를 정리하고 공유하는 시간 간격
- 사용자(다른이름이 있을까): 애플리케이션을 사용할 수 있도록 식별자가 부여된 인증된 유저
- 그룹(유니온, 팀 등 다른 이름도 생각중): 타이머를 공유할 1명 이상의 사용자 모임
- 권한: 어떠한 커맨드를 수행할 수 있는 자격이 주어진 것
- 역할: 1가지 이상의 권한을 가지는 책임
- 포모도로 플로우: 각 시간 간격을 중복을 허용하며 임의의 순서대로 나열한 시간 간격 리스트
