import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Builder<T> {
    private final Supplier<T> constructor;

    private final List<Consumer<T>> dInjects = new ArrayList<>();

    public Builder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    private static <T> Builder<T> builder(Supplier<T> constructor) {
        return new Builder<>(constructor);
    }

    private <U> Builder<T> with(BiConsumer<T, U> consumer, U value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        dInjects.add(c);
        return this;
    }

    public T build() {
        T instance = constructor.get();
        dInjects.forEach(dInject -> dInject.accept(instance));
        return instance;
    }

    public static void main(String[] args) {
        Student student = Builder.builder(Student::new).with(Student::setName, "张三").with(Student::setAge, 18).build();
        System.out.println();
    }

    static class Student {

        private String name;

        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
