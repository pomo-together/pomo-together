package pomo.joowan.pomotogether.flow.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pomo.joowan.pomotogether.flow.dto.IntervalInfo;
import pomo.joowan.pomotogether.flow.utils.Clock;
import pomo.joowan.pomotogether.flow.utils.JavaClock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Nested
@DisplayName("Flow class의")
class DescribeFlow {
    private Flow flow;
    private Clock clock;

    @BeforeEach
    void setUpFlow() {
        final String FLOW_NAME = "pomo flow";
        flow = new Flow(FLOW_NAME);
        clock = new JavaClock();
    }

    @Nested
    @DisplayName("createInterval 메소드는")
    class DescribeCreateInterval {
        private final int INTERVAL_ORDER = 1;

        @Nested
        @DisplayName("적절한 인터벌 정보를 받으면")
        class ContextWithProperIntervalInformation {
            private IntervalInfo properInfo;

            @BeforeEach
            void setUpIntervalInformation() {
                final String PROPER_INTERVAL_NAME = "pomo";
                final long PROPER_INTERVAL_TIME_LIMIT = 1000 * 60 * 30;   // 30 minutes
                properInfo = new IntervalInfo(PROPER_INTERVAL_NAME, PROPER_INTERVAL_TIME_LIMIT);
            }

            @Test
            @DisplayName("Interval 객체를 반환한다")
            void ItReturnsAnIntervalObject() {
                Interval interval = flow.createIntervalWith(INTERVAL_ORDER, clock, properInfo);
                assertThat(interval).isInstanceOf(Interval.class);
                assertThat(interval.getName()).isEqualTo(properInfo.getName());
                assertThat(interval.getTimeLimit()).isEqualTo(properInfo.getTimeLimit());
            }
        }

//        @Nested
//        @DisplayName("비어있는 문자열을 이름 정보로 받으면")
//        class ContextWithIntervalNameAsEmptyString {
//            private IntervalInfo emptyNameInfo;
//
//            @BeforeEach
//            void setUpIntervalInformation() {
//                final String EMPTY_STRING = "";
//                final long PROPER_INTERVAL_TIME_LIMIT = 1000 * 60 * 30;   // 30 minutes
//                emptyNameInfo = new IntervalInfo(EMPTY_STRING, PROPER_INTERVAL_TIME_LIMIT);
//            }
//
//            @Test
//            @DisplayName("EmptyIntervalNameException을 던진다")
//            void ItThrowsEmptyIntervalNameException() {
//                assertThatThrownBy(() -> flow.createIntervalWith(INTERVAL_ORDER, clock, emptyNameInfo))
//                        .isInstanceOf(Exception.class);
//            }
//        }
    }
}
