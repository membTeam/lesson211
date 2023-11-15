package beanComp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
    private Random rand;

    public RandomService( Random arg) {
        this.rand = arg;
    }

    public int getRandomInt() {
        return rand.nextInt();
    }
}
