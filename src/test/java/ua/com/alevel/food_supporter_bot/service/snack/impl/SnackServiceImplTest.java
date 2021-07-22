package ua.com.alevel.food_supporter_bot.service.snack.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.food_supporter_bot.model.Snack;
import ua.com.alevel.food_supporter_bot.model.response.SnackResponse;
import ua.com.alevel.food_supporter_bot.repository.SnackRepository;
import ua.com.alevel.food_supporter_bot.service.snack.SnackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SnackServiceImplTest {

    private SnackService snackService;
    private SnackRepository snackRepository;

    @BeforeEach
    void setUp() {
        snackRepository = mock(SnackRepository.class);
        snackService = new SnackServiceImpl(snackRepository);
    }

    @Test
    void getSnacks() {
        var snack = new Snack(1L, "name", 700, 6.6, 7.7, 8.8);
        List<Snack> snacks = new ArrayList<>();
        snacks.add(snack);

        when(snackRepository.findTop7ByCaloriesBetween(any(), any())).thenReturn(snacks);

        List<SnackResponse> snackResponses = snackService.getSnacks(700);
        Optional<SnackResponse> presentResponse = Optional.ofNullable(snackResponses.get(1));

        assertThat(presentResponse).hasValueSatisfying(snackResponse ->
                assertSnackMatchesResponse(snack, snackResponse));
    }

    private static void assertSnackMatchesResponse(Snack snack, SnackResponse snackResponse) {
        assertThat(snackResponse.getName()).isEqualTo(snack.getName());
        assertThat(snackResponse.getCalories()).isEqualTo(snack.getCalories());
        assertThat(snackResponse.getProtein()).isEqualTo(snack.getProtein());
        assertThat(snackResponse.getFat()).isEqualTo(snack.getFat());
        assertThat(snackResponse.getCarbs()).isEqualTo(snack.getCarbs());
    }
}