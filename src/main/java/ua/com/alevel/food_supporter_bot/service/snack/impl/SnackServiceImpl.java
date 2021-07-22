package ua.com.alevel.food_supporter_bot.service.snack.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.food_supporter_bot.model.Snack;
import ua.com.alevel.food_supporter_bot.model.response.SnackResponse;
import ua.com.alevel.food_supporter_bot.repository.SnackRepository;
import ua.com.alevel.food_supporter_bot.service.snack.SnackService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SnackServiceImpl implements SnackService {

    private final SnackRepository snackRepository;

    public SnackServiceImpl(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    @Transactional
    public List<SnackResponse> getSnacks(Integer calories) {
        log.info("Start find snacks");
        List<Snack> snacks = snackRepository.findTop7ByCaloriesBetween(calories, (int) (calories * 1.15));
        if (snacks.size() == 0) {
            snacks = snackRepository.findTop7ByCaloriesBetween(
                    (int) (calories * 0.70), (int) (calories * 1.20));
        }
        log.info("Finish find snacks");
        if (snacks.size() > 0 && snacks.size() < 7) {
            return increaseInTheNumberOfSnacks(snacks)
                    .stream().map(SnackResponse::fromSnack)
                    .collect(Collectors.toList());
        }
        return snacks.stream().map(SnackResponse::fromSnack)
                .collect(Collectors.toList());
    }

    private List<Snack> increaseInTheNumberOfSnacks(List<Snack> snacks) {
        while (snacks.size() != 7) {
            for (int i = 0; i < snacks.size(); i++) {
                snacks.add(snacks.get(i));
                if (snacks.size() == 7)
                    break;
            }
        }
        return snacks;
    }
}
