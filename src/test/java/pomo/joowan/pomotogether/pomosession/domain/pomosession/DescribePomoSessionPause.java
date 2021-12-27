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
@DisplayName("PomoSession 클래스의 pause 메소드는")
class DescribePomoSessionPause {
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
    class DescribePause {
        private long startTimeSeconds;
        private long limitMinutes;
        private long currentTimeSeconds;

        @Nested
        @DisplayName("세션의 상태가 정지 상태일 때")
        class ContextWhenSessionStateIsStopped {

            @BeforeEach
            void setUpCurrentTimeSeconds() {
                currentTimeSeconds = clock.currentTimeSeconds();
            }

            @Test
            @DisplayName("InvalidPomoSessionStateException을 던진다")
            void ItThrowsInvalidPomoSessionStateException() {
                assertThatThrownBy(() -> pomoSession.pause(currentTimeSeconds))
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
            @DisplayName("세션의 경과시간을 최신화한다")
            void ItMakesElapsedTimeBeLatest() {
                pomoSession.pause(currentTimeSeconds);
                long latestElapsedTimeSeconds = currentTimeSeconds - startTimeSeconds;
                assertThat(pomoSession.getElapsedSeconds()).isEqualTo(latestElapsedTimeSeconds);
            }

            @Test
            @DisplayName("세션의 상태를 일시정지 상태로 수정한다")
            void ItUpdatesSessionStateAsPaused() {
                pomoSession.pause(currentTimeSeconds);
                assertThat(pomoSession.getSessionState()).isEqualTo(SessionState.PAUSED);
            }

            @Nested
            @DisplayName("이전에 한번 일시정지에서 재개된 세션이라면")
            class ContextWithResumedSession {
                private long oneMorePausedSeconds;
                private long previousElapsedSeconds;

                @BeforeEach
                void setUpResumedSession() {
                    oneMorePausedSeconds = currentTimeSeconds + DELTA_MINUTES * SECONDS_PER_MINUTE;
                    pomoSession.pause(currentTimeSeconds);
                    previousElapsedSeconds = pomoSession.getElapsedSeconds();
                    pomoSession.resume(currentTimeSeconds);
                }

                @Test
                @DisplayName("세션의 경과시간을 이전에 계산했던 경과시간과 합친다")
                void ItCalculatesElapsedTime() {
                    pomoSession.pause(oneMorePausedSeconds);
                    long expected = previousElapsedSeconds + oneMorePausedSeconds - currentTimeSeconds;
                    assertThat(pomoSession.getElapsedSeconds()).isEqualTo(expected);
                }

                @Test
                @DisplayName("세션의 상태를 일시정지 상태로 수정한다")
                void ItUpdatesSessionStateAsPaused() {
                    pomoSession.pause(oneMorePausedSeconds);
                    assertThat(pomoSession.getSessionState()).isEqualTo(SessionState.PAUSED);
                }
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
            @DisplayName("InvalidPomoSessionStateException을 던진다")
            void ItThrowsInvalidPomoSessionStateException() {
                assertThatThrownBy(() -> pomoSession.pause(currentTimeSeconds))
                        .isInstanceOf(InvalidPomoSessionStateException.class);
            }
        }
    }
}
