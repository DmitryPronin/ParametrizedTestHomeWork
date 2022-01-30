import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ParametrizedTest {
    private final static String AVITO_URL = "https://www.avito.ru/";

    static Stream<Arguments> dataForCheckAvitoLinks() {
        return Stream.of(
                Arguments.of("Авто", 1500000, "Транспорт и запчасти"),
                Arguments.of("Недвижимость", 30000, "Недвижимость")
        );
    }

    @ParameterizedTest(name = "Проверка ссылки на раздел \"{0}\" по одному параметру")
    @ValueSource(strings = {"Авто", "Недвижимость"})
    @DisplayName("Проверка ссылки на раздел по одному параметру")
    void checkAvitoLinksStringTest(String chapter) {
        open(AVITO_URL);
        $(byText(chapter)).click();
        String value = $(".page-title-count-wQ7pG").getText();

        Assertions.assertThat(Integer.parseInt(value.replace(" ", ""))).isGreaterThan(0);
    }

    @ParameterizedTest(name = "Проверка ссылки на раздел \"{0}\" по нескольким параметрам")
    @MethodSource("dataForCheckAvitoLinks")
    @DisplayName("Проверка ссылки на раздел с несколькими данными")
    void checkAvitoLinksWithMethodSourceTest(String chapter, int count, String article) {
        open(AVITO_URL);
        $(byText(chapter)).click();
        String value = $(".page-title-count-wQ7pG").getText();

        Assertions.assertThat(Integer.parseInt(value.replace(" ", ""))).isGreaterThan(count);
        $("h1").shouldHave(text(article));
    }
}
