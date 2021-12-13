package pomo.joowan.pomotogether.flow.intervals.domain.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pomo.joowan.pomotogether.flow.intervals.domain.Interval;
import pomo.joowan.pomotogether.flow.intervals.utils.Clock;
import pomo.joowan.pomotogether.flow.intervals.utils.JavaClock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("Interval class의")
class DescribeInterval {
    private final long INTERVAL_TIME_LIMIT_MILLI_SECONDS = 30000;
    private Clock clock;
    private Interval interval;

    @BeforeEach
    void setUpInterval() {
        String INTERVAL_NAME = "test";
        clock = new JavaClock();
        interval = new Interval(INTERVAL_TIME_LIMIT_MILLI_SECONDS, INTERVAL_NAME, clock);
    }

    @Nested
    @DisplayName("isOver 메소드는")
    class DescribeIsOver {
        private long intervalStartTime;

        @Nested
        @DisplayName("인터벌의 제한시간이 아직 지나지 않았다면")
        class ContextWhenTimeLimitIsNotOver {
            @BeforeEach
            void setUpStartTime() {
                long halfTimeLimit = interval.getTimeLimit() - INTERVAL_TIME_LIMIT_MILLI_SECONDS/2;
                intervalStartTime = clock.current() - halfTimeLimit;
            }

            @Test
            @DisplayName("false를 반환한다")
            void ItReturnsFalse() {
                assertThat(interval.isOver(intervalStartTime)).isEqualTo(false);
            }
        }

        @Nested
        @DisplayName("인터벌의 제한시간과 타이머의 경과시간이 같다면")
        class ContextWhenTimerArrivesTimeLimit {
            @BeforeEach
            void setUpStartTime() {
                intervalStartTime = clock.current() - INTERVAL_TIME_LIMIT_MILLI_SECONDS;
            }

            @Test
            @DisplayName("True를 반환한다")
            void ItReturnsTrue() {
                assertThat(interval.isOver(intervalStartTime)).isEqualTo(true);
            }
        }

        @Nested
        @DisplayName("인터벌의 제한시간이 지났다면")
        class ContextWhenTimeLimitIsOver {
            @BeforeEach
            void setUpStartTime() {
                long twoTimesTimeLimit = INTERVAL_TIME_LIMIT_MILLI_SECONDS * 2;
                intervalStartTime = clock.current() - twoTimesTimeLimit;
            }

            @Test
            @DisplayName("True를 반환한다")
            void ItReturnsTrue() {
                assertThat(interval.isOver(intervalStartTime)).isEqualTo(true);
            }
        }
    }
}
