package com.gts.backcommons.models;


import jakarta.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("{\n");

        Field[] fields = this.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);


                if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class)
                        || field.isAnnotationPresent(ManyToMany.class)) {

                    setUpSubFields(field, sb);

                }
                // Tous les autres champs → affichage normal
                else {

                    Object value = field.get(this);
                    sb.append("  ")
                            .append(field.getName())
                            .append(": '")
                            .append(value)
                            .append("',\n");
                }
            }

            // Supprime la dernière virgule
            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }

            sb.append("\n}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void setUpSubFields(Field field, StringBuilder sb) throws IllegalAccessException {
        Object value = field.get(this);
        if (value != null) {
            sb.append("  ")
                    .append(field.getName())
                    .append(": {");

            Field[] innerFields = value.getClass().getDeclaredFields();
            for (Field inner : innerFields) {
                inner.setAccessible(true);
                Class<?> type = inner.getType();

                // garder uniquement les types simples / primitifs
                if (type.isPrimitive()
                        || type == String.class
                        || Number.class.isAssignableFrom(type)
                        || type == Boolean.class
                        || type.isEnum()
                        || type.getPackageName().startsWith("java.time")) {
                    Object innerValue = inner.get(value);
                    sb.append(inner.getName())
                            .append(": '")
                            .append(innerValue)
                            .append("', ");
                }
            }

            // Supprimer la dernière virgule
            if (sb.charAt(sb.length() - 2) == ',') {
                sb.setLength(sb.length() - 2);
            }

            sb.append("},\n");
        }
    }


}

