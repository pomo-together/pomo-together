package pomo.joowan.pomotogether.pomosession.domain.pomosession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pomo.joowan.pomotogether.pomosession.domain.model.PomoSession;
import pomo.joowan.pomotogether.pomosession.domain.model.SessionState;
import pomo.joowan.pomotogether.pomosession.exceptions.InvalidPomoSessionStateException;
import pomo.joowan.pomotogether.pomosession.utils.Clock;
import pomo.joowan.pomotogether.pomosession.utils.JavaClock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Nested
@DisplayName("PomoSession 클래스의")
class DescribePomoSessionStart {
    private final long SECOND_PER_MINUTE = 60;

    private PomoSession pomoSession;
    private Clock clock;

    @BeforeEach
    void setUpPomoSessionAndClock() {
        pomoSession = new PomoSession();
        clock = new JavaClock();
    }

    @Nested
    @DisplayName("start 메소드는")
    class DescribeStart {
        private long startTimeSeconds;
        private long limitMinutes;

        @BeforeEach
        void setUpStartInfo() {
            startTimeSeconds = clock.currentTimeSeconds();
            limitMinutes = 25;
        }

        @Nested
        @DisplayName("세션의 상태가 정지 상태일 때")
        class ContextWhenSessionStateIsStopped {

            @Test
            @DisplayName("제한시간을 주어진 분 단위 제한시간을 초 단위로 변환한 값으로 수정한다")
            void ItUpdatesLimitSecondsAsGiven() {
                pomoSession.start(startTimeSeconds, limitMinutes);
                long limitSeconds = limitMinutes * SECOND_PER_MINUTE;
                assertThat(pomoSession.getLimitSeconds()).isEqualTo(limitSeconds);
            }

            @Test
            @DisplayName("경과 시간을 0으로 수정한다")
            void ItUpdatesElapsedTimeAsZero() {
                pomoSession.start(startTimeSeconds, limitMinutes);
                assertThat(pomoSession.getElapsedSeconds()).isEqualTo(0);
            }

            @Test
            @DisplayName("시작 시각을 주어진 시간으로 수정한다")
            void ItUpdatesStartTimeSecondsAsGiven() {
                pomoSession.start(startTimeSeconds, limitMinutes);
                assertThat(pomoSession.getStartTimeSeconds()).isEqualTo(startTimeSeconds);
            }

            @Test
            @DisplayName("세션의 상태를 동작 중으로 수정한다")
            void ItUpdatesSessionStateAsWorking() {
                pomoSession.start(startTimeSeconds, limitMinutes);
                assertThat(pomoSession.getSessionState()).isEqualTo(SessionState.WORKING);
            }
        }

        @Nested
        @DisplayName("세션의 상태가 동작 상태일 때")
        class ContextWhenSessionStateIsWorking {

            @BeforeEach
            void setUpWorkingState() {
                pomoSession.start(startTimeSeconds, limitMinutes);
            }

            @Test
            @DisplayName("InvalidPomoSessionStateException을 던진다")
            void ItThrowsInvalidPomoSessionStateException() {
                assertThatThrownBy(() -> pomoSession.start(startTimeSeconds, limitMinutes))
                        .isInstanceOf(InvalidPomoSessionStateException.class);
            }
        }

        @Nested
        @DisplayName("세션의 상태가 일시정지 상태일 때")
        class ContextWhenSessionStateIsPaused {

//            @BeforeEach
//            void setUpPausedState() {
//                pomoSession.start();
//                pomoSession.paused();
//            }

//            @Test
//            @DisplayName("InvalidPomoSessionStateException을 던진다")
//            void ItThrowsInvalidPomoSessionStateException() {
//                assertThatThrownBy(() -> pomoSession.start(startTimeSeconds, limitMinutes))
//                        .isInstanceOf(InvalidPomoSessionStateException.class);
//            }
        }
    }
}
