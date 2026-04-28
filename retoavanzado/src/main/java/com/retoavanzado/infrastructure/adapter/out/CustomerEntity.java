package com.retoavanzado.infrastructure.adapter.out;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "customers")
@Setter
@Getter
public class CustomerEntity implements Persistable<String> {
    
    @Id
    private String identification;
    private String name;
    private String gender;
    private String address;
    private String phone;
    private String password;
    private boolean status;
    
    @Transient
    private Boolean isNew = true;

    @Override
    public String getId() {
        return this.identification;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }



}
