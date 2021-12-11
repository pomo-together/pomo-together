package pomo.joowan.pomotogether.timer.domain;

import org.junit.jupiter.api.*;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsNotPausedException;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsNotStoppedException;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsNotWorkingException;
import pomo.joowan.pomotogether.timer.utils.Clock;
import pomo.joowan.pomotogether.timer.utils.JavaClock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Nested
@DisplayName("Timer class의")
class DescribeTimer {
    private Clock clock;
    private Timer timer;

    @BeforeEach
    void setUpClock() {
        clock = new JavaClock();
    }

    @Nested
    @DisplayName("start 메소드는")
    class DescribeStart {

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

            @Test
            @DisplayName("타이머의 상태를 Working으로 수정한다")
            void ItUpdatesStateOfTimerAsWorking() {
                timer.start();
                assertThat(timer.getState()).isEqualTo(TimerState.WORKING);
            }
        }

        @Nested
        @DisplayName("타이머가 동작 중일때")
        class ContextWhenTimerIsWorking {
            @BeforeEach
            void startTimer() {
                timer = new Timer(clock);
                timer.start();
            }

            @Test
            @DisplayName("TimerIsNotStoppedException을 던진다")
            void ItThrowsTimerIsNotStoppedException() {
                assertThatThrownBy(() -> timer.start())
                        .isInstanceOf(TimerIsNotStoppedException.class);
            }

            @Test
            @DisplayName("타이머의 시작시각이 현재시각보다 이르거나 같아야 한다")
            void ItHasEarlierStartTimeThanCurrentTime() {
                assertThat(timer.getStartTime()).isLessThanOrEqualTo(clock.current());
            }
        }

        @Nested
        @DisplayName("타이머가 일시정지 상태일 때")
        class ContextWhenTimerIsPaused {
            @BeforeEach
            void pauseTimer() {
                timer = new Timer(clock);
                timer.start();
                timer.pause();
            }

            @Test
            @DisplayName("TimerIsNotStoppedException을 던진다")
            void ItThrowsTimerIsNotStoppedException() {
                assertThatThrownBy(() -> timer.start())
                        .isInstanceOf(TimerIsNotStoppedException.class);
            }
        }
    }

    @Nested
    @DisplayName("pause 메소드는")
    class DescribePause {

        @Nested
        @DisplayName("타이머가 동작하고 있을 때")
        class ContextWhenTimerIsWorking {
            @BeforeEach
            void startTimer() {
                timer = new Timer(clock);
                timer.start();
            }

            @Test
            @DisplayName("타이머의 시작시각을 0으로 수정한다")
            void ItUpdatesStartTimeAsZero() {
                timer.pause();
                assertThat(timer.getStartTime()).isEqualTo(0);
            }

            @Test
            @DisplayName("타이머의 경과시간을 현재시각과 시작시각의 차이로 수정한다")
            void ItUpdatesElapsedTimeAsDifferenceBetweenStartTimeAndCurrentTime() {
                timer.pause();
                assertThat(timer.getStartTime()).isGreaterThanOrEqualTo(0);
            }

            @Test
            @DisplayName("타이머의 상태를 Paused로 수정한다")
            void ItUpdatesStateOfTimerAsPaused() {
                timer.pause();
                assertThat(timer.getState()).isEqualTo(TimerState.PAUSED);
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
            @DisplayName("TimerIsNotWorkingException을 던진다")
            void ItThrowsTTimerIsNotWorkingException() {
                assertThatThrownBy(() -> timer.pause())
                        .isInstanceOf(TimerIsNotWorkingException.class);
            }
        }

        @Nested
        @DisplayName("타이머가 일시정지 상태일 때")
        class ContextWhenTimerIsPaused {
            @BeforeEach
            void pauseTimer() {
                timer = new Timer(clock);
                timer.start();
                timer.pause();
            }

            @Test
            @DisplayName("TimerIsNotWorkingException을 던진다")
            void ItThrowsTimerIsNotWorkingException() {
                assertThatThrownBy(() -> timer.pause())
                        .isInstanceOf(TimerIsNotWorkingException.class);
            }
        }
    }

    @Nested
    @DisplayName("resume 메소드는")
    class DescribeResume {

        @Nested
        @DisplayName("타이머가 멈춰있을 때")
        class ContextWhenTimerIsStopped {
            @BeforeEach
            void setUpTimer() {
                timer = new Timer(clock);
            }

            @Test
            @DisplayName("TimerIsNotPausedException을 던진다")
            void ItThrowsTimerIsNotPausedException() {
                assertThatThrownBy(() -> timer.resume())
                        .isInstanceOf(TimerIsNotPausedException.class);
            }
        }

        @Nested
        @DisplayName("타이머가 일시정지 상태일 때")
        class ContextWhenTimerIsPaused {
            @BeforeEach
            void pauseTimer() {
                timer = new Timer(clock);
                timer.start();
                timer.pause();
            }

            @Test
            @DisplayName("타이머의 시작시각을 현재시각으로 수정한다")
            void ItUpdatesStartTimeAsCurrentTime() {
                timer.resume();
                assertThat(timer.getStartTime()).isNotEqualTo(0);
            }

            @Test
            @DisplayName("타이머의 상태를 Working으로 수정한다")
            void ItUpdatesStateOfTimerAsWorking() {
                timer.resume();
                assertThat(timer.getState()).isEqualTo(TimerState.WORKING);
            }
        }

        @Nested
        @DisplayName("타이머가 동작중일 때")
        class ContextWhenTimerIsWorking {
            @BeforeEach
            void startTimer() {
                timer = new Timer(clock);
                timer.start();
            }

            @Test
            @DisplayName("TimerIsNotPausedException을 던진다")
            void ItThrowsTimerIsNotPausedException() {
                assertThatThrownBy(() -> timer.resume())
                        .isInstanceOf(TimerIsNotPausedException.class);
            }
        }
    }
}
