package hash.code.judge;

import hash.code.resources.Manager;
import hash.code.resources.Manager.Customer;
import hash.code.resources.Manager.Ingradient;

public class Judge {
    private Manager manager;

    public Judge(Manager manager) {
        this.manager = manager;
        judge();
    }

    private void judge() {
        int score = 0;
        for (Customer customer : manager.getCustomers()) {
            boolean didLike = true;
            for (Ingradient ingradient : customer.getLikes()) {
                if (!manager.getOffered().contains(ingradient)) {
                    didLike = false;
                    break;
                }
            }
            if (!didLike)
                continue;
            for (Ingradient ingradient : customer.getDislikes()) {
                if (manager.getOffered().contains(ingradient)) {
                    didLike = false;
                    break;
                }
            }
            if (didLike)
                score++;
        }
        System.out.println(manager.getName() + " " + score + " out of " + manager.getCustomers().length);
    }

}
