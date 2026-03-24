package dev.anthonyadcs.beverage_production_system.domain.valueObject;

import dev.anthonyadcs.beverage_production_system.exception.InvalidEntityCodeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Embeddable
@Getter
public class EntityCode {
    @Column(length = 50, nullable = false, unique = true, updatable = false)
    private String code;

    protected EntityCode(){};

    private EntityCode(String value){
        validate(value);
        this.code = value;
    }

    public static EntityCode create(String prefix) {
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

        StringBuilder randomPart = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < 4; i++){
            if(i % 2 == 0){
                String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                randomPart.append(alphabet.charAt(random.nextInt(alphabet.length())));
            } else {
                String numbers = "0123456789";
                randomPart.append(numbers.charAt(random.nextInt(numbers.length())));
            }
        }

        return new EntityCode(
                prefix + "-" + hour + "-" + randomPart
        );
    }

    public static void validate(String code){
        if(code == null || code.isEmpty() || !code.matches("^[A-Z]+-\\d{6}-[A-Z0-9]{4}$")){
            throw new InvalidEntityCodeException(
                    String.format(
                            "O código '%s' é inválido. O formato esperado é 'PREFIX-YYYYMMDD-XXXX' (ex: PROD-20260101-A1B2).",
                            code
                    )
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EntityCode that = (EntityCode) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
