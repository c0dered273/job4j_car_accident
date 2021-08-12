package ru.job4j.model;

import java.util.Objects;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@RequiredArgsConstructor(staticName = "of")
public class Accident {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private String text;

    @NonNull
    private String address;

    @NonNull
    private AccidentType type;

    @NonNull
    private Set<Rule> rules;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accident accident = (Accident) o;
        return id == accident.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
