package hash.code.resources;

import java.util.ArrayList;
import java.util.List;

import hash.code.io.Writer;
import hash.code.judge.Judge;

public class Manager {
    private final Customer[] customers;
    private final String name;
    private final List<Ingradient> ingradients;
    private final List<Ingradient> offered;

    public Manager(int customers, String name) {
        this.name = name;
        this.customers = new Customer[customers];
        this.ingradients = new ArrayList<Ingradient>();
        this.offered = new ArrayList<Ingradient>();
    }

    public void addCustomer(int index, String[] likesNames, String[] dislikesNames) {
        Ingradient[] likes = namesToIngradients(likesNames);
        Ingradient[] dislikes = namesToIngradients(dislikesNames);
        customers[index] = new Customer(likes, dislikes);
        for (Ingradient ingradient : dislikes)
            ingradient.addDisLike(customers[index]);
        for (Ingradient ingradient : likes)
            ingradient.addLiker(customers[index]);
    }

    public void compute() {
        computeOffered();
        Thread writer = new Thread() {
            public void run() {
                write();
            }
        };
        Thread judge = new Thread() {
            public void run() {
                judge();
            }
        };
        writer.start();
        judge.start();
    }

    private List<Ingradient> computeOffered() {
        for (Ingradient ingradient : ingradients) {
            if (ingradient.getLikes().size() > ingradient.getDislikes().size())
                offered.add(ingradient);
        }
        return offered;
    }

    public class Customer {
        private Ingradient[] likes;
        private Ingradient[] dislikes;

        public Customer(Ingradient[] likes, Ingradient[] dislikes) {
            this.likes = likes;
            this.dislikes = dislikes;
        }

        public Ingradient[] getLikes() {
            return likes;
        }

        public Ingradient[] getDislikes() {
            return dislikes;
        }
    }

    public class Ingradient {
        private String name;
        private List<Customer> likers;
        private List<Customer> dislikers;

        public Ingradient(String name) {
            this.name = name;
            this.likers = new ArrayList<Customer>();
            this.dislikers = new ArrayList<Customer>();
        }

        public void addLiker(Customer customer) {
            this.likers.add(customer);
        }

        public void addDisLike(Customer customer) {
            this.dislikers.add(customer);
        }

        public List<Customer> getLikes() {
            return likers;
        }

        public List<Customer> getDislikes() {
            return dislikers;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Ingradient ingradient = (Ingradient) o;

            return name.equals(ingradient.name);
        }

        public int hashCode() {
            return name.hashCode();
        }
    }

    private void write() {
        String[] offeredNames = new String[offered.size()];
        for (Ingradient ingradient : offered)
            offeredNames[offered.indexOf(ingradient)] = ingradient.getName();
        new Writer(name, offeredNames);
    }

    private void judge() {
        new Judge(this);
    }

    public List<Ingradient> getIngradients() {
        return ingradients;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public String getName() {
        return name;
    }

    public List<Ingradient> getOffered() {
        return offered;
    }

    private Ingradient[] namesToIngradients(String[] names) {
        Ingradient[] ingradients = new Ingradient[names.length];
        for (int i = 0; i < names.length; i++) {
            if (this.ingradients.contains(new Ingradient(names[i]))) {
                ingradients[i] = this.ingradients.get(this.ingradients.indexOf(new Ingradient(names[i])));
            } else {
                ingradients[i] = new Ingradient(names[i]);
                this.ingradients.add(ingradients[i]);
            }
        }
        return ingradients;
    }

}
