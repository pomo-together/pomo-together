package pomo.joowan.pomotogether.timer.domain;

import org.junit.jupiter.api.*;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsAlreadyStartedException;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsAlreadyStoppedException;
import pomo.joowan.pomotogether.timer.utils.Clock;
import pomo.joowan.pomotogether.timer.utils.JavaClock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Nested
@DisplayName("Timer class의")
class DescribeTimer {
    private Clock clock;

    @BeforeEach
    void setUpClock() {
        clock = new JavaClock();
    }

    @Nested
    @DisplayName("start 메소드는")
    class DescribeStart {
        private Timer timer;

        @Nested
        @DisplayName("타이머가 멈춰있을 때")
        class ContextWhenTimerIsStopped {
            @BeforeEach
            void setUpTimer() {
                timer = new Timer(clock);
            }

            @Test
            @DisplayName("타이머의 시작시각을 현재시각으로 수정한다")
            void ItInitializesStartTimeAsCurrentTime() {
                timer.start();
                assertThat(timer.getStartTime()).isGreaterThan(0);
            }
        }

        @Nested
        @DisplayName("타이머가 이미 시작되어 있을때")
        class ContextWhenTimerIsAlreadyStarted {
            @BeforeEach
            void startTimer() {
                timer = new Timer(clock);
                timer.start();
            }

            @Test
            @DisplayName("TimerIsAlreadyStartedException을 던진다")
            void ItThrowsTimerIsAlreadyStartedException() {
                assertThatThrownBy(() -> timer.start())
                        .isInstanceOf(TimerIsAlreadyStartedException.class);
            }

            @Test
            @DisplayName("타이머의 시작시각이 현재시각보다 이르거나 같아야 한다")
            void ItHasEarlierStartTimeThanCurrentTime() {
                assertThat(timer.getStartTime()).isLessThanOrEqualTo(clock.current());
            }
        }
    }

    @Nested
    @DisplayName("pause 메소드는")
    class DescribePause {
        private Timer timer;

        @Nested
        @DisplayName("타이머가 동작하고 있을 때")
        class ContextWhenTimerIsWorking {
            @BeforeEach
            void startTimer() {
                timer = new Timer(clock);
                timer.start();
            }

            @Test
            @DisplayName("시작시각을 0으로 수정한다")
            void ItUpdatesStartTimeAsZero() {
                timer.pause();
                assertThat(timer.getStartTime()).isEqualTo(0);
            }

            @Test
            @DisplayName("경과시간을 현재시각과 시작시각의 차이로 수정한다")
            void ItUpdatesElapsedTimeAsDifferenceBetweenStartTimeAndCurrentTime() {
                timer.pause();
                assertThat(timer.getStartTime()).isGreaterThanOrEqualTo(0);
            }
        }

        @Nested
        @DisplayName("타이머가 멈춰있을 때")
        class ContextWhenTimerIsStopped {
            @BeforeEach
            void setUpTimer() {
                timer = new Timer(clock);
            }

            @Test
            @DisplayName("TimerIsAlreadyStoppedException을 던진다")
            void ItThrowsTimerIsAlreadyStoppedException() {
                assertThatThrownBy(() -> timer.pause())
                        .isInstanceOf(TimerIsAlreadyStoppedException.class);
            }
        }
    }

}
