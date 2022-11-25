import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyBuilder<T> {

    private final Supplier<T> constructor;

    private Consumer<T> consumer = null;

    public MyBuilder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    private static <T> MyBuilder<T> builder(Supplier<T> constructor) {
        return new MyBuilder<>(constructor);
    }

    private <U> MyBuilder<T> with(BiConsumer<T, U> biConsumer, U value) {
        if (Objects.isNull(consumer)) {
            consumer = instance -> biConsumer.accept(instance, value);
        } else {
            consumer = consumer.andThen(instance -> biConsumer.accept(instance, value));
        }
        return this;
    }

    public T build() {
        T instance = constructor.get();
        consumer.accept(instance);
        return instance;
    }

    public static void main(String[] args) {
        Student student = MyBuilder.builder(Student::new).with(Student::setName, "张三").with(Student::setAge, 18)
                .with(Student::setPhone, "19869836985").with(Student::setAddress, "深海大菠萝").build();
        System.out.println();
    }

    static class Student {

        private String name;

        private Integer age;

        private String phone;

        private String address;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
