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
@DisplayName("PomoSession 클래스의 resume 메소드는")
class DescribePomoSessionResume {
    private final long NORMAL_WORK_TIME_MINUTES = 25;
    private final long SECONDS_PER_MINUTE = 60;
    private final long DELTA_MINUTES = 10;

    private PomoSession pomoSession;
    private Clock clock;

    @BeforeEach
    void setUpPomoSessionAndClock() {
        pomoSession = new PomoSession();
        clock = new JavaClock();
    }

    @Nested
    class DescribeResume {
        private long startTimeSeconds;
        private long limitMinutes;
        private long currentTimeSeconds;

        @Nested
        @DisplayName("세션의 상태가 정지 상태일 때")
        class ContextWhenSessionStateIsStopped {

            @Test
            @DisplayName("InvalidPomoSessionStateException을 던진다")
            void ItThrowsInvalidPomoSessionStateException() {
                assertThatThrownBy(() -> pomoSession.resume(currentTimeSeconds))
                        .isInstanceOf(InvalidPomoSessionStateException.class);
            }
        }

        @Nested
        @DisplayName("세션의 상태가 동작 상태일 때")
        class ContextWhenSessionStateIsWorking {

            @BeforeEach
            void setUpWorkingState() {
                startTimeSeconds = clock.currentTimeSeconds() - DELTA_MINUTES * SECONDS_PER_MINUTE;
                limitMinutes = NORMAL_WORK_TIME_MINUTES;
                currentTimeSeconds = clock.currentTimeSeconds();
                pomoSession.start(startTimeSeconds, limitMinutes);
            }

            @Test
            @DisplayName("InvalidPomoSessionStateException을 던진다")
            void ItThrowsInvalidPomoSessionStateException() {
                assertThatThrownBy(() -> pomoSession.resume(currentTimeSeconds))
                        .isInstanceOf(InvalidPomoSessionStateException.class);
            }
        }

        @Nested
        @DisplayName("세션의 상태가 일시정지 상태일 때")
        class ContextWhenSessionStateIsPaused {

            @BeforeEach
            void setUpPausedState() {
                startTimeSeconds = clock.currentTimeSeconds() - DELTA_MINUTES * SECONDS_PER_MINUTE;
                limitMinutes = NORMAL_WORK_TIME_MINUTES;
                currentTimeSeconds = clock.currentTimeSeconds();
                pomoSession.start(startTimeSeconds, limitMinutes);
                pomoSession.pause(currentTimeSeconds);
            }

            @Test
            @DisplayName("시작 시각을 현재 시각으로 수정한다")
            void ItUpdatesStartTimeAsCurrentTime() {
                pomoSession.resume(currentTimeSeconds);
                assertThat(pomoSession.getStartTimeSeconds()).isEqualTo(currentTimeSeconds);
            }

            @Test
            @DisplayName("세션의 상태를 동작 중으로 수정한다")
            void ItUpdatesStateOfSessionAsWorking() {
                pomoSession.resume(currentTimeSeconds);
                assertThat(pomoSession.getSessionState()).isEqualTo(SessionState.WORKING);
            }
        }
    }
}
