package pomo.joowan.pomotogether.pomosession.domain.pomosession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pomo.joowan.pomotogether.pomosession.domain.model.PomoSession;
import pomo.joowan.pomotogether.pomosession.domain.model.SessionState;
import pomo.joowan.pomotogether.pomosession.domain.model.SessionType;
import pomo.joowan.pomotogether.pomosession.exceptions.InvalidPomoSessionStateException;
import pomo.joowan.pomotogether.pomosession.exceptions.NotEnoughElapsedTimeException;
import pomo.joowan.pomotogether.pomosession.utils.Clock;
import pomo.joowan.pomotogether.pomosession.utils.JavaClock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Nested
@DisplayName("PomoSession 클래스의")
public class DescribePomoSessionFinish {
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
    @DisplayName("finish 메소드는")
    public class DescribeFinish {
        private long startTimeSeconds;
        private long limitMinutes;
        private long endTimeSeconds;

        @BeforeEach
        void setUpFinishInfo() {
            startTimeSeconds = clock.currentTimeSeconds();
            limitMinutes = NORMAL_WORK_TIME_MINUTES;
            endTimeSeconds = startTimeSeconds + limitMinutes * SECONDS_PER_MINUTE;
        }

        @Nested
        @DisplayName("세션의 상태가 정지 상태일 때")
        public class ContextWhenSessionStateIsStopped {

            @Test
            @DisplayName("InvalidPomoSessionStateException을 던진다")
            void ItThrowsInvalidPomoSessionStateException() {
                assertThatThrownBy(() -> pomoSession.finish(endTimeSeconds))
                        .isInstanceOf(InvalidPomoSessionStateException.class);
            }
        }

        @Nested
        @DisplayName("세션의 상태가 동작 상태일 때")
        public class ContextWhenSessionStateIsWorking {

            @BeforeEach
            void setUpWorkingState() {
                pomoSession.start(startTimeSeconds, limitMinutes);
            }

            @Nested
            @DisplayName("경과 시간이 제한 시간보다 크거나 이와 같을 때")
            public class ContextWhenElapsedTimeEqualsToOrIsOverLimitTime {

                @BeforeEach
                void setUpEndTimeSeconds() {
                    endTimeSeconds = startTimeSeconds + limitMinutes * SECONDS_PER_MINUTE;
                }

                @Test
                @DisplayName("세션의 상태를 멈춤으로 수정한다")
                void ItUpdatesSessionStateAsStopped() {
                    pomoSession.finish(endTimeSeconds);
                    assertThat(pomoSession.getSessionState()).isEqualTo(SessionState.STOPPED);
                }

                @Nested
                @DisplayName("작업 세션일 때")
                public class ContextWithWorkSession {

                    @Test
                    @DisplayName("세션의 타입을 휴식 세션으로 수정한다")
                    void ItUpdatesSessionTypeAsBreak() {
                        pomoSession.finish(endTimeSeconds);
                        assertThat(pomoSession.getSessionType()).isEqualTo(SessionType.BREAK);
                    }
                }

                @Nested
                @DisplayName("휴식 세션일 때")
                public class ContextWithBreakSession {

                    @BeforeEach
                    void setUpBreakSession() {
                        pomoSession.finish(endTimeSeconds);
                        pomoSession.start(startTimeSeconds, limitMinutes);
                    }

                    @Test
                    @DisplayName("세션의 타입을 작업 세션으로 수정한다")
                    void ItUpdatesSessionTypeAsWork() {
                        pomoSession.finish(endTimeSeconds);
                        assertThat(pomoSession.getSessionType()).isEqualTo(SessionType.WORK);
                    }
                }
            }

            @Nested
            @DisplayName("동작한 시간이 제한 시간을 넘기지 못했을 때")
            public class ContextWhenElapsedTimeIsNotOverLimitTime {

                @BeforeEach
                void setUpEndTimeSeconds() {
                    endTimeSeconds = startTimeSeconds +
                            limitMinutes * SECONDS_PER_MINUTE -
                            DELTA_MINUTES * SECONDS_PER_MINUTE;
                }

                @Test
                @DisplayName("NotEnoughElapsedTimeException을 던진다")
                void ItThrowsNotEnoughElapsedTimeException() {
                    assertThatThrownBy(() -> pomoSession.finish(endTimeSeconds))
                            .isInstanceOf(NotEnoughElapsedTimeException.class);
                }
            }
        }

        @Nested
        @DisplayName("세션의 상태가 일시정지 상태일 때")
        public class ContextWhenSessionStateIsPaused {

//            @BeforeEach
//            void setUpPausedState() {
//                pomoSession.start();
//                pomoSession.paused();
//            }

//            @Test
//            @DisplayName("InvalidPomoSessionStateException을 던진다")
//            void ItThrowsInvalidPomoSessionStateException() {
//                assertThatThrownBy(() -> pomoSession.finish(endTimeSeconds))
//                        .isInstanceOf(InvalidPomoSessionStateException.class);
//            }
        }
    }
}
